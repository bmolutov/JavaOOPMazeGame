import game.Game;
import invalidMapException.InvalidMapException;
import map.Map;
import myPlayer.MyPlayer;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) throws InvalidMapException, FileNotFoundException{

        // Welcome message
        welcome();

        // setting difficulty of the game
        String difficulty = setDifficulty();
       
        // given time info
        long givenTime = setGivenTime(difficulty);

        // creating main entities 
        MyPlayer myPlayer = new MyPlayer(new Map(difficulty));
        Game game = new Game();
        game.addPlayer(myPlayer);

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
                victory(difficulty);
                System.exit(0);
            }
        }
        in.close();
    }    
    public static void victory(String difficulty) {
        for(int i = 0; i < 100; i++) System.out.println();
        System.out.printf("Congratulations! You have passed * %s * level.", difficulty);
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
        System.out.println("***   Made by Daniyar Absatov, Bekzat Molutov   ***");
        System.out.println("***                                             ***");
        System.out.println("***                                             ***");
        System.out.println("*** Please select difficulty level of the game! ***");
        System.out.println("***************************************************");
        System.out.println("\n\tHard -> press h\n\tMedium -> press m\n\tEasy -> press e");
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
