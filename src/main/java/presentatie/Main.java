package presentatie;

import logica.Complex;
import logica.Helper;
import logica.Mandelbrot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Main {
    static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int w = 267;//sum abs default bounds*100
    static int h = 244;
    static final double extraScale=screenSize.getHeight()/(h*2);//h*3
    static final int iterations = 10;//100
    static final int defaultprecision = 5;//50
    static Mandelbrot m;
    static Colorband c;

    static int[] dragstart = new int[]{-1,-1};

    public static void main(String[] args) {
        w = (int)(w*extraScale);
        h = (int)(h*extraScale);
        m = new Mandelbrot(w,h,iterations,defaultprecision);
        c=new Colorband(iterations+1);
        createFrame(w,h);
    }

    private static void createFrame(int w, int h){
        JFrame frame = new JFrame("mandelbrot");
        InfoFrame internalInfo = new InfoFrame(m,frame);

        JFrame infoframe = new JFrame("infoFrame");
        infoframe.setContentPane(internalInfo.getMainpanel());
        infoframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        infoframe.pack();
        infoframe.setVisible(true);

        JPanel p = new JPanel(){
            @Override
            public void paint(Graphics g) {

                c=new Colorband(m.getIterations()+1);
                long t1 = System.currentTimeMillis();
                for(int j=0;j<this.getHeight();j++){
                    for(int i=0;i<this.getWidth();i++){
                        //int color = (int)Helper.map(m.inSet(new Complex(BigDecimal.valueOf(i),BigDecimal.valueOf(j),precision)),0,m.getIterations(),255,0);
                        //g.setColor(new Color(color,color,color));
                        g.setColor(c.getColor(m.inSet(new Complex(BigDecimal.valueOf(i),BigDecimal.valueOf(j),m.getPrecision()))));
                        g.drawLine(i,j,i+1,j);

                    }
                }
                internalInfo.setCornersAndZoom(System.currentTimeMillis()-t1);
            }
        };

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);//reset
                m.boundsX[0] = BigDecimal.valueOf(-2.10);
                m.boundsX[1] = BigDecimal.valueOf(0.57);
                m.boundsY[0] = BigDecimal.valueOf(-1.22);
                m.boundsY[1] = BigDecimal.valueOf(1.22);
                frame.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                dragstart[0] = e.getX();
                dragstart[1] = e.getY();
            }
        });

        frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                BigDecimal xMoved = Helper.mapBD(BigDecimal.valueOf(e.getX()-dragstart[0]),BigDecimal.valueOf(0),BigDecimal.valueOf(frame.getWidth()) ,BigDecimal.ZERO,m.boundsX[1].subtract(m.boundsX[0])).setScale(m.getPrecision(), RoundingMode.HALF_DOWN);
                BigDecimal yMoved = Helper.mapBD(BigDecimal.valueOf(e.getY()-dragstart[1]),BigDecimal.valueOf(0),BigDecimal.valueOf(frame.getHeight()),BigDecimal.ZERO,m.boundsY[1].subtract(m.boundsY[0])).setScale(m.getPrecision(), RoundingMode.HALF_DOWN);

                m.boundsX[0] = m.boundsX[0].subtract(xMoved).setScale(m.getPrecision(),RoundingMode.HALF_DOWN);
                m.boundsX[1] = m.boundsX[1].subtract(xMoved).setScale(m.getPrecision(),RoundingMode.HALF_DOWN);
                m.boundsY[0] = m.boundsY[0].subtract(yMoved).setScale(m.getPrecision(),RoundingMode.HALF_DOWN);
                m.boundsY[1] = m.boundsY[1].subtract(yMoved).setScale(m.getPrecision(),RoundingMode.HALF_DOWN);
                dragstart[0]=e.getX();
                dragstart[1]=e.getY();
                internalInfo.setMouseCoords(BigDecimal.valueOf(e.getX()), BigDecimal.valueOf(e.getY()),m.inSet(
                        new Complex(BigDecimal.valueOf(e.getX()),BigDecimal.valueOf(e.getY()),m.getPrecision())
                ));
                frame.repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e){
                internalInfo.setMouseCoords(BigDecimal.valueOf(e.getX()), BigDecimal.valueOf(e.getY()),m.inSet(
                        new Complex(BigDecimal.valueOf(e.getX()),BigDecimal.valueOf(e.getY()),m.getPrecision())
                ));
            }
        });

        frame.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                e.consume();//prevent double triggers
                BigDecimal cx = Helper.mapBD(BigDecimal.valueOf(e.getX()), BigDecimal.valueOf(0), BigDecimal.valueOf(frame.getWidth()), m.boundsX[0], m.boundsX[1]);
                BigDecimal cy = Helper.mapBD(BigDecimal.valueOf(e.getY()), BigDecimal.valueOf(0), BigDecimal.valueOf(frame.getHeight()), m.boundsY[0], m.boundsY[1]);
                BigDecimal dx = (m.boundsX[1].subtract(m.boundsX[0]));
                BigDecimal dy = (m.boundsY[1].subtract(m.boundsY[0]));
                if(e.getWheelRotation()==-1) {
                    dx = dx.divide(BigDecimal.valueOf(4),MathContext.DECIMAL32);
                    dy = dy.divide(BigDecimal.valueOf(4),MathContext.DECIMAL32);
                }
                m.boundsX[0] = cx.subtract(dx).setScale(m.getPrecision(),RoundingMode.HALF_DOWN);
                m.boundsX[1] = cx.add(dx).setScale(m.getPrecision(),RoundingMode.HALF_DOWN);
                m.boundsY[0] = cy.subtract(dy).setScale(m.getPrecision(),RoundingMode.HALF_DOWN);
                m.boundsY[1] = cy.add(dy).setScale(m.getPrecision(),RoundingMode.HALF_DOWN);
                frame.repaint();
            }
        });

        frame.setUndecorated(false);
        frame.setVisible(true);

        p.setPreferredSize(new Dimension(w,h));
        //frame.add(p);
        frame.setContentPane(p);
        frame.pack();
        frame.setLocation(100,0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}