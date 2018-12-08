package edu.cmu.cs.cs214.hw2;

public class Test {
    public static void main(String[] args) {
        NumbrixVerifier v1 = new NumbrixVerifier("src/main/resources/numbrix-problem-1.txt", "src/main/resources/numbrix-solution-1.txt");
        System.out.println(v1.getSolution());
    }


}
