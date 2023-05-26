package ru.eustas.pdfk;

import java.io.IOException;

import com.itextpdf.kernel.pdf.PdfReader;

public class Main {

    public static void main(String[] args) throws IOException {
        PdfReader reader = new PdfReader(args[0]);
        reader.close();
    }
}
