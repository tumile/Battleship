package model;

import java.util.LinkedList;
import java.util.List;

public class Ship {

    public List<Tile> tiles;

    public boolean orient;

    public Ship() {
        tiles = new LinkedList<>();
    }

    public boolean isSunk() {
        return tiles.stream().allMatch(t -> t.isHit);
    }
}
