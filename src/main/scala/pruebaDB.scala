import doobie._
import doobie.implicits._
import cats._
import cats.effect._
import cats.implicits._
import doobie.util.ExecutionContexts
import cats.effect.unsafe.implicits.global

object pruebaDB {
  def main(args: Array[String]): Unit = {
    val xa = Transactor.fromDriverManager[IO](
      "org.postgresql.Driver", // driver classname
      "jdbc:postgresql:world", // connect URL (driver-specific)
      "postgres", // user
      "" // password
    )
    val program1 = 42.pure[ConnectionIO]
    val io = program1.transact(xa)
    io.unsafeRunSync()
    val program2 = sql"select 42".query[Int].unique
    val io2 = program2.transact(xa)
    io2.unsafeRunSync()

    val program3: ConnectionIO[(Int, Double)] =
      for {
        a <- sql"select 42".query[Int].unique
        b <- sql"select random()".query[Double].unique
      } yield (a, b)
    program3.transact(xa).unsafeRunSync()

    val interpreter = KleisliInterpreter[IO].ConnectionInterpreter
    val kleisli = program1.foldMap(interpreter)
    val io3 = IO(null: java.sql.Connection) >>= kleisli.run

    io3.unsafeRunSync()
  }
}
