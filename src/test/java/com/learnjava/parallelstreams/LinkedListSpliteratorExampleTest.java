package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListSpliteratorExampleTest {

    LinkedListSpliteratorExample linkedListSpliteratorExample = new LinkedListSpliteratorExample();

    @Test
    void multiplyEachValue(){
        int size = 1000000;
        LinkedList<Integer> integers = DataSet.generateIntegerLinkedList(size);

        linkedListSpliteratorExample.multiplyEachValue(integers,2);

        assertEquals(size,integers.size());
    }

}