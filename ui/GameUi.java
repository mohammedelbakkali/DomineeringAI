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
import java.util.Vector;

public class GameUi {
        public JLabel turnLabel2 = new JLabel("----");
        private String selectedMode;
        public ArrayList<Position> listPrtiesSave;
        public boolean wantHelp = false;
        public Boolean humanVsHuman = false;
        public Boolean humanVsProgram = false;

        public GameUi(ComponentPanel p){
            listPrtiesSave = new ArrayList<>();
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JFrame frame1 = new JFrame("Domineering Game");
                    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame1.setLayout(new BorderLayout());
                    frame1.setSize(1100, 700);

                    JPanel panel = new JPanel(new BorderLayout());
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                    // Welcome text
                    JLabel labelText1 = new JLabel("Welcome to");
                    labelText1.setAlignmentX(Component.CENTER_ALIGNMENT);
                    labelText1.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0)); // Adjust top padding
                    labelText1.setFont(new Font("Arial", Font.BOLD, 20));
                    panel.add(labelText1);

                    JLabel labelText2 = new JLabel("<html><font color='#C35E61'>Domineering</font> <font color='#7EB6E9'>Game</font></html>");
                    labelText2.setAlignmentX(Component.CENTER_ALIGNMENT);
                    labelText2.setHorizontalAlignment(JLabel.CENTER); // Set horizontal alignment
                    labelText2.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
                    labelText2.setFont(new Font("Arial", Font.BOLD, 30));
                    panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add vertical spacing
                    panel.add(labelText2);


                    // Combo box
                    String niveau[] = {"  mode   ", "Human vs Human", "Human vs AI (Easy)", "Human vs AI (Medium)", "Human vs AI (Hard)"};
                    final JComboBox cb = new JComboBox(niveau);
                    cb.setPreferredSize(new Dimension(150, 20));
                    cb.setAlignmentX(Component.CENTER_ALIGNMENT);
                    cb.setMaximumSize(new Dimension(300, 30));
                    panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add vertical spacing
                    panel.add(cb);

                    // Start button
                    JButton nextPageButton = new JButton("Start");
                    nextPageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                    nextPageButton.setPreferredSize(new Dimension(150, 50));
                    nextPageButton.addActionListener(e -> {
                        Domineering.choos = true;
                        System.out.println("change choose");
                        frame1.dispose();
                        showPage2(p);
                    });
                    panel.add(Box.createRigidArea(new Dimension(0, 20))); // Add vertical spacing
                    panel.add(nextPageButton);

                    cb.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String selectedValue = (String) cb.getSelectedItem();
                            selectedMode = (String) cb.getSelectedItem();
                        }
                    });

                    frame1.add(panel, BorderLayout.CENTER);
                    frame1.setLocationRelativeTo(null);
                    frame1.setVisible(true);
                }
            });
        }



    private void showPage2(ComponentPanel p) {


        JFrame frame = new JFrame("Domineering Game");
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

        // Ajouter un écouteur d'événements pour le menu Quitter
        partie1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //  DisplayPanelBord(p);
                System.out.println("save la partie");
                listPrtiesSave.add(GameSearch.forSave);

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
        menuBar.add(listeJeu);
        // Définir la barre de menu pour le frame
        frame.setJMenuBar(menuBar);

        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton helpButton = new JButton("request help");
        helpButton.setPreferredSize(new Dimension(150, 30));

        helpButton.addActionListener(e -> {
            System.out.println("Asking for help...");
            if (GameSearch.helpRequestsRemaining > 0) {
                GameSearch.helpRequestsRemaining--;
                wantHelp = true;
                System.out.println("Help provided!");
                System.out.println("Remaining helps: " + GameSearch.helpRequestsRemaining);
            } else {
                System.out.println("No remaining helps :(");
            }
        });

        eastPanel.add(helpButton);
        frame.add(eastPanel, BorderLayout.EAST);

        // Create a panel for the turn indication
        JPanel turnPanel = new JPanel();
        turnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

// Create JLabels for each word
        JLabel turnLabel1 = new JLabel("It's");
        turnLabel1.setFont(new Font("Arial", Font.BOLD, 20));
        turnLabel2.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel turnLabel3 = new JLabel("turn");
        turnLabel3.setFont(new Font("Arial", Font.BOLD, 20));


// Customize the labels as needed
// ...

// Add JLabels to the turn panel
        turnPanel.add(turnLabel1);
        turnPanel.add(turnLabel2);
        turnPanel.add(turnLabel3);



        // Add the turn label to your frame
        frame.add(turnPanel, BorderLayout.NORTH);





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
    public boolean getWantHelp() {
        return wantHelp;
    }
}
