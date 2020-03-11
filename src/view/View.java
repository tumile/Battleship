package view;

import controller.Game;
import model.constant.Player;
import model.constant.ShipType;
import model.Tile;

public abstract class View {

    protected Game controller;

    public void setController(Game controller) {
        this.controller = controller;
    }

    public abstract void renderPlaceShip(Player player, ShipType type, Tile[][] map, String msg);

    public abstract void renderAttack(Player player, Tile[][] map1, Tile[][] map2, String msg);

    public abstract void renderWinner(Player player);
}
