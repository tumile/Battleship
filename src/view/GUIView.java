package view;

import controller.ArbitraryPosition;
import controller.View;
import model.Tile;

import java.util.Observable;

public class GUIView implements View {

    private Tile[][] tiles;

    @Override
    public ArbitraryPosition getShipPosition(boolean player, int shipType) {
        return null;
    }

    @Override
    public ArbitraryPosition getNextMove(boolean player) {
        return null;
    }

    @Override
    public void announceWinner(boolean player) {

    }

    @Override
    public void draw() {

    }

    @Override
    public void update(Observable o, Object arg) {
        tiles = (Tile[][]) arg;
        draw();
    }
}
