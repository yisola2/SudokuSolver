package util;

import core.SudokuBoard;

public class SudokuValidator {
    public static boolean isValidMove(SudokuBoard board, int index, int value) {
        if (value < 1 || value > 9) return false;
        
        int row = index / 9;
        int col = index % 9;
        
        // Check row
        for (int c = 0; c < 9; c++) {
            if (board.getValue(row * 9 + c) == value) {
                return false;
            }
        }

        // Check column
        for (int r = 0; r < 9; r++) {
            if (board.getValue(r * 9 + col) == value) {
                return false;
            }
        }

        // Check 3x3 box
        int boxStartRow = (row / 3) * 3;
        int boxStartCol = (col / 3) * 3;
        for (int r = boxStartRow; r < boxStartRow + 3; r++) {
            for (int c = boxStartCol; c < boxStartCol + 3; c++) {
                if (board.getValue(r * 9 + c) == value) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isValidConfiguration(SudokuBoard board) {
        // Check rows
        for (int row = 0; row < 9; row++) {
            if (!isValidUnit(board, row * 9, 1)) return false;
        }

        // Check columns
        for (int col = 0; col < 9; col++) {
            int[] columnIndices = new int[9];
            for (int i = 0; i < 9; i++) {
                columnIndices[i] = i * 9 + col;
            }
            if (!isValidUnit(board, columnIndices)) return false;
        }

        // Check boxes
        for (int box = 0; box < 9; box++) {
            int startRow = (box / 3) * 3;
            int startCol = (box % 3) * 3;
            int[] boxIndices = new int[9];
            int idx = 0;
            for (int r = startRow; r < startRow + 3; r++) {
                for (int c = startCol; c < startCol + 3; c++) {
                    boxIndices[idx++] = r * 9 + c;
                }
            }
            if (!isValidUnit(board, boxIndices)) return false;
        }

        return true;
    }

    private static boolean isValidUnit(SudokuBoard board, int startIndex, int step) {
        boolean[] seen = new boolean[10];
        for (int i = 0; i < 9; i++) {
            int value = board.getValue(startIndex + i * step);
            if (value != -1) {
                if (seen[value]) return false;
                seen[value] = true;
            }
        }
        return true;
    }

    private static boolean isValidUnit(SudokuBoard board, int[] indices) {
        boolean[] seen = new boolean[10];
        for (int index : indices) {
            int value = board.getValue(index);
            if (value != -1) {
                if (seen[value]) return false;
                seen[value] = true;
            }
        }
        return true;
    }
}