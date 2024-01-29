import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Game {
    private Player[] PLAYERS = new Player[2];
    private Board[] BOARDS = new Board[2];
    private JFrame ROOT = new JFrame();
    private GameScreen GAMESCREEN;
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

        setTile(1, 3, 2, 1);
        updateState();
    }

    public void addTile(Tile obj) {
        ROOT.add(obj);
    }

    // TODO: Finish this me?????
    public UnitData generateUnitStats(int UnitNumber) {
        return UnitData.Wall;
    }

    // Method to Grab and Drop units. Checks var IS GRABBING, and either deletes the unit and stores its info, or
    // spawns the unit to row of your choosing.
    public void grabUnit(int player, int row, int column){
        System.out.println(this.IS_GRABBING);
        if (!this.IS_GRABBING){//If not currently grabbing
            int check = columnBehindUpmostAlly(player, row, column);
            if (check < 5){
                check++;
                UnitData info = checkTile(player, row, check);
                if (info.getUnitNumber() != 0) {
                    this.IS_GRABBING = true;
                    this.GRABBED_UNIT = info;
                    deleteTile(player, row, check);
                    this.BOARDS[player].TILES[row][check].grabColorLock();
                    this.GRAB_SAVE = new int[]{player, row, check};
                }
            }


        }
        else { //If already grabbing
            int drop = columnBehindUpmostAlly(player, row, column);
            if (drop != 0){
                setTile(player, row, column, GRABBED_UNIT.getUnitNumber());
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
        System.out.println("adding unit");
        this.BOARDS[player].setTile(row, column, PLAYERS[player].FACTION, UnitNumber);
    }

    private UnitData checkTile(int player, int row, int column) {
        return this.BOARDS[player].returnTile(row, column).returnUnit();
    }

    public void checkFormation(int player, int row, int column) {
        if (column < 4) {
            UnitData[] unitCheck = new UnitData[3];
            unitCheck[0] = checkTile(player, row, column);
            unitCheck[1] = checkTile(player, row, column + 1);
            unitCheck[2] = checkTile(player, row, column + 2);
            if (unitCheck[0] == unitCheck[1] && unitCheck[1] == unitCheck[2]) {
                spawnFormation(player, row, column);
            }
        }
    }


    public void spawnFormation(int player, int row, int column) {
        boolean TODO = true;
    }

    public int columnBehindUpmostAlly(int player, int row, int column){
        int current = column;
        UnitData check;
        boolean loop = true;
        while (loop) {
            check = checkTile(player, row, current);
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
        return current;

    }
    public void moveUnitUp(int player, int row, int column, UnitData Unit){
        deleteTile(player, row, column);
        int target = columnBehindUpmostAlly(player, row, column);
        setTile(player, row, target, Unit.getUnitNumber());
    }

    public void updateState() {
        for (int Player = 0; Player < 2; Player++) {
            for (int Row = 0; Row < 8; Row++) {
                for (int Column = 4; Column >= 0; Column--) { //Doesn't check top row to avoid index errors
                    UnitData tile = checkTile(Player, Row, Column);
                    if (tile != null){
                        moveUnitUp(Player, Row, Column, tile);
                        checkFormation(Player, Row, Column);
                    }

                }
            }
        }
    }

    public Player[] getPlayers(int ID){return PLAYERS;}
    public int getPlayerFaction(int ID){return PLAYERS[ID].getFaction();}
}

