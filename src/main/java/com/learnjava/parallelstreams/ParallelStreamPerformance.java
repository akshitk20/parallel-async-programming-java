package com.learnjava.parallelstreams;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

public class ParallelStreamPerformance {

    public int intSumUsingIntStream(int count, boolean isParallel){
        startTimer();
        IntStream intStream = IntStream.rangeClosed(0, count);
        if(isParallel)
            intStream.parallel();

        int sum = intStream.sum();
        timeTaken();
        return sum;
    }

    public int intSumUsingList(List<Integer> inputList, boolean isParallel){
        startTimer();
        Stream<Integer> stream = inputList.stream();
        if(isParallel)
            stream.parallel();

        int sum = stream
                .mapToInt(Integer::intValue) //this does not perform well with parallel streams
                .sum();

        timeTaken();
        return sum;
    }
}
