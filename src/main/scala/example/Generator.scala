package example

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.io.{File, PrintWriter}
import scala.io.Source
import scala.util.Random
import java.io.BufferedWriter
import java.io.FileWriter

abstract class Generator(val id: String) {

  val filePath: String = s"$id.csv"
  

  def readProductionHistory(): List[(LocalDate, Double)] = {
    if !File(filePath).exists then return List()
    val lines = Source.fromFile(filePath).getLines().drop(1)
    lines.map { line =>
      val parts = line.split(",")
      (LocalDate.parse(parts(0)), parts(1).toDouble)
    }.toList
  }

  def appendToHistory(entries: List[(LocalDate, Double)]): Unit = {
    val fileExists = File(filePath).exists
    val writer = new PrintWriter(new java.io.FileOutputStream(filePath, true))
    if (!fileExists) writer.println("Date,Output")
    entries.foreach { case (date, output) =>
      writer.println(s"$date,$output")
    }
    writer.close()
  }

  def totalOutput(): Double = readProductionHistory().map(_._2).sum

  protected def generateEnergy(date: LocalDate, multipliers: List[Double], outputRange: (Int, Int)): Double = {
    val initial = Random.between(outputRange._1, outputRange._2)
    val month = date.getMonthValue
    month match {
      case m if 1 to 3 contains m => initial * multipliers(0)
      case m if 4 to 5 contains m => initial * multipliers(1)
      case m if 6 to 8 contains m => initial * multipliers(2)
      case m if 9 to 12 contains m => initial * multipliers(3)
    }
  }

  def generateEnergyList(dates: List[LocalDate]): List[(LocalDate, Double)]

  def name: String = s"$id"
}