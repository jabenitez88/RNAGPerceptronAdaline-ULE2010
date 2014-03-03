/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrador
 */
import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import java.awt.geom.Line2D;
import java.awt.event.*;

import javax.swing.Timer;

public class Plano extends Canvas {
	public int inser = 0, numser = 0, n = 0;
	public int[] x, y;
	public double MaxX, MaxY;
	public double maxx, maxy, minx, miny;
	public int posy[], posx[];
	public double etiqx[], etiqy[], cx[], cy[];
	public int numd, Xo, Yo;
	private int pos_ejex, pos_ejey;
	private int CoordenadasXH[][], CoordenadasYH[][];
	private int CoordenadasXV[][], CoordenadasYV[][];
	private double pesos[];
	private double x1, x0 = -999;
	private boolean pintalaLinea;
	private Line2D linea;
	private Image _buffer = null;
	int color = 0;

	/**
	 * Constructor de la clase:
	 * 
	 * @param cantidad
	 *            : indica el numero de marcas que tendra el plano, es decir las
	 *            etiquetas
	 */
	public Plano(int cantidad) {
		this.setBackground(Color.white);
		numd = cantidad;
		MaxX = 500;
		MaxY = 500;
		etiqx = new double[numd];
		etiqy = new double[numd];
		posx = new int[numd];
		posy = new int[numd];
		inser = 0;
		numser = 500;

	}

	/**
	 * Asigna los limites de los ejes, Xf, Xi, Yf, Yi
	 * 
	 * @param MaximoX
	 * @param MaximoY
	 * @param MinimoX
	 * @param MinimoY
	 */
	public void Limites(double MaximoX, double MaximoY, double MinimoX,
			double MinimoY) {
		minx = MinimoX;
		miny = MinimoY;
		maxx = MaximoX;
		maxy = MaximoY;
		this.EtiquetasX();
		this.EtiquetasY();
		this.LineasHorizontales();
		this.LineasVerticales();
	}

	public void LineasHorizontales() {
		int Cantidad = numd;
		CoordenadasXH = new int[Cantidad][2];
		CoordenadasYH = new int[Cantidad][2];
		for (int i = 0; i < Cantidad; i++) {
			CoordenadasXH[i][0] = posx[i];
			CoordenadasXH[i][1] = 0;
			CoordenadasYH[i][0] = posx[i];
			CoordenadasYH[i][1] = (int) MaxY;
		}
	}

	public void LineasVerticales() {
		int Cantidad = numd;
		CoordenadasXV = new int[Cantidad][2];
		CoordenadasYV = new int[Cantidad][2];
		for (int i = 0; i < Cantidad; i++) {
			CoordenadasXV[i][0] = 0;
			CoordenadasXV[i][1] = posy[i];
			CoordenadasYV[i][0] = (int) MaxX;
			CoordenadasYV[i][1] = posy[i];
		}
	}

	public void Punto(double xo, double yo, int puntos) {
		this.SerieX(xo);
		this.SerieY(yo);
		color = puntos;
	}

	/**
	 * Crea la Coordenada X de cada punto, para despues mostrarlo en pantalla
	 * 
	 * @param x0
	 */
	void SerieX(double x0) {
		x[inser] = (int) ((x0 - minx) / (maxx - minx) * (MaxX));
		cx[inser] = x0;
	}

	/**
	 * Crea la Coordenada Y de cada punto, para despues mostrarlo en pantalla
	 * 
	 * @param y0
	 */
	void SerieY(double y0) {
		if (inser >= numser)
			return;

		y[inser] = (int) ((y0 - miny) / (maxy - miny) * (MaxY));
		cy[inser] = y0;

		inser++;
	}

	/**
	 * Establce las coordenadas del origen en el plano
	 */
	public void Origen() {
		Yo = (int) ((-miny) / (maxy - miny) * (MaxY));
		Xo = (int) ((-minx) / (maxx - minx) * (MaxX));
		repaint();
	}

	/**
	 * Las etiquetas de cada eje que conforman el plano aqui las del eje X
	 * 
	 * @param x0
	 * @return
	 */
	public void EtiquetasX() {
		double inc, aux;
		int i = 0;
		if (inser == 0) {
			x = new int[numser];
			cx = new double[numser];
		}
		inc = (maxx - minx) / numd;
		if (Math.abs(maxx - minx) > 1.0) {
			if (Math.abs(inc) < 1.0)
				inc = (double) 1.0;
			else
				inc = Math.round(inc + 0.5);
		}
		// inc = (maxx - minx) / numd;
		for (i = 0; i < numd; i++) {
			aux = minx + inc * i;
			aux = (Math.round(aux * 10000.0)) / 10000.0;
			etiqx[i] = aux;
			posx[i] = (int) ((etiqx[i] - minx) / (maxx - minx) * (MaxX));
		}

		if (minx < 0 && maxx > 0)
			pos_ejex = (int) (-minx / (maxx - minx) * (MaxX));
	}

	/**
	 * Las etiquetas de cada eje que conforman el plano aqui las del eje Y
	 * 
	 * @param x0
	 * @return
	 */
	public void EtiquetasY() {
		double inc, aux;
		int i = 0;
		if (inser == 0) {
			y = new int[numser];
			cy = new double[numser];
		}

		inc = (maxy - miny) / numd;

		if (Math.abs(maxy - miny) > 1.0) {
			if (Math.abs(inc) < 1.0)
				inc = (double) 1.0;
			else
				inc = Math.round(inc + 0.5);
		}

		// inc = (maxy - miny)/numd;

		for (i = 0; i < numd; i++) {
			aux = miny + inc * i;

			aux = (Math.round(aux * 100000.0)) / 100000.0;
			etiqy[i] = aux;
			posy[i] = (int) ((etiqy[i] - miny) / (maxy - miny) * (MaxY));

		}
		if (miny < 0 && maxy > 0)
			pos_ejey = (int) (MaxY - (int) (-miny / (maxy - miny) * (MaxY)));
	}

	public void Recalcular() {
		for (int i = 0; i < inser; i++) {
			x[i] = (int) ((cx[i] - minx) / (maxx - minx) * (MaxX));
			y[i] = (int) ((cy[i] - miny) / (maxy - miny) * (MaxY));
		}
		repaint();
	}

	public void Restablecer() {
		pos_ejex = 0;
		pos_ejey = 0;
	}

	public void Reiniciar() {
		inser = 0;
		repaint();
	}

	public void paint(Graphics g) {
		int i, j, mm;

		g.setColor(new Color(216, 225, 251));
		// Dibujar Lineas Verticales
		for (i = 0; i < CoordenadasXH.length; i++)
			g.drawLine(CoordenadasXH[i][0], CoordenadasXH[i][1],
					CoordenadasYH[i][0], CoordenadasYH[i][1]);
		// Dibujar Lineas Horizontales
		for (i = 0; i < CoordenadasXV.length; i++)
			g.drawLine(CoordenadasXV[i][0], (int) MaxY - CoordenadasXV[i][1],
					CoordenadasYV[i][0], (int) MaxY - CoordenadasYV[i][1]);

		// dibujar unas cuantas figuras
		g.setColor(Color.black);

		g.drawLine(pos_ejex, 0, pos_ejex, 500);
		g.drawLine(0, pos_ejey, 500, pos_ejey);
		for (i = 0; i < numd; i++) {
			// g.drawString(Double.toString(etiqx[i]), 50 + posx[i], 20 );
			g.drawString(Redondear(etiqx[i]), posx[i] - 2, pos_ejey + 20);
			g.drawLine(posx[i], pos_ejey - 5, posx[i], pos_ejey + 5);

			g.drawString(Redondear(etiqy[i]), pos_ejex - 20, (int) MaxY
					- posy[i]);
			g.drawLine(pos_ejex - 5, (int) MaxY - posy[i], pos_ejex + 5,
					(int) MaxY - posy[i]);
		}

		if (inser > numser)
			mm = numser;
		else
			mm = inser;
		for (j = 0; j < mm; j++) {
			if (j < color)
				g.setColor(Color.BLUE);
			else
				g.setColor(Color.RED);
			// g.setColor(new
			// Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));
			g.fillOval(x[j] - 3, (int) MaxY - y[j] - 3, 6, 6);
			g.drawString("K" + (j + 1) + "(" + cx[j] + "," + cy[j] + ")",
					x[j] - 30, (int) MaxY - y[j] - 10);
		}
		g.setColor(Color.black);
		g.drawOval(Xo - 3, (int) MaxY - Yo - 3, 6, 6);

		if (x0 != -999) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.MAGENTA);
			g2.setStroke(new BasicStroke(3.0f));
			// g2 = (Graphics2D) g;
			// g2.drawLine(pos_ejex-200, pos_ejey-200-(int)x1*50,
			// (int)x0*50+pos_ejex+200, pos_ejey+200);
			// Point lineStart = new Point(pos_ejex-200,
			// pos_ejey-200-(int)(x1*50));
			// Point lineEnd = new Point((int)(x0*50)+pos_ejex+200,
			// pos_ejey+200);
			/* PUNTOS LIMITE */
			Point lineStart = new Point(250, 250 - (int) (x1 * 25));
			Point lineEnd = new Point(250 + (int) (x0 * 25), 250);
			/*
			 * Point lineStart = new Point(0, 0); Point lineEnd = new
			 * Point(500,500); if((x1==0) && (x0==0)) { lineStart = new Point(0,
			 * 0); lineEnd = new Point(500,500);
			 * 
			 * } else { //lineStart = new Point(250-(int)x1*25, 0); //lineEnd =
			 * new Point(500-(int)x0*25,500); }
			 */
			if (pesos[2] == 0) {
				lineStart = new Point(0, 500);
				lineEnd = new Point(500, 0);
			} else if ((x1 < 0) && (x0 > 0)){
				lineStart = new Point(
						500,
						250 - ((int) ((pesos[2] - pesos[0] * 10) / pesos[1]) * 25));
				lineEnd = new Point(
						(int) (((pesos[2] - pesos[1] * -10) / pesos[0]) * 25) + 250,
						500);
			} else if ((x1 > 0) && (x0 < 0)) {
				lineStart = new Point(
						500,
						250 - ((int) ((pesos[2] - pesos[0] * 10) / pesos[1]) * 25));
				lineEnd = new Point(
						(int) (((pesos[2] - pesos[1] * -10) / pesos[0]) * 25) + 250,
						500);

			} 
			else if ((x1 < 0 ) && (x0 < 0)) {
				lineStart = new Point(0, 250 - ((int) ((pesos[2] - pesos[0]
				                                 						* -10) / pesos[1]) * 25));
				                                 				lineEnd = new Point(
				                                 						(int) (((pesos[2] - pesos[1] * -10) / pesos[0]) * 25) + 250,
				                                 						500);
			}else {
				lineStart = new Point(0, 250 - ((int) ((pesos[2] - pesos[0]
						* -10) / pesos[1]) * 25));
				lineEnd = new Point(
						(int) (((pesos[2] - pesos[1] * -10) / pesos[0]) * 25) + 250,
						500);
			}
			// lineStart = new Point(250, 250-(int)(x1*25));
			// lineEnd = new Point(250+(int)(x0*25), 250);
			linea = new Line2D.Double(lineStart, lineEnd);
			g2.draw(linea);

			// Point lineStart = new Point(150, 150-(int)(x1*25));
			// Point lineStart = new Point(150, 0);
			/*
			 * int e1 = 500,y1 = -500; int e2 = -500, y2 = 500; int valor = 0;
			 * 
			 * if(pesos[0] == 0) { } else if(pesos[1] == 0) {
			 * 
			 * } else{
			 * g2.drawLine(e1,((int)((pesos[2]-pesos[0]*e1)/pesos[1])*-1)
			 * ,(int)((pesos[2]-y1*pesos[1])/pesos[0]),y1);
			 * //g2.drawLine(e1+250,
			 * ((int)((pesos[2]-pesos[0]*e1)/pesos[1])*-1)-250
			 * ,(int)((pesos[2]-y2*pesos[1])/pesos[0])-250,y2+250);
			 * //g2.drawLine
			 * (e2-250,(int)((pesos[2]-pesos[0]*e2)/pesos[1])*-1,(int
			 * )((pesos[2]-y1*pesos[1])/pesos[0])-250,y1);
			 * //g2.drawLine(e2-250,(
			 * int)((pesos[2]-pesos[0]*e2)/pesos[1])*-1,(int
			 * )((pesos[2]-y2*pesos[1])/pesos[0])-250,y2);
			 * 
			 * }
			 */

		}
		// g.drawLine((int)x0*50+pos_ejex,pos_ejey-(int)x1*50,0,pos_ejey+200);
		// g.drawLine(pos_ejex-100, pos_ejey-100, pos_ejex+100, pos_ejey+300);
	}

	public void pintaLinea(double[] p) {
		// x0 = (p[2]-(p[0]*500))/p[1];
		x0 = p[2] / p[0];
		x1 = p[2] / p[1];
		pesos = p;
		System.out.println(x0 + " " + x1);
		repaint();

	}

	public String Redondear(double val) {
		String aux = "";
		double auxi = 0;
		auxi = Math.round(val * 100);
		aux = Integer.toString((int) (auxi / 100));
		return aux;
	}
}
