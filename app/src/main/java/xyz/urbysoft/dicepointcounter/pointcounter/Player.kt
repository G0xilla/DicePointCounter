package xyz.urbysoft.dicepointcounter.pointcounter

/**
 * Data structure which represent player. The structure trace save name of the player in [name]
 * and points history in [pointsHistory].
 */
data class Player(
    val name: String,
    val pointsHistory: List<Double> = listOf()
)

/**
 * Add a new item at the end of [Player.pointsHistory]. This item will be the sum of
 * the last item in [Player.pointsHistory] and [points]. If [Player.pointsHistory] is empty
 * then the new item will have a [points] value.
 *
 * @return new [Player] with new [Player.pointsHistory]
 */
fun Player.addPoints(points: Double): Player {
    val newPoints = if(this.pointsHistory.isNotEmpty()) {
        pointsHistory.last() + points
    } else {
        points
    }
    val newList = this.pointsHistory.toMutableList()
    newList.add(newPoints)

    return this.copy(pointsHistory = newList)
}

/**
 * Remove the last item from the [Player.pointsHistory].
 *
 * @return new [Player] with new [Player.pointsHistory]
 * @exception IllegalStateException if [Player.pointsHistory] is empty
 */
fun Player.revertPoints(): Player {
    val newList = this.pointsHistory.toMutableList()
    try {
        newList.removeLast()
    } catch (e: NoSuchElementException) {
        throw IllegalStateException("Cannot revert points if points history is empty.", e)
    }

    return this.copy(pointsHistory = newList.toList())
}

/**
 * Get last item from [Player.pointsHistory]
 *
 * @return last item from [Player.pointsHistory]
 */
fun Player.getPoints(): Double {
    return this.pointsHistory.lastOrNull() ?: 0.0
}