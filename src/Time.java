import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 * This class creates a static Time object to control time.
 * It has 3 variables declared in the beginning that will be used in further code.
 * It has methods that sets and returns the declared variables,
 * Also skipMinutes and nop methods.
 */
public class Time {
    //Declared the variables
    private static LocalDateTime initialTime;
    private static LocalDateTime currentTime;
    //Defined a formatter to parse given String
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
    /**
     *Sets the initialTime as argument by parsing the String accordingly
     *@param argument - a String representing the initial time.
     */
    public static void setInitialTime(String argument){
        try{
        Time.initialTime = LocalDateTime.parse(argument,formatter);
        Time.currentTime = Time.initialTime;
        }
        catch (DateTimeParseException ex){
            System.out.println("ERROR: Format of the initial date is wrong! Program is going to terminate!");
            System.exit(0);
        }
    }
    /**
     *Sets the time as argument by parsing the String accordingly
     * Static CurrentTime attribute is set as argument
     * Then switches the necessary devices if there is one
     * @param argument - String represents time*/
    public static void setTime(String argument){
        //Check the wrong format exception
        try{
            if(getCurrentTime().equals(LocalDateTime.parse(argument,formatter))){
                System.out.println("ERROR: There is nothing to change!");
            }
            else if (!getCurrentTime().isBefore(LocalDateTime.parse(argument,formatter))) {
                System.out.println("ERROR: Time cannot be reversed!");
            }
            else{
            setCurrentTime(LocalDateTime.parse(argument,formatter));
            Accessory.switchNecessary();
            }
        }
        catch (DateTimeParseException ex){
            System.out.println("ERROR: Time format is not correct!");
        }
    }
    /**
     * Method skips minutes
     * Method parses the given String to Duration class and uses .plus to add on to time
     * @param durationString represents the minutes desired to skip
     */
    public static void skipMinutes(String durationString){
        try {
            if(Integer.parseInt(durationString) < 0){
                System.out.println("ERROR: Time cannot be reversed!");
            }
            else if(Integer.parseInt(durationString) == 0){
                System.out.println("ERROR: There is nothing to skip!");
            }
            else{
            long durationInMinutes = Long.parseLong(durationString);
            Duration duration = Duration.ofMinutes(durationInMinutes);
            setCurrentTime(getCurrentTime().plus(duration));
            Accessory.switchNecessary();
            }

        }
        catch(NumberFormatException ex){
            System.out.println("ERROR: Erroneous command!");
        }
    }
    /**
     * Method that sets the time to the earliest switching occasion
     * Method equates the switchTime to MAX LocalDateTime value
     * Then iterates through the Accessories ArrayList
     * If there is an occasion earliestSwitchTime will change, So we will understand there is an occasion
     * Sets the current time to the earliest occasion then switches the necessary ones.
     */
    public static void nop(){
        LocalDateTime earliestSwitchTime = LocalDateTime.MAX;
        for (Accessory accessory : Accessory.getAccessories()) {
            if (accessory.getSwitchTime() != null && accessory.getSwitchTime().isBefore(earliestSwitchTime)) {
                earliestSwitchTime = accessory.getSwitchTime();
            }
        }
        if(earliestSwitchTime == LocalDateTime.MAX){System.out.println("ERROR: There is nothing to switch!");}

        else{setCurrentTime(earliestSwitchTime);
            Accessory.switchNecessary();}
    }
    //Getter and setters
    /**
     * Returns the Initial Time of Time.
     * @return A LocalDateTime representing the InitialTime.
     */
    public static LocalDateTime getInitialTime() {
        return initialTime;
    }
    /**
     * Returns the Current Time.
     * @return A LocalDateTime representing the CurrentTime.
     */
    public static LocalDateTime getCurrentTime() {
        return currentTime;
    }
    /**
     * Sets the CurrentTime.
     * @param currentTime - a LocalDateTime representing the current time.
     */
    public static void setCurrentTime(LocalDateTime currentTime) {
        Time.currentTime = currentTime;
    }
}
