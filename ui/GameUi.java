package GameSearchDomineering.ui;

import GameSearchDomineering.Domineering;
import GameSearchDomineering.DomineeringPosition;
import GameSearchDomineering.GameSearch;
import GameSearchDomineering.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameUi {
    private String selectedMode;

       public ArrayList<Position> listPrtiesSave;

         public Boolean humanVsHuman = false;
         public Boolean humanVsProgram = false;


        public GameUi(ComponentPanel p){
            listPrtiesSave = new ArrayList<>();
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // Première fenêtre
                    JFrame frame1 = new JFrame("Page 1");
                    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame1.setLayout(new BorderLayout());
                    frame1.setSize(600, 500);
                    frame1.setLayout(new FlowLayout(FlowLayout.CENTER)); // Utilisez FlowLayout avec alignement au centre
                    JButton nextPageButton = new JButton("Go to Page 2");
                    nextPageButton.setPreferredSize(new Dimension(150, 50));
                    nextPageButton.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Lorsque le bouton est cliqué, passer à la deuxième page
                            Domineering.choos=true;
                            System.out.println("change choos");
                            frame1.dispose(); // Fermer la première fenêtre
                            showPage2(p); // Afficher la deuxième fenêtre
                        }
                    });

                    frame1.add(nextPageButton, BorderLayout.CENTER);


                    final JLabel label = new JLabel();
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setSize(400,100);
                    JButton b=new JButton("Show");
                    b.setBounds(200,100,75,20);
                    String niveau[]={"  mode   ","Human vs Human","Human vs Program(EASY)","Human vs Program(MEDIUM)","Human vs Program(hard)"};
                    final JComboBox cb=new JComboBox(niveau);
                    cb.setSize(100,100);
                    cb.setBounds(50, 100,90,20);
                    frame1.add(cb); frame1.add(label); frame1.add(b);


                    b.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String data = "Programming language Selected: "
                                    + cb.getItemAt(cb.getSelectedIndex());
                            label.setText(data);
                        }});


                     // Créer un ActionListener pour le JComboBox
                    cb.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Récupérer la valeur sélectionnée
                            String selectedValue = (String) cb.getSelectedItem();
                             selectedMode = (String) cb.getSelectedItem();
                            // Comparaison
                        }
                    });





                        // Pack et afficher le frame
                  //  frame1.pack();
                    frame1.setLocationRelativeTo(null);
                    frame1.setVisible(true);
                }
            });
        }



    private void showPage2(ComponentPanel p) {


        JFrame frame = new JFrame("Domineering");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(p, BorderLayout.CENTER);
        // Pack et afficher le frame
        //=======================================

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("menu");
        JMenu sauvegarder = new JMenu("Sauvegarder");
        JMenu comeBack  = new JMenu("comeback");
        JMenu listeJeu  = new JMenu("liste des parties enregistrées");
        JMenuItem exitMenuItem = new JMenuItem("Quitter");
        JMenu partie1  = new JMenu("partie 1");
        //=======================================
        JMenuItem help_me = new JMenuItem("help me?");

        // Ajouter un écouteur d'événements pour le menu Quitter
        partie1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //  DisplayPanelBord(p);
                System.out.println("save la partie");
                listPrtiesSave.add(GameSearch.forSave);

            }
        });
        help_me.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //  DisplayPanelBord(p);

            }
        });

        // Ajouter les éléments de menu au menu
        listeJeu.add(partie1);
        fileMenu.add(exitMenuItem);

        //=======================================
        // Ajouter le menu Fichier à la barre de menu
        menuBar.add(fileMenu);
        menuBar.add(sauvegarder);
        menuBar.add(comeBack);
        menuBar.add(help_me);
        menuBar.add(listeJeu);
        // Définir la barre de menu pour le frame
        frame.setJMenuBar(menuBar);






        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    public boolean isHumanVsHuman() {
        return humanVsHuman;
    }

    public boolean isHumanVsProgram() {
        return humanVsProgram;
    }
    public String getSelectedMode() {
        return selectedMode;
    }
}
