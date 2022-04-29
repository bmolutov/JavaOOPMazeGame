package myPlayer;
import map.Map;
import player.Player;
import position.Position;


public class MyPlayer implements Player {
    public Map map;
    private Position position;

    public MyPlayer(Map newMap) {
        this.map = newMap;
        this.map.n = this.map.getSize();
        outer:
        for(int i = 0; i < this.map.n; i++) {
            for(int j = 0; j < this.map.n; j++) {
                if(this.map.getValueAt(i, j) == 'P') {
                    this.map.y = i;
                    this.map.x = j;
                    break outer;
                }
            }
        }
        this.map.x = newMap.x;
        this.map.y = newMap.y;
    }
    public void moveRight() {
        if(this.map.x + 1 <= this.map.n - 1 && this.map.getValueAt(this.map.y, this.map.x + 1) != '1') {
            this.map.x += 1;
        }
    }
    public void moveLeft() {
        if(this.map.x - 1 >= 0 && this.map.getValueAt(this.map.y, this.map.x - 1) != '1') {
            this.map.x -= 1;
        }
    }
    public void moveUp() {
        if(this.map.y - 1 >= 0 && this.map.getValueAt(this.map.y - 1, this.map.x) != '1') {
            this.map.y -= 1;
        }
    }
    public void moveDown() {
        if(this.map.y + 1 <= this.map.n - 1 && this.map.getValueAt(this.map.y + 1, this.map.x) != '1') {
            this.map.y += 1;
        }
    }
    public Position getPosition() {
        this.position = new Position(this.map.x, this.map.y);
        return this.position;
    }
}