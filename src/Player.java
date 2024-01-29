public class Player {
    int ID;
    Game GAME;
    Board BOARD;
    int ACTIONS;

    int FACTION;

    // Constructor
     Player(int id, Game root) {
        ID = id;
        GAME = root;
        BOARD = new Board(GAME, ID);
        FACTION = 1;
        ACTIONS = 3;
    }
    public Board getBoard(){ return BOARD; }

    public int getFaction() { return FACTION; }
}


