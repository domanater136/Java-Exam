import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Tile extends JButton {
    Game GAME;
    int[] POS;
    boolean LOCKED = false;
    Random rand = new Random();
    Icon IMAGE;
    UnitData UNIT;
    int COLOR = 0; //(0 = Null, 1 = Red, 2 = Blue, 3 = Green)
    int ID;
    Tile(Game game, int[] position, int id){
        GAME = game;
        POS = position;
        ID = id;
        this.setDefault();
        // this.setBorderPainted(false);
    }

    Tile(Game game, int[] position, int id, int faction, int stats){
        GAME = game;
        POS = position;
        ID = id;
        this.setDefault();
        // this.setBorderPainted(false);
    }
    private void setDefault(){
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBackground(Color.blue);
        this.setRolloverEnabled(true);
        this.setIcon(IMAGE);

        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent event){
                if (!LOCKED){
                    Tile tile = (Tile)event.getComponent();
                    tile.setOpaque(true);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent event){
                if (!LOCKED){
                    Tile tile = (Tile)event.getComponent();
                    tile.setOpaque(false);
                }
            }

            public void mouseClicked(MouseEvent event) {
                int modify = event.getModifiersEx(); //0 = left click, 256 = Right click
                if (modify == 0) {
                    GAME.grabUnit(ID, POS[0], POS[1]);
                }
                else{
                    GAME.deleteTile(ID, POS[0], POS[1]);
                    GAME.updateState();
                }
            }
        });
    }

    //Creates Tile Info using Unit Data and Color
    public void editTileWithUnitData(UnitData unit){
        IMAGE = unit.getImage(unit.getColor());
        UNIT = unit;
        COLOR = unit.getColor();
        this.setIcon(IMAGE);

    }

    private UnitData getUnitInfo(int Faction, int UnitNumber){
        UnitData info = UnitData.getUnitObject(Faction, UnitNumber);
        if (info != null){
            return info;
        }else{
            System.out.println("Something has gone wrong - Get Unit Values in Change Unit");
            return info; //Will be Null
        }
    }

    // Creates Tile info with Random Color. Default Choice
    public void editTileWithFaction(int Faction, int UnitNumber) {
        editTileWithUnitData(getUnitInfo(Faction, UnitNumber), rand.nextInt(3));
    }

    // Creates tile info with chosen Color
    public void editTileWithFactionAndColor(int Faction, int UnitNumber, int Color){
        editTileWithUnitData(getUnitInfo(Faction, UnitNumber), Color);
    }

    public void deleteUnit(){
        IMAGE = new ImageIcon();
        UNIT = null;
        COLOR = 0;
        this.setIcon(IMAGE);
    }

    public UnitData returnUnit(){
        return UNIT;
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
