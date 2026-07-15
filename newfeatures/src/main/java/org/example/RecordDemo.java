package org.example;

import java.time.LocalDate;
import java.time.Month;

public class RecordDemo{
    public static void main(String[] args) {
        Person prasad1 = new Person("Prasad", "Thakur", LocalDate.of(1980, Month.JULY, 16), "880 Powder Mill Road, Wilmington DE 19803");
        Person prasad2 = new Person("Prasad", "Thakur", LocalDate.of(1980, Month.JULY, 16), "880 Powder Mill Road, Wilmington DE 19803");

    System.out.printf("Are they equal? "+ (prasad1.equals(prasad2)));
}

record Person(String firstName, String lastName, LocalDate birthDate, String address) {
    public Person {
        if (firstName == null || lastName == null || birthDate == null || address == null) {
            throw new IllegalArgumentException("All fields must be non-null");
        }
    }
}
}
