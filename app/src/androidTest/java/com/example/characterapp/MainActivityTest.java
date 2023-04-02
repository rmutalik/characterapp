package com.example.characterapp;

import androidx.test.annotation.UiThreadTest;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.action.ViewActions;
import static org.junit.Assert.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivityTest {

    // NOTE: I do not need to write a unit test for onCreate since it is an Android-specific method and contains boilerplate code

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void initExcelUtils_compareContactNames_success() {
        List<String> expectedContactNames = List.of(("Chuck Bartowski"), ("Sarah Walker"), ("John Casey"), ("Diane Beckman"), ("Bryce Larkin"),
                ("Daniel Shaw"), ("Morgan Grimes"), ("Jeff Barnes"), ("Lester Patel"));

        rule.getScenario().onActivity(activity -> {
            assertEquals(activity.contactNames, expectedContactNames);
        });
    }

    @Test
    public void initExcelUtils_compareOtherData_success() {
        List<List<String>> expectedOtherData = new ArrayList<>();
        expectedOtherData.add(createStringList("Male", "Agent", "Single", "Y"));
        expectedOtherData.add(createStringList("Female", "Agent", "Single", "Y"));
        expectedOtherData.add(createStringList("Male", "Agent", "Complicated", "Y"));
        expectedOtherData.add(createStringList("Female", "Director", "Unknown", "N"));
        expectedOtherData.add(createStringList("Male", "Agent", "Single", "Y"));
        expectedOtherData.add(createStringList("Male", "Agent", "Widowed", "N"));
        expectedOtherData.add(createStringList("Male", "Sales Associate", "Single", "N"));
        expectedOtherData.add(createStringList("Male", "Sales Associate", "Single", "N"));
        expectedOtherData.add(createStringList("Male", "Sales Associate", "Single", "N"));

        rule.getScenario().onActivity(activity -> {
            assertEquals(activity.otherData.get(0), expectedOtherData.get(0));
            assertEquals(activity.otherData.get(1), expectedOtherData.get(1));
            assertEquals(activity.otherData.get(2), expectedOtherData.get(2));
            assertEquals(activity.otherData.get(3), expectedOtherData.get(3));
            assertEquals(activity.otherData.get(4), expectedOtherData.get(4));
            assertEquals(activity.otherData.get(5), expectedOtherData.get(5));
            assertEquals(activity.otherData.get(6), expectedOtherData.get(6));
            assertEquals(activity.otherData.get(7), expectedOtherData.get(7));
            assertEquals(activity.otherData.get(8), expectedOtherData.get(8));
        });
    }

    private ArrayList<String> createStringList(String gender, String occupation, String marital_status, String want_to_meet) {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, gender, occupation, marital_status, want_to_meet);
        return list;
    }

    @Test
    public void initRecyclerViewAdapter_compareItemsList_success() {
        List<Item> expectedItemsList = new ArrayList<>();
        expectedItemsList.add(new Item("Chuck Bartowski", ""));
        expectedItemsList.add(new Item("Sarah Walker", ""));
        expectedItemsList.add(new Item("John Casey", ""));
        expectedItemsList.add(new Item("Diane Beckman", ""));
        expectedItemsList.add(new Item("Bryce Larkin", ""));
        expectedItemsList.add(new Item("Daniel Shaw", ""));
        expectedItemsList.add(new Item("Morgan Grimes", ""));
        expectedItemsList.add(new Item("Jeff Barnes", ""));
        expectedItemsList.add(new Item("Lester Patel", ""));

        rule.getScenario().onActivity(activity -> {
            assertEquals(activity.itemsList.get(0), expectedItemsList.get(0));
            assertEquals(activity.itemsList.get(1), expectedItemsList.get(1));
            assertEquals(activity.itemsList.get(2), expectedItemsList.get(2));
            assertEquals(activity.itemsList.get(3), expectedItemsList.get(3));
            assertEquals(activity.itemsList.get(4), expectedItemsList.get(4));
            assertEquals(activity.itemsList.get(5), expectedItemsList.get(5));
            assertEquals(activity.itemsList.get(6), expectedItemsList.get(6));
            assertEquals(activity.itemsList.get(7), expectedItemsList.get(7));
            assertEquals(activity.itemsList.get(8), expectedItemsList.get(8));
        });
    }

    @Test
    @UiThreadTest
    public void onItemClick_setPositiveButton_success() {
        rule.getScenario().onActivity(activity -> {
            new Thread(() -> {
                // BUG: Code below doesn't actually do anything; test passes regardless. Possibly due to new Thread?
                Espresso.onView(ViewMatchers.withId(R.id.rvContacts))
                        .perform(RecyclerViewActions.actionOnItemAtPosition(-1, ViewActions.click()));

//                Espresso.onView(ViewMatchers.withId(R.id.contactName))
//                        .check(ViewAssertions.matches(ViewMatchers.withText("Item clicked!")));
            });
        });
    }

    @Test
    public void onItemClick_setNeutralButton_success() {

    }

    @Test
    public void onItemClick_setNegativeButton_success() {

    }

    @Test
    public void callNotesDialog() {
    }

    @Test
    public void writeNotes() {
        Item item = new Item("Test", null);
        item.setAlertDialogMessage("");
        assertEquals(item.getAlertDialogMessage(), "");
    }

    @Test
    public void onNotesBtnClick() {
    }

    @Test
    public void callInfoDialog() {
    }

    @Test
    public void onInfoBtnClick() {
    }

}