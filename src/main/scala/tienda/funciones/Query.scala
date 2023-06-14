package tienda.funciones

import doobie.Update0
import doobie.implicits._

import java.time.LocalDate

object QueryFun {

  val select = sql"select user_id, juego from tienda"
    .query[(Int, String)]

   val alquilar = sql"select user_id, juego from tienda where alquilado = true"
    .query[(Int, String)]

  def update(dias:Int, hoy:LocalDate, juegoAl:String): Update0 = {
    val diafinal = hoy.plusDays(dias)
    sql"""update tienda set alquilado = true, fechaAl = $hoy, fechaDev = $diafinal where juego = $juegoAl """.update
  }

  def delete(game: String):Update0= {
    sql"delete from tienda where juego = $game".update
  }

  def insert(game: String): Update0 = {
    sql"insert into tienda (juego) values ($game)".update
  }
}
