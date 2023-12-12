package GameSearchDomineering;

import GameSearchDomineering.ui.CellPanel;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import static GameSearchDomineering.Domineering.createMoveOFinterface;

public abstract class GameSearch {

    public static final boolean DEBUG = false;
    public static boolean PROGRAM = false;
    public static boolean HUMAN = true;

    public static boolean game = false;


    /**             Abstract methods                     */

    public abstract boolean drawnPosition(Position p); //p = matrice 6*6
    public abstract boolean wonPosition(Position p ,boolean player);
    public abstract float positionEvalution(Position p , boolean player);
    public abstract void printPosition(Position p);
    public abstract Position[] possibleMoves(Position p , boolean player);
    public abstract Position makeMove(Position p,boolean player,Move move);
    public abstract boolean reachedMaxDepth(Position p ,int depth);
    public abstract Move createMove() throws IOException;

    public  Position positionPanel;

    public  void playGameHumenVsHuman(Position startingPosition, boolean role ,Move mv) throws IOException {
         positionPanel=startingPosition;
        while (true) {
        if(!role){
            printPosition(startingPosition);
            System.out.print("\nYour move HUMAN 1:");
            System.out.println("changer le role");
            startingPosition = makeMove(startingPosition, PROGRAM, mv);
            printPosition(startingPosition);
            if (wonPosition(startingPosition, PROGRAM)) {
                System.out.println("Program won");
                break;
            }
            if (wonPosition(startingPosition, HUMAN)) {
                System.out.println("Human won");
                break;
            }
            if (drawnPosition(startingPosition)) {
                System.out.println("Drawn game");
                break;
            }

        }else{
            System.out.print("\nYour move HUMAN 2:");
            System.out.println("changer le role");
            startingPosition = makeMove(startingPosition, HUMAN, mv);
            printPosition(startingPosition);
            if (wonPosition(startingPosition, HUMAN)) {
                System.out.println("Human won");
                break;
            }


            if(startingPosition ==null){
                System.out.println("Drawn game");
                break;
            }
        }













        }
    }


}
