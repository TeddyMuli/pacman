import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

public class PacMan extends JPanel {
    PacMan() {
        setPreferredSize(new Dimension(Constants.boardWidth, Constants.boardHeight));
        setBackground(Color.BLACK);
    }
}
