# FiffteenPuzzle Solver

This project is a solver for the **15 Puzzle**, where the goal is to rearrange the pieces on a grid to reach the goal configuration using graph exploration algorithms. The puzzle starts in a scrambled state, and the solver finds a sequence of moves to solve it.

## Problem Overview
The input is a file containing a puzzle in the same format as **Assignment 1**. The solver reads the puzzle, applies an algorithm to solve it, and writes the solution to an output file.

The algorithm can use **BFS**, **DFS**, or **A\*** search. For **A\***, different heuristics can be used to evaluate the distance from the current state to the solution.

## Algorithms Implemented
- **BFS (Breadth-First Search)**: Explores all possible moves level by level.
- **DFS (Depth-First Search)**: Explores as far as possible along a branch before backtracking.
- **A\***: Uses heuristics (e.g., Manhattan distance, misplaced tiles) to find the optimal solution.

### Heuristics for A\*:
- **Manhattan Distance**: Sum of the absolute differences between the current positions and the target positions of the tiles.
- **Misplaced Tiles**: The number of tiles that are not in their correct position.
