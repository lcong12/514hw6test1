package edu.cmu.cs.cs214.hw3.StrategyPattern;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HyperSudokuVerifierTest {

    private List<Verifier> verifiers = new ArrayList<>();

    @Before
    public void setUp() {
        verifiers.add(new HyperSudokuVerifier("src/main/resources/hypersudoku-problem-1.txt", "src/main/resources/hypersudoku-solution-1.txt"));
        verifiers.add(new HyperSudokuVerifier("src/main/resources/sudoku-problem-2.txt", "src/main/resources/sudoku-solution-2.txt"));
    }

    @Test
    public void isSolution() {
        assertTrue(verifiers.get(0).isSolution());
        assertFalse(verifiers.get(1).isSolution());
    }

    @Test
    public void toStringTest() {
        String string = "6 0 0 8 0 5 7 0 3\n" +
                "0 7 9 1 0 0 0 8 0\n" +
                "0 0 0 0 6 0 0 0 0\n" +
                "0 0 2 0 8 0 5 0 9\n" +
                "0 0 0 0 5 4 0 0 0\n" +
                "0 0 0 0 0 3 0 0 0\n" +
                "0 0 0 7 0 6 2 0 0\n" +
                "0 0 0 0 0 0 0 0 0\n" +
                "0 0 0 0 0 9 0 0 0\n" +
                "6 1 4 8 9 5 7 2 3\n" +
                "3 7 9 1 4 2 6 8 5\n" +
                "2 8 5 3 6 7 4 9 1\n" +
                "7 4 2 6 8 1 5 3 9\n" +
                "1 9 3 2 5 4 8 6 7\n" +
                "8 5 6 9 7 3 1 4 2\n" +
                "9 3 8 7 1 6 2 5 4\n" +
                "5 2 1 4 3 8 9 7 6\n" +
                "4 6 7 5 2 9 3 1 8\n";
        assertEquals(string, verifiers.get(0).toString());
    }
}