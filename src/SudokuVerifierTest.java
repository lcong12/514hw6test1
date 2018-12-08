package edu.cmu.cs.cs214.hw3.StrategyPattern;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SudokuVerifierTest {

    private List<SudokuVerifier> verifiers = new ArrayList<>();

    @Before
    public void setUp() {
        verifiers.add(new SudokuVerifier("src/main/resources/sudoku-problem-1.txt", "src/main/resources/sudoku-solution-1.txt"));
        verifiers.add(new SudokuVerifier("src/main/resources/sudoku-problem-2.txt", "src/main/resources/sudoku-solution-1.txt"));
        //Invalid input file
        verifiers.add(new SudokuVerifier("src/main/resources/sudoku-problem-2.txt", "src/main/resources/wrong-rows.txt"));
        verifiers.add(new SudokuVerifier("src/main/resources/sudoku-problem-2.txt", "src/main/resources/empty.txt"));
        verifiers.add(new SudokuVerifier("src/main/resources/sudoku-problem-2.txt", "src/main/resources/invalidNumber.txt"));
        verifiers.add(new SudokuVerifier("src/main/resources/sudoku-problem-2.txt", "src/main/resources/wrong-numbers-inrows.txt"));
    }

    @Test
    public void isSolution() {
        assertTrue(verifiers.get(0).isSolution());
        assertFalse(verifiers.get(1).isSolution());
        assertFalse(verifiers.get(2).isSolution());
        assertFalse(verifiers.get(3).isSolution());
        assertFalse(verifiers.get(4).isSolution());
        assertFalse(verifiers.get(5).isSolution());
    }

    @Test
    public void getProMatrix() {
        int[][] proMatrixTest = new int[][] {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 9, 1, 0, 7, 6, 0, 3},
                {0, 6, 0, 0, 3, 4, 0, 0, 7},
                {0, 0, 0, 0, 0, 1, 4, 0, 2},
                {0, 3, 4, 8, 0, 6, 5, 7, 0},
                {2, 0, 7, 3, 0, 0, 0, 0, 0},
                {5, 0, 0, 7, 1, 0, 0, 8, 0},
                {7, 0, 1, 5, 0, 9, 3, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}};
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(proMatrixTest[i][j], verifiers.get(0).getProMatrix()[i][j]);
            }
        }
    }

    @Test
    public void getLengthTest() {
        assertEquals(9, verifiers.get(0).getLength());
    }

    @Test
    public void getAreastepTest() {
        assertEquals(3, verifiers.get(0).getAreastep());
    }

    @Test
    public void toStringTest() {
        String string = "0 0 0 0 0 0 0 0 0\n" +
                "0 0 9 1 0 7 6 0 3\n" +
                "0 6 0 0 3 4 0 0 7\n" +
                "0 0 0 0 0 1 4 0 2\n" +
                "0 3 4 8 0 6 5 7 0\n" +
                "2 0 7 3 0 0 0 0 0\n" +
                "5 0 0 7 1 0 0 8 0\n" +
                "7 0 1 5 0 9 3 0 0\n" +
                "0 0 0 0 0 0 0 0 0\n" +
                "3 7 2 6 9 8 1 5 4\n" +
                "4 8 9 1 5 7 6 2 3\n" +
                "1 6 5 2 3 4 8 9 7\n" +
                "6 5 8 9 7 1 4 3 2\n" +
                "9 3 4 8 2 6 5 7 1\n" +
                "2 1 7 3 4 5 9 6 8\n" +
                "5 4 6 7 1 3 2 8 9\n" +
                "7 2 1 5 8 9 3 4 6\n" +
                "8 9 3 4 6 2 7 1 5\n";
        assertEquals(string, verifiers.get(0).toString());
    }
}