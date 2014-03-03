import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

/** class MiLogin 
 * Esta clase se encarga de mostrar la ventana de Login
 * para que el usuario introduzca usuario y contraseña.
 * */
public class PerceptronMain extends JPanel 
	{

		/** Declaracion de variables */
	    /** framePpal es el frame principal */
		static JFrame framePpal;

		/** Vacio es una constante que guarda el carácter vacío */
		protected static final String VACIO	= "";

		/** Texto Aceptar para el boton aceptar */
		protected static String ACEPTAR = "Perceptrón";
		/** Texto Cancelar para el boton cancelar */
		protected static String CANCELAR = "Adaline";

		/** Etiqueta de Informacion */
		JLabel labelInfo;

		/** Panel que contiene los botones Aceptar y Cancelar */
        JComponent buttonPanel;
        	
        /** Panel que contiene los TextField de usuario y contraseña y el botón de aceptar */
		JPanel textControlsPane = new JPanel();
		
		static JTextField erreTrabajo = new JTextField();
		JLabel erreTrabajoLbl = new JLabel("R en que trabaja:");

		public PerceptronMain() 
		{	
			//setMinimumSize(new java.awt.Dimension(200,400));  
			//setMaximumSize(new java.awt.Dimension(200,400));  
			setPreferredSize(new java.awt.Dimension(250,200));
			//Establecemos el tipo de layout
			setLayout(new BorderLayout());

			//Etiqueta de informacion
	        labelInfo = new JLabel("Elija una de las dos opciones a ejecutar");
	        erreTrabajo.setDocument(new controlarLontigud(2,true)); 

		   	// labelAccion.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));

			/* Creacion de lo campos de texto */

			/* Creacion de las etiquetas... */

			// Crea el panel de botones
			buttonPanel =  createButtonPanel();

		    /* Creamos un layout propio para los controles anteriores */
	       	GridBagLayout gridbag = new GridBagLayout();
		    GridBagConstraints c = new GridBagConstraints();
			
			//Establecemos la rejilla
			textControlsPane.setLayout(gridbag);

			//Por comodidad, creamos una funcion que aniada los controles a la rejilla
			//y al panel que la contiene...
	        c.gridwidth = GridBagConstraints.REMAINDER; //last
		    c.anchor = GridBagConstraints.EAST;
			c.weightx = 10.0;
			
			//Agregamos la etiqueta labelInfo al panel.
	        textControlsPane.add(labelInfo,c);
	        
			JLabel[] labels = {erreTrabajoLbl};
		    JTextField[] textFields = {erreTrabajo};
		    //Colocamos en el panel las etiquetas y los textfields.
			addLabelTextRows(labels, textFields, gridbag, textControlsPane);

        
			//Agregamos al panel los botones.
			textControlsPane.add(buttonPanel,c);


			//Situados los demas elementos, colocamos la etiqueta informativa

		    textControlsPane.setBorder(
					BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("RNAG - Perceptrón | Adaline"),
                                BorderFactory.createEmptyBorder(5,5,5,5)));  

		    // A�adimos el panel en la zona "LINE_START" al principio...
			add(textControlsPane, BorderLayout.LINE_START);
            setLocation(200, 200);

		}//fin_MiLogin()
		/** Con este procedimiento cre el panel de botones "Aceptar y Cancelar" */
    		protected static JComponent createButtonPanel() {
			// Creo un Jpanel p, que será el panel del botón, con un gridLayout.
        		JPanel p = new JPanel(new GridLayout(1,0));

			// Creo los 2 botones.
           		ImageIcon iconoAceptar 	= createImageIcon("images/001_06.png");
        		ImageIcon iconoCancelar = createImageIcon("images/001_05.png");
        		JButton aceptarButton = new JButton(ACEPTAR,iconoAceptar);
        		JButton cancelarButton = new JButton(CANCELAR,iconoCancelar);

			// Asigno un nombre para los botones
        		aceptarButton.setActionCommand(ACEPTAR);
        		cancelarButton.setActionCommand(CANCELAR);

			// Asigno la acción para los botones Aceptar y Cancelar.
        		aceptarButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{	// Si ha sido presionado el OK entonces entra aquí.
						if (ACEPTAR.equals(e.getActionCommand())) 
						{ 	// Creo un Objeto de tipo Mysql, para poder autentificarme en la base
							// de datos.
						
							Perceptron frame = new Perceptron(erreTrabajo.getText());
							frame.addWindowListener(new WindowListener(){
								public void windowClosed(WindowEvent arg0) {
									framePpal.setVisible(true);
						            erreTrabajo.setText("");
					
								}
								public void windowActivated(WindowEvent arg0){}
						        public void windowClosing(WindowEvent arg0) {
						           	framePpal.setVisible(true);
						           	erreTrabajo.setText("");

						            }
						        public void windowDeactivated(WindowEvent arg0) {}
						        public void windowDeiconified(WindowEvent arg0) {}
						        public void windowIconified(WindowEvent arg0) {}
						        public void windowOpened(WindowEvent arg0){}
							});		
							frame.setVisible(true);
							framePpal.setVisible(false);
							

						}
					}
				});
        		cancelarButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (CANCELAR.equals(e.getActionCommand())) 
						{
							Adaline frame = new Adaline(erreTrabajo.getText());
							frame.addWindowListener(new WindowListener(){
								public void windowClosed(WindowEvent arg0) {
									framePpal.setVisible(true);
						            erreTrabajo.setText("");
					
								}
								public void windowActivated(WindowEvent arg0){}
						        public void windowClosing(WindowEvent arg0) {
						           	framePpal.setVisible(true);
						           	erreTrabajo.setText("");

						            }
						        public void windowDeactivated(WindowEvent arg0) {}
						        public void windowDeiconified(WindowEvent arg0) {}
						        public void windowIconified(WindowEvent arg0) {}
						        public void windowOpened(WindowEvent arg0){}
							});		
							frame.setVisible(true);
							framePpal.setVisible(false);
						}


					}//fin_actionPerformed
				});
        		// Agrego los botones al panel.
        		p.add(aceptarButton);
        		p.add(cancelarButton);

        		return p;
    			}
    		protected static ImageIcon createImageIcon(String path) {
      	        java.net.URL imgURL = PerceptronMain.class.getResource(path);
      	        if (imgURL != null) {
      	            return new ImageIcon(imgURL);
      	        } else {
      	            System.err.println("Couldn't find file: " + path);
      	            return null;
      	        }
      	    }
    	/** Esta función sirve para colocar en un panel las etiquetas y los textfield de forma ordenada */
		private void addLabelTextRows(JLabel[] labels,
							      JTextField[] textFields,
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
		 /** Esta función es la que crea la interfaz gráfica realmente*/
		  private static void createAndShowGUI() {
				//Create and set up the window.
				framePpal = new JFrame("Perceptrón | Adaline - JABA");
				framePpal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				//Add content to the window.
				framePpal.add(new PerceptronMain());

				//Display the window.
				framePpal.pack();
				framePpal.setVisible(true);
			}

		/** Función principal */
		public static void main(String[] args) throws Exception
		{	  // Creo el objeto MiMysql que usaré para conectar a la base de datos 
			  
			  SwingUtilities.invokeLater(new Runnable() {
			     public void run() {
					UIManager.put("swing.boldMetal", Boolean.FALSE);
					createAndShowGUI();
				 }
			  });
			
		}
}
