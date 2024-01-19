import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
/**
 * Class that read, parse and execute the input files command
 * It has 4 methods:
 * readInput - reads and calls the Execute command function while checking the necessary conditions
 * ExecuteCommand - Evaluates each line, identifies the command and executes it
 * isObjectWithNameExists - Method that checks the accessories list for duplicate names
 */
public class Commands {
    /**
     *Method that reads the input file and run the Execute Command method line by line.
     * @param file Given file (will be taken as an argument)
     */
    public static void readInput(String file){
        File inputFile = new File(file);
        boolean isFirstLine = true;
        try {
            Scanner scanner = new Scanner(inputFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine(); //Current line

                //If line is not blank execute the commands
                if(!line.isEmpty()){
                    String[] lineParts = line.trim().split("\t");
                    System.out.println("COMMAND: " + line);
                    //Check if the first command is SetInitialTime
                    if(!lineParts[0].equals("SetInitialTime") && isFirstLine){
                        System.out.println("ERROR: First command must be set initial time! Program is going to terminate!");
                        System.exit(0);
                    }
                    else if (lineParts[0].equals("SetInitialTime") && !isFirstLine) {
                        System.out.println("ERROR: Erroneous command!");
                    }
                    else{
                        ExecuteCommand(lineParts[0],lineParts); //If nothing wrong, execute it
                        if(!lineParts[0].equals("ZReport") && !scanner.hasNextLine()){ //If the last command is not ZReport;
                            System.out.println("ZReport: ");
                            ZReport(); //Call ZReport
                        }
                    }
                    isFirstLine = false;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file);
        }
    }
    /**
     * Method that reads the index of the split lines and execute the commands by calling the methods
     * While obeying the restrictions.
     * @param command it's the 0th index of the list
     * @param lineParts it's the list that a result of splitting the line
     */
    public static void ExecuteCommand(String command,String[] lineParts){
        switch(command){
            case "SetInitialTime":
                //Check for erroneous command
                if(lineParts.length != 2){
                    System.out.println("ERROR: First command must be set initial time! Program is going to terminate!");
                    System.exit(0);
                }
                else {
                    Time.setInitialTime(lineParts[1]);
                    if(Time.getInitialTime() != null){
                        System.out.println("SUCCESS: Time has been set to " + Time.getInitialTime().format(Time.formatter) + "!");
                    }
                }
                break;
            case "SetTime":
                //Check for erroneous command
                if(lineParts.length != 2){
                    System.out.println("ERROR: Erroneous command!");
                }
                else{
                    Time.setTime(lineParts[1]);
                }
                break;
            case "SkipMinutes":
                //Check for erroneous command
                if(lineParts.length != 2){
                    System.out.println("ERROR: Erroneous command!");
                }
                else{
                    Time.skipMinutes(lineParts[1]);
                }
                break;
            case "Nop":
                //Check for erroneous command
                if(lineParts.length != 1){
                    System.out.println("ERROR: Erroneous command!");
                }
                else{
                    Time.nop();
                }
                break;
            case "Add":
                ArrayList<Accessory> accBefore = Accessory.getDummyAccessories(); //copy of the arraylist
                boolean errorWeight = false;
                switch(lineParts[1]){
                    case "SmartPlug":
                        //if case for SP
                        if(lineParts.length == 3){
                            SmartPlug plug = new SmartPlug(lineParts[2]);
                            if(isObjectWithNameExists(lineParts[2])){
                                Accessory.getAccessories().add(plug);
                            }
                        }
                        else if (lineParts.length == 4) {
                            SmartPlug plug = new SmartPlug(lineParts[2],lineParts[3]);
                            if(isObjectWithNameExists(lineParts[2]) && lineParts[3].equals("On") || lineParts[3].equals("Off")){
                                Accessory.getAccessories().add(plug);
                            }
                        }
                        else if (lineParts.length == 5){
                            try {
                                SmartPlug plug = new SmartPlug(lineParts[2], lineParts[3], Double.parseDouble(lineParts[4]));
                                if(isObjectWithNameExists(lineParts[2]) && Double.parseDouble(lineParts[4]) > 0 && (lineParts[3].equals("On") || lineParts[3].equals("Off"))){
                                    Accessory.getAccessories().add(plug);
                                }

                            }catch(NumberFormatException ex){
                                System.out.println("ERROR: Ampere value must be a positive number!");
                            }
                        }
                        else{
                            System.out.println("ERROR: Erroneous command!");
                            errorWeight = true;

                        }
                        break;
                    case "SmartCamera":
                        //if case for SmartCamera
                        if(lineParts.length == 4){
                            try{
                                SmartCamera camera = new SmartCamera(lineParts[2],Double.parseDouble(lineParts[3]));
                                if(isObjectWithNameExists(lineParts[2]) && Double.parseDouble(lineParts[3]) > 0){
                                    Accessory.getAccessories().add(camera);
                                }
                            }catch(NumberFormatException ex){
                                System.out.println("ERROR: Megabyte value must be a positive number!");
                            }
                        }
                        else if (lineParts.length == 5) {
                            try{
                                SmartCamera camera = new SmartCamera(lineParts[2],lineParts[4],Double.parseDouble(lineParts[3]));
                                if(isObjectWithNameExists(lineParts[2]) && Double.parseDouble(lineParts[3]) > 0 && (lineParts[4].equals("On") || lineParts[4].equals("Off"))){
                                    Accessory.getAccessories().add(camera);
                                }
                            }catch(NumberFormatException ex){
                                System.out.println("ERROR: Megabyte value must be a positive number!");
                            }
                        }
                        else{
                            System.out.println("ERROR: Erroneous command!");
                            errorWeight = true;
                        }

                        //if case for SmartCamera ends
                        break;
                    case "SmartLamp":
                        //if case for SmartLamp
                        if(lineParts.length == 3){
                            SmartLamp lamp = new SmartLamp(lineParts[2]);
                            if(isObjectWithNameExists(lineParts[2])){
                            Accessory.getAccessories().add(lamp);
                            }
                        }
                        else if (lineParts.length == 4) {
                            SmartLamp lamp = new SmartLamp(lineParts[2],lineParts[3]);
                            if(isObjectWithNameExists(lineParts[2]) && lineParts[3].equals("On") || lineParts[3].equals("Off")){
                                Accessory.getAccessories().add(lamp);
                            }
                        }
                        else if (lineParts.length == 6){
                            try {
                                SmartLamp lamp = new SmartLamp(lineParts[2], lineParts[3], Double.parseDouble(lineParts[4]), Double.parseDouble(lineParts[5]));
                                if(isObjectWithNameExists(lineParts[2]) && (lineParts[3].equals("On") || lineParts[3].equals("Off")) && Double.parseDouble(lineParts[4]) >= 2000 && Double.parseDouble(lineParts[4]) <= 6500 && Double.parseDouble(lineParts[5]) >= 0 && Double.parseDouble(lineParts[5]) <= 100){
                                    Accessory.getAccessories().add(lamp);
                                }
                            }catch(NumberFormatException ex){
                                System.out.println("ERROR: Kelvin and brightness values must be numbers!");}
                        }
                        else{
                            System.out.println("ERROR: Erroneous command!");
                            errorWeight = true;
                        }

                        //if case for SmartLamp ends
                        break;
                    case "SmartColorLamp":
                        //if case for SmartColorLamp
                        if(lineParts.length == 3){
                            SmartColorLamp colorLamp = new SmartColorLamp(lineParts[2]);
                            if(isObjectWithNameExists(lineParts[2])){
                                Accessory.getAccessories().add(colorLamp);
                            }
                        }
                        else if (lineParts.length == 4) {
                            SmartColorLamp colorLamp = new SmartColorLamp(lineParts[2],lineParts[3]);
                            if(isObjectWithNameExists(lineParts[2]) && lineParts[3].equals("On") || lineParts[3].equals("Off")){
                                Accessory.getAccessories().add(colorLamp);
                            }
                        }
                        else if (lineParts.length == 6){
                            //inner if case
                            try{
                            SmartColorLamp colorLamp;
                            if(lineParts[4].startsWith("0x") || lineParts[4].startsWith("0X")){
                                colorLamp = new SmartColorLamp(lineParts[2], lineParts[3], lineParts[4], Double.parseDouble(lineParts[5]));
                                if(isObjectWithNameExists(lineParts[2]) && SmartColorLamp.isHexadecimal(lineParts[4]) && (lineParts[3].equals("On") || lineParts[3].equals("Off")) && Double.parseDouble(lineParts[5]) >= 0 && Double.parseDouble(lineParts[5]) <= 100){
                                    Accessory.getAccessories().add(colorLamp);
                                }
                            }
                            else{
                                colorLamp = new SmartColorLamp(lineParts[2], lineParts[3], Double.parseDouble(lineParts[4]), Double.parseDouble(lineParts[5]));
                                if(isObjectWithNameExists(lineParts[2]) && (lineParts[3].equals("On") || lineParts[3].equals("Off")) && Double.parseDouble(lineParts[4]) >= 2000 && Double.parseDouble(lineParts[4]) <= 6500 && Double.parseDouble(lineParts[5]) >= 0 && Double.parseDouble(lineParts[5]) <= 100){
                                    Accessory.getAccessories().add(colorLamp);
                                }
                            }

                            }catch(NumberFormatException ex){
                                System.out.println("ERROR: Kelvin and brightness values must be numbers!");}
                            //inner if case ends
                        }
                        else{
                            System.out.println("ERROR: Erroneous command!");
                            errorWeight = true;
                        }
                        //if case for SmartColorLamp ends
                        break;
                    default:
                        System.out.println("ERROR: There are no device type named " + lineParts[1] + "!");
                        break;
                }
                for(Accessory accessory : accBefore){ //check for identical names,with error weight to prevent multiple error messages
                    if(accessory.getName().equals(lineParts[2]) && !errorWeight){
                        System.out.println("ERROR: There is already a smart device with same name!");
                        break;
                    }
                }
                break;
            case "Remove":
                if(lineParts.length != 2){
                    System.out.println("ERROR: Erroneous command!");
                }
                else{
                    String message = null;
                    for(Accessory accessory : Accessory.getAccessories()){
                        if(accessory.getName().equals(lineParts[1])){
                            if(accessory.getStatus()){
                                accessory.Switch("Off"); //Switch off before removing
                            }
                            message = accessory.toString();
                        }
                    }
                    if (Accessory.getAccessories().removeIf(accessory -> accessory.getName().equals(lineParts[1]))) { //Proud
                        System.out.println("SUCCESS: Information about removed smart device is as follows:");
                        System.out.println(message);
                    }else {
                        System.out.println("ERROR: There is not such a device!");
                    }
                }
                break;
            case "SetSwitchTime":
                if(lineParts.length != 3){
                    System.out.println("ERROR: Erroneous command!");
                }
                else{
                    boolean isAccessory = false; //Helps to determine if the given name is in the list or not
                    for (Accessory accessory : Accessory.getAccessories()) {
                    if (accessory.getName().equals(lineParts[1])) {
                        accessory.setSwitchTime(lineParts[2]);
                        isAccessory = true;
                        break;
                    }
                }
                    if(!isAccessory){
                        System.out.println("ERROR: There is not such a device!");
                    }
                }

                break;
            case "Switch":
                if(lineParts.length != 3){
                    System.out.println("ERROR: Erroneous command!");
                }
                else{
                    boolean isAccessory = false;
                    for (Accessory accessory : Accessory.getAccessories()) {
                        if (accessory.getName().equals(lineParts[1])) {
                            accessory.Switch(lineParts[2]);
                            isAccessory = true;
                            break;
                        }
                    }
                    if(!isAccessory){
                        System.out.println("ERROR: There is not such a device!");
                    }
                }
                break;
            case "ChangeName":
                if(lineParts.length != 3){
                    System.out.println("ERROR: Erroneous command!");
                }
                else{
                    boolean isAccessory = false;
                    boolean isNewExist = false;
                    for (Accessory accessory : Accessory.getAccessories()){
                        if (accessory.getName().equals(lineParts[2])) {
                            isNewExist = true;
                            break;
                        }
                    }
                    for (Accessory accessory : Accessory.getAccessories()) {
                        if (accessory.getName().equals(lineParts[1])) {
                            isAccessory = true;
                            if(!isNewExist){
                                accessory.setName(lineParts[2]);
                                break;
                            }
                        }
                    }
                    if(lineParts[1].equals(lineParts[2])){
                        System.out.println("ERROR: Both of the names are the same, nothing changed!");
                    } else if (!isAccessory) {
                        System.out.println("ERROR: There is not such a device!");
                    } else if (isNewExist) {
                        System.out.println("ERROR: There is already a smart device with same name!");
                    }
                }
                break;
            case "PlugIn":
                if(lineParts.length != 3){
                    System.out.println("ERROR: Erroneous command!");
                }
                else{
                    try{
                    boolean isAccessory = false;
                    for (Accessory accessory : Accessory.getAccessories()) {
                        if (accessory.getName().equals(lineParts[1])) {
                            SmartPlug smartPlug = (SmartPlug) accessory;
                            isAccessory = true;
                            smartPlug.PlugIn(lineParts[2]);

                            break;
                        }
                    }
                    if(!isAccessory){
                        System.out.println("ERROR: There is not such a device!");
                    }
                    }catch (ClassCastException ex){
                        System.out.println("ERROR: This device is not a smart plug!");
                    }
                }
                break;
            case "PlugOut":
                if(lineParts.length != 2){
                    System.out.println("ERROR: Erroneous command!");
                }
                else{
                    try{
                        boolean isAccessory = false;
                        for (Accessory accessory : Accessory.getAccessories()) {
                            if (accessory.getName().equals(lineParts[1])) {
                                SmartPlug smartPlug = (SmartPlug) accessory;
                                isAccessory = true;
                                smartPlug.PlugOut();
                                break;
                            }
                        }
                        if(!isAccessory){
                            System.out.println("ERROR: There is not such a device!");
                        }
                    }catch (ClassCastException ex){
                        System.out.println("ERROR: This device is not a smart plug!");
                    }
                }
                break;
            case "SetKelvin":
                if(lineParts.length != 3){
                    System.out.println("ERROR: Erroneous command!");
                }
                else{
                    try{
                        boolean isAccessory = false;
                        for (Accessory accessory : Accessory.getAccessories()) {
                            if (accessory.getName().equals(lineParts[1])) {
                                SmartLamp smartLamp = (SmartLamp) accessory;
                                isAccessory = true;

                                smartLamp.setKelvin(lineParts[2]);
                                break;
                            }
                        }
                        if(!isAccessory){
                            System.out.println("ERROR: There is not such a device!");
                        }
                    }catch (ClassCastException ex){
                        System.out.println("ERROR: This device is not a smart lamp!");
                    }
                }
                break;
            case "SetBrightness":
                if(lineParts.length != 3){
                    System.out.println("ERROR: Erroneous command!");
                }
                else{
                    try{
                        boolean isAccessory = false;
                        for (Accessory accessory : Accessory.getAccessories()) {
                            if (accessory.getName().equals(lineParts[1])) {
                                SmartLamp smartLamp = (SmartLamp) accessory;
                                isAccessory = true;
                                smartLamp.setBrightness(lineParts[2]);
                                break;
                            }
                        }
                        if(!isAccessory){
                            System.out.println("ERROR: There is not such a device!");
                        }
                    }catch (ClassCastException ex){
                        System.out.println("ERROR: This device is not a smart lamp!");
                    }
                }
                break;
            case "SetColorCode":
                if(lineParts.length != 3){
                    System.out.println("ERROR: Erroneous command!");
                }
                else{
                    try{
                        boolean isAccessory = false;
                        for (Accessory accessory : Accessory.getAccessories()) {
                            if (accessory.getName().equals(lineParts[1])) {
                                SmartColorLamp smartCLamp = (SmartColorLamp) accessory;
                                isAccessory = true;
                                smartCLamp.setColorCode(lineParts[2]);
                                break;
                            }
                        }
                        if(!isAccessory){
                            System.out.println("ERROR: There is not such a device!");
                        }
                    }catch (ClassCastException ex){
                        System.out.println("ERROR: This device is not a smart color lamp!");
                    }
                }
                break;
            case "SetWhite":
                if(lineParts.length != 4){
                    System.out.println("ERROR: Erroneous command!");
                }
                else{
                    try{
                        boolean isAccessory = false;
                        for (Accessory accessory : Accessory.getAccessories()) {
                            if (accessory.getName().equals(lineParts[1])) {
                                SmartLamp smartLamp = (SmartLamp) accessory;
                                isAccessory = true;
                                smartLamp.setKelvin(lineParts[2]);
                                if(Integer.parseInt(lineParts[2]) >= 2000 && Integer.parseInt(lineParts[2]) <= 6500 ){ //don't assign if SetKelvin raises error
                                    smartLamp.setBrightness(lineParts[3]);
                                }
                                break;
                            }
                        }
                        if(!isAccessory){
                            System.out.println("ERROR: There is not such a device!");
                        }
                    }catch (ClassCastException ex){
                        System.out.println("ERROR: This device is not a smart lamp!");
                    }
                }
                break;
            case "SetColor":
                if(lineParts.length != 4){
                    System.out.println("ERROR: Erroneous command!");
                }
                else{
                    try{
                        boolean isAccessory = false;
                        for (Accessory accessory : Accessory.getAccessories()) {
                            if (accessory.getName().equals(lineParts[1])) {
                                SmartColorLamp smartCLamp = (SmartColorLamp) accessory;
                                isAccessory = true;
                                if(smartCLamp.getBrightness() >= 0 && Integer.parseInt(lineParts[3]) <= 100){ //same logic as setWhite goes here
                                    smartCLamp.setColorCode(lineParts[2]);
                                }
                                if(smartCLamp.isSet){smartCLamp.setBrightness(lineParts[3]);}

                                break;
                            }
                        }
                        if(!isAccessory){
                            System.out.println("ERROR: There is not such a device!");
                        }
                    }catch (ClassCastException ex){
                        System.out.println("ERROR: This device is not a smart color lamp!");
                    }
                }
                break;
            case "ZReport":
                ZReport();
                break;
            default:
                System.out.println("ERROR: Erroneous command!");
                break;

        }
    }
    /**
     * Method that checks the accessories list for duplicate names
     */
    public static boolean isObjectWithNameExists(String objectName) {
        for (Accessory accessory : Accessory.getAccessories()) {
            if (accessory.getName().equals(objectName)) {
                return false;
            }
        }
        return true;
    }
    /**
     * Method that prints all accessories sorted.
     * It also contains the sorting algorithm.
     */
    public static void ZReport(){
        //Sorting algorithm
        Accessory.getAccessories().sort((d1, d2) -> { //PROUD
            if (d1.getSwitchTime() == null && d2.getSwitchTime() == null) {
                if(d1.getFormerSwitchTime() != null && d2.getFormerSwitchTime() != null && d2.getFormerSwitchTime().isBefore(d1.getFormerSwitchTime())) {
                    return -1; //if both null but different switch times, one with the latest switch time comes above
                }
                else{return 0;} //if both null and equal switch times before, they are equal

            } else if (d1.getSwitchTime() == null) { //non-nulls are superior to nulls
                return 1;
            } else if (d2.getSwitchTime() == null) {
                return -1;
            } else {
                return d1.getSwitchTime().compareTo(d2.getSwitchTime());
            }
        });
        //ZReport
        System.out.println("Time is:\t" + Time.getCurrentTime().format(Time.formatter));
        for(Accessory accessory: Accessory.getAccessories()){
            switch(accessory.getClass().getName()){
                case "SmartPlug":
                    SmartPlug sp = (SmartPlug) accessory;
                    System.out.println(sp);
                    break;
                case "SmartCamera":
                    SmartCamera sc = (SmartCamera) accessory;
                    System.out.println(sc);
                    break;
                case "SmartLamp":
                    SmartLamp sl = (SmartLamp) accessory;
                    System.out.println(sl);
                    break;
                case "SmartColorLamp":
                    SmartColorLamp scl = (SmartColorLamp) accessory;
                    System.out.println(scl);
                    break;
            }
        }
    }
}
