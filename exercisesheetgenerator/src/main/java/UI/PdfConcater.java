package UI;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;


/**
 * Tool that can be used to concatenate existing PDF files.
 * @since 2.1.1 (renamed to follow Java naming conventions)
 */
public class PdfConcater {
    
    /**
     * This class can be used to concatenate existing PDF files.
     * (This was an example known as PdfCopy.java)
     * @param args the command line arguments
     * @throws IOException 
     * @throws InvalidPasswordException 
     */
    public static void concatPdfs(File dir, File outFile) throws InvalidPasswordException, IOException {
       
        List<File> pdfFiles = new LinkedList<File>();
        
        collectFiles(dir, pdfFiles);
        
        PDFMergerUtility pdfMerger = new PDFMergerUtility();
        PDDocument document = new PDDocument();
        
        for(File f : pdfFiles) {
			System.out.println("Merging " + f);
        	PDDocument toMerge = PDDocument.load(f);
        	pdfMerger.appendDocument(document, toMerge );
        	toMerge.close();
        }
        
        document.save(outFile);
        document.close();
        System.out.println("Finished!");
        
    }

    private static Comparator<File> creationDateComparator = new Comparator<File>() {

		@Override
		public int compare(File f1, File f2) {
			return (int) (f1.lastModified() - f2.lastModified() );
		}
    	
    };
    
	private static void collectFiles(File dir, List<File> pdfFiles) {
		
		File [] files = dir.listFiles();
		Arrays.sort( files, creationDateComparator );
		for(File f : files ) {
			if(f.isDirectory() )
				collectFiles(f, pdfFiles);
			else if( f.getName().endsWith(".pdf"))
				pdfFiles.add(f);
		}
	}
}
