// Cell.java
package core;

import observer.*;
import java.util.*;


public class Cell {
    private final int index;
    private int value;
    private final Set<Integer> candidates;
    private final List<CellObserver> observers;

    public Cell(int index) {
        if (index < 0 || index >= 81) {
            throw new IllegalArgumentException("Index must be between 0 and 80");
        }
        this.index = index;
        this.value = -1;  // Empty cell
        this.candidates = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        this.observers = new ArrayList<>();
    }

    public void addObserver(CellObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(CellObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (CellObserver observer : observers) {
            observer.update(this);
        }
    }

    public int getIndex() {
        return index;
    }

    public int getRow() {
        return index / 9;
    }

    public int getCol() {
        return index % 9;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value < -1 || value > 9) {
            throw new IllegalArgumentException("Value must be between -1 and 9");
        }
        this.value = value;
        if (value != -1) {
            candidates.clear();
        }
        notifyObservers();
    }

    public boolean isEmpty() {
        return value == -1;
    }

    public Set<Integer> getCandidates() {
        return Collections.unmodifiableSet(candidates);
    }

    public boolean removeCandidate(int candidate) {
        boolean removed = candidates.remove(candidate);
        if (removed) {
            notifyObservers();
        }
        return removed;
    }

    public boolean hasSingleCandidate() {
        return value == -1 && candidates.size() == 1;
    }

    public int getSingleCandidate() {
        if (!hasSingleCandidate()) {
            throw new IllegalStateException("Cell does not have a single candidate");
        }
        return candidates.iterator().next();
    }

    @Override
    public String toString() {
        return String.format("Cell[index=%d, row=%d, col=%d, value=%d, candidates=%s]",
                index, getRow(), getCol(), value, candidates);
    }
}