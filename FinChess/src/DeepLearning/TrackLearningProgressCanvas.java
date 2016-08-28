package DeepLearning;

import java.util.LinkedList;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TrackLearningProgressCanvas extends Canvas implements ILearningProgressPlotter {

	private List<Point2D> xyValues = new LinkedList<Point2D>();

	private GraphicsContext gc = getGraphicsContext2D();

	public TrackLearningProgressCanvas() {
		super(1010, 1000);

		gc.setStroke(Color.BLACK);
		gc.setFill(Color.RED);
		gc.setFont(new Font(20));

		gc.fillText("Correct evaluations in %", 1, 25);
		gc.fillText("Number of analysed games", getWidth() - 200, getHeight());

		gc.strokeLine(10, getHeight() - 10, 10, 10);
		gc.strokeLine(10, getHeight() - 10, getWidth() - 10, getHeight() - 10);

		gc.translate(10, 10);
	}

	public void addXYValue(Point2D xyValue) {
		setWidth(Math.max(getWidth(), 10.0 + xyValue.getX() / 1000.0));

		gc.fillOval(xyValue.getX() / 1000 - 1, getHeight() - xyValue.getY() * 1000 - 1, 1, 1);

		if (!xyValues.isEmpty()) {
			Point2D previousPoint = xyValues.get(xyValues.size() - 1);
			gc.strokeLine(previousPoint.getX() / 1000, getHeight() - previousPoint.getY() * 1000, xyValue.getX() / 1000,
					getHeight() - xyValue.getY() * 1000);
		}

		xyValues.add(xyValue);
	}

	@Override
	public void plotLearningProgress(int numberOfGamesAnalyzed, double ratioOfCorrectEvaluations) {

		addXYValue(new Point2D(numberOfGamesAnalyzed, ratioOfCorrectEvaluations));
	}

}
