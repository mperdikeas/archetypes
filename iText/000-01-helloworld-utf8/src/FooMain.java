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
 
    private static final String RESULT = "./hello.pdf";
 
    public static void main(String[] args) throws DocumentException, IOException {
        listFonts();
        FooMain.createPdf(RESULT);
    }

    public static final Font BOLD_UNDERLINED = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD | Font.UNDERLINE);

    private static void listFonts() {
        GraphicsEnvironment ge= null;
        ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
        String [] fontNames=ge.getAvailableFontFamilyNames();
        for (int i = 0; i < fontNames.length; i++) {
            System.out.println(fontNames[i]);
        }
    }

    private static void createPdf(String filename) throws DocumentException, IOException {
        Document document = new Document();
         PdfWriter.getInstance(document, new FileOutputStream(filename));
        document.open();
        document.add(new Paragraph("iText αυτά τα ελληνικά δεν δουλεύουν", BOLD_UNDERLINED));
        document.add(new Paragraph("iText αυτά τα ελληνικά δεν δουλεύουν", FontFactory.getFont("Times New Roman"     , BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
        document.add(new Paragraph("iText Αυτά τα ελληνικά δεν δουλεύουν", FontFactory.getFont("DejaVu"              , BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
        document.add(new Paragraph("iText Αυτά τα ελληνικά δεν δουλεύουν", FontFactory.getFont("DejaVu Serif"        , BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
        try {
        document.add(new Paragraph("iText Αυτά τα ελληνικά δεν δουλεύουν", FontFactory.getFont(BaseFont.TIMES_ROMAN  , BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
        } catch (java.nio.charset.UnsupportedCharsetException e) {System.out.println("fails but why ?");}
        try {
        document.add(new Paragraph("iText Αυτά τα ελληνικά δεν δουλεύουν", FontFactory.getFont(BaseFont.HELVETICA    , BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
        } catch (java.nio.charset.UnsupportedCharsetException e) {System.out.println("fails but why ?");}
        document.add(new Paragraph("iText Αυτά τα ελληνικά δεν δουλεύουν", FontFactory.getFont("Times New Roman", "UTF-8", BaseFont.EMBEDDED)));


        BaseFont bf = BaseFont.createFont("DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        document.add(new Paragraph("iText !! Αυτά τα ελληνικά δουλεύουν", new Font(bf, 12)));
        document.close();

    }
}