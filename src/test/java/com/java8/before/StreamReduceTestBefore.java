package com.java8.before;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import junitparams.Parameters;
import junitparams.naming.TestCaseName;

public class StreamReduceTestBefore {

	@Test
	public void shouldAddNumbersUsingReduce() throws Exception {
		// given
		final List<Integer> input = Arrays.asList(2, 3, 5, 7);

		// when
		int sum = 0;
		for (Integer i : input) {
			sum += i;
		}

		// then
		assertThat(sum).isEqualTo(2 + 3 + 5 + 7);
	}

	@Test
	public void shouldConcatNumbersBrokenWithParallelStream() throws Exception {
		// given
		final List<Integer> input = Arrays.asList(2, 3, 5, 7);

		// when
		String result = "";
		for (Integer i : input) {
			result = result + i;
		}

		// then
		assertThat(result).isEqualToIgnoringCase("2357");
	}

	@Test
	public void shouldConcatNumbers() throws Exception {
		// given
		final List<Integer> input = Arrays.asList(2, 3, 5, 7);

		// when
		String result = "";
		for (Integer i : input) {
			result = result + i;
		}

		// then
		assertThat(result).isEqualToIgnoringCase("2357");
	}

	@Test
	public void shouldFindMaxUsingReduce() throws Exception {
		// given
		final List<Integer> input = Arrays.asList(4, 2, 6, 3, 8, 1);

		// when
		int max = Integer.MIN_VALUE;
		for (Integer i : input) {
			max = Math.max(max, i);
		}

		// then
		assertThat(max).isEqualTo(8);
	}

	@Test
	public void shouldSimulateMapUsingReduce() {
		// given
		final List<Integer> input = Arrays.asList(2, 3, 5, 7);
		List<Integer> doubledPrimes = new ArrayList<>();
		for(Integer i: input) {
			doubledPrimes.add(i * 2);
		}
		// then
		assertThat(doubledPrimes).containsExactly(2 * 2, 3 * 2, 5 * 2, 7 * 2);
	}

	@Test
	public void shouldSimulateFilterUsingReduce() {
		// given
		final List<Integer> input = Arrays.asList(2, 3, 4, 5, 6);

		// when
		List<Integer> onlyEvenNumbers = new ArrayList<>();
			for(Integer i: input) {
				if(i % 2 == 0) {
					onlyEvenNumbers.add(i);
				}
			}
		// then
		assertThat(onlyEvenNumbers).containsExactly(2, 4, 6);
	}
}
