import java.io.FileOutputStream;
import java.io.IOException;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
 
public class FooMain {
 
    private static final String RESULT = "./hello.pdf";
 
    public static void main(String[] args) throws DocumentException, IOException {
        FooMain.createPdf(RESULT);
    }

    private static void createPdf(String filename) throws DocumentException, IOException {
        Document document = new Document();
         PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();
        document.add(new Paragraph("Hello World!"));
        document.close();
    }
}