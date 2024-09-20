package logica;

import java.math.BigDecimal;
import java.math.MathContext;

public class Helper {
    public static double map(double val, double val_min, double val_max, double out_min, double out_max){
        return (val-val_min)/(val_max-val_min)*(out_max-out_min)+out_min;
    }

    public static BigDecimal mapBD(BigDecimal val, BigDecimal val_min, BigDecimal val_max, BigDecimal out_min, BigDecimal out_max){
        return val.subtract(val_min).divide(val_max.subtract(val_min), MathContext.DECIMAL32).multiply(out_max.subtract(out_min)).add(out_min);
    }
}
