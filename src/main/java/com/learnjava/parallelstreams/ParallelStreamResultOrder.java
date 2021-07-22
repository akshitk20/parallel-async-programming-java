package com.learnjava.parallelstreams;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.learnjava.util.LoggerUtil.log;

public class ParallelStreamResultOrder {

    public static List<Integer> listOrder(List<Integer> numbers){
        return numbers
                .parallelStream()
                .map(num -> num * 2)
                .collect(Collectors.toList());
    }

    public static Set<Integer> setOrder(Set<Integer> numbers){
        return numbers
                .parallelStream()
                .map(num -> num * 2)
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
//        List<Integer> list = List.of(1,2,3,4,5,6,7,8);
//        log("result " +list);
//
//        List<Integer> integers = listOrder(list);
//        log("result " +integers);

        Set<Integer> list = Set.of(1,2,3,4,5,6,7,8);
        log("result " +list);

        Set<Integer> integers = setOrder(list);
        log("result " +integers);
    }
}
