import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

public class PacMan extends JPanel {
    class Block {
        int x;
        int y;
        int width;
        int height;
        Image image;

        int startX, startY;

        Block(Image image, int x, int y, int width, int height) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.startX = x;
            this.startY = y;
        }
    }
    private Image wallImage, blueGhostImage, pinkGhostImage, redGhostImage, orangeGhostImage;
    private Image pacManUpImage, pacManDownImage, pacManLeftImage, pacManRightImage;

    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    Block pacman;

    PacMan() {
        setPreferredSize(new Dimension(Constants.boardWidth, Constants.boardHeight));
        setBackground(Color.BLACK);

        // load images
        wallImage = new ImageIcon(getClass().getResource("./assets/wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("./assets/blueGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("./assets/orangeGhost.png")).getImage();
        pinkGhostImage = new ImageIcon(getClass().getResource("./assets/pinkGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("./assets/redGhost.png")).getImage();

        pacManUpImage = new ImageIcon(getClass().getResource("./assets/pacmanUp.png")).getImage();
        pacManDownImage = new ImageIcon(getClass().getResource("./assets/pacmanDown.png")).getImage();
        pacManLeftImage = new ImageIcon(getClass().getResource("./assets/pacmanLeft.png")).getImage();
        pacManRightImage = new ImageIcon(getClass().getResource("./assets/pacmanRight.png")).getImage();

        loadMap();
    }
    public void loadMap() {
        walls = new HashSet<Block>();
        foods = new HashSet<Block>();
        ghosts = new HashSet<Block>();

        for (int r=0; r<Constants.rowCount; r++) {
            for (int c=0; c<Constants.colCount; c++) {
                String row = Constants.tileMap[r];
                char tileMapChar = row.charAt(c);

                int x = c*Constants.tileSize;
                int y = r*Constants.tileSize;

                if (tileMapChar == 'X') {
                    Block wall = new Block(wallImage, x, y, Constants.tileSize, Constants.tileSize);
                    walls.add(wall);
                } else if (tileMapChar == 'b') {
                    Block ghost = new Block(blueGhostImage, x, y, Constants.tileSize, Constants.tileSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'o') {
                    Block ghost = new Block(orangeGhostImage, x, y, Constants.tileSize, Constants.tileSize);
                    ghosts.add(ghost);
                }  else if (tileMapChar == 'p') {
                    Block ghost = new Block(pinkGhostImage, x, y, Constants.tileSize, Constants.tileSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'r') {
                    Block ghost = new Block(redGhostImage, x, y, Constants.tileSize, Constants.tileSize);
                    ghosts.add(ghost);
                } else if (tileMapChar == 'P') {
                    pacman = new Block(pacManRightImage, x,y,Constants.tileSize, Constants.tileSize);
                } else if (tileMapChar == ' ') {
                    Block food = new Block(null, x+14, y+14, 14, 4);
                    foods.add(food);
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(pacman.image, pacman.x, pacman.y, pacman.width, pacman.height, null);

        for (Block ghost : ghosts) {
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost
                    .height, null);
        }

        for (Block wall: walls) {
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }
    }
}
