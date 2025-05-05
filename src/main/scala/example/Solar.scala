package example

import java.time.LocalDate

class Solar(id: String) extends Generator(id) {
  override def generateEnergyList(dates: List[LocalDate]): List[(LocalDate, Double)] = {
    val multipliers = List(0.4, 0.6, 1.5, 0.6)
    val outputRange = (5, 31)
    val entries = dates.map(date => date -> generateEnergy(date, multipliers, outputRange))
    appendToHistory(entries)
    entries
  }
}