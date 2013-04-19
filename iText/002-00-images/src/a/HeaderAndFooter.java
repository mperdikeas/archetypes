package a;
import java.io.FileOutputStream;
import java.util.List; import java.util.ArrayList;
import java.io.IOException;
import java.net.MalformedURLException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Font;
import com.itextpdf.text.DocumentException;

public class HeaderAndFooter extends PdfPageEventHelper{

    private static String GAIA_LOGO_FILENAME = "logo-edited.png";
    private static String TUV_LOGO_FILENAME  = "tuv-edited.png";
    private static String BAR_FILENAME       = "tresa-edited.png";
    private PdfPTable header;
    private PdfPTable footer;
    int _pgNum = 1;

    private Image image(String imageName) throws BadElementException, MalformedURLException, IOException {
        java.net.URL imgURL = this.getClass().getResource(imageName);
        System.out.println("looking for: "+imgURL);
        return Image.getInstance(imgURL);
    }

    private static boolean DEBUG_BORDERS = false;

    private static int defaultBorder() {
        if (DEBUG_BORDERS)
            return Rectangle.BOX;
        else
            return 0;
    }


    private PdfPCell image(String imageName, int scalePercentage, Float fixedHeight, int colSpan, int rowSpan) throws BadElementException, MalformedURLException, IOException {
        Image img = image(imageName);
        img.scalePercent(scalePercentage);
        PdfPCell rv = new PdfPCell(new Phrase( new Chunk(img, 0, 0, true)));
        if (fixedHeight!=null)
            rv.setFixedHeight(fixedHeight);
        rv.setColspan(colSpan);
        rv.setRowspan(rowSpan);
        rv.setBorder(defaultBorder());
        rv.setPadding(1);
        return rv;
    }




    private static PdfPCell cell(Phrase phrase, int colspan, int rowspan) {
        return cell(phrase, null, Element.ALIGN_RIGHT, Element.ALIGN_MIDDLE, colspan, rowspan, defaultBorder());
    }

    private static PdfPCell cell(Phrase phrase, Integer fixedHeight, int horizAlign, int vertAlign,  int colspan, int rowspan, int border) {
        PdfPCell rv = new PdfPCell(phrase);
        rv.setColspan(colspan);
        rv.setRowspan(rowspan);
        rv.setBorder(border);
        rv.setHorizontalAlignment(horizAlign);
        rv.setVerticalAlignment(vertAlign);
        rv.setPadding(3);
        if (fixedHeight != null)
            rv.setFixedHeight(fixedHeight);
        return rv;
    }

    private static Phrase itsInYourHand() {
        Font main = new Font(DocumentCreator.getSmall());
        main.setSize(8);
        Font bold = new Font(main);
        bold.setStyle("bold");
        Phrase rv = new Phrase();
        List<Chunk> chunks = new ArrayList<>();
        chunks.add( new Chunk(" ",  main));
        chunks.add( Chunk.NEWLINE);
        chunks.add( new Chunk("Είναι στην",  main));
        chunks.add( new Chunk(" ",  main));
        chunks.add( new Chunk("κρίση", bold));
        chunks.add( new Chunk(" ",  main));
        chunks.add( new Chunk("σου | Είναι στο",  main));
        chunks.add( new Chunk(" ",  main));
        chunks.add( new Chunk("χέρι", bold));
        chunks.add( new Chunk(" ",  main));
        chunks.add( new Chunk("σου",  main));
        for (Chunk chunk : chunks)
            rv.add(chunk);
        return rv;
    }

    private static Phrase iso(String iso, String number) {
        Font main = new Font(DocumentCreator.getTeenyWeeny());
        Font bold = new Font(main);
        bold.setStyle("bold");
        Phrase rv = new Phrase();
        List<Chunk> chunks = new ArrayList<>();
        chunks.add( new Chunk(iso,  bold));
        chunks.add(Chunk.NEWLINE);
        chunks.add( new Chunk(" ",  main));
        chunks.add( new Chunk(number,  main));
        for (Chunk chunk : chunks)
            rv.add(chunk);
        return rv;
    }


    private static PdfPCell cell(String txt, Font font, Integer fixedHeight, int horizAlign, int vertAlign, int colspan, int rowspan) {
        return cell(                    txt,      font,         fixedHeight,     horizAlign,     vertAlign,     colspan,     rowspan, defaultBorder());
    }

    private static PdfPCell cell(String txt, Font font, Integer fixedHeight, int horizAlign, int vertAlign, int colspan, int rowspan, int border) {
        return cell(         new Phrase(txt,      font),        fixedHeight,     horizAlign,     vertAlign,     colspan,     rowspan,     border);
    }

    private static PdfPCell cell(int colspan, int rowspan) {
        return cell((Integer) null, colspan, rowspan);
    }

    private static PdfPCell cell(Integer fixedHeight, int colspan, int rowspan) {
        return cell(" ", DocumentCreator.getSmall(), fixedHeight, Element.ALIGN_RIGHT, Element.ALIGN_CENTER, colspan, rowspan);
    }

    public HeaderAndFooter() throws BadElementException, MalformedURLException, IOException {
        header = new PdfPTable(new float[]{20, 50, 10, 20});
        //        header.setTotalWidth(800);
        header.addCell(image(GAIA_LOGO_FILENAME, 100, 60f, 1, 4));
        header.addCell(cell(5, 3, 1));
        header.addCell(cell(5, 3, 1));        
        header.addCell(cell(itsInYourHand(), null, Element.ALIGN_CENTER, Element.ALIGN_TOP, 1, 2, defaultBorder()));
        header.addCell(image(TUV_LOGO_FILENAME, 80, null, 1, 2));
        header.addCell(cell(iso("    EN  ISO  27001:2005", "       No: 08011005"), null, Element.ALIGN_LEFT, Element.ALIGN_TOP, 1, 1, defaultBorder()));
        header.addCell(cell(iso("    EN  ISO   9001:2008", "       No: 01011148"), null, Element.ALIGN_LEFT, Element.ALIGN_TOP, 1, 1, defaultBorder()));
        header.addCell(cell("Εκτίμηση επιδοτήσεων για: ΠΑΠΑΔ/ΛΟΣ ΓΕΩΡΓΙΟΣ, ΑΦΜ: 239829387, 23/04/2013 13:32", DocumentCreator.getSmall(), null, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, 4, 1, Rectangle.BOTTOM));
        System.out.println("header table created");
        this.footer = createFooter();
    }

    private PdfPTable createFooter() throws BadElementException, MalformedURLException, IOException {
        PdfPTable footer = new PdfPTable(new float[]{20, 42, 12, 12, 12, 18});
        footer.setTotalWidth(300);
        
        footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        Font f  = DocumentCreator.getTeenyWeeny();
        Font f2 = DocumentCreator.getSmall();
        footer.addCell(image(BAR_FILENAME, 100, null, 6, 1));
        footer.addCell(cell("ΚΕΝΤΡΙΚΑ ΓΡΑΦΕΙΑ"                            , f, null, Element.ALIGN_LEFT, Element.ALIGN_BOTTOM, 1, 1, defaultBorder()));
        footer.addCell(cell("Αιτωλικού και Σφακτηρίας 11, 185 45 Πειραιάς", f, null, Element.ALIGN_LEFT, Element.ALIGN_BOTTOM, 1, 1, defaultBorder()));
        footer.addCell(cell("T: 210 4101079"                              , f, null, Element.ALIGN_LEFT, Element.ALIGN_BOTTOM, 1, 1, defaultBorder()));
        footer.addCell(cell("F: 210 4101013"                              , f, null, Element.ALIGN_LEFT, Element.ALIGN_BOTTOM, 1, 1, defaultBorder()));
        footer.addCell(cell("E: info@c-gaia.gr"                           , f, null, Element.ALIGN_LEFT, Element.ALIGN_BOTTOM, 1, 1, defaultBorder()));
        footer.addCell(cell("N/M"                                         , f, null, Element.ALIGN_LEFT, Element.ALIGN_BOTTOM, 1, 1, defaultBorder()));

        footer.addCell(cell("ΓΡΑΦΕΙΑ Β. ΕΛΛΑΔΑΣ"                          , f, null, Element.ALIGN_LEFT, Element.ALIGN_BOTTOM, 1, 1, defaultBorder()));
        footer.addCell(cell("Πλατεία Συντριβανίου 4, 54621 Θεσσαλονίκη"   , f, null, Element.ALIGN_LEFT, Element.ALIGN_BOTTOM, 1, 1, defaultBorder()));
        footer.addCell(cell("T: 2310 4101079"                             , f, null, Element.ALIGN_LEFT, Element.ALIGN_BOTTOM, 1, 1, defaultBorder()));
        footer.addCell(cell("F: 2310 383260"                              , f, null, Element.ALIGN_LEFT, Element.ALIGN_BOTTOM, 1, 1, defaultBorder()));
        footer.addCell(cell("E: info@c-gaia.gr"                           , f, null, Element.ALIGN_LEFT, Element.ALIGN_BOTTOM, 1, 1, defaultBorder()));
        footer.addCell(cell(""                                            , f, null, Element.ALIGN_LEFT, Element.ALIGN_BOTTOM, 1, 1, defaultBorder()));

        footer.addCell(cell("www.c-gaia.gr", f2, null, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, 6, 1));


        /*
        footer.addCell(new Phrase(new Chunk(String.format("**** Το τμήμα FOOTER του εγγράφου - σελίδα: %d ****", _pgNum++), DocumentCreator.getNormal())
        .setAction(new PdfAction(PdfAction.FIRSTPAGE))));*/
        return footer;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        float x   = document.leftMargin();
        float hei = header.getTotalHeight();
        float y   = document.top()+hei;
        boolean toggleBetweenTheTwoSolutions = false;
        if (toggleBetweenTheTwoSolutions) { // see: http://stackoverflow.com/questions/16087542/itext-setrowspan-not-working-for-tables-in-headers
            header.writeSelectedRows(0, -1, x , y, cb);
        } else {
            ColumnText column = new ColumnText(writer.getDirectContent());
            column.addElement(header);
            column.setSimpleColumn (0, 0, 630, 830); // (-12, -20, 604, 803); // set LLx, LLy, URx, and URy of the header
            try {   
                column.go();
            } catch (DocumentException de) {
                throw new RuntimeException();
            }
        }
        //ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, header,(document.right() - document.left()) / 2+ document.leftMargin(), document.top() + 10, 0);
        if (toggleBetweenTheTwoSolutions) {
            footer.writeSelectedRows(0, -1,(document.right() - document.left() - 300) / 2+ document.leftMargin(), document.bottom() - 10, cb);
        } else {
            ColumnText column = new ColumnText(writer.getDirectContent());
            column.addElement(footer);
            column.setSimpleColumn (0, 0, 630, 80); // (-12, -20, 604, 803); // set LLx, LLy, URx, and URy of the header
            try {   
                column.go();
            } catch (DocumentException de) {
                throw new RuntimeException();
            }
        }
    }
}