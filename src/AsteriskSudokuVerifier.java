package edu.cmu.cs.cs214.hw3.StrategyPattern;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * A specific strategy of verifier.
 * The implement of asterisk sudoku verifier that verifier whether the solution is correct.
 */
public class AsteriskSudokuVerifier implements Verifier {

    private static final int[] POINT1 = {1, 4};
    private static final int[] POINT2 = {2, 2};
    private static final int[] POINT3 = {2, 6};
    private static final int[] POINT4 = {4, 1};
    private static final int[] POINT5 = {4, 4};
    private static final int[] POINT6 = {4, 7};
    private static final int[] POINT7 = {6, 2};
    private static final int[] POINT8 = {6, 6};
    private static final int[] POINT9 = {7, 4};
    private List<int[]> pointList = new ArrayList<>();

    private SudokuVerifier sudokuVerifier;

    /**
     * Constructor of verifier.
     * Takes in two grid file. One is a sudoku problem, the other is solution to it.
     * @param problemFile file contains problem sudoku
     * @param solutionFile file contains solution sudoku
     */
    public AsteriskSudokuVerifier(String problemFile, String solutionFile) {
        sudokuVerifier = new SudokuVerifier(problemFile, solutionFile);

        pointList.add(POINT1);
        pointList.add(POINT2);
        pointList.add(POINT3);
        pointList.add(POINT4);
        pointList.add(POINT5);
        pointList.add(POINT6);
        pointList.add(POINT7);
        pointList.add(POINT8);
        pointList.add(POINT9);
    }

    private boolean normalCheck() {
        return sudokuVerifier.normalCheck();
    }

    private boolean specialCheck() {
        Set<Integer> numSet = new HashSet<>();
        for (int[] vertex: pointList) {
            int num = sudokuVerifier.getSolMatrix()[vertex[0]][vertex[1]];
            if (num > sudokuVerifier.getLength() || num <= 0 || numSet.contains(num)) {
                return false;
            } else {
                numSet.add(num);
            }
        }
        return true;
    }

    @Override
    public boolean isSolution() {
        return normalCheck() && specialCheck();
    }

    @Override
    public String toString() {
        return sudokuVerifier.toString();
    }
}
