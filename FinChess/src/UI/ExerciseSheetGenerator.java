package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;

import Model.InvalidFenStringException;

public class ExerciseSheetGenerator extends JFrame {

	
	
	private String title;
	
	private List<String> exercises = new LinkedList<String>();
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addExercise(String fenString) {
		exercises.add(fenString);
	}
	
	public void saveAsPdf(File file) {
		JPanel panel = new JPanel(new GridBagLayout() );
		panel.setBackground(Color.WHITE);
		
		JLabel lbTitle = new JLabel(title, SwingConstants.CENTER) {
			{ 
				setFont(new Font("Arial", Font.BOLD, 24) );
				setBackground(Color.WHITE);
			}
		};
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 50, 0);
		
		panel.add(lbTitle, c);
		int i = 0;
		
		for(String fenString : exercises) {
			try {
				Chessboard chessboard = new Chessboard(fenString);
				c = new GridBagConstraints();
				c.gridy = i/3 + 1;
				c.gridx = i%3;
				c.insets = new Insets(0, 20, 50, 20);
				
				panel.add( chessboard, c );
				++i;
				
			} catch (InvalidFenStringException e) {
				
				e.printStackTrace();
				System.exit(1);
			}
		}
		
		JPanel contentPanel  = (JPanel) getContentPane();
		contentPanel.setLayout(new BorderLayout() );
		
		contentPanel.add(panel, BorderLayout.CENTER);
		
		JButton btnSaveAsPng = new JButton("Save as png");
		btnSaveAsPng.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GraphicsConfiguration gfxConf = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
						.getDefaultConfiguration();
				BufferedImage image = gfxConf.createCompatibleImage(panel.getWidth(), panel.getHeight());
				
				//panel.paintComponent(image.createGraphics());
				
				try {
					ImageIO.write(image, "png", file );
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
			
		});
		contentPanel.add(btnSaveAsPng, BorderLayout.NORTH);
	
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pack();
		setVisible(true);
		
		
	}
	

	public static void main(String [] args) {
		ExerciseSheetGenerator esg = new ExerciseSheetGenerator();
		
		esg.setTitle("Schachmatt in einem Zug");
		
		for(int i=0; i < 6; ++i) {
			esg.addExercise("4rrk1/pp1q2bn/3p2p1/2pP1p2/PnP4B/2NB1Q1P/1P4P1/R4RK1 b - - 0 1");
		}
		
		esg.saveAsPdf(new File("C:/Users/Hermann/Desktop/ExerciseSheet.png") );
		System.out.println("Finished!");
		
		
	}
	
}
