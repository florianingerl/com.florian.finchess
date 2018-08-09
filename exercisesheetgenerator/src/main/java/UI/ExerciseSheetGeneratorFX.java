package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

import Model.Position;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.control.Label;

import javafx.application.Application;

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
		title = "Matt, Patt oder Schwarz zieht 1...";
		exercises = ExerciseSheetGenerator.parseExercisesFromStream(
				ExerciseSheetGenerator.class.getClassLoader().getResourceAsStream("MattPattOderSchwarzZieht.txt"));
		gridPane = new GridPane();
		
		
		Scene scene = new Scene(gridPane, PDRectangle.A4.getWidth(), PDRectangle.A4.getHeight());
		scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm() );
		buildUI();
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
		
		gridPane.setVgap(10);
		gridPane.setHgap(10);
		gridPane.setGridLinesVisible(true);
		
		Label lblTitle = new Label(title);
		GridPane.setHalignment(lblTitle, HPos.CENTER);
		gridPane.add(lblTitle, 0,0, 3, 1);
		
		int i = 0;

		for (Exercise exercise : exercises) {

			System.out.println(exercise.piecePlacements);

			ChessboardFX chessboard = new ChessboardFX(Position.fromPiecePlacements(exercise.piecePlacements));
			gridPane.add(chessboard,  i % 3, i / 3 + 1);

			
			/*chessboard.setColorBlackSquare(new Color(199, 199, 199));
			chessboard.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			c = new GridBagConstraints();
			c.fill = GridBagConstraints.NONE;
			c.gridheight = 2;
			panel1.add(chessboard, 0,0,1,2);

			NextMoveIndicator nmi = new NextMoveIndicator(chessboard.getPosition().getNextMove());
			c = new GridBagConstraints();
			c.gridx = 1;
			c.anchor = GridBagConstraints.PAGE_START;
			panel1.add(nmi, c);

			JPanel piecesToAdd = new JPanel();
			piecesToAdd.setBackground(Color.WHITE);
			piecesToAdd.setLayout(new BoxLayout(piecesToAdd, BoxLayout.Y_AXIS));

			for (int piece : exercise.piecesToAdd) {
				piecesToAdd.add(new JLabel(pieceIcons.get(piece)) {
					{
						setBackground(Color.WHITE);
					}
				});
			}

			c = new GridBagConstraints();
			c.gridx = 1;
			c.gridy = 1;
			c.anchor = GridBagConstraints.PAGE_END;
			panel1.add(piecesToAdd, c);

			JLabel lblQuestion = new JLabel("<html><ul style=\"list-style-type:circle\"><li style=\"display:inline; float:left\">Matt</li><li style=\"display:inline; float:left\">Patt</li><li style=\"display:inline; float:left\">Schwarz zieht 1...</li></ul></html>") {

				@Override
				public Dimension getPreferredSize() {
					Dimension dim = chessboard.getPreferredSize();
					return new Dimension(dim.width, 70);
				}

				@Override
				public Dimension getMaximumSize() {
					return getPreferredSize();
				}

				@Override
				public Dimension getMinimumSize() {
					return getPreferredSize();
				}
			};

			if (exercise.question != null)
				lblQuestion.setText(exercise.question);

			lblQuestion.setFont(new Font("Arial", Font.PLAIN, 18));
			lblQuestion.setBackground(Color.WHITE);
			Dimension dim = chessboard.getPreferredSize();
			lblQuestion.setPreferredSize(new Dimension(dim.width, 50));

			c = new GridBagConstraints();
			c.gridwidth = 1;
			c.gridy = 2;

			panel1.add(lblQuestion, c);*/

			++i;

		}
		
		
	}
}
