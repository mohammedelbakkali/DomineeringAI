package GameSearchDomineering;

public class DomineeringPosition  extends Position{
      final static public int BLANK = 0;
      final static public int HUMAN = 1;
      final static public int PROGRAM = -1;

      public int[][] board ;

      public DomineeringPosition(){
          board = new int[6][6];
      }


      public void displayBoard(){
          for(int i = 0 ;  i< 6 ; i++){
              for(int j = 0 ; j < 6 ; j++){
                 System.out.print(""+board[i][j]+" ");
              }
              System.out.println("");
          }
      }
}
