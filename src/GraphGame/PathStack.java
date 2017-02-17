package GraphGame;

/**
 * Created by preston on 2/16/17.
 */
public class PathStack {
    private int maxSize;
    private Cell[] stackArray;
    private int top;

    public PathStack(int maxSize) {
        this.maxSize = maxSize;
        stackArray = new Cell[this.maxSize];
        top = -1;
    }
    public void push(Cell c) {
        stackArray[++top] = c;
    }
    public Cell pop() {
        return stackArray[top--];
    }
    public Cell getTop() {
        return stackArray[top];
    }
    public boolean isEmpty() {
        return (top == -1);
    }
    public boolean isFull() {
        return (top == maxSize - 1);
    }
}
