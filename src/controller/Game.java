package controller;

import model.Board;
import util.Constants;

public class Game {

    private Board board1, board2;

    private View view;

    public Game(View view) {
        this.view = view;

        board1 = new Board();
        board1.addObserver(view);

        board2 = new Board();
        board2.addObserver(view);

        prepareBoards();

        boolean player = Constants.P1;

        while (true) {
            ArbitraryPosition pos = view.getNextMove(player);
            if (player == Constants.P1) {
                board2.hit(pos.row, pos.col);
                if (board2.allShipsSunk()) {
                    view.announceWinner(Constants.P1);
                    break;
                }
            } else {
                board1.hit(pos.row, pos.col);
                if (board1.allShipsSunk()) {
                    view.announceWinner(Constants.P2);
                    break;
                }
            }
        }
    }

    private void prepareBoards() {
        for (int ship : Constants.SHIPS) {
            while (true) {
                ArbitraryPosition pos = view.getShipPosition(Constants.P1, ship);
                if (board1.setShip(pos.row, pos.col, pos.orient, ship)) {
                    break;
                }
            }
        }
        for (int ship : Constants.SHIPS) {
            while (true) {
                ArbitraryPosition pos = view.getShipPosition(Constants.P2, ship);
                if (board2.setShip(pos.row, pos.col, pos.orient, ship)) {
                    break;
                }
            }
        }
    }
}
