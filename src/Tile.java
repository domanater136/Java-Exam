import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Tile extends JButton {
    Game GAME;
    int[] POS;
    boolean LOCKED = false;
    Icon IMAGE;
    // Values are: Defence, Attack, Delay, Unit Number, Power, Color (1 = Red, 2 = Blue, 3 = Green), Idle.
    // Values are: Defence, Attack, Delay, Unit Number
    int[] STATS = {0,0,0,3,0,0,0};
    int ID;
    Tile(Game game, int[] position, int id){
        GAME = game;
        POS = position;
        ID = id;
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBackground(Color.blue);
        this.setRolloverEnabled(true);
        this.setIcon(IMAGE);

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

            public void mouseClicked(MouseEvent e) {
                int modify = e.getModifiersEx(); //0 = left click, 256 = Right click
                System.out.println(modify);
                if (modify == 0) {
                    GAME.deleteUnit(ID, POS[0], POS[1]);
                }
                else{
                    int[] stats = {1, 5, 1, 0};
                    GAME.changeTile(ID, POS[0], POS[1], stats);
                    GAME.updateState();
                }
            }
        });
        // this.setBorderPainted(false);
    }

    public void changeUnit(Icon image, int[] info){
        IMAGE = image;
        STATS = info;
        this.setIcon(IMAGE);

    }

    public int[] returnStats(){
        return STATS;
    }

    public void grabColorLock(){
        this.setOpaque(true);
        this.setBackground(Color.green);
        LOCKED = true;
    }

    public void grabColorUnLock(){
        this.setOpaque(false);
        this.setBackground(Color.blue);
        LOCKED = false;
    }
}
