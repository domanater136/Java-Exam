// Values are: Defence, Attack, Delay, Unit Number, Crnt Power, Color (0 = Red, 1 = Blue, 2 = Green), idle, Extra.
// Values are: Defence, Attack, Delay, Unit Number

import javax.swing.*;
import java.awt.*;

// Values are: Unit Number, Image, Defence, Attack, Delay, Idle, Extra (9 = Wall | 10* = Ability)
public enum UnitData {
    Wall(1, 0, new ImageIcon[]{new ImageIcon("wall.JPG"), new ImageIcon("wall.JPG"), new ImageIcon("wall.JPG")}, 6, 0, 1, 0, 9),
    Archer(1, 1, new ImageIcon[]{new ImageIcon("archer.JPG"), new ImageIcon("archer.JPG"), new ImageIcon("archer.JPG")}, 2, 5, 1, 0, 0),
    Swordsmen(1, 2, new ImageIcon[]{new ImageIcon("swordsmen.JPG"), new ImageIcon("swordsmen.JPG"), new ImageIcon("swordsmen.JPG")}, 2, 8, 2, 0, 0),
    Wizard(1, 3, new ImageIcon[]{new ImageIcon("swordsmen.JPG"), new ImageIcon("swordsmen.JPG"), new ImageIcon("swordsmen.JPG")},2, 12, 3, 0, 0),
    ;

    private final int Faction;
    private final int UnitNumber;
    private final ImageIcon[] Images;
    private final int Defence;
    private final int Attack;
    private final int Delay;
    private final int Idle;
    private final int Extra;
    private static final UnitData[] Values = UnitData.values();
    private static final int MAX = UnitData.values().length;
    UnitData(int Faction, int UnitNumber, ImageIcon[] Images, int Defence, int Attack, int Delay, int Idle, int Extra) {
        this.Faction = Faction;
        this.UnitNumber = UnitNumber;
        this.Images = Images;
        this.Defence = Defence;
        this.Attack = Attack;
        this.Delay = Delay;
        this.Idle = Idle;
        this.Extra = Extra;
    }
    public static UnitData getUnitObject(int faction, int unitNumber) {
        for (int Total = 0; Total < MAX; Total++) {
            if (faction == Values[Total].getFaction() && unitNumber == Values[Total].getUnitNumber()) {
                return Values[Total];
            }
        }
        return null;
    }

    public int getFaction(){return this.Faction;}

    public int getUnitNumber(){return this.UnitNumber;}

    public ImageIcon getImage(int color){return this.Images[color];}

    public int getDefence() {return this.Defence;}

    public int getAttack() {return this.Attack;}

    public int getDelay() {return this.Delay;}

    public int getIdle() {return this.Idle;}

    public int getExtra() {return this.Extra;}
}
