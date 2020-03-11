package test;

import controller.Game;
import view.View;
import model.constant.Orientation;
import model.constant.Player;
import model.constant.ShipType;
import model.Tile;
import org.junit.Before;
import org.junit.Test;

public class GameTest {

    @Before
    public void setup() {
        Game game = new Game();
        View view = new TestView();
        view.setController(game);
        game.setView(view);
        game.start();
    }

    @Test
    public void gameOver() {
        // if this test passes, it means the game executed successfully with TestView
    }

    static class TestView extends View {

        private int placeShipCount1 = 0;
        private int placeShipCount2 = 0;

        private int attackCount1 = 0;
        private int attackCount2 = 0;

        @Override
        public void renderPlaceShip(Player player, ShipType type, Tile[][] map, String msg) {
            int row = player == Player.Player1 ? placeShipCount1++ : placeShipCount2++;
            controller.placeShip(player, row, 0, Orientation.HORIZONTAL, type);
        }

        @Override
        public void renderAttack(Player player, Tile[][] map1, Tile[][] map2, String msg) {
            int row, col;
            if (player == Player.Player1) {
                row = attackCount1 / 10;
                col = attackCount1 % 10;
                attackCount1++;
            } else {
                row = attackCount2 / 10;
                col = attackCount2 % 10;
                attackCount2++;
            }
            controller.attack(player, row, col);
        }

        @Override
        public void renderWinner(Player player) {
        }
    }
}