package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by preston on 2/7/17.
 */
public class Manager implements NumberSlider
{
    private int[][] board;
    private int winningValue;
    private static final Random random = new Random();
    private final Map<Integer, Cell> activeCells;

    public Manager(int rows, int columns){
        this.board = new int[rows][columns];
        this.winningValue = 2048;
        activeCells = new HashMap<Integer, Cell>(12);
    }

    @Override
    public void resizeBoard(int height, int width, int winningValue) {
    }

    private void setWinningValue(int winningValue)
    {
        for (int i = 2; i < winningValue; i = i*2) {
            if(i == winningValue) {
               this.winningValue = winningValue;
            }
            throw new IllegalArgumentException("Winning value must be a multiple of 2!");
        }
    }

    @Override
    public void reset() {
        for (int c = 0; c < board.length; c++) {
            for (int r = 0; r < board[c].length; r++) {
                int temp = board[c][r];
                Cell atTemp = this.getCell(r, c);
            }
        }
        this.placeRandomValue();
    }

    private Cell getCell(int r, int c) {
        return activeCells.get("Cell " + r + ", " + c);
    }

    @Override
    public void setValues(int[][] ref) {
        for (int r = 0; r < board.length; r++) {
            if(ref[r].length != this.board[r].length)
            {
                throw new IllegalArgumentException("Array at [ "+ r + ", 0 ]" +"doesn't match the game board");
            }
            for (int c = 0; c < board[c].length; c++) {
                board[r][c] = ref[r][c];
            }
        }
    }

    @Override
    public Cell placeRandomValue() {
        int row, column;
        Cell randomCell = null;
        while (randomCell != null){
            row = random.nextInt(board.length);
            column = random.nextInt(board[row].length);
            if(this.board[row][column] == 0){
                int value;
                if(System.currentTimeMillis() % 2 == 0)
                {
                    value = 2;
                } else {
                    value = 4;
                }

                this.board[row][column] = value;
                randomCell = new Cell(row, column, value);

            }
        }
        return randomCell;
    }

    @Override
    public boolean slide(SlideDirection dir) {
        if(dir != SlideDirection.DOWN){
            throw new UnsupportedOperationException("Only implemented for Down direction");
        }
        //iterate through hashmap values
        //
        return true;
    }

    @Override
    public ArrayList<Cell> getNonEmptyTiles() {
        return null;
    }

    @Override
    public GameStatus getStatus() {
        return null;
    }

    @Override
    public void undo() {

    }
}
