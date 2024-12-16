/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pazelproject;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author nada1
 */
public class PuzzleGUI {
    private final JFrame frame;
    private final JPanel mainPanel;
    private final JTextField[] pieceInputs;
    private final JButton solveButton;

    public PuzzleGUI(PuzzleController controller) {
        frame = new JFrame("💖 Girly Puzzle Solver 💖");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.PINK);

        JPanel leftPanel = new JPanel(new GridLayout(8, 2));
        leftPanel.setBackground(new Color(255, 182, 193));

        String[] pieceLabels = {"L", "Z", "I", "J", "T", "S", "O"};
        pieceInputs = new JTextField[pieceLabels.length];
        for (int i = 0; i < pieceLabels.length; i++) {
            JLabel label = new JLabel(pieceLabels[i], JLabel.CENTER);
            label.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
            label.setForeground(Color.WHITE);
            leftPanel.add(label);

            pieceInputs[i] = new JTextField("0");
            pieceInputs[i].setHorizontalAlignment(JTextField.CENTER);
            pieceInputs[i].setBackground(Color.WHITE);
            pieceInputs[i].setForeground(Color.PINK.darker());
            pieceInputs[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
            leftPanel.add(pieceInputs[i]);
        }

        solveButton = new JButton("💖 Solve 💖");
        solveButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        solveButton.setBackground(new Color(255, 105, 180));
        solveButton.setForeground(Color.WHITE);
        solveButton.addActionListener(e -> controller.solvePuzzle(getPieceCounts()));
        leftPanel.add(new JLabel());
        leftPanel.add(solveButton);
        frame.add(leftPanel, BorderLayout.WEST);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 3));
        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JFrame getFrame() {
        return frame;
    }

    public int[] getPieceCounts() {
        int[] pieceCounts = new int[pieceInputs.length];
        for (int i = 0; i < pieceInputs.length; i++) {
            try {
                pieceCounts[i] = Integer.parseInt(pieceInputs[i].getText().trim());
            } catch (NumberFormatException ex) {
                pieceCounts[i] = 0;
            }
        }
        return pieceCounts;
    }
    
}
