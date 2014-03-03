
import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.*;
import java.awt.*;
import java.util.Vector;

/* Used by InternalFrameDemo.java. */
public class PerceptronGrafica extends JFrame
			 {
    static final int xOffset = 30, yOffset = 30;


	public  PerceptronGrafica(double[][] coordenadas, int[] w,int totalCoord) 
	{

		super("Gráfica");//iconifiable
		
			//...Create the GUI and put it in the window...
		


		Plano PC = new Plano(20,w);
		PC.Limites(10, 10, -10, -10);
        PC.Origen();
        System.out.println(totalCoord+1);
		for(int i=0;i<totalCoord+1;i++)
		{
			PC.Punto(coordenadas[i][0],coordenadas[i][1]);
			System.out.println(coordenadas[i][0]+" "+coordenadas[i][1]);
		}
		System.out.println(w[0]+" "+w[1]+" "+w[2]);
	
		PC.repaint();

        
		add(PC);

		//Display the window.
		pack();
		setVisible(true);

		//...Then set the window size or call pack...
		setSize(510,510);

		//Set the window's location.
		setLocation(xOffset, yOffset);
    	}

}