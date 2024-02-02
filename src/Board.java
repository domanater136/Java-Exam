import javax.swing.*;
import java.awt.event.MouseEvent;

public class Board {
    Game GAME;
    Tile[][] TILES = new Tile[8][6];
    int ID;

    JButton REINFORCE;

    Board(Game root, int id) {
        GAME = root;
        ID = id;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                int[] position = {i, j};
                this.TILES[i][j] = new Tile(GAME, position, ID);
                // For Player 1, this is 50 * J. For Player 2, this is 700 - 50 * J.
                this.TILES[i][j].setBounds(50 * i, (700*ID) + (50 - (100*ID)) * j, 50, 50);
                this.GAME.addTile(TILES[i][j]);
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

    public Tile returnTile(int row, int column){
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
