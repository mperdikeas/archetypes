import au.com.bytecode.opencsv.*;
import java.io.FileReader;
public class OpenCSVTest {


    public static void main(String args[]) throws Exception {
        CSVReader reader = new CSVReader(new FileReader("test.csv"));
        String [] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            System.out.println("("+nextLine[0] + "),(" + nextLine[1] + "),(" + nextLine[2] + "),(" + nextLine[3]+ ") etc...");
        }
    }
}