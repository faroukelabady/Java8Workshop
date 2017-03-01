package com.java8.before;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.offset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

public class StreamInfiniteTestBefore {

	@Test
	public void shouldGenerateNaturalNumbersAndSumFirstThousand() throws Exception {
		// given
		// when
		long sum = 0;
		for (int i = 1; i <= 1000; i++) {
			sum += i;
		}

		// then
		assertThat(sum).isEqualTo(500500);
	}

	@Test
	public void shouldCheckForPrimes() throws Exception {
		assertThat(isPrime(2)).isTrue();
		assertThat(isPrime(3)).isTrue();
		assertThat(isPrime(4)).isFalse();
		assertThat(isPrime(5)).isTrue();
		assertThat(isPrime(6)).isFalse();
		assertThat(isPrime(7)).isTrue();
		assertThat(isPrime(8)).isFalse();
		assertThat(isPrime(9)).isFalse();
		assertThat(isPrime(10)).isFalse();
		assertThat(isPrime(11)).isTrue();
	}

	private boolean isPrime(long x) {
		final long upTo = Math.min(x - 1, (long) Math.ceil(Math.sqrt(x)));
		for (long i = 2; i <= upTo; i++) {
			if (x % i == 0) {
				return false;
			}
		}
		return true;
	}

	private long nextPrimeAfter(long x) {
		for (long i = x + 1;; i++) {
			if (isPrime(i))
				return i;
		}
	}

	@Test
	public void shouldFindNextPrime() throws Exception {
		assertThat(nextPrimeAfter(2)).isEqualTo(3);
		assertThat(nextPrimeAfter(3)).isEqualTo(5);
		assertThat(nextPrimeAfter(4)).isEqualTo(5);
		assertThat(nextPrimeAfter(5)).isEqualTo(7);
		assertThat(nextPrimeAfter(6)).isEqualTo(7);
		assertThat(nextPrimeAfter(7)).isEqualTo(11);
		assertThat(nextPrimeAfter(8)).isEqualTo(11);
		assertThat(nextPrimeAfter(9)).isEqualTo(11);
		assertThat(nextPrimeAfter(10)).isEqualTo(11);
		assertThat(nextPrimeAfter(11)).isEqualTo(13);
	}

	@Test
	public void shouldCalculateProductOfFirstFivePrimes() throws Exception {
		// given
		// LongStream primes = LongStream.iterate(2, PrimeUtil::nextPrimeAfter);

		List<Long> primes = new ArrayList<>();
		long prime = 2;
		for (int i = 1; i <= 5; i++) {
			primes.add(prime);
			prime = nextPrimeAfter(prime);
		}

		// when
		long product = 1;
		for (Long i : primes) {
			product *= i;
		}

		// then
		assertThat(product).isEqualTo(2 * 3 * 5 * 7 * 11);
	}

	@Test
	public void shouldGenerateGrowingStrings() throws Exception {
		// given
		// when
		List<String> strings = new ArrayList<>();
		String val = "";
		for (int i = 1; i <= 7; i++) {
			strings.add(val);
			val = val + "*";
		}

		// then
		assertThat(strings).containsExactly("", "*", "**", "***", "****", "*****", "******");
	}

	/**
	 * This method tries to estimate Pi number by randomly generating points on
	 * a 2x2 square. Then it calculates what's the distance of each point to the
	 * center of the square. The proportion of points closer than 1 to all of
	 * them approximates Pi. The more points you take, better approximation you
	 * get.
	 *
	 * @see <a href=
	 *      "http://en.wikipedia.org/wiki/Approximations_of_π#Summing_a_circle.27s_area">Wikipedia</a>
	 */
	@Test
	public void shouldEstimatePi() throws Exception {
		// given
		Stream<Point> randomPoints = Stream.generate(Point::random);

		List<Point> points = new ArrayList<>();
		int precision = 1_000;
		for (int i = 1; i <= precision; i++) {
			points.add(Point.random());
		}

		// when
		long count = 0;
		for (Point p : points) {
			if (p.distance() <= 1.0) {
				count++;
			}
		}
		double piDividedByFour = count / (double) precision;

		// then
		assertThat(piDividedByFour * 4).isEqualTo(Math.PI, offset(0.1));
	}

}

class Point {
	private final double x;
	private final double y;

	Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public static Point random() {
		return new Point(Math.random() * 2 - 1, Math.random() * 2 - 1);
	}

	public double distance() {
		return Math.sqrt(x * x + y * y);
	}
}
