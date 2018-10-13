import java.sql.{Connection, DriverManager}

import scala.util.Try

object KyvosRepository {

  val driverName: String = "org.apache.hive.jdbc.HiveDriver"

  def createConnection(): Try[Connection] = {
    Try {
      Class.forName(driverName)
      DriverManager.getConnection("jdbc:hive2://stage.kyvosinsights.com:8081/;transportMode=http;httpPath=kyvos/sql", "admin", "Quantium@123")
    }
  }

  def exexuteQuery(con: Connection)(sql: String): Try[scala.collection.mutable.Set[Map[String, AnyRef]]] = {

    Try {
      val result = scala.collection.mutable.Set[Map[String, AnyRef]]()

      val stmt = con.createStatement()

      stmt.executeQuery(sql)
      var res = stmt.executeQuery(sql)

      var cols = for {
        i <- 1 to res.getMetaData.getColumnCount
        col = res.getMetaData.getColumnName(i)
      } yield ((i, col))

      while (res.next()) {
        var row = cols.map(a => (a._2, res.getObject(a._1))).toMap
        result += row
      }
      result
    }
  }
}

