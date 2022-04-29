package player;
import map.Map;
import position.Position;


public interface Player {
    public void moveRight();
    public void moveLeft();
    public void moveUp();
    public void moveDown();
    public void setMap(Map newMap);
    public Position getPosition();
}