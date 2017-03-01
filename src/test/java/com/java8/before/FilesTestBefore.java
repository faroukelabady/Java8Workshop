package com.java8.before;

import static com.java8.people.Gender.FEMALE;
import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import static java.nio.file.FileVisitResult.*;
import static java.nio.file.FileVisitOption.*;

import org.junit.Test;

import com.google.common.base.Throwables;
import com.java8.people.Person;
import com.java8.people.PersonDao;

public class FilesTestBefore {

	private final PersonDao dao = new PersonDao();

	@Test
	public void shouldLoadAllPeople() throws IOException {
		final List<Person> people = dao.loadPeopleDatabase();

		assertThat(people).hasSize(137);
	}

	@Test
	public void shouldSortByName() throws IOException {
		final List<Person> people = dao.loadPeopleDatabase();

		List<String> names = new ArrayList<>();
		for (Person p : people) {
			if (!names.contains(p.getName())) {
				names.add(p.getName());
			}
		}
		Collections.sort(names);

		assertThat(names).startsWith("Aleksandar", "Alexander", "Alexandra", "Ali", "Alice");
	}

	/**
	 * Hint: Comparator.comparing()
	 */
	@Test
	public void shouldSortFemalesByHeightDesc() throws IOException {
		final List<Person> people = dao.loadPeopleDatabase();
		List<Person> persons = new ArrayList<>();
		for (Person p : people) {
			if (p.getGender() == FEMALE) {
				persons.add(p);
			}
		}
		Collections.sort(persons, new Comparator<Person>() {
			@Override
			public int compare(Person o1, Person o2) {
				return o2.getHeight() > o1.getHeight() ? 1 : o2.getHeight() == o1.getHeight() ? 0 : -1;
			}
		});
		List<String> names = new ArrayList<>();
		for (Person p : persons) {
			names.add(p.getName());
		}

		assertThat(names).startsWith("Mia", "Sevinj", "Anna", "Sofia");
	}

	@Test
	public void shouldSortByDateOfBirthWhenSameNames() throws IOException {
		final List<Person> people = dao.loadPeopleDatabase();

		List<Person> persons = new ArrayList<>();
		for (Person p : people) {
			persons.add(p);
		}

		Collections.sort(persons, new Comparator<Person>() {
			@Override
			public int compare(Person o1, Person o2) {
				int i = o1.getName().compareTo(o2.getName());
				if (i != 0)
					return i;
				i = o1.getDateOfBirth().compareTo(o2.getDateOfBirth());
				if (i != 0)
					return i;
				return i;
			}
		});

		List<String> names = new ArrayList<>();
		for (Person p : persons) {
			names.add(p.getName() + "-" + p.getDateOfBirth().getYear());
		}

		assertThat(names).startsWith("Aleksandar-1966", "Alexander-1986", "Alexander-1987", "Alexandra-1988",
				"Ali-1974");
	}

	/**
	 * @see Files#list(Path)
	 * @throws Exception
	 */
	@Test
	public void shouldGenerateStreamOfAllFilesIncludingSubdirectoriesRecursively() throws Exception {
		// given
		final String fileToSearch = FilesTestBefore.class.getSimpleName() + ".java";
		Finder finder = new Finder(fileToSearch);
		Files.walkFileTree(Paths.get("."), finder);
		finder.done();
		// when
		final Optional<Path> found = Optional.of(finder.foundPath);
		// then
		assertThat(found.isPresent()).isTrue();
	}

}

class Finder extends SimpleFileVisitor<Path> {

	private final PathMatcher matcher;
	public int numMatches = 0;
	public Path foundPath;

	Finder(String pattern) {
		matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
	}

	// Compares the glob pattern against
	// the file or directory name.
	void find(Path file) {
		Path name = file.getFileName();
		if (name != null && matcher.matches(name)) {
			numMatches++;
			foundPath = file;
			System.out.println(file);
		}
	}

	// Prints the total number of
	// matches to standard out.
	void done() {
		System.out.println("Matched: " + numMatches);
	}

	// Invoke the pattern matching
	// method on each file.
	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
		find(file);
		return CONTINUE;
	}

	// Invoke the pattern matching
	// method on each directory.
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
		find(dir);
		return CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) {
		System.err.println(exc);
		return CONTINUE;
	}
}
