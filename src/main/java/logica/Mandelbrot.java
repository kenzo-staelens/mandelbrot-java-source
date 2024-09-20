package logica;

import java.math.BigDecimal;

public class Mandelbrot{
    private final int width;
    private final int height;
    private int iterations;
    private int precision;

    public BigDecimal[] boundsX = new BigDecimal[]{BigDecimal.valueOf(-2.10), BigDecimal.valueOf(0.57)};//defaults
    public BigDecimal[] boundsY = new BigDecimal[]{BigDecimal.valueOf(-1.22), BigDecimal.valueOf(1.22)};//defaults

    public Mandelbrot(int w, int h, int maxIterations, int precision){
        this.width = w;
        this.height = h;
        this.iterations = maxIterations;
        this.precision = precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getPrecision(){
        return this.precision;
    }

    public void setIterations(int iterations){
        this.iterations = iterations;
    }

    public int getIterations(){return this.iterations;}

    public int inSet(Complex pixel){
        Complex c = new Complex(
                Helper.mapBD(pixel.real(), BigDecimal.valueOf(0), BigDecimal.valueOf(this.width), this.boundsX[0], this.boundsX[1]),
                Helper.mapBD(pixel.complex(), BigDecimal.valueOf(0), BigDecimal.valueOf(this.height), this.boundsY[0], this.boundsY[1]),
                precision
        );

        Complex z = c;

        for(int i=0;i<iterations;i++){
            if(Complex.absSquare(z).compareTo(BigDecimal.valueOf(4)) > 0) return i;
            z = Complex.add(Complex.square(z),c);
        }
        return iterations;
        //return 0;
    }
}