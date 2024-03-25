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
     * Add points for player with appropriate index and update [playerList].
     *
     * @throws IndexOutOfBoundsException if [playerIndex] is more than or equal to the size of the list
     * @throws IllegalStateException if [playerList] is null
     */
    fun addPoints(playerIndex: Int, points: Double) {
        val newList = _playerList.value?.addPoints(playerIndex, points)
            ?: throw IllegalStateException()
        _playerList.value = newList
    }

    /**
     * Revert points for the player with appropriate index and update [playerList].
     *
     * @throws IndexOutOfBoundsException if [playerIndex] is more than or equal to the size of the list
     * @throws IllegalStateException if [Player.pointsHistory] is empty or [playerList] is null
     */
    fun revertPoints(playerIndex: Int) {
        val newList = _playerList.value?.revertPoints(playerIndex)
            ?: throw IllegalStateException()
        _playerList.value = newList
    }
}