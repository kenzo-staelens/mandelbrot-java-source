package presentatie;

import logica.Mandelbrot;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class InfoFrame {
    private JTextField iterations;
    private JButton process;
    private JPanel mainpanel;
    private JLabel l;
    private JTextField precision;
    BigDecimal defaultx;
    BigDecimal defaulty;
    BigDecimal zoom = BigDecimal.ONE;
    Mandelbrot m;
    BigDecimal[] cornersAndZoom;
    BigDecimal[] mouseCoords = new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO};

    JFrame mandelframe;
    int ilimit;
    long calctime;

    public InfoFrame(Mandelbrot m, JFrame mandelframe){
        this.m = m;
        this.defaultx = m.boundsX[1].subtract(m.boundsX[0]);
        this.defaulty = m.boundsY[1].subtract(m.boundsY[0]);
        cornersAndZoom = new BigDecimal[]{m.boundsX[0],m.boundsY[0],
                m.boundsX[1],m.boundsY[1],zoom};
        iterations.setText(Integer.toString(m.getIterations()));
        precision.setText(Integer.toString(m.getPrecision()));

        this.mandelframe = mandelframe;

        process.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    m.setIterations(Integer.parseInt(iterations.getText()));
                    m.setPrecision(Integer.parseInt(precision.getText()));
                    mandelframe.repaint();
                }catch(Exception ignored){}
            }
        });
        setCornersAndZoom(0);
    }

    public void setMandelframe(JFrame mandel){
        this.mandelframe = mandel;
    }

    public JPanel getMainpanel() {
        return mainpanel;
    }

    public void setLabelText(){
        l.setText(String.format("<html>%s, %s<br>%s, %s<br>%s%%<br>%,dms<br>%s %s; %d</html>",m.boundsX[0], m.boundsY[0],m.boundsX[1],m.boundsY[1],zoom, calctime,mouseCoords[0],mouseCoords[1],ilimit));
    }

    public void setMouseCoords(BigDecimal x, BigDecimal y, int ilimit){
        mouseCoords[0]=x;
        mouseCoords[1]=y;
        this.ilimit = ilimit;
        setLabelText();
    }

    public void setCornersAndZoom(long calctime) {
        zoom = defaultx.divide(m.boundsX[1].subtract(m.boundsX[0]), RoundingMode.HALF_DOWN).multiply(BigDecimal.valueOf(100));//only one side needed
        cornersAndZoom[0]=m.boundsX[0];
        cornersAndZoom[1]=m.boundsY[0] ;
        cornersAndZoom[2]=m.boundsX[1];
        cornersAndZoom[3]=m.boundsY[1];
        cornersAndZoom[4]=zoom;
        this.calctime = calctime;
        setLabelText();
    }

    public static void main(String[] args) {
        JFrame infoframe = new JFrame("infoFrame");
        infoframe.setContentPane(new InfoFrame(new Mandelbrot(300,300,10,1),null).mainpanel);
        infoframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        infoframe.pack();
        infoframe.setVisible(true);
    }
}
