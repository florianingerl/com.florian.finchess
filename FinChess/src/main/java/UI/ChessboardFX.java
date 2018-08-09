package UI;

import java.util.HashMap;
import java.util.Map;

import Model.BasicEngine;
import Model.Position;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ChessboardFX extends Pane {

	public static final double SIZE = 800.0;

	public static Map<Integer, Image> piecesImages;

	static {
		loadImagesOfPieces();
	}

	private static void loadImagesOfPieces() {
		piecesImages = new HashMap<Integer, Image>();
		Image whitePawn = new Image(
				ChessboardFX.class.getClassLoader().getResourceAsStream("ImagesOfPieces/WhitePawn.png"));
		Image blackPawn = new Image(
				ChessboardFX.class.getClassLoader().getResourceAsStream("ImagesOfPieces/BlackPawn.png"));
		Image whiteKing = new Image(
				ChessboardFX.class.getClassLoader().getResourceAsStream("ImagesOfPieces/WhiteKing.png"));
		Image blackKing = new Image(
				ChessboardFX.class.getClassLoader().getResourceAsStream("ImagesOfPieces/BlackKing.png"));
		Image whiteKnight = new Image(
				ChessboardFX.class.getClassLoader().getResourceAsStream("ImagesOfPieces/WhiteKnight.png"));
		Image blackKnight = new Image(
				ChessboardFX.class.getClassLoader().getResourceAsStream("ImagesOfPieces/BlackKnight.png"));
		Image whiteBishop = new Image(
				ChessboardFX.class.getClassLoader().getResourceAsStream("ImagesOfPieces/WhiteBishop.png"));
		Image blackBishop = new Image(
				ChessboardFX.class.getClassLoader().getResourceAsStream("ImagesOfPieces/BlackBishop.png"));
		Image whiteRook = new Image(
				ChessboardFX.class.getClassLoader().getResourceAsStream("ImagesOfPieces/WhiteRock.png"));
		Image blackRook = new Image(
				ChessboardFX.class.getClassLoader().getResourceAsStream("ImagesOfPieces/BlackRock.png"));
		Image whiteQueen = new Image(
				ChessboardFX.class.getClassLoader().getResourceAsStream("ImagesOfPieces/WhiteQueen.png"));
		Image blackQueen = new Image(
				ChessboardFX.class.getClassLoader().getResourceAsStream("ImagesOfPieces/BlackQueen.png"));

		piecesImages.put(BasicEngine.W_PAWN, whitePawn);
		piecesImages.put(BasicEngine.B_PAWN, blackPawn);
		piecesImages.put(BasicEngine.W_KNIGHT, whiteKnight);
		piecesImages.put(BasicEngine.B_KNIGHT, blackKnight);
		piecesImages.put(BasicEngine.W_BISHOP, whiteBishop);
		piecesImages.put(BasicEngine.B_BISHOP, blackBishop);
		piecesImages.put(BasicEngine.W_ROOK, whiteRook);
		piecesImages.put(BasicEngine.B_ROOK, blackRook);
		piecesImages.put(BasicEngine.W_QUEEN, whiteQueen);
		piecesImages.put(BasicEngine.B_QUEEN, blackQueen);
		piecesImages.put(BasicEngine.W_KING, whiteKing);
		piecesImages.put(BasicEngine.B_KING, blackKing);
	}

	protected Position position;
	
	private final Canvas canvas;
	
	private int colorInFront = 1;
	protected int[][] squares = new int[8][8];
	
	protected Color colorBlackSquare = Color.color(153/256.0, 204/256.0, 255/256.0);
	protected Color colorWhiteSquare = Color.WHITE;
	
	public ChessboardFX(Position position) {
		this.position =position;
		redrawPosition();
		canvas = new Canvas(SIZE, SIZE);
		getChildren().add(canvas);
		widthProperty().addListener(e -> canvas.setWidth(getWidth()));
		heightProperty().addListener(e -> canvas.setHeight(getHeight()));
	}

	public ChessboardFX() {
		this(new Position());
	}
	
	@Override
	protected void layoutChildren() {
		super.layoutChildren();

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, getWidth(), getHeight());

		double size = Math.min(getWidth(), getHeight());
		double scale = size / SIZE;
		gc.save();
		gc.scale(scale, scale);

		drawSquares(gc);
		drawPieces(gc);
		gc.restore();
	}

	private void drawSquares(GraphicsContext gc) {
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				if ((i + j) % 2 == 0)
					gc.setFill(colorWhiteSquare);
				else
					gc.setFill(colorBlackSquare);

				gc.fillRect(i * 100, j * 100, 100, 100);
			}
		}
	}

	private void drawPieces(GraphicsContext gc) {
		Image image = null;
		for(int i=0; i < 8; ++i) {
			for(int j=0; j < 8;++j) {
				if(squares[i][j]!=Position.EMPTY) {
					image = piecesImages.get(squares[i][j] );
					gc.drawImage( image , i*100+50 - image.getWidth()/2.0 , 700-j*100+50 - image.getHeight()/2);
				}
			}
		}
	}
	
	public void redrawPosition() {

		int column;
		int row;

		int[] square = position.getSquare();

		for (int k = 0; k < 64; k++) {
			
			if (colorInFront == 1) {
				column = k % 8;
				row = k / 8;
			} else {
				column = 7 - k % 8;
				row = 7 - k / 8;
			}

			squares[column][row] = square[k];

		}

	}
	
	public void flipBoard() {
		colorInFront *= (-1);
	}

}
