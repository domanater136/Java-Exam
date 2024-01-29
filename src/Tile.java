import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Tile extends JButton {
    Game GAME;
    int[] POS;
    boolean LOCKED = false;
    Random rand = new Random();
    Icon IMAGE;
    UnitData UNIT;
    //(0 = Null, 1 = Red, 2 = Blue, 3 = Green)
    int COLOR = 0;
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
                if (modify == 0) {
                    //GAME.deleteUnit(ID, POS[0], POS[1]);
                    GAME.grabUnit(ID, POS[0], POS[1]);
                }
                else{
                    //int value = rand.nextInt(3);
                    int value = 2;
                    System.out.println(value);
                    UnitData unit = UnitData.getUnitObject(GAME.getPlayerFaction(ID), value);
                    editTileWithUnitData(unit, rand.nextInt(3));
                    GAME.updateState();
                }
            }
        });
    }

    //Creates Tile Info using Unit Data and Color
    public void editTileWithUnitData(UnitData unit, int Color){
        IMAGE = unit.getImage(Color);
        UNIT = unit;
        COLOR = Color;
        this.setIcon(IMAGE);

    }

    private UnitData getUnitInfo(int Faction, int UnitNumber){
        UnitData info = UnitData.getUnitObject(Faction, UnitNumber);
        if (info != null){
            return info;
        }else{
            System.out.println("Something has gone wrong - Get Unit Values in Change Unit");
            return null;
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
