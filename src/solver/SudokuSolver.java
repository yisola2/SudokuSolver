package solver;

import core.SudokuBoard;
import rules.DeductionRule;
import util.SudokuValidator;
import java.util.*;

public class SudokuSolver {
    private final List<DeductionRule> rules;
    private final Scanner scanner;
    private static final int MAX_ATTEMPTS = 3;

    public SudokuSolver() {
        this.rules = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void addRule(DeductionRule rule) {
        rules.add(rule);
    }

    public void solve(SudokuBoard board) {
        System.out.println("Initial board:");
        board.printBoard();

        boolean progress;
        int noProgressCount = 0;
        int totalMoves = 0;
        
        do {
            progress = false;
            int startingFilledCells = countFilledCells(board);
            
            // Try each rule in order (DR1 -> DR2 -> DR3)
            for (DeductionRule rule : rules) {
                System.out.println("\nApplying " + rule.getClass().getSimpleName() + "...");
                
                rule.apply(board);
                
                if (rule.hasMadeProgress()) {
                    progress = true;
                    totalMoves += rule.getApplicationCount();
                    System.out.println("\nAfter " + rule.getClass().getSimpleName() + ":");
                    board.printBoard();
                    break;  // Start over with DR1 when any progress is made
                }
            }

            int endingFilledCells = countFilledCells(board);
            if (endingFilledCells == startingFilledCells) {
                noProgressCount++;
                
                if (noProgressCount >= MAX_ATTEMPTS) {
                    if (!board.isFull()) {
                        System.out.println("\nNo deduction possible after " + MAX_ATTEMPTS + 
                                         " attempts. User input required.");
                        if (!getUserInput(board)) {
                            System.out.println("Invalid input or inconsistent state detected.");
                            System.out.println("Please restart solving from the beginning.");
                            return;
                        }
                        progress = true;
                        totalMoves++;
                        noProgressCount = 0;  // Reset counter after user input
                        System.out.println("\nAfter user input:");
                        board.printBoard();
                    }
                }
            } else {
                noProgressCount = 0;  // Reset counter when progress is made
            }

        } while ((progress || noProgressCount < MAX_ATTEMPTS) && !board.isFull());

        // Final results
        System.out.println("\nFinal board state:");
        board.printBoard();
        
        if (board.isFull()) {
            System.out.println("\nSudoku solved successfully!");
            System.out.println("Total moves made: " + totalMoves);
            if (!SudokuValidator.isValidConfiguration(board)) {
                System.out.println("WARNING: Solution may be invalid. Please verify.");
            }
        } else {
            System.out.println("\nUnable to solve Sudoku completely.");
            System.out.println("Cells filled: " + countFilledCells(board) + "/81");
        }
    }

    private boolean getUserInput(SudokuBoard board) {
        try {
            System.out.println("\nEnter your move:");
            System.out.print("Row (1-9): ");
            int row = scanner.nextInt() - 1;
            System.out.print("Column (1-9): ");
            int col = scanner.nextInt() - 1;
            System.out.print("Value (1-9): ");
            int value = scanner.nextInt();

            if (!isValidInput(row, col, value)) {
                System.out.println("Invalid input: values must be between 1 and 9");
                return false;
            }

            int index = row * 9 + col;
            if (SudokuValidator.isValidMove(board, index, value)) {
                board.setValue(index, value);
                return true;
            } else {
                System.out.println("Invalid move: violates Sudoku rules");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Invalid input format. Please enter numbers only.");
            scanner.nextLine(); // Clear scanner buffer
            return false;
        }
    }

    private boolean isValidInput(int row, int col, int value) {
        return row >= 0 && row < 9 && 
               col >= 0 && col < 9 && 
               value > 0 && value <= 9;
    }

    private int countFilledCells(SudokuBoard board) {
        int count = 0;
        for (int i = 0; i < 81; i++) {
            if (board.getValue(i) != -1) {
                count++;
            }
        }
        return count;
    }

    // For difficulty evaluation
    public boolean solveForEvaluation(SudokuBoard board) {
        boolean progress;
        do {
            progress = false;
            for (DeductionRule rule : rules) {
                rule.apply(board);
                if (rule.hasMadeProgress()) {
                    progress = true;
                    break;  // Start over with DR1 when progress is made
                }
            }
        } while (progress && !board.isFull());
        
        return board.isFull();
    }
}