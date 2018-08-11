package UI;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class NextMoveIndicatorFX extends Pane {
	private static final int SIZE = 20;
	
	private int nextMove;
	
	private final Canvas canvas;

	public NextMoveIndicatorFX(int nextMove) {
		this.nextMove = nextMove;
		setPrefWidth(SIZE);
		setPrefHeight(SIZE);
		setWidth(SIZE);
		setHeight(SIZE);
		
		canvas = new Canvas(SIZE, SIZE);
		getChildren().add(canvas);
		widthProperty().addListener(e -> canvas.setWidth(getWidth()));
		heightProperty().addListener(e -> canvas.setHeight(getHeight()));
	}

	@Override
	protected void layoutChildren() {
		super.layoutChildren();

		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0,0,getWidth(), getHeight());
		double edgeLength = Math.min(getWidth(), getHeight());
		
		if(nextMove == 1)
			gc.setFill(Color.WHITE);
		else
			gc.setFill(Color.BLACK);
			
		gc.fillOval(0,0, edgeLength, edgeLength);
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(2);
		gc.strokeOval(0,0,edgeLength, edgeLength);
	}
	
}
