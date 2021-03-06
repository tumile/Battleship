package view;

import model.Tile;
import model.constant.Orientation;
import model.constant.Player;
import model.constant.ShipType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class GUIView extends View {

    private static final int TILE_SIZE = 30;

    private JFrame screen;
    private Container pane;

    public GUIView() {
        screen = new JFrame();
        screen.setTitle("Battleship");
        screen.setSize(720, 480);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.setVisible(true);

        pane = screen.getContentPane();
    }

    @Override
    public void renderPlaceShip(Player player, ShipType type, Tile[][] map, String msg) {
        AtomicReference<Orientation> orient = new AtomicReference<>(Orientation.HORIZONTAL);

        pane.removeAll();

        // Create top labels
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

        JLabel playerLabel = new JLabel(player + "'s turn");
        playerLabel.setFont(new Font(null, Font.PLAIN, 22));
        playerLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        JLabel shipLabel = new JLabel("Choose your " + type.name() + "'s position");
        shipLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        labelPanel.add(playerLabel);
        labelPanel.add(shipLabel);

        pane.add(labelPanel, BorderLayout.PAGE_START);

        // Create board
        Board board = new Board(map, true, false);
        board.setListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / TILE_SIZE;
                int col = e.getX() / TILE_SIZE;
                controller.placeShip(player, row, col, orient.get(), type);
            }
        });

        pane.add(board, BorderLayout.CENTER);

        // Create orientation radio buttons
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));

        JRadioButton horButton = new JRadioButton("Horizontal", true);
        horButton.addActionListener(e -> orient.set(Orientation.HORIZONTAL));

        JRadioButton verButton = new JRadioButton("Vertical");
        verButton.addActionListener(e -> orient.set(Orientation.VERTICAL));

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(horButton);
        buttonGroup.add(verButton);

        radioPanel.add(horButton);
        radioPanel.add(verButton);

        pane.add(radioPanel, BorderLayout.LINE_START);

        // Create message
        if (msg != null) {
            JLabel msgLabel = new JLabel(msg, JLabel.CENTER);
            msgLabel.setFont(new Font(null, Font.PLAIN, 20));
            pane.add(msgLabel, BorderLayout.PAGE_END);
        }

        screen.validate();
        screen.repaint();
    }

    @Override
    public void renderAttack(Player player, Tile[][] map1, Tile[][] map2, String msg) {
        pane.removeAll();

        // Create top labels
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

        JLabel playerLabel = new JLabel(player + "'s turn");
        playerLabel.setFont(new Font(null, Font.PLAIN, 22));
        playerLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        JLabel promptLabel = new JLabel("Choose your next attack");
        promptLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        labelPanel.add(playerLabel);
        labelPanel.add(promptLabel);

        pane.add(labelPanel, BorderLayout.PAGE_START);

        // Create boards
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new FlowLayout());

        Board board1 = new Board(map1, false, player == Player.Player1);
        Board board2 = new Board(map2, false, player == Player.Player2);

        (player == Player.Player1 ? board2 : board1).setListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / TILE_SIZE;
                int col = e.getX() / TILE_SIZE;
                controller.attack(player, row, col);
            }
        });

        boardPanel.add(board1);
        boardPanel.add(board2);

        pane.add(boardPanel, BorderLayout.CENTER);

        // Create message
        if (msg != null) {
            JLabel msgLabel = new JLabel(msg, JLabel.CENTER);
            msgLabel.setFont(new Font(null, Font.PLAIN, 20));
            pane.add(msgLabel, BorderLayout.PAGE_END);
        }

        screen.validate();
        screen.repaint();
    }

    @Override
    public void renderWinner(Player player) {
        JOptionPane.showConfirmDialog(null, player + " has won!", "", JOptionPane.OK_CANCEL_OPTION);
        screen.dispose();
    }

    private static class Board extends JPanel {

        private JPanel grid;

        public Board(Tile[][] map, boolean showShips, boolean disabled) {
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

            grid = new JPanel() {
                @Override
                public void paint(Graphics g) {
                    super.paint(g);

                    try {
                        Image image = ImageIO.read(GUIView.class.getResource("surface.jpg"));
                        g.drawImage(image, 0, 0, TILE_SIZE * 10, TILE_SIZE * 10, null, null);

                        for (int col = 0; col < 10; col++) {
                            for (int row = 0; row < 10; row++) {
                                if (map[row][col].ship != null) {
                                    if (map[row][col].isHit) {
                                        g.setColor(Color.RED);
                                        g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                                    } else if (showShips) {
                                        g.setColor(Color.BLACK);
                                        g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                                    }
                                } else if (map[row][col].isHit) {
                                    g.setColor(Color.GRAY);
                                    g.fillRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                                }

                                g.setColor(Color.WHITE);
                                g.drawRect(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                            }
                        }

                        if (disabled) {
                            ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                            g.setColor(Color.LIGHT_GRAY);
                            g.fillRect(0, 0, TILE_SIZE * 10, TILE_SIZE * 10);
                        }
                    } catch (IOException ignored) {
                    }
                }
            };

            grid.setPreferredSize(new Dimension(TILE_SIZE * 10, TILE_SIZE * 10));

            cons.gridx = 1;
            cons.gridy = 1;
            cons.gridwidth = 10;
            cons.gridheight = 10;
            add(grid, cons);
        }

        void setListener(MouseListener listener) {
            grid.addMouseListener(listener);
        }
    }
}
