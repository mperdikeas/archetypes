import java.io.FileOutputStream;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderAndFooter extends PdfPageEventHelper{

    private Phrase header;
    int _pgNum = 1;

    public HeaderAndFooter() {
        header = new Phrase("**** Το τμήμα HEADER του εγγράφου ****", DocumentCreator.getNormal());

    }

    private PdfPTable createFooter() {
        PdfPTable footer = new PdfPTable(1);
        footer.setTotalWidth(300);
        footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        footer.addCell(new Phrase(new Chunk(String.format("**** Το τμήμα FOOTER του εγγράφου - σελίδα: %d ****", _pgNum++), DocumentCreator.getNormal())
                              .setAction(new PdfAction(PdfAction.FIRSTPAGE))));
        return footer;
    }

    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, header,(document.right() - document.left()) / 2+ document.leftMargin(), document.top() + 10, 0);
        createFooter().writeSelectedRows(0, -1,(document.right() - document.left() - 300) / 2+ document.leftMargin(), document.bottom() - 10, cb);
    }
}