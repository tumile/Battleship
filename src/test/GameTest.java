package test;

import controller.Game;
import view.View;
import model.Orientation;
import model.Position;
import model.ShipType;
import model.Tile;
import org.junit.Before;
import org.junit.Test;

public class GameTest {

    @Before
    public void setup() {
        new Game(new TestView());
    }

    @Test
    public void gameOver() {
        // if this test passes, it means the game executed successfully with TestView
    }

    static class TestView implements View {

        private int placeShipCount = 0;
        private int attackCount = 0;

        @Override
        public Position renderPlaceShip(String player, Tile[][] map, ShipType type, String msg) {
            return new Position(placeShipCount++, 0, Orientation.HORIZONTAL);
        }

        @Override
        public Position renderAttack(String player, Tile[][] map1, Tile[][] map2, String msg) {
            int row = attackCount / 10, col = attackCount % 10;
            attackCount++;
            return new Position(row, col, Orientation.HORIZONTAL);
        }

        @Override
        public void renderWinner(String player) {

        }
    }
}