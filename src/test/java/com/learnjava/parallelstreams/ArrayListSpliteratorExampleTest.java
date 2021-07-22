package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListSpliteratorExampleTest {

    ArrayListSpliteratorExample arrayListSpliteratorExample = new ArrayListSpliteratorExample();

//    @RepeatedTest(5)
    @Test
    void test(){
        int size = 1000000;
        ArrayList<Integer> integers = DataSet.generateArrayList(size);

        arrayListSpliteratorExample.multiplyEachValue(integers,2);

        assertEquals(size,integers.size());

    }

}