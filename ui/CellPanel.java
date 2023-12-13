package GameSearchDomineering.ui;

import GameSearchDomineering.Domineering;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


import static GameSearchDomineering.Domineering.searchAdjCell;

public class CellPanel extends JPanel {

    private Color defaultBackground;
    private Coordinates coordinates;
    public  boolean clicked = false; //
    public static boolean isClickedPanel = false; //
    private ComponentPanel componentPanel;
    private static int counterVertical= 0 ;
    private static  int counterHorizontal= 0;
    public static java.util.List<CellPanel> possiblesMovesVerticale;
    public static List<CellPanel> possiblesMovesHorizontal;
    public CellPanel adjTrue;
    public CellPanel adjFalse;
    public static boolean role;
    public static Coordinates coordinatesPointer;

   public Domineering p;
    public CellPanel(ComponentPanel componentPanel,Coordinates coordinates,boolean clicked) {
               this.clicked=clicked;
               this.componentPanel=componentPanel;
               this.coordinates=coordinates;
               eventMouseTraitement();
               p=new Domineering();
    }



    @Override
    public Dimension getPreferredSize(){
        return new Dimension(100,100);
    }

  public  void eventMouseTraitement(){
        defaultBackground = getBackground();
        addMouseListener(new MouseAdapter() {


            @Override
            public void mouseClicked(MouseEvent e) {

                super.mouseClicked(e);
                if(role){

                    adjTrue=searchAdjCell(coordinates,true);
                    if(adjTrue!=null){
                        isClickedPanel=true;
                        setBackground(Color.BLUE);
                        adjTrue.setBackground(Color.BLUE);
                        clicked=true;
                        coordinatesPointer=coordinates;
                        adjTrue.setClicked(true);

                    }
                }else{


                    adjFalse=searchAdjCell(coordinates,false);
                    if(adjFalse!=null){
                        isClickedPanel=true;
                        setBackground(Color.GREEN);
                        adjFalse.setBackground(Color.GREEN);
                        clicked=true;
                        adjFalse.setClicked(true);
                        coordinatesPointer=coordinates;
                    }else{
                        System.out.println("null");
                    }
                }


            }

            // minmax();


            @Override
            public void mouseEntered(MouseEvent e) {
                if(role && !clicked){

                    adjTrue=searchAdjCell(coordinates,true);
               if(adjTrue!=null){
                   setBackground(Color.BLUE);
                   adjTrue.setBackground(Color.BLUE);
               }
                }else if(!role && !clicked){

                    adjFalse=searchAdjCell(coordinates,false);
                    if(adjFalse!=null){
                        setBackground(Color.GREEN);
                        adjFalse.setBackground(Color.GREEN);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {

                 if(role && !clicked){
                     if(adjTrue!=null){
                         adjTrue.setBackground(defaultBackground);
                         setBackground(defaultBackground);
                     }
                 }else if(!role && !clicked){
                     if(adjFalse != null){
                         setBackground(defaultBackground);
                         adjFalse.setBackground(defaultBackground);
                     }
                 }

                }

        });
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public static void setRole(boolean role) {
        CellPanel.role = role;
    }
}

