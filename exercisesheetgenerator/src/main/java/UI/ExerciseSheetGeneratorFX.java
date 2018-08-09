package UI;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

import javafx.application.Application;

public class ExerciseSheetGeneratorFX extends Application {

	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		
		ChessboardFX chessboard = new ChessboardFX();
		Scene scene = new Scene(chessboard, 800, 800);
        stage.setTitle("Hello World Example");        
        stage.setScene(scene);        
        stage.show();  

		WritableImage image = chessboard.snapshot(new SnapshotParameters(), null);

		// TODO: probably use a file chooser here
		File imageFile = new File("C:/Users/Hermann/Desktop/ChessboardFX.png");

		ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", imageFile);
		
		ImageToPdfConverter.convertImgToPDF(imageFile, new File("C:/Users/Hermann/Desktop/ChessboardFX.pdf"));

		System.out.println("Finished!");
	}
}
