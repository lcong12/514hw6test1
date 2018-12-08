package edu.cmu.cs.cs214.hw4.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The board class.
 * Has a map mapping from coordinate of a tile to the tile, which act as virtual board.
 * Has four set that store four types of features.
 * This class response for all options needed to put a tile, put a follower and score.
 * @author Cong Liao, cliao1
 */
public class Board {
    //Four set that store four types of features.
    private Set<Feature> roadSet;
    private Set<Feature> citySet;
    private Set<Feature> cloisterSet;
    private Set<Feature> farmSet;

    //Coordinate change arrays.
    private static final int[] XDIRECTION = new int[] {0, 1, 0, -1};
    private static final int[] YDIRECTION = new int[] {1, 0, -1, 0};
    private static final int[] BORDERRATATION = new int[] {8, 7, 6, 11, 10, 9, 2, 1, 0, 5, 4, 3};
    private static final int[] XAROUND = new int[] {0, 1, 1, 1, 0, -1, -1, -1};
    private static final int[] YAROUND = new int[] {1, 1, 0, -1, -1, -1, 0, 1};

    //Map as board.
    private Map<String, Tile> tilesOnBoard;

    /**
     * Constructor of the board class, initial all sets and map. Also initial features sets based on initial tile.
     * @param initialTile The initial tile of the game.
     */
    public Board(Tile initialTile) {
        roadSet = new HashSet<>();
        citySet = new HashSet<>();
        cloisterSet = new HashSet<>();
        farmSet = new HashSet<>();
        tilesOnBoard = new HashMap<>();
        initialBoard(initialTile);
    }

    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append("road:").append(" ").append(roadSet).append("\n")
                .append("city:").append(" ").append(citySet).append("\n")
                .append("farm:").append(" ").append(farmSet).append("\n")
                .append("cloister:").append(" ").append(cloisterSet).append("\n");
        return toPrint.toString();
    }

    //Initial board map and feature set.
    private void initialBoard(Tile initialTile) {
        //Tile initialTile = tileBag.getInitialTile();
        String initialCoodinate = Integer.toString(0) + "," + Integer.toString(0);
        tilesOnBoard.put(initialCoodinate, initialTile);
        initialTile.setSegCoordinate(initialCoodinate);

        Feature initialRoadFeature = new RoadFeature(SegmentType.ROAD);
        initialRoadFeature.addSegment(initialTile.getSegmentList().get(0));       //Hard code here because always this case, may change later.
        initialRoadFeature.setOpen(initialTile.getSegmentList().get(0).getOpen());
        initialRoadFeature.addCoordinate(initialCoodinate);
        roadSet.add(initialRoadFeature);

        Feature initialCityFeature = new CityFeature(SegmentType.CITY);
        initialCityFeature.addSegment(initialTile.getSegmentList().get(1));
        initialCityFeature.setOpen(initialTile.getSegmentList().get(1).getOpen());
        initialCityFeature.addCoordinate(initialCoodinate);
        citySet.add(initialCityFeature);

        Feature initialFarmFeature1 = new FarmFeature(SegmentType.FIELD);
        initialFarmFeature1.addSegment(initialTile.getSegmentList().get(2));
        initialFarmFeature1.setOpen(initialTile.getSegmentList().get(2).getOpen());
        initialFarmFeature1.addCoordinate(initialTile.getSegmentList().get(2).getCoordinate());
        farmSet.add(initialFarmFeature1);

        Feature initialFarmFeature2 = new FarmFeature(SegmentType.FIELD);
        initialFarmFeature2.addSegment(initialTile.getSegmentList().get(3));
        initialFarmFeature2.setOpen(initialTile.getSegmentList().get(3).getOpen());
        initialFarmFeature2.addCoordinate(initialCoodinate);
        farmSet.add(initialFarmFeature2);
    }

    /**
     * Put a tile onto the board.
     * Check whether the put operation is feasible, and merge features.
     * @param tile the tile to be put
     * @param x x-coordinate value
     * @param y y-coordinate value
     * @return True if the user can put a tile at this position.
     */
    public boolean putTile(Tile tile, int x, int y) {
        if (!checkAbut(tile, x, y)) {
            System.out.println("You cannot put tile here.");
            return false;
        }

        String coordinate = Integer.toString(x) + "," + Integer.toString(y);
        tilesOnBoard.put(coordinate, tile);
        tile.setSegCoordinate(coordinate);
        mergeFeature(tile, x, y);
        return true;
    }

    /**
     * Put a follower on a segment.
     * @param tile the tile that the segment belongs to
     * @param segmentNum the segment to be put on a follower
     * @param follower the follower to be put
     * @return True if the follower can be put on the segment.
     */
    public boolean putFollower(Tile tile, int segmentNum, Follower follower) {
        Segment seg = tile.getSegmentList().get(segmentNum);
        switch (seg.getType()) {
            case ROAD:
                Feature roadFeature = findFeature(seg, roadSet);
                return putFollowerHelper(roadFeature, follower);

            case CITY:
                Feature cityFeature = findFeature(seg, citySet);
                return putFollowerHelper(cityFeature, follower);

            case FIELD:
                Feature farmFeature = findFeature(seg, farmSet);
                return putFollowerHelper(farmFeature, follower);

            case CLOISTER:
                Feature cloisterFeature = findFeature(seg, cloisterSet);
                cloisterFeature.addFollowers(follower);
                return true;

            default:
                System.out.println("make checkstyle happy, should not reach here");
                return false;
        }
    }

    //Check whether the follower can be put into the feature, and add the follower to the follower set of the feature if possible.
    private boolean putFollowerHelper(Feature feature, Follower follower) {
        if (feature.getFollowers().isEmpty()) {
            feature.addFollowers(follower);
            return true;
        } else {
            System.out.println("You cannot put follower here. Other's follower exists");
            return false;
        }
    }

    //Check whether the tile can be put at this coordinate.
    private boolean checkAbut (Tile tile, int x, int y) {
        boolean hasNeighbor = false;
        for (int i = 0; i < 4; i++) {
            int newX = x + XDIRECTION[i];
            int newY = y + YDIRECTION[i];
            String coordinate = Integer.toString(newX) + "," + Integer.toString(newY);
            if (tilesOnBoard.containsKey(coordinate)) {
                hasNeighbor = true;
                Tile anotherTile = tilesOnBoard.get(coordinate);
//                System.out.println(tile);
//                System.out.println(anotherTile);
                if (!tile.canAbut(anotherTile, i)) {
                    return false;
                }
            }
        }
        return hasNeighbor;
    }

    //Add the segment of the tile into four feature sets.
    private void mergeFeature(Tile tile, int x, int y) {
        String coordinate = Integer.toString(x) + "," + Integer.toString(y);
        for (Segment seg : tile.getSegmentList()) {       //First for segment

            //Segment is cloister, directly add to cloisterSet.
            if (seg.getType() == SegmentType.CLOISTER) {
                Feature newFeature = new CloisterFeature(SegmentType.CLOISTER);
                newFeature.addSegment(seg);
                newFeature.addCoordinate(coordinate);
                cloisterSet.add(newFeature);
                return;
            }

            boolean merged = false;
            Set<Integer> directions = new HashSet<>();
            for (int border : seg.getBorderSet()) {       //Second for border
                int direction = border / 3;                // 0, 1, 2, 3
                if (directions.contains(direction)) {
                    continue;
                }
                directions.add(direction);
                int newX = x + XDIRECTION[direction];
                int newY = y + YDIRECTION[direction];
                String nextCoordinate = Integer.toString(newX) + "," + Integer.toString(newY);
                if (tilesOnBoard.containsKey(nextCoordinate)) {
                    Tile anotherTile = tilesOnBoard.get(nextCoordinate);
                    int anotherBorder = BORDERRATATION[border];
                    Segment anotherSeg = anotherTile.borderToSeg(anotherBorder);

                    //the tile is already in other feature.
                    if (merged) {
                        Feature oneFeature;
                        Feature anotherFeature;
                        switch (seg.getType()) {
                            case ROAD:
                                anotherFeature = findFeature(anotherSeg, roadSet);
                                if (anotherFeature.getSegmentSet().contains(seg)) {
                                    anotherFeature.setOpen(anotherFeature.getOpen() - 2);
                                    continue;
                                }

                                oneFeature = findFeature(seg, roadSet);
                                Feature newRoadFeature = new RoadFeature(SegmentType.ROAD);
                                mergeTwoFeature(oneFeature, anotherFeature, newRoadFeature, roadSet);
                                break;

                            case CITY:
                                anotherFeature = findFeature(anotherSeg, citySet);
                                if (anotherFeature.getSegmentSet().contains(seg)) {
                                    anotherFeature.setOpen(anotherFeature.getOpen() - 2);
                                    continue;
                                }

                                oneFeature = findFeature(seg, citySet);
                                Feature newCityFeature = new CityFeature(SegmentType.CITY);
                                mergeTwoFeature(oneFeature, anotherFeature, newCityFeature, citySet);
                                break;

                            case FIELD:
                                anotherFeature = findFeature(anotherSeg, farmSet);
                                if (anotherFeature.getSegmentSet().contains(seg)) {
                                    anotherFeature.setOpen(anotherFeature.getOpen() - 2);
                                    continue;
                                }

                                oneFeature = findFeature(seg, farmSet);
                                Feature newFarmFeature = new FarmFeature(SegmentType.FIELD);
                                mergeTwoFeature(oneFeature, anotherFeature, newFarmFeature, farmSet);
                                break;

                            default:
                                System.out.println("make checkstyle happy, should not reach here");
                        }
                    } else {
                        //Add a segment into a feature.
                        switch (seg.getType()) {
                            case ROAD:
                                if(addSegToFeature(seg, anotherSeg, roadSet)) {
                                    merged = true;
                                }
                                break;

                            case CITY:
                                if(addSegToFeature(seg, anotherSeg, citySet)) {
                                    merged = true;
                                }
                                break;

                            case FIELD:
                                if(addSegToFeature(seg, anotherSeg, farmSet)) {
                                    merged = true;
                                }
                                break;

                            default:
                                System.out.println("make checkstyle happy, should not reach here");
                        }
                    }
                }
            }

            //The segment cannot add to any feature, so the segment become of a feature.
            if (!merged) {
                switch (seg.getType()) {
                    case ROAD:
                        Feature newRoadFeature = new RoadFeature(SegmentType.ROAD);
                        newRoadFeature.addSegment(seg);
                        newRoadFeature.addCoordinate(seg.getCoordinate());
                        newRoadFeature.setOpen(seg.getOpen());
                        roadSet.add(newRoadFeature);
                        break;

                    case CITY:
                        Feature newCityFeature = new CityFeature(SegmentType.CITY);
                        newCityFeature.addSegment(seg);
                        newCityFeature.addCoordinate(seg.getCoordinate());
                        newCityFeature.setOpen(seg.getOpen());
                        if (seg.getHasPennant()) {
                            newCityFeature.addPennant();
                        }
                        citySet.add(newCityFeature);
                        break;

                    case FIELD:
                        Feature newFarmFeature = new FarmFeature(SegmentType.FIELD);
                        newFarmFeature.addSegment(seg);
                        newFarmFeature.addCoordinate(seg.getCoordinate());
                        newFarmFeature.setOpen(seg.getOpen());
                        farmSet.add(newFarmFeature);
                        break;

                    default:
                        System.out.println("make checkstyle happy, should not reach here");
                }
            }
        }
    }

    //Find the feature that contain the segment.
    private Feature findFeature(Segment seg, Set<Feature> set) {
        for (Feature feature : set) {
            if (feature.getSegmentSet().contains(seg)) {
                return feature;
            }
        }
        System.out.println("In findFeature, should not reach here");
        return null;
    }

    //Add the segment into a feature.
    private boolean addSegToFeature(Segment seg, Segment anotherSeg, Set<Feature> set) {
        boolean merged = false;
        for (Feature feature : set) {
            if (feature.getSegmentSet().contains(anotherSeg)) {
                feature.addSegment(seg);
                feature.addCoordinate(seg.getCoordinate());
                feature.setOpen(feature.getOpen() + seg.getOpen() - 2);
                if (seg.getHasPennant()) {
                    feature.addPennant();
                }
                merged = true;
                break;
            }
        }
        return merged;
    }

    //Merge two feature into one feature.
    private void mergeTwoFeature(Feature oneFeature, Feature anotherFeature, Feature newFeature, Set<Feature> set) {
        newFeature.addAnotherFeature(oneFeature);
        newFeature.addAnotherFeature(anotherFeature);
        newFeature.setOpen(oneFeature.getOpen() + anotherFeature.getOpen() - 2);
        set.add(newFeature);
        set.remove(oneFeature);
        set.remove(anotherFeature);
    }

    /**
     * Check whether the tile can be put onto the board.
     * @param tile the tile to be put
     * @return True if there is a position that the tile can be put.
     */
    public boolean needDiscard(Tile tile) {
        for (String coordinate : tilesOnBoard.keySet()) {
            String[] strings = coordinate.split(",");
            int x = Integer.parseInt(strings[0]);
            int y = Integer.parseInt(strings[1]);
            for (int i = 0; i < 4; i++) {
                int nextX = x + XDIRECTION[i];
                int nextY = y + YDIRECTION[i];
                String nextCo = Integer.toString(nextX) + "," + Integer.toString(nextY);
                if (!tilesOnBoard.containsKey(nextCo)) {
                    Tile temp = new Tile(tile);
                    for (int j = 0; j < 4; j++) {
                        if (checkAbut(temp, nextX, nextY)) {
                            return false;
                        } else {
                            temp.rotateForCheck();
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * The score function runs every time a player put a tile.
     * @param playerNum number of players
     * @return A array which length is twice as the number of players.
     *         Each player relate to two position in the array. First is the score to be added and the second is followers that to be withdrew.
     */
    public int[] score(int playerNum) {
        int[] scores = new int[playerNum * 2];
        //cloister
        for (Feature cloister : cloisterSet) {
            if (cloister.getFollowers().size() != 0) {
                int num = cloisterSur(cloister);
                if (num == 8) {
                    Follower follower = cloister.getFollowers().get(0);
                    int player = follower.getPlayerNum();
                    scores[(player - 1) * 2] += 9;
                    scores[(player - 1) * 2 + 1] += 1;
                    cloister.clearFollower();
                }
            }
        }

        //road and city
        for (Feature city : citySet) {
            if (city.getOpen() == 0 && city.getFollowers().size() > 0) {
                cityRoadScore(scores, city, playerNum, true);
            }
        }

        for (Feature road: roadSet) {
            if (road.getOpen() == 0 && road.getFollowers().size() > 0) {
                cityRoadScore(scores, road, playerNum, true);
            }
        }

        return scores;
    }

    //Check the numbers of tile around a cloister.
    private int cloisterSur(Feature cloister) {
        int num = 0;
        for (String coordinate : cloister.getCoordinateSet()) {
            String[] strings = coordinate.split(",");
            int x = Integer.parseInt(strings[0]);
            int y = Integer.parseInt(strings[1]);
            for (int i = 0; i < 8; i++) {
                int nextX = x + XAROUND[i];
                int nextY = y + YAROUND[i];
                String nextCo = Integer.toString(nextX) + "," + Integer.toString(nextY);
                if (tilesOnBoard.containsKey(nextCo)) {
                    num += 1;
                }
            }
        }

        return num;
    }

    //Decide the feature belongs to which player based on followers number, calculate score to be added and followers to be withdrew. Store then in an array.
    private void cityRoadScore(int[] scores, Feature feature, int totalPlayer, boolean isComplete) {

        int[] count = new int[totalPlayer];
        for (Follower follower : feature.getFollowers()) {
            count[follower.getPlayerNum() - 1] += 1;
        }

        int maxCount = 0;
        for (int i : count) {
            if (maxCount < i) {
                maxCount = i;
            }
        }

        int newScore = feature.score(feature.getCoordinateSet().size(), feature.getPennant(), isComplete);
        for (int i = 0; i < totalPlayer; i++) {
            if (count[i] == maxCount) {
                scores[i * 2] += newScore;
            }
            if (!(feature.getFeatureType() == SegmentType.FIELD)) {
                scores[i * 2 + 1] += count[i];
            }

        }
        if (!(feature.getFeatureType() == SegmentType.FIELD)) {
            feature.clearFollower();
        }

    }

    /**
     * The score function runs when the game is end.
     * @param playerNum the number of players
     * @return A array which length is twice as the number of players.
     *         Each player relate to two position in the array. First is the score to be added and the second is followers that to be withdrew.
     */
    public int[] endGameScore(int playerNum) {
        //cloister
        int[] scores = new int[playerNum * 2];
        for (Feature cloister : cloisterSet) {
            if (cloister.getFollowers().size() > 0) {
                int num = cloisterSur(cloister);
                Follower follower = cloister.getFollowers().get(0);
                int player = follower.getPlayerNum();
                scores[(player - 1) * 2] += num + 1;
                scores[(player - 1) * 2 + 1] += 1;
                cloister.clearFollower();
            }
        }
        //city and road
        for (Feature city : citySet) {
            if (city.getFollowers().size() > 0) {
                cityRoadScore(scores, city, playerNum, false);
            }
        }

        for (Feature road : roadSet) {
            if (road.getFollowers().size() > 0) {
                cityRoadScore(scores, road, playerNum, false);
            }
        }
        //farm
        for (Feature farm : farmSet) {
            Set<Feature> neighborCity = new HashSet<>();
            if (farm.getFollowers().size() > 0) {
                for (Segment seg : farm.getSegmentSet()) {
                    for (Segment citySeg : seg.getNeighborCity()) {
                        for (Feature city : citySet) {
                            if (city.getSegmentSet().contains(citySeg) && city.getOpen() == 0) {
                                neighborCity.add(city);
                            }
                        }
                    }
                }
            }

            for (Feature city : neighborCity) {
                cityRoadScore(scores, farm, playerNum, false);
            }
        }

        return scores;
    }
}
