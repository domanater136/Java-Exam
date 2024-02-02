import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Game {
    private Player[] PLAYERS = new Player[2];
    private Board[] BOARDS = new Board[2];
    private final JFrame ROOT = new JFrame();
    private final GameScreen GAMESCREEN;
    private Boolean IS_GRABBING = false;
    private int[] GRAB_SAVE;
    private UnitData GRABBED_UNIT;
    private final Random rand = new Random();
    private int CurrentPlayer = 0;

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

        setTile(1, 3, 2, 2);
        updateState();
    }

    public void addTile(Tile obj) {
        ROOT.add(obj);
    }

    public void addJButton(JButton obj) {
        ROOT.add(obj);
    }


    public UnitData generateUnitStats(int UnitNumber) {
        return UnitData.Wall;
    } // TODO: Finish this me?????

    public UnitData generateRandomUnit(int Faction) {
        return UnitData.getUnitObject(Faction, rand.nextInt(3));
    }

    // Method to Grab and Drop units. Checks var IS GRABBING, and either deletes the unit and stores its info, or
    // spawns the unit to row of your choosing.
    public void grabUnit(int player, int row, int column){
        System.out.println(this.IS_GRABBING);
        if (!this.IS_GRABBING){//If not currently grabbing
            int check = columnBehindUpmostAlly(player, row, 0);
            if (check < 5){
                check++;
                UnitData info = checkTileUnit(player, row, check);
                if (info.getUnitNumber() != 0) {
                    this.IS_GRABBING = true;
                    this.GRABBED_UNIT = info;
                    int color = this.BOARDS[player].TILES[row][check].COLOR;
                    deleteTile(player, row, check);
                    this.BOARDS[player].TILES[row][check].grabColorLock();
                    this.GRAB_SAVE = new int[]{player, row, check, color};
                }
            }
        }


        else { //If already grabbing
            int drop = columnBehindUpmostAlly(player, row, 0);
            if (drop >= 0){
                setTileWithColor(player, row, drop, GRABBED_UNIT.getUnitNumber(), this.GRAB_SAVE[3]);
                this.IS_GRABBING = false;
                this.BOARDS[this.GRAB_SAVE[0]].TILES[this.GRAB_SAVE[1]][this.GRAB_SAVE[2]].grabColorUnLock();
            }
        }
        updateState();

    }

    public void deleteTile(int player, int row, int column){
        this.BOARDS[player].deleteTile(row, column);
    }

    public void setTile(int player, int row, int column, int UnitNumber) {
        this.BOARDS[player].setTile(row, column, PLAYERS[player].FACTION, UnitNumber);
    }
    public void setTileWithColor(int player, int row, int column, int UnitNumber, int Color) {
        this.BOARDS[player].setTileWithColor(row, column, PLAYERS[player].FACTION, UnitNumber, Color);
    }

    private UnitData checkTileUnit(int player, int row, int column) {
        return checkTile(player, row, column).returnUnit();
    }

    private Tile checkTile(int player, int row, int column){
        return this.BOARDS[player].returnTile(row, column);
    }

    public void checkMatches(int player, int row, int column) {
        boolean Formation = false;
        boolean Wall = false;
        if (column < 4) {
            UnitData[] unitCheck = new UnitData[3];
            int[] colorCheck = new int[3];
            for (int item = 0; item <= 2; item++) {
                unitCheck[item] = checkTileUnit(player, row, column + item);
                colorCheck[item] = checkTile(player, row, column + item).COLOR;
            }
            if (unitCheck[0] == unitCheck[1] && unitCheck[1] == unitCheck[2] && colorCheck[0] == colorCheck[1] &&
            colorCheck[1] == colorCheck[2]) {
                Formation = true;
            }
        }
        if (row > 2) {
            UnitData[] unitCheck = new UnitData[3];
            int[] colorCheck = new int[3];
            for (int item = 0; item <= 2; item++) { // Check - as each object is sent downwards left to right
                unitCheck[item] = checkTileUnit(player, row-item, column);
                colorCheck[item] = checkTile(player, row-item, column).COLOR;
            }
            if (unitCheck[0] == unitCheck[1] && unitCheck[1] == unitCheck[2] && colorCheck[0] == colorCheck[1] &&
                    colorCheck[1] == colorCheck[2]) {
                Wall = true;
            }
        }
        if (Wall && Formation){


        } else if (Wall) {

        } else if (Formation){
            deleteTile(player, row, column);
            deleteTile(player, row, column+1);
            deleteTile(player, row, column+2);
            spawnFormation(player, row, column+1);
        }
    }


    // Given Centre Row and Column of Formation, and spawns it.
    public void spawnFormation(int player, int row, int column) {
        boolean TODO = true;
    }

    public int columnBehindUpmostAlly(int player, int row, int column){
        int current = column;
        UnitData check;

        boolean loop = current <= 5; // Dont check if Index is outside of Range
        while (loop) {
            if (current < 6) {
                check = checkTileUnit(player, row, current);
            } else {check = null;}

            if (check == null && current < 5){
                current++;
            } else if (check != null) {
                loop = false;
                current--;
            }
            else{
                loop = false;
            }
        }
        return Math.min(current, 5);

    }
    public void moveUnitUp(int player, int row, int column, UnitData Unit){
        int columncheck = columnBehindUpmostAlly(player, row, column+1) ;
        if (columncheck != column) {
            System.out.println("Moving unit up"); //TODO - Remove this debug
            int color = checkTile(player, row, column).COLOR;
            deleteTile(player, row, column);
            int target = columnBehindUpmostAlly(player, row, column);
            setTileWithColor(player, row, target, Unit.getUnitNumber(), color);
        }
    }

    public void updateState() {
        for (int Player = 0; Player < 2; Player++) {
            int UnitTotal = 32;
            for (int Row = 0; Row < 8; Row++) {
                for (int Column = 5; Column >= 0; Column--) {
                    UnitData tile = checkTileUnit(Player, Row, Column);
                    if (tile != null){
                        UnitTotal -= 1;
                        moveUnitUp(Player, Row, Column, tile);
                        checkMatches(Player, Row, Column);
                    }

                }
            PLAYERS[Player].setReinforce(UnitTotal);
            }
        }
    }

    public void reinforceBoard(int ID){
        int total = PLAYERS[ID].getReinforce();
        for (int x = 0; x < total; x++){
            setTile(ID, rand.nextInt(0,8), 0, rand.nextInt(1, 4));
            this.updateState();
        }
    }

    public Player getPlayer(int ID){return PLAYERS[ID];}
    public int getPlayerFaction(int ID){return PLAYERS[ID].getFaction();}
}

