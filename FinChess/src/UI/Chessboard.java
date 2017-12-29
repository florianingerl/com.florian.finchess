package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JLabel;

import Model.BasicEngine;
import Model.InvalidFenStringException;
import Model.Move;
import Model.Position;

public class Chessboard extends JLabel implements ComponentListener {

	// This position will always be drawn!!!
	protected Position position;
	//float to make calculation faster
	protected float scale = 0.5f;

	protected Color colorBlackSquare = new Color(153, 204, 255);
	protected Color colorWhiteSquare = Color.white;

	protected int colorInFront = 1;
	protected int[][] squares = new int[8][8];
	
	public static Map<Integer, BufferedImage> piecesImages;
	protected Map<Integer, BufferedImage> scaledPiecesImages;

	public Chessboard() {
		setPosition(new Position());
		loadImagesOfPieces();
		this.addComponentListener(this);
	}

	public Chessboard(Position position) {
		setPosition(position);
		loadImagesOfPieces();
		this.addComponentListener(this);
	}

	public Chessboard(String fenString) throws InvalidFenStringException {
		setPosition(Position.fromFenString(fenString));
		loadImagesOfPieces();
		this.addComponentListener(this);
	}
	
	public void setPosition(String fenString) throws InvalidFenStringException{
		position.setPosition(fenString);	
	}
	
	protected void loadImagesOfPieces(){
		if(piecesImages!=null){
			return;
		}
		
		piecesImages = new HashMap<Integer, BufferedImage>(12);
		scaledPiecesImages = new HashMap<Integer, BufferedImage>(12);
		
		
		BufferedImage whitePawn = null;
		BufferedImage blackPawn = null;
		BufferedImage whiteKing = null;
		BufferedImage blackKing = null;
		BufferedImage whiteRook = null;
		BufferedImage blackRook = null;
		BufferedImage whiteQueen = null;
		BufferedImage blackQueen = null;
		BufferedImage whiteKnight = null;
		BufferedImage blackKnight = null;
		BufferedImage whiteBishop = null;
		BufferedImage blackBishop = null;
		
		
		try {
			whitePawn = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("ImagesOfPieces/WhitePawn.png"));
			blackPawn = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("ImagesOfPieces/BlackPawn.png"));
			whiteKing = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("ImagesOfPieces/WhiteKing.png"));
			blackKing = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("ImagesOfPieces/BlackKing.png"));
			whiteKnight = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("ImagesOfPieces/WhiteKnight.png"));
			blackKnight = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("ImagesOfPieces/BlackKnight.png"));
			whiteBishop = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("ImagesOfPieces/WhiteBishop.png"));
			blackBishop = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("ImagesOfPieces/BlackBishop.png"));
			whiteRook = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("ImagesOfPieces/WhiteRock.png"));
			blackRook = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("ImagesOfPieces/BlackRock.png"));
			whiteQueen = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("ImagesOfPieces/WhiteQueen.png"));
			blackQueen = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("ImagesOfPieces/BlackQueen.png"));
			whiteBishop = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("ImagesOfPieces/WhiteBishop.png"));
			blackBishop = ImageIO.read(getClass().getClassLoader()
					.getResourceAsStream("ImagesOfPieces/BlackBishop.png"));

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

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
		
		scaledPiecesImages.put(BasicEngine.W_PAWN, whitePawn);
		scaledPiecesImages.put(BasicEngine.B_PAWN, blackPawn);
		scaledPiecesImages.put(BasicEngine.W_KNIGHT, whiteKnight);
		scaledPiecesImages.put(BasicEngine.B_KNIGHT, blackKnight);
		scaledPiecesImages.put(BasicEngine.W_BISHOP, whiteBishop);
		scaledPiecesImages.put(BasicEngine.B_BISHOP, blackBishop);
		scaledPiecesImages.put(BasicEngine.W_ROOK, whiteRook);
		scaledPiecesImages.put(BasicEngine.B_ROOK, blackRook);
		scaledPiecesImages.put(BasicEngine.W_QUEEN, whiteQueen);
		scaledPiecesImages.put(BasicEngine.B_QUEEN, blackQueen);
		scaledPiecesImages.put(BasicEngine.W_KING, whiteKing);
		scaledPiecesImages.put(BasicEngine.B_KING, blackKing);
		
		
		
	}
	

	public void makeMove(Move move) {
		position.makeMove(move);
		redrawPosition();
		repaint();

	}

	public void unmakeMove(Move move) {
		position.unmakeMove(move);
		redrawPosition();
		repaint();
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
		redrawPosition();
		repaint();
	}

	public Color getColorBlackSquare() {
		return colorBlackSquare;
	}

	public void setColorBlackSquare(Color colorBlackSquare) {
		this.colorBlackSquare = colorBlackSquare;
	}

	public Color getColorWhiteSquare() {
		return colorWhiteSquare;
	}

	public void setColorWhiteSquare(Color colorWhiteSquare) {
		this.colorWhiteSquare = colorWhiteSquare;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
		redrawPosition();
		repaint();
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}
	
	@Override
	public Dimension getMinimumSize(){
		return new Dimension(300,300);
	}

	protected void computeScale() {
		Dimension size = getSize();
		double edgeLength = Math.min(size.getHeight(), size.getWidth());

		// 800 is the default size of the board
		scale = (float) (edgeLength / 800);

	}

	protected void paintBackground(Graphics g) {
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				if ((i + j) % 2 == 0) {
					g.setColor(colorBlackSquare);
				} else {
					g.setColor(colorWhiteSquare);
				}
				
				g.fillRect((int) (j * 100 * scale),
						(int) ((700 - i * 100) * scale), (int) (100 * scale),
						(int) (100 * scale));
			}
		}
	}
	
	
	

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
	
		paintBackground(graphics);

		// fehlt scale
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {

				//Dies k�nnte man auch vorberechnen, jedesmal wenn square[][] changes
				if (squares[i][j] != Position.EMPTY)
					graphics.drawImage(
							scaledPiecesImages.get(squares[i][j]),
							(int)((i*100+50)*scale - this.scaledPiecesImages.get(squares[i][j]).getWidth()/2.0f),
							(int)((700-j*100+50)*scale - scaledPiecesImages.get(squares[i][j]).getHeight()/2.0f), null);
			}
		}

	}

	public int parseSquareToBit(int i, int j) {
		if (colorInFront == 1) {
			return i + 8 * j;
		} else {
			return (7 - i) + 8 * (7 - j);
		}
	}

	public int[] parseBitToSquare(int i) {

		int[] square = new int[2];

		if (colorInFront == 1) {
			square[0] = i % 8;
			square[1] = i / 8;
		} else {
			square[0] = 7 - i % 8;
			square[1] = 7 - i / 8;
		}
		return square;

	}
	
	public void changeColour() {
		Color chosen = JColorChooser.showDialog(this, "Choose another colour", colorBlackSquare);
		if(chosen !=null){
			colorBlackSquare = chosen;
			repaint();
		}
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		
		this.computeScale();
		
		for(int key: piecesImages.keySet()){
			scaledPiecesImages.put(key, ScalingBufferedImage.scaleBufferedImage(piecesImages.get(key), scale));
		}
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		
	}

	@Override
	public void componentShown(ComponentEvent e) {

		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		
	}
	

}
