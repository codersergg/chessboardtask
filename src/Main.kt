class Chessboard(
    private val startPosition: Position,
    private val figureSteps: List<Position>,
    private val size: Int = 8
) {
    private val board: Array<IntArray> = Array(size) { IntArray(size) { -1 } }

    init {
        board.step(Position(startPosition.x, startPosition.y), 0)
        println("Start $startPosition")
        println()
    }

    fun findSolution(currentPosition: Position = startPosition, stepNumber: Int = 1): Boolean {
        if (stepNumber == size * size) return true
        figureSteps
            .map { Position(currentPosition.x + it.x, currentPosition.y + it.y) }
            .filter { isValidStep(it) }
            .forEach {
                if (findSolution(it, board.step(it, stepNumber))) return true
                board.backStep(it)
            }
        return false
    }

    fun print() {
        for (row in board) {
            for (n in row) {
                print(n.toString().padStart(4))
            }
            println()
        }
    }

    private fun Array<IntArray>.step(p: Position, n: Int): Int {
        val nextStepNumber = n + 1
        this[p.x][p.y] = nextStepNumber
        return nextStepNumber
    }

    private fun Array<IntArray>.backStep(p: Position) {
        this[p.x][p.y] = -1
    }

    private fun isValidStep(p: Position) =
        p.x in 0..<size && p.y in 0..<size && board[p.x][p.y] == -1
}

data class Position(val x: Int, val y: Int)

val horseSteps = listOf(
    Position(-1, -2),
    Position(-1, 2),
    Position(1, -2),
    Position(1, 2),
    Position(-2, -1),
    Position(-2, 1),
    Position(2, -1),
    Position(2, 1)
)

fun main() {
    val startPosition = Position(0, 0)
    val chessboard = Chessboard(startPosition, horseSteps)

    if (chessboard.findSolution()) chessboard.print()
    else println("No solution exists from $startPosition")
}
