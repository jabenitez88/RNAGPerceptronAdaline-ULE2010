//package dbutils;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import java.awt.event.*;
import java.awt.*;

/**
 * Clase que crea la ventana principal del programa
 */
public class Perceptron extends JFrame implements ActionListener {
	MainDesktopPane desktop;
	static String erreBueno;

	public Perceptron(String erre) {
		super("PerAda 2.0 - JABA");
		erreBueno = erre;

		int inset = 50;
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//setBounds(inset * 3, 0, screenSize.width - inset * 6, screenSize.height
			//	- inset * 1);
		setBounds(0,0,1076,710);
		// Set up the GUI.
		desktop = new MainDesktopPane(); // a specialized layered pane
		setContentPane(desktop);
		/* Dependiendo del nivel del usuario muestra una barra u otra */
		setJMenuBar(createMenuBarAdmin());

		// Make dragging a little faster but perhaps uglier.
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

	}

	/** Funci�n que crea la barra de men� del administrador */
	protected JMenuBar createMenuBarAdmin() {
		JMenuBar menuBar = new JMenuBar();

		// Crea los men�s de arriba y sus submen�s
		JMenu menuArchivo = new JMenu("Cálculos");
		menuArchivo.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menuArchivo);

		JMenuItem menuItem = new JMenuItem("Hallar Perceptrón");
		menuItem.setMnemonic(KeyEvent.VK_U);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				ActionEvent.ALT_MASK));
		menuItem.setActionCommand("hallarPerceptron");
		menuItem.addActionListener(this);
		menuArchivo.add(menuItem);

		// Crea el men� item
		menuItem = new JMenuItem("Hallar Adaline");
		menuItem.setMnemonic(KeyEvent.VK_F);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				ActionEvent.ALT_MASK));
		menuItem.setActionCommand("hallarAdaline");
		menuItem.addActionListener(this);
		menuArchivo.add(menuItem);

		JMenu menuAcercaDe = new JMenu("Acerca de");
		menuAcercaDe.setMnemonic(KeyEvent.VK_I);
		menuBar.add(menuAcercaDe);

		menuItem = new JMenuItem("Sobre...");
		menuItem.setMnemonic(KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				ActionEvent.ALT_MASK));
		menuItem.setActionCommand("Sobre");
		menuItem.addActionListener(this);
		menuAcercaDe.add(menuItem);

		return menuBar;
	}

	public void actionPerformed(ActionEvent e) {

		if ("hallarPerceptron".equals(e.getActionCommand())) {
			PerceptronDatos frame = new PerceptronDatos(erreBueno);
			frame.setVisible(true);
			desktop.add(frame);
		}
		if ("hallarAdaline".equals(e.getActionCommand())) {
			AdalineDatos frame = new AdalineDatos(erreBueno);
			frame.setVisible(true);
			desktop.add(frame);
		} else if ("Sobre".equals(e.getActionCommand())) { // quit
			JPanel textControlsPane = new JPanel();
			JOptionPane
					.showMessageDialog(
							textControlsPane,
							"Autor: Jose Alberto Benitez Andrades\n"
									+ "Asignatura: Redes Neuronales y Algoritmos Genéticos\n"
									+ "Programa: Cálculo de Perceptrones y Adalines",
							"RNAG", JOptionPane.INFORMATION_MESSAGE);
		} else if ("relogin".equals(e.getActionCommand())) {
			dispose();
		} else if ("quit".equals(e.getActionCommand())) { // quit
			System.exit(0);
		}

	}

	// Quit the application.
	protected void quit() {
		System.exit(0);
	}

	class MainDesktopPane extends JDesktopPane {

		private Image image;

		public MainDesktopPane() {
			super();
			image = new ImageIcon(getClass().getResource("/images/fondo.jpg"))
					.getImage();
			setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
			setDoubleBuffered(true);
			setBackground(new Color(255, 255, 255));
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			int h = (int) image.getHeight(null) / 2;
			int w = (int) (image.getWidth(null) / 2);
			g.drawImage(image, (int) getWidth() / 2 - w, (int) getHeight() / 2
					- h, null);

		}
	}
}
