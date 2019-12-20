/**
 * 
 */
package com.cv.facerec;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imencode;
import static org.bytedeco.opencv.global.opencv_imgproc.rectangle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;

import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_core.Size;
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
public class Setup extends JFrame {

	private Logger logger = Log.getLogger(Setup.class.getName());

	private videoThread myThread = null;
	private boolean capturing = false;
	@SuppressWarnings("unused")
	private int count = 0;
	private VideoCapture webSource = null;
	private Mat frame = new Mat();
	private BytePointer buff = new BytePointer();
	private CascadeClassifier faceDetector = new CascadeClassifier(Defaults.LBPCSCDE_FRNTLFACE_CLASSFR);
	private RectVector face = new RectVector();
	private JPanel jPanel1, jPanel2;

	private JLabel labelUsername, labelPassword, labelName, labelID, author;
	private JTextField txtUsername, txtIDNumber, txtName;
	@SuppressWarnings("unused")
	private JButton buttonCreate, buttonClear, openCamera, pauseCamera, buttonCapture, buttonLogin;
	private JPasswordField txtPassword;

	private Eigenfaces eigen = new Eigenfaces();
	@SuppressWarnings("unused")
	private FaceRecognizer fr;

	public Setup() {
		fr = eigen.getEigenfaces(Main.FR);

		labelUsername = new JLabel("Username");
		labelPassword = new JLabel("Password");
		labelName = new JLabel("Name");
		labelID = new JLabel("Identification");
		jPanel1 = new JPanel();
		jPanel2 = new JPanel();

		author = new JLabel("2019, Daniel Fesalbon");

		txtName = new JTextField() {
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

		txtIDNumber = new JTextField() {
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

		txtUsername = new JTextField() {
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
		txtPassword = new JPasswordField() {
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
		buttonClear = new JButton("Clear") {
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

		buttonCreate = new JButton("Sign up") {
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

		buttonCapture = new JButton("Capture") {
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

		setSize(500, 700);
		author.setForeground(Color.black);

		int y = 380;
		int x = 55;
		labelUsername.setBounds(x + 50, y + 110, 200, 30);
		labelPassword.setBounds(x + 50, y + 150, 200, 30);
		labelName.setBounds(x + 50, y + 70, 200, 30);
		labelID.setBounds(x + 50, y + 30, 200, 30);
		txtName.setBounds(x + 140, y + 70, 170, 30);
		txtIDNumber.setBounds(x + 140, y + 30, 170, 30);
		txtUsername.setBounds(x + 140, y + 110, 170, 30);
		txtPassword.setBounds(x + 140, y + 150, 170, 30);
		buttonLogin.setBounds(x + 50, y + 190, 80, 25);
		buttonCreate.setBounds(x + 230, y + 190, 80, 25);
		buttonCapture.setBounds(210, 10, 80, 25);
		buttonClear.setBounds(x + 140, y + 190, 80, 25);
		author.setBounds(0, 610, 500, 25);
		jPanel1.setBounds(45, 45, 400, 350);
		jPanel2.setBounds(43, 43, 404, 354);
		jPanel2.setBackground(Color.BLACK);

		labelUsername.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		labelPassword.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		labelName.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		labelID.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		buttonClear.setFont(new Font("Century Gothic", Font.BOLD, 12));
		buttonCreate.setFont(new Font("Century Gothic", Font.BOLD, 12));
		buttonCapture.setFont(new Font("Century Gothic", Font.BOLD, 12));
		buttonLogin.setFont(new Font("Century Gothic", Font.BOLD, 12));

		author.setFont(new Font("Calibri", Font.ITALIC, 12));
		author.setHorizontalAlignment(SwingConstants.CENTER);

		buttonClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClearInput();
			}
		});

		buttonCapture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!capturing) {
					capturing = true;
				}
			}
		});

		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeCamera();
				new Login().setVisible(true);
				dispose();
			}
		});

		add(labelUsername);
		add(txtUsername);

		add(labelPassword);
		add(txtPassword);

		add(txtName);
		add(txtIDNumber);
		add(labelID);
		add(labelName);

		add(buttonCapture);

		add(buttonLogin);
		add(buttonClear);
		add(buttonCreate);
		add(author);

		add(jPanel2);
		add(jPanel1);

		openCamera();
		setTitle("Account creation");
		setLayout(null);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

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

	class videoThread implements Runnable {

		protected volatile boolean runnable = false;

		@Override
		public void run() {

			synchronized (this) {
				while (runnable) {
					if (webSource.grab()) {
						try {
							webSource.retrieve(frame);
							Graphics g = jPanel1.getGraphics();
							faceDetector.detectMultiScale(frame, face);
							if (face.size() > 1 || txtName.getText() == null || txtName.getText().isEmpty()) {
								buttonCapture.setEnabled(false);
							} else {
								buttonCapture.setEnabled(true);
							}
							for (int i = 0; i < face.size(); i++) {
								Rect rect = face.get(i);
								Mat compare = new Mat(frame, rect);
								if (capturing) {
									org.bytedeco.opencv.global.opencv_imgproc.resize(compare, compare,
											new Size(160, 160));
									if (FileTools.getFileName() != null) {
										String fname = "" + FileTools.getFileName();
										String name = Defaults.IMAGES + "\\" + FileTools.getFileName() + "." + "jpg";
										if (org.bytedeco.opencv.global.opencv_imgcodecs.imwrite(name, compare)) {
											FileTools.addElement(txtName.getText(), fname + "." + "jpg");
										}
									}
									capturing = false;
								}
								rectangle(frame, rect, Scalar.RED);
							}
							imencode(".bmp", frame, buff);
							Image im = ImageIO.read(new ByteArrayInputStream(buff.getStringBytes()));
							BufferedImage buff = (BufferedImage) im;
							if (buff != null && g != null) {
								if (g.drawImage(buff, 0, 0, getWidth(), getHeight() - 150, 0, 0, buff.getWidth(),
										buff.getHeight(), null)) {
									if (runnable == false) {
										this.wait();
									}
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

	public void openCamera() {
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

	public void ClearInput() {
		txtUsername.setText(null);
		txtName.setText(null);
		txtIDNumber.setText(null);
		txtPassword.setText(null);
	}

	public void Capture(String id, Mat img) {

	}
}
