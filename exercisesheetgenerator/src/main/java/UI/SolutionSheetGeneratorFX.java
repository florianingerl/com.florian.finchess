package UI;

import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class SolutionSheetGeneratorFX extends Application {

	private List<Exercise> exercises;
	
	private GridPane gridPane;
	
	@Override
	public void start(Stage stage) throws Exception {
		exercises = ExerciseSheetGenerator.parseExercisesFromStream(
				ExerciseSheetGenerator.class.getClassLoader().getResourceAsStream("SpringerGegenBauer2.txt"));
		buildUI();
		Scene scene = new Scene(gridPane, PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight());
		scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm() );
		
		stage.setScene(scene); 
        stage.setResizable(false);
        stage.show();  
        
        WritableImage image = gridPane.snapshot(new SnapshotParameters(), null);

        // TODO: probably use a file chooser here
        File imageFile = new File("C:/Users/Hermann/ExerciseSheet.png");
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", imageFile);
        
        ImageToPdfConverter.convertImgToPDF(imageFile, new File("C:/Users/Hermann/Desktop/ExerciseSheet.pdf"));

		System.out.println("Finished!");
	}
	
	private void buildUI() {
		gridPane = new GridPane();
		
		int i = 0;
		for(Exercise exercise: exercises) {
			
			++i;
		}
	}
	
	public static void main(String [] args) {
		Application.launch(args);
	}

}
