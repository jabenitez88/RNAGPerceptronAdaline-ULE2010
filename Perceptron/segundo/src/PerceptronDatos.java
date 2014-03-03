
import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

/* Used by InternalFrameDemo.java. */
public class PerceptronDatos extends JInternalFrame
			 {
    static final int xOffset = 30, yOffset = 30;


	public  PerceptronDatos(String r) 
	{

		super("Insertar Datos", 
		      true, //resizable
		      true, //closable
		      false, //maximizable
		      true);//iconifiable
		
			//...Create the GUI and put it in the window...
		

		FormularioInsertarUsuario formulario = new FormularioInsertarUsuario(r);
		add(formulario);

		//Display the window.
		pack();
		setVisible(true);

		//...Then set the window size or call pack...
		setSize(810,400);

		//Set the window's location.
		setLocation(xOffset, yOffset);
    	}

	/** class FormularioInsertarUsuario */
	class FormularioInsertarUsuario extends JPanel implements ActionListener
	{
	    
		/** Declaración de variables */
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String AGREGARA = "Agregar A";
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String BORRARA = "Borrar A";
		
		
		/** Texto Cancelar para el boton cancelar */
		protected static final String AGREGARB = "Agregar B";
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String BORRARB = "Borrar B";
		
		protected static final String BORRAR = "Reiniciar";

		
		/** Texto Aceptar para el boton aceptar */
		protected static final String CALCULAR = "Calcular";
		
		protected static final String MANUAL = "Manual";
		
		/** Texto Aceptar para el boton aceptar */
		protected static final String GRAFICA = "Ver Gráfica";
		
		/** Texto que muestra labelNombre */
	    protected static final String string01		= "Tabla A: ";

		/** Texto que muestra labelApellidos*/
		protected static final String string02		= "Tabla B: ";

		/** Texto que muestra labelPassword */
		protected static final String string03		= "Tabla Pesos: ";

		/** Texto que muestra labelRol */
		protected static final String string04		= "Tabla de k: ";
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
		/** Campo de texto para introducir los Apellidos */
		JTextField text01;
		
		JTextField text02;
		
		JTextField text03;
		
		JTextField text04;
		
		JLabel label01;
		
		JLabel label02;
		
		JLabel label03;
		
		JLabel label04;

		int[] pesos;
		
		int[] k;

		int erre;
		
		double varP = 1;
		
		int iActual = 0;
		
		int jActual = 0;
		
		boolean terminado = false;
		
		boolean enfriamiento = false;


		int pesosCalc = 1;

		/** Panel de control que contiene los botones */
		JComponent buttonPanel;
		
		JComponent buttonPanel2;

		JComponent buttonPanel3;



		/** Método que crea el formulario de Insertar Usuario */
		public FormularioInsertarUsuario(String r) 
		{
			//Establecemos el tipo de layout
			setLayout(new GridLayout(2,2));


			/* Creación de lo campos de texto */

			//Campo de texto de Nombre
			erre = Integer.parseInt(r);
	
			
			/* Creación de las etiquetas... */

			
			/* ***************************************************************************** */
			/* ***************************************************************************** */
			/* ************************ TABLA A EN LEFTPANE  ****************************** */
			/* ***************************************************************************** */
			/* ***************************************************************************** */
	        
	        modeloA = new DefaultTableModel();
	        
	        for(int i=0; i<Integer.parseInt(r); i++)
	        {
	        	modeloA.addColumn(i);
	        }
	        tablaA = new JTable(modeloA);

	        tablaA.setPreferredScrollableViewportSize(new Dimension(300,75));
	        
	        JScrollPane scrollPaneA = new JScrollPane(tablaA);
	        
			label01 = new JLabel(string01);
	        label01.setLabelFor(tablaA);


		    /* Creamos un layout propio para los controles anteriores */
			textControlsPane = new JPanel();
	        GridBagLayout gridbag = new GridBagLayout();
		    GridBagConstraints c = new GridBagConstraints();
			
			//Establecemos la rejilla
			textControlsPane.setLayout(gridbag);

			//Por comodidad, creamos una función que añada los controles a la rejilla
			//y al panel que la contiene...


			c.gridwidth = GridBagConstraints.REMAINDER; 
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1.0;
			textControlsPane.add(scrollPaneA,c);
			c.gridwidth = GridBagConstraints.RELATIVE;
			c.fill = GridBagConstraints.NONE;      
			c.weightx = 0.0;   


			//Propiedades de la rejilla
	        c.gridwidth = GridBagConstraints.REMAINDER; //last
			c.fill = GridBagConstraints.NONE;  
		    c.anchor = GridBagConstraints.EAST;
			c.weightx = 1.0;
			
			//Situados los demás elementos, colocamos la etiqueta informativa
			buttonPanel =  createButtonPanel();
	        textControlsPane.add(buttonPanel, c);
		    textControlsPane.setBorder(
					BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Tabla A"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));  

			//Agrupamos los distintos paneles creados (uno en este caso)
			JPanel leftPane = new JPanel(new BorderLayout());

			leftPane.add(textControlsPane, BorderLayout.PAGE_START);
			//leftPane.add(areaScrollPane, BorderLayout.CENTER);
			
			
			
			
			
			/* Creación de lo campos de texto */

	
			/* ***************************************************************************** */
			/* ***************************************************************************** */
			/* ************************ TABLA B EN RIGHTPANE  ****************************** */
			/* ***************************************************************************** */
			/* ***************************************************************************** */

			//Etiqueta para Usuario
			
			modeloB = new DefaultTableModel();
		        
		    for(int i=0; i<Integer.parseInt(r); i++)
		    {
		      modeloB.addColumn(i);
		    }
		    tablaB = new JTable(modeloB);

		        
	        
	        tablaB.setPreferredScrollableViewportSize(new Dimension(300,75));
	        
	        JScrollPane scrollPaneB = new JScrollPane(tablaB);

			label02 = new JLabel(string02);
	        label02.setLabelFor(tablaB);
	        
			textControlsPane2 = new JPanel();
	        GridBagLayout gridbag2 = new GridBagLayout();
		    GridBagConstraints c2 = new GridBagConstraints();
			
			//Establecemos la rejilla
			textControlsPane2.setLayout(gridbag2);

			//Por comodidad, creamos una función que añada los controles a la rejilla
			//y al panel que la contiene...

			c2.gridwidth = GridBagConstraints.REMAINDER; 
			c2.fill = GridBagConstraints.HORIZONTAL;
			c2.weightx = 1.0;
			textControlsPane2.add(scrollPaneB,c2);
			

			//Propiedades de la rejilla
	        c2.gridwidth = GridBagConstraints.REMAINDER; //last
			c2.fill = GridBagConstraints.NONE;  
		    c2.anchor = GridBagConstraints.EAST;
			c2.weightx = 1.0;
			
			//Situados los demás elementos, colocamos la etiqueta informativa
			buttonPanel2 =  createButtonPanel2();
	        textControlsPane2.add(buttonPanel2, c);
		    textControlsPane2.setBorder(
					BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Tabla B"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));  


			JPanel rightPane = new JPanel(new BorderLayout());

			rightPane.add(textControlsPane2, BorderLayout.PAGE_START);
			//leftPane.add(areaScrollPane, BorderLayout.CENTER);
			
			/* ***************************************************************************** */
			/* ***************************************************************************** */
			/* ************************ TABLA Pesos EN DOWNPANE  *************************** */
			/* ***************************************************************************** */
			/* ***************************************************************************** */
	        
	        modeloPesos = new DefaultTableModel();
	        
	        modeloPesos.addColumn("Iteración Nº");
	        for(int i=0; i<Integer.parseInt(r); i++)
	        {
	        	modeloPesos.addColumn("w"+(i+1));
	        }
	        modeloPesos.addColumn("o");
	        modeloPesos.addColumn("Aprendizaje");
			modeloPesos.addRow(new Vector());
			modeloPesos.setValueAt(0,0,0);
			for(int i=1; i<=Integer.parseInt(r)+1;i++)modeloPesos.setValueAt(1,0, i);
			modeloPesos.setValueAt(varP,0,erre+2);
	        
	        tablaPesos = new JTable(modeloPesos);

	        
	        tablaPesos.setPreferredScrollableViewportSize(new Dimension(300,75));
	        
	        JScrollPane scrollPanePesos = new JScrollPane(tablaPesos);
	        
			label03 = new JLabel(string03);
	        label03.setLabelFor(tablaPesos);

		    /* Creamos un layout propio para los controles anteriores */
			textControlsPane3 = new JPanel();
	        GridBagLayout gridbag3 = new GridBagLayout();
		    GridBagConstraints c3 = new GridBagConstraints();
			
			//Establecemos la rejilla
			textControlsPane3.setLayout(gridbag);

			//Por comodidad, creamos una función que añada los controles a la rejilla
			//y al panel que la contiene...


			c3.gridwidth = GridBagConstraints.REMAINDER; 
			c3.fill = GridBagConstraints.HORIZONTAL;
			c3.weightx = 1.0;
			textControlsPane3.add(scrollPanePesos,c);
			c3.gridwidth = GridBagConstraints.RELATIVE;
			c3.fill = GridBagConstraints.NONE;      
			c3.weightx = 0.0;   


			//Propiedades de la rejilla
	        c3.gridwidth = GridBagConstraints.REMAINDER; //last
			c3.fill = GridBagConstraints.NONE;  
		    c3.anchor = GridBagConstraints.EAST;
			c3.weightx = 1.0;
			
			buttonPanel3 =  createButtonPanel3();
	        textControlsPane3.add(buttonPanel3, c);
			//Situados los demás elementos, colocamos la etiqueta informativa
		    textControlsPane3.setBorder(
					BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Tabla Pesos"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));  

			//Agrupamos los distintos paneles creados (uno en este caso)
			JPanel downPane = new JPanel(new BorderLayout());

			downPane.add(textControlsPane3, BorderLayout.PAGE_START);
			
			
			
			/* ***************************************************************************** */
			/* ***************************************************************************** */
			/* ************************ TABLA K EN DOWNPANE2  ****************************** */
			/* ***************************************************************************** */
			/* ***************************************************************************** */
	        
	        modeloK = new DefaultTableModel();
	        
	        
	        tablaK = new JTable(modeloK);
	        modeloK.addColumn("K");
	        modeloK.addColumn("Valor");

	        tablaK.setPreferredScrollableViewportSize(new Dimension(300,75));
	        
	        JScrollPane scrollPaneK = new JScrollPane(tablaK);
	        
			label04 = new JLabel(string04);
	        label04.setLabelFor(tablaK);

		    /* Creamos un layout propio para los controles anteriores */
			textControlsPane4 = new JPanel();
	        GridBagLayout gridbag4 = new GridBagLayout();
		    GridBagConstraints c4 = new GridBagConstraints();
			
			//Establecemos la rejilla
			textControlsPane4.setLayout(gridbag4);

			//Por comodidad, creamos una función que añada los controles a la rejilla
			//y al panel que la contiene...


			c4.gridwidth = GridBagConstraints.REMAINDER; 
			c4.fill = GridBagConstraints.HORIZONTAL;
			c4.weightx = 1.0;
			textControlsPane4.add(scrollPaneK,c);
			c4.gridwidth = GridBagConstraints.RELATIVE;
			c4.fill = GridBagConstraints.NONE;      
			c4.weightx = 0.0;   


			//Propiedades de la rejilla
	        c4.gridwidth = GridBagConstraints.REMAINDER; //last
			c4.fill = GridBagConstraints.NONE;  
		    c4.anchor = GridBagConstraints.EAST;
			c4.weightx = 1.0;
			
			//Situados los demás elementos, colocamos la etiqueta informativa
		    textControlsPane4.setBorder(
					BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Tabla K"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));  

			//Agrupamos los distintos paneles creados (uno en este caso)
			JPanel downPane2 = new JPanel(new BorderLayout());

			downPane2.add(textControlsPane4, BorderLayout.PAGE_START);
			

			add(leftPane);
			add(rightPane);
			add(downPane);
			add(downPane2);
	        

		}//fin_FormularioInsertarUsuario()

			private void addLabelTextRows(JLabel[] labels,
									  JComponent[] textFields,
									  GridBagLayout gridbag,
									  Container container) 
			{

				GridBagConstraints c = new GridBagConstraints();
				c.anchor = GridBagConstraints.EAST;
				int numLabels = labels.length;

					for (int i = 0; i < numLabels; i++) {
						c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
						c.fill = GridBagConstraints.NONE;      //reset to default
						c.weightx = 0.0;                       //reset to default
						container.add(labels[i], c);

						c.gridwidth = GridBagConstraints.REMAINDER;     //end row
						c.fill = GridBagConstraints.HORIZONTAL;
						c.weightx = 1.0;
						container.add(textFields[i], c);
					}//fin_del_for

			}//fin_addLabelTextRows
    		protected JComponent createButtonPanel() {
    				// Creo un Jpanel p, que será el panel del botón, con un gridLayout.
            		JPanel p = new JPanel(new GridLayout(1,0));

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
        		JPanel p = new JPanel(new GridLayout(1,0));

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
        		JPanel p = new JPanel(new GridLayout(1,0));

        		// Creo los 2 botones.
        		JButton graficaButton = new JButton(GRAFICA);
        		JButton calcularButton = new JButton(CALCULAR);
        		JButton manualButton = new JButton(MANUAL);
        		JButton borrarButton = new JButton(BORRAR);


        		// Asigno un nombre para los botones
        		graficaButton.setActionCommand(GRAFICA);
        		calcularButton.setActionCommand(CALCULAR);
        		manualButton.setActionCommand(MANUAL);
        		borrarButton.setActionCommand(BORRAR);

        		// Asigno la acción para los botones Aceptar y Cancelar.
        		graficaButton.addActionListener(this);
        		calcularButton.addActionListener(this);
        		manualButton.addActionListener(this);
        		borrarButton.addActionListener(this);

        		p.add(graficaButton);
        		p.add(calcularButton);
        		p.add(manualButton);
        		p.add(borrarButton);

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
    		
    		protected void calcularPerceptron(int paso)
    		{
    			int i = 0;
    			int j = 0;
    			int l = 0;
    			int contbucle = 0;
    			int pesoAux = 0;
    			terminado = false;
    			
    			// Creo el array de pesos, que tendrá erre + 1 (contando theta)
    			pesos = new int[erre+1];
    			// Creo el array de k[x] que tendrá en total la suma de las filas de las 2 tablas
    			k = new int[modeloA.getRowCount()+modeloB.getRowCount()];
    			// Inicializo las variables aleatoriamente a 1
    			//for(int m=0; m < erre+1; m++) pesos[m] = 1;
    			
    			int manual = paso;
    			// Si no está terminado y el bucle no se ha ejecutado X veces
    			// sigue realizando cálculos, sino, salte.
    			
    			// Este bucle reinicia la tabla de pesos
    			if (manual != 1) 
    				{
    					for(int aux=modeloPesos.getRowCount()-1;aux>0;aux--) modeloPesos.removeRow(aux);
    					pesosCalc = 1;
    				}
    			for(int aux=0;aux<erre+1;aux++) pesos[aux] = Integer.parseInt(modeloPesos.getValueAt(modeloPesos.getRowCount()-1, aux+1).toString());
    			//System.out.println(pesos[0]+ " "+pesos[1]+ " "+pesos[2]);
    			FileWriter fichero = null;
    	        PrintWriter pw = null;
    	        try
    	        {
    	            fichero = new FileWriter("peperceptron.txt", true);
    	            pw = new PrintWriter(fichero);
    	            String cadenaAux = null;
    	            if(pesosCalc == 1)
    	            {
    	            	pw.println("");
	    	            pw.println("***********************************");
	    	            pw.println("***** Cálculo del Perceptrón ******");
	    	            pw.println("***********************************");
	    	            pw.println("");
    	            }

        			while((!terminado) && (contbucle<50) && (manual < 2))
        			{	
        				contbucle++;
        				if (manual == 1) manual++;
        				// Mientras que no hayas recorrido la tabla 1 completa
        				// sigue haciendo cálculos para la tabla A (+1)
    	    			while(i < modeloA.getRowCount())
    	    			{
    	    				k[i] = 0;
    	    				// Calculamos k[i], la j representa el número de peso que multiplica por
    	    				// la coordenada x,y,z..... que la obtiene de la tablaA posición [i,j]
    	    				cadenaAux = "k["+(i+1)+"] = ";
    	    				for(j = 0; j < erre; j++)
    	    				{
    	    					k[i] = k[i] + Integer.parseInt(modeloA.getValueAt(i,j).toString())*pesos[j];
    	    					if(j<erre-1) cadenaAux = cadenaAux + modeloA.getValueAt(i,j).toString() + "*" + pesos[j] +" + ";
    	    					else cadenaAux = cadenaAux + modeloA.getValueAt(i,j).toString() + "*" + pesos[j];
    	    				}
    	    				// Finalmente, resta el valor de theta.
    	    				k[i] = k[i] - pesos[erre];
    	    				if (pesos[erre]<0)
    	    					pw.println(cadenaAux+" + "+(pesos[erre]*-1)+ " = "+k[i]);
    	    				else 
    	    					pw.println(cadenaAux+" - "+pesos[erre]+" = "+k[i]);
    	    				//System.out.println("La k["+i+"] vale "+k[i]);
    	    				
    	    				// Al ser la tabla A, si el valor es menor o igual a 0, recalcula los pesos.
    	    				if (k[i] <= 0) 
    	    				{	
    	    					pw.println("");
    	    					pw.println("El valor de k["+(i+1)+"] no es correcto, debemos recalcular:");
    	    					pw.println("Iteración Nº \n"+modeloPesos.getRowCount());
    	    					pw.println("");
        						modeloPesos.addRow(new Vector());
        						modeloPesos.setValueAt(modeloPesos.getRowCount()-1,pesosCalc,0);
    	    					for(j = 0; j < erre; j++)
    	    					{
    	    						pesoAux = pesos[j];
    	    						pesos[j] = pesos[j] + (varP * 1 * Integer.parseInt(modeloA.getValueAt(i,j).toString())); 
    	    						pw.println("w"+(j+1)+" = "+pesoAux+" + (1 * 1 * "+modeloA.getValueAt(i,j)+") = "+pesos[j]);
    	    						modeloPesos.setValueAt(pesos[j],pesosCalc,j+1);
    	    						//System.out.println("Valor coordenada A "+j+" "+Integer.parseInt(modeloA.getValueAt(i,j).toString()));
    	    					}
    	    					// Este último es el valor de theta.
    	    					pesoAux = pesos[erre];
    	    					pesos[erre] = pesos[erre] + (varP * 1 * (-1));
    	    					pw.println("o = "+pesoAux+" + 1 * 1 * (-1) = "+pesos[erre]);
    	    					pw.println("");
    	    					modeloPesos.setValueAt(pesos[erre],pesosCalc,erre+1);
    	    					pesosCalc++;
    	    					// Y vuelve a inicializar la i a 0, para recalcular desde el principio.
    	    					i = 0;
    	    				}
    	    				else {
    	    					i++;
    	    					if(enfriamiento) 
    	    						{
    	    							varP = varP - i/(modeloA.getRowCount()+modeloB.getRowCount());
    	    							modeloPesos.setValueAt(varP,modeloPesos.getRowCount()-1,erre+2);
    	    						}
    	    				}
    	    			}
    	    	
    	    			
    	    			while((l < modeloB.getRowCount()) && (i != 0))
    	    			{
    	    				k[i+l] = 0;
    	    				// Calculamos k[i+l], la j representa el número de peso que multiplica por
    	    				// la coordenada x,y,z..... que la obtiene de la tablaA posición [l,j]
    	    				cadenaAux = "k["+(i+1+l)+"] = ";
    	    				for(j = 0; j < erre; j++)
    	    				{
    	    					k[i+l] = k[i+l] + Integer.parseInt(modeloB.getValueAt(l,j).toString())*pesos[j];
    	    					if(j<erre-1) cadenaAux = cadenaAux + modeloB.getValueAt(l,j).toString() + "*" + pesos[j] +" + ";
    	    					else cadenaAux = cadenaAux + modeloB.getValueAt(l,j).toString() + "*" + pesos[j];

    	    				}
    	    				// Finalmente, resta el valor de theta.
    	    				k[i+l] = k[i+l] - pesos[erre];
    	    				if (pesos[erre]<0)
    	    					pw.println(cadenaAux+" + "+(pesos[erre]*-1)+ " = "+k[i+l]);
    	    				else 
    	    					pw.println(cadenaAux+" - "+pesos[erre]+" = "+k[i+l]);
    	    			
    	    				//System.out.println("La k["+(i+l)+"] vale "+k[i+l]);
    	    				
    	    				// Al ser la tabla B, si el valor es mayor o igual a 0, recalcula los pesos.
    	    				if (k[i+l] >= 0) 
    	    				{	
    	    					pw.println("");
    	    					pw.println("El valor de k["+(i+1+l)+"] no es correcto, debemos recalcular:");
    	    					pw.println("Iteración Nº \n"+modeloPesos.getRowCount());
    	    					pw.println("");

        						modeloPesos.addRow(new Vector());
        						modeloPesos.setValueAt(modeloPesos.getRowCount()-1,pesosCalc,0);
    	    					for(j = 0; j < erre; j++)
    	    					{
    	    						pesoAux = pesos[j];
    	    						pesos[j] = pesos[j] + (varP * (-1) * Integer.parseInt(modeloB.getValueAt(l,j).toString()));
    	    						pw.println("w"+(j+1)+" = "+pesoAux+" + (1 * -1 * "+modeloB.getValueAt(l,j)+") = "+pesos[j]);
    	    						modeloPesos.setValueAt(pesos[j],pesosCalc,j+1);
    	    	
    	    					}
    	    					pesoAux = pesos[erre];
    	    					pesos[erre] = pesos[erre] + (varP * (-1) * (-1));
    	    					pw.println("o = "+pesoAux+" + 1 * (-1) * (-1) = "+pesos[erre]);
    	    					pw.println("");
    	    					modeloPesos.setValueAt(pesos[erre],pesosCalc,erre+1);
    	    					pesosCalc++;
    	    					
    	    					// Y vuelve a inicializar la i a 0, para recalcular desde el principio.
    	    					l = 0;
    	    					i = 0;
    	    				}
    	    				else l++;
    	    			}
    	    			
    	    			// Si aquí llega con la i > 0, significa que realizó todos los cálculos
    	    			// con éxito, y no tiene que volver a repetir nada más.
    	    			if(i != 0)
    	    				{	
    	    					//System.out.println(contbucle+" "+i);
    	    					terminado = true;
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
		    public void actionPerformed(ActionEvent e) 
		    {
				if (AGREGARA.equals(e.getActionCommand()))
				{	
					/** Datos es un array que posee todos los datos a insertar del usuario */
					
					modeloA.addRow(new Vector());
				}
				else if(BORRARA.equals(e.getActionCommand()))
				{ //quit
					//dispose();
					modeloA.removeRow(modeloA.getRowCount()-1);
				}
				
				else if (AGREGARB.equals(e.getActionCommand()))
				{	
					/** Datos es un array que posee todos los datos a insertar del usuario */
					
					modeloB.addRow(new Vector());
				}
				else if(BORRARB.equals(e.getActionCommand()))
				{ //quit
					//dispose();
					modeloB.removeRow(modeloB.getRowCount()-1);
				}
				
				else if(CALCULAR.equals(e.getActionCommand()))
				{ //quit
					//dispose();
					calcularPerceptron(0);
					if(!terminado)
					{
						JOptionPane.showMessageDialog(textControlsPane,"El perceptrón no tiene solución","Error de cálculo",JOptionPane.ERROR_MESSAGE);
					}
					else 
					{
						for(int i=modeloK.getRowCount()-1;i>=0;i--) modeloK.removeRow(i);

						for(int i=0;i<modeloA.getRowCount()+modeloB.getRowCount();i++)
						{
							modeloK.addRow(new Vector());
							modeloK.setValueAt("k["+(i+1)+"]",i,0);
							modeloK.setValueAt(k[i],i,1);

						}
						/*for(int i=0;i<erre+1;i++)
						{
							modeloPesos.setValueAt(pesos[i],0,i);
						}*/
						JOptionPane.showMessageDialog(textControlsPane, "El perceptrón se calculó correctamente\nTiene los valores en la tabla", "Cálculo Exitoso :)", JOptionPane.INFORMATION_MESSAGE);
					}				
				}
				else if(MANUAL.equals(e.getActionCommand()))
				{ //quit
					//dispose();
					if(!terminado) 
					{
						calcularPerceptron(1);
					}
					else
					{
						for(int i=modeloK.getRowCount()-1;i>=0;i--) modeloK.removeRow(i);

						for(int i=0;i<modeloA.getRowCount()+modeloB.getRowCount();i++)
						{
							modeloK.addRow(new Vector());
							modeloK.setValueAt("k["+(i+1)+"]",i,0);
							modeloK.setValueAt(k[i],i,1);

						}
						/*for(int i=0;i<erre+1;i++)
						{
							modeloPesos.setValueAt(pesos[i],0,i);
						}*/
						JOptionPane.showMessageDialog(textControlsPane, "El perceptrón se calculó correctamente\nTiene los valores en la tabla", "Cálculo Exitoso :)", JOptionPane.INFORMATION_MESSAGE);
					}				
				}
				else if(BORRAR.equals(e.getActionCommand()))
				{ //quit
					//dispose();
					
					for(int i=modeloK.getRowCount()-1;i>=0;i--) modeloK.removeRow(i);
					for(int i=modeloA.getRowCount()-1;i>=0;i--) modeloA.removeRow(i);
					for(int i=modeloB.getRowCount()-1;i>=0;i--) modeloB.removeRow(i);
					for(int i=modeloPesos.getRowCount()-1;i>0;i--) modeloPesos.removeRow(i);
					pesosCalc = 1;
								
				}
				else if(GRAFICA.equals(e.getActionCommand()))
				{ //quit
					if(erre==2)
					{
						double[][] coordenadas = new double[modeloA.getRowCount()+modeloB.getRowCount()][2];
						double[] pesosGrafica = new double[3];
						int totalCoord = 0;
						int i=0;
						for(i=0;i<modeloA.getRowCount();i++)
						{
							coordenadas[i][0] = Double.parseDouble(modeloA.getValueAt(i, 0).toString());
							coordenadas[i][1] = Double.parseDouble(modeloA.getValueAt(i, 1).toString());
						}
						for(int j=0;j<modeloB.getRowCount();j++)
						{
							coordenadas[i+j][0] = Double.parseDouble(modeloB.getValueAt(j, 0).toString());
							coordenadas[i+j][1] = Double.parseDouble(modeloB.getValueAt(j, 1).toString());
							totalCoord = i+j;
						}
						pesosGrafica[0] = pesos[0];
						pesosGrafica[1] = pesos[1];
						pesosGrafica[2] = pesos[2];
						PerceptronGrafica grafica = new PerceptronGrafica(coordenadas,pesos,totalCoord);
						grafica.setVisible(true);
					}
					else JOptionPane.showMessageDialog(textControlsPane,"Sólo puede verse gráfica trabajando en R2","Gráfica",JOptionPane.ERROR_MESSAGE);

				}

		    }
	}

}