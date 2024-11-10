// DeductionRule.java
package rules;

import core.SudokuBoard;


public abstract class DeductionRule {
    protected boolean madeProgress;
    protected int applicationCount;
    
    protected DeductionRule() {
        this.madeProgress = false;
        this.applicationCount = 0;
    }

    public final void apply(SudokuBoard board) {
        executeRule(board);
    }

    protected abstract void executeRule(SudokuBoard board);

    public boolean hasMadeProgress() {
        return madeProgress;
    }
    
    public int getApplicationCount() {
        return applicationCount;
    }

    // Helper methods for all rules
    protected int[] getRowIndices(int index) {
        int row = index / 9;
        int[] indices = new int[9];
        for (int i = 0; i < 9; i++) {
            indices[i] = row * 9 + i;
        }
        return indices;
    }

    protected int[] getColumnIndices(int index) {
        int col = index % 9;
        int[] indices = new int[9];
        for (int i = 0; i < 9; i++) {
            indices[i] = i * 9 + col;
        }
        return indices;
    }

    protected int[] getBoxIndices(int index) {
        int row = index / 9;
        int col = index % 9;
        int boxStartRow = (row / 3) * 3;
        int boxStartCol = (col / 3) * 3;
        int[] indices = new int[9];
        int idx = 0;
        for (int r = boxStartRow; r < boxStartRow + 3; r++) {
            for (int c = boxStartCol; c < boxStartCol + 3; c++) {
                indices[idx++] = r * 9 + c;
            }
        }
        return indices;
    }
}