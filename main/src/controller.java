import javax.swing.*;
import java.awt.*;

/**
 * Created by preston on 1/24/17.
 */
public class controller implements twentyfourtyeight.GameStateListener, GamePanel.GameMovementListener {
    //start the program
    public static void main(String[] args) {
        controller c = new controller(new twentyfourtyeight());
        c.start();
    }

    private twentyfourtyeight gameModel;

    private GamePanel panel;
    private twentyfourtyeight model;

    public controller(twentyfourtyeight gameModel) {
        this.gameModel = gameModel;
    }

    public void start(){
        this.panel = new GamePanel(new Dimension(40,40), 4);

        JFrame frame = new JFrame("2048 game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
    }

    @Override
    public void onGameCompleted(int finalScore) {
        panel.onComplete(finalScore);
    }

    @Override
    public void onMove(Direction which) {
        gameModel.move(which);
    }
}
