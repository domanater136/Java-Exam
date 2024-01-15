public class Board {
    Game GAME;
    Tile[][] TILES = new Tile[8][6];
    int ID;

    Board(Game root, int id) {
        GAME = root;
        ID = id;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                int[] position = {i,j};
                TILES[i][j] = new Tile(position);
                TILES[i][j].setBounds(50 * i, 50 * j + (400*ID), 50, 50);
                GAME.addTile(TILES[i][j]);
            }
        }
    }
}
