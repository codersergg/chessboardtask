class Chessboard(
    private val size: Int,
    private val startPosition: Position,
    private val figureSteps: List<Position>
) {
    private val board: Array<IntArray> = Array(size) { IntArray(size) { -1 } }

    init {
        board[startPosition.x][startPosition.y] = 1
        println("Start $startPosition")
        println()
    }

    fun findSolution(currentPosition: Position = startPosition, stepNumber: Int = 1): Boolean {
        if (stepNumber == size * size) return true

        val validSteps = figureSteps
            .map { Position(currentPosition.x + it.x, currentPosition.y + it.y) }
            .filter { isValidStep(it.x, it.y) }

        for (move in validSteps) {
            board[move.x][move.y] = stepNumber + 1
            if (findSolution(Position(move.x, move.y), stepNumber + 1)) return true
            board[move.x][move.y] = -1
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

    private fun isValidStep(x: Int, y: Int) =
        x in 0..<size && y in 0..<size && board[x][y] == -1

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
    val chessboardSize = 8
    val startPosition = Position(0, 0)
    val chessboard = Chessboard(chessboardSize, startPosition, horseSteps)

    if (chessboard.findSolution()) chessboard.print()
    else println("No solution exists from $startPosition")
}
