package game;
import java.util.*;
import player.Player;


public class Game {
    private ArrayList<Player> players;
   
    public Game() {
        this.players = new ArrayList<>();
    }
    public void addPlayer(Player newPlayer) {
        this.players.add(newPlayer);
    }
}