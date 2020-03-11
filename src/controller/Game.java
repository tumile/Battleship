package controller;

import model.Board;
import model.constant.Orientation;
import model.constant.Player;
import model.constant.ShipType;
import view.View;

public class Game {

    private Board board1;
    private Board board2;

    private View view;

    public Game() {
        board1 = new Board();
        board2 = new Board();
    }

    public void setView(View view) {
        this.view = view;
    }

    public void start() {
        view.renderPlaceShip(Player.Player1, board1.getNextShip(), board1.map, null);
    }

    public void placeShip(Player player, int row, int col, Orientation orient, ShipType type) {
        Board board = player == Player.Player1 ? board1 : board2;
        boolean ok = board.placeShip(row, col, orient, type);
        if (!ok) {
            view.renderPlaceShip(player, type, board.map, "You cannot place ship here");
        } else if (!board.isReady()) {
            view.renderPlaceShip(player, board.getNextShip(), board.map, null);
        } else if (player == Player.Player1) {
            view.renderPlaceShip(Player.Player2, board2.getNextShip(), board2.map, null);
        } else {
            view.renderAttack(Player.Player1, board1.map, board2.map, null);
        }
    }

    public void attack(Player player, int row, int col) {
        Board board = player == Player.Player1 ? board2 : board1;
        boolean ok = board.attack(row, col);
        if (!ok) {
            view.renderAttack(player, board1.map, board2.map, "You've already attacked this position");
        } else if (board.isLost()) {
            view.renderWinner(player);
        } else {
            view.renderAttack(player == Player.Player1 ? Player.Player2 : Player.Player1, board1.map, board2.map, null);
        }
    }
}
