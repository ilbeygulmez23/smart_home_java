/**
 * The SmartColorLamp class represents a smart color lamp that has a color code attribute above SmartLamp class
 * It inherits SmartLamp , thus Accessory too.
 */
public class SmartColorLamp extends SmartLamp{
    //Declare variables
    private String colorCode;
    boolean isSet;
    //Constructors for SmartColorLamp
    /**
     * Creates a new SmartColorLamp object with the given name.
     * @param name The name of the smart color lamp.
     */
    public SmartColorLamp(String name){
        super(name);
    }
    /**
     * Creates a new SmartColorLamp object with the given name.
     * @param name The name of the smart color lamp.
     * @param statusString The initial status of the smart color lamp, represented as a string ("On" or "Off").
     */
    public SmartColorLamp(String name,String statusString){
        super(name,statusString);
    }
    /**
     * Creates a new SmartColorLamp object with the given name.
     * @param name The name of the smart color lamp.
     * @param statusString The initial status of the smart color lamp, represented as a string ("On" or "Off").
     * @param colorCode The hexadecimal color value of the smart color lamp.
     * @param brightness The percent of brightness of the smart color lamp.
     */
    public SmartColorLamp(String name,String statusString,String colorCode,double brightness){
        super(name,statusString,brightness);
        this.colorCode = colorCode;
    }
    /**
     * Creates a new SmartColorLamp object with the given name.
     * @param name The name of the smart color lamp.
     * @param statusString The initial status of the smart color lamp, represented as a string ("On" or "Off").
     * @param kelvin The kelvin value of smart colored lamp
     * @param brightness The percent of brightness of the smart color lamp.
     */
    public SmartColorLamp(String name,String statusString,double kelvin,double brightness){
        super(name,statusString,kelvin,brightness);
    }
    /**
     * Getter and setter methods for SmartColorLamp class.
     */
    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        if(isHexadecimal(colorCode)){ //Check if hexadecimal
            this.colorCode = colorCode;
            isSet = true;
        }
    }
    /**
     * Method to check if given String is hexadecimal or not.
     * Also prints error messages in wrong formats.
     * @param str String given as an argument.
     */
    public static boolean isHexadecimal(String str) {
        if(str == null || str.length() < 2 || str.charAt(0) != '0' || (str.charAt(1) != 'x' && str.charAt(1) != 'X')) {
            System.out.println("ERROR: Erroneous command!");
            return false;
        }
        String hex = str.substring(2);
        try {
            int val = Integer.parseInt(hex, 16);
             if(!(val >= 0 && val <= 0xFFFFFF)){
                 System.out.println("ERROR: Color code value must be in range of 0x0-0xFFFFFF!");
                 return false;
             }
        } catch(NumberFormatException ex) {
            System.out.println("ERROR: Erroneous command!");
            return false;
        }
        return true;
    }
    /**
     * Overridden method to generate a string representation of the smart color lamp.
     * Contains both color mode and kelvin mode options.
     * Will be used in ZReport and Remove functions.
     * @return a string representation of the smart color lamp.
     */
    public String toString() {
        if(this.getColorCode() != null){
            try{
                return "Smart Color Lamp "+ this.getName()+ " is "+ this.getStatusString().toLowerCase() +" and its color value is "+ this.getColorCode() + " with "+ (int)this.getBrightness() +"% brightness, and its time to switch its status is "+ this.getSwitchTime().format(Time.formatter) +".";

            }catch(NullPointerException ex){
                return "Smart Color Lamp "+ this.getName()+ " is "+ this.getStatusString().toLowerCase() +" and its color value is "+ this.getColorCode() + " with "+ (int)this.getBrightness() +"% brightness, and its time to switch its status is "+ this.getSwitchTime() +".";

            }

        }
        else{
            try{
                return "Smart Color Lamp "+ this.getName()+ " is "+ this.getStatusString().toLowerCase() +" and its color value is "+ (int)this.getKelvin() + "K with "+ (int)this.getBrightness() +"% brightness, and its time to switch its status is "+ this.getSwitchTime().format(Time.formatter) +".";

            }catch(NullPointerException ex){
                return "Smart Color Lamp "+ this.getName()+ " is "+ this.getStatusString().toLowerCase() +" and its color value is "+ (int)this.getKelvin() + "K with "+ (int)this.getBrightness() +"% brightness, and its time to switch its status is "+ this.getSwitchTime() +".";
            }

        }
    }
}
