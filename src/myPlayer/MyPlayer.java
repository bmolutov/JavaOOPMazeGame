package myPlayer;
import map.Map;
import player.Player;
import position.Position;


/*
x value stands for column
y value stands for row
*/


public class MyPlayer implements Player {
    private Map map;
    private int n;
    private int x;
    private int y;
    private Position position;
    
    public void setMap(Map newMap) {
        this.map = newMap;
        this.n = this.map.getSize();
        outer:
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(this.map.getValueAt(i, j) == 'P') {
                    this.y = i;
                    this.x = j;
                    break outer;
                }
            }
        }
    }
    public void moveRight() {
        if(this.x + 1 <= this.n - 1 && this.map.getValueAt(this.y, this.x + 1) != '1') {
            this.x += 1;
        }
    }
    public void moveLeft() {
        if(this.x - 1 >= 0 && this.map.getValueAt(this.y, this.x - 1) != '1') {
            this.x -= 1;
        }
    }
    public void moveUp() {
        if(this.y - 1 >= 0 && this.map.getValueAt(this.y - 1, this.x) != '1') {
            this.y -= 1;
        }
    }
    public void moveDown() {
        if(this.y + 1 <= this.n - 1 && this.map.getValueAt(this.y + 1, this.x) != '1') {
            this.y += 1;
        }
    }
    public Position getPosition() {
        this.position = new Position(this.x, this.y);
        return this.position;
    }
}