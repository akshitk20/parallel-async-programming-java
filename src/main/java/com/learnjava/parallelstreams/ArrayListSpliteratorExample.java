package com.learnjava.parallelstreams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;

public class ArrayListSpliteratorExample {

    public List<Integer> multiplyEachValue(ArrayList<Integer> inputList, int multiplyValue){
        startTimer();
        List<Integer> integerList = inputList
                .stream()
//                .parallel()
//                .parallelStream()
                .map(elem -> elem * multiplyValue)
                .collect(Collectors.toList());
        timeTaken();
        return integerList;
    }
}
