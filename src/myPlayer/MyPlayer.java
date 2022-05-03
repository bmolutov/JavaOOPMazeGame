package myPlayer;
import map.Map;
import player.Player;
import position.Position;
import playerInfo.PlayerInfo;
import java.io.*;
import java.util.*;


public class MyPlayer implements Player {
    public Map map;
    public PlayerInfo<?> playerInfo;
    private Position position;

    public MyPlayer(Map newMap) {
        // setting map
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

    public void setPlayerInfo(PlayerInfo<?> newPlayerInfo) {
        this.playerInfo = newPlayerInfo;
        // checking if the player is present in database
        boolean isPresent = false;
        try {
            File file = new File("./database/database.txt");
            Scanner in = new Scanner(file);
            while(in.hasNextLine()) {
                String data = in.nextLine();
                String[] splitted = data.split("\\s");
                String temp = String.valueOf(newPlayerInfo.getLogin());
                if(splitted[0].equals(temp) && splitted[1].equals(newPlayerInfo.getName())) {
                    isPresent = true;
                }
            }
            in.close();
        } catch(FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        // actions depending on isPresent
        if(!isPresent) {
            try {
                FileWriter out = new FileWriter("./database/database.txt", true);
                String temp = String.valueOf(newPlayerInfo.getLogin());
                out.append(String.format("%s %s %d\n", temp, newPlayerInfo.getName(), 100));
                out.close();
            } catch(IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
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