package xyz.urbysoft.dicepointcounter.mainactivity

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
        viewModel.addPoints(1, 10.0)
        viewModel.addPoints(1, 5.0)
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
        viewModel.addPoints(0, 10.0)
        viewModel.addPoints(0, 5.0)
        viewModel.revertPoints(0)
        val actualState = viewModel.playerList.value

        assertEquals(exceptedState, actualState)
    }
    @Test
    fun testAddPoints_whenPlayerIndexIsMoreThanOrEqualToTheSizeOfTheList_thenThrowIndexOutOfBoundException() {
        val exceptedException = IndexOutOfBoundsException::class.java

        val viewModel = MainActivityViewModel()
        viewModel.startNewGame(listOf("G0xilla"))

        assertThrows(exceptedException) {
            viewModel.addPoints(1, 10.0)
        }
    }

    @Test
    fun testRevertPoints_whenPlayerIndexIsMoreThanOrEqualToTheSizeOfTheList_thenThrowIndexOutOfBoundException() {
        val exceptedException = IndexOutOfBoundsException::class.java

        val viewModel = MainActivityViewModel()
        viewModel.startNewGame(listOf("G0xilla"))

        assertThrows(exceptedException) {
            viewModel.revertPoints(1)
        }
    }

    @Test
    fun testRevertPoints_whenPlayerHistoryIsEmpty_thenThrowIllegalStateException() {
        val exceptedException = IllegalStateException::class.java

        val viewModel = MainActivityViewModel()
        viewModel.startNewGame(listOf("G0xilla"))

        assertThrows(exceptedException) {
            viewModel.revertPoints(0)
        }
    }

    @Test
    fun testAddPoints_whenPlayerListIsNull_thenThrowIllegalStateException() {
        val expectedException = IllegalStateException::class.java

        val viewModel = MainActivityViewModel()

        assertThrows(expectedException) {
            viewModel.addPoints(0, 10.0)
        }
    }

    @Test
    fun testRevertPoints_whenPlayerListIsNull_thenThrowIllegalStateException() {
        val expectedException = IllegalStateException::class.java

        val viewModel = MainActivityViewModel()

        assertThrows(expectedException) {
            viewModel.revertPoints(0)
        }
    }
}