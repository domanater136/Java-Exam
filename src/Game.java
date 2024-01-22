import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Game {
    Player[] PLAYERS = new Player[2];
    Board[] BOARDS = new Board[2];
    JFrame ROOT = new JFrame();
    GameScreen GAMESCREEN;
    Icon[] UNIT_ICON = new Icon[5];
    int[][] UNIT_STATS = new int[5][4];

    int IS_GRABBING = -1;
    int[] GRAB_SAVE;
    Random rand = new Random();

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

        unitValues();

        setTile(1, 3, 2, UNIT_STATS[1]);
        updateState();
    }

    public void addTile(Tile obj) {
        ROOT.add(obj);
    }

    // Values are: Defence, Attack, Delay, Unit Number, Crnt Power, Color (0 = Red, 1 = Blue, 2 = Green), idle, Extra.
    public int[] generateUnitStats(int unitnum) {
        int[] info = new int[8];
                for (int i = 0; i < 4; i++){
                    info[i] = UNIT_STATS[unitnum][i];
        }
        int value = rand.nextInt(3);
        info[4] = info[0];
        info[5] = rand.nextInt(3);
        info[6] = 0;
        info[7] = 0;
        return info;
    }

    public void grabUnit(int player, int row, int column){
        System.out.println(IS_GRABBING);
        if (IS_GRABBING == -1){
            int check = columnBehindUpmostAlly(player, row, column);
            if (check < 5){
                check++;
                int[] info = checkTile(player, row, check);
                if (info[3] != 3) {
                    IS_GRABBING = info[3];
                    setTile(player, row, check, UNIT_STATS[3]);
                    BOARDS[player].TILES[row][check].grabColorLock();
                    GRAB_SAVE = new int[]{player, row, check};
                }
            }


        }
        else {
            int drop = columnBehindUpmostAlly(player, row, column);
            if (drop != 0){
                setTile(player, row, column, UNIT_STATS[IS_GRABBING]);
                IS_GRABBING = -1;
                BOARDS[GRAB_SAVE[0]].TILES[GRAB_SAVE[1]][GRAB_SAVE[2]].grabColorUnLock();
            }
        }
        updateState();

    }

    public void deleteUnit(int player, int row, int column){
        int[] info = checkTile(player, row, column);
        if (info[3] != 3){
            setTile(player, row, column, UNIT_STATS[3]);
            updateState();
        }
    }


    private void unitValues() {
        UNIT_ICON[0] = new ImageIcon("archer.JPG");
        UNIT_ICON[1] = new ImageIcon("swordsmen.JPG");
        UNIT_ICON[2] = new ImageIcon("wizard.JPG");
        UNIT_ICON[4] = new ImageIcon("wall.JPG");
        // Values are: Defence, Attack, Delay, Unit Number, Crnt Power, Color (0 = Red, 1 = Blue, 2 = Green), idle, Extra.
        // Values are: Defence, Attack, Delay, Unit Number
        UNIT_STATS[0] = new int[]{1, 5, 1, 0};
        UNIT_STATS[1] = new int[]{2, 8, 2, 1};
        UNIT_STATS[2] = new int[]{3, 12, 3, 2};
        UNIT_STATS[3] = new int[]{0, 0, 0, 3};
        UNIT_STATS[4] = new int[]{6, 0, 0, 3, 0, 0, 0, 9};
    }

    public void setTile(int player, int row, int column, int[] info) {
        System.out.println("adding unit");
        BOARDS[player].TILES[row][column].changeUnit(UNIT_ICON[info[3]], info);
    }

    public int[] checkTile(int player, int row, int column) {
        return BOARDS[player].TILES[row][column].returnStats();
    }

    public void checkFormation(int player, int row, int column) {
        if (column < 4) {
            int[][] unitCheck = new int[3][4];
            unitCheck[0] = checkTile(player, row, column);
            unitCheck[1] = checkTile(player, row, column + 1);
            unitCheck[2] = checkTile(player, row, column + 2);
            if (unitCheck[0][2] == unitCheck[1][2] && unitCheck[1][2] == unitCheck[2][2]) {
                spawnFormation(player, row, column);
            }
        }
    }


    public void spawnFormation(int player, int row, int column) {
        boolean TODO = true;
    }

    public int columnBehindUpmostAlly(int player, int row, int column){
        int current = column;
        int[] check;
        boolean loop = true;
        while (loop) {
            check = checkTile(player, row, current);
            if (check[3] == 3 && current < 5){
                current++;
            } else if (check[3] != 3) {
                loop = false;
                current--;
            }
            else{
                loop = false;
            }
        }
        return current;

    }
    public void moveUnitUp(int player, int row, int column, int[] info){
        setTile(player, row, column, UNIT_STATS[3]);
        int target = columnBehindUpmostAlly(player, row, column);
        setTile(player, row, target, info);
    }

    public void updateState() {
        for (int k = 0; k < 2; k++) {
            for (int i = 0; i < 8; i++) {
                for (int j = 4; j >= 0; j--) { //Doesn't check top row to avoid index errors
                    int[] tile = checkTile(k, i, j);
                    if (tile[3] != 3){
                        moveUnitUp(k, i, j, tile);
                        checkFormation(k, i, j);
                    }

                }
            }
        }

    }
}

