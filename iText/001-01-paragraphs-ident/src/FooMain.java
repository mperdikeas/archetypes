import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.awt.GraphicsEnvironment;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import static mutil.base.Util.greekText;


class DocumentCreator {
    private static BaseFont bf   = null;
    private static Font TITLE    = null;
    private static Font CHAPTER  = null;
    private static Font HEADING  = null;
    private static Font NORMAL   = null;

    static {
        try {
            bf     = BaseFont.createFont("DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            TITLE    = new Font(bf, 14, Font.BOLD | Font.UNDERLINE);
            CHAPTER  = new Font(bf, 13, Font.BOLD | Font.ITALIC);
            HEADING  = new Font(bf, 12, Font.BOLD | Font.ITALIC);
            NORMAL   = new Font(bf, 11);
        } catch (DocumentException e) {
            System.out.println(e);  
        } catch (IOException e) {
            System.out.println(e);  
        }
    }

    private Document     document;
    private OutputStream os;
    private int _chaptIdx;
    private int _headingIdx;
    
    public DocumentCreator(OutputStream os) throws DocumentException {
        this.document = new Document();
        PdfWriter.getInstance(this.document, os);
        this.document.open();
        this._chaptIdx = 0;
        this._headingIdx = 0;
    }
    public void newChapter(String chapterTitle) throws DocumentException {
        document.add(new Chapter(new Paragraph(chapterTitle, CHAPTER), ++_chaptIdx));
        _headingIdx = 0;
    }


    private static Paragraph _emptyLines(int number) throws DocumentException {
        Paragraph rv = new Paragraph(" ");
        for (int i = 0; i < number-1; i++) {
            rv.add(new Paragraph(" "));
        }
        return rv;
    }

    public void emptyLines(int n) throws DocumentException {
        document.add(_emptyLines(n));
    }

    public void newHeading(String headingTitle) throws DocumentException {
        document.add(new Paragraph(String.format("%d.%d", _chaptIdx, ++_headingIdx)+" - "+headingTitle, HEADING));
    }

    public void newParagraph(String text) throws DocumentException {
        document.add(new Paragraph(text, NORMAL));
    }
    
    public void close() throws DocumentException {
        document.close();
    }

}
 
public class FooMain {
 
    private static final String RESULT = "./paragraphs.pdf";

 
    public static void main(String[] args) throws DocumentException, IOException {
        FooMain.createPdf(RESULT);
    }



    private static void createPdf(String filename) throws DocumentException, IOException {
        DocumentCreator dc = new DocumentCreator(new FileOutputStream(filename));
        dc.newChapter("Εκτίμηση για την Δράση 314.1.1");
        dc.emptyLines(3);
        dc.newHeading("Συνολική Επιλεξιμότητα");
        dc.newParagraph(greekText(30));
        dc.emptyLines(1);
        dc.newHeading("Επιλεξιμότητα Προσώπου");
        dc.newParagraph(greekText(1000));
        dc.emptyLines(1);
        dc.newHeading("Επιλεξιμότητα Κεφαλαίου");
        dc.newParagraph(greekText(50));

        dc.newChapter("Εκτίμηση για την Δράση 314.1.2");
        dc.emptyLines(3);
        dc.newHeading("Συνολική Επιλεξιμότητα");
        dc.newParagraph(greekText(30));
        dc.emptyLines(1);
        dc.newHeading("Επιλεξιμότητα Προσώπου");
        dc.newParagraph(greekText(1000));
        dc.emptyLines(1);
        dc.newHeading("Επιλεξιμότητα Κεφαλαίου");
        dc.newParagraph(greekText(50));
        dc.close();
    }
}