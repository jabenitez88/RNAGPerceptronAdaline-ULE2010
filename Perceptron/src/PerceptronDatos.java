import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class PerceptronDatos extends JInternalFrame {
	static final int xOffset = 0, yOffset = 0;
	PanelCompleto panelTotal;

	public PerceptronDatos(String r) {

		super("Calcular Perceptrón", true, // resizable
				true, // closable
				false, // maximizable
				true);// iconifiable

		panelTotal = new PanelCompleto(r);
		add(panelTotal);

		// Display the window.
		pack();
		setVisible(true);

		// ...Then set the window size or call pack...
		setSize(1050, 580);

		// Set the window's location.
		setLocation(xOffset, yOffset);
	}

	class PanelCompleto extends JPanel implements ActionListener {

		/** Declaración de variables */

		/** Texto Aceptar para el boton aceptar */
		protected static final String AGREGARA = "Agregar A";

		protected static final String BORRARA = "Borrar A";
		protected static final String AGREGARB = "Agregar B";
		protected static final String BORRARB = "Borrar B";
		protected static final String AGREGARC = "Agregar";
		protected static final String BORRARC = "Borrar";
		protected static final String BORRAR = "Reset";

		protected static final String CALCULAR = "Calcula";
		protected static final String MANUAL = "Manual";
		protected static final String HVALOR = "Hallar";

		protected static final String FICHERO = "Cargar Fichero";

		protected static final String string01 = "Tabla A: ";
		protected static final String string02 = "Tabla B: ";
		protected static final String string03 = "Tabla Pesos: ";
		protected static final String string04 = "Tabla de k: ";

		JPanel textControlsPane;
		JPanel textControlsPane2;
		JPanel textControlsPane3;
		JPanel textControlsPane4;
		JPanel textControlsPane5;

		JTable tablaA;
		DefaultTableModel modeloA;
		JTable tablaB;
		DefaultTableModel modeloB;
		JTable tablaPesos;
		DefaultTableModel modeloPesos;
		JTable tablaK;
		DefaultTableModel modeloK;

		JTable tablaC;
		DefaultTableModel modeloC;

		JLabel label01;
		JLabel label02;
		JLabel label03;
		JLabel label04;

		double[] pesos;
		double[] k;
		int erre;
		double varP = 1;
		double varPInicial;

		int iActual = 0;
		int jActual = 0;
		boolean terminado = false;
		boolean enfriamiento = false;

		int pesosCalc = 1;

		/** Panel de control que contiene los botones */
		JComponent buttonPanel;
		JComponent buttonPanel2;
		JComponent buttonPanel3;
		JComponent buttonPanel4;
		JComponent buttonPanel5;
		JPanel panelPpal;

		Plano PC;

		public PanelCompleto(String r) {
			// Establecemos el tipo de layout
			setLayout(new GridLayout(1, 2));
			panelPpal = new JPanel();

			panelPpal.setLayout(new GridLayout(3, 2));

			/* Creación de lo campos de texto */

			// Campo de texto de Nombre
			erre = Integer.parseInt(r);

			/* Creación de las etiquetas... */

			/* ***************************************************************************** */
			/* ***************************************************************************** */
			/*
			 * ************************ TABLA A EN LEFTPANE
			 * ******************************
			 */
			/* ***************************************************************************** */
			/* ***************************************************************************** */

			modeloA = new DefaultTableModel();

			for (int i = 0; i < Integer.parseInt(r); i++) {
				modeloA.addColumn(i);
			}
			tablaA = new JTable(modeloA);

			tablaA.setPreferredScrollableViewportSize(new Dimension(200, 100));

			JScrollPane scrollPaneA = new JScrollPane(tablaA);

			label01 = new JLabel(string01);
			label01.setLabelFor(tablaA);

			/* Creamos un layout propio para los controles anteriores */
			textControlsPane = new JPanel();
			GridBagLayout gridbag = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();

			// Establecemos la rejilla
			textControlsPane.setLayout(gridbag);

			// Por comodidad, creamos una función que añada los controles a la
			// rejilla
			// y al panel que la contiene...

			c.gridwidth = GridBagConstraints.REMAINDER;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			textControlsPane.add(scrollPaneA, c);
			c.gridwidth = GridBagConstraints.RELATIVE;
			c.fill = GridBagConstraints.NONE;
			c.weightx = 0.0;

			// Propiedades de la rejilla
			c.gridwidth = GridBagConstraints.REMAINDER; // last
			c.fill = GridBagConstraints.NONE;
			c.anchor = GridBagConstraints.EAST;
			c.weightx = 1.0;

			// Situados los demás elementos, colocamos la etiqueta informativa
			buttonPanel = createButtonPanel();
			textControlsPane.add(buttonPanel, c);
			textControlsPane.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder("Tabla A"), BorderFactory
							.createEmptyBorder(5, 5, 5, 5)));

			// Agrupamos los distintos paneles creados (uno en este caso)
			JPanel leftPane = new JPanel(new BorderLayout());

			leftPane.add(textControlsPane, BorderLayout.PAGE_START);
			// leftPane.add(areaScrollPane, BorderLayout.CENTER);

			/* Creación de lo campos de texto */

			/* ***************************************************************************** */
			/* ***************************************************************************** */
			/*
			 * ************************ TABLA B EN RIGHTPANE
			 * ******************************
			 */
			/* ***************************************************************************** */
			/* ***************************************************************************** */

			// Etiqueta para Usuario

			modeloB = new DefaultTableModel();

			for (int i = 0; i < Integer.parseInt(r); i++) {
				modeloB.addColumn(i);
			}
			tablaB = new JTable(modeloB);

			tablaB.setPreferredScrollableViewportSize(new Dimension(200, 100));

			JScrollPane scrollPaneB = new JScrollPane(tablaB);

			label02 = new JLabel(string02);
			label02.setLabelFor(tablaB);

			textControlsPane2 = new JPanel();
			GridBagLayout gridbag2 = new GridBagLayout();
			GridBagConstraints c2 = new GridBagConstraints();

			// Establecemos la rejilla
			textControlsPane2.setLayout(gridbag2);

			// Por comodidad, creamos una función que añada los controles a la
			// rejilla
			// y al panel que la contiene...

			c2.gridwidth = GridBagConstraints.REMAINDER;
			c2.fill = GridBagConstraints.HORIZONTAL;
			c2.weightx = 1.0;
			textControlsPane2.add(scrollPaneB, c2);

			// Propiedades de la rejilla
			c2.gridwidth = GridBagConstraints.REMAINDER; // last
			c2.fill = GridBagConstraints.NONE;
			c2.anchor = GridBagConstraints.EAST;
			c2.weightx = 1.0;

			// Situados los demás elementos, colocamos la etiqueta informativa
			buttonPanel2 = createButtonPanel2();
			textControlsPane2.add(buttonPanel2, c);
			textControlsPane2.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder("Tabla B"), BorderFactory
							.createEmptyBorder(5, 5, 5, 5)));

			JPanel rightPane = new JPanel(new BorderLayout());

			rightPane.add(textControlsPane2, BorderLayout.PAGE_START);
			// leftPane.add(areaScrollPane, BorderLayout.CENTER);

			/* ***************************************************************************** */
			/* ***************************************************************************** */
			/*
			 * ************************ TABLA Pesos EN DOWNPANE
			 * ***************************
			 */
			/* ***************************************************************************** */
			/* ***************************************************************************** */

			modeloPesos = new DefaultTableModel();

			modeloPesos.addColumn("Iteración Nº");
			for (int i = 0; i < Integer.parseInt(r); i++) {
				modeloPesos.addColumn("w" + (i + 1));
			}
			modeloPesos.addColumn("o");
			modeloPesos.addColumn("Aprendizaje");
			modeloPesos.addRow(new Vector());
			modeloPesos.setValueAt(0, 0, 0);
			double valorPeso = 0;
			
			for (int i = 1; i <= Integer.parseInt(r) + 1; i++)
			{
				if((int)(Math.random()*10)%2 == 0) valorPeso = (Math.random()*10);
				else valorPeso = - (Math.random()*10);
				modeloPesos.setValueAt(valorPeso, 0, i);
			}
			modeloPesos.setValueAt(varP, 0, erre + 2);

			tablaPesos = new JTable(modeloPesos);

			tablaPesos.setPreferredScrollableViewportSize(new Dimension(200,
					100));

			JScrollPane scrollPanePesos = new JScrollPane(tablaPesos);

			label03 = new JLabel(string03);
			label03.setLabelFor(tablaPesos);

			/* Creamos un layout propio para los controles anteriores */
			textControlsPane3 = new JPanel();
			GridBagLayout gridbag3 = new GridBagLayout();
			GridBagConstraints c3 = new GridBagConstraints();

			// Establecemos la rejilla
			textControlsPane3.setLayout(gridbag);

			// Por comodidad, creamos una función que añada los controles a la
			// rejilla
			// y al panel que la contiene...

			c3.gridwidth = GridBagConstraints.REMAINDER;
			c3.fill = GridBagConstraints.HORIZONTAL;
			c3.weightx = 1.0;
			textControlsPane3.add(scrollPanePesos, c3);

			// Propiedades de la rejilla
			c3.gridwidth = GridBagConstraints.REMAINDER; // last
			c3.fill = GridBagConstraints.NONE;
			c3.anchor = GridBagConstraints.EAST;
			c3.weightx = 1.0;

			buttonPanel3 = createButtonPanel3();
			textControlsPane3.add(buttonPanel3, c3);
			// Situados los demás elementos, colocamos la etiqueta informativa
			textControlsPane3.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder("Tabla Pesos"),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));

			// Agrupamos los distintos paneles creados (uno en este caso)
			JPanel downPane = new JPanel(new BorderLayout());

			downPane.add(textControlsPane3, BorderLayout.PAGE_START);

			/* ***************************************************************************** */
			/* ***************************************************************************** */
			/*
			 * ************************ TABLA K EN DOWNPANE2
			 * ******************************
			 */
			/* ***************************************************************************** */
			/* ***************************************************************************** */

			modeloK = new DefaultTableModel();

			tablaK = new JTable(modeloK);
			modeloK.addColumn("K");
			modeloK.addColumn("Valor");

			tablaK.setPreferredScrollableViewportSize(new Dimension(200, 100));

			JScrollPane scrollPaneK = new JScrollPane(tablaK);

			label04 = new JLabel(string04);
			label04.setLabelFor(tablaK);

			/* Creamos un layout propio para los controles anteriores */
			textControlsPane4 = new JPanel();
			GridBagLayout gridbag4 = new GridBagLayout();
			GridBagConstraints c4 = new GridBagConstraints();

			// Establecemos la rejilla
			textControlsPane4.setLayout(gridbag4);

			// Por comodidad, creamos una función que añada los controles a la
			// rejilla
			// y al panel que la contiene...

			c4.gridwidth = GridBagConstraints.REMAINDER;
			c4.fill = GridBagConstraints.HORIZONTAL;
			c4.weightx = 1.0;
			textControlsPane4.add(scrollPaneK, c4);

			// Propiedades de la rejilla
			c4.gridwidth = GridBagConstraints.REMAINDER; // last
			c4.fill = GridBagConstraints.NONE;
			c4.anchor = GridBagConstraints.EAST;
			c4.weightx = 1.0;
			buttonPanel4 = createButtonPanel4();
			textControlsPane4.add(buttonPanel4, c4);

			// Situados los demás elementos, colocamos la etiqueta informativa
			textControlsPane4.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder("Tabla K"), BorderFactory
							.createEmptyBorder(5, 5, 5, 5)));

			// Agrupamos los distintos paneles creados (uno en este caso)

			JPanel downPane2 = new JPanel(new BorderLayout());

			downPane2.add(textControlsPane4, BorderLayout.PAGE_START);

			PC = new Plano(20);

			PC.Limites(10, 10, -10, -10);
			PC.Origen();
			/*
			 * if(totalCoord!=0) { for(int i=0;i<totalCoord+1;i++) {
			 * PC.Punto(coordenadas[i][0],coordenadas[i][1]);
			 * System.out.println(coordenadas[i][0]+" "+coordenadas[i][1]); } }
			 */

			// System.out.println(w[0]+" "+w[1]+" "+w[2]);

			PC.repaint();
			JFrame grafica = new JFrame();
			grafica.add(PC);

			/* ***************************************************************************** */
			/* ***************************************************************************** */
			/*
			 * ************************ TABLA C EN RIGHTPANE
			 * ******************************
			 */
			/* ***************************************************************************** */
			/* ***************************************************************************** */

			// Etiqueta para Usuario

			modeloC = new DefaultTableModel();

			for (int i = 0; i < Integer.parseInt(r); i++) {
				modeloC.addColumn("x" + i);
			}
			modeloC.addColumn("Valor");
			tablaC = new JTable(modeloC);

			tablaC.setPreferredScrollableViewportSize(new Dimension(200, 100));

			JScrollPane scrollPaneC = new JScrollPane(tablaC);

			/* Creamos un layout propio para los controles anteriores */
			textControlsPane5 = new JPanel();
			GridBagLayout gridbag5 = new GridBagLayout();
			GridBagConstraints c5 = new GridBagConstraints();

			// Establecemos la rejilla
			textControlsPane5.setLayout(gridbag5);

			// Por comodidad, creamos una función que añada los controles a la
			// rejilla
			// y al panel que la contiene...

			c5.gridwidth = GridBagConstraints.REMAINDER;
			c5.fill = GridBagConstraints.HORIZONTAL;
			c5.weightx = 1.0;
			textControlsPane5.add(scrollPaneC, c5);

			// Propiedades de la rejilla
			c5.gridwidth = GridBagConstraints.REMAINDER; // last
			c5.fill = GridBagConstraints.NONE;
			c5.anchor = GridBagConstraints.EAST;
			c5.weightx = 1.0;
			buttonPanel5 = createButtonPanel5();
			textControlsPane5.add(buttonPanel5, c5);

			// Situados los demás elementos, colocamos la etiqueta informativa
			textControlsPane5.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder("Puntos"), BorderFactory
							.createEmptyBorder(5, 5, 5, 5)));

			JPanel downPane3 = new JPanel(new BorderLayout());

			downPane3.add(textControlsPane5, BorderLayout.PAGE_START);

			panelPpal.add(leftPane);
			panelPpal.add(rightPane);
			panelPpal.add(downPane);
			panelPpal.add(downPane2);
			panelPpal.add(downPane3);

			add(panelPpal);
			if (erre == 2)
				add(PC);

		}// fin_FormularioInsertarUsuario()

		private void addLabelTextRows(JLabel[] labels, JComponent[] textFields,
				GridBagLayout gridbag, Container container) {

			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.EAST;
			int numLabels = labels.length;

			for (int i = 0; i < numLabels; i++) {
				c.gridwidth = GridBagConstraints.RELATIVE; // next-to-last
				c.fill = GridBagConstraints.NONE; // reset to default
				c.weightx = 0.0; // reset to default
				container.add(labels[i], c);

				c.gridwidth = GridBagConstraints.REMAINDER; // end row
				c.fill = GridBagConstraints.HORIZONTAL;
				c.weightx = 1.0;
				container.add(textFields[i], c);
			}// fin_del_for

		}// fin_addLabelTextRows

		protected JComponent createButtonPanel() {
			// Creo un Jpanel p, que será el panel del botón, con un gridLayout.
			JPanel p = new JPanel(new GridLayout(1, 0));

			// Creo los 2 botones.
			JButton agregaraButton = new JButton(AGREGARA);
			JButton borraraButton = new JButton(BORRARA);

			// Asigno un nombre para los botones
			agregaraButton.setActionCommand(AGREGARA);
			borraraButton.setActionCommand(BORRARA);

			// Asigno la acción para los botones Aceptar y Cancelar.
			agregaraButton.addActionListener(this);
			borraraButton.addActionListener(this);

			p.add(agregaraButton);
			p.add(borraraButton);

			return p;
		}

		protected JComponent createButtonPanel2() {
			// Creo un Jpanel p, que será el panel del botón, con un gridLayout.
			JPanel p = new JPanel(new GridLayout(1, 0));

			// Creo los 2 botones.
			JButton agregarbButton = new JButton(AGREGARB);
			JButton borrarbButton = new JButton(BORRARB);

			// Asigno un nombre para los botones
			agregarbButton.setActionCommand(AGREGARB);
			borrarbButton.setActionCommand(BORRARB);

			// Asigno la acción para los botones Aceptar y Cancelar.
			agregarbButton.addActionListener(this);
			borrarbButton.addActionListener(this);

			p.add(agregarbButton);
			p.add(borrarbButton);

			return p;
		}

		protected JComponent createButtonPanel3() {
			// Creo un Jpanel p, que será el panel del botón, con un gridLayout.
			JPanel p = new JPanel(new GridLayout(1, 0));

			// Creo los 2 botones.
			JButton calcularButton = new JButton(CALCULAR);
			JButton manualButton = new JButton(MANUAL);
			JButton borrarButton = new JButton(BORRAR);

			// Asigno un nombre para los botones
			calcularButton.setActionCommand(CALCULAR);
			manualButton.setActionCommand(MANUAL);
			borrarButton.setActionCommand(BORRAR);

			// Asigno la acción para los botones Aceptar y Cancelar.
			calcularButton.addActionListener(this);
			manualButton.addActionListener(this);
			borrarButton.addActionListener(this);

			p.add(calcularButton);
			p.add(manualButton);
			p.add(borrarButton);

			return p;
		}

		protected JComponent createButtonPanel4() {
			// Creo un Jpanel p, que será el panel del botón, con un gridLayout.
			JPanel p = new JPanel(new GridLayout(1, 0));

			// Creo los 2 botones.
			JCheckBox enfriamientoButton = new JCheckBox("Enfriamiento");
			JButton botonOpen = new JButton("Cargar Fichero");
			// Asigno un nombre para los botones
			enfriamientoButton.setActionCommand("Con Enfriamiento");
			botonOpen.setActionCommand(FICHERO);
			botonOpen.addActionListener(this);

			// Asigno la acción para los botones Aceptar y Cancelar.
			enfriamientoButton.addActionListener(this);

			p.add(enfriamientoButton);
			p.add(botonOpen);
			return p;

		}

		protected JComponent createButtonPanel5() {
			// Creo un Jpanel p, que será el panel del botón, con un gridLayout.
			JPanel p = new JPanel(new GridLayout(1, 0));

			// Creo los 2 botones.
			JButton graficaButton = new JButton(HVALOR);
			JButton borrarbButton = new JButton(BORRARC);
			JButton agregarbButton = new JButton(AGREGARC);

			// Asigno un nombre para los botones
			graficaButton.setActionCommand(HVALOR);
			borrarbButton.setActionCommand(BORRARC);
			agregarbButton.setActionCommand(AGREGARC);

			// Asigno la acción para los botones Aceptar y Cancelar.
			graficaButton.addActionListener(this);
			borrarbButton.addActionListener(this);
			agregarbButton.addActionListener(this);
			p.add(agregarbButton);
			p.add(borrarbButton);
			p.add(graficaButton);

			return p;
		}

		protected void calcularPerceptron(int paso) {
			int i = 0;
			int j = 0;
			int l = 0;
			int contbucle = 0;
			double pesoAux = 0;
			terminado = false;
			varPInicial = varP;
			// Creo el array de pesos, que tendrá erre + 1 (contando theta)
			pesos = new double[erre + 1];
			// Creo el array de k[x] que tendrá en total la suma de las filas de
			// las 2 tablas
			k = new double[modeloA.getRowCount() + modeloB.getRowCount()];
			// Inicializo las variables aleatoriamente a 1
			// for(int m=0; m < erre+1; m++) pesos[m] = 1;

			int manual = paso;
			// Si no está terminado y el bucle no se ha ejecutado X veces
			// sigue realizando cálculos, sino, salte.

			// Este bucle reinicia la tabla de pesos
			if (manual != 1) {
				for (int aux = modeloPesos.getRowCount() - 1; aux > 0; aux--)
					modeloPesos.removeRow(aux);
				pesosCalc = 1;
			}
			for (int aux = 0; aux < erre + 1; aux++)
				pesos[aux] = Double.parseDouble(modeloPesos.getValueAt(
						modeloPesos.getRowCount() - 1, aux + 1).toString());
			// System.out.println(pesos[0]+ " "+pesos[1]+ " "+pesos[2]);
			FileWriter fichero = null;
			PrintWriter pw = null;
			try {
				fichero = new FileWriter("peperceptron.txt", true);
				pw = new PrintWriter(fichero);
				String cadenaAux = null;
				if (pesosCalc == 1) {
					pw.println("");
					pw.println("***********************************");
					pw.println("***** Cálculo del Perceptrón ******");
					pw.println("***********************************");
					pw.println("");
				}

				while ((!terminado) && (contbucle < 5000) && (manual < 2)) {
					contbucle++;
					int estanBien = 0;
					int recalcular = 0;
					// Mientras que no hayas recorrido la tabla 1 completa
					// sigue haciendo cálculos para la tabla A (+1)
					while (i < modeloA.getRowCount()) {

						k[i] = 0;
						// Calculamos k[i], la j representa el número de peso
						// que multiplica por
						// la coordenada x,y,z..... que la obtiene de la tablaA
						// posición [i,j]
						cadenaAux = "k[" + (i + 1) + "] = ";
						for (j = 0; j < erre; j++) {
							k[i] = k[i]
									+ Double.parseDouble(modeloA.getValueAt(i,
											j).toString()) * pesos[j];
							if (j < erre - 1)
								cadenaAux = cadenaAux
										+ modeloA.getValueAt(i, j).toString()
										+ "*" + pesos[j] + " + ";
							else
								cadenaAux = cadenaAux
										+ modeloA.getValueAt(i, j).toString()
										+ "*" + pesos[j];
						}
						// Finalmente, resta el valor de theta.
						k[i] = k[i] - pesos[erre];
						if (pesos[erre] < 0)
							pw.println(cadenaAux + " + " + (pesos[erre] * -1)
									+ " = " + k[i]);
						else
							pw.println(cadenaAux + " - " + pesos[erre] + " = "
									+ k[i]);
						// System.out.println("La k["+i+"] vale "+k[i]);

						// Al ser la tabla A, si el valor es menor o igual a 0,
						// recalcula los pesos.
						if (k[i] <= 0) {
							pw.println("");
							pw.println("El valor de k[" + (i + 1)
									+ "] no es correcto, debemos recalcular:");
							pw.println("Iteración Nº \n"
									+ modeloPesos.getRowCount());
							pw.println("");
							modeloPesos.addRow(new Vector());
							modeloPesos
									.setValueAt(modeloPesos.getRowCount() - 1,
											pesosCalc, 0);
							for (j = 0; j < erre; j++) {
								pesoAux = pesos[j];
								pesos[j] = pesos[j]
										+ (varP * 1 * Double
												.parseDouble(modeloA
														.getValueAt(i, j)
														.toString()));
								pw.println("w" + (j + 1) + " = " + pesoAux
										+ " + (1 * 1 * "
										+ modeloA.getValueAt(i, j) + ") = "
										+ pesos[j]);
								modeloPesos.setValueAt(pesos[j], pesosCalc,
										j + 1);
								// System.out.println("Valor coordenada A "+j+" "+Integer.parseInt(modeloA.getValueAt(i,j).toString()));
							}
							// Este último es el valor de theta.
							pesoAux = pesos[erre];
							pesos[erre] = pesos[erre] + (varP * 1 * (-1));
							pw.println("o = " + pesoAux + " + 1 * 1 * (-1) = "
									+ pesos[erre]);
							pw.println("");
							modeloPesos.setValueAt(pesos[erre], pesosCalc,
									erre + 1);
							pesosCalc++;
							estanBien = 0;
							double auxiliar = 0;
							for (int aux = 0; aux < modeloA.getRowCount(); aux++) {
								for (j = 0; j < erre; j++) {
									auxiliar = k[aux]
											+ Double.parseDouble(modeloA
													.getValueAt(aux, j)
													.toString()) * pesos[j];
								}
								if (auxiliar > 0)
									estanBien++;
								auxiliar = 0;
							}
							for (int aux = 0; aux < modeloB.getRowCount(); aux++) {
								for (j = 0; j < erre; j++) {
									auxiliar = k[modeloA.getRowCount() + aux]
											+ Double.parseDouble(modeloB
													.getValueAt(aux, j)
													.toString()) * pesos[j];
								}
								if (auxiliar > 0)
									estanBien++;
								auxiliar = 0;
							}
							if (enfriamiento) {
								varP = varPInicial
										* (1 - Double.parseDouble(Integer
												.toString(estanBien))
												/ (modeloA.getRowCount() + modeloB
														.getRowCount()));
								System.out.println("Aprendizaje: "
										+ varP
										+ " - "
										+ (i / (modeloA.getRowCount() + modeloB
												.getRowCount())));
								modeloPesos.setValueAt(varP, modeloPesos
										.getRowCount() - 1, erre + 2);
							}
							// Y vuelve a inicializar la i a 0, para recalcular
							// desde el principio.
							i = 0;
							if (manual == 1)
								manual++;
							if (erre == 2) {
								PC.pintaLinea(pesos);
								/*
								 * formulario.revalidate();
								 * formulario.paint(formulario.getGraphics());
								 * Thread.sleep(400);
								 */
							}

						} else {
							i++;

						}

					}

					while ((l < modeloB.getRowCount()) && (i != 0)) {
						k[i + l] = 0;
						// Calculamos k[i+l], la j representa el número de peso
						// que multiplica por
						// la coordenada x,y,z..... que la obtiene de la tablaA
						// posición [l,j]
						cadenaAux = "k[" + (i + 1 + l) + "] = ";
						for (j = 0; j < erre; j++) {
							k[i + l] = k[i + l]
									+ Double.parseDouble(modeloB.getValueAt(l,
											j).toString()) * pesos[j];
							if (j < erre - 1)
								cadenaAux = cadenaAux
										+ modeloB.getValueAt(l, j).toString()
										+ "*" + pesos[j] + " + ";
							else
								cadenaAux = cadenaAux
										+ modeloB.getValueAt(l, j).toString()
										+ "*" + pesos[j];

						}
						// Finalmente, resta el valor de theta.
						k[i + l] = k[i + l] - pesos[erre];
						if (pesos[erre] < 0)
							pw.println(cadenaAux + " + " + (pesos[erre] * -1)
									+ " = " + k[i + l]);
						else
							pw.println(cadenaAux + " - " + pesos[erre] + " = "
									+ k[i + l]);

						// System.out.println("La k["+(i+l)+"] vale "+k[i+l]);

						// Al ser la tabla B, si el valor es mayor o igual a 0,
						// recalcula los pesos.
						if (k[i + l] >= 0) {
							pw.println("");
							pw.println("El valor de k[" + (i + 1 + l)
									+ "] no es correcto, debemos recalcular:");
							pw.println("Iteración Nº \n"
									+ modeloPesos.getRowCount());
							pw.println("");

							modeloPesos.addRow(new Vector());
							modeloPesos
									.setValueAt(modeloPesos.getRowCount() - 1,
											pesosCalc, 0);
							for (j = 0; j < erre; j++) {
								pesoAux = pesos[j];
								pesos[j] = pesos[j]
										+ (varP * (-1) * Double
												.parseDouble(modeloB
														.getValueAt(l, j)
														.toString()));
								pw.println("w" + (j + 1) + " = " + pesoAux
										+ " + (1 * -1 * "
										+ modeloB.getValueAt(l, j) + ") = "
										+ pesos[j]);
								modeloPesos.setValueAt(pesos[j], pesosCalc,
										j + 1);

							}
							pesoAux = pesos[erre];
							pesos[erre] = pesos[erre] + (varP * (-1) * (-1));
							pw.println("o = " + pesoAux
									+ " + 1 * (-1) * (-1) = " + pesos[erre]);
							pw.println("");
							modeloPesos.setValueAt(pesos[erre], pesosCalc,
									erre + 1);
							pesosCalc++;
							estanBien = 0;
							double auxiliar = 0;
							for (int aux = 0; aux < modeloA.getRowCount(); aux++) {
								for (j = 0; j < erre; j++) {
									auxiliar = k[aux]
											+ Double.parseDouble(modeloA
													.getValueAt(aux, j)
													.toString()) * pesos[j];
								}
								if (auxiliar > 0)
									estanBien++;
								auxiliar = 0;
							}
							for (int aux = 0; aux < modeloB.getRowCount(); aux++) {
								for (j = 0; j < erre; j++) {
									auxiliar = k[modeloA.getRowCount() + aux]
											+ Double.parseDouble(modeloB
													.getValueAt(aux, j)
													.toString()) * pesos[j];
								}
								if (auxiliar > 0)
									estanBien++;
								auxiliar = 0;
							}
							if (enfriamiento) {
								varP = varPInicial
										* (1 - Double.parseDouble(Integer
												.toString(estanBien))
												/ (modeloA.getRowCount() + modeloB
														.getRowCount()));
								System.out.println("Aprendizaje: "
										+ varP
										+ " - "
										+ (i / (modeloA.getRowCount() + modeloB
												.getRowCount())));
								modeloPesos.setValueAt(varP, modeloPesos
										.getRowCount() - 1, erre + 2);
							}
							if (erre == 2) {
								PC.pintaLinea(pesos);
								/*
								 * formulario.revalidate();
								 * formulario.paint(formulario.getGraphics());
								 * Thread.sleep(400);
								 */
								// panelPpal.paintImmediately(0,0,panelPpal.getSize().width,panelPpal.getSize().height);
								// Thread.sleep(400);
								// panelPpal.paintImmediately(0,0,this.getWidth(),this.getHeight());

							}
							// Y vuelve a inicializar la i a 0, para recalcular
							// desde el principio.
							l = 0;
							i = 0;
							if (manual == 1)
								manual++;

						} else {
							l++;

						}

					}

					// Si aquí llega con la i > 0, significa que realizó todos
					// los cálculos
					// con éxito, y no tiene que volver a repetir nada más.
					if (i != 0) {
						// System.out.println(contbucle+" "+i);
						terminado = true;
						if (erre == 2)
							PC.pintaLinea(pesos);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					// Nuevamente aprovechamos el finally para
					// asegurarnos que se cierra el fichero.
					if (null != fichero)
						fichero.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}

		public void actionPerformed(ActionEvent e) {
			if (AGREGARA.equals(e.getActionCommand())) {
				/**
				 * Datos es un array que posee todos los datos a insertar del
				 * usuario
				 */

				modeloA.addRow(new Vector());
			} else if (BORRARA.equals(e.getActionCommand())) { // quit
				// dispose();
				modeloA.removeRow(modeloA.getRowCount() - 1);
			}

			else if (AGREGARB.equals(e.getActionCommand())) {
				/**
				 * Datos es un array que posee todos los datos a insertar del
				 * usuario
				 */

				modeloB.addRow(new Vector());
			} else if (BORRARB.equals(e.getActionCommand())) { // quit
				// dispose();
				modeloB.removeRow(modeloB.getRowCount() - 1);
			}

			else if (AGREGARC.equals(e.getActionCommand())) {
				/**
				 * Datos es un array que posee todos los datos a insertar del
				 * usuario
				 */

				modeloC.addRow(new Vector());
			} else if (BORRARC.equals(e.getActionCommand())) { // quit
				// dispose();
				modeloC.removeRow(modeloC.getRowCount() - 1);
			}

			else if (CALCULAR.equals(e.getActionCommand())) { // quit
				// dispose();
				if (erre == 2) {
					for (int i = 0; i < modeloA.getRowCount(); i++) {
						PC.Punto(Double.parseDouble(modeloA.getValueAt(i, 0)
								.toString()), Double.parseDouble(modeloA
								.getValueAt(i, 1).toString()), modeloA
								.getRowCount());
					}
					for (int i = 0; i < modeloB.getRowCount(); i++) {
						PC.Punto(Double.parseDouble(modeloB.getValueAt(i, 0)
								.toString()), Double.parseDouble(modeloB
								.getValueAt(i, 1).toString()), modeloA
								.getRowCount());
					}
				}

				calcularPerceptron(0);

				if (!terminado) {
					JOptionPane.showMessageDialog(textControlsPane,
							"El perceptrón no tiene solución",
							"Error de cálculo", JOptionPane.ERROR_MESSAGE);
				} else {
					for (int i = modeloK.getRowCount() - 1; i >= 0; i--)
						modeloK.removeRow(i);

					for (int i = 0; i < modeloA.getRowCount()
							+ modeloB.getRowCount(); i++) {
						modeloK.addRow(new Vector());
						modeloK.setValueAt("k[" + (i + 1) + "]", i, 0);
						modeloK.setValueAt(k[i], i, 1);

					}
					/*
					 * for(int i=0;i<erre+1;i++) {
					 * modeloPesos.setValueAt(pesos[i],0,i); }
					 */
					JOptionPane
							.showMessageDialog(
									textControlsPane,
									"El perceptrón se calculó correctamente\nTiene los valores en la tabla",
									"Cálculo Exitoso :)",
									JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (MANUAL.equals(e.getActionCommand())) { // quit
				// dispose();
				if (!terminado) {

					if ((erre == 2) && (pesosCalc == 1)) {
						for (int i = 0; i < modeloA.getRowCount(); i++) {
							PC
									.Punto(Double.parseDouble(modeloA
											.getValueAt(i, 0).toString()),
											Double.parseDouble(modeloA
													.getValueAt(i, 1)
													.toString()), modeloA
													.getRowCount());
						}
						for (int i = 0; i < modeloB.getRowCount(); i++) {
							PC
									.Punto(Double.parseDouble(modeloB
											.getValueAt(i, 0).toString()),
											Double.parseDouble(modeloB
													.getValueAt(i, 1)
													.toString()), modeloA
													.getRowCount());
						}
					}
					calcularPerceptron(1);
				} else {
					for (int i = modeloK.getRowCount() - 1; i >= 0; i--)
						modeloK.removeRow(i);

					for (int i = 0; i < modeloA.getRowCount()
							+ modeloB.getRowCount(); i++) {
						modeloK.addRow(new Vector());
						modeloK.setValueAt("k[" + (i + 1) + "]", i, 0);
						modeloK.setValueAt(k[i], i, 1);

					}
					/*
					 * for(int i=0;i<erre+1;i++) {
					 * modeloPesos.setValueAt(pesos[i],0,i); }
					 */
					JOptionPane
							.showMessageDialog(
									textControlsPane,
									"El perceptrón se calculó correctamente\nTiene los valores en la tabla",
									"Cálculo Exitoso :)",
									JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (BORRAR.equals(e.getActionCommand())) { // quit
				// dispose();

				for (int i = modeloK.getRowCount() - 1; i >= 0; i--)
					modeloK.removeRow(i);
				for (int i = modeloA.getRowCount() - 1; i >= 0; i--)
					modeloA.removeRow(i);
				for (int i = modeloB.getRowCount() - 1; i >= 0; i--)
					modeloB.removeRow(i);
				for (int i = modeloPesos.getRowCount() - 1; i > 0; i--)
					modeloPesos.removeRow(i);
				pesosCalc = 1;
				terminado = false;
				PC.Reiniciar();
			} else if ("Con Enfriamiento".equals(e.getActionCommand())) { // quit
				// dispose();

				enfriamiento = !enfriamiento;
				System.out.println(enfriamiento);

			} else if (HVALOR.equals(e.getActionCommand())) {
				for (int aux = 0; aux < modeloC.getRowCount(); aux++) {
					double auxiliar = 0;
					double esperado = 0;
					for (int j = 0; j < erre; j++) {
						auxiliar = auxiliar
								+ Double.parseDouble(modeloC.getValueAt(aux, j)
										.toString()) * pesos[j];
					}
					auxiliar = auxiliar - pesos[erre];
					if (auxiliar > 0)
						modeloC.setValueAt("A", aux, erre);
					else
						modeloC.setValueAt("B", aux, erre);

					auxiliar = 0;

				}
			} else if (FICHERO.equals(e.getActionCommand())) { // quit
				JFileChooser chooser = new JFileChooser();
				JFrame frame = new JFrame();
				String dialogTitle = "Abrir un fichero";

				int linea = 0;
				int valor = 1;
				int fila = 0;
				int columna = 0;
				int AoB = 0;
				chooser.setDialogTitle(dialogTitle);
				chooser.setMultiSelectionEnabled(false);
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				int sel = chooser.showOpenDialog(frame);
				if (sel == JFileChooser.APPROVE_OPTION) {
					File selectedFile = chooser.getSelectedFile();
					// JOptionPane.showMessageDialog(frame,selectedFile.getAbsolutePath());
					FileReader fr;
					try {
						fr = new FileReader(selectedFile.getAbsolutePath());
						BufferedReader bf = new BufferedReader(fr);
						String sCadena;
						try {
							while ((sCadena = bf.readLine()) != null) {
								if (linea < erre + 1) {
									modeloPesos.setValueAt(sCadena, 0, valor);
									valor++;
								}
								if ((AoB == 1) && !(sCadena.equals("B"))) {
									if (columna == 0)
										modeloA.addRow(new Vector());
									System.out.println(sCadena + " " + fila
											+ " " + columna);
									modeloA.setValueAt(sCadena, fila, columna);
									if (columna < erre - 1)
										columna++;
									else {
										fila++;
										columna = 0;
									}
								}
								if (AoB == 2) {
									if (columna == 0)
										modeloB.addRow(new Vector());
									modeloB.setValueAt(sCadena, fila, columna);
									if (columna < erre - 1)
										columna++;
									else {
										fila++;
										columna = 0;
									}
								}
								if (sCadena.equals("A")) {
									AoB = 1;
								}
								if (sCadena.equals("B")) {
									AoB = 2;
									fila = 0;
									columna = 0;
								}

								linea++;
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					return;
				}
			}

		}
	}

}