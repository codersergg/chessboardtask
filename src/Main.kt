class Chessboard(
    private val size: Int,
    private val startPosition: Position,
    private val figureSteps: List<Position>
) {
    private val board: Array<IntArray> = Array(size) { IntArray(size) { -1 } }

    init {
        board[startPosition.x][startPosition.y] = 1
    }

    fun findSolution(currentPosition: Position = startPosition, stepNumber: Int = 1): Boolean {
        if (stepNumber == size * size) {
            return true
        }

        for (move in figureSteps) {
            val nextX = currentPosition.x + move.x
            val nextY = currentPosition.y + move.y
            if (isValidPosition(nextX, nextY) && board[nextX][nextY] == -1) {
                board[nextX][nextY] = stepNumber + 1
                if (findSolution(Position(nextX, nextY), stepNumber + 1)) {
                    return true
                }
                board[nextX][nextY] = -1
            }
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

    private fun isValidPosition(x: Int, y: Int): Boolean = x in 0..<size && y in 0..<size
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

    if (chessboard.findSolution()) {
        chessboard.print()
    } else {
        println("No solution exists from position $startPosition")
    }
}
