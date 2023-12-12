package GameSearchDomineering.ui;

import GameSearchDomineering.Domineering;
import GameSearchDomineering.DomineeringPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
public class GameUi {

        public GameUi(ComponentPanel p){
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JFrame frame = new JFrame("Domineering");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setLayout(new BorderLayout());
                    frame.add(p, BorderLayout.CENTER);
                    // Pack et afficher le frame
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            });
        }

}
