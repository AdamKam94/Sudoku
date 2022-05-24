package sudoku.computationlogic;

import sudoku.constants.GameState;
import sudoku.constants.Rows;
import sudoku.problemdomain.SudokuGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;


/**
 * Q: Why isn't this a class hidden behind an interface?
 * A: It requires no external libraries, nor do I ever plan to switch to using external libraries.
 */
public class GameLogic {

    public static SudokuGame getNewGame() {
        return new SudokuGame(
                GameState.NEW,
                GameGenerator.getNewGameGrid()
        );
    }

    /**
     * Check to see if the incoming state (what the values of each square happen to be) of game is either Active
     * (i.e. Unsolved) or Complete (i.e. Solved).
     *
     * @param grid A virtual representation of a sudoku puzzle, which may or not may be solved.
     * @return Either GameState.Active or GameState.Complete, based on analysis of solvedSudoku.
     *
     * Rules:
     * - A number may not be repeated among Rows, e.g.:
     * - [0, 0] == [0-8, 1] not allowed
     * - [0, 0] == [3, 4] allowed
     * - A number may not be repeated among Columns, e.g.:
     * - [0-8, 1] == [0, 0] not allowed
     * - [0, 0] == [3, 4] allowed
     * - A number may not be repeated within respective GRID_BOUNDARY x GRID_BOUNDARY regions within the sudoku puzzle
     * - [0, 0] == 1, 2] not allowed
     *  - [0, 0] == [3, 4] = allowed
     */

    public static GameState checkForCompletion(int[][] grid) {
        if (sudokuIsInvalid(grid)) return GameState.ACTIVE;
        if (tilesAreNotFilled(grid)) return GameState.ACTIVE;
        return GameState.COMPLETE;
    }

    /**
     * Traverse all t
     * @param grid
     * @return
     */
    public static boolean tilesAreNotFilled(int[][] grid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                if (grid[xIndex][yIndex] == 0) return true;
            }
        }
        return false;
    }

    public static boolean sudokuIsInvalid(int[][] grid) {
        if (rowsAreInvalid(grid)) return true;
        if (columnsAreInvalid(grid)) return true;
        if (squaresAreInvalid(grid)) return true;
        else return false;
    }

    private static boolean rowsAreInvalid(int[][] grid) {
        for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++){
            List<Integer> row = new ArrayList<>();
            for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
                row.add(grid[xIndex][yIndex]);
            }
            if (collectionHasRepeats(row)) return true;
        }

        return false;
    }

    private static boolean columnsAreInvalid(int[][] grid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++){
            List<Integer> row = new ArrayList<>();
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++) {
                row.add(grid[xIndex][yIndex]);
            }
            if (collectionHasRepeats(row)) return true;
        }

        return false;
    }

    private static boolean squaresAreInvalid(int[][] grid) {
        if (rowOfSquareIsInvalid(Rows.TOP, grid)) return true;

        if (rowOfSquareIsInvalid(Rows.MIDDLE, grid)) return true;

        if (rowOfSquareIsInvalid(Rows.BOTTOM, grid)) return true;

        return false;
    }

    private static boolean rowOfSquareIsInvalid(Rows value, int[][] grid) {
        switch (value) {
            case TOP:
                if (squareIsInvalid(0, 0, grid)) return true;
                if (squareIsInvalid(0, 3, grid)) return true;
                if (squareIsInvalid(0, 6, grid)) return true;
                return false;
            case MIDDLE:
                if (squareIsInvalid(3, 0, grid)) return true;
                if (squareIsInvalid(3, 3, grid)) return true;
                if (squareIsInvalid(3, 6, grid)) return true;
                return false;
            case BOTTOM:
                if (squareIsInvalid(6, 0, grid)) return true;
                if (squareIsInvalid(6, 3, grid)) return true;
                if (squareIsInvalid(6, 6, grid)) return true;
                return false;
            default:
                return false;

        }
    }

    private static boolean squareIsInvalid(int xIndex, int yIndex, int[][] grid) {
        int yIndexEnd = yIndex +3;
        int xIndexEnd = xIndex +3;

        List<Integer> square = new ArrayList<>();

        while (yIndex < yIndexEnd) {
            while (xIndex < xIndexEnd) {
                square.add(
                        grid[xIndex][yIndex]
                );

                xIndex++;
            }

            xIndex -= 3;

            yIndex++;
        }
        if (collectionHasRepeats(square)) return true;
        return false;
    }

    public static boolean collectionHasRepeats(List<Integer> collection) {
        for (int index = 1; index <= GRID_BOUNDARY; index++) {
            if (Collections.frequency(collection, index) > 1) return true;
        }

        return false;

    }


}
