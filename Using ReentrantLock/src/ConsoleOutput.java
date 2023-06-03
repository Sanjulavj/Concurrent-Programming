import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleOutput {
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String GREEN = "\u001B[32m";
    public static final String RESET = "\u001B[0m";

    public enum MessageFormat{
        INFO,
        WARN,
        ERROR;
    }
    public enum DataFormat {
        PRINTER,
        STUDENT,
        TONER_TECHNICIAN,
        PAPER_TECHNICIAN,
        PRINTING_SYSTEM,
    }

    /**
     * print outputs all the messages of the system with relevant color and info changes.
     * And writing all the console outputs to a text file
     *
     * @param dataFormat data type of the output
     * @param message message of the output
     * @param infoType info type of the output
     */

    public static synchronized void print(DataFormat dataFormat, String message, MessageFormat infoType) {
        String output = "";
        String infoOutput = "";

        Date date = new Date(System.currentTimeMillis());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String timeDateFormat = dateFormat.format(date);

        infoOutput = "[" + infoType.toString().toUpperCase() + "] ";
        output = " [" + timeDateFormat + "]  " + "[" + dataFormat.toString().toUpperCase()
                + "]  " + message;

        if (infoType == MessageFormat.INFO) {
            coloredOutput(GREEN, dataFormat, infoOutput,output);


        } else if (infoType == MessageFormat.WARN) {
            coloredOutput(YELLOW, dataFormat, infoOutput,output);

        } else if (infoType == MessageFormat.ERROR) {
            coloredOutput(RED, dataFormat, infoOutput,output);
        }

        //writing outputs to a file
        try {
            FileWriter myWriter = new FileWriter("Outputs.txt", true);
            myWriter.write(infoOutput + output + "\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * coloredOutput method manage all the messages with relevant colors
     *
     * @param color adding relevant colors
     * @param dataFormat data type of the output
     * @param infoOutput info output
     * @param output message output
     */
    private static void coloredOutput(String color, DataFormat dataFormat, String infoOutput, String output){
        if(dataFormat == DataFormat.PRINTING_SYSTEM){
            System.out.println(color + infoOutput + WHITE + output + RESET);
        } else if (dataFormat == DataFormat.STUDENT) {
            System.out.println(color + infoOutput + BLUE + output + RESET);
        }else if (dataFormat == DataFormat.TONER_TECHNICIAN) {
            System.out.println(color + infoOutput + PURPLE + output + RESET);
        }else if (dataFormat == DataFormat.PAPER_TECHNICIAN) {
            System.out.println(color + infoOutput + CYAN + output + RESET);
        }else if (dataFormat == DataFormat.PRINTER) {
            System.out.println(color + infoOutput + RESET + output);
        }

    }

    /**
     * clearFile method to clear out all the data in the file when system restarts
     *
     */
    public static void clearFile(){
        try {
            FileWriter fileWriter = new FileWriter("Outputs.txt", false);
            PrintWriter printWriter = new PrintWriter(fileWriter, false);
            printWriter.flush();
            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
