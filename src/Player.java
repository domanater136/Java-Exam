public class Player {
    int ID;
    Game GAME;
    Board BOARD;
    int ACTIONS;
    int REINFORCETOTAL;

    int FACTION;

    UnitData[] UNITS = new UnitData[3]; // Each Unit, Ordered in Color.

    // Constructor
     Player(int id, Game root, UnitData[] Units) {
        ID = id;
        GAME = root;
        BOARD = new Board(GAME, ID);
        FACTION = 1;
        ACTIONS = 3;
        UNITS = Units;
    }
    public Board getBoard(){ return BOARD; }

    public int getFaction() { return FACTION; }

    public int getReinforce() { return REINFORCETOTAL; }
    public void setReinforce(int New) { REINFORCETOTAL = New; }

    public UnitData[] getUnitData() { return UNITS; }
    public UnitData getSpecificUnit(int color) { return UNITS[color]; }
}


