import game.Game;
import invalidMapException.InvalidMapException;
import map.Map;
import myPlayer.MyPlayer;
import position.Position;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {
    public static void main(String[] args) throws InvalidMapException, FileNotFoundException{

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

        Scanner in = new Scanner(System.in);
        char init = in.nextLine().charAt(0);
        while(init != 'h' && init != 'm' && init != 'e') {
            System.out.println("Please, choose the correct one!");
            init = in.nextLine().charAt(0);
        }

        String difficulty = (init == 'h') ? "hard" : (init == 'm') ? "medium" : "easy";
        Map map = new Map(difficulty);

        while(true) {
            for(int i = 0; i < 100; i++) System.out.println();
            System.out.println("\n\tThe game has started!");
            System.out.println("\nto exit the game press 'q'");

            map.print();
            char checker = in.next().charAt(0);
            System.out.println();
            if(checker == 'q') {
                break;
            }
        }
    }    
}
