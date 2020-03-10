package view;

import model.Position;
import model.ShipType;
import model.Tile;

public interface View {

	Position renderPlaceShip(String player, Tile[][] map, ShipType type, String msg);

	Position renderAttack(String player, Tile[][] map, String msg);

	void renderWinner(String player);
}
