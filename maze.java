import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main {
    static State initial = new State (null , 0,0,0);
    static State goal = new State(null , 0 , 1 , 0);
    static ArrayList pathFromGoaltoInit = new ArrayList();
    static final int m = 10; // rows
    static final int n = 10; // columns
    private static queue frontier = new queue(1000000);
    private static queue closedList = new queue(1000000);
    static State[][] states = new State[m][n];
    static WallX[][] WALLX = new WallX[m - 1][n];
    static WallY[][] WALLY = new WallY[m][n - 1];
    static State runAStar() {
        frontier.enqueue(initial);
        State current;
        while (!frontier.isEmpty()) {
            current = frontier.dequeue();
            current.expandChildren();
            for (State s : current.getChildren()) {
                if (s.matchGoal()) {
                    return s;
                }
                s.addIfWorthy();
            }
            closedList.enqueue(current);
        }
        return null;
    }
    public static State getGoal() {
        return goal;
    }
    public static queue getFrontier() {
        return frontier;
    }
    public static queue getClosedList() {
        return closedList;
    }

    public static void main(String[] args)
    {
        runAStar();
    }

}
class State {
    private Set<State> children;
    private State parent;
    private int row;
    private int col;
    private int g;

    public State(State parent, int row, int col, int g) {
        this.parent = parent;
        this.row = row;
        this.col = col;
        this.g = g;
    }

    public Set<State> getChildren() {
        return children;
    }


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getG() {
        return g;
    }


    int getH() {
        return Math.abs(row - Main.goal.row) + Math.abs(col - Main.goal.col);
    }

    int getF() {
        return g + getH();
    }

    public boolean matchGoal() {
        State goal = Main.goal;
        if (row == goal.getRow() && col == goal.getCol()) {
            return true;
        }
        return false;
    }

    void expandChildren() {
        children = new HashSet<State>();
        //left wall
            if (!Main.WALLY[row][col - 1].isHasWall()) {
                children.add(new State(this, row, col - 1, g + 1));
            }
        //right wall
            if (!Main.WALLY[row][col].isHasWall()) {
                children.add(new State(this, row, col + 1, g + 1));
            }
        //bottom wall
            if (!Main.WALLX[row - 1][col].isHasWall()) {
                children.add(new State(this, row - 1, col, g + 1));
            }
        //up wall
            if (!Main.WALLX[row][col].isHasWall()) {
                children.add(new State(this, row + 1, col, g + 1));
            }

    }
    public boolean worthAdding() {
        queue frontier = Main.getFrontier();
        queue closedList = Main.getClosedList();
        State sameOnFrontier = frontier.getSameSuccessor(this);
        State sameOnClosed = closedList.getSameSuccessor(this);

        if (sameOnFrontier != null && sameOnFrontier.getF() <= this.getF()) {
            return false;
        } else if (sameOnClosed != null && sameOnClosed.getF() <= this.getF()) {
            return false;
        }
        // Otherwise
        return true;
    }

    public void addIfWorthy() {
        if (worthAdding()) {
            Main.getFrontier().enqueue(this);
        }
    }

}
abstract class Wall {

    public boolean hasWall;
    private int row;
    private int col;

    public Wall(int row, int col) {
        this.hasWall = true;
        this.row = row;
        this.col = col;
    }

    public boolean isHasWall() {
        return hasWall;
    }


}
class WallX extends Wall {

    public WallX(int row, int col) {
        super(row,col);
    }
}
class WallY extends Wall {

    public WallY(int row, int col) {
        super(row, col);
    }

}
class queue {
    State data[];
    public int size = 0;
    public queue(){
        data = new State[100];
    }
    public queue(int size){
        data = new State[size];
    }
    public void enqueue(State element) {
        if (size == data.length) {
            return;
        }
        data[(size)] = element;
        size++;
        sort();
    }


    void sort()
    {

        for (int i = 1; i < data.length; ++i) {
            State key = data[i];
            int j = i - 1;
            while (j >= 0 && data[j].getF() > key.getF()) {
                data[j + 1] = data[j];
                j = j - 1;
            }
            data[j + 1] = key;
        }
    }
    public State dequeue() {
        if (size == 0) {
            return null;
        }
        State result = data[ size - 1 ];
        size--;
        return result;
    }

    public State getSameSuccessor(State element) {
        int i = 0;
        int iterated = 0;
        while (iterated < size && data[i] != null) {
            if (data[i].getRow() == element.getRow() && data[i].getCol() == element.getCol()) {
                return data[i];
            }

            i++;
            iterated++;
        }

        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }
}
