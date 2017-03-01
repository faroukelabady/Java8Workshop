package com.java8.people;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Person {

	private final String name;
	private final Gender gender;
	private final int weight;
	private final int height;
	private final LocalDate dateOfBirth;
	private final Set<Phone> phoneNumbers;

	public Person(String name, Gender gender, int weight, int height, LocalDate dateOfBirth, Phone... phoneNumbers) {
		this.name = name;
		this.gender = gender;
		this.weight = weight;
		this.height = height;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumbers = new HashSet<>(Arrays.asList(phoneNumbers));
	}

	public String getName() {
		return name;
	}

	public int getHeight() {
		return height;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public int getWeight() {
		return weight;
	}

	public Set<Phone> getPhoneNumbers() {
		return phoneNumbers;
	}

	@Override
	public String toString() {
		return "Person{name='" + name + '\'' + ", gender=" + gender + ", weight=" + weight + ", height=" + height + ", dateOfBirth=" + dateOfBirth + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Person person = (Person) o;
		return height == person.height
				&& weight == person.weight
				&& dateOfBirth.equals(person.dateOfBirth)
				&& name.equals(person.name)
				&& phoneNumbers.equals(person.phoneNumbers)
				&& gender == person.gender;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + gender.hashCode();
		result = 31 * result + weight;
		result = 31 * result + height;
		result = 31 * result + dateOfBirth.hashCode();
		result = 31 * result + phoneNumbers.hashCode();
		return result;
	}
}
