import distributions.Distribution;
import distributions.NormalDistribution;
import distributions.UniformDistribution;
import models.Interval;
import models.UserInput;
import utils.DataUtils;
import utils.MapUtils;
import utils.MathUtils;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner reader = new Scanner(System.in);
    private static PrintWriter writer;

    static {
        try {
            writer = new PrintWriter("./output/result.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UserInput input = input();

        output("Number of generated values within interval: " + input.getNumberElem());
        Interval tolValInterval = new Interval(
                MathUtils.getLowerLimit(input.getBasicVal(), input.getTolVal()),
                MathUtils.getUpperLimit(input.getBasicVal(), input.getTolVal())
        );
        output("Interval limits: " + tolValInterval);

        Distribution uniform = new UniformDistribution(tolValInterval);
        output("The expected value is: " + uniform.getExpectedValue());
        output("The variance is: " + MathUtils.round(uniform.getDispersion()));
        output("The standard deviation is: " + MathUtils.round(Math.sqrt(uniform.getDispersion())));

        double[] dataSet = DataUtils.generateDataSet(tolValInterval, input.getNumberElem());
        output("Generated values:");
        Arrays.stream(dataSet).boxed().forEach(e -> output(e.toString()));

        output("The mean is: " + MathUtils.round(MathUtils.mean(dataSet)));

        Map<Double, Integer> frequencyMap = MapUtils.getFrequencyMap(dataSet);
        output("Frequency of appearance map:");
        frequencyMap.forEach((k, v) -> output(k + " --> " + v));

        output("");

        double tolVal2 = MathUtils.round(getTolVal2(input.getTolVal(), 0.1));
        output("Tolerance value 2 (tolVal2): " + tolVal2);

        Interval tolVal2Interval = new Interval(
                MathUtils.getLowerLimit(input.getBasicVal(), tolVal2),
                MathUtils.getUpperLimit(input.getBasicVal(), tolVal2)
        );

        output("Interval limits for filtered range: " + tolVal2Interval);

        UniformDistribution uniform_2 = new UniformDistribution(tolVal2Interval);
        output("The expected value is: " + uniform_2.getExpectedValue());
        output("The variance is: " + MathUtils.round(uniform_2.getDispersion()));
        output("The standard deviation is: " +
                MathUtils.round(Math.sqrt(uniform_2.getDispersion())));

        double[] filteredValues = DataUtils.filterDataSet(tolVal2Interval, dataSet);
        output("Filtered values:");
        Arrays.stream(filteredValues).boxed().forEach(e -> output(e.toString()));
        output("Number of filtered values: " + filteredValues.length);

        output("The mean is: " + MathUtils.round(MathUtils.mean(filteredValues)));

        output("Percentage of filtered values relatively to the entire series: "
                + MathUtils.round(MathUtils.percentage(filteredValues.length, dataSet.length)) + "%");

        output("The Probability of falling into the 'filtered' range: "
                + MathUtils.round(MathUtils.percentage(
                tolVal2Interval.getLength(), tolValInterval.getLength())) + "%");

        Map<Double, Integer> filteredFrequencyMap = MapUtils.filterMap(frequencyMap,
                (Map.Entry<Double, Integer> entry) ->
                        MathUtils.isWithinInterval(entry.getKey(), tolVal2Interval));
        output("Frequency of appearance map for filtered values:");
        filteredFrequencyMap.forEach((k, v) -> output(k + " --> " + v));

        NormalDistribution normal =
                new NormalDistribution(uniform_2.getExpectedValue(), uniform_2.getDispersion());

        Interval conf_level_50 = normal.getConfidenceInterval(0.5);
        output("Confidence interval for 50% confidence level: " + conf_level_50);

        Interval conf_level_75 = normal.getConfidenceInterval(0.75);
        output("Confidence interval for 75% confidence level: " + conf_level_75);

        Interval conf_level_80 = normal.getConfidenceInterval(0.8);
        output("Confidence interval for 80% confidence level: " + conf_level_80);

        Interval conf_level_95 = normal.getConfidenceInterval(0.95);
        output("Confidence interval for 95% confidence level: " + conf_level_95);

        writer.close();
    }

    private static UserInput input() {
        UserInput input = new UserInput();
        System.out.print("Please enter the basic value (basicVal): ");
        input.setBasicVal(reader.nextInt());
        System.out.print("Please enter the tolerance value (tolVal): ");
        input.setTolVal(reader.nextDouble());
        System.out.print("Please enter the number of elements (numberElem): ");
        input.setNumberElem(reader.nextInt());
        return input;
    }
    private static void output(String output) {
        System.out.println(output);
        writer.println(output);
    }
    private static double getTolVal2(double tolVal, double k) {
        return k * tolVal;
    }
}
