import core.SudokuBoard;
import rules.RuleFactory;
import solver.SudokuSolver;
//import util.DifficultyEvaluator;
import util.FileHandler;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -cp bin src/Main.java <input_file>");
            System.out.println("Example: java -cp bin src/Main.java DR1_puzzle.txt");
            return;
        }

        try {
            // Initialize the board
            SudokuBoard board = SudokuBoard.getInstance();

            // Evaluate difficulty
            /**System.out.println("\nEvaluating puzzle difficulty...");
            try {
                DifficultyEvaluator.Difficulty difficulty = DifficultyEvaluator.evaluatePuzzle(board);
                System.out.println("Puzzle difficulty: " + difficulty);
            } catch (Exception e) {
                System.out.println("Error evaluating difficulty:");
                e.printStackTrace();
                // Continue with solving anyway
            }**/
            
            // Get input file from command line argument
            String inputFile = args[0];
            File file = new File(inputFile);
            
            if (!file.exists()) {
                System.out.println("Error: Input file '" + inputFile + "' not found");
                return;
            }
            
            // Load the puzzle
            System.out.println("Loading puzzle from: " + file.getAbsolutePath());
            FileHandler.loadFromFile(board, inputFile);
            

            System.out.println("\nInitial board:");
            board.printBoard();

            // Initialize solver with rules
            SudokuSolver solver = new SudokuSolver();
            RuleFactory ruleFactory = RuleFactory.getInstance();
            
            solver.addRule(ruleFactory.createRule("DR1"));
            solver.addRule(ruleFactory.createRule("DR2"));
            solver.addRule(ruleFactory.createRule("DR3"));

            // Solve the puzzle
            solver.solve(board);

            // Create output file name based on input file
            String outputFile = file.getName().replaceFirst("[.][^.]+$", "") + "_solution.txt";
            
            // Save the result if solved
            if (board.isFull()) {
                FileHandler.saveToFile(board, outputFile);
                System.out.println("\nSolution saved to: " + new File(outputFile).getAbsolutePath());
            }
        } catch (Exception e) {
            System.out.println("Error occurred: ");
            e.printStackTrace();
        }
    }
}