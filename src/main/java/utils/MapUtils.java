package utils;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MapUtils {

    public static <K, V> Map<K, V> filterMap(Map<K, V> map, Predicate<Map.Entry<K, V>> predicate) {
        return map.entrySet().stream()
                .filter(predicate)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y));
    }

    public static Map<Double, Integer> getFrequencyMap(double[] data) {
        return Arrays.stream(data).boxed().collect(
                Collectors.toMap(Function.identity(), val -> 1, (s, a) -> s >= a ? ++s : ++a));
    }

}
