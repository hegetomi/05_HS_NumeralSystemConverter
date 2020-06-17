package converter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        String[] inputs = new String[3];

        for (int i = 0; i < 3; i++) {
            if (scan.hasNext()) {
                inputs[i] = scan.next();
            } else {
                System.out.println("Error5");
                return;
            }
        }

        if (!inputs[0].matches("[\\d.]{1,}") || !inputs[2].matches("[\\d.]{1,}")) {
            System.out.println("Error: bad input");
            return;
        }

        int base = Integer.valueOf(inputs[0]);
        String input = inputs[1];
        int target = Integer.valueOf(inputs[2]);

        int inputInteger = 0;
        double inputFraction = 0;
        String outputWhole = "";
        String outputFraction = "";
        String[] splitInput = input.split("\\.");

        if (base > 36 || base < 1 || target > 36 || target < 1) {
            System.out.println("Error1");
            return;
        }

        if (!isImpossible(base, splitInput[0])) {
            inputInteger = convertWholeInputToDecimal(base, splitInput);
            outputWhole = convertWholeDecimalToTarget(target, inputInteger);
        } else {
            System.out.println("Error2");
        }
        if (splitInput.length == 2) {
            if (!isImpossible(base, splitInput[1])) {
                inputFraction = convertFractionInputToDecimal(base, splitInput);
                outputFraction = convertFractionDecimalToTarget(target, inputFraction);
            } else {
                System.out.println("Error3");
            }
        }

        if (inputFraction == Integer.MIN_VALUE || inputInteger == Integer.MIN_VALUE) {
            System.out.println("Error4");
            return;
        }
            if (!outputFraction.isEmpty()) {
                System.out.println(outputWhole + "." + outputFraction);
            } else {
                System.out.println(outputWhole);
            }
        }

    static int convertWholeInputToDecimal(int base, String[] splitInput) {
        if (base != 10) {
            if (base == 1) {
                return String.valueOf(splitInput[0]).length();
            } else {
                return Integer.parseInt("" + Integer.valueOf(splitInput[0], base));
            }
        } else {
            return Integer.valueOf(splitInput[0]);
        }
    }

    static double convertFractionInputToDecimal(int base, String[] splitInput) {
        if (base != 10) {
            if (base == 1) {
                return String.valueOf(splitInput[1]).length();
            } else {

                double fraction = 0;
                for (int i = 0; i < splitInput[1].length(); i++) {
                    fraction += (double) Integer.parseInt(String.valueOf(splitInput[1].charAt(i)), base) / Math.pow(base, i + 1);
                }
                return fraction;
            }
        } else {
            return Double.valueOf("0." + splitInput[1]);
        }
    }

    static String convertWholeDecimalToTarget(int target, int inputInteger) {
        if (target == 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("1".repeat(Math.max(0, inputInteger)));
            return sb.toString();
        } else {
            return Integer.toString(inputInteger, target);
        }
    }

    static String convertFractionDecimalToTarget(int target, double inputFraction) {
        double fraction = inputFraction;
        String outputFraction = "";
        if (fraction > 0) {
            for (int i = 0; i < 5; i++) {
                fraction = fraction * target;
                int value = (int) fraction;
                outputFraction = outputFraction.concat(Integer.toString(value, target));
                fraction = fraction - value;
            }
        }
        return outputFraction;
    }

    static boolean isImpossible(int base, String number) {
        String numbers = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] invalidNumberValues;
        if (base == 36) {
            invalidNumberValues = "".toCharArray();
        } else if (base == 1) {
            String validateInput = numbers.replaceAll("1", "");
            invalidNumberValues = validateInput.toCharArray();
        } else {
            String validateInput = numbers.substring(base, numbers.length() - 1);
            invalidNumberValues = validateInput.toCharArray();
        }
        for (int i = 0; i < invalidNumberValues.length; i++) {
            if (number.toUpperCase().contains(String.valueOf(invalidNumberValues[i]))) {
                return true;
            }
        }
        return false;
    }
}