import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * This class represents an accessory that can be switched on or off.
 * It provides methods to switch the accessory on or off, get and set the accessory name,
 * get and set the accessory status and switch time, and get a list of all accessories.
 */
public class Accessory {
    //Attributes for accessory class
    private String name;
    private boolean statusBool = false;
    private String statusString = "Off";
    private LocalDateTime switchTime;
    private LocalDateTime formerSwitchTime;
    private static final ArrayList<Accessory> accessories = new ArrayList<>();
    //Constructors for Accessory class
    /**
     * Constructor that creates an Accessory object with the given name.
     * @param name - a String representing the name of the accessory.
     */
    public Accessory(String name) {
        this.name = name;
    }
    /**
     *Constructor that creates an Accessory object with the given name and status string.
     * @param name - a String representing the name of the accessory.
     * @param statusString - a String representing the status of the accessory.
     */
    public Accessory(String name, String statusString) {
        this.name = name;
        this.statusString = statusString;
        Switch(statusString);
    }
    //Getter and setters for accessory class
    /**
     * Returns the name of the accessory.
     * @return the String representing the name of the accessory.
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the accessory.
     * @param name - a String representing the name of the accessory.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Returns the status of the accessory.
     * @return A boolean representing the status of the accessory.
     */
    public boolean getStatus() {
        return statusBool;
    }
    /**
     * Returns the status string of the accessory.
     * @return A String representing the status of the accessory.
     */
    public String getStatusString(){return statusString;}
    /**
     * Sets the status of the accessory.
     * @param statusBool - a boolean representing the status of the accessory.
     */
    public void setStatus(boolean statusBool) {
        this.statusBool = statusBool;
    }
    /**
     * Sets the status string of the accessory.
     * @param statusString - a String representing the status of the accessory.
     */
    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }
    /**
     * Returns the switch time of the accessory.
     * @return A LocalDateTime object representing the switch time of the accessory.*/
    public LocalDateTime getSwitchTime() {
        return switchTime;
    }
    /**
     * Sets the switch time of the accessory.
     * If the given argument is null, method sets the switch time null.
     * Else format the switchTime and assign it.
     * With assigning it to formerSwitchTime variable that will be used in Comparator.
     * @param switchTime - a String representing the switch time of the accessory.
     */
    public void setSwitchTime(String switchTime) {
        try {
            if(switchTime == null){this.switchTime = null;}
            else{
                if(LocalDateTime.parse(switchTime, Time.formatter).isBefore(Time.getCurrentTime())){
                    System.out.println("ERROR: Switch time cannot be in the past!");
                }else{
                    this.switchTime = LocalDateTime.parse(switchTime, Time.formatter);
                    this.formerSwitchTime = LocalDateTime.parse(switchTime, Time.formatter);
                        switchNecessary();
                }
            }
        }
        catch(DateTimeParseException ex){
            System.out.println("ERROR: Time format is not correct!");
        }
    }
    /**
     * Returns the former switch time of the accessory.
     * @return A LocalDateTime object representing the former switch time of the accessory.
     */
    public LocalDateTime getFormerSwitchTime() {
        return formerSwitchTime;
    }
    /**
     * Returns the list of accessories.
     * @return An ArrayList of Accessory objects.
     */
    public static ArrayList<Accessory> getAccessories() {
        return accessories;
    }
    /**
     * Returns a copy of the list of accessories that will be used in Commands class.
     * @return An ArrayList of Accessory objects.
     */
    public static ArrayList<Accessory> getDummyAccessories() {
        return new ArrayList<>(accessories);
    }
    /**
     * Switches the accessory on or off depending on the given status string.
     * It also setSwitchTime to null after executing switching command.
     * @param stats - a String representing the desired status of the accessory.*/
    public void Switch(String stats){
        if(stats.equals("On")){
            if(this.statusBool){
                System.out.println("ERROR: This device is already switched on!");
            }
            else{
                this.statusBool = true;
                this.statusString = "On";
                this.setSwitchTime(null);

            }
        }
        else if (stats.equals("Off")) {
            if(!this.statusBool){
                System.out.println("ERROR: This device is already switched off!");
            }
            else{
                this.statusBool = false;
                this.statusString = "Off";
                this.setSwitchTime(null);
            }
        }
        else{
            System.out.println("ERROR: Erroneous command!");
        }

    }
    /**
     * Checks if any accessories need to be switched on or off based on their switch times,
     * and switches them accordingly.
     */
    public static void switchNecessary(){
            for(Accessory accessory : Accessory.getAccessories()){
            if(accessory.getSwitchTime() != null && (accessory.getSwitchTime().isBefore(Time.getCurrentTime()) || accessory.getSwitchTime().equals(Time.getCurrentTime()))){
                        if(!accessory.getStatus()){
                            accessory.Switch("On");
                        }else if (accessory.getStatus()) {
                            accessory.Switch("Off");
                        }
                   }
            }
    }
}
