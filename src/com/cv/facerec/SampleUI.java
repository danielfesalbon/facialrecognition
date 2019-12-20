/**
 * 
 */
package com.cv.facerec;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * @author Daniel Fesalbon
 *
 */
@SuppressWarnings("serial")
public class SampleUI extends JFrame {

	JLabel labelUI, labelDetails, author;

	public SampleUI(String name) {

		labelUI = new JLabel("Hello, " + name + "!");
		labelDetails = new JLabel("<html>Unfortunately this is the end of the Facial recognition <br>demo.Thank you.</html>");
		author = new JLabel("2019, Daniel Fesalbon");

		labelUI.setBounds(20, 20, 480, 30);
		labelDetails.setBounds(20, 50, 480, 60);
		author.setBounds(20, 110, 480, 30);

		labelDetails.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		labelUI.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 24));
		author.setFont(new Font("Calibri", Font.ITALIC, 12));

		labelDetails.setHorizontalAlignment(SwingConstants.LEFT);
		labelUI.setHorizontalAlignment(SwingConstants.LEFT);
		author.setHorizontalAlignment(SwingConstants.CENTER);

		add(author);
		add(labelUI);
		add(labelDetails);

		setTitle("Facial Recognition");
		setSize(500, 200);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

}
