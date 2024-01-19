/**
 * The SmartLamp class represents a smart lamp accessory.
 * It extends the accessory class
 */
public class SmartLamp extends Accessory{
    //Declare variables
    private double kelvin = 4000;
    private double brightness = 100;
    /**
     * Creates a new SmartLamp object with the given name.
     * @param name The name of the smart lamp.
     */
    public SmartLamp(String name){
        super(name);
    }
    /**
     * Creates a new SmartLamp object with the given name.
     * @param name The name of the smart lamp.
     * @param statusString The initial status of the smart lamp, represented as a string ("On" or "Off").
     */
    public SmartLamp(String name,String statusString){
        super(name,statusString);
    }
    /**
     * Creates a new SmartLamp object with the given name.
     * @param name The name of the smart lamp.
     * @param statusString The initial status of the smart lamp, represented as a string ("On" or "Off").
     * @param kelvin The kelvin value of the smart lamp, restricted in given interval.
     * @param brightness The brightness percent of the smart lamp, restricted in given interval.
     */
    public SmartLamp(String name, String statusString, double kelvin, double brightness) {
        super(name, statusString);
        if(2000 <= kelvin && kelvin <= 6500){this.kelvin = kelvin;}
        else{System.out.println("ERROR: Kelvin value must be in range of 2000K-6500K!");}
        if(0 <= brightness && brightness <= 100){this.brightness = brightness;}
        else{System.out.println("ERROR: Brightness must be in range of 0%-100%!");}
    }
    /**
     * Creates a new SmartLamp object with the given name.
     * This is the constructor that going to be inherited in SmartColorLamp.java.
     * @param name The name of the smart lamp.
     * @param statusString The initial status of the smart lamp, represented as a string ("On" or "Off").
     * @param brightness The brightness percent of the smart lamp, restricted in given interval.
     */
    public SmartLamp(String name, String statusString, double brightness) {
        super(name,statusString);
        if(0 <= brightness && brightness <= 100){this.brightness = brightness;}
        else{System.out.println("ERROR: Brightness must be in range of 0%-100%!");}
    }
    /**
     * Getter and setter methods for SmartLamp class.
     * It operates under only given intervals and correct formats.
     */

    public double getKelvin() {
        return kelvin;
    }

    public void setKelvin(String kelvinStr) {
        try{
        int kelvin = Integer.parseInt(kelvinStr);
        if(2000 <= kelvin && kelvin <= 6500){
            this.kelvin = kelvin;
        }
        else{
            System.out.println("ERROR: Kelvin value must be in range of 2000K-6500K!");

        }
        }catch(NumberFormatException ex){
            System.out.println("ERROR: Kelvin value must be in range of 2000K-6500K!");

        }
    }

    public double getBrightness() {
        return brightness;
    }

    public void setBrightness(String brightnessStr) {
        try {
            int brightness = Integer.parseInt(brightnessStr);
            if (0 <= brightness && brightness <= 100) {
                this.brightness = brightness;
            } else {
                System.out.println("ERROR: Brightness must be in range of 0%-100%!");

            }
        }catch(NumberFormatException ex){
            System.out.println("ERROR: Brightness must be in range of 0%-100%!");

        }
    }
    /**
     * Overridden method to generate a string representation of the smart lamp.
     * Will be used in ZReport and Remove functions
     * @return a string representation of the smart lamp.
     */
    @Override
    public String toString() {
        try{
            return "Smart Lamp "+ this.getName()+ " is "+ this.getStatusString().toLowerCase() +" and its kelvin value is "+ (int)this.getKelvin() + "K with "+ (int)this.getBrightness() +"% brightness, and its time to switch its status is "+ this.getSwitchTime().format(Time.formatter) +".";
        }catch(NullPointerException ex){
            return "Smart Lamp "+ this.getName()+ " is "+ this.getStatusString().toLowerCase() +" and its kelvin value is "+ (int)this.getKelvin() + "K with "+ (int)this.getBrightness() +"% brightness, and its time to switch its status is "+ this.getSwitchTime() +".";

        }
    }
}
