import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    Game GAME;
    GameScreen(Game root) {
        GAME = root;
        this.setPreferredSize(new Dimension(800, 800));
        this.setBackground(Color.black);
    }
}
