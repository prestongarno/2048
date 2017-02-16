import game.Cell;

/**
 * Created by preston on 2/15/17.
 */
public class strategies {
    public interface GameObserver {
        void onMoved(int previousX, int previousY, Cell current);
        void onCellsCombined(Cell from, Cell to);
    }
    public interface GameObservable{
        void registerObserver(GameObserver gameObserver);
    }
}
