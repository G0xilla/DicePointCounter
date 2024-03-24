package xyz.urbysoft.dicepointcounter.pointcounter

import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import java.lang.IllegalStateException

class PlayerTest {
    @Test
    fun testAddPoints() {
        val exceptedPointsHistory = listOf(5.5, 15.5)

        val player = Player("G0xilla")
            .addPoints(5.5)
            .addPoints(10.0)
        val actualPointsHistory = player.pointsHistory

        assertEquals(exceptedPointsHistory, actualPointsHistory)
    }

    @Test
    fun testGetPoints() {
        val exceptedPoints = 15.5

        val player = Player("G0xilla")
            .addPoints(5.5)
            .addPoints(10.0)
        val actualPoints = player.getPoints()

        assertEquals(exceptedPoints, actualPoints)
    }

    @Test
    fun testRevertPoints() {
        val exceptedPointsHistory = listOf(5.5)

        val player = Player("G0xilla")
            .addPoints(5.5)
            .addPoints(10.0)
            .revertPoints()
        val actualPointsHistory = player.pointsHistory

        assertEquals(exceptedPointsHistory, actualPointsHistory)
    }

    @Test
    fun whenPointsHistoryIsEmpty_thenThrowIllegalStateException() {
        val expectedException = IllegalStateException::class.java

        val player = Player("G0xilla")

        assertThrows(expectedException) {
            player.revertPoints()
        }
    }
}