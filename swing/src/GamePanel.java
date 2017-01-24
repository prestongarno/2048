import javax.swing.*;
import java.awt.*;

/**
 * Created by preston on 1/24/17.
 */
public class GamePanel extends JPanel {

    private int numRowsColumns;

    public GamePanel(Dimension tileDimension, int numRowsColumns)
    {
        this.numRowsColumns = numRowsColumns;
    }


    public void disable(boolean which)
    {

    }

    public void onComplete(int score){

    }

    public interface GameMovementListener
    {
        void onMove(Direction which);
    }

}
