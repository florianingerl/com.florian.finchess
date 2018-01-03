package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JTextArea;
import javax.swing.BoxLayout;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import Model.Position;

public class ExerciseSheetGenerator extends JPanel {

	public static HashMap<Integer, ImageIcon> pieceIcons;

	static {
		loadPieceIcons();
	}

	private static void loadPieceIcons() {
		pieceIcons = new HashMap<Integer, ImageIcon>();
		ClassLoader cl = ExerciseSheetGenerator.class.getClassLoader();

		pieceIcons.put(Position.W_QUEEN, new ImageIcon(cl.getResource("ImagesOfPieces/WhiteQueenIcon.png")));
		pieceIcons.put(Position.B_QUEEN, new ImageIcon(cl.getResource("ImagesOfPieces/BlackQueenIcon.png")));
		pieceIcons.put(Position.W_KNIGHT, new ImageIcon(cl.getResource("ImagesOfPieces/WhiteKnightIcon.png")));
		pieceIcons.put(Position.W_PAWN, new ImageIcon(cl.getResource("ImagesOfPieces/WhitePawnIcon.png")));
		pieceIcons.put(Position.B_PAWN, new ImageIcon(cl.getResource("ImagesOfPieces/BlackPawnIcon.png")));
		pieceIcons.put(Position.B_BISHOP, new ImageIcon(cl.getResource("ImagesOfPieces/BlackBishopIcon.png")));
		pieceIcons.put(Position.B_ROOK, new ImageIcon(cl.getResource("ImagesOfPieces/BlackRookIcon.png")));
		pieceIcons.put(Position.W_BISHOP, new ImageIcon(cl.getResource("ImagesOfPieces/WhiteBishopIcon.png")));
		pieceIcons.put(Position.W_KING, new ImageIcon(cl.getResource("ImagesOfPieces/WhiteKingIcon.png")));
	}

	private String title;
	private List<Exercise> exercises;

	private JPanel panel = this;

	public ExerciseSheetGenerator(String title, List<Exercise> exercises) {

		this.title = title;
		this.exercises = exercises;
		buildUI();
	}
	
	private void buildUI() {
		setLayout(new GridBagLayout());
		panel.setBackground(Color.WHITE);

		JLabel lbTitle = new JLabel(title, SwingConstants.CENTER) {
			{
				setFont(new Font("Arial", Font.BOLD, 50));
				setBackground(Color.WHITE);
				Dimension dim = getPreferredSize();
				dim.height = 75;
				setPreferredSize(dim);
			}
		};
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 50, 0);

		panel.add(lbTitle, c);
		int i = 0;

		for (Exercise exercise : exercises) {

			System.out.println(exercise.piecePlacements);

			JPanel panel1 = new JPanel(new GridBagLayout());
			panel1.setBackground(Color.WHITE);
			c = new GridBagConstraints();
			c.gridy = i / 3 + 1;
			c.gridx = i % 3;

			c.insets = new Insets(0, 20, 50, 20);

			panel.add(panel1, c);

			Chessboard chessboard = new Chessboard(Position.fromPiecePlacements(exercise.piecePlacements));
			chessboard.setColorBlackSquare(new Color(199, 199, 199));
			chessboard.setBorder(BorderFactory.createLineBorder(Color.BLACK));

			c = new GridBagConstraints();
			c.fill = GridBagConstraints.NONE;
			c.gridheight = 2;
			panel1.add(chessboard, c);

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

			JLabel lblQuestion = new JLabel() {

				@Override
				public Dimension getPreferredSize() {
					Dimension dim = chessboard.getPreferredSize();
					return new Dimension(dim.width, 50);
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

			panel1.add(lblQuestion, c);

			++i;

		}
		
		Dimension dim = panel.getPreferredSize();
		panel.setSize(dim);
		layoutComponent(panel);
	}
	
	
	private void layoutComponent(Component component) {
		synchronized (component.getTreeLock()) {
			component.doLayout();

			if (component instanceof Container) {
				for (Component child : ((Container) component).getComponents()) {
					layoutComponent(child);
				}
			}
		}
	}

	public void saveAsPng(File file) {
		GraphicsConfiguration gfxConf = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		Dimension dim = panel.getSize();
		BufferedImage image = gfxConf.createCompatibleImage((int) dim.getWidth(), (int) dim.getHeight());
		panel.paint(image.createGraphics());

		try {
			ImageIO.write(image, "png", file);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		System.out.println("Wrote the png file!");
	}
	
	
	public void showInAJFrame() {
		JFrame frame = new JFrame();
		JPanel contentPanel = (JPanel) frame.getContentPane();
		contentPanel.setLayout(new BorderLayout());

		contentPanel.add(panel, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		List<Exercise> exercises = parseExercisesFromStream(
				ExerciseSheetGenerator.class.getClassLoader().getResourceAsStream("SchachmattAusdenken.txt"));
		ExerciseSheetGenerator esg = new ExerciseSheetGenerator("Schachmatt ausdenken", exercises);
		File pngFile = new File("C:/Users/Hermann/Desktop/ExerciseSheet.png");
		esg.saveAsPng(pngFile);
		ImageToPdfConverter.convertImgToPDF(pngFile, new File("C:/Users/Hermann/Desktop/ExerciseSheet.pdf"));
		System.out.println("Finished!");
	}

	private static List<Exercise> parseExercisesFromStream(InputStream stream) {
		List<Exercise> exercises = new LinkedList<Exercise>();

		Scanner scanner = new Scanner(stream);
		Exercise exercise = new Exercise();

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (line.startsWith("pp:")) {
				exercise.piecePlacements = line.substring(3);

			} else if (line.startsWith("q:")) {
				exercise.question = line.substring(2);
			} else if (line.startsWith("pta:")) {
				for (int i = 4; i < line.length(); ++i) {
					int piece = Position.EMPTY;
					switch (line.charAt(i)) {
					case 'Q':
						piece = Position.W_QUEEN;
						break;
					case 'q':
						piece = Position.B_QUEEN;
						break;
					case 'N':
						piece = Position.W_KNIGHT;
						break;
					case 'P':
						piece = Position.W_PAWN;
						break;
					case 'p':
						piece = Position.B_PAWN;
						break;
					case 'b':
						piece = Position.B_BISHOP;
						break;
					case 'r':
						piece = Position.B_ROOK;
						break;
					case 'K':
						piece = Position.W_KING;
						break;
					case 'B':
						piece = Position.W_BISHOP;
						break;
					}
					exercise.piecesToAdd.add(piece);
				}
			} else {
				exercises.add(exercise);
				exercise = new Exercise();
			}
		}

		scanner.close();

		return exercises;
	}

	

}
