import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
/**

 The SmartPlug class represents a smart plug accessory that can be controlled through a home automation system.

 It extends the Accessory class and adds functionality to track energy consumption and time of usage.
 */
public class SmartPlug extends Accessory{
    //Declare variables
    private double ampere;
    private boolean pluggedIn = false;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    /**
     * The total number of hours that the smart plug has been in use.
     */
    private double hoursDiff = 0;
    private double consumedEnergy;
    /**
     * Boolean value representing whether a device will be plugged in to the smart plug in the future.
     */
    public boolean willPlug = true;
    //Constructors for SmartPlug
    /**
     * Creates a new SmartPlug object with the given name.
     * @param name The name of the smart plug.
     */
    public SmartPlug(String name) {
        super(name);
    }
    /**

     * Creates a new SmartPlug object with the given name and status string.
     * @param name The name of the smart plug.
     * @param statusString The initial status of the smart plug, represented as a string ("On" or "Off").
     */
    public SmartPlug(String name,String statusString) {
        super(name);
        if(statusString.equals("On")){
            this.Switch("On");
        }else{
            this.setStatus(false);
        }
    }
    /**

     * Creates a new SmartPlug object with the given name, status string, and ampere rating.
     * @param name The name of the smart plug.
     * @param statusString The initial status of the smart plug, represented as a string ("On" or "Off").
     * @param ampere The ampere value of the smart plug.
     */
    public SmartPlug(String name, String statusString, double ampere) {
        super(name);
        if(statusString.equals("On")){
            this.Switch("On");
        }else{
            this.setStatus(false);
        }
        PlugIn(String.valueOf(ampere));
    }
    //Getter and setters for SmartPlug
    /**
     * @return The ampere rating of the smart plug.
     */
    public double getAmpere() {
        return ampere;
    }
    /**
     * @return The time at which the smart plug was last plugged in.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }
    /**

    * Sets the time at which the smart plug was last plugged in.
    * @param startTime The time at which the smart plug was last plugged in.
    */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    /**
     * @return The time at which the smart plug was last unplugged.
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }
    /**
     Sets the time at which the smart plug was last unplugged.
     @param endTime The time at which the smart plug was last unplugged.
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    /**
     * Setter method for the hours difference of the smart plug.
     * @param hoursDiff the hours difference to add to the current hours difference of the smart plug
     */
    public void setHoursDiff(double hoursDiff) {
        this.hoursDiff += hoursDiff;
    }
    /**
     * Getter method for the total energy consumption of the smart plug.
     * @return the total energy consumption of the smart plug
     */
    public double getConsumedEnergy() {
        return consumedEnergy;
    }
    /**
     * Setter method for the total energy consumption of the smart plug.
     * @param consumedEnergy the total energy consumption to set for the smart plug
     */
    public void setConsumedEnergy(double consumedEnergy) {
        this.consumedEnergy += consumedEnergy;
    }
    /**
     * Method to plug in an item to the smart plug.
     * @param strAmpere the ampere value of the item to be plugged in
     */
    public void PlugIn(String strAmpere){
        if(!this.pluggedIn){
            if(willPlug && Double.parseDouble(strAmpere) > 0){
                this.pluggedIn = true;
                if(getStatus()){
                    setStartTime(Time.getCurrentTime());
                }
            }
            try{
                double ampere = Double.parseDouble(strAmpere);
                if(ampere > 0){this.ampere = ampere;}
                else{
                    System.out.println("ERROR: Ampere value must be a positive number!");
                }
            }
            catch(NumberFormatException ex){
                System.out.println("ERROR: Ampere value must be a positive number!");
            }
        }
        else{System.out.println("ERROR: There is already an item plugged in to that plug!");}

        willPlug = true;
    }
    /**
     * Method to unplug an item from the smart plug.
     */
    public void PlugOut(){
        if(this.pluggedIn){
            this.pluggedIn = false;
            if(getStatus()){
                setEndTime(Time.getCurrentTime());
                try{
                    setHoursDiff(-(ChronoUnit.MINUTES.between(getEndTime(), getStartTime()))/60f);
                    setConsumedEnergy(energyConsumption());
                }catch(NullPointerException ex){
                    setHoursDiff(0);
                    setConsumedEnergy(0);
                }

            }
        }
        else{
            System.out.println("ERROR: This plug has no item to plug out from that plug!");
        }


    }
    /**
     * Overridden method to switch the status of the smart plug (on/off).
     * Starts timer if and if only is the SmartPlug is plugged
     * @param stats the status to switch to (on/off)
     */
    @Override
    public void Switch(String stats) {
        if(stats.equals("On")){
            if(getStatus()){
                System.out.println("ERROR: This device is already switched on!");
            }
            else{
                setStatus(true);
                setStatusString("On");
                this.setSwitchTime(null);
                if(pluggedIn){
                    setStartTime(Time.getCurrentTime());
                }

            }
        }
        else if (stats.equals("Off")) {
            if(!getStatus()){
                System.out.println("ERROR: This device is already switched off!");
            }
            else{
                setStatus(false);
                setStatusString("Off");
                this.setSwitchTime(null);
                setEndTime(Time.getCurrentTime());
                try{
                    setHoursDiff(-(ChronoUnit.MINUTES.between(getEndTime(), getStartTime()))/60f);
                    setConsumedEnergy(energyConsumption());
                }catch(NullPointerException ex){
                    setHoursDiff(0);
                    setConsumedEnergy(0);
                }

            }
        }
        else{
            System.out.println("ERROR: Erroneous command!");
        }
    }
    /**
     * Method to calculate the energy consumption of the smart plug.
     * @return the energy consumption of the smart plug
     */
    public double energyConsumption(){
        try{
            int volt = 220;
            double energy = volt *getAmpere()* hoursDiff;
            this.hoursDiff = 0;
            return energy;
        }catch(NullPointerException ex){
            return 0;
        }

    }
    /**
     * Overridden method to generate a string representation of the smart plug.
     * Will be used in ZReport and Remove functions
     * @return a string representation of the smart plug
     */
    @Override
    public String toString() {
        try{
            return "Smart Plug "+ this.getName()+ " is "+ this.getStatusString().toLowerCase() +" and consumed "+ new DecimalFormat("0.00").format(this.getConsumedEnergy()).replace(".",",") + "W so far (excluding current device), and its time to switch its status is "+ this.getSwitchTime().format(Time.formatter) +".";

        }catch(NullPointerException ex){
            return "Smart Plug "+ this.getName()+ " is "+ this.getStatusString().toLowerCase() +" and consumed "+ new DecimalFormat("0.00").format(this.getConsumedEnergy()).replace(".",",") + "W so far (excluding current device), and its time to switch its status is "+ this.getSwitchTime() +".";
        }
    }
}
