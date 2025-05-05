package example

object Stats {
  def mean(values: List[Double]): Double =
    if values.nonEmpty then values.sum / values.length else 0.0

  def median(values: List[Double]): Double = {
    val sorted = values.sorted
    val size = sorted.length
    if size == 0 then 0.0
    else if size % 2 == 1 then sorted(size / 2)
    else (sorted(size / 2 - 1) + sorted(size / 2)) / 2
  }

  def mode(values: List[Double]): List[Double] = {
    val grouped = values.groupBy(identity).view.mapValues(_.size).toMap
    val maxCount = grouped.values.max
    grouped.filter(_._2 == maxCount).keys.toList
  }

  def range(values: List[Double]): Double =
    if values.isEmpty then 0.0 else values.max - values.min

  def midrange(values: List[Double]): Double =
    if values.isEmpty then 0.0 else (values.max + values.min) / 2
}