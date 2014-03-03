/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrador
 */
import java.awt.*;


public class Plano extends Canvas
{
    public int inser =0, numser=0, n=0;
    public int[] x, y;
    public double MaxX, MaxY;
    public double maxx, maxy, minx, miny;
    public int posy[], posx[];
    public double etiqx[], etiqy[], cx[], cy[];
    public int numd, Xo, Yo;
    private int pos_ejex, pos_ejey;
    private int CoordenadasXH[][],CoordenadasYH[][];
    private int CoordenadasXV[][],CoordenadasYV[][];
    private int pesos[];
    
    /**
     * Constructor de la clase:
     * @param cantidad: indica el numero de marcas que tendra el plano, es decir las etiquetas
     */
    public Plano(int cantidad, int[] pesosP)
    {
        this.setBackground(Color.white);
        numd=cantidad;
        MaxX=500;
        MaxY=500;
        etiqx = new double[numd];
        etiqy = new double[numd];
        posx  = new int[numd];
        posy  = new int[numd];
        inser=0;
        numser=500;
        pesos = new int[3];
        pesos = pesosP;
    }
    /**
     * Asigna los limites de los ejes, Xf, Xi, Yf, Yi
     * @param MaximoX
     * @param MaximoY
     * @param MinimoX
     * @param MinimoY
     */
    public void Limites(double MaximoX,double MaximoY,double MinimoX,double MinimoY)
    {
        minx=MinimoX;
        miny=MinimoY;
        maxx=MaximoX;
        maxy=MaximoY;
        this.EtiquetasX();
        this.EtiquetasY();
        this.LineasHorizontales();
        this.LineasVerticales();
    }
    public void LineasHorizontales()
    {
        int Cantidad=numd;
        CoordenadasXH=new int[Cantidad][2];
        CoordenadasYH=new int[Cantidad][2];
        for(int i=0; i<Cantidad; i++)
        {
            CoordenadasXH[i][0]=posx[i];
            CoordenadasXH[i][1]=0;
            CoordenadasYH[i][0]=posx[i];
            CoordenadasYH[i][1]=(int)MaxY;
        }
    }
    public void LineasVerticales()
    {
        int Cantidad=numd;
        CoordenadasXV=new int[Cantidad][2];
        CoordenadasYV=new int[Cantidad][2];
        for(int i=0; i<Cantidad; i++)
        {
            CoordenadasXV[i][0]=0;
            CoordenadasXV[i][1]=posy[i];
            CoordenadasYV[i][0]=(int)MaxX;
            CoordenadasYV[i][1]=posy[i];
        }
    }
    public void Punto(double xo, double yo)
    {
        this.SerieX(xo);
        this.SerieY(yo);
    }
    /**
     * Crea la Coordenada X de cada punto, para despues mostrarlo en pantalla
     * @param x0
     */
   void SerieX(double x0)
   {
       x[inser] = (int) ( (x0 - minx) / (maxx - minx) * (MaxX));
       cx[inser] = x0;
   }
   /**
    * Crea la Coordenada Y de cada punto, para despues mostrarlo en pantalla
    * @param y0
    */
   void SerieY(double y0)
   {
     if(inser >= numser) return;

       y[inser] = (int) ((y0 - miny)/(maxy - miny)*(MaxY));
       cy[inser] = y0;

      inser++;
   }
   /**
    * Establce las coordenadas del origen en el plano
    */
   public void Origen()
   {
       Yo=(int) ((-miny)/(maxy - miny)*(MaxY));
       Xo=(int) ( (-minx)/(maxx - minx)*(MaxX));
       repaint();
   }
   /**
    * Las etiquetas de cada eje que conforman el plano aqui las del eje X
    * @param x0
    * @return
    */
   public void EtiquetasX()
   {
     double inc, aux;
     int i=0;
     if (inser==0)
     {
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
       //inc = (maxx - minx) / numd;
       for (i = 0; i < numd; i++) {
         aux = minx + inc * i;
         aux = (Math.round(aux * 10000.0)) / 10000.0;
         etiqx[i] = aux;
         posx[i] = (int) ( (etiqx[i] - minx) / (maxx - minx) * (MaxX));
       }
     
       if (minx < 0 && maxx > 0)
         pos_ejex = (int) ( -minx / (maxx - minx) * (MaxX));
   }
   /**
    * Las etiquetas de cada eje que conforman el plano aqui las del eje Y
    * @param x0
    * @return
    */
   public void EtiquetasY()
   {
     double inc, aux;
     int i=0;
     if(inser == 0)
     {
       y = new int[numser];
       cy = new double[numser];
     }

       inc = (maxy - miny)/numd;

       if( Math.abs(maxy - miny) > 1.0)
       {
          if(Math.abs(inc) < 1.0) inc = (double)1.0;
          else inc = Math.round(inc+0.5);
       }

       //inc = (maxy - miny)/numd;

       for(i=0; i<numd; i++)
       {
         aux = miny + inc*i;

         aux = (Math.round(aux*100000.0))/100000.0;
         etiqy[i] = aux;
         posy[i]  = (int) ((etiqy[i] - miny)/(maxy - miny)*(MaxY));
      
       }
       if(miny < 0 && maxy > 0)
         pos_ejey = (int)(MaxY -(int)(-miny /(maxy - miny)*(MaxY)));
   }
   public void Recalcular()
   {
       for(int i=0; i<inser; i++)
       {
           x[i]=(int) ( (cx[i] - minx) / (maxx - minx) * (MaxX));
           y[i]=(int) ( (cy[i] - miny) / (maxy - miny) * (MaxY));
       }
       repaint();
   }
   public void Restablecer()
   {
       pos_ejex=0;
       pos_ejey=0;
   }
   public void Reiniciar()
   {
       inser=0;
       repaint();
   }
   public void paint(Graphics g)
   {
       int i, j, mm;

        g.setColor(new Color(216,225,251));
        //Dibujar Lineas Verticales
        for(i=0; i<CoordenadasXH.length; i++)
            g.drawLine(CoordenadasXH[i][0], CoordenadasXH[i][1],CoordenadasYH[i][0], CoordenadasYH[i][1]);
        //Dibujar Lineas Horizontales
        for(i=0; i<CoordenadasXV.length; i++)
            g.drawLine(CoordenadasXV[i][0], (int)MaxY-CoordenadasXV[i][1],CoordenadasYV[i][0], (int)MaxY-CoordenadasYV[i][1]);

       // dibujar unas cuantas figuras
       g.setColor(Color.black);

       g.drawLine(pos_ejex, 0, pos_ejex, 500);
       g.drawLine(0, pos_ejey, 500, pos_ejey);
       for(i=0; i<numd; i++)
       {
         //g.drawString(Double.toString(etiqx[i]), 50 + posx[i], 20 );
         g.drawString(Redondear(etiqx[i]), posx[i]-2, pos_ejey + 20 );
         g.drawLine(posx[i], pos_ejey-5,  posx[i], pos_ejey+5);

         g.drawString(Redondear(etiqy[i]), pos_ejex-20, (int)MaxY-posy[i]);
         g.drawLine(pos_ejex-5, (int)MaxY-posy[i], pos_ejex+5, (int)MaxY-posy[i]);
        }

       if(inser> numser) mm = numser;
       else mm = inser;
       for(j=0; j<mm; j++)
       {
           g.setColor(new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255)));
           g.fillOval(x[j]-3, (int)MaxY-y[j]-3,  6, 6);
           g.drawString("K"+(j+1)+"("+cx[j]+","+cy[j]+")", x[j]-30, (int)MaxY-y[j]-10);
        }
        g.setColor(Color.black);
        g.drawOval(Xo-3, (int)MaxY-Yo-3, 6, 6);
        pesos[0] = (int) ( (pesos[0] - minx) / (maxx - minx) * (MaxX));
        pesos[1] = (int) ((pesos[1]- miny)/(maxy - miny)*(MaxY));
        pesos[2] = (int) ((pesos[2]- miny)/(maxy - miny)*(MaxY));
        g.drawLine(pesos[0],pesos[1], 500, pos_ejey+pesos[2]);
        g.drawLine(0, pos_ejey+100, 700, pos_ejey+100);
        System.out.println("HOLA");
   }

   public String Redondear(double val)
   {
       String aux="";
       double auxi=0;
       auxi=Math.round(val*100);
       aux=Integer.toString((int)(auxi/100));
       return aux;
   }
}
