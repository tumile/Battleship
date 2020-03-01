package model;

import java.util.Map;

public class Board {

	final boolean HORIZONTAL = true, VERTICAL = false;

	final int CARRIER = 0, BATTLESHIP = 1, CRUISER = 2, SUBMARINE = 3, DESTROYER = 4;

	Map<Integer, Integer> SIZES = Map.of(CARRIER, 5, BATTLESHIP, 4, CRUISER, 3, SUBMARINE, 3, DESTROYER, 2);


	class Ship {

		int startRow, startCol;

		int endRow, endCol;

		Ship(int startRow, int startCol, int endRow, int endCol) {
			this.startRow = startRow;
			this.startCol = startCol;
			this.endRow = endRow;
			this.endCol = endCol;
		}
	}

	class Tile {

		Ship ship;

		boolean hit;

		Tile(Ship ship) {
			this.ship = ship;
		}
	}

	Tile[][] hitMap = new Tile[10][10];

	int shipCount = 0;

	public Board() {
		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++) {
				hitMap[r][c] = new Tile(null);
			}
		}
	}

	boolean hit(int row, int col) {
		if (hitMap[row][col].hit) {
			return false;
		}
		hitMap[row][col].hit = true;
		if (hitMap[row][col].ship != null && isShipSunk(hitMap[row][col].ship)) {
			shipCount--;
		}
		return true;
	}

	boolean setShip(int row, int col, boolean orient, int type) {
		if ((orient == HORIZONTAL && col + SIZES.get(type) > 10)
				|| (orient == VERTICAL && row + SIZES.get(type) > 10)) {
			return false;
		}
		if (orient == HORIZONTAL) {
			int maxCol = col + SIZES.get(type);
			for (int c = col; c < maxCol; c++) {
				if (hitMap[row][c].ship != null) {
					return false;
				}
			}
			Ship ship = new Ship(row, col, row, maxCol);
			for (int c = col; c < maxCol; c++) {
				hitMap[row][c].ship = ship;
			}
		} else {
			int maxRow = row + SIZES.get(type);
			for (int r = row; r < maxRow; r++) {
				if (hitMap[r][col].ship != null) {
					return false;
				}
			}
			Ship ship = new Ship(row, col, maxRow, col);
			for (int r = row; r < maxRow; r++) {
				hitMap[r][col].ship = ship;
			}
		}
		shipCount++;
		return true;
	}

	boolean allShipSunk() {
		return shipCount == 0;
	}

	boolean isShipSunk(Ship ship) {
		for (int r = ship.startRow; r < ship.endRow; r++) {
			for (int c = ship.startCol; c < ship.endCol; c++) {
				if (!hitMap[r][c].hit) {
					return false;
				}
			}
		}
		return true;
	}
}
