package com.andedit.mapcolor;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class AppFrame extends JFrame {

	private static final long serialVersionUID = -3837044151708182897L;

	private final JPanel contentPane;
	private final ImagePreview cTexture;
	private final JTextField tfColorName;
	private final JTextField tfColorId;
	private final JPanel pColor;
	private final JLabel lColorHex;

	/**
	 * Create the frame.
	 */
	public AppFrame() {
		setResizable(false);
		setTitle("MapColorMC");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(320, 270);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 238, 238));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		cTexture = new ImagePreview(this::loadTexture);
		cTexture.setBounds(10, 12, 128, 128);
		contentPane.add(cTexture);

		pColor = new JPanel();
		pColor.setBackground(Color.WHITE);
		pColor.setBounds(150, 12, 142, 70);
		contentPane.add(pColor);
		pColor.setLayout(new GridLayout(1, 0, 0, 0));

		lColorHex = new JLabel("Color Hex");
		lColorHex.setFont(new Font("Dialog", Font.BOLD, 18));
		lColorHex.setHorizontalAlignment(SwingConstants.CENTER);
		pColor.add(lColorHex);

		tfColorName = new JTextField();
		tfColorName.setBackground(Color.WHITE);
		tfColorName.setEditable(false);
		tfColorName.setText("Color Name");
		tfColorName.setBounds(150, 89, 142, 23);
		contentPane.add(tfColorName);
		tfColorName.setColumns(10);

		tfColorId = new JTextField();
		tfColorId.setBackground(Color.WHITE);
		tfColorId.setEditable(false);
		tfColorId.setText("Color ID");
		tfColorId.setColumns(10);
		tfColorId.setBounds(150, 117, 142, 23);
		contentPane.add(tfColorId);

		JButton bTexture = new JButton("Select Texture");
		bTexture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					JFileChooser chooser = new JFileChooser();

					chooser.setFileFilter(new PngFilter());
					if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					    loadTexture(chooser.getSelectedFile());
					}
				});
			}
		});
		bTexture.setBounds(10, 152, 128, 28);
		contentPane.add(bTexture);

		JButton bClear = new JButton("Clear");
		bClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					cTexture.clear();
					pColor.setBackground(Color.WHITE);
					lColorHex.setForeground(new Color(51, 51, 51));
					lColorHex.setText("Color Hex");
					tfColorName.setText("Color Name");
					tfColorId.setText("Color ID");
				});
			}
		});
		bClear.setBounds(10, 192, 128, 28);
		contentPane.add(bClear);

		JTextPane tpDescription = new JTextPane();
		tpDescription.setEditable(false);
		tpDescription.setBackground(new Color(238, 238, 238));
		tpDescription.setText("Get a map color for blocks proximately by averaging their texture's total color.");
		tpDescription.setBounds(150, 148, 142, 71);
		contentPane.add(tpDescription);
	}

	public void loadTexture(File file) {
		BufferedImage img;
		try {
			img = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		MapColor index = MapColor.getMapColor(img);
		if (index == null) return;

		pColor.setBackground(index.color);
		lColorHex.setText(index.toHex());
		lColorHex.setForeground(index.getInvertColor());
		tfColorName.setText(index.name);
		tfColorId.setText(Integer.toString(index.id));
		cTexture.setImage(img);
	}
}
