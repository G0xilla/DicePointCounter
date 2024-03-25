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

/**
 * Add a new item at the end of [Player.pointsHistory] on player with appropriate index.
 * This item will be the sum of the last item in [Player.pointsHistory] and [points]. If
 * [Player.pointsHistory] is empty then the new item will have a [points] value.
 *
 * @return new list with updated player
 * @throws IndexOutOfBoundsException if [playerIndex] is more than or equal to the size of the list
 * @param playerIndex updated player
 */
fun List<Player>.addPoints(playerIndex: Int, points: Double): List<Player> {
    val newList = this.toMutableList()
    val player = newList.removeAt(playerIndex)
    val newPlayer = player.addPoints(points)
    newList.add(playerIndex, newPlayer)
    return newList.toList()
}

/**
 * Get the last item from [Player.pointsHistory] from player with appropriate index.
 *
 * @return last item from [Player.pointsHistory] with appropriate index
 * @throws IndexOutOfBoundsException if [playerIndex] is more than or equal to the size of the list
 * @param playerIndex index of the player
 */
fun List<Player>.getPoints(playerIndex: Int): Double {
    return this[playerIndex].getPoints()
}

/**
 * Remove the last item from the [Player.pointsHistory] with the appropriate index.
 *
 * @return new list with updated [Player]
 * @throws IndexOutOfBoundsException if [playerIndex] is more than or equal to the size of the list
 * @throws IllegalStateException if [Player.pointsHistory] is empty
 */
fun List<Player>.revertPoints(playerIndex: Int): List<Player> {
    val newList = this.toMutableList()
    val player = newList.removeAt(playerIndex)
    val newPlayer = player.revertPoints()
    newList.add(playerIndex, newPlayer)
    return newList.toList()
}