// custom packages
import invalidMapException.InvalidMapException;
import map.Map;
import myPlayer.MyPlayer;
import playerInfo.PlayerInfo;

// Java API packages
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
        Long givenTime = setGivenTime(difficulty);

        // creating main entities 
        MyPlayer myPlayer = new MyPlayer(new Map(difficulty));

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
        in.close();
    }    
    /**
     * getting user's credentials
     * login: can represented as telephone number or email address
     * name: can any string
     */
    public static void getUserCredentials(MyPlayer myPlayer) {
        String login = "";
        String name = "";
        while(!isEmail(login) && !isTel(login)) {
            System.out.println("\n\tPlease enter your login (email | tel. number),\n\tname (any string) separated by space");
            System.out.print("\n\tYour input: ");
            login = in.next();
            name = in.next();
            PlayerInfo<?> playerInfo;
            if(isEmail(login)) {
                playerInfo = new PlayerInfo<String>(login, name, 100L);
                myPlayer.setPlayerInfo(playerInfo);
            } else if(isTel(login)) {
                playerInfo = new PlayerInfo<Long>(Long.valueOf(login), name, 100L);
                myPlayer.setPlayerInfo(playerInfo);
            }
        }
    }
    /** 
     * checking the string whether it is an email
     */
    public static boolean isEmail(String raw) {
        return (raw.matches("^[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]+$")) ? true : false;
    }
    /** 
     * checking the string whether it is a phone number of length 11
     */
    public static boolean isTel(String raw) {
        return (raw.matches("^[0-9]{11}$")) ? true : false;
    }
    /** 
     * if we have won
     * 1) firstly we got through lines of database, each line represented as user credentials separated by whitespace
     * 2) if our player broke it's record we update record
     * 
     * how rows update in database? - Just using extra text file
     * firstly copying there all lines besides old row with old record
     * then put there row with updated record
     * finally putting data into cleared database file
     */
    public static void victory(String difficulty, long elapsedTime, MyPlayer myPlayer) {
        for(int i = 0; i < 100; i++) System.out.println();
        System.out.printf("\tCongratulations! You have passed * %s * level.\n", difficulty);
        System.out.println("\n\tYour result was saved in our database!\n\n\n");
        // traversing database
        try {
            File database = new File("./database/database.txt");
            File temp = new File("./database/temp.txt");
            Scanner in = new Scanner(database);
            FileWriter out = new FileWriter(temp);
            out.write("");
            while(in.hasNextLine()) {
                String[] data = in.nextLine().split("\\s");
                if(data.length <= 1) {
                    continue;
                }
                String login = data[0];
                String name = data[1];
                if((login + " " + name).equals(String.valueOf(myPlayer.playerInfo.getLogin()) + " " + myPlayer.playerInfo.getName())) {
                    Long oldRecord = Long.valueOf(data[2]);
                    if(elapsedTime < oldRecord) {
                        out.append(String.format("%s %s %d", data[0], data[1], elapsedTime));
                        continue;
                    }
                }
                out.append(String.join(" ", data) + "\n");
            }
            in.close();
            out.close();
        } catch(IOException e) {
            System.out.println(e);
            e.printStackTrace();
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
    /** 
     * if we lose
     */
    public static void defeat(String difficulty) {
        for(int i = 0; i < 100; i++) System.out.println();
        System.out.printf("Oh! You failed to pass * %s * level.", difficulty);
    }
    /**
     * simply printing welcome message with extra info for the player
     */
    public static void welcome() {
        for(int i = 0; i < 100; i++) System.out.println();
        System.out.println("\t***************************************************");
        System.out.println("\t***                                             ***");
        System.out.println("\t***                   Maze Game                 ***");
        System.out.println("\t***                                             ***");
        System.out.println("\t***************************************************");
        System.out.println("\n\tComplete passing of the maze as soon as possible!");
        System.out.println("\n\t<===============================================>");
        printLast5Games();
        System.out.println("\n\n\tTo select difficulty level of the game\n");
        System.out.printf("\t%-10s -> press h\n\t%-10s -> press m\n\t%-10s -> press e", "Hard", "Medium", "Easy");
    }
    /**
     * simply printing last 5 game sessions with the best results of gamers
     */
    public static void printLast5Games() {
        ArrayList<PlayerInfo<?>> arr = new ArrayList<>();
        try {
            File database = new File("./database/database.txt");
            Scanner in = new Scanner(database);
            while(in.hasNextLine()) {
                String row = in.nextLine();
                String[] data = row.split("\\s");
                // arr.add(new PlayerInfo(data[1], Long.valueOf(data[2])));
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
        System.out.println("\n\n\tLast 5 playing sessions of our players:\n");
        int sz = (arr.size() <= 5) ? arr.size() : 5;
        System.out.printf("\t%-15s %-15s %s\n\n", "Login", "Name", "the best score");
        for(int i = arr.size() - 1; i >= arr.size() - sz; i--) {
            System.out.printf("\t%-15s %-15s %d\n", String.valueOf(arr.get(i).getLogin()), arr.get(i).getName(), arr.get(i).getRecord());
        }
        System.out.println("\n\t<===============================================>");
    }
    /** 
     * used for setting difficult of the game
     */
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
    /** 
     * setting time that will be given depending on chosen difficulty
     */
    public static long setGivenTime(String difficulty) {
       return (difficulty == "hard") ? 30L : (difficulty == "medium") ? 45L : 60L;
    }
    /** 
     * giving to player some info about game controllers
     */
    public static void gameControlsInfo(String difficulty, long givenTime) {
        for(int i = 0; i < 100; i++) System.out.println();
        System.out.println("\n\tThe game has started!\n");
        System.out.println("\n\tYour task is to reach point D!");
        System.out.printf("\tYou've chosen * %s * level, you have * %d * seconds for passing it!\n", difficulty, givenTime);
        System.out.println("\n\tto go (up/down/left/right) press ('w'/'s'/'a'/'d') + ENTER respectively");
        System.out.println("\tto exit the game press 'q' ENTER\n");
    }
}