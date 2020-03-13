package model;

import java.util.LinkedList;
import java.util.List;

public class Ship {

    public List<Tile> tiles = new LinkedList<>();

    public boolean isSunk() {
        return tiles.stream().allMatch(t -> t.isHit);
    }
}
