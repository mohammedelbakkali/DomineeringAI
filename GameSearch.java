package GameSearchDomineering;

import GameSearchDomineering.ui.CellPanel;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;


public abstract class GameSearch {

    public static final boolean DEBUG = true;
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
    public abstract Move createMoveOFinterface();
    public  Position positionPanel;

    public  void playGameHumenVsHuman(Position startingPosition, boolean role) throws IOException {
        positionPanel = startingPosition;
        //CellPanel.setRole(!role); // Set the initial role in the GUI
        CellPanel.setRole(role);
        boolean etat=true;
        while (etat) {

            if (startingPosition != null) {
                if (wonPosition(startingPosition, PROGRAM)) {
                    System.out.println("Program won");
                    etat=false;
                    break;
                }
                if (wonPosition(startingPosition, HUMAN)) {
                    System.out.println("Human won");
                    etat=false;
                    break;
                }
                if (drawnPosition(startingPosition)) {
                    System.out.println("Drawn game");
                    etat=false;
                    break;
                }
            } else {
                System.out.println("Game ended unexpectedly. Starting position is null.");
                break;
            }
        if(role==false){
            printPosition(startingPosition);
            System.out.print("\nYour move PROGRAM:");
            System.out.println("changer le role");

            while (!CellPanel.isClickedPanel) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Move mv1 = createMoveOFinterface();
            DomineeringMove mvCast = (DomineeringMove)mv1;

            //Move mv1 = createMove();
            startingPosition = makeMove(startingPosition, PROGRAM, mv1);
            printPosition(startingPosition);
            System.out.println("mv1 ===========================================: x = "+mvCast.moveIndexRow+" y= "+mvCast.moveIndexColl);
            CellPanel.isClickedPanel=false;
            role=true;
            CellPanel.setRole(role); // Set the initial role in the GUI
        }else{
            System.out.print("\nYour move HUMAN :");
            System.out.println("changer le role");


            while (!CellPanel.isClickedPanel) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            CellPanel.isClickedPanel=false;
            Move mv1 = createMoveOFinterface();
            //Move mv1 = createMove();
            startingPosition = makeMove(startingPosition, HUMAN, mv1);
            printPosition(startingPosition);
            role=false;
            CellPanel.setRole(role); // Set the initial role in the GUI

            if(startingPosition ==null){
                System.out.println("Drawn game");
                etat=false;
                break;
            }
        }

        }
    }


}
