import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

public class PacMan extends JPanel implements ActionListener, KeyListener {
    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyPressed(KeyEvent keyEvent) {}

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        //System.out.println("KeyEvent: " + keyEvent.getKeyCode());
        if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
            pacman.updateDirection('U');
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
            pacman.updateDirection('D');
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
            pacman.updateDirection('L');
        } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
            pacman.updateDirection('R');
        }
    }

    class Block {
        int x;
        int y;
        int width;
        int height;
        Image image;

        int startX, startY;
        char direction = 'U'; // U D L R
        int velocityX = 0;
        int velocityY = 0;

        Block(Image image, int x, int y, int width, int height) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.startX = x;
            this.startY = y;
        }

        void updateDirection(char direction) {
            char prevDirection = this.direction;
            this.direction = direction;
            updateVelocity();
            this.x += this.velocityX;
            this.y += this.velocityY;
            for (Block wall: walls) {
                if (collisions(this, wall)) {
                    this.x -= this.velocityX;
                    this.y -= this.velocityY;
                    this.direction = prevDirection;
                    updateVelocity();
                }
            }
        }

        void updateVelocity() {
            if (this.direction == 'U') {
                this.velocityX = 0;
                this.velocityY = -Constants.tileSize/4;
            } else if (this.direction == 'D') {
                this.velocityX = 0;
                this.velocityY = Constants.tileSize/4;
            } else if (this.direction == 'L') {
                this.velocityY = 0;
                this.velocityX = -Constants.tileSize/4;
            } else if (this.direction == 'R') {
                this.velocityY = 0;
                this.velocityX = Constants.tileSize/4;
            }
        }
    }
    private Image wallImage, blueGhostImage, pinkGhostImage, redGhostImage, orangeGhostImage;
    private Image pacManUpImage, pacManDownImage, pacManLeftImage, pacManRightImage;

    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    Block pacman;

    Timer gameLoop;

    PacMan() {
        setPreferredSize(new Dimension(Constants.boardWidth, Constants.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

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
        gameLoop = new Timer(50, this);
        gameLoop.start();
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
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
        }

        for (Block wall: walls) {
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }
        g.setColor(Color.white);
        for (Block food: foods) {
            g.fillRect(food.x, food.y, food.width, food.height);
        }
    }
    public void move() {
        pacman.x += pacman.velocityX;
        pacman.y += pacman.velocityY;

        for (Block wall: walls) {
            if (collisions(pacman, wall)) {
                pacman.x -= pacman.velocityX;
                pacman.y -= pacman.velocityY;
                break;
            }
        }
    }

    public boolean collisions(Block a, Block b) {
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }
}
