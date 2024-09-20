package presentatie;

import javax.swing.*;
import java.awt.*;

public class Colorband {
    static int i=0;
    private final Color[] cArray;
    public Colorband(float p1, Color c1,float p2, Color c2,float p3, Color c3,float p4, Color c4,float p5, Color c5,int len){
        Color[] colorpoints = new Color[]{c1,c2,c3,c4,c5,c1};
        float[] coloridx = new float[]{p1,p2,p3,p4,p5,100};
        cArray = new Color[len];
        for(int premap =0,i=0;premap<cArray.length;premap++){
            float x = premap/(float)len*100;
            if(x>coloridx[i+1]) i++;
            float t = (x-coloridx[i])/(coloridx[i+1]-coloridx[i]);
            this.cArray[premap]=new Color(
                    (int)(colorpoints[i].getRed()+(colorpoints[i+1].getRed()-colorpoints[i].getRed())*t),
                    (int)(colorpoints[i].getGreen()+(colorpoints[i+1].getGreen()-colorpoints[i].getGreen())*t),
                    (int)(colorpoints[i].getBlue()+(colorpoints[i+1].getBlue()-colorpoints[i].getBlue())*t)
            );
        }
    }

    public Colorband(){
        this(0, new Color(0,7,100),16,new Color(32,107,203),
                42,new Color(237, 255, 255),64.25f,new Color(255, 170, 0),
                85.75f,new Color(0, 2, 0),256);
    }

    public Colorband(int len){
        this(0, new Color(0,0,0),16,new Color(32,107,203),
                42,new Color(237, 255, 255),64.25f,new Color(255, 170, 0),
                85.75f,new Color(0, 2, 0),len);
    }

    public Color getColor(int i){
        return cArray[i];
    }

    public static void main(String[] args) {//test
        int w = 256;
        Colorband c = new Colorband();
        JFrame f = new JFrame(){
            @Override
            public void paint(Graphics g){
                for(int premap=0;premap<w;premap++){
                    g.setColor(c.cArray[premap]);
                    g.drawLine(premap,0,premap,100);
                }
            }
        };
        f.setSize(w,100);
        f.setUndecorated(true);
        f.setVisible(true);
    }
}
