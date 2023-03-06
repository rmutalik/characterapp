package com.example.characterapp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import androidx.recyclerview.widget.RecyclerView;

import org.junit.Test;
import org.mockito.Mockito;

public class MainActivityTest {

    @Test
    public void onCreate() {
        RecyclerView mockRecyclerView = Mockito.mock(RecyclerView.class);
        RecyclerViewAdapter mockRecyclerViewAdapter = Mockito.mock(RecyclerViewAdapter.class);
    }

    @Test
    public void onItemClick() {
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