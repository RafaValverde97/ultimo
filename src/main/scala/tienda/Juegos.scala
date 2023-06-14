package tienda

import cats.effect._
import doobie._
import scala.io.StdIn.readLine
import funciones.funciones



object Juegos {


  def main(args: Array[String]): Unit = {

    menu()
  }
  def menu(): Unit = {
    val xa = Transactor.fromDriverManager[IO](
      "org.postgresql.Driver", // driver classname
      "jdbc:postgresql:world", // connect URL (driver-specific)
      "postgres", // user
      "" // password
    )
    println("Hola, que desea hacer: ")
    println("1. Insertar nuevo juego")
    println("2. Eliminar un juego")
    println("3. Ver todos los jugeos")
    println("4. Alquilar un juego")
    println("5. Filtrar por juegos filtrados")
    println("6. Salir")

    val opcion = readLine()

    if (opcion == "1") {
      funciones().insertar(xa)
      menu()
    }else if(opcion == "2"){
      funciones().eliminar(xa)
      menu()
    }else if(opcion == "3"){
      funciones().select(xa)
      menu()
    }else if(opcion == "4"){
      funciones().update(xa)
      menu()
    }else if(opcion =="5"){
      funciones().alquilados(xa)
      menu()
    }else if(opcion =="6"){
      sys.exit(0)
    }else{
      println("Caracter no valido!")
      menu()
    }

  }

}
