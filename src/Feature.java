package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The abstract Feature class represent features constructed by segments.
 * @author Cong Liao, cliao1
 */
public abstract class Feature {
    private SegmentType featureType;
    //The set contains all segments of the feature.
    private Set<Segment> segmentSet;
    //The set of coordinates of all segments.
    private Set<String> coordinateSet;
    //Followers on the feature.
    private List<Follower> followers;
    //Numbers of open of the feature.
    private int open = 0;
    private int pennent = 0;

    /**
     * Constructor of the feature class.
     * @param type the type of the feature.
     */
    public Feature(SegmentType type) {
        featureType = type;
        segmentSet = new HashSet<>();
        coordinateSet = new HashSet<>();
        followers = new ArrayList<>();
    }

    //The feature function, need to implement in subclass.
    protected abstract int score(int tileNum, int totalPennent, boolean isComplete);

    /**
     * Get the feature's type.
     * @return the type of the feature.
     */
    public SegmentType getFeatureType() {
        return featureType;
    }

    /**
     * Add a pennant to the feature.
     */
    public void addPennant() {
        pennent += 1;
    }

    /**
     * Get the number of pennants of the feature.
     * @return number of pennants
     */
    public int getPennant() {
        return pennent;
    }

    private void addAllSegment(Set<Segment> set) {
        segmentSet.addAll(set);
    }

    private void addAllFollowers(List<Follower> list) {
        followers.addAll(list);
    }

    /**
     * Add a segment into the feature.
     * @param segment the segment to be added
     */
    public void addSegment(Segment segment) {
        segmentSet.add(segment);
    }

    /**
     * Get the segment set.
     * @return Segment set.
     */
    public Set<Segment> getSegmentSet() {
        return new HashSet<>(segmentSet);
    }

    /**
     * Get the list of followers.
     * @return followers list
     */
    public List<Follower> getFollowers() {
        return new ArrayList<>(followers);
    }

    /**
     * Add a follower into the feature.
     * @param follower the follower to be added
     */
    public void addFollowers(Follower follower) {
        followers.add(follower);
    }

    /**
     * Get the coordinate set.
     * @return the coordinate set
     */
    public Set<String> getCoordinateSet() {
        return new HashSet<>(coordinateSet);
    }

    /**
     * Add a coordinate into the feature.
     * @param coordinate the coordinate to be added
     */
    public void addCoordinate(String coordinate) {
        coordinateSet.add(coordinate);
    }

    private void addAllCoordinate(Set<String> set) {
        coordinateSet.addAll(set);
    }

    private void addAllPennent(int anotherPennent) {
        pennent += anotherPennent;
    }

    /**
     * Merge two feature, add all segments, followers, coordinates, pennants belonging to another feature to this feature.
     * @param anotherFeature another feature to be added.
     */
    public void addAnotherFeature(Feature anotherFeature) {
        addAllSegment(anotherFeature.getSegmentSet());
        addAllFollowers(anotherFeature.getFollowers());
        addAllCoordinate(anotherFeature.getCoordinateSet());
        addAllPennent(anotherFeature.getPennant());
    }

    /**
     * Clear all followers, used when the feature is completed.
     */
    public void clearFollower() {
        followers = new ArrayList<>();
    }

    /**
     * Get the open number of the feature.
     * @return the open
     */
    public int getOpen() {
        return open;
    }

    /**
     * Set the open number of the feature.
     * @param num the number that open will be set
     */
    public void setOpen(int num) {
        open = num;
    }

    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append(featureType).append(" ")
                .append("segment: ").append(segmentSet).append("\n")
                .append("followers: ").append(followers).append("\n")
                .append("coordinates: ").append(coordinateSet).append("\n")
                .append("open:")
                .append(open).append("\t")
                .append("pennant: ").append(pennent).append("\n");
        return toPrint.toString();
    }
}
