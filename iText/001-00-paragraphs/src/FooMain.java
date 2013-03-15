import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.GraphicsEnvironment;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
 
public class FooMain {
 
    private static final String RESULT = "./paragraphs.pdf";

    private static BaseFont bf = null;
    private static Font TITLE  = null;
    private static Font NORMAL = null;

    static {
        try {
            bf     = BaseFont.createFont("DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            TITLE  = new Font(bf, 14, Font.BOLD | Font.UNDERLINE);
            NORMAL = new Font(bf, 11);
        } catch (DocumentException e) {
            System.out.println(e);  
        } catch (IOException e) {
            System.out.println(e);  
        }
    }
 
    public static void main(String[] args) throws DocumentException, IOException {
        FooMain.createPdf(RESULT);
    }

    private static String text(int n) {
        String[] seed = {"πιστεύω", "εις", "έναν", "Θεό", "πατέρα", "παντοκράτορα", "ποιητή", "ούρανού", "και", "γης", "ορατών", "τε", "πάντων",
                         "και", "αοράτων.", "Και", "εις", "έναν", "Κύριον", "Ιησούν", "Χριστόν", "τον", "υιό", "του", "Θεού", "τον", "μονογενή",
                         ",", "τον", "εκ", "του", "πατρός", "γεννηθέντα", "προ", "πάντων", "των", "αιώνων.", "Φως", "εκ", "φωτός", "θεόν",
                         "αληθινόν", "εκ", "θεού", "αληθινού", "γεννηθέντα", "ου", "ποιηθέντα", "ομοούσιο", "τω", "Πατρί", "δι", "ου", "τα", "πάντα", "εγένετο."};
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < n ; i++)
            sb.append(seed[i % seed.length]+" ");
        return sb.toString()+"Αμήν.";
    }

    private static Paragraph emptyLines(int number) {
        Paragraph rv = new Paragraph(" ");
        for (int i = 0; i < number-1; i++) {
            rv.add(new Paragraph(" "));
        }
        return rv;
    }

    private static void createPdf(String filename) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();
        document.add(new Paragraph("Εκτίμηση για την Δράση 314.1.2", TITLE));
        document.add(emptyLines(3));
        document.add(new Paragraph("Συνολική Επιλεξιμότητα - "+text(  30) , NORMAL));
        document.add(emptyLines(1));
        document.add(new Paragraph("Επιλεξιμότητα Προσώπου- "+text(1000) , NORMAL));
        document.add(emptyLines(1));
        document.add(new Paragraph("Επιλεξιμότητα Κεφαλαίου - "+text(   3) , NORMAL));
        document.close();
    }
}