package xyz.urbysoft.dicepointcounter.mainactivity

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import xyz.urbysoft.dicepointcounter.pointcounter.Player
import xyz.urbysoft.dicepointcounter.pointcounter.addPoints
import xyz.urbysoft.dicepointcounter.pointcounter.revertPoints

/**
 * [ViewModel] implementation for [MainActivity]. The implementation has a [playerList] which
 * contains information about the active players. Also have methods for alternate active players state
 * [startNewGame], [resetGame], [addPoints] and [revertPoints].
 */
class MainActivityViewModel : ViewModel() {
    private val _playerList = MutableStateFlow<List<Player>?>(null)

    /**
     * Player list of the active players
     */
    val playerList = _playerList.asStateFlow()

    /**
     * Start a new game. The value of [playerList] will be set on new player list with
     * names specified in [playerNames].
     */
    fun startNewGame(playerNames: List<String>) {
        val list = mutableListOf<Player>()
        playerNames.forEach {
            list.add(Player(it))
        }

        _playerList.value = list
    }

    /**
     * Reset a game. The value of [playerList] will be set on null.
     */
    fun resetGame() {
        _playerList.value = null
    }

    /**
     * Add points for the player and update [playerList]
     *
     * @throws IllegalArgumentException if [player] is not in the [playerList]
     * @throws IllegalStateException if [playerList] is null
     */
    fun addPoints(player: Player, points: Double) {
        val newList = _playerList.value?.addPoints(player, points)
            ?: throw IllegalStateException()
        _playerList.value = newList
    }

    /**
     * Revert points for the player and update [playerList]
     *
     * @throws IllegalArgumentException if [player] is not in the [playerList]
     * @throws IllegalStateException if [Player.pointsHistory] is empty or [playerList] is null
     */
    fun revertPoints(player: Player) {
        val newList = _playerList.value?.revertPoints(player)
            ?: throw IllegalStateException()
        _playerList.value = newList
    }
}