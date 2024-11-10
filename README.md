# Sudoku Solver

## Overview
This project is a Sudoku Solver that aims to solve Sudoku puzzles programmatically. The solution employs various strategies and deduction rules to find solutions efficiently.

### Key Folders
- **src/**: Contains all the source code files written in Java.
  - `Main.java`: The entry point of the application.
  - `core/`, `rules/`, `solver/`, etc.: Contains different parts of the solver logic.
- **bin/**: Compiled `.class` files.
- **Report/**: Contains parts of the report written in LaTeX.

### Text Files
- **DR1_puzzle.txt**, **DR2_puzzle.txt**, **DR3_puzzle.txt**: These files contain Sudoku puzzles that are used as input for testing different solving strategies.

## Getting Started
To get started with the project, you need to compile the source code and then execute the `Main` class to run the solver.

### Prerequisites
- **Java JDK 8 or higher**
- **Build Tools**: You can use a tool like `javac` to compile the code or set up a Java IDE like IntelliJ IDEA or Eclipse.

### Compiling and Running
1. **Clone the repository**:
   ```sh
   git clone https://github.com/yisola2/SudokuSolver.git
   cd SudokuSolver
   ```

2. **Compile the source files**:
   ```sh
   javac -d bin src/Main.java src/core/*.java src/observer/*.java src/rules/*.java src/solver/*.java src/strategy/*.java src/util/*.java
   ```

3. **Run the Solver**:
   ```sh
   java -cp bin src/Main.java <input.txt> 
   ```

### Usage
- **Input**: Provide a Sudoku puzzle in one of the provided text files (e.g., `DR1_puzzle.txt`).
- **Output**: The solution to the puzzle will be printed to the console.

## Rules and Strategies
The project applies several deduction rules to solve puzzles:
- **DR1**: Simple elimination.
- **DR2**: Hidden single.
- **DR3**: Locked Candidates strategy.

## Report
The `Report` directory contains LaTeX files that document the project, including its design, approach, and the logic behind each deduction rule.

## Authors
- **Yassin Es Saim - M1 Informatique - 22109654**


