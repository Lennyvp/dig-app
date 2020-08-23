package com.lenny.digapp.service.solution;

import com.lenny.digapp.model.Input;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolutionService {

    public SolutionService() {
    }

//    public SolutionService(List<Double> values, int size) {
//        this.values = takeTheMostValuableList(values, size);
//        this.size = size;
//        this.valuePerGroup = sumValue(this.values) / values.size() * size;
//    }

    private List<Double> takeTheMostValuableList(List<Double> values, int size) {
        values = sortInput(values);
        if (values.size() % size == 0) {
            return values;
        } else {
            List<Double> newValues = new ArrayList<>(values);
            for (int i = 0; i < values.size(); i++) {
                if (values.size() % size == 0) {
                    newValues.remove(i);
                } else {
                    break;
                }
            }
            return newValues;
        }
    }

    public List<List<List<Double>>> solve(Input input) {
        List<Double> values = input.getValues();
        int size = input.getSize();
        List<List<Double>> solutions = generate(values, size);
        List<List<Double>> solutionsFiltered = filter(solutions, calculateValuePerGroup(values, size));
        return match(solutionsFiltered, input.getValues());
    }

    public List<List<List<Double>>> match(List<List<Double>> solutions, List<Double> values) {
        List<List<List<Double>>> results = new ArrayList<>();
        for (List<Double> solution : solutions) {
            List<Double> comparisonValueList = new ArrayList<>(values);
            List<List<Double>> result = new ArrayList<>();
            for (Double value : solution) {
                comparisonValueList.remove(value);
            }
            result.add(solution);
            recMeth(comparisonValueList, new ArrayList<>(solutions), result, results, solutions);
        }
        return results;
    }

    private void recMeth(List<Double> comparison, List<List<Double>> comparisons, List<List<Double>> lists, List<List<List<Double>>> results, List<List<Double>> solutions) {
        for (List<Double> solution : solutions) {
            if (comparison.size() != 0) {
                if (comparison.containsAll(solution)) {
                    for (Double value : solution) {
                        comparison.remove(value);
                    }
                    lists.add(solution);
                    comparisons.remove(solution);
                }
            } else {
                results.add(lists);
                break;
            }
        }
    }

    public List<List<Double>> filter(List<List<Double>> values, double valuePerGroup) {
        return values.stream()
                .filter(x -> isEqualValue(sumValue(x), valuePerGroup, 0.15))
                .collect(Collectors.toList());
    }

//    public List<List<Double>> filter(double deviation) {
//        return solutions.stream()
//                .filter(x -> isEqualValue(sumValue(x), valuePerGroup, deviation))
//                .collect(Collectors.toList());
//    }

    public List<List<Double>> generate(List<Double> values, int size) {
        List<int[]> combinations = generate(values.size(), size);
        List<List<Double>> solutions = new ArrayList<>();

        for (int[] combination : combinations) {
            List<Double> combs = new ArrayList<>();
            for (int j : combination) {
                combs.add(values.get(j));

                if (combs.size() % size == 0) {
                    solutions.add(combs);
                }
            }
        }

        return solutions;
    }

    private List<int[]> generate(int n, int r) {
        List<int[]> combinations = new ArrayList<>();
        helper(combinations, new int[r], 0, n - 1, 0);
        return combinations;
    }

    private void helper(List<int[]> combinations, int[] data, int start, int end, int index) {
        if (index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        } else {
            int max = Math.min(end, end + 1 - data.length + index);
            for (int i = start; i <= max; i++) {
                data[index] = i;
                helper(combinations, data, i + 1, end, index + 1);
            }
        }
    }

    private List<Double> sortInput(List<Double> values) {
        return values.stream().sorted().collect(Collectors.toList());
    }

    private double calculateValuePerGroup(List<Double> values, int size) {
        return sumValue(values) / size;
    }

    private double sumValue(List<Double> list) {
        return list.stream().mapToDouble(Double::doubleValue).sum();
    }

    private boolean isEqualValue(double currentValue, double value, double deviation) {
        return currentValue <= value + deviation * value && currentValue >= value - deviation * value;
    }
}
