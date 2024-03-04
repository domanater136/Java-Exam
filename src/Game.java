import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Game {
    private Player[] PLAYERS = new Player[2]; //List of Players
    private Board[] BOARDS = new Board[2]; //Each Player's Board
    private final JFrame ROOT = new JFrame(); //Root Frame
    private final GameScreen GAMESCREEN = new GameScreen(this); //Gamescreen placed on Root
    private Boolean IS_GRABBING = false; //Grab Check
    private int[] GRAB_SAVE; //Grab Saving, for whhich tile was grabbed
    private UnitData GRABBED_UNIT; //Info of Grabbed Unit
    private final Random rand = new Random(); //Random
    private int CurrentPlayer = 0; //Currently active player

    Game() { //Game Initialization
        varsInit();
        setTile(1, 3, 2, 2);
        updateState();
    }

    private void varsInit() {
        //Init root frame settings.
        ROOT.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ROOT.setResizable(false);
        ROOT.setTitle("Video Gaming");
        ROOT.setPreferredSize(new Dimension(800, 800));

        //Init player variables.
        PLAYERS[0] = new Player(0, this, new UnitData[]{UnitData.Archer, UnitData.Swordsmen, UnitData.Wizard});
        PLAYERS[1] = new Player(1, this, new UnitData[]{UnitData.Archer, UnitData.Swordsmen, UnitData.Wizard});
        BOARDS[0] = PLAYERS[0].getBoard();
        BOARDS[1] = PLAYERS[1].getBoard();

        //Add Root to Gamescreen, and pack it
        ROOT.add(GAMESCREEN);
        ROOT.pack();

        //Extra variables
        ROOT.setLocationRelativeTo(null);
        ROOT.setVisible(true);
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
                UnitData info = getTileUnit(player, row, check);
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

    //The below 3 functions do not Update State as there may be multiple instance in one sequence. Make sure to
    //Update state after doing so.

    //Deletes the "tile", removing whatever is in the tile. Does not remove the tile.
    public void deleteTile(int player, int row, int column){
        this.BOARDS[player].deleteTile(row, column);
    }

    //Sets the tile to a specific unit
    public void setTile(int player, int row, int column, int UnitNumber) {
        this.BOARDS[player].setTile(row, column, PLAYERS[player].FACTION, UnitNumber);
    }

    //Sets the tile to a specific unit, with a color. TODO: remove this when refactoring colors
    public void setTileWithColor(int player, int row, int column, int UnitNumber, int Color) {
        this.BOARDS[player].setTileWithColor(row, column, PLAYERS[player].FACTION, UnitNumber, Color);
    }

    //Gets the UnitData in a tile.
    private UnitData getTileUnit(int player, int row, int column) {
        return getTile(player, row, column).returnUnit();
    }

    //Gets the tile itself. Useful for extracting extra information
    private Tile getTile(int player, int row, int column){
        return this.BOARDS[player].getTile(row, column);
    }

    //Match checking. Checks for 3 in a row or 3 in a column
    public void checkMatches(int player, int row, int column) {
        //Set Checks
        boolean Formation = false;
        boolean Wall = false;
        if (column < 4) { //If there is space above the chosen tile

            //Get the UnitData and the Color of each Unit for the chosen tile, and te 2 tiles above it.
            UnitData[] unitCheck = new UnitData[3];
            int[] colorCheck = new int[3];
            for (int item = 0; item <= 2; item++) {
                unitCheck[item] = getTileUnit(player, row, column + item);
                colorCheck[item] = getTile(player, row, column + item).COLOR;
            }
            //If there is matching units all the way up
            if (unitCheck[0] == unitCheck[1] && unitCheck[1] == unitCheck[2] && colorCheck[0] == colorCheck[1] &&
            colorCheck[1] == colorCheck[2]) {
                Formation = true; // Make Formation
            }
        }
        if (row > 2) { //If there is space to the right of the selected tile
            UnitData[] unitCheck = new UnitData[3];
            int[] colorCheck = new int[3];
            for (int item = 0; item <= 2; item++) { // Check - as each object is sent downwards left to right
                unitCheck[item] = getTileUnit(player, row-item, column);
                colorCheck[item] = getTile(player, row-item, column).COLOR;
            }
            if (unitCheck[0] == unitCheck[1] && unitCheck[1] == unitCheck[2] && colorCheck[0] == colorCheck[1] &&
                    colorCheck[1] == colorCheck[2]) {
                Wall = true;
            }
        }
        if (Wall && Formation){ //TODO


        } else if (Wall) { //TODO

        } else if (Formation){ //TODO
            deleteTile(player, row, column);
            deleteTile(player, row, column+1);
            deleteTile(player, row, column+2);
            spawnFormation(player, row, column+1);
        }
    }


    // Given Centre Row and Column of Formation, and spawns it. //TODO
    public void spawnFormation(int player, int row, int column) {
        boolean TODO = true;
    }

    //Obtains the Tile Index of the "topmost" Unit in this column.
    public int columnBehindUpmostAlly(int player, int row, int column){
        int current = column;
        UnitData check;

        boolean loop = current <= 5; // Dont check if Index is outside of Range
        while (loop) {
            if (current < 6) { //Get the info of a tile so long as index not out of range.
                check = getTileUnit(player, row, current);
            } else {check = null;}

            if (check == null && current < 5){ //If no unit and not out of index
                current++;
            } else if (check != null) { //If unit is there
                loop = false;
                current--;
            }
            else{ //If out of Index
                loop = false;
            }
        }
        return Math.min(current, 5); //Cannot return out of index results.

    }

    //Move the chosen unit up behind the topmost ally.
    public void moveUnitUp(int player, int row, int column, UnitData Unit){
        int columncheck = columnBehindUpmostAlly(player, row, column+1) ;
        if (columncheck != column) {
            System.out.println("Moving unit up"); //TODO - Remove this debug
            int color = getTile(player, row, column).COLOR;
            deleteTile(player, row, column);
            int target = columnBehindUpmostAlly(player, row, column);
            setTileWithColor(player, row, target, Unit.getUnitNumber(), color);
        }
    }

    //Updates the board state to confine with rules. Checks every single tile individually and updates alot of things.
    public void updateState() {
        for (int Player = 0; Player < 2; Player++) {
            int UnitTotal = 32;
            for (int Row = 0; Row < 8; Row++) {
                for (int Column = 5; Column >= 0; Column--) {
                    UnitData tile = getTileUnit(Player, Row, Column);
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

    //Fills the player's board, so they have 32 total units. TODO: Make this check for matches.
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

