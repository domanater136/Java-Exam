// Values are: Defence, Attack, Delay, Unit Number, Crnt Power, Color (0 = Red, 1 = Blue, 2 = Green), idle, Extra.
// Values are: Defence, Attack, Delay, Unit Number

import javax.swing.*;
import java.awt.*;

// Values are: Unit Number, Image, Defence, Attack, Delay, Idle, Extra (9 = Wall | 10* = Ability)
public enum Holy_Unit_Data {
    Archer(1, new ImageIcon("archer.JPG"), 2, 5, 1, 0, 0),
    Swordsmen(2, new ImageIcon("swordsmen.JPG"), 2, 8, 2, 0, 0),
    Wizard(3, new ImageIcon("swordsmen.JPG"), 2, 12, 3, 0, 0),
    Wall(4, new ImageIcon("wall.JPG"), 6, 0, 1, 0, 9),
    ;

    Holy_Unit_Data(int Unit_Number, ImageIcon Image, int Defence, int Attack, int Delay, int Idle, int Extra) {
    }
};
