package GameSearchDomineering.ui;
import GameSearchDomineering.Domineering;
import GameSearchDomineering.DomineeringMove;
import GameSearchDomineering.DomineeringPosition;

import java.awt.*;
import java.io.IOException;
import java.util.Map;

import static GameSearchDomineering.ui.ComponentPanel.mapCellPanel;

public class GameController {

    private ComponentPanel componentPanel;
    private Domineering domineering;
    private DomineeringPosition currentPosition;

    public GameController(ComponentPanel componentPanel) {
        this.componentPanel = componentPanel;
        this.domineering = new Domineering();
        this.currentPosition = new DomineeringPosition();
        this.componentPanel.setGameController(this);
    }

    public void startGame() {
        try {
            domineering.playGameHumenVsHuman(currentPosition, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetGame() {
        currentPosition = new DomineeringPosition();
        componentPanel.resetPanel(); // Implement this method in ComponentPanel to reset the UI
    }



    public void makeMove(DomineeringMove move) {
        currentPosition = (DomineeringPosition) domineering.makeMove(currentPosition, true, move);
        componentPanel.updateUI(currentPosition); // Implement this method in ComponentPanel to update the UI
    }




    // Add more methods as needed for game control and UI interaction
}
