import scala.util.{Failure, Success, Try}

object Main extends App  {


  val sql = """ SELECT (SUM(quantium_7q_cube.sum_tot_amt_incld_gst)/COUNT(DISTINCT quantium_7q_cube.count_distinct_rng_store_nbr_key)) AS
              |Sales_Per_Range_Store,
              |quantium_7q_cube.district_code AS district_code FROM quantium.quantium_7q_cube quantium_7q_cube
              |WHERE ((quantium_7q_cube.ranged_in_store = 1) AND (quantium_7q_cube.sgmnt_key = 2669)
              |AND (quantium_7q_cube.promotion_week_key between 20170101 and 20170601))
              |GROUP BY quantium_7q_cube.district_code""".stripMargin

  val tryData = for{
    con <- KyvosRepository.createConnection()
    res <- KyvosRepository.exexuteQuery(con)(sql)
  }yield(res)


  tryData match {
    case a: Success[scala.collection.mutable.Set[Map[String, _]]] => println(a.value)
    case b: Failure[_] => b.exception.printStackTrace()
  }
}