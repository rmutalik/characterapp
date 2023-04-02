package com.example.characterapp;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.test.annotation.UiThreadTest;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.characterapp.RecyclerViewAdapter.ViewHolder;

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
    ClickListener mockListener = mock(ClickListener.class);
    List<String> data = new ArrayList<>(Arrays.asList("a", "b", "c"));
    RecyclerViewAdapter rva = new RecyclerViewAdapter(spyContext, data, mockListener);

    ViewGroup viewGroup = new ViewGroup(spyContext) {
        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {}
    };
    LayoutInflater layoutInflater = LayoutInflater.from(spyContext);
    View view = layoutInflater.inflate(R.layout.recyclerview_row, viewGroup, false);
    ViewHolder viewHolder = new ViewHolder(view);

    @Test
    @UiThreadTest
    public void onCreateViewHolder() {
        int dummyViewType = -1;

        Mockito.when(LayoutInflater.from(spyContext)).thenReturn(layoutInflater);

        viewHolder.alertDialog = mock(String.valueOf(AlertDialog.Builder.class));
        viewHolder.myTextView.setOnClickListener(mockListener::onItemClick);
        viewHolder.myNotesBtnView.setOnClickListener(mockListener::onNotesBtnClick);
        viewHolder.myInfoBtnView.setOnClickListener(mockListener::onInfoBtnClick);

        // Simulates click event, because view can't physically be clicked during testing
        mockListener.onItemClick(viewHolder.myTextView);
        mockListener.onNotesBtnClick(viewHolder.myNotesBtnView);
        mockListener.onInfoBtnClick(viewHolder.myInfoBtnView);

        // Assertions
        Class<?> expectedType = ViewHolder.class;
        Class<?> actualType;

        actualType = (rva.onCreateViewHolder(viewGroup, dummyViewType)).getClass();
        assertThat(expectedType, equalTo(actualType));

        actualType = (viewHolder).getClass();
        assertThat(expectedType, equalTo(actualType));

        Mockito.verify(mockListener).onItemClick(viewHolder.myTextView);
        Mockito.verify(mockListener).onNotesBtnClick(viewHolder.myNotesBtnView);
        Mockito.verify(mockListener).onInfoBtnClick(viewHolder.myInfoBtnView);
    }

    @Test
    public void onBindViewHolder() {
        int position = 0;
        String person = data.get(position);
        Item item = mock(Item.class);

        viewHolder.myTextView.setText(person);
        item.setUpdated(true);

        rva.onBindViewHolder(viewHolder, position);

        assertEquals(viewHolder.myTextView.getText(), person);
        assertFalse(item.isUpdated());

    }

    @Test
    public void getItemCount() {
        assertEquals(rva.getItemCount(), 3);
    }

    @Test
    public void getItems() {
        // INFO: Either use toString() or override equals & hashCode methods since the compared objects are different
        assertEquals(rva.getItems().toString(),
                new ArrayList<>(Arrays.asList
                        (new Item("a", ""), new Item("b", ""), new Item("c", ""))
                ).toString()
        );
    }
}