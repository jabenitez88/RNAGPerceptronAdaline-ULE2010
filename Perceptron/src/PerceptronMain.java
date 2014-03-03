import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;


public class PerceptronMain extends JPanel 
	{

	    /** framePpal es el frame principal */
		static JFrame framePpal;
		
		protected static final String VACIO	= "";
		protected static String ACEPTAR = "Calcular";
		protected static String CANCELAR = "Salir";

		/** Etiqueta de Informacion */
		JLabel labelInfo;

        JComponent buttonPanel;
        	
		JPanel textControlsPane = new JPanel();
		
		static JTextField erreTrabajo = new JTextField();
		JLabel erreTrabajoLbl = new JLabel("R en que trabaja:");

		public PerceptronMain() 
		{	
			  
			setPreferredSize(new java.awt.Dimension(250,200));
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation(screenSize.width/2,screenSize.height/2);
			//Establecemos el tipo de layout
			setLayout(new BorderLayout());

			//Etiqueta de informacion
	        labelInfo = new JLabel("Elija una de las dos opciones a ejecutar");
	        erreTrabajo.setDocument(new controlarLontigud(2,true)); 

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

		}//fin_MiLogin()
    		protected static JComponent createButtonPanel() {
			// Creo un Jpanel p, que será el panel del botón, con un gridLayout.
        		JPanel p = new JPanel(new GridLayout(1,0));

			// Creo los 2 botones.
           		
        		JButton aceptarButton = new JButton(ACEPTAR);
        		JButton cancelarButton = new JButton(CANCELAR);

			// Asigno un nombre para los botones
        		aceptarButton.setActionCommand(ACEPTAR);
        		cancelarButton.setActionCommand(CANCELAR);

			// Asigno la acción para los botones Aceptar y Cancelar.
        		aceptarButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{	// Si ha sido presionado el OK entonces entra aquí.
						if (ACEPTAR.equals(e.getActionCommand())) 
						{ 	
						
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
							System.exit(0);
						}


					}//fin_actionPerformed
				});
        		// Agrego los botones al panel.
        		p.add(aceptarButton);
        		p.add(cancelarButton);

        		return p;
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
		{	  
			  
			  SwingUtilities.invokeLater(new Runnable() {
			     public void run() {
					UIManager.put("swing.boldMetal", Boolean.FALSE);
					createAndShowGUI();
				 }
			  });
			
		}
}
