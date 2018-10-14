
import scala.util.{Failure, Success, Try}
import cats.effect._
import fs2.StreamApp
import fs2.StreamApp.ExitCode
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext.Implicits.global

object Main extends StreamApp[IO]{

  val sqle =
    """ SELECT (SUM(quantium_7q_cube.sum_tot_amt_incld_gst)/COUNT(DISTINCT quantium_7q_cube.count_distinct_rng_store_nbr_key)) AS
      |Sales_Per_Range_Store,
      |quantium_7q_cube.district_code AS district_code FROM quantium.quantium_7q_cube quantium_7q_cube
      |WHERE ((quantium_7q_cube.ranged_in_store = 1) AND (quantium_7q_cube.sgmnt_key = 2669)
      |AND (quantium_7q_cube.promotion_week_key between 20170101 and 20170601))
      |GROUP BY quantium_7q_cube.district_code""".stripMargin

  val helloWorldService = HttpService[IO] {
    case req @ POST -> Root / "sql" =>

      val res = executeSql(req.body.toString)
      res match {
        case Success(a) => Ok(s"${a.size}")
        case Failure(e) => InternalServerError(e.getMessage)
      }
  }

  def executeSql(sql: String): Try[scala.collection.mutable.Set[Map[String, AnyRef]]] = {
     for {
      con <- KyvosRepository.createConnection()
      res <- KyvosRepository.exexuteQuery(con)(sql)
    } yield (res)
  }
  override def stream(args: List[String], requestShutdown: IO[Unit]): fs2.Stream[IO, ExitCode] =
    BlazeBuilder[IO]
      .bindHttp(8080, "localhost")
      .mountService(helloWorldService, "/")
      .serve
}