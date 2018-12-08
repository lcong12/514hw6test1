package edu.cmu.cs.cs214.hw1;


/**
 * Takes a list of URLs on the command line and prints the two URL whose web pages have the highest cosine similarity.
 * Prints a stack trace if any of the URLs are invalid, or if an exception occurs while reading data from
 * the URLs.
 */
public class FindClosestMatch {

    /**
     * Method that is called when this program is run (the 'main' method).
     *
     * @param args
     *            command line arguments, the input urls.
     */

    public static void main(String[] args) {
        double maxSimilarity = -1;
        int numDocs = args.length;
        // Array that store all documents.
        Document[] allPages = new Document[numDocs];
        int[] pair = {-1, -1};

        for (int i = 0; i < numDocs; i++) {
            allPages[i] = new Document(args[i]);
        }

        for (int i = 0; i < numDocs; i++) {
            for (int j = i + 1; j < numDocs; j++) {
                double curSimilarity = allPages[i].similarity(allPages[j]);
                if (curSimilarity > maxSimilarity) {
                    maxSimilarity = curSimilarity;
                    pair[0] = i;
                    pair[1] = j;
                }
            }
        }

        System.out.println(allPages[pair[0]] + " " + allPages[pair[1]]);
    }
}
