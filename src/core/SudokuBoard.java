package core;


public class SudokuBoard {
    private static SudokuBoard instance;
    private final Cell[] cells;
    private final CandidateManager candidateManager;

    public SudokuBoard() {
        this.cells = new Cell[81];
        // Initialize cells
        for (int i = 0; i < 81; i++) {
            cells[i] = new Cell(i);
        }
        this.candidateManager = new CandidateManager(this);
    }

    public Cell getCell(int index) {
        validateIndex(index);
        return cells[index];
    }

    public Cell getCell(int row, int col) {
        validatePosition(row, col);
        return cells[row * 9 + col];
    }

    public void setInitialValue(int row, int col, int value) {
        validatePosition(row, col);
        validateValue(value);
        int index = row * 9 + col;
        cells[index].setValue(value);
        if (value != -1) {
            candidateManager.updateCandidates(index, value);
        }
    }

    public void setValue(int index, int value) {
        validateIndex(index);
        validateValue(value);
        cells[index].setValue(value);
        if (value != -1) {
            candidateManager.updateCandidates(index, value);
        }
    }

    public int getValue(int index) {
        validateIndex(index);
        return cells[index].getValue();
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= 81) {
            throw new IllegalArgumentException("Index must be between 0 and 80");
        }
    }

    private void validatePosition(int row, int col) {
        if (row < 0 || row >= 9 || col < 0 || col >= 9) {
            throw new IllegalArgumentException("Invalid position: row and column must be between 0 and 8");
        }
    }

    private void validateValue(int value) {
        if (value < -1 || value > 9) {
            throw new IllegalArgumentException("Value must be between -1 and 9");
        }
    }

    public boolean isFull() {
        for (Cell cell : cells) {
            if (cell.getValue() == -1) {
                return false;
            }
        }
        return true;
    }

    public CandidateManager getCandidateManager() {
        return candidateManager;
    }

    public void printBoard() {
        System.out.println("┌───────┬───────┬───────┐");
        for (int i = 0; i < 9; i++) {
            System.out.print("│ ");
            for (int j = 0; j < 9; j++) {
                int value = cells[i * 9 + j].getValue();
                System.out.print(value == -1 ? "." : value);
                if (j % 3 == 2) {
                    System.out.print(" │ ");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
            if (i == 2 || i == 5) {
                System.out.println("├───────┼───────┼───────┤");
            }
        }
        System.out.println("└───────┴───────┴───────┘");
    }

    // Helper method for rules
    public int[] getGridValues() {
        int[] values = new int[81];
        for (int i = 0; i < 81; i++) {
            values[i] = cells[i].getValue();
        }
        return values;
    }
    public static SudokuBoard getInstance() {
        if (instance == null) {
            instance = new SudokuBoard();
        }
        return instance;
    }
}
