package GameSearchDomineering.ui;

import GameSearchDomineering.DomineeringPosition;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ComponentPanel extends JPanel {
       static Map<CellPanel,Coordinates> mapCellPanel = new LinkedHashMap<>();
    private GameController gameController; // Add this field
    public static DomineeringPosition    domineeringPosition;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }



    public void resetPanel() {

        for (Map.Entry<CellPanel, Coordinates> entry : mapCellPanel.entrySet()) {
            CellPanel cellPanel = entry.getKey();
            cellPanel.setBackground(Color.lightGray);
            cellPanel.setClicked(false);
        }
    }


       public ComponentPanel(){
           domineeringPosition=new DomineeringPosition();
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

    public static void DisplayPanelBord() {
        // Récupérer la carte d'EventPane à Position depuis TestPane
        Map<CellPanel, Coordinates> map = getMapCellPanel();

        // Afficher les coordonnées de chaque EventPane
        for (Map.Entry<CellPanel, Coordinates> entry : map.entrySet()) {
           // System.out.println("Valeur x : " + entry.getValue().row + " Valeur Y : " + entry.getValue().col);
            //entry.getKey().cliked=false;

            Color customColor = new Color(238, 238, 238);

            entry.getKey().setBackground(Color.BLUE);

        }
    }


    public static Map<CellPanel, Coordinates> getMapCellPanel() {
        return mapCellPanel;
    }


    public static CellPanel getCellPanel(Coordinates coordinates){
        Map<CellPanel, Coordinates> map = getMapCellPanel();

        for(Map.Entry<CellPanel,Coordinates> item :map.entrySet()){
            System.out.println(item.getValue().row+";"+item.getValue().col);
            if(item.getValue().row==coordinates.row && item.getValue().col==coordinates.col && !item.getKey().clicked){
                return item.getKey();
            }
        }
        return null;
    }

    public void updateUI(DomineeringPosition currentPosition) {
        for (Map.Entry<CellPanel, Coordinates> entry : mapCellPanel.entrySet()) {
            int row = entry.getValue().row;
            int col = entry.getValue().col;
            int value = currentPosition.board[row][col];
            Color customColor = new Color(238, 238, 238);

            if (value == DomineeringPosition.HUMAN) {
                entry.getKey().setBackground(Color.BLUE);
            } else if (value == DomineeringPosition.PROGRAM) {
                entry.getKey().setBackground(Color.GREEN);
            } else {
                entry.getKey().setBackground(customColor);
            }
        }
    }
    public void setCellColor(int row, int col, Color color) {
        CellPanel cellPanel = findCellPanel(row, col);
        if (cellPanel != null) {
            cellPanel.setBackground(color);
            repaint(); // Assurez-vous de redessiner le panneau après la mise à jour
        }
    }

    private CellPanel findCellPanel(int row, int col) {
        for (Map.Entry<CellPanel, Coordinates> entry : mapCellPanel.entrySet()) {
            if (entry.getValue().row == row && entry.getValue().col == col) {
                return entry.getKey();
            }
        }
        return null;
    }

}
