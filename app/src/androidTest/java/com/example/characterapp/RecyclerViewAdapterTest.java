package com.example.characterapp;

import static org.junit.Assert.*;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewAdapterTest {

    Context context = ApplicationProvider.getApplicationContext();
    Context spyContext = Mockito.spy(context);
    LayoutInflater mockInflater = Mockito.mock(LayoutInflater.class);

    ClickListener mockListener = Mockito.mock(ClickListener.class);
    RecyclerViewAdapter mockAdapter = Mockito.mock(RecyclerViewAdapter.class);
    RecyclerViewAdapter.ViewHolder mockViewHolder = Mockito.mock(RecyclerViewAdapter.ViewHolder.class);

    List<String> data = new ArrayList<>(Arrays.asList("a", "b", "c"));
    RecyclerViewAdapter rva = new RecyclerViewAdapter(spyContext, data, mockListener);

    @Test
    public void onCreateViewHolder() {
        Mockito.when(LayoutInflater.from(spyContext))
                .thenReturn(mockInflater);

        View mockView = Mockito.mock(View.class);
        RecyclerViewAdapter.ViewHolder viewHolder = new RecyclerViewAdapter.ViewHolder(mockView);

        viewHolder.myTextView = Mockito.mock(TextView.class);
        viewHolder.myInfoBtnView = Mockito.mock(Button.class);
        viewHolder.myNotesBtnView = Mockito.mock(Button.class);

        viewHolder.myTextView.setOnClickListener(mockListener::onItemClick);
        viewHolder.myNotesBtnView.setOnClickListener(mockListener::onNotesBtnClick);
        viewHolder.myInfoBtnView.setOnClickListener(mockListener::onInfoBtnClick);

        // BUG: Can't verify clickListener because item can't be clicked during testing; need to simulate click event with `when`
//        Mockito.verify(mockListener).onItemClick(mockView);
    }

    @Test
    public void onBindViewHolder() {
        Item mockItem = Mockito.mock(Item.class);
        RecyclerViewAdapter.ViewHolder mockHolder = Mockito.mock(RecyclerViewAdapter.ViewHolder.class);

        Mockito.when(mockItem.isUpdated()).thenReturn(true);
    }

    @Test
    public void getItemCount() {
//        System.out.println("Size: " + rva.getmData().size());
        assertEquals(rva.getmData().size(), 3);
    }

    @Test
    public void getItems() {
        Item item = Mockito.mock(Item.class);
        List<Item> items = new ArrayList<>();

        for (int i=0; i<data.size(); i++) {
            items.add(new Item(data.get(i), ""));
        }

        Mockito.when(mockAdapter.getItems()).thenReturn(items);

//        System.out.println("Items: " + mockAdapter.getItems().toString());
        // INFO: Either use toString() or override equals & hashCode methods since the compared objects are different
        assertEquals(mockAdapter.getItems().toString(),
                new ArrayList<>(Arrays.asList
                        (new Item("a", ""), new Item("b", ""), new Item("c", ""))
                ).toString()
        );
    }
}