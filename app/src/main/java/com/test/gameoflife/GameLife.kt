package com.test.gameoflife

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*
import java.util.stream.Collectors.toSet
import java.util.stream.Stream
import java.util.stream.Stream.concat

@RequiresApi(Build.VERSION_CODES.N)
class GameLife(val initCells: List<Point>) {

    companion object {

        private val NEIGHBOURS = setOf(
            Point(0, 1),
            Point(0, -1),

            Point(1, 0),
            Point(1, -1),
            Point(1, 1),

            Point(-1, 0),
            Point(-1, -1),
            Point(-1, 1),
        )
    }

    private val liveCells = HashSet<Point>().apply {
        addAll(initCells)
    }

    fun getLiveCells() : Set<Point> = Collections.unmodifiableSet(liveCells)

    private fun getNeighbours(point: Point): Stream<Point> =
        NEIGHBOURS.stream().map { neighbour -> Point(neighbour.x + point.x, neighbour.y + point.y) }

    private fun getLiveNeighbours(point: Point): Int =
        getNeighbours(point).filter { liveCells.contains(it) }.count().toInt()

    private fun hasTwoOrThreeNeighboursLive(point: Point): Boolean {
        val neighbours = getLiveNeighbours(point)
        return neighbours == 2 || neighbours == 3
    }

    private fun getSurvivors(): Stream<Point> =
        liveCells.stream().filter(this::hasTwoOrThreeNeighboursLive)

    private fun newBorn(): Stream<Point> {
        return liveCells.stream().flatMap(this::toDeath).distinct()
            .filter(this::hasThreeLiveNeighbours)
    }

    private fun toDeath(point: Point): Stream<Point> =
        getNeighbours(point).filter { liveCells.contains(it).not() }

    private fun hasThreeLiveNeighbours(point: Point) = getLiveNeighbours(point) == 3

    fun nextGeneration() {
        val nextGenerationCells = concat(getSurvivors(), newBorn()).collect(toSet())
        liveCells.clear()
        liveCells.addAll(nextGenerationCells)
    }

}