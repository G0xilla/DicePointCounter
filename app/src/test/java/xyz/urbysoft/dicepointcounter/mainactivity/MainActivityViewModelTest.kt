package xyz.urbysoft.dicepointcounter.mainactivity

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import xyz.urbysoft.dicepointcounter.pointcounter.Player

class MainActivityViewModelTest {

    @Test
    fun testInitStateOfPlayerList() {
        val exceptedState: List<Player>? = null

        val actualState = MainActivityViewModel().playerList.value

        assertEquals(exceptedState, actualState)
    }

    @Test
    fun testStartNewGame() {
        val exceptedState = listOf(
            Player("G0xilla"),
            Player("Trenazery")
        )

        val viewModel = MainActivityViewModel()
        viewModel.startNewGame(
            listOf("G0xilla", "Trenazery")
        )
        val actualState = viewModel.playerList.value

        assertEquals(exceptedState, actualState)
    }

    @Test
    fun testResetGame() {
        val exceptedState: List<Player>? = null

        val viewModel = MainActivityViewModel()
        viewModel.startNewGame(
            listOf("G0xilla", "Trenazery")
        )
        viewModel.resetGame()
        val actualState = viewModel.playerList.value

        assertEquals(exceptedState, actualState)
    }

    @Test
    fun testAddPoints() {
        val exceptedState = listOf(
            Player("G0xilla"),
            Player("Trenazery", listOf(10.0, 15.0))
        )

        val viewModel = MainActivityViewModel()
        viewModel.startNewGame(listOf("G0xilla", "Trenazery"))
        viewModel.addPoints(viewModel.playerList.value!![1], 10.0)
        viewModel.addPoints(viewModel.playerList.value!![1], 5.0)
        val actualState = viewModel.playerList.value

        assertEquals(exceptedState, actualState)
    }

    @Test
    fun testRevertPoints() {
        val exceptedState = listOf(
            Player("G0xilla", listOf(10.0))
        )

        val viewModel = MainActivityViewModel()
        viewModel.startNewGame(listOf("G0xilla"))
        viewModel.addPoints(viewModel.playerList.value!![0], 10.0)
        viewModel.addPoints(viewModel.playerList.value!![0], 5.0)
        viewModel.revertPoints(viewModel.playerList.value!![0])
        val actualState = viewModel.playerList.value

        assertEquals(exceptedState, actualState)
    }
    @Test
    fun testAddPoints_whenPlayerIsNotInThePlayerList_thenThrowIllegalArgumentException() {
        val exceptedException = IllegalArgumentException::class.java

        val viewModel = MainActivityViewModel()
        viewModel.startNewGame(listOf("G0xilla"))

        assertThrows(exceptedException) {
            viewModel.addPoints(Player("Trenazery"), 10.0)
        }
    }

    @Test
    fun testRevertPoints_whenPlayerIsNotInThePlayerList_thenThrowIllegalArgumentException() {
        val exceptedException = IllegalArgumentException::class.java

        val viewModel = MainActivityViewModel()
        viewModel.startNewGame(listOf("G0xilla"))

        assertThrows(exceptedException) {
            viewModel.revertPoints(Player("Trenazery"))
        }
    }

    @Test
    fun testRevertPoints_whenPlayerHistoryIsEmpty_thenThrowIllegalStateException() {
        val exceptedException = IllegalStateException::class.java

        val viewModel = MainActivityViewModel()
        viewModel.startNewGame(listOf("G0xilla"))

        assertThrows(exceptedException) {
            viewModel.revertPoints(viewModel.playerList.value!![0])
        }
    }

    @Test
    fun testAddPoints_whenPlayerListIsNull_thenThrowIllegalStateException() {
        val expectedException = IllegalStateException::class.java

        val viewModel = MainActivityViewModel()

        assertThrows(expectedException) {
            viewModel.addPoints(Player("G0xilla"), 10.0)
        }
    }

    @Test
    fun testRevertPoints_whenPlayerListIsNull_thenThrowIllegalStateException() {
        val expectedException = IllegalStateException::class.java

        val viewModel = MainActivityViewModel()

        assertThrows(expectedException) {
            viewModel.revertPoints(Player("G0xilla"))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testPersistPlayers() {
        val expectedPlayerList = listOf(
            Player("G0xilla", listOf(10.5, 15.5)),
            Player("Trenazery")
        )

        val savedStateHandle = SavedStateHandle()
        val viewModelOne = MainActivityViewModel(savedStateHandle)
        viewModelOne.startNewGame(listOf("G0xilla", "Trenazery"))
        viewModelOne.addPoints(viewModelOne.playerList.value!![0], 10.5)
        viewModelOne.addPoints(viewModelOne.playerList.value!![0], 5.0)

        val viewModelTwo = MainActivityViewModel(savedStateHandle)
        val actualPlayerList = viewModelTwo.playerList.value!!

        assertEquals(expectedPlayerList, actualPlayerList)
    }
}