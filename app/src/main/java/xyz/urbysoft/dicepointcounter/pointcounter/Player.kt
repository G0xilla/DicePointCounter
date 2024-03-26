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
 * Add a new item at the end of [Player.pointsHistory] on player in list. This item will be
 * the sum of the last item in [Player.pointsHistory] and [points]. If [Player.pointsHistory]
 * is empty then the new item will have a [points] value.
 *
 * @return new list with updated player
 * @throws IllegalArgumentException if [player] is not in the list
 */
fun List<Player>.addPoints(player: Player, points: Double): List<Player> {
    val newList = this.toMutableList()
    val newPlayer = newList.find { it === player }?.addPoints(points)
        ?: throw IllegalArgumentException("Cannot find a player")
    newList.replaceAll {
        if(player === it) {
            newPlayer
        } else {
            it
        }
    }
    return newList.toList()
}

/**
 * Get the last item from [Player.pointsHistory] from player in list
 *
 * @return last item from [Player.pointsHistory]
 * @throws IllegalArgumentException if [player] is not in list
 */
fun List<Player>.getPoints(player: Player): Double {
//    return this[playerIndex].getPoints()
    return this.find {
        it === player
    }?.getPoints()
        ?: throw IllegalArgumentException("Cannot find a player")
}

/**
 * Remove the last item from the [Player.pointsHistory] from player in the list
 *
 * @return new list with updated [player]
 * @throws IllegalArgumentException if [player] is not in the list
 * @throws IllegalStateException if [Player.pointsHistory] is empty
 */
fun List<Player>.revertPoints(player: Player): List<Player> {
    val newList = this.toMutableList()
    val newPlayer = newList.find { it === player }?.revertPoints()
        ?: throw IllegalArgumentException("Cannot find a player")
    newList.replaceAll {
        if(it === player) {
            newPlayer
        } else {
            it
        }
    }
    return newList
}