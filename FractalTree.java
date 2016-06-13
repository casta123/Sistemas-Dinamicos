
    import java.awt.Color;
    import java.awt.Graphics;
    import java.util.Random;
    import javax.swing.JFrame;
     
    public class FractalTree extends JFrame {
     
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public FractalTree() {
            super("Naturaleza fractal");
            setSize(800, 800);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
     
        private void drawTree(Graphics g, int x1, int y1, double angle, int depth) {
          if (depth > 0) {
            Random rand = new Random();
            int branch_step=5+rand.nextInt(5);
            int x2=x1+(int)(Math.cos(Math.toRadians(angle))*(depth*branch_step));
            int y2=y1+(int)(Math.sin(Math.toRadians(angle))*(depth*branch_step));
            g.setColor(new Color(100-(depth*5),250-(depth*10),10));
            int[] xpoints=new int[4];
            int[] ypoints=new int[4];
            xpoints[0]=x1;
            ypoints[0]=y1;
            xpoints[1]=x1+depth;
            ypoints[1]=y1;
            xpoints[2]=x2+depth;
            ypoints[2]=y2;
            xpoints[3]=x2;
            ypoints[3]=y2;
            g.fillPolygon(xpoints, ypoints, 4);
            int rangle=rand.nextInt(30);
            int langle=rand.nextInt(30);
            drawTree(g, x2, y2, angle-langle, depth-1);
            drawTree(g, x2, y2, angle+rangle, depth-1);
          }
        }
     
        @Override
        public void paint(Graphics g) {
            drawTree(g, 400, 600, -90, 12);
        }
     
        public static void main(String[] args) {
            new FractalTree().setVisible(true);
        }
    }
