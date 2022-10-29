import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * @author Andriy Pavlovych
 * The class is meant to illustrate image-processing algorithms applied from a separate class
 * Java Swing is used to implement the GUI of the application
 * Limitations: image sizes are limited by computer screen resolution (no scaling is implemented) 
 */
@SuppressWarnings("serial")
public class GraphicsApp extends JFrame implements ActionListener{
	private String fileName;
	private BufferedImage image;
	private JButton blurButton, edgesButton;
	private JLabel sourceImageLabel, resultImageLabel;
	private JTextField sigmaField;
	private JPanel sourcePanel, middlePanel, resultPanel, blurPanel, edgeDetPanel;

	/**
	 * @param fileName name of the image file to process
	 * loads the image with the filename provided
	 */
	public GraphicsApp(String fileName) {
		this.fileName = fileName;
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) { }
		initUI();
	}

	private void initUI() {
		sourceImageLabel = new JLabel(new ImageIcon(fileName));
		resultImageLabel = new JLabel(new ImageIcon(image));

		sigmaField = new JTextField(4);
		sigmaField.setText("1.0");
		blurButton = new JButton("Blur");
		blurButton.setPreferredSize(new Dimension(84,24));
		blurButton.addActionListener(this);
		edgesButton = new JButton("Edges");
		edgesButton.setPreferredSize(new Dimension(84,24));
		edgesButton.addActionListener(this);

		getContentPane().setLayout(new BorderLayout());
		sourcePanel = new JPanel();
		middlePanel = new JPanel();
		resultPanel = new JPanel();       
		sourcePanel.setLayout(new BoxLayout(sourcePanel, BoxLayout.Y_AXIS));
		sourcePanel.add (new JLabel("Source"));
		sourcePanel.add (sourceImageLabel);
		add(sourcePanel,BorderLayout.WEST);

		middlePanel.setLayout(new BorderLayout(10,10));

		blurPanel = new JPanel();
		blurPanel.setLayout(new FlowLayout(FlowLayout.TRAILING,5,5));
		blurPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		blurPanel.add(new JLabel("Sigma "));
		blurPanel.add(sigmaField);
		blurPanel.add(blurButton);

		edgeDetPanel = new JPanel();
		edgeDetPanel.setLayout(new FlowLayout(FlowLayout.TRAILING,5,5));
		edgeDetPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		edgeDetPanel.add(edgesButton);
		middlePanel.add(new JLabel(" "), BorderLayout.NORTH);
		middlePanel.add(blurPanel, BorderLayout.CENTER);
		middlePanel.add(edgeDetPanel, BorderLayout.SOUTH);
		add(middlePanel,BorderLayout.CENTER);

		resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
		resultPanel.add (new JLabel("Result"));
		resultPanel.add (resultImageLabel);
		add(resultPanel,BorderLayout.EAST);

		pack();
		setTitle("ImageFilter");
		setLocationRelativeTo(null); //place in the centre of the screen
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		//TODO make it possible to load the file from the command-line parameter 
		String fileName = args[0];
		System.out.print(fileName);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == blurButton){
			int []rgbData = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			ImageFilter.blur(rgbData, image.getWidth(), Double.parseDouble(sigmaField.getText()));
			image.setRGB(0, 0, image.getWidth(), image.getHeight(), rgbData, 0, image.getWidth());
			resultImageLabel.setIcon(new ImageIcon(image));

		}
		else 
			if (e.getSource() == edgesButton){
				int []rgbData = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
				ImageFilter.edgeDetection(rgbData, WIDTH);
				image.setRGB(0, 0, image.getWidth(), image.getHeight(), rgbData, 0, image.getWidth());
				resultImageLabel.setIcon(new ImageIcon(image));
			}
	}


}
