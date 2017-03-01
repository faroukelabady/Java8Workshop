package com.java8.before;

import static com.java8.people.Gender.FEMALE;
import static com.java8.people.Gender.MALE;
import static org.fest.assertions.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.Test;

import com.java8.people.Person;
import com.java8.people.Phone;

public class StreamTest2Before {

	public static final List<Person> PEOPLE = Arrays.asList(
			new Person("Jane", FEMALE, 62, 169, LocalDate.of(1986, Month.DECEMBER, 21), new Phone(10, 555100200)),
			new Person("Bob", MALE, 71, 183, LocalDate.of(1982, Month.FEBRUARY, 5), new Phone(10, 555100201)),
			new Person("Steve", MALE, 85, 191, LocalDate.of(1980, Month.MAY, 4), new Phone(11, 555100200),
					new Phone(11, 555100201), new Phone(11, 555100202)),
			new Person("Alice", FEMALE, 54, 178, LocalDate.of(1984, Month.AUGUST, 17), new Phone(12, 555100202)),
			new Person("Eve", FEMALE, 61, 176, LocalDate.of(1987, Month.FEBRUARY, 9), new Phone(10, 555100200)));

	@Test
	public void doesAnyFemaleExist() {

		boolean anyFemale = false;
		for (Person p : PEOPLE) {
			if (p.getGender().equals(FEMALE)) {
				anyFemale = true;
				break;
			}
		}

		assertThat(anyFemale).isTrue();
	}

	@Test
	public void shouldReturnNamesSorted() {
		final List<String> names = new ArrayList<>();
		for (Person p : PEOPLE) {
			names.add(p.getName());
		}
		Collections.sort(names, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		assertThat(names).containsExactly("Alice", "Bob", "Eve", "Jane", "Steve");
	}

	@Test
	public void areAllPeopleSlim() {
		boolean allSlim = true;
		for (Person p : PEOPLE) {
			if (p.getWeight() > 80) {
				allSlim = false;
				break;
			}

		}
		assertThat(allSlim).isFalse();
	}

	@Test
	public void areAllPeopleNotSlim() {
		boolean allNotSlim = false;
		for (Person p : PEOPLE) {
			if (p.getWeight() < 80) {
				allNotSlim = false;
				break;
			}
		}

		assertThat(allNotSlim).isFalse();
	}

	@Test
	public void findTallestPerson() {
		Optional<Person> max = Optional.ofNullable(null);
		for (Person p : PEOPLE) {
			if (!max.isPresent()) {
				max = Optional.of(p);
			} else if (max.get().getHeight() < p.getHeight()) {
				max = Optional.of(p);
			}
		}
		assertThat(max.isPresent()).isTrue();
		assertThat(max.get()).isEqualTo(PEOPLE.get(2));
	}

	@Test
	public void countMales() {
		long malesCount = 0;
		for (Person p : PEOPLE) {
			if (p.getGender().equals(MALE)) {
				malesCount++;
			}
		}

		assertThat(malesCount).isEqualTo(2);
	}

	/**
	 * Hint: use limit(2)
	 */
	@Test
	public void twoOldestPeople() {
		Comparator<Person> c = new Comparator<Person>() {
			@Override
			public int compare(Person o1, Person o2) {
				return o1.getDateOfBirth().compareTo(o2.getDateOfBirth());
			}
		};
		List<Person> p = new ArrayList<>(PEOPLE);
		Collections.sort(p, c);
		List<Person> oldest = Arrays.asList(p.get(0), p.get(1));
		assertThat(oldest).containsExactly(PEOPLE.get(2), PEOPLE.get(1));
	}

	/**
	 * Hint: PEOPLE.stream()...mapToInt()...sum()
	 */
	@Test
	public void totalWeight() {
		int totalWeight = 0;
		for (Person p : PEOPLE) {
			totalWeight += p.getWeight();
		}
		assertThat(totalWeight).isEqualTo(333);
	}

	@Test
	public void findUniqueCountryCodes() {
		Set<Integer> distinctCountryCodes = new HashSet<>();
		for (Person p : PEOPLE) {
			for (Phone ph : p.getPhoneNumbers()) {
				distinctCountryCodes.add(ph.getCountryCode());
			}
		}

		assertThat(distinctCountryCodes).containsExactly(10, 11, 12);
	}

	/**
	 * For each person born after LocalDate.of(1985, Month.DECEMBER, 25), add
	 * name to 'names'
	 */
	@Test
	public void forEachYoungPerson() {
		List<String> names = new ArrayList<>();

		for (Person p : PEOPLE) {
			if (p.getDateOfBirth().isAfter(LocalDate.of(1985, Month.DECEMBER, 25))) {
				names.add(p.getName());
			}
		}

		assertThat(names).containsExactly("Jane", "Eve");
	}

	/**
	 * @see Iterator#forEachRemaining(Consumer)
	 */
	@Test
	public void shouldRunOverIterator() throws Exception {
		// given
		final Iterator<Integer> iter = Arrays.asList(1, 2, 3).iterator();
		final StringBuilder sb = new StringBuilder();

		// when
		while (iter.hasNext()) {
			sb.append(iter.next());
		}

		// then
		assertThat(sb.toString()).isEqualToIgnoringCase("123");
	}

}
