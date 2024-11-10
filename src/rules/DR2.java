package rules;

import core.SudokuBoard;
import core.CandidateManager;
import java.util.*;

public class DR2 extends DeductionRule {
    
    @Override
    protected void executeRule(SudokuBoard board) {
        madeProgress = false;
        applicationCount = 0;
        CandidateManager candidateManager = board.getCandidateManager();

        // Process all units in a single pass
        processAllUnits(board, candidateManager);

        if (applicationCount > 0) {
            System.out.printf("DR2: Found and filled %d hidden single(s)%n", applicationCount);
        }
    }

    private void processAllUnits(SudokuBoard board, CandidateManager candidateManager) {
        // Check all unit types in one pass to avoid multiple board scans
        for (int i = 0; i < 9; i++) {
            // Check row
            findHiddenSinglesInUnit(board, candidateManager, 
                getRowIndices(i * 9), "row " + (i + 1));
            
            // Check column
            findHiddenSinglesInUnit(board, candidateManager, 
                getColumnIndices(i), "column " + (i + 1));
            
            // Check box
            int boxRow = (i / 3) * 3;
            int boxCol = (i % 3) * 3;
            int startIndex = boxRow * 9 + boxCol;
            findHiddenSinglesInUnit(board, candidateManager, 
                getBoxIndices(startIndex), "box " + (i + 1));
        }
    }

    private void findHiddenSinglesInUnit(SudokuBoard board, CandidateManager candidateManager, 
                                       int[] unitIndices, String unitName) {
        // For each value 1-9
        for (int value = 1; value <= 9; value++) {
            // Skip if value already exists in this unit
            if (valueExistsInUnit(board, unitIndices, value)) {
                continue;
            }

            // Count occurrences of each candidate in the unit
            List<Integer> possiblePositions = new ArrayList<>();
            for (int index : unitIndices) {
                if (board.getValue(index) == -1 && 
                    candidateManager.hasCandidate(index, value)) {
                    possiblePositions.add(index);
                }
            }

            // If exactly one position is possible, we've found a hidden single
            if (possiblePositions.size() == 1) {
                int index = possiblePositions.get(0);
                if (board.getValue(index) == -1) {
                    // Store candidates before setting for logging
                    Set<Integer> previousCandidates = candidateManager.getCandidates(index);
                    
                    // Set the value
                    board.setValue(index, value);
                    madeProgress = true;
                    applicationCount++;
                    
                    // Detailed logging
                    System.out.printf("DR2: Found hidden single %d in %s at position [%d,%d]%n", 
                        value, unitName, index/9, index%9);
                    System.out.printf("Previous candidates at position [%d,%d]: %s%n", 
                        index/9, index%9, previousCandidates);
                }
            }
        }
    }

    private boolean valueExistsInUnit(SudokuBoard board, int[] unitIndices, int value) {
        for (int index : unitIndices) {
            if (board.getValue(index) == value) {
                return true;
            }
        }
        return false;
    }

    private void debugPrintUnitCandidates(CandidateManager candidateManager, 
                                        int[] unitIndices, String unitName) {
        System.out.println("Debug: Candidates in " + unitName + ":");
        for (int index : unitIndices) {
            if (candidateManager.getCandidatesCount(index) > 0) {
                System.out.printf("Position [%d,%d]: %s%n", 
                    index/9, index%9, candidateManager.getCandidates(index));
            }
        }
    }
}