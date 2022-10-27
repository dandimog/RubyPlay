package distributions;

import lombok.AllArgsConstructor;
import lombok.Data;
import models.Interval;

@AllArgsConstructor
@Data
public class UniformDistribution implements Distribution {
    private final Interval interval;
    @Override
    public double getExpectedValue() {
        return (interval.getUpperLimit() + interval.getLowerLimit()) / 2;
    }
    @Override
    public double getDispersion() {
         return Math.pow(interval.getUpperLimit() - interval.getLowerLimit(), 2) / 12;
    }
}
