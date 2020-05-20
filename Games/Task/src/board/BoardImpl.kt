package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)

open class SquareBoardImpl(final override val width: Int) : SquareBoard {
    private val cells: MutableList<Cell> = mutableListOf()
    val cellRange: IntRange = 1..width

    init {
        for (i in cellRange) {
            for (j in cellRange) {
                cells.add(Cell(i, j))
            }
        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? = cells.getOrNull(j + (i - 1) * width - 1)

    override fun getCell(i: Int, j: Int): Cell = cells[j + (i - 1) * width - 1]

    override fun getAllCells(): Collection<Cell> = cells

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        if (i !in cellRange) throw IllegalArgumentException()
        val row = mutableListOf<Cell>()
        for (j in jRange) {
            if (j < 1) continue
            if (j > width) break
            row.add(cells[j + (i - 1) * width - 1])
        }
        return row
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        if (j !in cellRange) throw IllegalArgumentException()
        val col = mutableListOf<Cell>()
        for (i in iRange) {
            if (i < 1) continue
            if (i > width) break
            col.add(cells[j + (i - 1) * width - 1])
        }
        return col
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? =
            when (direction) {
                UP -> cells.getOrNull(j + (i - 2) * width - 1)
                DOWN -> cells.getOrNull(j + i * width - 1)
                LEFT -> cells.getOrNull(j - 1 + (i - 1) * width - 1)
                RIGHT -> cells.getOrNull(j + 1 + (i - 1) * width - 1)
            }
}

class GameBoardImpl<T>(width: Int) : GameBoard<T>, SquareBoardImpl(width) {
    private val map = mutableMapOf<Cell, T?>()

    init {
        for (i in cellRange) {
            for (j in cellRange) {
                map[getCell(i, j)] = null
            }
        }
    }

    override fun get(cell: Cell): T? = map.getOrDefault(cell, null)

    override fun set(cell: Cell, value: T?) {
        map[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> = map.filterValues(predicate).keys

    override fun find(predicate: (T?) -> Boolean): Cell? {
        val value = map.values.find(predicate)
        return map.entries.find { (_, v) -> v == value }?.key
    }

    override fun any(predicate: (T?) -> Boolean): Boolean = map.values.any(predicate)

    override fun all(predicate: (T?) -> Boolean): Boolean = map.values.all(predicate)

}