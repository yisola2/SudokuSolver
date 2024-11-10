package util;

import core.SudokuBoard;
import java.io.*;
import java.util.Scanner;

public class FileHandler {
    public static void loadFromFile(SudokuBoard board, String filename) throws IOException {
        try (Scanner scanner = new Scanner(new File(filename))) {
            int index = 0;
            
            for (int i = 0; i < 9; i++) {
                if (!scanner.hasNextLine()) {
                    throw new IOException("File format error: insufficient lines");
                }
                String[] values = scanner.nextLine().split(",");
                if (values.length != 9) {
                    throw new IOException("File format error: line " + (i + 1));
                }
                for (int j = 0; j < 9; j++) {
                    try {
                        int value = Integer.parseInt(values[j].trim());
                        // Convert from file format (0) to internal format (-1)
                        board.setValue(index++, value == 0 ? -1 : value);
                    } catch (NumberFormatException e) {
                        throw new IOException("File format error at line " + (i + 1));
                    }
                }
            }
        }
    }

    public static void saveToFile(SudokuBoard board, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            int[] grid = board.getGridValues();
            for (int i = 0; i < 9; i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < 9; j++) {
                    if (j > 0) line.append(",");
                    int value = grid[i * 9 + j];
                    // Convert from internal format (-1) to file format (0)
                    line.append(value == -1 ? "0" : value);
                }
                writer.println(line);
            }
        }
    }
}