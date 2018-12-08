package edu.cmu.cs.cs214.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Grid class.
 * @author Cong Liao, cliao1
 */

public class Grid {

    private static final int LENGTH = 9;
    private int[][] matrix = new int[LENGTH][LENGTH];
    private boolean isValid = false;
    /**
     * Construcor of Grid.
     * Takes in two files which contain a grid and convert it to 2d matrix.
     * @param filePath file to read in
     */
    public Grid(String filePath) {
        if (valid(filePath)) {
            isValid = true;
        } else {
            matrix = new int[LENGTH][LENGTH];
        }
    }

    private boolean valid(String filePath) {
        if (filePath != null) {
            try (Scanner scanner = new Scanner(new File(filePath))) {
                int i;
                for (i = 0; scanner.hasNextLine(); i++) {
                    if (i >= LENGTH) {
                        System.out.println("There are to many rows in the file.");
                        System.out.println("Input grid is invalid.");
                        return false;
                    }
                    String line = scanner.nextLine();
                    String[] strings = line.split(" ");
                    if (!stringsToNums(strings, i)) {
                        System.out.println("Input grid is invalid.");
                        return false;
                    }
                }
                if (i < LENGTH) {
                    System.out.println("Your input contains " + i + " rows of numbers");
                    System.out.println("Input grid is invalid.");
                    return false;
                }
                return true;
            } catch (FileNotFoundException e) {
                System.out.println("File does not exist.");
                // return false;
                // e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Check whether the input is a valid grid.
     * @return return true if it is valid.
     */
    public boolean getValid() {
        return isValid;
    }

    /**
     * Get the 2d matrix converted from the input file.
     * @return deep copy of matrix
     */
    public int[][] getMatrix() {
        int[][] m = new int[LENGTH][LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            m[i] = Arrays.copyOf(matrix[i], LENGTH);
        }
        return m;
    }

    /**
     * Get the length of each row of column.
     * @return length of matrix row
     */
    public int getLength() {
        return LENGTH;
    }

    private boolean stringsToNums(String[] strings, int i) {
        if (strings.length != LENGTH) {
            System.out.println("There are " + strings.length + " numbers in this row.");
            return false;
        }
        for (int j = 0; j < LENGTH; j++) {
            try {
                int num = Integer.parseInt(strings[j]);
                if (num < 0 || num > 81) {
                    System.out.println("Exist number that smaller than 0 or bigger than 81.");
                    return false;
                }
                matrix[i][j] = Integer.parseInt(strings[j]);
            } catch (NumberFormatException e) {
                System.out.println("Number input shouble be integers");
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] row: matrix) {
            String prefix = "";
            for (int num: row) {
                stringBuilder.append(prefix);
                prefix = " ";
                stringBuilder.append(String.valueOf(num));
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
