package example

import java.time.LocalDate
import scala.io.StdIn.*
import java.time.format.DateTimeFormatter

@main def main(): Unit = {
  println("Welcome to the Renewable Energy Plant Simulator!")
  menu(List(), List(), List())
}

def menu(solarList: List[Generator], waterList: List[Generator], windList: List[Generator]): Unit = {
  println("\nChoose an option:")
  println("1) Create a new generator")
  println("2) List generators")
  println("3) Generate energy")
  println("4) View Statistics")
  println("0) Exit")

  val input = readInt()

  input match
    case 1 =>
      val (solar, water, wind) = createGenerator(solarList, waterList, windList)
      menu(solar, water, wind)
    case 2 =>
      listGenerators(solarList, waterList, windList)
      menu(solarList, waterList, windList)
    case 3 =>
      val dates = askForDates()
      val updatedSolar = solarList.map(g => { g.generateEnergyList(dates); g })
      val updatedWater = waterList.map(g => { g.generateEnergyList(dates); g })
      val updatedWind = windList.map(g => { g.generateEnergyList(dates); g })
      menu(updatedSolar, updatedWater, updatedWind)
    case 4 =>
      (solarList ++ waterList ++ windList).foreach { gen =>
        val data = gen.readProductionHistory().map(_._2)
        println(s"\nStats for ${gen.name}:")
        println(s"  Mean: ${Stats.mean(data)}")
        println(s"  Median: ${Stats.median(data)}")
        println(s"  Mode: ${Stats.mode(data)}")
        println(s"  Range: ${Stats.range(data)}")
        println(s"  Midrange: ${Stats.midrange(data)}")
      }
      menu(solarList, waterList, windList)
    case 0 => sys.exit(0)
    case _ =>
      println("Invalid option!")
      menu(solarList, waterList, windList)
}

def createGenerator(solar: List[Generator], water: List[Generator], wind: List[Generator]): (List[Generator], List[Generator], List[Generator]) = {
  println("\nWhich generator do you want to create?")
  println("1) Solar\n2) Water\n3) Wind")

  val choice = readInt()

  choice match
    case 1 =>
      val id = s"Solar-${solar.length + 1}"
      (solar :+ new Solar(id), water, wind)
    case 2 =>
      val id = s"Water-${water.length + 1}"
      (solar, water :+ new Water(id), wind)
    case 3 =>
      val id = s"Wind-${wind.length + 1}"
      (solar, water, wind :+ new Wind(id))
    case _ =>
      println("Invalid input")
      (solar, water, wind)
}

def listGenerators(solar: List[Generator], water: List[Generator], wind: List[Generator]): Unit = {
  println("\nSolar:")
  solar.foreach(g => println(s"${g.name}, Total Output: ${g.totalOutput()}"))
  println("\nWater:")
  water.foreach(g => println(s"${g.name}, Total Output: ${g.totalOutput()}"))
  println("\nWind:")
  wind.foreach(g => println(s"${g.name}, Total Output: ${g.totalOutput()}"))
}

def askForDates(): List[LocalDate] = {
  println("Enter start date (yyyy-mm-dd):")
  val start = LocalDate.parse(readLine())

  println("Enter duration (days):")
  val days = readInt()

  (0 until days).map(start.plusDays(_)).toList
}
