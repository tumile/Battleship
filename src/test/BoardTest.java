package test;

import model.Board;
import model.Orientation;
import model.Position;
import model.ShipType;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class BoardTest {

    @Test
    public void placeShip() {
        Board board = new Board();
        assertFalse(board.placeShip(new Position(-1, 0, Orientation.HORIZONTAL), ShipType.BATTLESHIP));
        assertFalse(board.placeShip(new Position(0, -1, Orientation.HORIZONTAL), ShipType.BATTLESHIP));
        assertFalse(board.placeShip(new Position(10, 0, Orientation.HORIZONTAL), ShipType.BATTLESHIP));
        assertFalse(board.placeShip(new Position(0, 10, Orientation.HORIZONTAL), ShipType.BATTLESHIP));

        assertTrue(board.placeShip(new Position(0, 0, Orientation.HORIZONTAL), ShipType.CARRIER));
        assertFalse(board.placeShip(new Position(0, 0, Orientation.HORIZONTAL), ShipType.CRUISER));

        assertTrue(board.placeShip(new Position(1, 0, Orientation.HORIZONTAL), ShipType.CRUISER));
        assertTrue(board.placeShip(new Position(2, 0, Orientation.HORIZONTAL), ShipType.BATTLESHIP));
        assertTrue(board.placeShip(new Position(3, 0, Orientation.HORIZONTAL), ShipType.CRUISER));
        assertTrue(board.placeShip(new Position(4, 0, Orientation.HORIZONTAL), ShipType.DESTROYER));
    }

    @Test
    public void attack() {
        Board board = new Board();
        board.placeShip(new Position(0, 0, Orientation.HORIZONTAL), ShipType.BATTLESHIP);

        assertFalse(board.attack(new Position(-1, 0, null)));
        assertFalse(board.attack(new Position(0, -1, null)));
        assertFalse(board.attack(new Position(10, 0, null)));
        assertFalse(board.attack(new Position(0, 10, null)));

        assertTrue(board.attack(new Position(0, 0, null)));
        assertFalse(board.attack(new Position(0, 0, null)));


        assertTrue(board.attack(new Position(0, 1, null)));
        assertTrue(board.attack(new Position(0, 2, null)));
        assertTrue(board.attack(new Position(0, 3, null)));
        assertTrue(board.attack(new Position(0, 4, null)));

        assertTrue(board.lost());
    }
}