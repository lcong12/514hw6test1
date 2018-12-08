package edu.cmu.cs.cs214.hw4.core;

/**
 * The player class.
 * @author Cong Liao, cliao1
 */
public class Player {
    private int playerNum;
    private int score;
    private int followerNum;

    /**
     * Constructor of the class.
     * @param pNum play number of the player
     * @param newScore the score of the player
     * @param fNum the follower number of the player
     */
    public Player (int pNum, int newScore, int fNum){
        playerNum = pNum;
        score = newScore;
        followerNum = fNum;
    }

    /**
     * Get the player number of the player.
     * @return player number
     */
    public int getPlayerNum() {
        return playerNum;
    }

    /**
     * Get the score of the player.
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Get the number of remain follower.
     * @return follower number
     */
    public int getFollowerNum() {
        return followerNum;
    }

    /**
     * Add score of the player.
     * @param num new score to be added.
     */
    public void addScore(int num) {
        score = score + num;
    }

    /**
     * Add a follower to the player
     * @param num number of followers to be added
     */
    public void addFollower(int num) {
        followerNum += num;
    }

    /**
     * The player put a tile.
     * @return True if the player have enough follower to put.
     */
    public boolean putFollower() {
        if (followerNum > 0) {
            followerNum -= 1;
            return true;
        }
        return false;
    }
}
