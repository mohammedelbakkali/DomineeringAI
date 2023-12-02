package GameSearchDomineering;

import GameSearchDomineering.ui.CellPanel;
import GameSearchDomineering.ui.ComponentPanel;
import GameSearchDomineering.ui.Coordinates;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Domineering extends GameSearch{

    @Override
    public boolean drawPosition(Position p) {
        boolean isFinished = true;
        int countVertical =0;
        int countHorizentale=0;
        if(possibleMoves (p, true) != null){
             countVertical = possibleMoves (p, true).length;
        }
        if(possibleMoves (p, false) != null){
            countHorizentale = possibleMoves (p, false).length;
        }
        if ( countVertical == 0  || countHorizentale == 0) return true;
        return false;
    }

    @Override
    public boolean wonPosition(Position p, boolean player) {
        boolean ret = false;
        int countVertical =0;
        int countHorizentale=0;
        if(possibleMoves (p, player) != null){
            countVertical = possibleMoves (p, true).length;
        }
        if(possibleMoves (p, !player) != null){
            countHorizentale = possibleMoves (p, false).length;
        }
        //traitement:
        if (countVertical==countHorizentale && countHorizentale == 0) ret=false;
        else if((countVertical==0  && countHorizentale !=0)) ret = false;
        else if((countHorizentale==0  && countVertical !=0)) ret = true;
        return ret;
    }

    @Override
    public float positionEvalution(Position p, boolean player) {
        return 0;
    }

    @Override
    public void printPosition(Position p) {

    }

    @Override
    public boolean reachedMaxDepth(Position p, int depth) {
        boolean ret = false;
        if(depth>=5) return true;
        if(wonPosition(p,false)) ret=true;
        if(wonPosition(p,true)) ret=true;
        if(drawPosition(p)) ret=true;
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
                    if(i==0 && j==5 || i==1 && j==5 || i==2 && j==5 || i==3 && j==5 || i==4 && j==5 || i==5 && j==5) {
                        if(GameSearch.DEBUG){
                            System.out.println("adjacent null !");
                        }
                    }
                 else  if(pos.board[i][j]==DomineeringPosition.BLANK){
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


    @Override
    public Position makeMove(Position p, boolean player, Move move) {
        if(GameSearch.DEBUG) System.out.println("Entred Domineering.makeMove");
        DomineeringMove mv = (DomineeringMove)move;
        DomineeringPosition pos = (DomineeringPosition)p;
        DomineeringPosition pos2 = affecterPosition(pos);
        if(isAdjExist(p,player,move)){
            int pp;
            if (player){
                pp=1;
                pos2.board[mv.moveIndexRow][mv.moveIndexColl]=pp;
                pos2.board[mv.moveIndexRow+1][mv.moveIndexColl]=pp;
            }else{
                pp=-1;
                pos2.board[mv.moveIndexRow][mv.moveIndexColl]=pp;
                pos2.board[mv.moveIndexRow][mv.moveIndexColl+1]=pp;
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
    public Move createMove() {
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



   /**---------- methodes complementaires ----------*/

   public CellPanel searchAdjCell(Coordinates coordinates,boolean player){
       Map<CellPanel, Coordinates> map =  ComponentPanel.getMapCellPanel();
          if(player){ //search vertical

              for (Map.Entry<CellPanel, Coordinates> item : map.entrySet()) {
                   if(item.getValue().row==coordinates.row+1 && item.getValue().col==coordinates.col && !item.getKey().clicked){
                       return item.getKey();
                   }
                   else return null;
              }

          }else{

              for (Map.Entry<CellPanel, Coordinates> item : map.entrySet()) {
                  if(item.getValue().row==coordinates.row && item.getValue().col==coordinates.col+1 && !item.getKey().clicked){
                      return item.getKey();
                  }
                  else return null;
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


    public static void main(String[] args) {
        Position pos = new DomineeringPosition();
        Domineering d = new Domineering();
         int [][] bord ;
          bord = new int[][]{{1,0,0,0,0,0},
                             {1,0,0,0,0,0},
                             {0,0,0,0,0,0},
                             {0,0,0,0,0,0},
                             {0,0,0,0,0,0},
                             {0,0,0,0,0,0}};
         ((DomineeringPosition)pos).board =bord;
        //d.possibleMoves(pos,false);

     /**
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
       */

            Move mv = d.createMove();
           Position poss =  d.makeMove(pos,false,mv);
            ((DomineeringPosition) poss).displayBoard();
    }




}
