package GameSearchDomineering.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ComponentPanel extends JPanel {
       static Map<CellPanel,Coordinates> mapCellPanel = new LinkedHashMap<>();

       public ComponentPanel(){

           setLayout(new GridBagLayout());
           GridBagConstraints  gbc = new GridBagConstraints();

           for(int row = 0; row<6 ; row++){
               for(int col = 0 ; col < 6 ; col++){
                   gbc.gridx=col;
                   gbc.gridy = row;
                   CellPanel cellPanel = new CellPanel(this,new Coordinates(row,col),false);

                   Border border = null;
                   border = new MatteBorder(1, 1, 1, 1, Color.lightGray);
                   String position = ""+row+"; "+col;
                   JLabel label = new JLabel(position);
                   cellPanel.add(label);
                   cellPanel.setBorder(border);
                   mapCellPanel.put(cellPanel,new Coordinates(row,col));
                   add(cellPanel,gbc);

               }
           }


       }


    public static Map<CellPanel, Coordinates> getMapCellPanel() {
        return mapCellPanel;
    }
}
