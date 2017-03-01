package com.java8.after;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.junit.Test;

public class StreamTestAfter {

	@Test
	public void shouldMutliplyUsingStream() {
		List<Integer> input = Arrays.asList(2, 3, 5, 7, 11);
		List<Integer> output = input.
				stream().
				map(new Function<Integer, Integer>() {
					@Override
					public Integer apply(Integer t) {
						return t * 2;
					}
				}).
				collect(new Supplier<List<Integer>>() {

					@Override
					public List<Integer> get() {
						return new ArrayList<>();
					}
				}, new BiConsumer<List<Integer>, Integer>() {

					@Override
					public void accept(List<Integer> t, Integer u) {
						t.add(u);

					}
				}, new BiConsumer<List<Integer>, List<Integer>>() {

					@Override
					public void accept(List<Integer> t, List<Integer> u) {
						t.addAll(u);

					}
				});
		assertThat(output).containsExactly(4, 6, 10, 14, 22);
	}

	@Test
	public void shouldMutliplyUsingStreamAndLambdaV1() {
		List<Integer> input = Arrays.asList(2, 3, 5, 7, 11);
		List<Integer> output = input.
				stream().
				map(i -> i * 2).
				collect(Collectors.toList());
		assertThat(output).containsExactly(4, 6, 10, 14, 22);
	}

	@Test
	public void shouldMutliplyUsingStreamAndLambdaV2() {
		List<Integer> input = Arrays.asList(2, 3, 5, 7, 11);
		List<Integer> output = input.
				stream().
				map(i -> i * 2).
				collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		assertThat(output).containsExactly(4, 6, 10, 14, 22);
	}



}
