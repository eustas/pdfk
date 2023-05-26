package ru.eustas.pdfk;

import java.io.IOException;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

public class Main {

    public static void main(String[] args) throws IOException {
        String in = args[0];
        String out = args[1];
        float targetW = 72.0f * 210.0f / 25.4f;
        float targetH = 72.0f * 297.0f / 25.4f;
        System.out.println(targetW + " x " + targetH);
        PdfDocument doc = new PdfDocument(new PdfReader(in), new PdfWriter(out));
        int n = doc.getNumberOfPages();
        for (int i = 1; i <= n; ++i) {
            PdfPage p = doc.getPage(i);
            System.out.println(p.getCropBox() + " | " + p.getTrimBox() + " | " + p.getMediaBox());
            PdfDictionary d = p.getPdfObject();
            Rectangle mBox = p.getMediaBox();
            float w = mBox.getWidth();
            float h = mBox.getHeight();
            float deltaW = targetW - w;
            float deltaH = targetH - h;
            System.out.println(w + " / " + targetW + " | " + h + " / " + targetH);
            float x = mBox.getX() - (deltaW / 2.0f);
            float y = mBox.getY() - (deltaH / 2.0f);
            mBox = new Rectangle(x, y, x + targetW, y + targetH);
            d.remove(PdfName.MediaBox);
            d.remove(PdfName.CropBox);
            d.remove(PdfName.TrimBox);
            d.put(PdfName.MediaBox, new PdfArray(new PageSize(mBox)));
            //d.put(PdfName.CropBox, new PdfArray(new PageSize(new Rectangle(0, 0, 0.5f, 0.5f))));
        }
        doc.close();
    }
}
