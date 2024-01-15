import javax.swing.JPanel;
import java.awt.*;

public class GameScreen extends JPanel {
    Game GAME;
    GameScreen(Game root) {
        GAME = root;
        this.setPreferredSize(new Dimension(800, 800));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    public void addTile(Tile object) {
        this.add(object);
    }
}
