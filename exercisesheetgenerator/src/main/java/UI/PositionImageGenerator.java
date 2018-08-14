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

import javax.imageio.ImageIO;

import Model.Position;
import javafx.scene.image.Image;

public class PositionImageGenerator {

	private Position position;
	
	private int colorInFront = 1;
	private int [][] squares = new int[8][8];
	
	private Color colorBlackSquare = new Color(199, 199, 199);
	private Color colorWhiteSquare = Color.white;
	
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
}
