package tienda.funciones

import cats.effect._
import cats.effect.unsafe.implicits.global
import doobie.implicits._
import doobie.util.transactor

import java.time.LocalDate
import scala.io.StdIn.readLine


case class funciones() {
  def insertar(value: transactor.Transactor.Aux[IO, Unit]): Unit = {
    println("¿Que juego quiere insertar?")
    val gameinsert = readLine()
    QueryFun.insert(gameinsert).run.transact(value)
  }

  def eliminar(xa: transactor.Transactor.Aux[IO, Unit]): Unit = {
    println("¿Que juego quiere eliminar?")
    val game = readLine()
    QueryFun.delete(game).run.transact(xa)
  }

  def select(xa: transactor.Transactor.Aux[IO, Unit]): Unit = {
    QueryFun.select
      .to[List]
      .transact(xa)
      .unsafeRunSync()
      .foreach(println)
  }

  def update(xa: transactor.Transactor.Aux[IO, Unit]): Unit = {
    println("¿Que juego quieres alquilar?")
    val juegoAl = readLine()
    println("¿Cuantos dias quieres alquilarlo?")
    val dias = readLine().toInt
    val hoy = LocalDate.now
    QueryFun.update(dias, hoy, juegoAl).run.transact(xa)
  }

  def alquilados(xa: transactor.Transactor.Aux[IO, Unit]): Unit = {
    QueryFun.alquilar
      .to[List]
      .transact(xa)
      .unsafeRunSync()
      .foreach(println)
  }
}
