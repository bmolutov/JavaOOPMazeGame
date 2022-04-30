import game.Game;
import invalidMapException.InvalidMapException;
import map.Map;
import myPlayer.MyPlayer;
import playerInfo.PlayerInfo;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.*;


public class Main {
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) throws InvalidMapException, FileNotFoundException{

        // welcome message
        welcome();

        // setting difficulty of the game
        String difficulty = setDifficulty();
       
        // given time info
        long givenTime = setGivenTime(difficulty);

        // creating main entities 
        MyPlayer myPlayer = new MyPlayer(new Map(difficulty));
        Game game = new Game();
        game.addPlayer(myPlayer);

        // getting user's credentials
        getUserCredentials(myPlayer);

        // setting starting time
        Date start = new Date(System.currentTimeMillis());

        // game variables
        boolean won = false;

        // game cycle
        while(true) {
            // game controls info
            gameControlsInfo(difficulty, givenTime);

            // map printing
            myPlayer.map.print();

            // elapsed time
            long elapsedTime = (System.currentTimeMillis() - start.getTime()) / 1000;

            // remaining time
            long remainingTime = givenTime - elapsedTime;
            
            // check if we have remaining time
            if(remainingTime <= 0 && won == false) {
                defeat(difficulty);
                break;
            }

            // game process info and taking user input for the next move
            System.out.printf("\tRemaining time: %d\n", remainingTime);
            System.out.print("\tThe next move: ");
            char checker = in.next().charAt(0);
            System.out.println();

            // command processing
            if(checker == 'w') {
                myPlayer.moveUp();
            } else if(checker == 's') {
                myPlayer.moveDown();
            } else if(checker == 'a') {
                myPlayer.moveLeft();
            } else if(checker == 'd') {
                myPlayer.moveRight();
            } else if(checker == 'q') {
                break;
            }

            // check if we've reached destination
            if(myPlayer.map.x == myPlayer.map.dx && myPlayer.map.y == myPlayer.map.dy) {
                won = true;
                victory(difficulty, elapsedTime, myPlayer);
                System.exit(0);
            }
        }
        System.out.println(myPlayer.playerInfo.getLogin());
        in.close();
    }    
    public static void getUserCredentials(MyPlayer myPlayer) {
        System.out.println("\n\tPlease enter your login, name separated by space");
        System.out.print("\n\tYour input: ");
        String login = in.next(), name = in.next();
        if(isEmail(login)) {
            PlayerInfo<String> playerInfo = new PlayerInfo<String>(login, name, 100);
            myPlayer.setPlayerInfo(playerInfo);
        } else if(isTel(login)) {
            PlayerInfo<Long> playerInfo = new PlayerInfo<Long>(Long.parseLong(login), name, 100);
            myPlayer.setPlayerInfo(playerInfo);
        }
        while(!isEmail(login) && !isTel(login)) {
            System.out.println("\tPlease, type the correct one!");
            System.out.print("\n\tYour input: ");
            login = in.next();
            name = in.next();
        }
    }
    public static boolean isEmail(String raw) {
        return (raw.matches("^[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]+$")) ? true : false;
    }
    public static boolean isTel(String raw) {
        return (raw.matches("^[0-9]{11}$")) ? true : false;
    }
    public static void victory(String difficulty, long elapsedTime, MyPlayer myPlayer) {
        for(int i = 0; i < 100; i++) System.out.println();
        System.out.printf("Congratulations! You have passed * %s * level.", difficulty);

        // check if the player broke the record
        long record1 = myPlayer.playerInfo.getRecord();
        long record2 = elapsedTime;
        if(record2 < record1) {
            String oldValue = String.format("%s %s %d", (String) myPlayer.playerInfo.getLogin(), myPlayer.playerInfo.getName(), record1);
            String newValue = String.format("%s %s %d", (String) myPlayer.playerInfo.getLogin(), myPlayer.playerInfo.getName(), record2);
            // copying content from database.txt to temp.txt without containing oldValue
            // appending newValue to temp.txt
            try {
                File database = new File("./database/database.txt");
                File temp = new File("./database/temp.txt");

                Scanner in = new Scanner(database);
                FileWriter out = new FileWriter(temp);

                out.write("");
                while(in.hasNextLine()) {
                    String data = in.nextLine();
                    if(!data.equals(oldValue)) {
                        out.append(data + "\n");
                    }
                }
                out.append(newValue + "\n");

                in.close();
                out.close();
            } catch(IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        // returning data back into database.txt with updated values
        try {
            File database = new File("./database/database.txt");
            File temp = new File("./database/temp.txt");
            
            Scanner in = new Scanner(temp);
            FileWriter out = new FileWriter(database);

            out.write("");
            while(in.hasNextLine()) {
                out.append(in.nextLine() + "\n");
            }

            in.close();
            out.close();
        } catch(IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    public static void defeat(String difficulty) {
        for(int i = 0; i < 100; i++) System.out.println();
        System.out.printf("Oh! You failed to pass * %s * level.", difficulty);
    }
    public static void welcome() {
        for(int i = 0; i < 100; i++) System.out.println();
        System.out.println("***************************************************");
        System.out.println("***                                             ***");
        System.out.println("***                   Maze Game                 ***");
        System.out.println("***                                             ***");
        System.out.println("***                                             ***");
        System.out.println("***   Made by Daniyar Absatov, Bekzat Molutov   ***");
        System.out.println("***                                             ***");
        System.out.println("***************************************************");

        printTopTen();
        
        System.out.println("\n\n\tTo select difficulty level of the game\n");
        System.out.println("\tHard -> press h\n\tMedium -> press m\n\tEasy -> press e");
    }
    public static void printTopTen() {
        ArrayList<PlayerInfo> arr = new ArrayList<>();
        try {
            File database = new File("./database/database.txt");
            Scanner in = new Scanner(database);
            while(in.hasNextLine()) {
                String row = in.nextLine();
                String[] data = row.split("\\s");
                if(isEmail(data[0])) {
                    arr.add(new PlayerInfo<String>(data[0], data[1], Long.parseLong(data[2])));
                } else if(isTel(data[0])) {
                    arr.add(new PlayerInfo<Long>(Long.parseLong(data[0]), data[1], Long.parseLong(data[2])));
                }
            }
            in.close();
        } catch(FileNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
        }
        Collections.sort(arr);
        System.out.println("\n\n\tTop five players of the game\n");
        for(int i = 0; i < 5; i++) {
            System.out.printf("\t%s %s %d\n", arr.get(i).getLogin(), arr.get(i).getName(), arr.get(i).getRecord());
        }
    }
    public static String setDifficulty() {
        System.out.print("\n\tYour choice: ");
        char init = in.nextLine().charAt(0);
        while(init != 'h' && init != 'm' && init != 'e') {
            System.out.println("\tPlease, choose the correct one!");
            System.out.print("\n\tYour choice: ");
            init = in.nextLine().charAt(0);
        }
        return (init == 'h') ? "hard" : (init == 'm') ? "medium" : "easy";
    }
    public static long setGivenTime(String difficulty) {
       return (difficulty == "hard") ? 30L : (difficulty == "medium") ? 45L : 60L;
    }
    public static void gameControlsInfo(String difficulty, long givenTime) {
        for(int i = 0; i < 100; i++) System.out.println();
        System.out.println("\n\tThe game has started!\n");
        System.out.println("\n\tYour task is to reach point D!");
        System.out.printf("\tYou've chosen * %s * level, you have * %d * seconds for passing it!\n", difficulty, givenTime);
        System.out.println("\n\tto go (up/down/left/right) press ('w'/'s'/'a'/'d') + ENTER respectively");
        System.out.println("\tto exit the game press 'q' ENTER\n");
    }
}
