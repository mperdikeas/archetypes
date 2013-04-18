import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

class HeaderAndFooter extends PdfPageEventHelper{

    private PdfPTable header;

    public HeaderAndFooter() throws Exception {
        header = FooMain.createTable();
        header.setTotalWidth(530);
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();
        float x   = document.leftMargin();
        float hei = header.getTotalHeight();
        float y   = document.top()+hei;
        header.writeSelectedRows(0, -1, x , y, cb);
    }
}
 
public class FooMain {

    public static void main(String[] args) throws Exception {
        Document doc = new Document(PageSize.A4, 30, 30, 150, 40);
        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("./brokenTableInHeader.pdf"));
        writer.setPageEvent(new HeaderAndFooter());
        doc.open();
        doc.add(createTable());
        doc.close();
    }

    public static PdfPTable createTable() throws Exception {
        PdfPTable table = new PdfPTable(2);
        PdfPCell cell = new PdfPCell(new Phrase("cell spanning two rows")); 
        cell.setRowspan(2);
        table.addCell(cell);
        table.addCell( new Phrase ("a"));
        table.addCell( new Phrase ("b"));
        return table;
    }
}