package model;

import util.Constants;

import java.util.Observable;

public class Board extends Observable {

    public Tile[][] map;

    public int shipCount = 0;

    public Board() {
        map = new Tile[10][10];
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                map[r][c] = new Tile(null);
            }
        }
    }

    public boolean setShip(int row, int col, boolean orient, int type) {
        if (row < 0 || row > 10 || col < 0 || col > 10) {
            return false;
        }
        if (orient == Constants.HORIZONTAL) {
            int maxCol = col + Constants.SIZES.get(type);
            if (maxCol > 10) {
                return false;
            }
            for (int c = col; c < maxCol; c++) {
                if (map[row][c].ship != null) {
                    return false;
                }
            }
            Ship ship = new Ship();
            ship.orient = orient;
            for (int c = col; c < maxCol; c++) {
                map[row][c].ship = ship;
                ship.tiles.add(map[row][c]);
            }
        } else {
            int maxRow = row + Constants.SIZES.get(type);
            if (maxRow > 10) {
                return false;
            }
            for (int r = row; r < maxRow; r++) {
                if (map[r][col].ship != null) {
                    return false;
                }
            }
            Ship ship = new Ship();
            ship.orient = orient;
            for (int r = row; r < maxRow; r++) {
                map[r][col].ship = ship;
                ship.tiles.add(map[r][col]);
            }
        }
        shipCount++;
        notifyObservers(map);
        return true;
    }

    public boolean hit(int row, int col) {
        if (map[row][col].isHit) {
            return false;
        }
        map[row][col].isHit = true;
        if (map[row][col].ship != null && map[row][col].ship.isSunk()) {
            shipCount--;
        }
        notifyObservers(map);
        return true;
    }

    public boolean allShipsSunk() {
        return shipCount == 0;
    }
}
