package edu.cmu.cs.cs214.hw4.core;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


/**
 * The tile class represent a tile.
 * @author Cong Liao, cliao1
 */
public class Tile {
    //List stores all segment of the tile.
    private List<Segment> segmentList;
    private int tileNum;
    //private int rotated = 0;
    private boolean hasCloister;
    private BufferedImage tileImage;
    private int rotateTimes = 0;
    //private Map<Integer, Segment> borderToSegMap;

    /**
     * Constructor of the tile.
     * @param list list stores all segment of the tile
     * @param tileNum the tile number of the tile
     * @param hasCloister whether the segment has a cloister
     */
    public Tile(List<Segment> list, int tileNum, boolean hasCloister) {
        this.tileNum = tileNum;
        segmentList = list;
        this.hasCloister = hasCloister;
    }

    /**
     * Set the image of the tile.
     * @param img image
     */
    public void setImage(BufferedImage img) {
        tileImage = img;
    }

    /**
     * Get the image of the tile.
     * @return the image of the tile
     */
    public BufferedImage getTileImage() {
        return tileImage;
    }
    /**
     * Constructor that takes another tile and make a deepcopy of the tile.
     * @param tile another tile to be copied
     */
    public Tile(Tile tile) {
        //this.segmentList = tile.getSegmentList();
        segmentList = new ArrayList<>();
        for (Segment seg : tile.getSegmentList()) {
            Segment newSeg = new Segment(seg);
            this.segmentList.add(newSeg);
        }
        this.tileNum = tile.getTileNum();
        this.hasCloister = tile.getHasCloister();
    }

    /**
     * Check whether the tile has a cloister.
     * @return True if the tile has a cloister
     */
    public boolean getHasCloister() {
        return hasCloister;
    }

    /**
     * Get the tile number.
     * @return tile number
     */
    public int getTileNum() {
        return tileNum;
    }

    /**
     * Get the list of segment.
     * @return list of segment
     */
    public List<Segment> getSegmentList() {
        return new ArrayList<>(segmentList);
    }

    /**
     * Find the segment based on border.
     * @param border border number
     * @return the segment found.
     */
    public Segment borderToSeg(int border) {
        for (Segment seg : segmentList) {
            if (seg.getBorderSet().contains(border)) {
                return seg;
            }
        }
        System.out.println("Could not find the segment, should not reach here.");
        return null;            // should not reach here.
    }

    /**
     * Check whether the tile can abut another tile in the direction.
     * @param anotherTile another tile
     * @param direction the direction of abut
     * @return True if these two tile can be next to each other
     */
    public boolean canAbut(Tile anotherTile, int direction) {
        int border = 3 * direction + 1;
        //System.out.println("in canBut: i, border," + direction + border);
        Segment seg = borderToSeg(border);
        int anotherBorder = border > 6 ? border - 6 : border + 6;
        Segment anotherSeg = anotherTile.borderToSeg(anotherBorder);
        return seg.getType() == anotherSeg.getType();
    }

    /**
     * Used only when check there the tile need to be discard.
     */
    public void rotateForCheck() {
        for (Segment seg : segmentList) {
            List<Integer> list = new ArrayList<>();
            for (int border : seg.getBorderSet()) {
                seg.removeBorderSet(border);
                int newBorder = (border + 3) % 12;
                list.add(newBorder);
            }

            for (int border : list) {
                seg.addBorderSet(border);
            }
        }
    }

    /**
     * Rotate the tile clockwise.
     */
    public void rotate() {
        rotateForCheck();

        BufferedImage newImage = rotateClockwise(tileImage, 1);
        tileImage = newImage;

        rotateTimes += 1;
    }

    /**
     * Get how many time the tile has been rotate.
     * @return times
     */
    public int getRotateTimes() {
        return rotateTimes;
    }

    /**
     * Set the coordinate of the all segment in the tile.
     * @param coordinate coordinate
     */
    public void setSegCoordinate(String coordinate) {
        for (Segment seg : segmentList) {
            seg.setCoordinate(coordinate);
        }
    }

    @Override
    public String toString() {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append(Integer.toString(tileNum))
                .append("\n");
        for (Segment seg : segmentList) {
            toPrint.append(seg.toString());
        }
        toPrint.append("has cloister: ").append(hasCloister).append("\n");
        return toPrint.toString();
    }

    private BufferedImage rotateClockwise(BufferedImage src, int n) {
        int weight = src.getWidth();
        int height = src.getHeight();

        AffineTransform at = AffineTransform.getQuadrantRotateInstance(n, weight / 2.0, height / 2.0);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

        BufferedImage dest = new BufferedImage(weight, height, src.getType());
        op.filter(src, dest);
        return dest;
    }
}
