package games.gameOfFifteen

import board.Cell
import board.Direction
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
        GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)
    private val cells = board.getAllCells() as List<Cell>
    private var nullCell = cells.last()

    override fun initialize() {
        val initPermutation = initializer.initialPermutation
        repeat(15) { i ->
            board[cells[i]] = initPermutation[i]
        }
    }

    override fun canMove(): Boolean = true

    override fun hasWon(): Boolean {
        repeat(15) { i ->
            if (board[cells[i]] != i + 1) return false
        }
        return true
    }

    override fun processMove(direction: Direction) {
        with(board) {
            val neighborCell = nullCell.getNeighbour(direction.reversed())
            if (neighborCell != null) {
                set(nullCell, get(neighborCell))
                set(neighborCell, null)
                nullCell = neighborCell
            }
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }

}