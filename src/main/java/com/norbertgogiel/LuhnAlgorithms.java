package com.norbertgogiel;

public final class LuhnAlgorithms {

    /**
     * Private constructor.
     */
    private LuhnAlgorithms() {}

    /**
     * Accepts any number as {@code String} and checks if it
     * is valid Luhn number.
     *
     * <p>The number has to be equal or greater than zero and be
     * equal to or smaller than {@link Long#MAX_VALUE}.
     *
     * <p>The method parses {@code String} to {@code long} and passes
     * the result to {@link #calculateLuhnSum(long, int)}.
     *
     * <p>An exception of type {@code NumberFormatException} is
     * thrown if any of the following situations occurs:
     * <ul>
     *
     * <li>The first argument is {@code null} or is a {@code String}
     * of length zero.
     *
     * <li>The String contains any other ASCII character
     * than numbers.
     *
     * <li>The number is greater than {@link Long#MAX_VALUE}.
     * </ul>
     *
     * @param       number a {@code String} containing a representation
     *                     of the number to be parsed and validated
     * @throws      NumberFormatException if the string does not contain
     *              a parsable {@code long}.
     * @return      a {@code boolean} indicator whether the number is valid
     *              Luhn number
     */
    public static boolean isValid(String number) {
        return isValid(Long.parseLong(number));
    }

    /**
     * Accepts an input as {@code long} that is to be validated
     * as Luhn number.
     *
     * <p>The number has to be equal to or greater than zero and be equal
     * to or smaller than {@link Long#MAX_VALUE}.
     *
     * <p>The method passes the value to {@link #calculateLuhnSum(long, int)}
     * to calculate the Luhn sum.
     *
     * <p>This method then checks if mod10 of the result is equal to zero
     * to verify validity.
     *
     * @param       number a {@code long} as a parameter to be validated
     * @return      a {@code boolean} indicator whether the number is
     *              valid Luhn number
     */
    public static boolean isValid(long number) {
        return calculateLuhnSum(number, 1) % 10 == 0;
    }

    /**
     * Generates valid Luhn number within the range provided by
     * lowerBound and upperBound.
     *
     * <p>The advantage over most algorithms is that this algorithm does
     * NOT circularly use random number generation until a match is
     * found. It is only using number generation once to find a random
     * between the range provided. It then calculates the final number
     * to pass mod-10 validation making this a much faster algorithm
     * than some of the alternatives.
     *
     * <p>The two bounds could have different sizes in length. In this case
     * the shorter number will have trailing zeros added, so that it
     * matches the length of the longer number. In this case the algorithm
     * is NOT going to provide the result between the lower value and
     * the higher value.
     *
     * <p>If the required length is 1 the algorithm returns 0 by default
     * as this is the only valid digit that passes mod10 validation.
     *
     * <p>The user will have to provide the final length as {@code int}
     * to return the final result of that length.
     *
     * <p>The method splits generation into multiple steps. The first
     * step is to normalise the numbers in case their lengths differ, as
     * described above. Secondly, trailing zeroes are going to
     * be added to both numbers to match {@code finalLength - 1}.
     * Finally, a random number is generated between normalised
     * ranges. Then, a Luhn sum is calculated to find the missing digit
     * to meet mod-10 requirement. That last digit number is then
     * added to the end of the randomly generated number.
     *
     * @param       lowerBound a {@code long} to be used as a lower
     *                         bound for the calculation
     * @param       upperBound a {@code long} to be used as an upper
     *                         bound for the calculation
     * @param       finalLength an {@code int} to be used as
     *                          a final length of the result
     * @return      random Luhn valid number between bounds of
     *              {@code length = finalLength}
     */
    public static long generateLuhnFromRange(long lowerBound, long upperBound, int finalLength) {
        if (finalLength == 1)
            return 0;
        int lowerBoundLength = countDigits(lowerBound);
        int upperBoundLength = countDigits(upperBound);
        lowerBound = normaliseBound(false, lowerBound, finalLength - lowerBoundLength - 1);
        upperBound = normaliseBound(true, upperBound, finalLength - upperBoundLength - 1);
        long randomLong = calculateRandom(lowerBound, upperBound);
        long luhnRemainder = calculateLuhnLastDigit(randomLong);
        return (randomLong * 10) + luhnRemainder;
    }

    /**
     * Returns a random number between 0 and 9 of length
     * defined by the final length.
     *
     * <p>The number is generated by the algorithm in
     * {@link #generateLuhnFromRange(long, long, int)}.
     *
     * @param       finalLength an {@code int} to be used as
     *                          a final length of the result
     * @return      random Luhn valid number between bounds of
     *              {@code length = finalLength}
     */
    public static long generateRandomLuhn(int finalLength) {
        return generateLuhnFromRange(0, 9, finalLength);
    }

    private static int countDigits(long number) {
        int length = 0;
        long multiplier = 1;
        do {
            length++;
            multiplier *= 10;
        } while (multiplier <= number);
        return length;
    }

    private static long normaliseBound(boolean isUpperBound, long number, int power) {
        do {
            number *= 10;
            if (isUpperBound)
                number += 9;
            power--;
        } while (power > 0);
        return number;
    }

    private static long calculateRandom(long lowerBound, long upperBound) {
        return lowerBound + (long)(Math.random() * (upperBound - lowerBound));
    }

    private static long calculateLuhnLastDigit(long number) {
        long sum = calculateLuhnSum(number, 2);
        return (sum % 10 == 0) ? 0 : 10 - (sum % 10);
    }

    private static long calculateLuhnSum(long number, int multiplier) {
        long sum = 0;
        do {
            long lastDigit = number % 10;
            number /= 10;
            long product = lastDigit * multiplier;
            sum += product > 9 ? product - 9 : product;
            multiplier = 3 - multiplier;
        } while (number > 0);
        return sum;
    }
}
