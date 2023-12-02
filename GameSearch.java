package GameSearchDomineering;

import java.io.IOException;

public abstract class GameSearch {

    public static final boolean DEBUG = true;
    public static boolean PROGRAM = false;
    public static boolean HUMAN = true;


    /**             Abstract methods                     */

    public abstract boolean drawPosition(Position p); //p = matrice 6*6
    public abstract boolean wonPosition(Position p ,boolean player);
    public abstract float positionEvalution(Position p , boolean player);
    public abstract void printPosition(Position p);
    public abstract Position[] possibleMoves(Position p , boolean player);
    public abstract Position makeMove(Position p,boolean player,Move move);
    public abstract boolean reachedMaxDepth(Position p ,int depth);
    public abstract Move createMove() throws IOException;





}
