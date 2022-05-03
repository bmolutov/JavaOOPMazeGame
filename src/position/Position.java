package position;


/*
x value stands for column
y value stands for row
*/


public class Position {
    private int y;
    private int x;
    
    public Position(int newX, int newY) {
        // when we create new position we firstly read col and then row
        this.y = newY;
        this.x = newX;
    }

    public void setX(int newX) {
        this.x = newX;
    }
    public void setY(int newY) {
        this.y = newY;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public boolean equals(Position another) {
        return another.getX() == this.getX() && another.getY() == this.getY();
    }
    @Override
    public String toString() {
        return String.format("(%d,%d)", this.x, this.y);
    }
}