package stadancing;

import java.util.Random;
import java.awt.*;
import java.io.*;

import javax.swing.*;
 
class SpaceDance extends JFrame {
 
  int RES_X = 640;
  int RES_Y = 480;
 
  public SpaceDance() {
    super("SpaceDance");
    setBounds(0, 0, RES_X, RES_Y);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container con = this.getContentPane();
    con.setBackground(Color.black);
    GCanvas canvas = new GCanvas();
    con.add(canvas);
    setVisible(true);
  }
 
  public static void main(String[] args) {
    new SpaceDance();
  }
}
 
class Star {
 
  int posx, posy, posz; // posición
  double spdx, spdy, spdz; // velocidad
  double accx, accy, accz; // aceleración
  int mass; // masa
}
 
class GCanvas extends Canvas implements Runnable {
	String linea1="";
	String linea2="";
	String lineam="";
  int RES_X = 640;
  int RES_Y = 480;
  int NUM_STARS =1;
  int tiempo_pausa = 100;
  Star[] stars = new Star[NUM_STARS];
  Thread t;
Star s=new Star();
  public GCanvas() {
    t = new Thread(this);
    t.start();
  }
 
  @Override
  public void paint(Graphics g) {
    // dibujar las estrellas
    g.setColor(Color.black);
    g.fillRect(0, 0, RES_X, RES_Y);
    for (int i = 0; i < NUM_STARS; i++) {
      if (stars[i] != null) {
        int tam = (int) stars[i].mass;
        if (tam < 2) {
          g.setColor(Color.white);
        } else if (tam < 4) {
          g.setColor(Color.yellow);
        } else if (tam < 6) {
          g.setColor(Color.blue);
        } else {
          g.setColor(Color.red);
        }
        g.fillOval(stars[i].posx, stars[i].posy, tam, tam);
      }
    }
    g.setColor(Color.GREEN);
    g.fillOval(s.posx, s.posy, 20, 20);
  }
 
  public void initStars() {
    // inicializar estrellas aleatoriamente
	s.posx=200;
    s.posy=200;
    s.posz=200;
    s.accx=0;
    s.accy=0;
    s.accz=0;
    s.mass=50;
	Random rnd = new Random();
    for (int i = 0; i < NUM_STARS; i++) {
      stars[i] = new Star();
      stars[i].posx = rnd.nextInt(RES_X);
      stars[i].posy = rnd.nextInt(RES_Y);
      stars[i].posz = rnd.nextInt(RES_Y);
      stars[i].accx = 0;
      stars[i].accy = 0;
      stars[i].accz = 0;
      stars[i].mass = rnd.nextInt(10);
    }
  }
 
  @Override
  public void run() {
    initStars();
    do {
    	s.accx =0;
        s.accy =0;
        s.accz =0;
    	for (int i = 0; i < NUM_STARS; i++) {
        // cálculo de las aceleraciones
    		
    	stars[i].accx = 0;
        stars[i].accy = 0;
        stars[i].accz = 0;  
    	int dx = s.posx - stars[i].posx;
        int dy = s.posy - stars[i].posy;
        int dz = s.posz - stars[i].posz;
        double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (d != 0) {
        double f = (stars[i].mass*s.mass) / d;
        s.accx += f * (stars[i].posx-s.posx) / d;
        s.accy += f * (stars[i].posy-s.posy) / d;
        s.accz += f * (stars[i].posz-s.posz) / d;
        stars[i].accx += f * (s.posx - stars[i].posx) / d;
        stars[i].accy += f * (s.posy - stars[i].posy) / d;
        stars[i].accz += f * (s.posz - stars[i].posz) / d;
        }
    	
    	for (int j = 0; j < NUM_STARS; j++) {
          if (i != j) {
             dx = stars[i].posx - stars[j].posx;
             dy = stars[i].posy - stars[j].posy;
             dz = stars[i].posz - stars[j].posz;
            // distancia
             d = Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (d != 0) {
              // fuerza
              double f = (stars[i].mass*stars[j].mass) / d;
              // aceleraciÛn
              stars[i].accx += f * (stars[j].posx - stars[i].posx) / d;
              stars[i].accy += f * (stars[j].posy - stars[i].posy) / d;
              stars[i].accz += f * (stars[j].posz - stars[i].posz) / d;
            }
          }
        }
      }
 
      // cálculo de las velocidades
      s.spdx+=s.accx;
      s.spdy+=s.accy;
      s.spdz+=s.accz;
      for (int i = 0; i < NUM_STARS; i++) {
        stars[i].spdx += stars[i].accx;
        stars[i].spdy += stars[i].accy;
        stars[i].spdz += stars[i].accz;
      }
 
      // cálculo de las coordenadas
      s.posx+=(int)s.spdx;
      s.posy+=(int)s.spdy;
      s.posz+=(int)s.spdz;
      for (int i = 0; i < NUM_STARS; i++) {
        stars[i].posx += (int) stars[i].spdx;
        stars[i].posy += (int) stars[i].spdy;
        stars[i].posz += (int) stars[i].spdz;
      }
      
      // Guardado en archivos
      linea1+=("\n"+stars[0].mass+"\t"+stars[0].posx+"\t"+stars[0].posy+"\t"+stars[0].posz+"\t"+stars[0].accx+"\t"+stars[0].accy+"\t"+stars[0].accz);
      if(NUM_STARS!=1)
      linea2+=("\n"+stars[1].mass+"\t"+stars[1].posx+"\t"+stars[1].posy+"\t"+stars[0].posz+"\t"+stars[1].accx+"\t"+stars[1].accy+"\t"+stars[1].accz);
      lineam+=("\n"+s.mass+"\t"+s.posx+"\t"+s.posy+"\t"+s.posz+"\t"+s.accx+"\t"+s.accy+"\t"+s.accz);
      try {
		guardar("m1.txt",linea1);
		if(NUM_STARS!=1)
		guardar("m2.txt",linea2);
		guardar("masam.txt",lineam);
	} catch (IOException e1) {
		e1.printStackTrace();
	}
      
      repaint();
      try {
        Thread.sleep(tiempo_pausa);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } while (true);
  }
  
  public void guardar(String arch,String line) throws IOException

  {
        FileWriter fw;
        fw= new FileWriter(arch);
        fw.write(line);
        fw.close();
  }
  
}
 

