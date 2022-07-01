package com.test.gameoflife

import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
class Main {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val game = GameLife(
                listOf(
                    Point(0, 0),
                    Point(0, 1),
                    Point(0, 2),
                )
            )

            game.nextGeneration()
            val liveCells = game.getLiveCells()
            liveCells.forEach {
                System.out.println("${it.x} *** ${it.y}")
            }

        }
    }
}