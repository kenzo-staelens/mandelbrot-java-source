package logica;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Complex(BigDecimal real, BigDecimal complex, int precision) {
    public Complex(BigDecimal real, BigDecimal complex, int precision) {
        this.precision = precision;
        this.real = real.setScale(precision, RoundingMode.HALF_DOWN);
        this.complex = complex.setScale(precision, RoundingMode.HALF_DOWN);
    }

    public static BigDecimal absSquare(Complex c) {
        return c.real.multiply(c.real).add(c.complex.multiply(c.complex));
    }

    public static Complex square(Complex c) {
        return new Complex(c.real.multiply(c.real).subtract(c.complex.multiply(c.complex)), c.real.multiply(c.complex).multiply(BigDecimal.valueOf(2)), c.precision);
    }

    public static Complex add(Complex c1, Complex c2) {
        return new Complex(c1.real.add(c2.real), c1.complex.add(c2.complex), c1.precision);
    }

    @Override
    public String toString() {
        return String.format("%f + %fi", real, complex);
    }
}