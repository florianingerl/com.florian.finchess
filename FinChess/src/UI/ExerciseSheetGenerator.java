package UI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Model.Position;

public class ExerciseSheetGenerator extends JFrame {

	public ExerciseSheetGenerator(String title, List<String> exercises) {

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
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

		for (String piecePlacements : exercises) {

			System.out.println(piecePlacements);
			Chessboard chessboard = new Chessboard(Position.fromPiecePlacements(piecePlacements));
			chessboard.setColorBlackSquare(new Color(199, 199, 199));
			chessboard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			c = new GridBagConstraints();
			c.gridy = i / 3 + 1;
			c.gridx = i % 3;
			c.insets = new Insets(0, 20, 50, 20);

			panel.add(chessboard, c);
			++i;

		}
		
		GraphicsConfiguration gfxConf = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getDefaultConfiguration();
		Dimension dim = panel.getPreferredSize();
		panel.setSize(dim);
		BufferedImage image = gfxConf.createCompatibleImage((int) dim.getWidth(), (int) dim.getHeight());

		layoutComponent(panel);
		panel.paint(image.createGraphics());

		try {
			ImageIO.write(image, "png", new File("C:/Users/Hermann/Desktop/ExerciseSheet.png"));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		System.out.println("Wrote the file!");

		JPanel contentPanel = (JPanel) getContentPane();
		contentPanel.setLayout(new BorderLayout());

		contentPanel.add(panel, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pack();
		setVisible(true);

	}

	public static void main(String[] args) {
		List<String> exercises = new LinkedList<String>();
		
		exercises.add("wQc5Be7ka4");
		exercises.add("wBa6ka8Qd7");
		exercises.add("wNd5Qg7ke8");
		exercises.add("wPh6Qa7kh8");
		exercises.add("wPa7Nd7ka8bb8Bg4");
		exercises.add("wkf8pf7Ne5Qd5");
		exercises.add("wRb1Qb2ka8nc5");
		exercises.add("wRe7f7kh8pg7h7bd5");
		exercises.add("wka5Qb7Pc4");
		exercises.add("wKf7kh8Pg6ph7");
		exercises.add("wNe7Qb1kh5ph4");
		exercises.add("wbg8kh8ph7Pg6Nf5");
		

		ExerciseSheetGenerator esg = new ExerciseSheetGenerator("Schachmatt in einem Zug", exercises);

		System.out.println("Finished!");

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

}
