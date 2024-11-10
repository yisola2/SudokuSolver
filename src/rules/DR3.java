package rules;

import core.SudokuBoard;
import core.CandidateManager;
import java.util.*;

public class DR3 extends DeductionRule {
    
    @Override
    protected void executeRule(SudokuBoard board) {
        madeProgress = false;
        applicationCount = 0;
        CandidateManager candidateManager = board.getCandidateManager();
        
        // First pass: Box to Line reduction
        for (int boxIndex = 0; boxIndex < 9; boxIndex++) {
            checkBoxToLine(board, candidateManager, boxIndex);
        }
        
        // Second pass: Line to Box reduction
        for (int lineIndex = 0; lineIndex < 9; lineIndex++) {
            checkLineToBox(board, candidateManager, lineIndex, true);  // Check rows
            checkLineToBox(board, candidateManager, lineIndex, false); // Check columns
        }
    }

    private void checkBoxToLine(SudokuBoard board, CandidateManager candidateManager, int boxIndex) {
        int boxRow = (boxIndex / 3) * 3;
        int boxCol = (boxIndex % 3) * 3;
        
        for (int num = 1; num <= 9; num++) {
            Set<Integer> rows = new HashSet<>();
            Set<Integer> cols = new HashSet<>();
            
            // Find all positions where this candidate appears in the box
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    int index = (boxRow + r) * 9 + (boxCol + c);
                    if (board.getValue(index) == -1 && candidateManager.hasCandidate(index, num)) {
                        rows.add(boxRow + r);
                        cols.add(boxCol + c);
                    }
                }
            }
            
            // If confined to one row, remove from rest of row
            if (rows.size() == 1) {
                int row = rows.iterator().next();
                eliminateFromRestOfRow(board, candidateManager, row, boxCol, boxCol + 2, num);
            }
            
            // If confined to one column, remove from rest of column
            if (cols.size() == 1) {
                int col = cols.iterator().next();
                eliminateFromRestOfColumn(board, candidateManager, col, boxRow, boxRow + 2, num);
            }
        }
    }

    private void checkLineToBox(SudokuBoard board, CandidateManager candidateManager, int lineIndex, boolean isRow) {
        for (int num = 1; num <= 9; num++) {
            Set<Integer> boxes = new HashSet<>();
            
            // Find all boxes where this candidate appears in the line
            for (int i = 0; i < 9; i++) {
                int index = isRow ? (lineIndex * 9 + i) : (i * 9 + lineIndex);
                if (board.getValue(index) == -1 && candidateManager.hasCandidate(index, num)) {
                    boxes.add(i / 3);
                }
            }
            
            // If confined to one box, remove from rest of box
            if (boxes.size() == 1) {
                int box = boxes.iterator().next();
                int boxStartRow = isRow ? lineIndex / 3 * 3 : box * 3;
                int boxStartCol = isRow ? box * 3 : lineIndex / 3 * 3;
                eliminateFromRestOfBox(board, candidateManager, boxStartRow, boxStartCol, lineIndex, num, isRow);
            }
        }
    }

    private void eliminateFromRestOfRow(SudokuBoard board, CandidateManager candidateManager, int row, int boxStartCol, int boxEndCol, int num) {
        for (int col = 0; col < 9; col++) {
            if (col < boxStartCol || col > boxEndCol) {
                int index = row * 9 + col;
                if (board.getValue(index) == -1 && candidateManager.hasCandidate(index, num)) {
                    candidateManager.removeCandidate(index, num);
                    madeProgress = true;
                    applicationCount++;
                }
            }
        }
    }

    private void eliminateFromRestOfColumn(SudokuBoard board, CandidateManager candidateManager, int col, int boxStartRow, int boxEndRow, int num) {
        for (int row = 0; row < 9; row++) {
            if (row < boxStartRow || row > boxEndRow) {
                int index = row * 9 + col;
                if (board.getValue(index) == -1 && candidateManager.hasCandidate(index, num)) {
                    candidateManager.removeCandidate(index, num);
                    madeProgress = true;
                    applicationCount++;
                }
            }
        }
    }

    private void eliminateFromRestOfBox(SudokuBoard board, CandidateManager candidateManager, int boxStartRow, int boxStartCol, int lineIndex, int num, boolean isRow) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int row = boxStartRow + r;
                int col = boxStartCol + c;
                if (isRow ? row != lineIndex : col != lineIndex) {
                    int index = row * 9 + col;
                    if (board.getValue(index) == -1 && candidateManager.hasCandidate(index, num)) {
                        candidateManager.removeCandidate(index, num);
                        madeProgress = true;
                        applicationCount++;
                    }
                }
            }
        }
    }
}