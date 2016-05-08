package com.apps4j.javatests.lambdas.input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        List<Person> list = createShortList();
        for (int i = 0; i < 1000; i++) {
            createShortList().stream().count();
        }
        {
            long start = System.currentTimeMillis();
            long totalAge = 0;
            for (Person p : list) {
                if (p.getAge() >= 23 && p.getAge() <= 11165) {
                    totalAge += p.getAge();
                }
            }

            System.out.println("total age: " + totalAge);
            System.out.println("millis: " + (System.currentTimeMillis() - start));
        }
        System.out.println("========================================================================");
        {
            long start = System.currentTimeMillis();
            Predicate<Person> predicate = p -> p.getAge() >= 23 && p.getAge() <= 11165;
            ToIntFunction<Person> getAge = Person::getAge;
            Stream<Person> stream = list
                    .stream();
            long totalAge = stream
                    .filter(predicate)
                    .mapToInt(getAge)
                    .sum();
            System.out.println("total age: " + totalAge);
            System.out.println("millis: " + (System.currentTimeMillis() - start));
        }
        System.out.println("========================================================================");
        {
            long start = System.currentTimeMillis();
            Predicate<Person> predicate = p -> p.getAge() >= 23 && p.getAge() <= 11165;
            ToIntFunction<Person> getAge = Person::getAge;
            Stream<Person> stream = list
                    .stream();
            long totalAge = stream
                    .filter(predicate)
                    .mapToInt(getAge)
                    .sum();
            System.out.println("total age: " + totalAge);
            System.out.println("millis: " + (System.currentTimeMillis() - start));
        }

    }


    public static List<Person> createShortList() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            people.add(new Person("test " + i, "test " + i, i, "test " + i, "test " + i, "test " + i));
        }
        return Collections.unmodifiableList(people);
    }
}

class Person {
    private String givenName;
    private String surName;
    private int age;
    private String address;
    private String eMail;
    private String phone;

    public Person(String givenName, String surName, int age, String address, String eMail, String phone) {
        this.givenName = givenName;
        this.surName = surName;
        this.age = age;
        this.address = address;
        this.eMail = eMail;
        this.phone = phone;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
