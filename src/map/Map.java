package map;
import java.util.*;
import java.io.*;
import invalidMapException.InvalidMapException;


/*
x value stands for column
y value stands for row
x and y stand for position of player

dx and dy stand for coordinates destination point
*/


public class Map {
    public int n; 
    public int x;
    public int y;
    public int dx;
    public int dy;
    public String difficulty;
    private ArrayList<ArrayList<Character>> map;

    public Map(String difficulty) throws InvalidMapException, FileNotFoundException {
        try {
            this.difficulty = difficulty;
            File file = (difficulty == "hard") ? new File("./world/hard/world.txt") : 
                        (difficulty == "medium") ? new File("./world/medium/world.txt") : 
                        new File("./world/easy/world.txt");
            
            Scanner in = new Scanner(file);
            this.n = in.nextInt();
            this.map = new ArrayList<>();
            if(this.n == 0) {
                in.close();
                throw new InvalidMapException("Map size can not be zero");
            }
            // filling rows
            for(int i = 0; i < this.n; i++) {
                this.map.add(new ArrayList<Character>());
            }
            // reading map
            for(int i = 0; i < this.n; i++) {
                for(int j = 0; j < this.n; j++) {
                    if(!in.hasNext()) {
                        in.close();
                        throw new InvalidMapException("Not enough map elements");
                    }
                    char point = in.next().charAt(0);
                    this.map.get(i).add(point);
                }
            }
            // reading player's position
            this.x = in.nextInt();
            this.y = in.nextInt();
            // reading position of destination point
            this.dx = in.nextInt();
            this.dy = in.nextInt();
            in.close();
        } catch(FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public int getSize() {
        return this.n;
    }
    public char getValueAt(int atY, int atX) {
        // when we fetch value we firstly look at row value and then col value
        return this.map.get(atY).get(atX);
    }
    public void print() {
        for(int i = 0; i < this.n; i++) {
            System.out.print("\t");
            for(int j = 0; j < this.n; j++) {
                if(i == this.y && j == this.x) {
                    System.out.printf("%c ", 'P');
                } else if(i == this.dy && j == this.dx) {
                    System.out.printf("%c ", 'D');
                } else {
                    System.out.printf("%c ", this.map.get(i).get(j));
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}