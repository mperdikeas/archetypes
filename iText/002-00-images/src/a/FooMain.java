package a;
import java.io.FileOutputStream; import java.io.OutputStream; import java.io.IOException; import java.awt.GraphicsEnvironment;

import java.util.List; import java.util.ArrayList; import java.util.Arrays; import org.apache.commons.lang3.StringUtils;
 
import com.itextpdf.text.Document; import com.itextpdf.text.DocumentException; import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Chapter; import com.itextpdf.text.Font; import com.itextpdf.text.FontFactory; import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont; import com.itextpdf.text.pdf.PdfWriter;

import static mutil.base.Util.greekText;


 
public class FooMain {
 
    private static final String RESULT = "./paragraphs.pdf";

 
    public static void main(String[] args) throws DocumentException, IOException {
        FooMain.createPdf(RESULT);
    }



    private static void createPdf(String filename) throws DocumentException, IOException {
        DocumentCreator dc = new DocumentCreator(new FileOutputStream(filename));
        dc.chapter("Συνολική Εκτίμηση");
        dc.emptyLines(3);
        dc.table(DocumentCreator.createTable1());
        dc.emptyLines(3);
        dc.paragraph("Συνολική εκτίμηση σε μορφή λίστας");
        dc.list("δράση 1.1: ΝΑΙ", "δράση 1.2: OXI");
        dc.chapter("Εκτίμηση για την Δράση 314.1.1");
        dc.heading("Συνολική Επιλεξιμότητα");
        dc.heading("Συνολική Επιλεξιμότητα 2");
        dc.paragraph(greekText(30));
        dc.heading("Επιλεξιμότητα Προσώπου");
        dc.paragraph(greekText(100));
        dc.levelIn();
        dc.heading("κριτήριο κατοικίας");
        dc.paragraph(greekText(100));
        dc.levelIn();
        dc.heading("κριτήριο οδού");
        dc.heading("κριτήριο αριθμού");
        dc.heading("κριτήριο ορόφου");
        dc.levelOut();
        dc.heading("κριτήριο εισοδήματος");
        dc.paragraph(greekText(100));
        dc.levelOut();
        dc.heading("Επιλεξιμότητα Περιοχής");
        dc.paragraph(greekText(30));    
        dc.levelIn();
        dc.heading("κριτήριο περιφέρειας");
        dc.paragraph(greekText(100));
        dc.heading("κριτήριο δήμου");
        dc.paragraph(greekText(100));
        dc.levelOut();
        dc.heading("Επιλεξιμότητα Κεφαλαίου");
        dc.paragraph(greekText(50));

        dc.chapter("Εκτίμηση για την Δράση 314.1.2");
        dc.heading("Συνολική Επιλεξιμότητα");
        dc.paragraph(greekText(30));
        dc.heading("Επιλεξιμότητα Προσώπου");
        dc.paragraph(greekText(1000));
        dc.heading("Επιλεξιμότητα Κεφαλαίου");
        dc.paragraph(greekText(50));
        dc.close();
    }
}