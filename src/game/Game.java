package game;
import java.util.*;
import player.Player;
import map.Map;


public class Game {
    private Map map;
    private ArrayList<Player> players;
    
    public Game(Map newMap) {
        this.map = newMap;
        this.players = new ArrayList<>();
    }
    public void setMap(Map newMap) {
        this.map = newMap;
    }
    public void addPlayer(Player newPlayer) {
        newPlayer.setMap(this.map);
        this.players.add(newPlayer);
    }
}