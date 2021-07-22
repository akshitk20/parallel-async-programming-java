package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;

import java.util.List;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class ParallelStreamsExample {

    public List<String> stringTransform(List<String> namesList){
        return namesList
                //.stream()
                .parallelStream()
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> namesList = DataSet.namesList();
//        ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();
        startTimer();
//        List<String> result = parallelStreamsExample.stringTransform(namesList);

        List<String> result = namesList
                .parallelStream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        System.out.println(result);
        timeTaken();

    }

    private String addNameLengthTransform(String name) {
        delay(500);
        return name.length()+" - "+name ;
    }
}
