package main.java.utility;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by ritobrotoseth on 23/09/18.
 */
public class PdfUtility {

  public void writeToPdf(List<String> stringList, String fileName) {
    Document document = writeToPdfSetup(fileName);

    int counter = 1;
    for(String value : stringList) {
      try {
        Paragraph paragraph = new Paragraph();
        paragraph.add(counter + ") " + value);
        document.add(paragraph);

//        Paragraph nextLine = new Paragraph();
//        nextLine.add("\t");
//        document.add(nextLine);
        counter++;
      } catch (DocumentException e) {
        e.printStackTrace();
      }
    }

    writeToPdfClose(document);
  }

  private Document writeToPdfSetup(String fileName) {
    Document document = new Document();
    try {
      PdfWriter.getInstance(document, new FileOutputStream(fileName));
    } catch (DocumentException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    document.open();
    Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
    return document;
  }

  private void writeToPdfClose(Document document) {
    document.close();
  }
}
