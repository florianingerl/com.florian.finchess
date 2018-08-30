package UI;

import java.io.File;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import Model.Position;
import Model.SquareRepresentationConverter;
import javafx.scene.image.Image;

public class PositionImageGenerator {

	private Position position;
	
	private int colorInFront = 1;
	private int [][] squares = new int[8][8];
	
	private Color colorBlackSquare = new Color(199, 199, 199);
	private Color colorWhiteSquare = Color.white;
	
	private List<Arrow> arrows = new LinkedList<Arrow>();
	
	public PositionImageGenerator(Position position) {
		this.setPosition(position);
	}
	
	
	public void setPosition(Position position) {
		this.position = position;
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
	
	public void addArrow(Arrow arrow) {
		arrows.add(arrow);
		
		
		
	}
	
	private void drawArrows(Graphics2D g) {
		for(Arrow arrow : arrows) {
			drawArrow(g, arrow);
		}
	}
	
	private void drawArrow(Graphics2D g, Arrow arrow) {
		int f = SquareRepresentationConverter.getBitFromString(arrow.from);
		int t = SquareRepresentationConverter.getBitFromString(arrow.to);
		
		int fromColumn = f % 8;
		int fromRow = f / 8;
		
		int toColumn = t % 8;
		int toRow = t / 8;
		
		int fromPosX = fromColumn * 100 + 50;
		int fromPosY = 700 - fromRow * 100 + 50;
		
		int toPosX = toColumn * 100 + 50;
		int toPosY = 700 - toRow * 100 + 50;
		
		double dirX = toPosX - fromPosX;
		double dirY = toPosY - fromPosY;
		
		double s = Math.sqrt( dirX * dirX + dirY * dirY );
		dirX = 1/s * dirX;
		dirY = 1/s * dirY;
		
		int [] xPoints = new int [3];
		int [] yPoints = new int [3];
		
		xPoints[0] = toPosX;
		yPoints[0] = toPosY;
		
		toPosX = (int) (toPosX - 30 * dirX);
		toPosY = (int) (toPosY - 30 * dirY);
		
		g.setStroke(new BasicStroke(10));
		if(arrow.color != null)
			g.setColor(arrow.color);
		else
			g.setColor(Color.YELLOW);
		g.drawLine(fromPosX, fromPosY, toPosX, toPosY);
		
		xPoints[1] = (int) (toPosX + 30 * dirY);
		yPoints[1] = (int) (toPosY - 30 * dirX);
		
		xPoints[2] = (int) (toPosX - 30 * dirY);
		yPoints[2] = (int) (toPosY + 30 * dirX);
		g.fillPolygon(xPoints, yPoints, 3);
		
	}



	public void createImageFile(File pngFile) {
		  int width = 900;
		  int height = 800;
		  BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

	      Graphics2D ig2 = bi.createGraphics();

	      drawChessboard(ig2);
	      try {
			ImageIO.write(bi, "PNG", pngFile );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void drawChessboard(Graphics2D g) {
		drawSquares(g);
		drawPieces(g);
		drawArrows(g);
		drawMoveIndicator(g);
	}
	
	private void drawSquares(Graphics2D g) {
		for(int i=0; i < 8; ++i) {
			for(int j=0; j < 8; ++j) {
				if((i+j)%2==0)
					g.setColor(colorBlackSquare);
				else
					g.setColor(colorWhiteSquare);
				g.fillRect(j * 100 ,
						700 - i * 100 ,100,100);
			}
		}
	}
	
	private void drawPieces(Graphics2D g) {
		BufferedImage image = null;
		for(int i=0; i < 8; ++i) {
			for(int j=0; j < 8;++j) {
				if(squares[i][j]!=Position.EMPTY) {
					image = Chessboard.piecesImages.get(squares[i][j] );
					g.drawImage( image , (int) (i*100+50 - image.getWidth()/2.0) , (int) (700-j*100+50 - image.getHeight()/2.0), null);
				}
			}
		}
	}
	
	private void drawMoveIndicator(Graphics2D g) {
		
		if (position.getNextMove() == 1) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.BLACK);
		}

		g.fillOval(850 - 30, 50 - 30, 60, 60 );
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(2));
		g.drawOval(850 - 30, 50 - 30, 60, 60 );
	}
	
	public static void main(String [] args) {
		Position pos = Position.fromPiecePlacements("wKa8Na4ke2qb1");
		PositionImageGenerator pig = new PositionImageGenerator(pos);
		pig.addArrow(new Arrow("a4","c3", Color.GREEN) );
		pig.addArrow(new Arrow("c3", "b1", Color.BLUE));
		pig.addArrow(new Arrow("c3", "e2", Color.BLUE));
		pig.createImageFile(new File("C:\\GitChess\\SchulschachSpringerGegenBauern\\images\\Image5.png"));
		System.out.println("Finished!");
	}
}
