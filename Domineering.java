package GameSearchDomineering;

import GameSearchDomineering.ui.CellPanel;
import GameSearchDomineering.ui.ComponentPanel;
import GameSearchDomineering.ui.Coordinates;
import GameSearchDomineering.ui.GameUi;

import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import static GameSearchDomineering.ui.ComponentPanel.getCellPanel;

public class Domineering extends GameSearch{
    public    ComponentPanel componentPanel;
    public static DomineeringPosition pos;
    public Domineering(){}

    public static boolean humanVsHuman = false;
    public static boolean humanVsProgram = false;
    public static boolean choos = false;
    public static int depthGame = 0;

    Domineering(ComponentPanel a){
        this.componentPanel=a;
    }

    @Override
    public Position provideHelp(Position position, GameUi gameUi) {

        // Call alphaBeta to suggest a move
        Vector suggestedMoveVector = alphaBeta(3, position, true, gameUi);

        // Extract the suggested move from the result
        if (suggestedMoveVector.size() > 1) {
            Position suggestedMove = (Position) suggestedMoveVector.elementAt(1);
            System.out.println("Suggested move: " + suggestedMove);
            return suggestedMove;
        } else {
            System.out.println("No valid move suggested.");
        }
        return null;
    }


    @Override
    public boolean drawnPosition(Position p) {
        boolean isFinished = true;
        int countVertical =0;
        int countHorizentale=0;
        if(possibleMoves (p, true) != null){
            countVertical = possibleMoves (p, true).length;
        }
        if(possibleMoves (p, false) != null){
            countHorizentale = possibleMoves (p, false).length;
        }
        if ( countVertical == 0  && countHorizentale == 0) return true;
        return false;
    }

    @Override
    public boolean wonPosition(Position p, boolean player) {
        boolean ret = false;
        int countVertical =0;
        int countHorizentale=0;
        try{


            if (player==true){
                Position []h= possibleMoves (p, false);
                Position []v= possibleMoves (p, true);
                if(v != null){
                    countVertical =v.length;
//            System.out.println(countVertical);
                }
                if(h != null){
                    countHorizentale = h.length;
//            System.out.println(countHorizentale);
                }
                if((countVertical !=0  && countHorizentale ==0)) ret = true;


            }else if(player==false){
                Position []h= possibleMoves (p, false);
                Position []v= possibleMoves (p, true);
                if(v != null){
                    countVertical =v.length;
//            System.out.println(countVertical);
                }
                if(h != null){
                    countHorizentale = h.length;
//            System.out.println(countHorizentale);
                }
                if(countHorizentale != 0 && countVertical == 0) ret = true;
            }else{
                if (countVertical==countHorizentale && countHorizentale == 0) ret=false;
            }

            //traitement:



        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public float positionEvaluation(Position p, boolean player) {
        DomineeringPosition pos = (DomineeringPosition) p;

        // Heuristic 1: Count the number of available moves for each player
        int countPlayer = 0;
        int countOpponent = 0;

        if (possibleMoves(p, player) != null) {
            countPlayer = possibleMoves(p, player).length;
        }

        if (possibleMoves(p, !player) != null) {
            countOpponent = possibleMoves(p, !player).length;
        }

        // Heuristic 2: Evaluate based on creating a guaranteed winning move
        float guaranteedWinningMove = 0.0f;

        // Iterate through available moves for the current player
        Position[] playerMoves = possibleMoves(p, player);
        if (playerMoves != null) {
            for (Position move : playerMoves) {
                if (wonPosition(move, player)) {
                    // If the move leads to a win, assign a high value
                    guaranteedWinningMove = 1.0f;
                    break;
                }
            }
        }

        // Combine the two heuristics, giving more weight to the guaranteed winning move
        float evaluation = countPlayer - countOpponent + 2.0f * guaranteedWinningMove;

        return evaluation;
    }

    @Override
    public void printPosition(Position p) {
        DomineeringPosition pos = (DomineeringPosition)p;

        if(pos!=null){
            for(int i = 0;i<6;i++){
                for(int j = 0;j<6;j++){
                    if(pos.board[i][j]==DomineeringPosition.HUMAN){
                        System.out.print("V    ");
                    }else if(pos.board[i][j]==DomineeringPosition.PROGRAM){
                        System.out.print("H    ");
                    }else if(pos.board[i][j]==DomineeringPosition.BLANK) {
                        System.out.print("0    ");
                    }

                }
                System.out.println();
            }
        }else{
            if(GameSearch.DEBUG){
                System.out.println("la case n'est pas vide");
            }
        }

    }

    @Override
    public boolean reachedMaxDepth(Position p, int depth) {
        boolean ret = false;
        if(depth>= depthGame) return true;
        if(wonPosition(p,false)) ret=true;
        if(wonPosition(p,true)) ret=true;
        if(drawnPosition(p)) ret=true;
        if(GameSearch.DEBUG) {
            System.out.println("reachedMaxDepth : pos" );
            ((DomineeringPosition)p).displayBoard();
            System.out.println("depth : "+depth+" ret: "+ret);
        }
        return ret;
    }


    /**
     *
     * @param position
     * @param player
     * @return
     *
     */
    @Override
    public Position[] possibleMoves(Position position, boolean player) {
        DomineeringPosition pos = (DomineeringPosition)position;
        int count = 0;
        // on calcule le nombre de moves vide
        if(player){ //vertical
            for(int i = 0 ; i<6 ; i++){
                for (int j = 0 ; j<6 ; j++){
                    if(pos.board[i][j]==DomineeringPosition.BLANK ){
                        if(i==5 && j==0 || i==5 && j==1 || i==5 && j==2 || i==5 && j==3 || i==5 && j==4 || i==5 && j==5) {
                            if(GameSearch.DEBUG){
                                System.out.println("adjacent null !");
                            }
                        }
                        else if(pos.board[i+1][j]==DomineeringPosition.BLANK){
                            count++;
                        }else{
                            if(GameSearch.DEBUG){
                                System.out.println("vertical : la case pas vide i:"+(i+1)+",j:"+j);
                            }
                        }
                    }
                }
            }
        }else{
            for(int i = 0 ; i<6 ; i++){
                for (int j = 0 ; j<6 ; j++){
                    if(pos.board[i][j]==DomineeringPosition.BLANK){
                        if(i==0 && j==5 || i==1 && j==5 || i==2 && j==5 || i==3 && j==5 || i==4 && j==5 || i==5 && j==5) {
                            if(GameSearch.DEBUG){
                                System.out.println("adjacent null !");
                            }
                        }

                        else  if(pos.board[i][j+1]==DomineeringPosition.BLANK){
                            count++;
                        }else{
                            if(GameSearch.DEBUG){
                                System.out.println("horizontal : la case pas vide i:"+i+",j:"+(j+1));
                            }
                        }
                    }
                }
            }
        }
        if(count==0) return null;
        Position [] possiblesMoves = new Position[count];
        count=0;
        for(int i = 0 ; i<6 ; i++){
            for(int j = 0 ; j<6 ; j++){
                if(pos.board[i][j] == 0){
                    if(player) {
                        if(i==5 && j==0 || i==5 && j==1 || i==5 && j==2 || i==5 && j==3 || i==5 && j==4 || i==5 && j==5) {
                            if(GameSearch.DEBUG){
                                System.out.println("adjacent null !");
                            }
                        }
                        else  if(pos.board[i+1][j]==0){//vertical move
                            DomineeringPosition domineeringPosition = affecterPosition(position);
                            domineeringPosition.board[i][j]=1;
                            domineeringPosition.board[i+1][j]=1;
                            possiblesMoves[count++]=domineeringPosition;
                            if(GameSearch.DEBUG){
                                System.out.println("Vertical possible Move : ");
                                domineeringPosition.displayBoard();
                            }
                        }else if(GameSearch.DEBUG) System.out.println("adjacent vertical non vide");
                    }else{
                        if(i==0 && j==5 || i==1 && j==5 || i==2 && j==5 || i==3 && j==5 || i==4 && j==5 || i==5 && j==5) {
                            if(GameSearch.DEBUG){
                                System.out.println("adjacent null !");
                            }
                        }
                        else  if(pos.board[i][j+1]==0){//horizontal move
                            DomineeringPosition domineeringPosition = affecterPosition(position);
                            domineeringPosition.board[i][j]=-1;
                            domineeringPosition.board[i][j+1]=-1;
                            possiblesMoves[count++]=domineeringPosition;
                            if(GameSearch.DEBUG){
                                System.out.println("Horizontal possible Move : ");
                                domineeringPosition.displayBoard();
                            }
                        } else if(GameSearch.DEBUG) System.out.println("Horizontal vertical non vide");
                    }
                }
            }
        }
        return possiblesMoves;
    }


    /**=========================*/
    @Override
    public void makeMoveAlphaBeta(Position p) {
        DomineeringPosition ps = (DomineeringPosition)p;
        for(int i = 0 ; i<6 ; i++){
            for(int j = 0; j<6 ; j++){
                Coordinates c = new Coordinates(i,j);
                CellPanel cellPanel1 = getCellPanel(c);
                if(ps.board[i][j]==1){
                    if(cellPanel1!=null){
                        cellPanel1.clicked=true;
                        Color currentColor = new Color(195, 94, 97);
                        cellPanel1.setBackground(currentColor);
                    }
                } else if (ps.board[i][j]==-1) {
                    if(cellPanel1!=null){
                        cellPanel1.clicked=true;
                        Color currentColor = new Color(126, 182, 233);
                        cellPanel1.setBackground(currentColor);
                    }

                }
            }
        }

    }


    @Override
    public Position makeMove(Position p, boolean player, Move move) {
        if(GameSearch.DEBUG) System.out.println("Entred Domineering.makeMove");
        DomineeringMove mv = (DomineeringMove)move;
        DomineeringPosition pos = (DomineeringPosition)p;
        DomineeringPosition pos2 = affecterPosition(pos);
        // colorer



        if(isAdjExist(p,player,move)){
            int pp;
            if (player){
                pp=1;
                pos2.board[mv.moveIndexRow][mv.moveIndexColl]=pp;
                pos2.board[mv.moveIndexRow+1][mv.moveIndexColl]=pp;
                Coordinates c = new Coordinates(mv.moveIndexRow,mv.moveIndexColl);
                CellPanel cellPanel = searchAdjCell(c, true);
                CellPanel cellPanel1 = getCellPanel(c);
                //ComponentPanel.DisplayPanelBord();
                //  System.out.println("x = "+mv.moveIndexRow+" y = "+mv.moveIndexColl);
                if(cellPanel!=null && cellPanel1!=null){
                    cellPanel.setBackground(Color.BLUE);
                    cellPanel.clicked=true;
                    cellPanel1.clicked=true;
                    cellPanel1.setBackground(Color.BLUE);
                }
            }else{
                pp=-1;
                pos2.board[mv.moveIndexRow][mv.moveIndexColl]=pp;
                pos2.board[mv.moveIndexRow][mv.moveIndexColl+1]=pp;
                Coordinates c2 = new Coordinates(mv.moveIndexRow,mv.moveIndexColl);
                CellPanel cellPanel = searchAdjCell(c2, false);
                CellPanel cellPanel1 = getCellPanel(c2);
                //ComponentPanel.DisplayPanelBord();
                // System.out.println("x = "+mv.moveIndexRow+" y = "+mv.moveIndexColl);
                if(cellPanel!=null && cellPanel1!=null){
                    cellPanel.setBackground(Color.GREEN);
                    cellPanel.clicked=true;
                    cellPanel1.clicked=true;
                    cellPanel1.setBackground(Color.GREEN);
                }
            }

        }else {
            if(GameSearch.DEBUG) System.out.println("la case n'est pas vide !");
            return null;
        }
        return pos2;

    }

    public boolean isAdjExist(Position p,boolean player , Move move){
        DomineeringPosition pos = (DomineeringPosition)p;
        DomineeringMove mv = (DomineeringMove)move;
        if(player){
            if(mv.moveIndexRow==5 && mv.moveIndexColl==0 || mv.moveIndexRow==5 && mv.moveIndexColl==1
                    || mv.moveIndexRow==5 && mv.moveIndexColl==2 || mv.moveIndexRow==5 && mv.moveIndexColl==3
                    || mv.moveIndexRow==5 && mv.moveIndexColl==4 || mv.moveIndexRow==5 && mv.moveIndexColl==5) {
                if(GameSearch.DEBUG){
                    System.out.println("adjacent null !");
                }
            }else{
                if(pos.board[mv.moveIndexRow][mv.moveIndexColl]==DomineeringPosition.BLANK){
                    if(pos.board[mv.moveIndexRow+1][mv.moveIndexColl]==DomineeringPosition.BLANK){
                        return true;
                    }else{
                        if(GameSearch.DEBUG) System.out.println("Vertical :impossible de choisir cette case!");
                        return false;
                    }
                }
            }

        }else{
            if(mv.moveIndexRow==0 && mv.moveIndexColl==5 || mv.moveIndexRow==1 && mv.moveIndexColl==5
                    || mv.moveIndexRow==2 && mv.moveIndexColl==5 || mv.moveIndexRow==3 && mv.moveIndexColl==5
                    || mv.moveIndexRow==4 && mv.moveIndexColl==5 || mv.moveIndexRow==5 && mv.moveIndexColl==5) {
                if(GameSearch.DEBUG){
                    System.out.println("adjacent null !");
                }
            }else{
                if(pos.board[mv.moveIndexRow][mv.moveIndexColl]==DomineeringPosition.BLANK){
                    if(pos.board[mv.moveIndexRow][mv.moveIndexColl+1]==DomineeringPosition.BLANK){
                        return true;
                    }else{
                        if(GameSearch.DEBUG) System.out.println("Horizontal : impossible de choisir cette case!");
                        return false;
                    }
                }
            }

        }

        return false;
    }



    @Override
    public  Move createMove() {
        if(GameSearch.DEBUG) System.out.println("Create Move Fonction :");
        int i = 0;
        int j = 0;

        try {
            Scanner sc = new Scanner(System.in);
            i = sc.nextInt();
            j = sc.nextInt();

        }catch (Exception e ){}
        DomineeringMove m = new DomineeringMove();
        m.moveIndexRow=i;
        m.moveIndexColl=j;
        return m;

    }

    @Override
    public  Move createMoveOFinterface(){
        Coordinates clickedCoordinates = CellPanel.coordinatesPointer;

        if (GameSearch.DEBUG) System.out.println("Create Move Function:");

        DomineeringMove m = new DomineeringMove();
        m.moveIndexRow = clickedCoordinates.row;
        m.moveIndexColl = clickedCoordinates.col;

        return m;
    }



    /**---------- methodes complementaires ----------*/

    public static CellPanel searchAdjCell(Coordinates coordinates,boolean player){
        Map<CellPanel, Coordinates> map =  ComponentPanel.getMapCellPanel();
        if(player){
            if(coordinates.row==5 && coordinates.col==0 || coordinates.row==5 && coordinates.col==1 || coordinates.row==5 && coordinates.col==2
                    || coordinates.row==5 && coordinates.col==3 ||  coordinates.row==5 && coordinates.col==4 || coordinates.row==5 && coordinates.col==5){
                return null;
            }
            for(Map.Entry<CellPanel,Coordinates> item :map.entrySet()){
                // System.out.println(item.getValue().row+";"+item.getValue().col);
                if(item.getValue().row==coordinates.row+1 && item.getValue().col==coordinates.col && !item.getKey().clicked){
                    return item.getKey();
                }
            }
        }else{
            if(coordinates.row==0 && coordinates.col==5 || coordinates.row==1 && coordinates.col==5 || coordinates.row==2 && coordinates.col==5
                    || coordinates.row==3 && coordinates.col==5 ||  coordinates.row==4 && coordinates.col==5 || coordinates.row==5 && coordinates.col==5){
                return null;
            }
            for(Map.Entry<CellPanel,Coordinates> item :map.entrySet()){
                // System.out.println(item.getValue().row+";"+item.getValue().col);
                if(item.getValue().row==coordinates.row && item.getValue().col==coordinates.col+1 && !item.getKey().clicked){
                    return item.getKey();
                }
            }
        }
        return null;
    }



    /**
     *
     public Position searchPosition(Position position,boolean player){
     DomineeringPosition pos = (DomineeringPosition)position;
     int count = 0;
     // on calcule le nombre de moves vide
     if(player){ //vertical
     for(int i = 0 ; i<6 ; i++){
     for (int j = 0 ; j<6 ; j++){
     if(pos.board[i][j]==DomineeringPosition.BLANK){
     if(pos.board[i+1][j]==DomineeringPosition.BLANK){
     count++;
     }else{
     if(GameSearch.DEBUG){
     System.out.println("vertical : la case pas vide i:"+(i+1)+",j:"+j);
     }
     }
     }
     }
     }
     }else{
     for(int i = 0 ; i<6 ; i++){
     for (int j = 0 ; j<6 ; j++){
     if(pos.board[i][j]==DomineeringPosition.BLANK){
     if(pos.board[i][j+1]==DomineeringPosition.BLANK){
     count++;
     }else{
     if(GameSearch.DEBUG){
     System.out.println("horizontal : la case pas vide i:"+i+",j:"+(j+1));
     }
     }
     }
     }
     }
     }



     return null;
     }
     */

    public DomineeringPosition affecterPosition(Position pos){
        DomineeringPosition position = (DomineeringPosition)pos;
        DomineeringPosition domineeringPosition = new DomineeringPosition();

        for(int i = 0 ; i<6 ; i++){
            for(int j = 0 ; j<6 ; j++){
                domineeringPosition.board[i][j]=position.board[i][j];
            }
        }

        return domineeringPosition;
    }


    /**
     public static void main(String[] args) throws IOException {
     Position pos = new DomineeringPosition();
     Domineering d = new Domineering();
     int [][] bord ;
     bord = new int[][]{{1,0,0,0,0,0},
     {1,0,0,0,0,0},
     {0,-1,-1,0,0,0},
     {0,0,0,0,0,0},
     {0,0,0,1,0,0},
     {0,0,0,1,0,0}};
     ((DomineeringPosition)pos).board =bord;
     //d.possibleMoves(pos,false);


     boolean e =  d.drawPosition(pos);
     if(e)
     System.out.println("match fini");
     else
     System.out.println("match non fini");

     boolean ret = d.wonPosition(pos,true);
     if(ret)
     System.out.println("player won");
     else
     System.out.println("player lost");


     // Move mv = d.createMove();
     // Position poss =  d.makeMove(pos,false,mv);
     //   ((DomineeringPosition) poss).displayBoard();

     d.printPosition(pos);


     }

     */

    static public void main(String [] args) throws IOException {
        ComponentPanel cp = new ComponentPanel();
        DomineeringPosition p = new DomineeringPosition();

        Domineering ttt = new Domineering(cp);
        GameUi gameUi = new GameUi(cp);
//        gameUi.turnLabel2.setText("Player 1's");

        while (gameUi.getSelectedMode() == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String selectedMode = gameUi.getSelectedMode();
        if ("Human vs Human".equals(selectedMode)) {
            ttt.playGameHumenVsHuman(p, true, gameUi);
        } else if ("Human vs AI (Easy)".equals(selectedMode)) {
            depthNiveau=2;
            depthGame=2;
            ttt.playGameHumanVsProgram(p, true, gameUi);


        } else if ("Human vs AI (Medium)".equals(selectedMode)) {
            depthNiveau=4;
            depthGame=4;
            ttt.playGameHumanVsProgram(p, true, gameUi);

        } else if ("Human vs AI (Hard)".equals(selectedMode)) {
            depthNiveau=5;
            depthGame=5;
            ttt.playGameHumanVsProgram(p, true, gameUi);

        }

        boolean wantHelp =gameUi.getWantHelp();
        if(wantHelp){
            System.out.println("OKKKKK");
        }

    }

    static public void startGame(){

    }

}