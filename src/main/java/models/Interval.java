package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.MathUtils;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Interval {
    private double lowerLimit, upperLimit;

    public double getLength() {
        return upperLimit - lowerLimit;
    }

    @Override
    public String toString() {
        return "[" + MathUtils.round(lowerLimit) + "; " + MathUtils.round(upperLimit) + "]";
    }
}
