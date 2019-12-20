/**
 * 
 */
package com.cv.facerec;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;

import org.bytedeco.opencv.opencv_face.FaceRecognizer;

import com.cv.main.Main;
import com.cv.util.Eigenfaces;
import com.cv.util.Log;

/**
 * @author Daniel Fesalbon
 *
 */
@SuppressWarnings("serial")
public class Login extends JFrame {

	@SuppressWarnings("unused")
	private static Logger logger = Log.getLogger(Main.class.getName());

	private JLabel labelLogin, labelUsername, labelPassword, labelCreate, author, labelFR;
	private JTextField txtUsername;
	private JButton buttonLogin, buttonClear;
	private JPasswordField txtPassword;
	private Eigenfaces eigen = new Eigenfaces();
	@SuppressWarnings("unused")
	private FaceRecognizer fr;

	public Login() {
		fr = eigen.getEigenfaces(Main.FR);

		/* ! [Setup] */
		labelLogin = new JLabel("Welcome,");
		labelCreate = new JLabel("<html><u>Setup new account</u><html>");
		labelUsername = new JLabel("Username");
		labelPassword = new JLabel("Password");
		author = new JLabel("2019, Daniel Fesalbon");
		labelFR = new JLabel("<html><u>Use facial recognition</u><html>");

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

		buttonClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		labelLogin.setForeground(Color.black);
		labelCreate.setForeground(Color.black);
		author.setForeground(Color.black);
		labelFR.setForeground(Color.black);

		int y = 10;
		labelLogin.setBounds(50, y + 70, 400, 30);
		labelUsername.setBounds(50, y + 110, 200, 30);
		labelPassword.setBounds(50, y + 150, 200, 30);

		txtUsername.setBounds(140, y + 110, 170, 30);
		txtPassword.setBounds(140, y + 150, 170, 30);

		labelCreate.setBounds(140, y + 215, 120, 30);
		labelFR.setBounds(140, y + 235, 250, 30);
		buttonLogin.setBounds(230, y + 190, 80, 25);
		buttonClear.setBounds(140, y + 190, 80, 25);
		author.setBounds(0, y + 400, 400, 25);

		labelUsername.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		labelPassword.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		labelCreate.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		labelCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		labelFR.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		labelFR.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		labelLogin.setFont(new Font("Century Gothic", Font.BOLD, 25));
		buttonLogin.setFont(new Font("Century Gothic", Font.BOLD, 12));
		buttonClear.setFont(new Font("Century Gothic", Font.BOLD, 12));
		author.setFont(new Font("Calibri", Font.ITALIC, 12));
		author.setHorizontalAlignment(SwingConstants.CENTER);

		/* ! [Components] */
		add(labelCreate);
		add(labelLogin);
		add(labelUsername);
		add(txtUsername);
		add(labelPassword);
		add(txtPassword);
		add(buttonLogin);
		add(buttonClear);
		add(author);
		add(labelFR);

		/* ! [Title] */
		setTitle("Login");
		/* ! [Frame Area] */
		setSize(400, 500);
		/* ! [Cannot be resized] */
		setResizable(false);
		/* ! [Center on screen] */
		setLocationRelativeTo(null);
		/* ! [No Layout] */
		setLayout(null);
		/* ! [Termination] */
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		labelFR.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new FaceLogin().setVisible(true);
				dispose();
			}
		});

		labelCreate.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new Setup().setVisible(true);
				dispose();
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
}
