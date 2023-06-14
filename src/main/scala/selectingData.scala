import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import fs2.Stream
import cats.effect.unsafe.implicits.global

object selectingData {

  def main(args: Array[String]): Unit = {
    val xa = Transactor.fromDriverManager[IO](
      "org.postgresql.Driver", // driver classname
      "jdbc:postgresql:world", // connect URL (driver-specific)
      "postgres", // user
      "" // password
    )
    val y = xa.yolo // a stable reference is required
    import y._
    import shapeless._

    import shapeless.record.Record

    type Rec = Record.`'code -> String, 'name -> String, 'pop -> Int, 'gnp -> Option[Double], 'continent -> String, 'region -> String, 'surfacearea -> String, 'localname -> String, 'governmentform -> String, 'code2 -> String`.T

    sql"select code, name, population, gnp, continent, region, surfacearea, localname, governmentform, code2 from country"
      .query[Rec]
      .stream
      .take(5)
      .quick
      .unsafeRunSync()

  }
}
