package edu.cmu.cs.cs214.hw4.core;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.List;
import java.util.Arrays;

/**
 * The tileBag class that act as a list of tiles.
 * @author Cong Liao, cliao1
 */
public class TileBag {
    private Stack<Tile> bag = new Stack<>();
    //Class need to initial tile
    private TileFramework framework = new TileFramework();
    private Tile initialTile;
    private BufferedImage image;

    /**
     * Constructor of the class, initial all tiles and add them to list.
     */
    public TileBag() {
        try {
            image = ImageIO.read(new File("Carcassonne.png"));  //close file?
        } catch (IOException e) {
            System.out.println("read image file fails");
        }
        initialBag();
        initialTile = bag.get(7);
        bag.remove(7);
        Collections.shuffle(bag);
    }
    /**
     * Get the initial tile.
     * @return initial tile
     */
    public Tile getInitialTile() {
        return initialTile;
    }

    /**
     * Random get a tile from tile bag
     * @return next tile
     */
    public Tile nextTile() {
        return bag.pop();
    }

    /**
     * Get the number of remaining tile.
     * @return number of remaining tile
     */
    public int getRemainTileNum() {
        return bag.size();
    }

    /**
     * Get the tile based on tile index. Used in test.
     * @param num index
     * @return tile
     */
    public Tile getTile(int num) {
        for (Tile tile : bag) {
            if (tile.getTileNum() == num) {
                return tile;
            }
        }
        System.out.println("The tile does not exist");
        return new Tile(new ArrayList<>(), -1, false);
    }

    private void initialBag() {

        //Tile A
        List<List<Integer>> roadLista = new ArrayList<>();
        roadLista.add(Arrays.asList(7));
        int[] roadOpena = new int[] {1};

        List<List<Integer>> cityLista = new ArrayList<>();
        int[] cityOpena = new int[] {};

        List<List<Integer>> fieldLista = new ArrayList<>();
        fieldLista.add(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 8, 9, 10, 11));
        int[] fieldOpena = new int[] {4};

        for (int i = 0; i < 2; i++) {
            Tile tile = framework.initialTile(1 + i, roadLista, roadOpena, cityLista, cityOpena, fieldLista, fieldOpena, true);
            BufferedImage tileImage = image.getSubimage(0, 0, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile B
        List<List<Integer>> roadListb = new ArrayList<>();
        int[] roadOpenb = new int[] {};

        List<List<Integer>> cityListb = new ArrayList<>();
        int[] cityOpenb = new int[] {};

        List<List<Integer>> fieldListb = new ArrayList<>();
        fieldListb.add(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
        int[] fieldOpenb = new int[] {4};

        for (int i = 0; i < 4; i++) {
            Tile tile = framework.initialTile(3 + i, roadListb, roadOpenb, cityListb, cityOpenb, fieldListb, fieldOpenb, true);
            BufferedImage tileImage = image.getSubimage(90, 0, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile C
        List<List<Integer>> roadListc = new ArrayList<>();
        int[] roadOpenc = new int[] {};

        List<List<Integer>> cityListc = new ArrayList<>();
        cityListc.add(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 ,11));
        int[] cityOpenc = new int[] {4};

        List<List<Integer>> fieldListc = new ArrayList<>();
        int[] fieldOpenc = new int[] {};

        Tile tile7 = framework.initialTile(7, roadListc, roadOpenc, cityListc, cityOpenc, fieldListc, fieldOpenc, false);
        tile7.getSegmentList().get(0).setPennant(true);
        BufferedImage tileImage7 = image.getSubimage(180, 0, 90, 90);
        tile7.setImage(tileImage7);
        bag.add(tile7);

        //Tile D
        List<List<Integer>> roadListd = new ArrayList<>();
        roadListd.add(Arrays.asList(1, 7));
        int[] roadOpend = new int[] {2};

        List<List<Integer>> cityListd = new ArrayList<>();
        cityListd.add(Arrays.asList(3, 4, 5));
        int[] cityOpend = new int[] {1};

        List<List<Integer>> fieldListd= new ArrayList<>();
        fieldListd.add(Arrays.asList(2, 6));
        fieldListd.add(Arrays.asList(0, 8, 9, 10, 11));
        int[] fieldOpend = new int[] {2, 3};

        for (int i = 0; i < 4; i++) {
            Tile tile = framework.initialTile(8 + i, roadListd, roadOpend, cityListd, cityOpend, fieldListd, fieldOpend, false);
            tile.getSegmentList().get(2).addNeighborCity(tile.getSegmentList().get(1));
            BufferedImage tileImage = image.getSubimage(270, 0, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile E
        List<List<Integer>> roadListe = new ArrayList<>();
        int[] roadOpene = new int[] {};

        List<List<Integer>> cityListe = new ArrayList<>();
        cityListe.add(Arrays.asList(0, 1, 2));
        int[] cityOpene = new int[] {1};

        List<List<Integer>> fieldListe= new ArrayList<>();
        fieldListe.add(Arrays.asList(3, 4, 5, 6, 7, 8, 9, 10, 11));
        int[] fieldOpene = new int[] {3};

        for (int i = 0; i < 5; i++) {
            Tile tile = framework.initialTile(12 + i, roadListe, roadOpene, cityListe, cityOpene, fieldListe, fieldOpene, false);
            tile.getSegmentList().get(1).addNeighborCity(tile.getSegmentList().get(0));
            BufferedImage tileImage = image.getSubimage(360, 0, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile F
        List<List<Integer>> roadListf = new ArrayList<>();
        int[] roadOpenf = new int[] {};

        List<List<Integer>> cityListf = new ArrayList<>();
        cityListf.add(Arrays.asList(3, 4, 5, 9, 10, 11));
        int[] cityOpenf = new int[] {2};

        List<List<Integer>> fieldListf= new ArrayList<>();
        fieldListf.add(Arrays.asList(0, 1, 2));
        fieldListf.add(Arrays.asList(6, 7, 8));
        int[] fieldOpenf = new int[] {1, 1};

        for (int i = 0; i < 2; i++) {
            Tile tile = framework.initialTile(17 + i, roadListf, roadOpenf, cityListf, cityOpenf, fieldListf, fieldOpenf, false);
            tile.getSegmentList().get(0).setPennant(true);
            tile.getSegmentList().get(1).addNeighborCity(tile.getSegmentList().get(0));
            tile.getSegmentList().get(2).addNeighborCity(tile.getSegmentList().get(0));
            BufferedImage tileImage = image.getSubimage(450, 0, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile G
        List<List<Integer>> roadListg = new ArrayList<>();
        int[] roadOpeng = new int[] {};

        List<List<Integer>> cityListg = new ArrayList<>();
        cityListg.add(Arrays.asList(0, 1, 2, 6, 7, 8));
        int[] cityOpeng = new int[] {2};

        List<List<Integer>> fieldListg= new ArrayList<>();
        fieldListg.add(Arrays.asList(3, 4, 5));
        fieldListg.add(Arrays.asList(9, 10, 11));
        int[] fieldOpeng = new int[] {1, 1};

        Tile tile19 = framework.initialTile(19, roadListg, roadOpeng, cityListg, cityOpeng, fieldListg, fieldOpeng, false);
        tile19.getSegmentList().get(1).addNeighborCity(tile19.getSegmentList().get(0));
        tile19.getSegmentList().get(2).addNeighborCity(tile19.getSegmentList().get(0));
        BufferedImage tileImage19 = image.getSubimage(0, 90, 90, 90);
        tile19.setImage(tileImage19);

        bag.add(tile19);

        //Tile H
        List<List<Integer>> roadListh = new ArrayList<>();
        int[] roadOpenh = new int[] {};

        List<List<Integer>> cityListh = new ArrayList<>();
        cityListh.add(Arrays.asList(3, 4, 5));
        cityListh.add(Arrays.asList(9, 10, 11));
        int[] cityOpenh = new int[] {1, 1};

        List<List<Integer>> fieldListh= new ArrayList<>();
        fieldListh.add(Arrays.asList(0, 1, 2, 6, 7, 8));
        int[] fieldOpenh = new int[] {2};

        for (int i = 0; i < 3; i++) {
            Tile tile = framework.initialTile(20 + i, roadListh, roadOpenh, cityListh, cityOpenh, fieldListh, fieldOpenh, false);
            tile.getSegmentList().get(2).addNeighborCity(tile.getSegmentList().get(0));
            tile.getSegmentList().get(2).addNeighborCity(tile.getSegmentList().get(1));
            BufferedImage tileImage = image.getSubimage(90, 90, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile I
        List<List<Integer>> roadListi = new ArrayList<>();
        int[] roadOpeni = new int[] {};

        List<List<Integer>> cityListi = new ArrayList<>();
        cityListi.add(Arrays.asList(3, 4, 5));
        cityListi.add(Arrays.asList(6, 7, 8));
        int[] cityOpeni = new int[] {1, 1};

        List<List<Integer>> fieldListi= new ArrayList<>();
        fieldListi.add(Arrays.asList(0, 1, 2, 9, 10, 11));
        int[] fieldOpeni = new int[] {2};

        for (int i = 0; i < 2; i++) {
            Tile tile = framework.initialTile(23 + i, roadListi, roadOpeni, cityListi, cityOpeni, fieldListi, fieldOpeni, false);
            tile.getSegmentList().get(2).addNeighborCity(tile.getSegmentList().get(0));
            tile.getSegmentList().get(2).addNeighborCity(tile.getSegmentList().get(1));
            BufferedImage tileImage = image.getSubimage(180, 90, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile J
        List<List<Integer>> roadListj = new ArrayList<>();
        roadListj.add(Arrays.asList(4, 7));
        int[] roadOpenj = new int[] {2};

        List<List<Integer>> cityListj = new ArrayList<>();
        cityListj.add(Arrays.asList(0, 1, 2));
        int[] cityOpenj = new int[] {1};

        List<List<Integer>> fieldListj= new ArrayList<>();
        fieldListj.add(Arrays.asList(5, 6));
        fieldListj.add(Arrays.asList(3, 8, 9, 10, 11));
        int[] fieldOpenj = new int[] {2, 3};

        for (int i = 0; i < 3; i++) {
            Tile tile = framework.initialTile(25 + i, roadListj, roadOpenj, cityListj, cityOpenj, fieldListj, fieldOpenj, false);
            tile.getSegmentList().get(3).addNeighborCity(tile.getSegmentList().get(1));
            BufferedImage tileImage = image.getSubimage(270, 90, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile k
        List<List<Integer>> roadListk = new ArrayList<>();
        roadListk.add(Arrays.asList(1, 10));
        int[] roadOpenk = new int[] {2};

        List<List<Integer>> cityListk = new ArrayList<>();
        cityListk.add(Arrays.asList(3, 4, 5));
        int[] cityOpenk = new int[] {1};

        List<List<Integer>> fieldListk= new ArrayList<>();
        fieldListk.add(Arrays.asList(0, 11));
        fieldListk.add(Arrays.asList(2, 6, 7, 8, 9));
        int[] fieldOpenk = new int[] {2, 3};

        for (int i = 0; i < 3; i++) {
            Tile tile = framework.initialTile(28 + i, roadListk, roadOpenk, cityListk, cityOpenk, fieldListk, fieldOpenk, false);
            tile.getSegmentList().get(3).addNeighborCity(tile.getSegmentList().get(1));
            BufferedImage tileImage = image.getSubimage(360, 90, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile L
        List<List<Integer>> roadListl = new ArrayList<>();
        roadListl.add(Arrays.asList(1));
        roadListl.add(Arrays.asList(7));
        roadListl.add(Arrays.asList(10));
        int[] roadOpenl = new int[] {1, 1, 1};

        List<List<Integer>> cityListl = new ArrayList<>();
        cityListl.add(Arrays.asList(3, 4, 5));
        int[] cityOpenl = new int[] {1};

        List<List<Integer>> fieldListl= new ArrayList<>();
        fieldListl.add(Arrays.asList(0, 11));
        fieldListl.add(Arrays.asList(8, 9));
        fieldListl.add(Arrays.asList(2, 6));
        int[] fieldOpenl = new int[] {2, 2, 2};

        for (int i = 0; i < 3; i++) {
            Tile tile = framework.initialTile(31 + i, roadListl, roadOpenl, cityListl, cityOpenl, fieldListl, fieldOpenl, false);
            tile.getSegmentList().get(6).addNeighborCity(tile.getSegmentList().get(3));
            BufferedImage tileImage = image.getSubimage(450, 90, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile M
        List<List<Integer>> roadListm = new ArrayList<>();
        int[] roadOpenm = new int[] {};

        List<List<Integer>> cityListm = new ArrayList<>();
        cityListm.add(Arrays.asList(0, 1, 2, 9, 10, 11));
        int[] cityOpenm = new int[] {2};

        List<List<Integer>> fieldListm= new ArrayList<>();
        fieldListm.add(Arrays.asList(3, 4, 5, 6, 7, 8));
        int[] fieldOpenm = new int[] {2};

        for (int i = 0; i < 2; i++) {
            Tile tile = framework.initialTile(34 + i, roadListm, roadOpenm, cityListm, cityOpenm, fieldListm, fieldOpenm, false);
            tile.getSegmentList().get(1).addNeighborCity(tile.getSegmentList().get(0));
            tile.getSegmentList().get(0).setPennant(true);
            BufferedImage tileImage = image.getSubimage(0, 180, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile n
        for (int i = 0; i < 3; i++) {
            Tile tile = framework.initialTile(36 + i, roadListm, roadOpenm, cityListm, cityOpenm, fieldListm, fieldOpenm, false);
            tile.getSegmentList().get(1).addNeighborCity(tile.getSegmentList().get(0));
            BufferedImage tileImage = image.getSubimage(90, 180, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile o
        List<List<Integer>> roadListo = new ArrayList<>();
        roadListo.add(Arrays.asList(4, 7));
        int[] roadOpeno = new int[] {2};

        List<List<Integer>> cityListo = new ArrayList<>();
        cityListo.add(Arrays.asList(0, 1, 2, 9, 10, 11));
        int[] cityOpeno = new int[] {2};

        List<List<Integer>> fieldListo= new ArrayList<>();
        fieldListo.add(Arrays.asList(3, 8));
        fieldListo.add(Arrays.asList(5, 6));
        int[] fieldOpeno = new int[] {2, 2};

        for (int i = 0; i < 2; i++) {
            Tile tile = framework.initialTile(39 + i, roadListo, roadOpeno, cityListo, cityOpeno, fieldListo, fieldOpeno, false);
            tile.getSegmentList().get(2).addNeighborCity(tile.getSegmentList().get(1));
            tile.getSegmentList().get(1).setPennant(true);
            BufferedImage tileImage = image.getSubimage(180, 180, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile p
        for (int i = 0; i < 3; i++) {
            Tile tile = framework.initialTile(41 + i, roadListo, roadOpeno, cityListo, cityOpeno, fieldListo, fieldOpeno, false);
            tile.getSegmentList().get(2).addNeighborCity(tile.getSegmentList().get(1));
            BufferedImage tileImage = image.getSubimage(270, 180, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile q
        List<List<Integer>> roadListq = new ArrayList<>();
        int[] roadOpenq = new int[] {};

        List<List<Integer>> cityListq = new ArrayList<>();
        cityListq.add(Arrays.asList(0, 1, 2, 3, 4, 5, 9, 10, 11));
        int[] cityOpenq = new int[] {3};

        List<List<Integer>> fieldListq= new ArrayList<>();
        fieldListq.add(Arrays.asList(6, 7, 8));
        int[] fieldOpenq = new int[] {1};

        for (int i = 0; i < 1; i++) {
            Tile tile = framework.initialTile(44 + i, roadListq, roadOpenq, cityListq, cityOpenq, fieldListq, fieldOpenq, false);
            tile.getSegmentList().get(1).addNeighborCity(tile.getSegmentList().get(0));
            tile.getSegmentList().get(0).setPennant(true);
            BufferedImage tileImage = image.getSubimage(360, 180, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile r
        for (int i = 0; i < 3; i++) {
            Tile tile = framework.initialTile(45 + i, roadListq, roadOpenq, cityListq, cityOpenq, fieldListq, fieldOpenq, false);
            tile.getSegmentList().get(1).addNeighborCity(tile.getSegmentList().get(0));
            BufferedImage tileImage = image.getSubimage(450, 180, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile s
        List<List<Integer>> roadLists = new ArrayList<>();
        roadLists.add(Arrays.asList(7));
        int[] roadOpens = new int[] {1};

        List<List<Integer>> cityLists = new ArrayList<>();
        cityLists.add(Arrays.asList(0, 1, 2, 3, 4, 5, 9, 10, 11));
        int[] cityOpens = new int[] {3};

        List<List<Integer>> fieldLists= new ArrayList<>();
        fieldLists.add(Arrays.asList(6));
        fieldLists.add(Arrays.asList(8));
        int[] fieldOpens = new int[] {1, 1};

        for (int i = 0; i < 2; i++) {
            Tile tile = framework.initialTile(48 + i, roadLists, roadOpens, cityLists, cityOpens, fieldLists, fieldOpens, false);
            tile.getSegmentList().get(2).addNeighborCity(tile.getSegmentList().get(1));
            tile.getSegmentList().get(3).addNeighborCity(tile.getSegmentList().get(1));
            tile.getSegmentList().get(1).setPennant(true);
            BufferedImage tileImage = image.getSubimage(0, 270, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile t
        Tile tile50 = framework.initialTile(50, roadLists, roadOpens, cityLists, cityOpens, fieldLists, fieldOpens, false);
        tile50.getSegmentList().get(2).addNeighborCity(tile50.getSegmentList().get(1));
        tile50.getSegmentList().get(3).addNeighborCity(tile50.getSegmentList().get(1));
        BufferedImage tileImage50 = image.getSubimage(90, 270, 90, 90);
        tile50.setImage(tileImage50);
        bag.add(tile50);

        //Tile u
        List<List<Integer>> roadListu = new ArrayList<>();
        roadListu.add(Arrays.asList(1, 7));
        int[] roadOpenu = new int[] {2};

        List<List<Integer>> cityListu = new ArrayList<>();
        int[] cityOpenu = new int[] {};

        List<List<Integer>> fieldListu= new ArrayList<>();
        fieldListu.add(Arrays.asList(2, 3, 4, 5, 6));
        fieldListu.add(Arrays.asList(8, 9, 10, 11, 0));
        int[] fieldOpenu = new int[] {3, 3};

        for (int i = 0; i < 8; i++) {
            Tile tile = framework.initialTile(51 + i, roadListu, roadOpenu, cityListu, cityOpenu, fieldListu, fieldOpenu, false);
            BufferedImage tileImage = image.getSubimage(180, 270, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile v
        List<List<Integer>> roadListv = new ArrayList<>();
        roadListv.add(Arrays.asList(10, 7));
        int[] roadOpenv = new int[] {2};

        List<List<Integer>> cityListv = new ArrayList<>();
        int[] cityOpenv = new int[] {};

        List<List<Integer>> fieldListv= new ArrayList<>();
        fieldListv.add(Arrays.asList(8, 9));
        fieldListv.add(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 11));
        int[] fieldOpenv = new int[] {2, 4};

        for (int i = 0; i < 9; i++) {
            Tile tile = framework.initialTile(59 + i, roadListv, roadOpenv, cityListv, cityOpenv, fieldListv, fieldOpenv, false);
            BufferedImage tileImage = image.getSubimage(270, 270, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile w
        List<List<Integer>> roadListw = new ArrayList<>();
        roadListw.add(Arrays.asList(4));
        roadListw.add(Arrays.asList(7));
        roadListw.add(Arrays.asList(10));
        int[] roadOpenw = new int[] {1, 1, 1};

        List<List<Integer>> cityListw = new ArrayList<>();
        int[] cityOpenw = new int[] {};

        List<List<Integer>> fieldListw= new ArrayList<>();
        fieldListw.add(Arrays.asList(5, 6));
        fieldListw.add(Arrays.asList(8, 9));
        fieldListw.add(Arrays.asList(0, 1, 2, 3, 11));
        int[] fieldOpenw = new int[] {2, 2, 3};

        for (int i = 0; i < 4; i++) {
            Tile tile = framework.initialTile(68 + i, roadListw, roadOpenw, cityListw, cityOpenw, fieldListw, fieldOpenw, false);
            BufferedImage tileImage = image.getSubimage(360, 270, 90, 90);
            tile.setImage(tileImage);
            bag.add(tile);
        }

        //Tile x
        List<List<Integer>> roadListx = new ArrayList<>();
        roadListx.add(Arrays.asList(1));
        roadListx.add(Arrays.asList(4));
        roadListx.add(Arrays.asList(7));
        roadListx.add(Arrays.asList(10));
        int[] roadOpenx = new int[] {1, 1, 1, 1};

        List<List<Integer>> cityListx = new ArrayList<>();
        int[] cityOpenx = new int[] {};

        List<List<Integer>> fieldListx= new ArrayList<>();
        fieldListx.add(Arrays.asList(0, 11));
        fieldListx.add(Arrays.asList(2, 3));
        fieldListx.add(Arrays.asList(5, 6));
        fieldListx.add(Arrays.asList(8, 9));
        int[] fieldOpenx = new int[] {2, 2, 2, 2};
        Tile tile72 = framework.initialTile(72, roadListx, roadOpenx, cityListx, cityOpenx, fieldListx, fieldOpenx, false);
        BufferedImage tileImage72 = image.getSubimage(450, 270, 90, 90);
        tile72.setImage(tileImage72);
        bag.add(tile72);


    }

}
