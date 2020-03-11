package view;

import model.constant.Orientation;
import model.constant.Player;
import model.constant.ShipType;
import model.Tile;

import java.util.Arrays;
import java.util.Scanner;

public class TextView extends View {

    @Override
    public void renderPlaceShip(Player player, ShipType type, Tile[][] map, String msg) {
        Scanner scanner = new Scanner(System.in);

        int row, col;
        Orientation orient;

        while (true) {
            clearOutput();

            System.out.printf("%s's turn\n", player);
            draw(map, true);

            if (msg != null) {
                System.out.println(msg);
            }

            System.out.printf("Choose your %s's position\n", type.name());
            System.out.print("Row (A - J), column (0 - 9), orientation (H or V) -- e.g: C,2,V  ");

            String s = scanner.nextLine();
            String[] input = Arrays.stream(s.split(",")).map(String::toLowerCase).toArray(String[]::new);

            if (validateShipPositionInput(input)) {
                row = input[0].charAt(0) - 'a';
                col = Integer.parseInt(input[1]);
                orient = input[2].equals("h") ? Orientation.HORIZONTAL : Orientation.VERTICAL;
                break;
            }

            msg = "Invalid input";
        }

        controller.placeShip(player, row, col, orient, type);
    }

    @Override
    public void renderAttack(Player player, Tile[][] map1, Tile[][] map2, String msg) {
        Scanner scanner = new Scanner(System.in);

        int row, col;

        while (true) {
            clearOutput();

            System.out.printf("%s's turn\n", player);
            draw(map1, map2, false);

            if (msg != null) {
                System.out.println(msg);
            }

            System.out.println("Choose your next attack");
            System.out.print("Row (A - J), column (0 - 9) -- e.g: A,5  ");

            String s = scanner.nextLine();
            String[] input = Arrays.stream(s.split(",")).map(String::toLowerCase).toArray(String[]::new);

            if (validateAttackPositionInput(input)) {
                row = input[0].charAt(0) - 'a';
                col = Integer.parseInt(input[1]);
                break;
            }

            msg = "Invalid input";
        }

        controller.attack(player, row, col);
    }

    @Override
    public void renderWinner(Player player) {
        System.out.printf("%s has won the game!\n", player);
    }

    private void clearOutput() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    private boolean validateShipPositionInput(String[] input) {
        if (input.length != 3) {
            return false;
        }
        String orient = input[2];
        if (!orient.equals("h") && !orient.equals("v")) {
            return false;
        }
        return validatePosition(input[0], input[1]);
    }

    private boolean validateAttackPositionInput(String[] input) {
        if (input.length != 2) {
            return false;
        }
        return validatePosition(input[0], input[1]);
    }

    private boolean validatePosition(String row, String col) {
        if (row.length() != 1 || row.charAt(0) < 'a' || row.charAt(0) > 'j') {
            return false;
        }
        try {
            int n = Integer.parseInt(col);
            if (n < 0 || n > 9) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void draw(Tile[][] map, boolean showShips) {
        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 11; col++) {
                if (row == 0 && col == 0) {
                    System.out.print(" ");
                } else if (row == 0) {
                    System.out.print(" " + (col - 1));
                } else if (col == 0) {
                    System.out.print(Character.toString(64 + row) + " ");
                } else {
                    System.out.print(getSymbol(map[row - 1][col - 1], showShips));
                }
            }
            System.out.println();
        }
    }

    public void draw(Tile[][] map1, Tile[][] map2, boolean showShips) {
        for (int row = 0; row < 11; row++) {
            System.out.print(" ");
            for (int col = 0; col < 11; col++) {
                if (row == 0 && col == 0) {
                    System.out.print(" ");
                } else if (row == 0) {
                    System.out.print(" " + (col - 1));
                } else if (col == 0) {
                    System.out.print(Character.toString(64 + row) + " ");
                } else {
                    System.out.print(getSymbol(map1[row - 1][col - 1], showShips));
                }
            }
            System.out.print("  ");
            for (int col = 0; col < 11; col++) {
                if (row == 0 && col == 0) {
                    System.out.print(" ");
                } else if (row == 0) {
                    System.out.print(" " + (col - 1));
                } else if (col == 0) {
                    System.out.print(Character.toString(64 + row) + " ");
                } else {
                    System.out.print(getSymbol(map2[row - 1][col - 1], showShips));
                }
            }
            System.out.println();
        }
    }

    private String getSymbol(Tile tile, boolean showShips) {
        String s;
        if (tile.ship != null) {
            if (tile.isHit) {
                s = "\uD83D\uDCA5";
            } else if (showShips) {
                s = "\uD83D\uDEA2";
            } else {
                s = "\uD83C\uDF0A";
            }
        } else if (tile.isHit) {
            s = "❌";
        } else {
            s = "\uD83C\uDF0A";
        }
        return s;
    }
}
