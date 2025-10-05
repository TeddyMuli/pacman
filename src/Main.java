import javax.swing.JFrame;
public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Pac Man");
        frame.setSize(Constants.boardWidth, Constants.boardHeight);
        frame.setLocationRelativeTo(null); //appear at center
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PacMan pacmanGame = new PacMan();
        frame.add(pacmanGame);
        frame.pack();
        pacmanGame.requestFocus();
        frame.setVisible(true);
    }
}
