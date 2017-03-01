package com.java8.before;

import static org.fest.assertions.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class StreamTestBefore {



	@Test
	public void shouldMutliply() {
		List<Integer> input = new ArrayList<Integer>();
		input.add(2);
		input.add(3);
		input.add(5);
		input.add(7);
		input.add(11);

		List<Integer> output = new ArrayList<Integer>();

		for(Integer in : input) {
			output.add(in * 2);
		}

		assertThat(output).containsExactly(4, 6, 10, 14, 22);

	}


}
