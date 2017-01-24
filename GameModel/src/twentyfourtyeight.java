/**
 * Created by preston on 1/24/17.
 */
public class twentyfourtyeight {

    private Tile[][] tiles;

    /**
     * starts the game with a 5x5 game
     */
    public twentyfourtyeight(){
        tiles = new Tile[5][5];
    }

    public twentyfourtyeight(int x, int y){
        tiles = new Tile[x][y];
    }

    public Tile getAt(int x, int y)
    {
        return tiles[x][y];
    }

    public void setAt(Tile newTileState, int x, int y)
    {
        tiles[x][y] = newTileState;
    }

    public interface GameStateListener
    {
        void onGameCompleted(int finalScore);
    }

    public int getHeight(){
        return tiles.length;
    }

    public int getWidth(){
        return tiles[0].length;
    }

    public void move(Direction direction) {
        //shift the tiles in given direction
    }
}
