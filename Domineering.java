package GameSearchDomineering;

import GameSearchDomineering.ui.CellPanel;
import GameSearchDomineering.ui.ComponentPanel;
import GameSearchDomineering.ui.Coordinates;

import java.util.Map;

public class Domineering extends GameSearch{

    @Override
    public boolean drawPosition(Position p) {

        return false;
    }

    @Override
    public boolean wonPosition(Position p, boolean player) {
        return false;
    }

    @Override
    public float positionEvalution(Position p, boolean player) {
        return 0;
    }

    @Override
    public void printPosition(Position p) {

    }

    @Override
    public Position[] possibleMoves(Position p, boolean player) {
        if(player){ // vertical
            Map<CellPanel, Coordinates> map =  ComponentPanel.getMapCellPanel();

        }else{

        }
        return new Position[0];
    }


    @Override
    public Position makeMove(Position p, boolean player, Move move) {
        return null;
    }

    @Override
    public boolean reachedMaxDepth(Position p, int depth) {
        return false;
    }

    @Override
    public Move createMove() {
        return null;
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






}
