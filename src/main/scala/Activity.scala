import doobie._
import doobie.implicits._
import cats.effect._
import cats.effect.unsafe.implicits.global

object Activity {

  case class Accounts(uid: Int, name: String)


  def main(args: Array[String]): Unit = {


    val xa = Transactor.fromDriverManager[IO](
      "org.postgresql.Driver", // driver classname
      "jdbc:postgresql:world", // connect URL (driver-specific)
      "postgres", // user
      "" // password
    )





    def insert1(codigo: Accounts): Update0 = {
      val id = codigo.uid
      val user = codigo.name
      sql"insert into accounts (user_id, username) values ($id, $user)".update
    }

    insert1(Accounts(7,"Persona 7")).run.transact(xa).unsafeRunSync()



    sql"select user_id, username from accounts"
      .query[(Accounts)]
      .to[List]
      .transact(xa)
      .unsafeRunSync()
      .foreach(println)


  }

}
