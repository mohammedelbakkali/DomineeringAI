package GameSearchDomineering;

import GameSearchDomineering.ui.CellPanel;
import GameSearchDomineering.ui.ComponentPanel;
import GameSearchDomineering.ui.GameUi;

import javax.swing.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;


public abstract class GameSearch {

    public static final boolean DEBUG = false;
    public static boolean PROGRAM = false;
    public static boolean HUMAN = true;

    public static boolean game = false;


    /**             Abstract methods                     */

    public abstract boolean drawnPosition(Position p); //p = matrice 6*6
    public abstract boolean wonPosition(Position p ,boolean player);
    public abstract float positionEvaluation(Position p , boolean player);
    public abstract void printPosition(Position p);
    public abstract Position[] possibleMoves(Position p , boolean player);
    public abstract Position makeMove(Position p,boolean player,Move move);
    public abstract boolean reachedMaxDepth(Position p ,int depth);
    public abstract Move createMove() throws IOException;
    public abstract Move createMoveOFinterface();
    public abstract void makeMoveAlphaBeta(Position p);
    public  Position positionPanel;
    public static Position forSave;
    public JFrame f;
    public static int depthNiveau;
    public void   alertGame(String txt){
        f=new JFrame();
        JOptionPane.showMessageDialog(f,txt,"Message",JOptionPane.WARNING_MESSAGE);
    }

    // The alphaBeta method is a wrapper that initializes alpha and beta values
    // and then calls alphaBetaHelper.
    protected Vector alphaBeta(int depth, Position p, boolean player, GameUi gameUi) {
        gameUi.turnLabel2.setText("<html><font color='#7EB6E9'>AI 's</font></html>");
        return alphaBetaHelper(depth, p, player, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY);
    }

    // The alphaBetaHelper method is the core of the alpha-beta pruning algorithm
    protected Vector alphaBetaHelper(int depth, Position p, boolean player, float alpha, float beta) {
        if (GameSearch.DEBUG) System.out.println("alphaBetaHelper("+depth+","+p+","+alpha+","+beta+")");
        if (reachedMaxDepth(p, depth) || drawnPosition(p) || wonPosition(p, true) || wonPosition(p, false)) {
            Vector v = new Vector(2);
            float value = positionEvaluation(p, player);
            v.addElement(Float.valueOf(value));
            v.addElement(null);
            if (GameSearch.DEBUG) {
                System.out.println(" alphaBetaHelper: mx depth at " + depth +
                        ", value=" + value);
            }
            return v;
        }
        Vector best = new Vector();
        Position[] moves = possibleMoves(p, player);
        for (int i = 0; i < moves.length; i++) {
            Vector v2 = alphaBetaHelper(depth + 1, moves[i], !player, -beta, -alpha);
            float value = -((Float)v2.elementAt(0)).floatValue();
            if (value > beta) {
                if (GameSearch.DEBUG) System.out.println(" ! ! ! value=" + value + ", beta=" + beta);
                beta = value;
                best = new Vector();
                best.addElement(moves[i]);
                Enumeration enum2 = v2.elements();
                enum2.nextElement(); // skip previous value
                while (enum2.hasMoreElements()) {
                    Object o = enum2.nextElement();
                    if (o != null) best.addElement(o);
                }
            }
            /**
             * Use the alpha-beta cutoff test to abort search if we
             * found a move that proves that the previous move in the
             * move chain was dubious
             */
            if (beta >= alpha) {
                break;
            }
        }
        Vector v3 = new Vector();
        v3.addElement(Float.valueOf(beta));
        Enumeration enum2 = best.elements();
        while (enum2.hasMoreElements()) {
            v3.addElement(enum2.nextElement());
        }
        return v3;
    }

    public void playGameHumanVsProgram(Position startingPosition, boolean role, GameUi gameUi) throws IOException {
        forSave=startingPosition;
        CellPanel.role= role;
        gameUi.turnLabel2.setText("<html><font color='#C35E61'>your</font></html>");
        if (role == false) { //means program play
            Vector v = alphaBeta(0, startingPosition, PROGRAM, gameUi);
            System.out.println("================" + v);
            startingPosition = (Position) v.elementAt(1);
            makeMoveAlphaBeta(startingPosition);
            // startingPosition = makeMove(startingPosition, HUMAN, move);
        }
        while (true) {
            makeMoveAlphaBeta(startingPosition);
            printPosition(startingPosition);
            if (wonPosition(startingPosition, PROGRAM)) {
                System.out.println("Program won");
                alertGame("Program won");
                break;
            }
            if (wonPosition(startingPosition, HUMAN)) {
                System.out.println("Human won");
                alertGame("Human won");
                break;
            }
            if (drawnPosition(startingPosition)) {
                alertGame("Drawn won");
                break;
            }
            System.out.print("\nYour move:");

            while (!CellPanel.isClickedPanel) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            gameUi.turnLabel2.setText("<html><font color='##C35E61'>your</font></html>");

            Move move = createMoveOFinterface();
            startingPosition = makeMove(startingPosition, HUMAN, move);

            printPosition(startingPosition);

            CellPanel.isClickedPanel=false;
            role=true;
            CellPanel.setRole(role);

            if (wonPosition(startingPosition, HUMAN)) {
                System.out.println("Human won");
                break;
            }

            Vector v = alphaBeta(0, startingPosition, PROGRAM, gameUi);
            System.out.println("================" + v);
            Enumeration enum2 = v.elements();
            while (enum2.hasMoreElements()) {
                System.out.println(" next element: " + enum2.nextElement());
            }
            gameUi.turnLabel2.setText("<html><font color='#C35E61'>your</font></html>");

            startingPosition = (Position) v.elementAt(1);


            if (startingPosition != null) {
                if (wonPosition(startingPosition, PROGRAM)) {
                    System.out.println("Program won");
                    alertGame("Program won");
                }
                if (wonPosition(startingPosition, HUMAN)) {
                    System.out.println("Human won");
                    alertGame("Human won");
                }
                if (drawnPosition(startingPosition)) {
                    System.out.println("Drawn game");
                }
            }
        }
    }

    public  void playGameHumenVsHuman(Position startingPosition, boolean role, GameUi gameUi) throws IOException {
        positionPanel = startingPosition;
        forSave=startingPosition;
//        ComponentPanel cp = new ComponentPanel();
//        GameUi gameUi = new GameUi(cp);
        //CellPanel.setRole(!role); // Set the initial role in the GUI
        CellPanel.setRole(role);
        boolean etat=true;
        while (etat) {

            if (startingPosition != null) {
                if (wonPosition(startingPosition, PROGRAM)) {
                    System.out.println("Program won");
                    alertGame("Program won");
                    etat=false;
                    break;
                }
                if (wonPosition(startingPosition, HUMAN)) {
                    System.out.println("Human won");
                    alertGame("Human won");
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
                gameUi.turnLabel2.setText("<html><font color='#7EB6E9'>Player 2 's</font></html>");
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
                gameUi.turnLabel2.setText("<html><font color='#C35E61'>Player 1 's</font></html>");
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