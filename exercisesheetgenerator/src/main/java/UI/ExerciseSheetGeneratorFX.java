package UI;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

import Model.Position;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.Group;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.BorderPane;

public class ExerciseSheetGeneratorFX extends Application {
	
	public static Map<Integer, Image> pieceIcons;

	static {
		loadPieceIcons();
	}

	private static void loadPieceIcons() {
		pieceIcons = new HashMap<Integer, Image>();
		ClassLoader cl = ExerciseSheetGeneratorFX.class.getClassLoader();

		pieceIcons.put(Position.W_QUEEN, new Image(cl.getResourceAsStream("ImagesOfPieces/WhiteQueenIcon.png")));
		pieceIcons.put(Position.B_QUEEN, new Image(cl.getResourceAsStream("ImagesOfPieces/BlackQueenIcon.png")));
		pieceIcons.put(Position.W_KNIGHT, new Image(cl.getResourceAsStream("ImagesOfPieces/WhiteKnightIcon.png")));
		pieceIcons.put(Position.W_PAWN, new Image(cl.getResourceAsStream("ImagesOfPieces/WhitePawnIcon.png")));
		pieceIcons.put(Position.B_PAWN, new Image(cl.getResourceAsStream("ImagesOfPieces/BlackPawnIcon.png")));
		pieceIcons.put(Position.B_BISHOP, new Image(cl.getResourceAsStream("ImagesOfPieces/BlackBishopIcon.png")));
		pieceIcons.put(Position.B_ROOK, new Image(cl.getResourceAsStream("ImagesOfPieces/BlackRookIcon.png")));
		pieceIcons.put(Position.W_BISHOP, new Image(cl.getResourceAsStream("ImagesOfPieces/WhiteBishopIcon.png")));
		pieceIcons.put(Position.W_KING, new Image(cl.getResourceAsStream("ImagesOfPieces/WhiteKingIcon.png")));
		pieceIcons.put(Position.W_ROOK, new Image(cl.getResourceAsStream("ImagesOfPieces/WhiteRookIcon.png")));
		pieceIcons.put(Position.B_KNIGHT, new Image(cl.getResourceAsStream("ImagesOfPieces/BlackKnightIcon.png")));
	}
	
	private String title;
	private List<Exercise> exercises;
	
	private GridPane gridPane;

	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		title = "Kann der Springer den Bauern noch stoppen?";
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
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.setGridLinesVisible(true);
		
		Label lblTitle = new Label(title);
		lblTitle.getStyleClass().add("title");
		
		gridPane.add(lblTitle, 0, 0,3,1);
		GridPane.setHalignment(lblTitle, HPos.CENTER);
		
		/*ColumnConstraints [] columnConstraints = new ColumnConstraints[3];
		for(int i=0; i< 3; ++i) {
			columnConstraints[i] = new ColumnConstraints();
			columnConstraints[i].setPercentWidth(1.0/3.0);
		}
		gridPane.getColumnConstraints().addAll(columnConstraints);*/
		
		
		int i = 0;

		for (Exercise exercise : exercises) {

			System.out.println(exercise.piecePlacements);
			
			GridPane pane1 = new GridPane();
			gridPane.add(pane1,  i % 3, i / 3 + 1);

			Label lblNr = new Label();
			lblNr.setText("Aufgabe "+ (i+1) +")");
			lblNr.getStyleClass().add("labelNumber");
			pane1.add(lblNr, 0, 0,2,1);
			
			Position position = Position.fromPiecePlacements(exercise.piecePlacements);
			ChessboardFX chessboard = new ChessboardFX(position);
			chessboard.setColorBlackSquare(Color.color(199/256.0, 199/256.0, 199/256.0));
			
			pane1.add(chessboard, 0, 1,1,2);
			
			NextMoveIndicatorFX nmi = new NextMoveIndicatorFX(position.getNextMove());
			GridPane.setHalignment(nmi, HPos.LEFT);
			GridPane.setValignment(nmi, VPos.TOP);
			pane1.add(nmi, 1, 1);
			
			VBox piecesToAdd = new VBox();
			piecesToAdd.setAlignment(Pos.BOTTOM_LEFT);
	
			for (int piece : exercise.piecesToAdd) {
				piecesToAdd.getChildren().add(new ImageView(pieceIcons.get(piece)));
			}

			GridPane.setValignment(piecesToAdd, VPos.BOTTOM);
			pane1.add(piecesToAdd, 1,2);
			
			/*TextArea taQuestion = new TextArea();
			taQuestion.setPrefRowCount(3);
			taQuestion.setWrapText(true);
			if (exercise.question != null)
				taQuestion.setText(exercise.question);

			pane1.add(new Group(taQuestion), 0,2,2,1);*/

			Label lblQuestion = new Label();
			if(exercise.question!=null)
				lblQuestion.setText(exercise.question);
			
			pane1.add(lblQuestion, 0, 3,2,1);
			
			++i;

		}
		
		
	}
}
