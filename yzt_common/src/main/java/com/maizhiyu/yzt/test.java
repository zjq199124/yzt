package com.maizhiyu.yzt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class test {

    public static void main(String[] args) {

        List<Integer> list1 = Arrays.asList(1,2,3,4,5,6);

        //map的方式
        List<Integer> collect1 = list1.stream()
                .map(t -> {
                    return t+1;
                })
                .collect(Collectors.toList());
        System.out.println(collect1);

        List<Student> list = new ArrayList<>();
        list.add(new Student("Jack1", 88));
        list.add(new Student("Jack2", 81));
        list.add(new Student("Jack3", 82));
        list.add(new Student("Jack4", 83));
        list.add(new Student("Jack5", 84));
        list.add(new Student("Jack6", 85));
        list.sort(Student::compareByScore);
        System.out.println(list);

    }

    static class Student {
        private String name;
        private int score;

        public Student() {

        }

        public Student(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int compareByScore(Student student) {
            return this.getScore() - student.getScore();
        }

        @Override
        public String toString() {
            return this.name + "  " + this.score + "  ";
        }
    }
}
