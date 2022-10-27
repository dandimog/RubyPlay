package utils;

import models.Interval;

import java.util.Arrays;
import java.util.stream.Stream;

public class DataUtils {

    public static double[] generateDataSet(Interval interval, int numberElem) {
        return generateDataSet(interval, numberElem, 3);
    }

    public static double[] generateDataSet(Interval interval, int numberElem, int valsAfterComma) {
        return Stream.generate(() -> MathUtils.nextDoubleWithinInterval(interval))
                .limit(numberElem)
                .mapToDouble(Double::doubleValue)
                .map(val -> MathUtils.round(val, valsAfterComma))
                .toArray();
    }

    public static double[] filterDataSet(Interval interval, double[] data) {
        return Arrays.stream(data).filter(element ->
                MathUtils.isWithinInterval(element, interval)).toArray();
    }

}
