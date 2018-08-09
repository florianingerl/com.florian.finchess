package UI;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;

public class ChessboardFX extends Pane {

	public static final double SIZE = 800.0;
	
	//double scale = 1.0f;

	private final Canvas canvas;
	
	public ChessboardFX(){
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
		gc.fillRect(0,0, getWidth(), getHeight());
		
		double size = Math.min(getWidth(), getHeight() );
		double scale = size/SIZE; 
		gc.save();
		gc.scale(scale, scale);
		
		drawSquares(gc);
		gc.restore();
	}
	
	private void drawSquares(GraphicsContext gc){
		for(int i=0; i < 8; ++i ){
			for(int j=0; j < 8; ++j){
				if((i+j)%2 == 0 ) gc.setFill(Color.WHITE);
				else gc.setFill(Color.BLACK);
				
				gc.fillRect(i*100, j*100, 100, 100 );
			}
		}
	}
	
}
