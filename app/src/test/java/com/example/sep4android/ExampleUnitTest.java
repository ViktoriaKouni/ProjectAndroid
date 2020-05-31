package com.example.sep4android;

import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Models.ArchiveRoom;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void some_test() {
       MutableLiveData<ArrayList<ArchiveRoom>> rooms= new MutableLiveData<>();

    }
}