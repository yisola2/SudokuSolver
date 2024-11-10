package rules;

import core.SudokuBoard;
import core.CandidateManager;
import java.util.Set;

public class DR1 extends DeductionRule {
    
    @Override
    protected void executeRule(SudokuBoard board) {
        madeProgress = false;
        applicationCount = 0;
        CandidateManager candidateManager = board.getCandidateManager();
        
        for (int index = 0; index < 81; index++) {
            if (board.getValue(index) == -1) {  // Empty cell
                Set<Integer> candidates = candidateManager.getCandidates(index);
                if (candidates.size() == 1) {  // Single candidate found
                    int value = candidates.iterator().next();
                    if (board.getValue(index) == -1) {  // Double check cell is still empty
                        board.setValue(index, value);
                        madeProgress = true;
                        applicationCount++;
                        System.out.printf("DR1: Set value %d at position [%d,%d]%n", 
                            value, index/9, index%9);
                    }
                }
            }
        }
        
        if (applicationCount > 0) {
            System.out.printf("DR1: Found and filled %d single candidate(s)%n", applicationCount);
        }
    }
}