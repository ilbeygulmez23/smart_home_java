import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
/**
 *This is the main class of the Smart Home system.
 *It reads the input from a file and parses it to create a list of Smart Accessories,
 such as lamps, plugs, and cameras, along with their current status and attributes.
 *The program then processes the data according to the instructions in the input file,
 updates the status of the accessories, and generates a report of their current state.
 *The report is written to a file and can be used for grading.
 *This class contains the main method that executes the program and handles any exceptions that occur.
 @author İlbey GÜLMEZ 2210765024
 @version 1.0
 @since 2023-04-06
 */
public class Main {
    public static void main(String[] args) {
        //comment then report
        try {
            FileOutputStream fos = new FileOutputStream(args[1]);
            PrintStream ps = new PrintStream(fos);
            System.setOut(ps);
            Commands.readInput(args[0]);
            ps.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

