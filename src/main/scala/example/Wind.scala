package example

import java.time.LocalDate

class Wind(id: String) extends Generator(id) {
override def generateEnergyList(dates: List[LocalDate]): List[(LocalDate, Double)] = {
    val multipliers = List(1.0, 1.0, 1.0, 1.0)
    val outputRange = (15, 61)
    val entries = dates.map(date => date -> generateEnergy(date, multipliers, outputRange))
    appendToHistory(entries)
    entries
}
}