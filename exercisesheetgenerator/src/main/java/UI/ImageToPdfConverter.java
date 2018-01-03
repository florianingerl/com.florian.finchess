package UI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class ImageToPdfConverter {

	public static void convertImgToPDF(File imageFile, File pdfFile) {
        try {
			PDDocument document = new PDDocument();
			InputStream in = new FileInputStream(imageFile);
			BufferedImage bimg = ImageIO.read(in);
			float width = bimg.getWidth();
			float height = bimg.getHeight();
			PDPage page = new PDPage(new PDRectangle(width, height));
			document.addPage(page);
			PDImageXObject img = PDImageXObject.createFromFile(imageFile.getAbsolutePath(), document);
			PDPageContentStream contentStream = new PDPageContentStream(document, page);
			contentStream.drawImage(img, 0, 0);
			contentStream.close();
			in.close();
			document.save(pdfFile);
			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        System.out.println("Wrote the pdf file!");
    }

}
