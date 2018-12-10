
/* 
 * 
 * Copyright (c) 2001-2014, JGraph Ltd
 * All rights reserved.
 */


package br.edu.ifam.umlhelper.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import com.mxgraph.util.mxResources;
import com.mxgraph.view.mxGraph;
import javax.swing.ImageIcon;

public class EditorSobreFrame extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3378029138434324390L;

	/**
	 * 
	 */
	public EditorSobreFrame(Frame owner)
	{
		super(owner);
		setTitle("Sobre UMLibras");
		setLayout(new BorderLayout());

		// Creates the gradient panel
		JPanel panel = new JPanel(new BorderLayout())
		{

			/**
			 * 
			 */
			//private static final long serialVersionUID = -5062895855016210947L;

			/**
			 * 
			 */
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);

				// Paint gradient background
				Graphics2D g2d = (Graphics2D) g;
				g2d.setPaint(new GradientPaint(0, 0, Color.WHITE, getWidth(),
						0, getBackground()));
				g2d.fillRect(0, 0, getWidth(), getHeight());
			}

		};

		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createMatteBorder(0, 0, 1, 0, Color.GRAY), BorderFactory
				.createEmptyBorder(8, 8, 12, 8)));

		// Adds title
		JLabel titleLabel = new JLabel("Sobre UMLibras");
		titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
		titleLabel.setOpaque(false);
		panel.add(titleLabel, BorderLayout.NORTH);

		// Adds optional subtitle
		JLabel subtitleLabel = new JLabel(
				"Para mais informações acesse: https://github.com/Antonnyx/TCCI/tree/development/UMLibras");
		subtitleLabel.setBorder(BorderFactory.createEmptyBorder(4, 18, 0, 0));
		subtitleLabel.setOpaque(false);
		panel.add(subtitleLabel, BorderLayout.CENTER);

		getContentPane().add(panel, BorderLayout.NORTH);

		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

		content.add(new JLabel("UMLibras - Uma ferramenta computacional para criação de Diagramas de Caso de Uso "
                        + "com acessibilidade em Libras"));
		content.add(new JLabel(" "));

		content.add(new JLabel("UMLibras Version " + 1.0));
                content.add(new JLabel("Autores:"));
		content.add(new JLabel("Antonio José BarbosaFonseca - antoniojfns@gmail.com"));
                content.add(new JLabel("Prof. Msc. Sérgio Augusto C. Bezerra - sergio.bezerra@ifam.edu.br"));
                content.add(new JLabel("Prof. Msc. Emmerson Santa Rita da Silva - emmerson.silva@ifam.edu.br"));
                content.add(new JLabel(" "));
                content.add(new JLabel("Apoio:"));
                content.add(new JLabel("DAIC - Departamento Acadêmico de Informação e Comunicação"));
                ImageIcon ifamIcon = new ImageIcon(Paletas.class.getResource("/br/edu/ifam/umlhelper/images/icon_ifam2.png"));
                ImageIcon apoemaIcon = new ImageIcon(Paletas.class.getResource("/br/edu/ifam/umlhelper/images/icon_apoema2.png"));
                JLabel ifamLabel = new JLabel(ifamIcon);
                JLabel apoemaLabel = new JLabel(apoemaIcon);
                content.add(new JLabel(" "));
                content.add(new JLabel("IFAM"));
		content.add(ifamLabel);
                content.add(new JLabel(" "));
                content.add(new JLabel("APOEMA"));
		content.add(apoemaLabel);
		content.add(new JLabel(" "));

		try
		{
			content.add(new JLabel("Sistema Operacional: "
					+ System.getProperty("os.name")));
			content.add(new JLabel("Versão: "
					+ System.getProperty("os.version")));
			content.add(new JLabel(" "));

			content.add(new JLabel("Java : "
					+ System.getProperty("java.vendor", "undefined")));
			content.add(new JLabel("Versão: "
					+ System.getProperty("java.version", "undefined")));
			content.add(new JLabel(" "));

		}
		catch (Exception e)
		{
			// ignore
		}

		getContentPane().add(content, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createMatteBorder(1, 0, 0, 0, Color.GRAY), BorderFactory
				.createEmptyBorder(16, 8, 8, 8)));
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		// Adds OK button to close window
		JButton closeButton = new JButton("Fechar");
		closeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});

		buttonPanel.add(closeButton);

		// Sets default button for enter key
		getRootPane().setDefaultButton(closeButton);
                setLocationRelativeTo(null);
		setResizable(false);
		setSize(750, 800);
	}

	/**
	 * Overrides {@link JDialog#createRootPane()} to return a root pane that
	 * hides the window when the user presses the ESCAPE key.O
	 */
	protected JRootPane createRootPane()
	{
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		JRootPane rootPane = new JRootPane();
		rootPane.registerKeyboardAction(new ActionListener()
		{
			public void actionPerformed(ActionEvent actionEvent)
			{
				setVisible(false);
			}
		}, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
		return rootPane;
	}

}
