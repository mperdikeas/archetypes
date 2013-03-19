import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.awt.GraphicsEnvironment;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;
 
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

    private static int HEADING_IDX[] = {0,0,0,0,0,0,0,0,0,0,0,0};
    private int headingIdx[] = HEADING_IDX;
    private int level ;
    private void clearHeadings() {
        for (int i = 0 ; i < headingIdx.length ; i++)
            headingIdx[i] = 0;
    }

    public void levelIn() {
        headingIdx[level-1]--;
        level++;
        if (level == headingIdx.length)
            throw new RuntimeException();
    }

    public void levelOut() {
        headingIdx[level-1]=0;
        level--;
        headingIdx[level-1]++;
    }

    public void printVector(String header) {
        System.out.println(header);
        for (int i = 0 ; i < level ; i++)
            System.out.print(headingIdx[i] + " ");
        System.out.println("<-- end of vector");
    }

    private List<Integer> nextHeadingVector() {
        List<Integer> rv = new ArrayList<Integer>();
        for (int i = 0 ; i < level-1 ; i++) {
            rv.add(headingIdx[i]+1);
        }
        rv.add(++headingIdx[level-1]);
        return rv;
    }

    private String nextHeadingVector(List<Integer> headingElems) {
        return StringUtils.join(headingElems, ".");            
    }

    private String nextHeadingVectorS() {
        return StringUtils.join(nextHeadingVector(), ".");            
    }


    public void heading(String headingTitle) throws DocumentException {
        document.add(new Paragraph(String.format("%d - %s", _chaptIdx, nextHeadingVectorS())+" - "+headingTitle, HEADING));
    }

    
    public DocumentCreator(OutputStream os) throws DocumentException {
        this.document = new Document();
        PdfWriter.getInstance(this.document, os);
        this.document.open();
        this._chaptIdx = 0;
        this.level = 0;
    }
    public void chapter(String chapterTitle) throws DocumentException {
        document.add(new Chapter(new Paragraph(chapterTitle, CHAPTER), ++_chaptIdx));
        clearHeadings();
        level = 1;
    }


    private static Paragraph _emptyLines(int n) throws DocumentException {
        Paragraph rv = new Paragraph(" ");
        for (int i = 0; i < n-1; i++) {
            rv.add(new Paragraph(" "));
        }
        return rv;
    }

    public void emptyLines(int n) throws DocumentException {
        document.add(_emptyLines(n));
    }

    public void paragraph(String text) throws DocumentException {
        Paragraph p = new Paragraph(text, NORMAL);
        p.setFirstLineIndent(15);
        document.add(p);
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
        dc.chapter("Εκτίμηση για την Δράση 314.1.1");
        dc.emptyLines(3);
        dc.printVector("A:");
        dc.heading("Συνολική Επιλεξιμότητα");
        dc.printVector("B:");
        dc.heading("Συνολική Επιλεξιμότητα 2");
        dc.printVector("C:");
        dc.paragraph(greekText(30));
        dc.emptyLines(1);
        dc.heading("Επιλεξιμότητα Προσώπου");
        dc.paragraph(greekText(100));
        dc.emptyLines(1);
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
        dc.emptyLines(3);
        dc.heading("Συνολική Επιλεξιμότητα");
        dc.paragraph(greekText(30));
        dc.emptyLines(1);
        dc.heading("Επιλεξιμότητα Προσώπου");
        dc.paragraph(greekText(1000));
        dc.emptyLines(1);
        dc.heading("Επιλεξιμότητα Κεφαλαίου");
        dc.paragraph(greekText(50));
        dc.close();
    }
}