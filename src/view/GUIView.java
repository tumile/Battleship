package view;

import model.Position;
import model.ShipType;
import model.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUIView implements View {

	private Container pane;

	public GUIView() {
		JFrame screen = new JFrame();
		screen.setTitle("Battleship");
		screen.setSize(720, 480);
		screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screen.setVisible(true);
		pane = screen.getContentPane();
	}

	@Override
	public Position renderPlaceShip(String player, Tile[][] map, ShipType type, String msg) {
		Board board = new Board();
		pane.add(new Board(50));
		while (true) {

		}
	}

	@Override
	public Position renderAttack(String player, Tile[][] map, String msg) {
		return null;
	}

	@Override
	public void renderWinner(String player) {

	}

	private void draw(Tile[][] map, boolean showShips) {

	}

	class Board extends JPanel {

		private static final int TILE_SIZE = 30;

		public Board() {
			setPreferredSize(new Dimension(TILE_SIZE * 11, TILE_SIZE * 11));
			setLayout(new GridBagLayout());

			GridBagConstraints cons = new GridBagConstraints();
			Dimension dim = new Dimension(TILE_SIZE, TILE_SIZE);

			for (int i = 1; i < 11; i++) {
				JLabel hor = new JLabel(String.valueOf(i), JLabel.CENTER);
				hor.setPreferredSize(dim);
				cons.gridx = i;
				cons.gridy = 0;
				add(hor, cons);

				JLabel ver = new JLabel(Character.toString(64 + i), JLabel.CENTER);
				ver.setPreferredSize(dim);
				cons.gridx = 0;
				cons.gridy = i;
				add(ver, cons);
			}

			cons.gridx = 1;
			cons.gridy = 1;
			cons.gridwidth = 10;
			cons.gridheight = 10;
			add(new Grid(), cons);
		}

		class Grid extends JPanel {

			public Grid() {
				setPreferredSize(new Dimension(TILE_SIZE * 10, TILE_SIZE * 10));
				addMouseListener(new MouseInputAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);
						int row = e.getY() / TILE_SIZE, col = e.getX() / TILE_SIZE;

					}
				});
			}

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				try {
					BufferedImage image = ImageIO.read(new File("surface.jpg"));
					g.drawImage(image, 0, 0, TILE_SIZE * 10, TILE_SIZE * 10, null, null);
					for (int col = 0; col < 10; col++) {
						for (int row = 0; row < 10; row++) {
							g.setColor(Color.WHITE);
							g.drawRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
