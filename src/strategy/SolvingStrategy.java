package strategy;

import core.SudokuBoard;

public interface SolvingStrategy {
    void apply(SudokuBoard board);
    boolean hasMadeProgress();
    String getRuleName();
}