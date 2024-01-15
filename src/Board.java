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
                    TILES[i][j] = new Tile(GAME, position, ID);
                    TILES[i][j].setBounds(50 * i, 50 * j, 50, 50);
                    GAME.addTile(TILES[i][j]);
                }
            }
        }
        else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 6; j++) {
                    int[] position = {i, j};
                    TILES[i][j] = new Tile(GAME, position, ID);
                    TILES[i][j].setBounds(50 * i, 700 - 50 * j, 50, 50);
                    GAME.addTile(TILES[i][j]);
                }
            }
        }

    }

    public Tile returnTile(int column, int row){
        return TILES[row][column];
    }
}
