package controller;

import model.Board;
import model.Position;
import model.ShipType;
import view.View;

public class Game {

	private String player1 = "Player 1";
	private String player2 = "Player 2";

	private Board board1;
	private Board board2;

	private View view;

	public Game(View view) {
		board1 = new Board();
		board2 = new Board();
		this.view = view;

		prepareBoards();
		startGame();
	}

	private void prepareBoards() {
		for (ShipType shipType : ShipType.values()) {
			setShip(player1, board1, shipType);
		}

		for (ShipType shipType : ShipType.values()) {
			setShip(player2, board2, shipType);
		}
	}

	private void setShip(String player, Board board, ShipType type) {
		String msg = null;
		boolean ok = false;

		while (!ok) {
			Position pos = view.renderPlaceShip(player, board.map, type, msg);
			ok = board.placeShip(pos, type);
			msg = "Ship cannot be placed here";
		}
	}

	private void startGame() {
		String player;
		boolean turn = true;

		while (true) {
			player = turn ? player1 : player2;
			Board board = turn ? board2 : board1;

			String msg = null;
			boolean ok = false;

			while (!ok) {
				Position pos = view.renderAttack(player, board.map, msg);
				ok = board.attack(pos);
				msg = "You've already attacked this position";
			}

			if (board.lost()) {
				break;
			}

			turn = !turn;
		}

		view.renderWinner(player);
	}
}
