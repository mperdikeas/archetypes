import au.com.bytecode.opencsv.*;
import java.io.FileReader;
import java.io.File;
import java.io.CharArrayReader;

import mutil.base.FileUtil;

public class OpenCSVTest {


    public static void main(String args[]) throws Exception {
        byte[] testData = FileUtil.readAsByteArray(new File("test.csv"));
        char[] testChars = FileUtil.toCharArray(testData);
        
        CSVReader reader = new CSVReader(new CharArrayReader(testChars));
        String [] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            System.out.println("("+nextLine[0] + "),(" + nextLine[1] + "),(" + nextLine[2] + "),(" + nextLine[3]+ ") etc...");
        }
    }
}