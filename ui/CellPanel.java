package GameSearchDomineering.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CellPanel extends JPanel {

    private Color defaultBackground;
    private Coordinates coordinates;
    public boolean clicked; //
    private ComponentPanel componentPanel;
    private static int counterVertical= 0 ;
    private static  int counterHorizontal= 0;
    public static java.util.List<CellPanel> possiblesMovesVerticale;
    public static List<CellPanel> possiblesMovesHorizontal;



    public CellPanel(ComponentPanel componentPanel,Coordinates coordinates,boolean clicked) {
               this.clicked=clicked;
               this.componentPanel=componentPanel;
               this.coordinates=coordinates;
    }




}

