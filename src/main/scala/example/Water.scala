package example

import java.time.LocalDate

class Water(id: String) extends Generator(id) {
override def generateEnergyList(dates: List[LocalDate]): List[(LocalDate, Double)] = {
    val multipliers = List(0.6, 2.0, 0.8, 0.7)
    val outputRange = (25, 100)
    val entries = dates.map(date => date -> generateEnergy(date, multipliers, outputRange))
    appendToHistory(entries)
    entries
}
}