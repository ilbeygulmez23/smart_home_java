import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
/**
 * The SmartCamera class represents a smart camera accessory that can consume storage space and inherits the Accessory class.
 */
public class SmartCamera extends Accessory{
    private double MBPerRecord;
    private double ConsumedMB;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double minutesDiff;
    //Constructors
    /**
     * Creates a new SmartCamera object with the given name.
     * @param name The name of the smart camera.
     */
    public SmartCamera(String name) {
        super(name);
    }
    /**

     * Creates a new SmartCamera object with the given name and megabyte per record.
     * @param name The name of the smart camera.
     * @param MBPerRecord The megabyte value will be used in calculation.
     */
    public SmartCamera(String name,double MBPerRecord){
        super(name);
        if(MBPerRecord > 0){this.MBPerRecord = MBPerRecord;}
        else{System.out.println("ERROR: Megabyte value must be a positive number!");}
    }
    /**
     * Creates a new SmartCamera object with the given name, string status and megabyte per record.
     * @param name The name of the smart camera.
     * @param MBPerRecord The megabyte value will be used in calculation.
     * @param statusString The initial status of the smart camera, represented as a string ("On" or "Off").
     */
    public SmartCamera(String name,String statusString,double MBPerRecord){
        super(name);
        if(statusString.equals("On")){
            this.Switch("On");
        }else{
            this.setStatus(false);
        }
        this.MBPerRecord = MBPerRecord;
    }
    /**
     * Getter and setter methods for declared variables
     */
    public double getMBPerRecord() {
        return MBPerRecord;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    /**
     *This adds on to previous minutesDiff value
     */
    public void setMinutesDiff(double minutesDiff) {
        this.minutesDiff += minutesDiff;
    }

    public double getConsumedMB() {
        return ConsumedMB;
    }

    public void setConsumedMB(double consumedMB) {
        ConsumedMB += consumedMB;
    }
    /**
     * Overridden method to switch the status of the smart camera (on/off).
     * Starts timer if and if only is the SmartCamera is on and calculates the time in Minutes
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
                setStartTime(Time.getCurrentTime());
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
                    setMinutesDiff(-(ChronoUnit.MINUTES.between(getEndTime(), getStartTime())));
                    setConsumedMB(MBConsumption());
                }catch(NullPointerException ex){
                    setMinutesDiff(0);
                    setConsumedMB(0);
                }

            }
        }
        else{
            System.out.println("ERROR: Erroneous command!");
        }
    }
    /**
     * Method to calculate the megabyte consumption of the smart camera.
     * @return the megabyte consumption of the smart camera.
     */
    public double MBConsumption(){
        try{
            double MB = getMBPerRecord()*minutesDiff;
            this.minutesDiff = 0;
            return MB;
        }catch(NullPointerException ex){
            return 0;
        }

    }
    /**
     * Overridden method to generate a string representation of the smart camera.
     * Will be used in ZReport and Remove functions
     * @return a string representation of the smart camera
     */
    @Override
    public String toString() {
        try{
            return "Smart Camera "+ this.getName()+ " is "+ this.getStatusString().toLowerCase() +" and used "+ new DecimalFormat("0.00").format(this.getConsumedMB()).replace(".",",") + "MB of storage so far (excluding current status), and its time to switch its status is "+ this.getSwitchTime().format(Time.formatter) +".";
        }catch(NullPointerException ex){
            return "Smart Camera "+ this.getName()+ " is "+ this.getStatusString().toLowerCase() +" and used "+ new DecimalFormat("0.00").format(this.getConsumedMB()).replace(".",",") + "MB of storage so far (excluding current status), and its time to switch its status is "+ this.getSwitchTime() +".";

        }
    }
}
