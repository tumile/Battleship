package view;

import model.Orientation;
import model.Position;
import model.ShipType;
import model.Tile;

import java.util.Arrays;
import java.util.Scanner;

public class TextView implements View {

	@Override
	public Position renderPlaceShip(String player, Tile[][] map, ShipType type, String msg) {
		Scanner scanner = new Scanner(System.in);

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
				return new Position(
						input[0].charAt(0) - 'a',
						Integer.parseInt(input[1]),
						input[2].equals("h") ? Orientation.HORIZONTAL : Orientation.VERTICAL
				);
			}

			msg = "Invalid input";
		}
	}

	@Override
	public Position renderAttack(String player, Tile[][] map, String msg) {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			clearOutput();

			System.out.printf("%s's turn\n", player);
			draw(map, false);

			if (msg != null) {
				System.out.println(msg);
			}

			System.out.println("Choose your next attack");
			System.out.print("Row (A - J), column (0 - 9) -- e.g: A,5  ");

			String s = scanner.nextLine();
			String[] input = Arrays.stream(s.split(",")).map(String::toLowerCase).toArray(String[]::new);

			if (validateAttackPositionInput(input)) {
				return new Position(
						input[0].charAt(0) - 'a',
						Integer.parseInt(input[1]),
						null
				);
			}

			msg = "Invalid input";
		}
	}

	@Override
	public void renderWinner(String player) {
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
		for (int r = 0; r < 11; r++) {
			for (int c = 0; c < 11; c++) {
				if (r == 0 && c == 0) {
					System.out.print(" ");
				} else if (r == 0) {
					System.out.print("  " + (c - 1));
				} else if (c == 0) {
					System.out.print(Character.toString(64 + r) + " ");
				} else {
					String s;
					if (map[r - 1][c - 1].isHit) {
						if (map[r - 1][c - 1].ship == null) {
							s = "❌";
						} else {
							s = "\uD83D\uDCA5";
						}
					} else if (map[r - 1][c - 1].ship != null) {
						s = showShips ? "\uD83D\uDEA2" : "\uD83C\uDF0A";
					} else {
						s = "\uD83C\uDF0A";
					}
					System.out.print(s);
				}
			}
			System.out.println();
		}
	}


//	public void draw(Tile[][] map1, Tile[][] map2, boolean showShips) {
//		for (int r = 0; r < 11; r++) {
//			for (int c = 0; c < 11; c++) {
//				if (r == 0 && c == 0) {
//					System.out.print(" ");
//				} else if (r == 0) {
//					System.out.print(c - 1);
//				} else if (c == 0) {
//					System.out.print(Character.toString(64 + r));
//				} else {
//					String s;
//					if (map[r - 1][c - 1].isHit) {
//						if (map[r - 1][c - 1].ship == null) {
//							s = "x";
//						} else {
//							s = "*";
//						}
//					} else if (map[r - 1][c - 1].ship != null) {
//						s = showShips ? "o" : "~";
//					} else {
//						s = "~";
//					}
//					System.out.print(s);
//				}
//			}
//			System.out.println();
//		}
//	}
}
