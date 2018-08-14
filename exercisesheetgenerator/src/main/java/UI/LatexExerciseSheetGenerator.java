package UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import Model.PgnGame;
import Model.Position;
import Model.ReadPGNHeaders;

public class LatexExerciseSheetGenerator {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		ReadPGNHeaders reader = new ReadPGNHeaders(new FileInputStream("C:/GitChess/KnightVsTwoPawns.pgn") );
		reader.parseHeaders();
		
		List<PgnGame > games = reader.getListOfGames();
		
		int i = 1;
		for(PgnGame game : games) {
			System.out.println(game.getFenString());
			Position position = Position.fromFenString(game.getFenString());
			
			File dir = new File("C:/GitChess/ImagesOfExercises");
			PositionImageGenerator pig = new PositionImageGenerator(position);
			
			pig.createImageFile(new File(dir, "Aufgabe"+i+".png" ));
			++i;
		}

	}

}
