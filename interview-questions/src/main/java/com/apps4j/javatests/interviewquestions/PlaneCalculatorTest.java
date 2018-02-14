package com.apps4j.javatests.interviewquestions;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(Parameterized.class)
public class PlaneCalculatorTest {

    private int expectation;
    private int rows;
    private String seats;
    private String name;

    @Parameterized.Parameters(name = "{0}: Exp: {1}. Rows: {2}. Seats: {3}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"positive", 4, 2, "1A 2F 1C"},
                {"All busy", 0, 1, "1A 1E 1H"},
                {"All busy2", 0, 0, "1A 1E 1H"},
                {"All busy3", 0, 5, "1A 1E 1H 2A 2E 2H 3A 3E 3H 4A 4E 4H 5A 5E 5H"},
                {"All busy duplicates", 0, 2, "1A 1E 1H 2A 2E 2H 1A 1E 1H 2A 2E 2H"},
                {"Boundaries", 8 * 3, 10, "1A 1E 1H 10A 10E 10H"},
                {"Formatting", 8 * 3, 10, "1A     1E 1H 10A       10E 10H"},
                {"Lowercase letters", 8 * 3, 10, "1a 1e 1h 10a 10e 10h"},
                {"None busy", 3, 1, ""},
                {"Zero values", 0, 0, ""},
                {"Null values 1", 0, 0, null},
                {"Null values seats", 3, 1, null},
                {"Negative values", 0, -10, "1A 2F 1C"},
                {"Error Values", 3, 1, "A 2"},
                {"Left seats Boundaries left", 2, 1, "1A"},
                {"Left seats Boundaries right", 2, 1, "1C"},
                {"Right seats Boundaries left", 2, 1, "1H"},
                {"Right seats Boundaries right", 2, 1, "1K"},
                {"Medium seats Boundaries left", 3, 1, "1D"},
                {"Medium seats Boundaries right", 3, 1, "1G"},
                {"Medium Free", 1, 1, "1A 1K"},
                {"Left Free", 1, 1, "1E 1K"},
                {"Right Free", 1, 1, "1A 1E"},
                {"Outside upper boundaries", 6, 2, "3A 3E 3K"},
                {"Outside lower boundaries", 6, 2, "0A 0E 0K"},
                {"Outside letter boundaries", 6, 2, "3A 3E 3K 3Z 3Y"},
                {"Random order", 6, 3, "3A 2E 1K"}
        });
    }

    public PlaneCalculatorTest(String name, int expectation, int rows, String seats) {
        this.expectation = expectation;
        this.rows = rows;
        this.seats = seats;
        this.name = name;
    }

    @Test()
    public void test() {
        Assert.assertEquals(name, expectation, new Solution().solution(rows, seats));
    }
}

class Solution {
    private static Map<String, Integer> SEAT_MAP = new HashMap<String, Integer>() {{
        put("A", 0);
        put("B", 1);
        put("C", 2);

        put("D", 3);
        put("E", 4);
        put("F", 5);
        put("G", 6);

        put("H", 7);
        put("J", 8);
        put("K", 9);
    }};

    int solution(int N, String S) {
        if (N <= 0) {
            return 0;
        }
        if (S == null) {
            S = "";
        }
        String trim = S.trim().toUpperCase();
        String[] occupiedSeats = new String[0];
        if (trim.length() > 0) {
            occupiedSeats = trim.split(" ");
        }

        List<List<Seat>> seats = generateSeatMap(N);
        for (String occupiedSeat : occupiedSeats) {
            Integer[] seatIndex = convertSeatToIndex(occupiedSeat);
            if (seatIndex == null || seatIndex[0] > (N - 1)) {
                continue;
            }

            List<Seat> seatRow = seats.get(seatIndex[0]);
            seatRow.get(seatIndex[1]).setOccupied(true);
        }

        int count = 0;
        for (List<Seat> seat : seats) {
            List<Seat> left = seat.subList(0, 3);
            List<Seat> medium = seat.subList(3, 7);
            List<Seat> right = seat.subList(7, 10);

            if (left.stream().noneMatch(Seat::isOccupied)) {
                count++;
            }
            int c = 0;
            for (Seat s : medium) {
                if (s.isOccupied()) {
                    c = 0;
                } else {
                    c++;
                }
                if (c >= 3) {
                    count++;
                    c = 0;
                }
            }


            if (right.stream().noneMatch(Seat::isOccupied)) {
                count++;
            }
        }

        return count;
    }

    private List<List<Seat>> generateSeatMap(int rowsAmount) {
        List<List<Seat>> seats = new ArrayList<>();
        for (int i = 0; i < rowsAmount; i++) {
            List<Seat> seatArray = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                seatArray.add(new Seat(i, j, false));
            }
            seats.add(seatArray);
        }
        return seats;
    }


    private Integer[] convertSeatToIndex(String s) {
        Pattern p = Pattern.compile("(\\d+)([A-Za-z]+)");
        Matcher m = p.matcher(s);
        boolean matches = m.matches();
        if (matches) {
            String num = m.group(1);
            String letter = m.group(2);
            int seatNumber = Integer.valueOf(num) - 1;

            if (seatNumber < 0 || SEAT_MAP.get(letter) == null) {
                return null;
            }
            return new Integer[]{seatNumber, SEAT_MAP.get(letter.toUpperCase())};
        }
        return null;
    }
}

class Seat {
    private int row;
    private int col;
    private boolean occupied;

    Seat(int row, int col, boolean occupied) {
        this.row = row;
        this.col = col;
        this.occupied = occupied;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }


    @Override
    public String toString() {
        return String.format(
                " Seat: "
                        + " row [%s] "
                        + " col [%s] "
                        + " occupied [%s] "
                , row
                , col
                , occupied
        );
    }
}