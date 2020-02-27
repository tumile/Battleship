import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Grid extends JPanel {

    private int tileSize;

    public Grid(int tileSize) {
        this.tileSize = tileSize;

        setPreferredSize(new Dimension(tileSize * 10, tileSize * 10));

        addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = e.getY() / tileSize, col = e.getX() / tileSize;
                Graphics g = getGraphics();
                g.setColor(Color.RED);
                g.fillOval(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        try {
            BufferedImage image = ImageIO.read(new File("surface.jpg"));
            g.drawImage(image, 0, 0, tileSize * 10, tileSize * 10, null, null);
            for (int col = 0; col < 10; col++) {
                for (int row = 0; row < 10; row++) {
                    g.setColor(Color.WHITE);
                    g.drawRect(col * tileSize, row * tileSize, tileSize, tileSize);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Board extends JPanel {

    public Board(int tileSize) {
        setPreferredSize(new Dimension(tileSize * 11, tileSize * 11));
        setLayout(new GridBagLayout());

        GridBagConstraints cons = new GridBagConstraints();

        for (int i = 1; i < 11; i++) {
            Dimension dim = new Dimension(tileSize, tileSize);

            JLabel hor = new JLabel(String.valueOf(i), JLabel.CENTER);
            hor.setPreferredSize(dim);
            cons.gridx = i;
            cons.gridy = 0;
            add(hor, cons);

            JLabel ver = new JLabel(Character.toString('A' + i - 1), JLabel.CENTER);
            ver.setPreferredSize(dim);
            cons.gridx = 0;
            cons.gridy = i;
            add(ver, cons);
        }

        cons.gridx = 1;
        cons.gridy = 1;
        cons.gridwidth = 10;
        cons.gridheight = 10;
        add(new Grid(tileSize), cons);
    }
}

public class Game {

    public static void main(String[] args) {
        JPanel mainPanel = new JPanel();
        mainPanel.add(new Board(40));
        mainPanel.add(new Board(40));

        JFrame frame = new JFrame();
        frame.setTitle("Battleship");
        frame.setSize(1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
