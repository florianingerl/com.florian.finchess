package UI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

import Model.PgnGame;
import Model.Position;
import Model.ReadPGNHeaders;

public class LatexExerciseSheetGenerator {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		ReadPGNHeaders reader = new ReadPGNHeaders(new FileInputStream("D:\\ChessGit\\KnightVsTwoPawns.pgn") );
		reader.parseHeaders();
		
		List<PgnGame > games = reader.getListOfGames();
		
		File flatexBlueprint = getFileFromResources("LatexVorlage.tex");
		String sLatexBlueprint = readFile(flatexBlueprint);
		
		int index = sLatexBlueprint.indexOf("Aufgaben");
		
		StringBuilder sb = new StringBuilder();
		sb.append(sLatexBlueprint.substring(0, index));
		
		
		int i = 1;
		for(PgnGame game : games) {
			if(i==13) break;
			if(i%3!=1) {
				sb.append(" & ");
			}
			
			sb.append("\\chessboard[setfen="+game.getFenString() + "]");
			
			if(i%3==0) {
				sb.append("\\\\ \n");
			}
			
			++i;
		}
		
		sb.append(sLatexBlueprint.substring(index+"Aufgaben".length()));
		
		try (PrintWriter out = new PrintWriter("Chess.tex")) {
		    out.println(sb);
		}
		
		System.out.println("Finished!");

	}
	
	private static File getFileFromResources(String fileName) {

        ClassLoader classLoader = LatexExerciseSheetGenerator.class.getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }

    }
	
	 private static String readFile(File file) throws IOException {

	        if (file == null) return null;

	        StringBuilder sb = new StringBuilder();
	        try (FileReader reader = new FileReader(file);
	             BufferedReader br = new BufferedReader(reader)) {

	            String line;
	            while ((line = br.readLine()) != null) {
	               sb.append(line+"\n");
	            }
	        }
	        return sb.toString();
	    }

}
