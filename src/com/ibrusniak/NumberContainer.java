package com.ibrusniak;

import java.util.stream.Stream;

public class NumberContainer {

    private Integer[] intPart = new Integer[] {0};
    private Integer[] frcPart = new Integer[] {0};

    @Override
    public String toString() {

        Stream<String> stream =
            Stream.<String>concat(
                (Stream.<String>concat(
                    Stream.of(intPart).map(String::valueOf),
                    Stream.<String>of("."))),
                Stream.of(frcPart).map(String::valueOf));
        
        return stream.reduce((x, y) -> x + y).get();
    }
}