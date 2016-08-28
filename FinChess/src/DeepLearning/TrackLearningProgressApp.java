package DeepLearning;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TrackLearningProgressApp extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Drawing Operations Test");
		GridPane root = new GridPane();

		TrackLearningProgressCanvas canvas = new TrackLearningProgressCanvas();
		ScrollPane scrollPane = new ScrollPane();
		// scrollPane.setPrefSize(1010, 110);
		scrollPane.setContent(canvas);

		canvas.addXYValue(new Point2D(10000, 0.5));
		canvas.addXYValue(new Point2D(20000, 0.7));
		canvas.addXYValue(new Point2D(30000, 0.4));

		root.getChildren().add(scrollPane);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

}
