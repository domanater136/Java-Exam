public class Player {
    int ID;
    Game GAME;
    Board BOARD;

    // Constructor
     Player(int id, Game root) {
        System.out.println("Constructor Called");
        ID = id;
        GAME = root;
        BOARD = new Board(GAME, ID);
    }
    public Board getBoard(){
         return BOARD;
    }

}


