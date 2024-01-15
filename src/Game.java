import javax.swing.*;
import java.awt.*;

public class Game {
    Player[] PLAYERS = new Player[2];
    Board[] BOARDS = new Board[2];
    JFrame ROOT = new JFrame();
    GameScreen GAMESCREEN;
    Game() {
        ROOT.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ROOT.setResizable(false);
        ROOT.setTitle("Video Gaming");
        ROOT.setPreferredSize(new Dimension(800, 800));

        GAMESCREEN = new GameScreen(this);
        PLAYERS[0] = new Player(0, this);
        PLAYERS[1] = new Player(1, this);
        BOARDS[0] = PLAYERS[0].getBoard();
        BOARDS[1] = PLAYERS[1].getBoard();

        ROOT.add(GAMESCREEN);
        ROOT.pack();
        //root.validate();

        ROOT.setLocationRelativeTo(null);
        ROOT.setVisible(true);
    }
    public void addTile(Tile obj){
        ROOT.add(obj);
    }
}
