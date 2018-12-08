package edu.cmu.cs.cs214.hw3.StrategyPattern;

import edu.cmu.cs.cs214.hw3.Grid;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A specific strategy of verifier.
 * The implement of sudoku verifier that verifier whether the solution is correct.
 */
public class SudokuVerifier implements Verifier{
    private Grid problemGrid;
    private Grid solutionGrid;
    private static final int LENGTH = 9;
    private boolean isValidInput;
    private int[][] problem;
    private int[][] solution;
    private static final int AREASTEP = 3;

    /**
     * Constructor of verifier.
     * Takes in two grid file. One is a sudoku problem, the other is solution to it.
     * @param problemFile file contains problem sudoku
     * @param solutionFile file contains solution sudoku
     */
    public SudokuVerifier(String problemFile, String solutionFile) {
        this.problemGrid = new Grid(problemFile);
        this.solutionGrid = new Grid(solutionFile);
        isValidInput = problemGrid.getValid() && solutionGrid.getValid();
        problem = problemGrid.getMatrix();
        solution = solutionGrid.getMatrix();
    }

    /**
     * Get LENGTH
     * @return LENGTH
     */
    public int getLength() {
        return LENGTH;
    }

    /**
     * Get LENGTH
     * @return LENGTH
     */
    public int getAreastep() {
        return AREASTEP;
    }

    /**
     * Do some normal check about a solution including isPair check, row check, valid check and area check.
     * @return True if all check pass.
     */
    public boolean normalCheck() {
        return isValidInput && isPair() && rowCheck() && columnCheck() && areaCheck();
    }

    private boolean specialCheck() {
        return true;
    }

    private boolean isPair() {
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < LENGTH; j++) {
                if (problem[i][j] != 0 && problem[i][j] != solution[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean rowCheck() {
        for (int[] row: solution) {
            Set<Integer> numSet = new HashSet<>();
            for (int num: row) {
                if (num > LENGTH || num <= 0 || numSet.contains(num)) {
                    return false;
                } else {
                    numSet.add(num);
                }
            }
        }

        return true;
    }

    private boolean columnCheck() {
        for (int i = 0; i < LENGTH; i++) {
            Set<Integer> numSet = new HashSet<>();
            for (int j = 0; j < LENGTH; j++) {
                int num = solution[i][j];
                if (num > LENGTH || num <= 0 || numSet.contains(num)) {
                    return false;
                } else {
                    numSet.add(num);
                }
            }
        }
        return true;
    }

    private boolean areaCheck() {
        int step = AREASTEP;
        for (int i = 0; i < LENGTH; i += step) {
            for (int j = 0; j < LENGTH; j += step) {
                Set<Integer> numSet = new HashSet<>();
                for (int k = 0; k < LENGTH; k++) {
                    int num = solution[i + k / step][j + k % step];
                    if (num > LENGTH || num <= 0 || numSet.contains(num)) {
                        return false;
                    } else {
                        numSet.add(num);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean isSolution() {
        return normalCheck() && specialCheck();

    }

    private int[][] deepCopy(int[][] from) {
        int[][] to = new int[LENGTH][LENGTH];
        for (int i = 0; i <LENGTH; i++) {
            to[i] = Arrays.copyOf(from[i], LENGTH);
        }
        return to;
    }

    /**
     * Get the problem sudoku matrix.
     * @return problem matrix
     */
    public int[][] getProMatrix() {
        return deepCopy(problem);
    }

    /**
     * Get the solution sudoku matrix.
     * @return solution matrix
     */
    public int[][] getSolMatrix() {
        return deepCopy(solution);
    }

    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append(problemGrid.toString());
        toPrint.append(solutionGrid.toString());
        return toPrint.toString();
    }
}
