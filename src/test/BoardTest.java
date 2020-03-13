package test;

import model.Board;
import model.constant.Orientation;
import model.constant.ShipType;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class BoardTest {

    @Test
    public void placeShip() {
        Board board = new Board();
        assertFalse(board.placeShip(-1, 0, Orientation.HORIZONTAL, ShipType.BATTLESHIP));
        assertFalse(board.placeShip(0, -1, Orientation.HORIZONTAL, ShipType.BATTLESHIP));
        assertFalse(board.placeShip(10, 0, Orientation.HORIZONTAL, ShipType.BATTLESHIP));
        assertFalse(board.placeShip(0, 10, Orientation.HORIZONTAL, ShipType.BATTLESHIP));

        assertTrue(board.placeShip(0, 0, Orientation.HORIZONTAL, ShipType.CARRIER));
        assertFalse(board.placeShip(0, 0, Orientation.HORIZONTAL, ShipType.CRUISER));

        assertTrue(board.placeShip(1, 0, Orientation.HORIZONTAL, ShipType.CRUISER));
        assertTrue(board.placeShip(2, 0, Orientation.HORIZONTAL, ShipType.BATTLESHIP));
        assertTrue(board.placeShip(3, 0, Orientation.HORIZONTAL, ShipType.CRUISER));
        assertTrue(board.placeShip(4, 0, Orientation.HORIZONTAL, ShipType.DESTROYER));
    }

    @Test
    public void attack() {
        Board board = new Board();
        board.placeShip(0, 0, Orientation.HORIZONTAL, ShipType.BATTLESHIP);

        assertFalse(board.attack(-1, 0));
        assertFalse(board.attack(0, -1));
        assertFalse(board.attack(10, 0));
        assertFalse(board.attack(0, 10));

        assertTrue(board.attack(0, 0));
        assertFalse(board.attack(0, 0));


        assertTrue(board.attack(0, 1));
        assertTrue(board.attack(0, 2));
        assertTrue(board.attack(0, 3));
        assertTrue(board.attack(0, 4));

        assertTrue(board.isLost());
    }
}