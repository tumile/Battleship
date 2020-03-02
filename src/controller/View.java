package controller;

import java.util.Observer;

public interface View extends Observer {

    ArbitraryPosition getShipPosition(boolean player, int shipType);

    ArbitraryPosition getNextMove(boolean player);

    void announceWinner(boolean player);

    void draw();
}
