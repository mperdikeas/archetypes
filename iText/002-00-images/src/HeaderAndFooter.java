import java.io.FileOutputStream;
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

    private static String GAIA_LOGO_FILENAME = "logo_G_sketo-01-edited.jpg";

    private PdfPTable header;
    int _pgNum = 1;

    private Image image(String imageName) throws BadElementException, MalformedURLException, IOException {
        return Image.getInstance(this.getClass().getResource(imageName));
    }

    private PdfPCell image(String imageName, int scalePercentage, Float fixedHeight, int colSpan, int rowSpan) throws BadElementException, MalformedURLException, IOException {
        Image img = image(imageName);
        img.scalePercent(scalePercentage);
        PdfPCell rv = new PdfPCell(new Phrase( new Chunk(img, 0, 0, true)));
        if (fixedHeight!=null)
            rv.setFixedHeight(fixedHeight);
        rv.setColspan(colSpan);
        rv.setRowspan(rowSpan);
        rv.setBorder(Rectangle.BOX);
        rv.setPadding(0);
        return rv;
    }


    private static PdfPCell cell(Phrase phrase, int colspan, int rowspan) {
        return cell(phrase, colspan, rowspan, Rectangle.BOX);
    }

    private static PdfPCell cell(Phrase phrase, int colspan, int rowspan, int border) {
        PdfPCell rv = new PdfPCell(phrase);
        rv.setColspan(colspan);
        rv.setRowspan(rowspan);
        rv.setBorder(border);
        return rv;
    }


    private static PdfPCell cell(String txt, Font font, int colspan, int rowspan) {
                     return cell(       txt,      font,     colspan,     rowspan, Rectangle.BOX);
    }

    private static PdfPCell cell(String txt, Font font, int colspan, int rowspan, int border) {
        return cell(new Phrase(txt, font), colspan, rowspan, border);
    }


    private static PdfPCell cell(int colspan, int rowspan) {
        return cell(" ", DocumentCreator.getNormal(), colspan, rowspan, Rectangle.BOX);
    }

    public HeaderAndFooter() throws BadElementException, MalformedURLException, IOException {
        header = new PdfPTable(new float[]{20, 50, 13, 17});
        header.setTotalWidth(530);
        header.addCell(cell(4, 1));

        header.addCell(image(GAIA_LOGO_FILENAME, 100, null, 1, 3));
        header.addCell(cell(3, 1));        
        header.addCell(cell("Είναι στην κρίση σου | Είναι στο χέρι σου", DocumentCreator.getNormal(), 1, 2));
        header.addCell(image(GAIA_LOGO_FILENAME, 50, null, 1, 2));
        header.addCell(cell("EN ISO 27001:2005 No: 08011005", DocumentCreator.getNormal(), 1, 1));

        header.addCell(cell("EN ISO 27001:2005 No: 08011005", DocumentCreator.getNormal(), 1, 1));
        header.addCell(cell(4, 1));      
        header.addCell(cell(" ", DocumentCreator.getNormal(), 4, Rectangle.BOTTOM));
        System.out.println("header table created");
    }

    private PdfPTable createFooter() {
        PdfPTable footer = new PdfPTable(1);
        footer.setTotalWidth(300);
        
        footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        footer.addCell(new Phrase(new Chunk(String.format("**** Το τμήμα FOOTER του εγγράφου - σελίδα: %d ****", _pgNum++), DocumentCreator.getNormal())
                              .setAction(new PdfAction(PdfAction.FIRSTPAGE))));
        return footer;
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        float x   = document.leftMargin();
        float hei = header.getTotalHeight();
        float y   = document.top()+hei;
        header.writeSelectedRows(0, -1, x , y, cb);
        //ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, header,(document.right() - document.left()) / 2+ document.leftMargin(), document.top() + 10, 0);
        createFooter().writeSelectedRows(0, -1,(document.right() - document.left() - 300) / 2+ document.leftMargin(), document.bottom() - 10, cb);
    }
}