import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class AdalineDatos extends JInternalFrame {
	static final int xOffset = 30, yOffset = 30;

	public AdalineDatos(String r) {

		super("Calcular Adaline", true, // resizable
				true, // closable
				false, // maximizable
				true);// iconifiable

		PanelCompleto panelTotal = new PanelCompleto(r);
		add(panelTotal);

		// Display the window.
		pack();
		setVisible(true);

		// ...Then set the window size or call pack...
		setSize(800, 600);

		// Set the window's location.
		setLocation(xOffset, yOffset);
	}

	/** class FormularioInsertarUsuario */
	class PanelCompleto extends JPanel implements ActionListener {

		/** Declaración de variables */

		/** Textos de los botones */
		protected static final String AGREGARA = "Agregar A";
		protected static final String BORRARA = "Borrar A";
		protected static final String AGREGARB = "Agregar Pto";
		protected static final String BORRARB = "Borrar Pto";
		protected static final String CALCULAR = "Calcular";
		protected static final String FICHERO = "Cargar";
		protected static final String BORRAR = "Reset";


		protected static final String string01 = "Tabla A: ";
		protected static final String string02 = "Tabla B: ";
		protected static final String string03 = "Tabla Pesos: ";
		protected static final String string04 = "Tabla de k: ";

		JPanel textControlsPane;
		JPanel textControlsPane2;
		JPanel textControlsPane3;
		JPanel textControlsPane4;
		JPanel textControlsPane5;

		/** Campo de texto para introducir el Nombre */
		JTable tablaA;
		DefaultTableModel modeloA;
		JTable tablaB;
		DefaultTableModel modeloB;
		JTable tablaPesos;
		DefaultTableModel modeloPesos;
		JTable tablaK;
		DefaultTableModel modeloK;
		JTable tablaPts;
		DefaultTableModel modeloPts;

		JLabel label01;
		JLabel label02;
		JLabel label03;
		JLabel label04;

		double[] pesos;
		double[] k;
		int erre;
		boolean terminado = false;
		boolean enfriamiento = false;

		/** Panel de control que contiene los botones */
		JComponent buttonPanel;
		JComponent buttonPanel2;
		JComponent buttonPanel3;
		JComponent buttonPanel4;

		double varP = 0.5;
		double varPInicial;
		int pesosCalc = 1;
		double ErrorPpal = 0;

		/** Método que crea el formulario de Insertar Usuario */
		public PanelCompleto(String r) {
			// Establecemos el tipo de layout
			setLayout(new GridLayout(2, 2));
			erre = Integer.parseInt(r);

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
				modeloA.addColumn("x" + i);
			}
			modeloA.addColumn("V.Esperado");
			modeloA.addColumn("Error");

			tablaA = new JTable(modeloA);
			tablaA.setPreferredScrollableViewportSize(new Dimension(300, 155));

			JScrollPane scrollPaneA = new JScrollPane(tablaA);

			label01 = new JLabel(string01);
			label01.setLabelFor(tablaA);

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

			/* ***************************************************************************** */
			/* ***************************************************************************** */
			/* 
			 * ************************ TABLA Pesos EN DOWNPANE
			 * ***************************
			 */
			/* ***************************************************************************** */
			/* ***************************************************************************** */

			modeloPesos = new DefaultTableModel();
			modeloPesos.addColumn("Iteración");
			for (int i = 0; i < Integer.parseInt(r); i++) {
				modeloPesos.addColumn("w" + (i + 1));
			}
			modeloPesos.addColumn("o");
			modeloPesos.addColumn("Aprendizaje");
			modeloPesos.addColumn("Error");
			modeloPesos.addRow(new Vector());
			double valorPeso = 0;

			for (int i = 0; i < Integer.parseInt(r) + 1; i++)
			{
				if((int)(Math.random()*10)%2 == 0) valorPeso = (Math.random()*10);
				else valorPeso = - (Math.random()*10);
				modeloPesos.setValueAt(valorPeso, 0, i + 1);
			}
			modeloPesos.setValueAt(0.5, 0, erre + 2);
			modeloPesos.setValueAt(0.1, 0, erre + 3);

			tablaPesos = new JTable(modeloPesos);

			tablaPesos.setPreferredScrollableViewportSize(new Dimension(300,
					155));

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
			c3.gridwidth = GridBagConstraints.RELATIVE;
			c3.fill = GridBagConstraints.NONE;
			c3.weightx = 0.0;

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

			tablaK.setPreferredScrollableViewportSize(new Dimension(300, 155));

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
			c4.gridwidth = GridBagConstraints.RELATIVE;
			c4.fill = GridBagConstraints.NONE;
			c4.weightx = 0.0;

			// Propiedades de la rejilla
			c4.gridwidth = GridBagConstraints.REMAINDER; // last
			c4.fill = GridBagConstraints.NONE;
			c4.anchor = GridBagConstraints.EAST;
			c4.weightx = 1.0;

			// Situados los demás elementos, colocamos la etiqueta informativa
			textControlsPane4.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder("Tabla K"), BorderFactory
							.createEmptyBorder(5, 5, 5, 5)));

			// Agrupamos los distintos paneles creados (uno en este caso)
			JPanel downPane2 = new JPanel(new BorderLayout());

			downPane2.add(textControlsPane4, BorderLayout.PAGE_START);

			/* ***************************************************************************** */
			/* ***************************************************************************** */
			/* 
			 * ************************ TABLA PTS EN DOWNPANE3
			 * ***************************
			 */
			/* ***************************************************************************** */
			/* ***************************************************************************** */

			modeloPts = new DefaultTableModel();

			tablaPts = new JTable(modeloK);
			for (int i = 0; i < Integer.parseInt(r); i++) {
				modeloPts.addColumn("x" + i);
			}
			modeloPts.addColumn("V.Esperado");
			tablaPts = new JTable(modeloPts);

			tablaPts
					.setPreferredScrollableViewportSize(new Dimension(300, 155));

			JScrollPane scrollPanePts = new JScrollPane(tablaPts);

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
			textControlsPane5.add(scrollPanePts, c5);
			c5.gridwidth = GridBagConstraints.RELATIVE;
			c5.fill = GridBagConstraints.NONE;
			c5.weightx = 0.0;

			// Propiedades de la rejilla
			c5.gridwidth = GridBagConstraints.REMAINDER; // last
			c5.fill = GridBagConstraints.NONE;
			c5.anchor = GridBagConstraints.EAST;
			c5.weightx = 1.0;
			buttonPanel4 = createButtonPanel4();
			textControlsPane5.add(buttonPanel4, c5);
			// Situados los demás elementos, colocamos la etiqueta informativa
			textControlsPane5.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder("Puntos"), BorderFactory
							.createEmptyBorder(5, 5, 5, 5)));

			// Agrupamos los distintos paneles creados (uno en este caso)
			JPanel downPane3 = new JPanel(new BorderLayout());

			downPane3.add(textControlsPane5, BorderLayout.PAGE_START);

			add(leftPane);
			// add(rightPane);
			add(downPane);
			add(downPane2);
			add(downPane3);

		}// fin PanelCompleto

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
			JButton calcularButton = new JButton(CALCULAR);

			// Asigno un nombre para los botones
			agregaraButton.setActionCommand(AGREGARA);
			borraraButton.setActionCommand(BORRARA);
			calcularButton.setActionCommand(CALCULAR);

			// Asigno la acción para los botones Aceptar y Cancelar.
			agregaraButton.addActionListener(this);
			borraraButton.addActionListener(this);
			calcularButton.addActionListener(this);

			p.add(agregaraButton);
			p.add(borraraButton);
			p.add(calcularButton);

			return p;
		}

		protected JComponent createButtonPanel3() {
			// Creo un Jpanel p, que será el panel del botón, con un gridLayout.
			JPanel p = new JPanel(new GridLayout(1, 0));

			// Creo los 2 botones.
			JCheckBox enfriamientoButton = new JCheckBox("Con Enfriamiento");
			JButton graficaButton = new JButton(FICHERO);
			JButton borrarButton = new JButton(BORRAR);


			// Asigno un nombre para los botones
			graficaButton.setActionCommand(FICHERO);
			borrarButton.setActionCommand(BORRAR);
			enfriamientoButton.setActionCommand("Con Enfriamiento");

			// Asigno la acción para los botones Aceptar y Cancelar.
			graficaButton.addActionListener(this);
			borrarButton.addActionListener(this);
			enfriamientoButton.addActionListener(this);
			
			p.add(graficaButton);
			p.add(borrarButton);
			p.add(enfriamientoButton);

			return p;
		}

		protected JComponent createButtonPanel4() {
			// Creo un Jpanel p, que será el panel del botón, con un gridLayout.
			JPanel p = new JPanel(new GridLayout(1, 0));

			// Creo los 2 botones.
			JButton graficaButton = new JButton("Hallar Valor");
			JButton borrarbButton = new JButton(BORRARB);
			JButton agregarbButton = new JButton(AGREGARB);

			// Asigno un nombre para los botones
			graficaButton.setActionCommand("Hallar Valor");
			borrarbButton.setActionCommand(BORRARB);
			agregarbButton.setActionCommand(AGREGARB);

			// Asigno la acción para los botones Aceptar y Cancelar.
			graficaButton.addActionListener(this);
			borrarbButton.addActionListener(this);
			agregarbButton.addActionListener(this);
			p.add(agregarbButton);
			p.add(borrarbButton);
			p.add(graficaButton);

			return p;
		}

		protected ImageIcon createImageIcon(String path) {
			java.net.URL imgURL = PerceptronDatos.class.getResource(path);
			if (imgURL != null) {
				return new ImageIcon(imgURL);
			} else {
				System.err.println("Couldn't find file: " + path);
				return null;
			}
		}

		protected void calcularAdaline(int paso) {
			int i = 0;
			int j = 0;
			int contbucle = 0;
			double pesoAux = 0;
			terminado = false;
			varP = Double.parseDouble(modeloPesos.getValueAt(0, erre + 2)
					.toString());
			varPInicial = varP;
			// Creo el array de pesos, que tendrá erre + 1 (contando theta)
			pesos = new double[erre + 1];
			// Creo el array de k[x] que tendrá en total la suma de las filas de
			// las 2 tablas
			k = new double[modeloA.getRowCount()];
			double[] errores = new double[modeloA.getRowCount()];
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
			double errorSucedido = 0;
			try {
				fichero = new FileWriter("pepeadaline.txt", true);
				pw = new PrintWriter(fichero);
				String cadenaAux = null;
				if (pesosCalc == 1) {
					pw.println("");
					pw.println("***********************************");
					pw.println("***** Cálculo del Adaline ******");
					pw.println("***********************************");
					pw.println("");
				}

				while ((!terminado) && (contbucle < 50000) && (manual < 2)) {
					contbucle++;
					if (manual == 1)
						manual++;
					// Mientras que no hayas recorrido la tabla 1 completa
					// sigue haciendo cálculos para la tabla A (+1)
					while (i < modeloA.getRowCount()) {
						System.out.println(contbucle);
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
						if (k[i] != Double.parseDouble(modeloA.getValueAt(i,
								erre).toString())) {
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
										+ (varP
												* ((Double.parseDouble(modeloA
														.getValueAt(i, erre)
														.toString())) - k[i]) * Double
												.parseDouble(modeloA
														.getValueAt(i, j)
														.toString()));
								pw.println("w"
										+ (j + 1)
										+ " = "
										+ pesoAux
										+ " + ( "
										+ varP
										+ " * "
										+ ((Double
												.parseDouble(modeloA
														.getValueAt(i, erre)
														.toString())) - k[i])
										+ " * " + modeloA.getValueAt(i, j)
										+ ") = " + pesos[j]);
								modeloPesos.setValueAt(pesos[j], pesosCalc,
										j + 1);
								// System.out.println("Valor coordenada A "+j+" "+Integer.parseInt(modeloA.getValueAt(i,j).toString()));
							}
							// Este último es el valor de theta.
							pesoAux = pesos[erre];
							pesos[erre] = pesos[erre]
									+ (varP
											* ((Double.parseDouble(modeloA
													.getValueAt(i, erre)
													.toString())) - k[i]) * -1);
							pw.println("o = "
									+ pesoAux
									+ " + ("
									+ varP
									+ " * "
									+ ((Double.parseDouble(modeloA.getValueAt(
											i, erre).toString())) - k[i])
									+ " * (-1) = " + pesos[erre]);
							pw.println("");
							modeloPesos.setValueAt(pesos[erre], pesosCalc,
									erre + 1);
							pesosCalc++;
							if (enfriamiento) {
								int estanBien = 0;
								double auxiliar = 0;
								for (int aux = 0; aux < modeloA.getRowCount(); aux++) {
									double esperado = 0;
									for (j = 0; j < erre; j++) {
										auxiliar = auxiliar
												+ Double.parseDouble(modeloA
														.getValueAt(aux, j)
														.toString()) * pesos[j];
									}
									auxiliar = auxiliar - pesos[erre];
									esperado = (Double.parseDouble(modeloA
											.getValueAt(aux, erre).toString()));
									auxiliar = ((auxiliar - esperado) * (auxiliar - esperado)) / 2;
									if (auxiliar < ErrorPpal)
										estanBien++;
									System.out.println(errores[aux]);
								}
								varP = varPInicial
										* (1 - Double.parseDouble(Integer
												.toString(estanBien))
												/ (modeloA.getRowCount()));
								modeloPesos.setValueAt(varP, modeloPesos
										.getRowCount() - 1, erre + 2);
							}

							// Y vuelve a inicializar la i a 0, para recalcular
							// desde el principio.

						}

						i++;
						contbucle++;
					}

					for (int aux = 0; aux < modeloA.getRowCount(); aux++) {
						double auxiliar = 0;
						double esperado = 0;
						for (j = 0; j < erre; j++) {
							auxiliar = auxiliar
									+ Double.parseDouble(modeloA.getValueAt(
											aux, j).toString()) * pesos[j];
						}
						auxiliar = auxiliar - pesos[erre];
						esperado = (Double.parseDouble(modeloA.getValueAt(aux,
								erre).toString()));
						errores[aux] = ((auxiliar - esperado) * (auxiliar - esperado)) / 2;
						System.out.println(" REAL " + auxiliar + " ESPERADO ");
						auxiliar = 0;

						System.out.println(errores[aux]);

					}
					double errorTotal = 0;
					for (int aux = 0; aux < modeloA.getRowCount(); aux++)
						errorTotal = errorTotal + errores[aux];
					errorTotal = errorTotal / modeloA.getRowCount();
					// Si aquí llega con la i > 0, significa que realizó todos
					// los cálculos
					// con éxito, y no tiene que volver a repetir nada más.
					ErrorPpal = Double.parseDouble(modeloPesos.getValueAt(0,
							erre + 3).toString());
					System.out.println("error esperado " + ErrorPpal
							+ " error real " + errorTotal);
					System.out.println("error i");
					if (errorTotal < ErrorPpal) {
						// System.out.println(contbucle+" "+i);
						terminado = true;
						for (int aux = 0; aux < modeloA.getRowCount(); aux++)
							modeloA.setValueAt(errores[aux], aux, erre + 1);
					}

					else
						i = 0;

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
			else if (BORRAR.equals(e.getActionCommand())) { // quit
				// dispose();

				for (int i = modeloK.getRowCount() - 1; i >= 0; i--)
					modeloK.removeRow(i);
				for (int i = modeloA.getRowCount() - 1; i >= 0; i--)
					modeloA.removeRow(i);
				for (int i = modeloPesos.getRowCount() - 1; i > 0; i--)
					modeloPesos.removeRow(i);
				pesosCalc = 1;
				terminado = false;
			} 
			if (AGREGARB.equals(e.getActionCommand())) {
				/**
				 * Datos es un array que posee todos los datos a insertar del
				 * usuario
				 */

				modeloPts.addRow(new Vector());
			} else if (BORRARB.equals(e.getActionCommand())) { // quit
				// dispose();
				modeloPts.removeRow(modeloPts.getRowCount() - 1);
			} else if (CALCULAR.equals(e.getActionCommand())) { // quit
				// dispose();
				calcularAdaline(0);
				if (!terminado) {
					JOptionPane.showMessageDialog(textControlsPane,
							"El Adaline no tiene solución", "Error de cálculo",
							JOptionPane.ERROR_MESSAGE);
				} else {
					for (int i = 0; i < modeloA.getRowCount(); i++) {
						modeloK.addRow(new Vector());
						modeloK.setValueAt("k[" + (i + 1) + "]", i, 0);
						modeloK.setValueAt(k[i], i, 1);

					}
					for (int i = 0; i < erre + 1; i++) {
						// modeloPesos.setValueAt(pesos[i],0,i);
					}
					JOptionPane
							.showMessageDialog(
									textControlsPane,
									"El Adaline se calculó correctamente\nTiene los valores en la tabla",
									"Cálculo Exitoso :)",
									JOptionPane.INFORMATION_MESSAGE);
				}
			} else if ("Con Enfriamiento".equals(e.getActionCommand())) { // quit
				// dispose();

				enfriamiento = !enfriamiento;
				System.out.println(enfriamiento);

			} else if ("Hallar Valor".equals(e.getActionCommand())) {
				for (int aux = 0; aux < modeloPts.getRowCount(); aux++) {
					double auxiliar = 0;
					double esperado = 0;
					for (int j = 0; j < erre; j++) {
						auxiliar = auxiliar
								+ Double.parseDouble(modeloPts.getValueAt(aux,
										j).toString()) * pesos[j];
					}
					auxiliar = auxiliar - pesos[erre];
					modeloPts.setValueAt(auxiliar, aux, erre);
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
								if (linea < erre + 3) {
									modeloPesos.setValueAt(sCadena, 0, valor);
									valor++;
								} else {
									if (columna == 0)
										modeloA.addRow(new Vector());
									System.out.println(sCadena + " " + fila
											+ " " + columna);
									modeloA.setValueAt(sCadena, fila, columna);
									if (columna < erre)
										columna++;
									else {
										fila++;
										columna = 0;
									}
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