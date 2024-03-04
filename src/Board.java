import javax.swing.*;
import java.awt.event.MouseEvent;

public class Board {
    Game GAME; //Game Root
    Tile[][] TILES = new Tile[8][6]; //Array of each tile, in order. Left to Right, Bottom to Top.
    int ID; // ID of the player who made this. Player 1 is 0, Player 2 is 1.

    JButton REINFORCE;

    //TODO: Change this from regular to Grid Layout, by putting everything inside a Jframe.
    Board(Game root, int id) {
        GAME = root;
        ID = id;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 6; column++) {
                int[] position = {row, column};
                this.TILES[row][column] = new Tile(GAME, position, ID);
                // For Player 1, Y value is 20 + 50 * Column. For Player 2, this is 700 - 50 * Column.
                // The "Top" tile is the tile closest to the centre of the screen
                this.TILES[row][column].setBounds(50 * row + 25, (20 + (680*ID)) + (50 - (100*ID)) * column, 50, 50);
                this.GAME.addTile(TILES[row][column]);
            }
        }
        this.REINFORCE = new JButton("Reinforce");
        REINFORCE.setBounds(500, (500*ID) + 100, 100, 50);
        this.GAME.addJButton(REINFORCE);

        REINFORCE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.out.println("Reinforcing - " + ID);
                GAME.reinforceBoard(ID);
            }
        });
    }

    public Tile getTile(int row, int column){
        return this.TILES[row][column];
    }

    public void setTile(int row, int column, int Faction, int UnitNumber){
        TILES[row][column].editTileWithFaction(Faction, UnitNumber);
    }

    public void setTileWithColor(int row, int column, int Faction, int UnitNumber, int Color){
        TILES[row][column].editTileWithFactionAndColor(Faction, UnitNumber, Color);
    }
    public void deleteTile(int row, int column){
        TILES[row][column].deleteUnit();
    }
}
