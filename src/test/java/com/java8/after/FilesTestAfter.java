package com.java8.after;

import static com.java8.people.Gender.FEMALE;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.base.Throwables;
import com.java8.before.FilesTestBefore;
import com.java8.people.Person;
import com.java8.people.PersonDao;

public class FilesTestAfter {

	private final PersonDao dao = new PersonDao();

	@Test
	public void shouldLoadAllPeople() throws IOException {
		final List<Person> people = dao.loadPeopleDatabase();

		assertThat(people).hasSize(137);
	}

	@Test
	public void shouldSortByName() throws IOException {
		final List<Person> people = dao.loadPeopleDatabase();

		final List<String> names = people.stream().
				map(Person::getName).
				distinct().
				sorted().
				collect(toList());

		assertThat(names).startsWith("Aleksandar", "Alexander", "Alexandra", "Ali", "Alice");
	}

	/**
	 * Hint: Comparator.comparing()
	 */
	@Test
	public void shouldSortFemalesByHeightDesc() throws IOException {
		final List<Person> people = dao.loadPeopleDatabase();

		final List<String> names = people.stream()
				.filter(p -> p.getGender() == FEMALE)
				.sorted(comparing(Person::getHeight).reversed())
				.map(Person::getName)
				.collect(toList());

		assertThat(names).startsWith("Mia", "Sevinj", "Anna", "Sofia");
	}

	@Test
	public void shouldSortByDateOfBirthWhenSameNames() throws IOException {
		final List<Person> people = dao.loadPeopleDatabase();

		final List<String> names = people.stream().
				sorted(comparing(Person::getName).
						thenComparing(Person::getDateOfBirth)).
				map(p -> p.getName() + '-' + p.getDateOfBirth().getYear()).
				collect(toList());

		assertThat(names).startsWith("Aleksandar-1966", "Alexander-1986", "Alexander-1987", "Alexandra-1988", "Ali-1974");
	}

	/**
	 * @see Files#list(Path)
	 * @throws Exception
	 */
	@Test
	public void shouldGenerateStreamOfAllFilesIncludingSubdirectoriesRecursively() throws Exception {
		//given
		final String fileToSearch = FilesTestBefore.class.getSimpleName() + ".java";

		//when
		final Optional<Path> found = filesInDir(Paths.get("."))
				.filter(path -> path.endsWith(fileToSearch))
				.findAny();

		//then
		assertThat(found.isPresent()).isTrue();
	}

	private static Stream<Path> filesInDir(Path dir) {
		return listFiles(dir)
				.flatMap(path ->
						path.toFile().isDirectory() ?
								filesInDir(path) :
								Stream.of(path));
	}

	private static Stream<Path> listFiles(Path dir) {
		try {
			return Files.list(dir);
		} catch (IOException e) {
			throw Throwables.propagate(e);
		}
	}

}
