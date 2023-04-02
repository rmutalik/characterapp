package com.example.characterapp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import android.content.Context;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class ExcelUtilsTest {

    static Context mockContext = mock(Context.class);
    static ExcelUtils mockExcel = mock(ExcelUtils.class);

    private static List<List<List<String>>> readExcelData() {
        when(mockExcel.readExcelData(mockContext, "contacts_info.xls"))
                .thenReturn(List.of(
                        List.of(List.of("Chuck Bartowski"), List.of("Sarah Walker"), List.of("John Casey"), List.of("Diane Beckman"), List.of("Bryce Larkin"),
                                List.of("Daniel Shaw"), List.of("Morgan Grimes"), List.of("Jeff Barnes"), List.of("Lester Patel")
                        ),
                        List.of(List.of("Male, Agent, Single, Y"), List.of("Female, Agent, Single, Y"), List.of("Male, Agent, Complicated, Y"),
                                List.of("Female, Director, Unknown, N"), List.of("Male, Agent, Single, Y"), List.of("Male, Agent, Widowed, N"),
                                List.of("Male, Sales Associate, Single, N"), List.of("Male, Sales Associate, Single, N"), List.of("Male, Sales Associate, Single, N")
                        )
                ));

        return mockExcel.readExcelData(mockContext, "contacts_info.xls");
    }

    @Test
    public void getNameValues() {
        List<List<String>> result = ExcelUtilsTest.readExcelData().get(0);
        List<List<String>> expectedResult = List.of(List.of("Chuck Bartowski"), List.of("Sarah Walker"), List.of("John Casey"), List.of("Diane Beckman"), List.of("Bryce Larkin"),
                List.of("Daniel Shaw"), List.of("Morgan Grimes"), List.of("Jeff Barnes"), List.of("Lester Patel"));

        assertEquals(result, expectedResult);
    }

    @Test
    public void getOtherValues() {
        List<List<String>> result = ExcelUtilsTest.readExcelData().get(1);
        List<List<String>> expectedResult = List.of(List.of("Male, Agent, Single, Y"), List.of("Female, Agent, Single, Y"), List.of("Male, Agent, Complicated, Y"),
                List.of("Female, Director, Unknown, N"), List.of("Male, Agent, Single, Y"), List.of("Male, Agent, Widowed, N"),
                List.of("Male, Sales Associate, Single, N"), List.of("Male, Sales Associate, Single, N"), List.of("Male, Sales Associate, Single, N"));

        assertEquals(result, expectedResult);
;
    }

}