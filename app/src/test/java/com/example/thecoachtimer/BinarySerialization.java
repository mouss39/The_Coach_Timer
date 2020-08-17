package com.example.thecoachtimer;

import com.example.thecoachtimer.models.SessionFinalStats;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import java.util.Arrays;

import static org.apache.commons.lang3.SerializationUtils.*;

public class BinarySerialization {

    @Test
    public void BinarySerializationTest() {
        //simple example of how to do binary Serialization (Bonus Feature 2
        Integer[] arrayOfNumbers=new Integer[]{1,6,4,2};
        //same way for the array of the key metrics like cadence, variance, number of laps, ...
        byte[] data = serialize(arrayOfNumbers);

        Integer[] arrayOfNumbersOutput=deserialize(data);
        boolean areEqual = Arrays.equals(arrayOfNumbers, arrayOfNumbersOutput);
        System.out.println("Arrays are equal : "+areEqual);



    }
}
