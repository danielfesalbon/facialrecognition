/**
 * 
 */
package com.cv.facerec;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imencode;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

import com.cv.main.Defaults;
import com.cv.main.Main;
import com.cv.util.Eigenfaces;
import com.cv.util.FileTools;
import com.cv.util.Log;

/**
 * @author Daniel Fesalbon
 *
 */
@SuppressWarnings("serial")
public class FaceLogin extends JFrame {
	private Logger logger = Log.getLogger(FaceLogin.class.getName());

	private videoThread myThread = null;
	@SuppressWarnings("unused")
	private int count = 0;
	private VideoCapture webSource = null;
	private Mat frame = new Mat();
	private CascadeClassifier faceDetector = new CascadeClassifier(Defaults.LBPCSCDE_FRNTLFACE_CLASSFR);
	private RectVector face = new RectVector();

	private JButton buttonCancel, buttonLogin;
	private JLabel labelName, labelDate, labelManual, labelLogin, author;
	private JPanel panelVision, panelBackground;
	private BytePointer buff = new BytePointer();
	private int j = 0;
	private String classified = "";

	private Eigenfaces eigen = new Eigenfaces();
	private FaceRecognizer fr;
	private boolean loggingin = false;

	class videoThread implements Runnable {

		protected volatile boolean runnable = false;

		@Override
		public void run() {
			synchronized (this) {
				while (runnable) {
					labelDate.setText(new SimpleDateFormat("MMMM dd, YYYY, KK:mm:ss a").format(new Date()));
					if (webSource.grab()) {
						try {
							webSource.retrieve(frame);
							Graphics g = panelVision.getGraphics();
							faceDetector.detectMultiScale(frame, face);
							for (int i = 0; i < face.size(); i++) {
								Rect rect = face.get(i);
								Mat compare = new Mat(frame, rect);
								org.bytedeco.opencv.global.opencv_imgproc.resize(compare, compare, new Size(160, 160));
								rectangle(frame, rect, Scalar.RED);
								if (face.size() == 1) {
									int x = eigen.predict(compare, fr);
									if (j < 5) {
										labelName.setText(FileTools.getName(Integer.toString(x) + ".jpg"));
									}
									if (loggingin) {
										closeCamera();
										loggingin = false;
										new SampleUI(labelName.getText()).setVisible(true);
										dispose();
									}
								}
							}
							if (j < 5) {
								/* ! [computer confirming the face] */
								if (classified.equals(labelName.getText()) && labelName.getText() != "") {
									j++;
								} else {
									j = 0;
									classified = labelName.getText();
								}
							}
							if (j > 4) {
								/* ! [here is where the computer recognized the face already] */
								/* ! [execute next process when computer recognized the person logging in] */
								labelLogin.setText("Log in as " + classified + "?");
								buttonLogin.setVisible(true);
								buttonCancel.setVisible(true);
								labelLogin.setVisible(true);
							} else {
								buttonLogin.setVisible(false);
								buttonCancel.setVisible(false);
								labelLogin.setVisible(false);
							}
							imencode(".bmp", frame, buff);
							Image im = ImageIO.read(new ByteArrayInputStream(buff.getStringBytes()));
							BufferedImage buff = (BufferedImage) im;
							if (g.drawImage(buff, 0, 0, getWidth(), getHeight() - 150, 0, 0, buff.getWidth(),
									buff.getHeight(), null)) {
								if (runnable == false) {
									this.wait();
								}
							}
						} catch (Exception ex) {
							logger.warning(ex.getMessage());
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}

	public FaceLogin() {
		fr = eigen.getEigenfaces(Main.FR);
		initComponents();
	}

	private void initComponents() {

		labelLogin = new JLabel("");
		labelName = new JLabel("");
		labelLogin = new JLabel("Log in as");
		labelManual = new JLabel("<html><u>Manual Login</u><html>");
		labelDate = new JLabel(new SimpleDateFormat("MMMM dd, YYYY, KK:mm:ss a").format(new Date()));
		panelBackground = new JPanel();
		panelVision = new JPanel();
		author = new JLabel("2019, Daniel Fesalbon");
		buttonCancel = new JButton("Cancel") {
			@Override
			protected void paintComponent(Graphics g) {
				if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setPaint(getBackground());
					g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(0, 0, getWidth() - 1, getHeight() - 1));
					g2.dispose();
				}
				super.paintComponent(g);
			}

			@Override
			public void updateUI() {
				super.updateUI();
				setOpaque(false);
				setBorder(new RoundedCornerBorder());
			}
		};

		buttonLogin = new JButton("Login") {
			@Override
			protected void paintComponent(Graphics g) {
				if (!isOpaque() && getBorder() instanceof RoundedCornerBorder) {
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setPaint(getBackground());
					g2.fill(((RoundedCornerBorder) getBorder()).getBorderShape(0, 0, getWidth() - 1, getHeight() - 1));
					g2.dispose();
				}
				super.paintComponent(g);
			}

			@Override
			public void updateUI() {
				super.updateUI();
				setOpaque(false);
				setBorder(new RoundedCornerBorder());
			}
		};

		int x = 0, y = 0;

		labelName.setBounds(x + 20, y + 515, 450, 30);
		labelDate.setBounds(x + 20, y + 20, 450, 30);
		labelLogin.setBounds(x + 20, y + 550, 450, 30);
		panelBackground.setBounds(x + 20, y + 50, 450, 450);
		panelVision.setBounds(x + 22, y + 52, panelBackground.getWidth() - 4, panelBackground.getHeight() - 4);
		buttonCancel.setBounds(x + 300, y + 575, 80, 25);
		buttonLogin.setBounds(x + 390, y + 575, 80, 25);
		labelManual.setBounds(x + 20, y + 600, 450, 30);
		author.setBounds(0, y + 610, 500, 30);

		labelName.setFont(new Font("Century Gothic", Font.BOLD, 24));
		labelDate.setFont(new Font("Century Gothic", Font.BOLD, 12));
		labelManual.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		buttonCancel.setFont(new Font("Century Gothic", Font.BOLD, 12));
		buttonLogin.setFont(new Font("Century Gothic", Font.BOLD, 12));
		panelBackground.setBackground(Color.BLACK);
		labelManual.setForeground(Color.BLACK);
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		labelDate.setHorizontalAlignment(SwingConstants.CENTER);
		labelLogin.setHorizontalAlignment(SwingConstants.RIGHT);
		labelManual.setHorizontalAlignment(SwingConstants.RIGHT);
		labelLogin.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		labelManual.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		add(labelManual);
		add(labelName);
		add(labelDate);
		add(buttonCancel);
		add(buttonLogin);
		add(panelBackground);
		add(panelVision);
		add(labelLogin);
		add(author);

		author.setForeground(Color.BLACK);
		author.setFont(new Font("Calibri", Font.ITALIC, 12));
		author.setHorizontalAlignment(SwingConstants.CENTER);

		labelLogin.setVisible(false);
		buttonCancel.setVisible(false);
		buttonLogin.setVisible(false);

		openCamera();
		setTitle("Facial Recognition");
		setSize(500, 700);
		setLayout(null);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		labelLogin.setVisible(false);
		buttonCancel.setVisible(false);
		buttonLogin.setVisible(false);

		labelManual.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				closeCamera();
				new Login().setVisible(true);
				dispose();
			}
		});

		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loggingin = true;
			}
		});

		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				j = 0;

			}
		});
	}

	class RoundedCornerBorder extends AbstractBorder {
		private final Color ALPHA_ZERO = new Color(0x0, true);

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Shape border = getBorderShape(x, y, width - 1, height - 1);
			g2.setPaint(ALPHA_ZERO);
			Area corner = new Area(new Rectangle2D.Double(x, y, width, height));
			corner.subtract(new Area(border));
			g2.fill(corner);
			g2.setPaint(Color.GRAY);
			g2.draw(border);
			g2.dispose();
		}

		public Shape getBorderShape(int x, int y, int w, int h) {
			int r = 10;
			return new RoundRectangle2D.Double(x, y, w, h, r, r);
		}

		@Override
		public Insets getBorderInsets(Component c) {
			return new Insets(4, 8, 4, 8);
		}

		@Override
		public Insets getBorderInsets(Component c, Insets insets) {
			insets.set(4, 8, 4, 8);
			return insets;
		}
	}

	public void openCamera() {
		labelLogin.setVisible(false);
		buttonCancel.setVisible(false);
		buttonLogin.setVisible(false);
		webSource = new VideoCapture(0); /* ! [Start camera] */
		myThread = new videoThread(); /* ! [New Thread] */
		Thread t = new Thread(myThread);
		t.setDaemon(true);
		myThread.runnable = true;
		t.start(); /* ! [Start thread] */
	}

	public void closeCamera() {
		myThread.runnable = false; /* ! [Stop thread] */
		webSource.release(); /* ! [Stop capturing] */
	}
}
