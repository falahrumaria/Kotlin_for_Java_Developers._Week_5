package games.gameOfFifteen

import kotlin.random.Random

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation by lazy {
        val initialPermutation = (1..15).shuffled()
        if (isEven(initialPermutation)) return@lazy initialPermutation
        val index1 = Random.nextInt(15)
        val index2 = (index1 + Random.nextInt(14) + 1) % 15
        initialPermutation as MutableList
        val e = initialPermutation[index1]
        initialPermutation[index1] = initialPermutation[index2]
        initialPermutation[index2] = e
        return@lazy initialPermutation
    }
}

