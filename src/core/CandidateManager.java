package core;

import java.util.*;

public class CandidateManager {
    private final SudokuBoard board;
    private final Map<Integer, Set<Integer>> candidateCache;

    public CandidateManager(SudokuBoard board) {
        this.board = board;
        this.candidateCache = new HashMap<>();
        initializeCandidates();
    }

    private void initializeCandidates() {
        for (int i = 0; i < 81; i++) {
            candidateCache.put(i, new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
        }
        
        for (int i = 0; i < 81; i++) {
            int value = board.getValue(i);
            if (value != -1) {
                updateCandidates(i, value);
            }
        }
    }

    public void updateCandidates(int index, int value) {
        if (value == -1) return;
    
        Set<Integer> affectedIndices = new HashSet<>();
        int row = index / 9;
        int col = index % 9;
    
        // Add row indices
        for (int c = 0; c < 9; c++) {
            affectedIndices.add(row * 9 + c);
        }
    
        // Add column indices
        for (int r = 0; r < 9; r++) {
            affectedIndices.add(r * 9 + col);
        }
    
        // Add box indices
        int boxStartRow = (row / 3) * 3;
        int boxStartCol = (col / 3) * 3;
        for (int r = boxStartRow; r < boxStartRow + 3; r++) {
            for (int c = boxStartCol; c < boxStartCol + 3; c++) {
                affectedIndices.add(r * 9 + c);
            }
        }
    
        for (int idx : affectedIndices) {
            if (board.getValue(idx) == -1) {
                removeCandidate(idx, value);
            }
        }
    }

    public void removeCandidate(int index, int value) {
        Set<Integer> candidates = candidateCache.get(index);
        if (candidates != null && board.getValue(index) == -1) {
            Set<Integer> newCandidates = new HashSet<>(candidates);
            newCandidates.remove(value);
            candidateCache.put(index, newCandidates);
        }
    }

    public Set<Integer> getCandidates(int index) {
        return new HashSet<>(candidateCache.getOrDefault(index, new HashSet<>()));
    }

    public boolean hasCandidate(int index, int value) {
        Set<Integer> candidates = candidateCache.get(index);
        return candidates != null && candidates.contains(value);
    }

    public boolean isValidCandidate(int index, int value) {
        return hasCandidate(index, value);
    }

    public int getCandidatesCount(int index) {
        return getCandidates(index).size();
    }

    public boolean hasSingleCandidate(int index) {
        return getCandidatesCount(index) == 1;
    }

    public int getSingleCandidate(int index) {
        Set<Integer> candidates = getCandidates(index);
        if (candidates.size() != 1) {
            throw new IllegalStateException("Cell does not have a single candidate");
        }
        return candidates.iterator().next();
    }
}