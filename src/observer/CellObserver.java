package observer;

import core.Cell;

public interface CellObserver {
    /**
     * Called when a cell's value or candidates have changed
     * @param cell The cell that was changed
     */
    void update(Cell cell);
}