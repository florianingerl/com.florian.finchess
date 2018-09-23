package UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import Model.PgnGame;
import Model.Position;
import Model.ReadPGN;
import Model.ReadPGNHeaders;
import Model.VariationTree;

public class SolutionSheetGenerator {

	public static void generateSolutionSheet(File pgnDatabase, File solutionFile) throws IOException {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		
		ReadPGN rpgn = new ReadPGN(pgnDatabase);
		List<PgnGame> games = rpgn.getListOfGames();
		
		for(PgnGame game : games) {
			sb.append("Aufgabe " + (i+1) +"\n\n" );
			
			VariationTree vt = game.getGame();
			sb.append("\\newgame\n");
			if(game.getFenString() != null) {
			sb.append("\\fenboard{");
			sb.append(game.getFenString());
			sb.append("}\n");
			}
			sb.append("\\mainline{");
			
			Position pos = Position.fromFenString(game.getFenString());
			
			if(game.getFenString() != null)
				sb.append(vt.toStringSAN(game.getFenString()));
			else 
				sb.append(vt.toStringSAN());
			sb.append("}\n\n");
			++i;
		}
		
		try (PrintWriter out = new PrintWriter(solutionFile)) {
		    out.println(sb.toString());
		}
	}
	
	public static void main(String [] args) {
		try {
			generateSolutionSheet(new File("C:\\Users\\Emmi_\\Downloads\\AlbertKaunzinger.pgn"), new File("C:\\Users\\Emmi_\\Desktop\\SolutionSheet.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Finished!");
	}
	
}
