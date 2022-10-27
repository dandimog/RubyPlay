package distributions;

import lombok.AllArgsConstructor;
import lombok.Data;
import models.Interval;
import utils.MathUtils;

@AllArgsConstructor
@Data
public class NormalDistribution implements Distribution {
    private final double mean, variance;
    @Override
    public double getExpectedValue() {
        return mean;
    }
    @Override
    public double getDispersion() {
        return variance;
    }

    public Interval getConfidenceInterval(double confidenceLvl) {
        double quantileValue = MathUtils.quantileFuncValues.get(confidenceLvl / 2);
        return new Interval(
                mean - quantileValue * Math.sqrt(variance),
                mean + quantileValue * Math.sqrt(variance)
        );
    }
}
