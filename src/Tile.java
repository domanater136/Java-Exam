import javax.swing.*;
import java.awt.*;

public class Tile extends JButton {
    int[] POS;
    boolean LOCKED = false;
    Tile(int[] position){
        POS = position;
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBackground(Color.blue);
        this.setRolloverEnabled(true);

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt){
                if (!LOCKED){
                    Tile tile = (Tile)evt.getComponent();
                    tile.setOpaque(true);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt){
                if (!LOCKED){
                    Tile tile = (Tile)evt.getComponent();
                    tile.setOpaque(false);
                }
            }
        });
        // this.setBorderPainted(false);
    }

}
