public class Board {
    Game GAME;
    Tile[][] TILES = new Tile[8][6];
    int ID;

    Board(Game root, int id) {
        GAME = root;
        ID = id;
        if (ID == 0) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 6; j++) {
                    int[] position = {i, j};
                    this.TILES[i][j] = new Tile(GAME, position, ID);
                    this.TILES[i][j].setBounds(50 * i, 50 * j, 50, 50);
                    this.GAME.addTile(TILES[i][j]);
                }
            }
        }
        else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 6; j++) {
                    int[] position = {i, j};
                    this.TILES[i][j] = new Tile(GAME, position, ID);
                    this.TILES[i][j].setBounds(50 * i, 700 - 50 * j, 50, 50);
                    this.GAME.addTile(TILES[i][j]);
                }
            }
        }

    }

    public Tile returnTile(int row, int column){
        return this.TILES[row][column];
    }

    public void setTile(int row, int column, int Faction, int UnitNumber){
        TILES[row][column].editTileWithFaction(Faction, UnitNumber);
    }
    public void deleteTile(int row, int column){
        TILES[row][column].deleteUnit();
    }
}
