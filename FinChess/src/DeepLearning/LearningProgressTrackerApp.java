package DeepLearning;

import java.io.File;
import java.util.LinkedList;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import Model.PgnGame;

public class LearningProgressTrackerApp implements ILearningProgressPlotter {

	protected Shell shell;
	private Canvas canvas;

	private static class Point {
		public double x;
		public double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	private LinkedList<Point> points = new LinkedList<Point>();

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LearningProgressTrackerApp window = new LearningProgressTrackerApp();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(new GridLayout(1, false));

		Button btnStartTraining = new Button(shell, SWT.NONE);
		btnStartTraining.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent se) {
				NeuralNet net = new NeuralNet(68, 60, 1);
				PgnGameAnalyzer pgnGameAnalyzer = new PgnGameAnalyzer(net);
				pgnGameAnalyzer.setLearningProgressPlotter(LearningProgressTrackerApp.this);
				ConsumerProducerPattern<PgnGame> cpp = new ConsumerProducerPattern<PgnGame>(
						new PgnGameProducer(new File("PgnGames")), pgnGameAnalyzer);
				cpp.produceAndConsume();
			}
		});
		btnStartTraining.setText("Start training");

		canvas = new Canvas(shell, SWT.NULL);
		GridData gd_canvas = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_canvas.heightHint = 520;
		gd_canvas.widthHint = 1020;
		canvas.setLayoutData(gd_canvas);

		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent pe) {
				System.out.println("Repainting!");
				GC gc = pe.gc;
				gc.setForeground(pe.display.getSystemColor(SWT.COLOR_BLACK));

				gc.drawLine(10, 10, 10, canvas.getSize().y - 10);
				gc.drawLine(10, canvas.getSize().y - 10, canvas.getSize().x - 10, canvas.getSize().y - 10);
				Transform transform = new Transform(pe.display);
				transform.translate(10, canvas.getSize().y - 10);
				Transform mirror = new Transform(pe.display);
				mirror.setElements(1, 0, 0, -1, 0, 0);
				transform.multiply(mirror);

				gc.setTransform(transform);
				gc.setBackground(pe.display.getSystemColor(SWT.COLOR_RED));

				Point lastPoint = null;
				for (Point p : points) {
					gc.drawOval((int) p.x / 1000 - 1, (int) p.y * 1000 - 1,	2, 2);
					if (lastPoint == null) {
						lastPoint = p;
						continue;
					}
					gc.drawLine((int) lastPoint.x / 1000, (int) lastPoint.y * 1000, (int) p.x / 1000, (int) p.y * 1000);

				}

			}
		});

		canvas.redraw();

		Random random = new Random();
		for (int i = 0; i < 1000; ++i) {
			plotLearningProgress(i * 10000, random.nextDouble());
		}
		System.out.println("Finished plotting some tests!");

		shell.pack();
	}

	@Override
	public void plotLearningProgress(int numberOfGamesAnalyzed, double ratioOfCorrectEvaluations) {
		points.add(new Point(numberOfGamesAnalyzed, ratioOfCorrectEvaluations));
		canvas.redraw();
	}

}
