# Puzzle Solver - Java Implementation

This project provides a graphical interface for solving a puzzle game using a backtracking algorithm. The puzzle board is a 4x4 grid where pieces are placed, rotated, and arranged to solve the puzzle.

## Features

- **Backtracking Algorithm**: Efficiently searches for a solution by recursively placing and removing pieces.
- **Graphical Display**: Uses `Swing` to visualize the puzzle board in real-time as pieces are placed.
- **Piece Rotation**: Supports rotating pieces in 90-degree increments for flexible placement.
- **Colorful Pieces**: Each piece is assigned a unique color for better visualization.

## Key Components

### Classes and Methods
- **`solveForThread`**: Shuffles the pieces and initiates the backtracking process for a thread.
- **`backtrack`**: Recursively attempts to place pieces on the board.
- **`displayBoard`**: Updates the graphical representation of the board in a `JPanel`.
- **`canPlace`**: Checks if a piece can be placed at a specific position.
- **`placePiece`**: Places a piece on the board.
- **`removePiece`**: Removes a piece from the board.
- **`rotatePiece`**: Rotates a piece a specified number of times.
- **`rotateOnce`**: Rotates a piece by 90 degrees.

### Constants
- `BOARD_SIZE`: Specifies the size of the puzzle board (4x4 grid).
- `DELAY`: Controls the delay (in milliseconds) between graphical updates for better visualization.
- `pieceColors`: Array of predefined colors for the puzzle pieces.

## How It Works

1. **Initialization**: The board is initialized as a 4x4 grid, with all cells set to `-1` to indicate empty spaces.
2. **Piece Placement**: Pieces are iteratively placed on the board. If a valid placement cannot be found, the algorithm backtracks by removing the last placed piece and trying a new configuration.
3. **Graphical Updates**: Each step of the algorithm updates the graphical display to show the current state of the board.
4. **Piece Rotation**: Pieces can be rotated to fit into the board. This ensures all configurations are explored.

## Requirements

- **Java Development Kit (JDK)**: Version 8 or higher.
- **Swing**: For graphical user interface components.

## Example Usage

1. Initialize the board and pieces.
2. Call the `solveForThread` method to start solving the puzzle.
3. Visualize the solution process in real-time.

```java
int[][] board = new int[4][4];
for (int[] row : board) {
    Arrays.fill(row, -1);
}

List<int[][]> pieces = new ArrayList<>();
// Add pieces to the list

PuzzleSolver solver = new PuzzleSolver();
JLabel statusLabel = new JLabel("Solving...");
JPanel boardPanel = new JPanel(new GridLayout(4, 4));

boolean solved = solver.solveForThread(board, pieces, 0, statusLabel, boardPanel);
if (solved) {
    statusLabel.setText("Solved!");
} else {
    statusLabel.setText("No solution found.");
}
```

## Customization

- Modify `BOARD_SIZE` to change the grid size.
- Adjust `DELAY` to control the visualization speed.
- Add or modify `pieceColors` for additional customization.


