package view;

import controller.ArbitraryPosition;
import controller.View;
import model.Tile;
import util.Constants;

import java.util.Observable;
import java.util.Scanner;

public class TextView implements View {

    private Tile[][] map;

    @Override
    public ArbitraryPosition getShipPosition(boolean player, int shipType) {
        String playerName = player == Constants.P1 ? "Player 1" : "Player 2";
        String shipName;
        switch (shipType) {
            case Constants.CARRIER:
                shipName = "Carrier";
                break;
            case Constants.BATTLESHIP:
                shipName = "Battleship";
                break;
            case Constants.CRUISER:
                shipName = "Cruiser";
                break;
            case Constants.SUBMARINE:
                shipName = "Submarine";
                break;
            default:
                shipName = "Destroyer";
                break;
        }
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.printf("%s, choose your %s position\n", playerName, shipName);
            System.out.print("Row (A - J), column (1 - 10), orientation (H or V) -- e.g: C,2,V  ");
            String s = scanner.nextLine();
            String[] input = s.split(",");
            if (validateShipPositionInput(input)) {
                ArbitraryPosition pos = new ArbitraryPosition();
                pos.row = input[0].charAt(0) - 'A';
                pos.col = Integer.parseInt(input[1]) - 1;
                pos.orient = input[2].equals("H") ? Constants.HORIZONTAL : Constants.VERTICAL;
                return pos;
            }
            System.out.println("Invalid input! Please follow the given instruction.");
        }
    }

    private boolean validateShipPositionInput(String[] input) {
        if (input.length != 3) {
            return false;
        }
        String row = input[0];
        if (row.length() != 1 || row.charAt(0) < 'A' || row.charAt(0) > 'J') {
            return false;
        }
        try {
            int col = Integer.parseInt(input[1]);
            if (col < 1 || col > 10) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        String orient = input[2];
        if (orient.length() != 1 || (orient.charAt(0) != 'H' && orient.charAt(0) != 'V')) {
            return false;
        }
        return true;
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
        for (int r = 0; r < 11; r++) {
            for (int c = 0; c < 11; c++) {
                if (r == 0 && c == 0) {
                    System.out.print(" ");
                } else if (r == 0) {
                    System.out.print(c);
                } else if (c == 0) {
                    System.out.print(Character.toString(64 + r));
                } else {
                    String s;
                    if (map[r][c].isHit) {
                        s = "x";
                    } else if (map[r][c].ship != null) {
                        if (map[r][c].ship.orient == Constants.HORIZONTAL) {
                            s = "-";
                        } else {
                            s = "|";
                        }
                    } else {
                        s = "~";
                    }
                    System.out.print(s);
                }
            }
            System.out.println();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        map = (Tile[][]) arg;
        draw();
    }
}
