/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pazelproject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class PuzzleController {
    private final PuzzleGUI gui;
    private final PuzzleSolver solver;
    private final ExecutorService executor;

    public PuzzleController() {
        gui = new PuzzleGUI(this);
        solver = new PuzzleSolver();
        executor = Executors.newFixedThreadPool(7);
    }

    public void solvePuzzle(int[] pieceCounts) {
    List<int[][]> pieces = generatePieces(pieceCounts);
    JPanel mainPanel = gui.getMainPanel();
    mainPanel.removeAll();


    List<JPanel> boardPanels = new ArrayList<>();
    List<JLabel> solutionStatusLabels = new ArrayList<>();
    
    // Identify distinct piece types to process
    List<Integer> distinctPieceTypes = new ArrayList<>();
    for (int i = 0; i < pieceCounts.length; i++) {
        if (pieceCounts[i] > 0) {
            distinctPieceTypes.add(i); // Store the index of selected piece types
        }
    }

    // If there are fewer than 4 pieces, display the "No Solution" message for each thread
    if (pieces.size() < 4) {
        // Display message for insufficient pieces
        for (int pieceTypeIndex : distinctPieceTypes) {
        final int threadIndex = pieceTypeIndex;

            JPanel boardPanel = new JPanel(new GridLayout(4, 4));
            boardPanel.setBackground(new Color(255, 228, 225)); // Misty rose

            JLabel statusLabel = new JLabel("No Solution", JLabel.CENTER);
            statusLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
            statusLabel.setForeground(Color.WHITE);
            statusLabel.setBackground(new Color(255, 69, 0)); // Red for no solution
            statusLabel.setOpaque(true);

            JPanel threadPanel = new JPanel(new BorderLayout());
            threadPanel.add(new JLabel("Thread " + (threadIndex + 1), JLabel.CENTER), BorderLayout.NORTH);
            threadPanel.add(boardPanel, BorderLayout.CENTER);
            threadPanel.add(statusLabel, BorderLayout.SOUTH);

            // Update the UI with the "No Solution" message
            SwingUtilities.invokeLater(() -> {
                mainPanel.add(threadPanel);
                gui.getFrame().revalidate();
                gui.getFrame().repaint();
            });

            // Submit a task for each thread to display the "No Solution" message
            executor.submit(() -> {
                int[][] board = new int[4][4];
                for (int[] row : board) {
                    java.util.Arrays.fill(row, -1); // Fill board with -1 indicating empty cells
                }
                fillBoard(board, pieces, boardPanel); // You may choose to keep this method or modify it to show something for "No Solution"
            });
        }
        return; // Return early since there's no solution
    }

    

    
    int i = 1;
    // For each distinct piece type, create a new board panel
    for (int pieceTypeIndex : distinctPieceTypes) {
        final int threadIndex = pieceTypeIndex;
        // Create a new JPanel for each thread's board
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(4, 4));
        boardPanel.setBackground(new Color(255, 228, 225)); // Misty rose
        JLabel statusLabel = new JLabel("Not Found", JLabel.CENTER);
        statusLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBackground(new Color(255, 105, 180)); // Hot pink
        statusLabel.setOpaque(true);

        // Add thread number label above the board
        JLabel threadNumberLabel = new JLabel("Thread " + (i), JLabel.CENTER);
        threadNumberLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        threadNumberLabel.setForeground(Color.BLACK);

        // Create a JPanel to wrap the board and the status label
        JPanel threadPanel = new JPanel();
        threadPanel.setLayout(new BorderLayout());
        threadPanel.add(threadNumberLabel, BorderLayout.NORTH); // Place thread label at the top
        threadPanel.add(boardPanel, BorderLayout.CENTER);
        threadPanel.add(statusLabel, BorderLayout.SOUTH); // Place status label at the bottom

        // Update the main panel in the Swing Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            mainPanel.add(threadPanel);
            boardPanels.add(boardPanel);
            solutionStatusLabels.add(statusLabel);
            gui.getFrame().revalidate();
            gui.getFrame().repaint();
        });

        // Submit a task for each piece type to a fixed thread pool
        executor.submit(() -> {
            int[][] board = new int[4][4];
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    board[row][col] = -1; // Initialize empty cells
                }
            }

            // Solve for the specific piece type
            boolean solvedForThisThread = solver.solveForThread(board, pieces, threadIndex, statusLabel, boardPanel);

            // Update the status label after solving the puzzle
            SwingUtilities.invokeLater(() -> {
                if (solvedForThisThread) {
                    statusLabel.setText("Solved");
                    statusLabel.setBackground(new Color(34, 139, 34)); // Green color for solved
                } else {
                    statusLabel.setText("Not Found");
                    statusLabel.setBackground(new Color(255, 69, 0)); // Red color for not found
                }
            });
        });
        i++;
    }
}

    private List<int[][]> generatePieces(int[] pieceCounts) {
        List<int[][]> pieces = new ArrayList<>();
        for (int i = 0; i < pieceCounts[0]; i++) pieces.add(new int[][]{{1, 1, 1}, {1, 0, 0}}); // L-shape
        for (int i = 0; i < pieceCounts[1]; i++) pieces.add(new int[][]{{1, 1, 0}, {0, 1, 1}}); // Z-shape
        for (int i = 0; i < pieceCounts[2]; i++) pieces.add(new int[][]{{1}, {1}, {1}, {1}}); // I-shape
        for (int i = 0; i < pieceCounts[3]; i++) pieces.add(new int[][]{{1, 1, 1}, {0, 0, 1}}); // J-shape
        for (int i = 0; i < pieceCounts[4]; i++) pieces.add(new int[][]{{1, 1, 1}, {0, 1, 0}}); // T-shape
        for (int i = 0; i < pieceCounts[5]; i++) pieces.add(new int[][]{{0, 1, 1}, {1, 1, 0}}); // S-shape
        for (int i = 0; i < pieceCounts[6]; i++) pieces.add(new int[][]{{1, 1}, {1, 1}}); // O-shape
        return pieces;
    }
    
    private void fillBoard(int[][] board, List<int[][]> pieces, JPanel boardPanel) {
        PuzzleSolver tempSolver = new PuzzleSolver();
        tempSolver.solveForThread(board, pieces, 0, new JLabel(), boardPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PuzzleController(); // Start the application
        });
    }
}

