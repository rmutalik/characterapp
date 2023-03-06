package com.example.characterapp;

//import com.example.characterapp.ExcelUtils;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.characterapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements ClickListener {

    private String m_text = "";
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<List<List<String>>> m_data;
    List<String> contactNames = new ArrayList<>();
    List<List<String>> otherData = new ArrayList<>();
    List<Item> itemsList = new ArrayList<>();
    int currentIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Read Excel file and provide data to populate the RecyclerView
        ExcelUtils workbook = new ExcelUtils();
        m_data = workbook.readExcelData(MainActivity.this, "contacts_info.xls");
        m_data.get(0).forEach(contactNames::addAll);
        otherData = m_data.get(1);

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.rvContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this, contactNames, this);
        recyclerView.setAdapter(adapter);

        // Add dividers between entries
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                RecyclerView.LayoutManager.getProperties(this, null, 0, 0).orientation);
        recyclerView.addItemDecoration(dividerItemDecoration);

        itemsList = adapter.getItems();
    }


    @Override
    public void onItemClick(View itemView) {
        View view = (View) itemView.getParent();
        TextView notesButtonView = (TextView) view.findViewById(R.id.contactName);
        String currentContact = notesButtonView.getText().toString();
        currentIndex = contactNames.indexOf(currentContact);
        Item item = itemsList.get(currentIndex);

        new AlertDialog.Builder(view.getContext())
                .setTitle("Character")
                .setMessage("Does this person have an overall good or bad character?")
                .setPositiveButton("Good", (dialog, which) -> {
                    view.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                    m_text = "";
                    item.setAlertDialogMessage(m_text);
                    dialog.dismiss();
                })
                .setNeutralButton("Clear", (dialog, which) -> {
                    view.setBackgroundColor(Color.TRANSPARENT);
//                    view.findViewById(R.id.btnNotes).setBackgroundColor(Color.TRANSPARENT);
                    m_text = "";
                    item.setAlertDialogMessage(m_text);
                    dialog.dismiss();
                })
                .setNegativeButton("Bad", (dialog, which) -> {
                    view.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red));
                    callNotesDialog(view.findViewById(R.id.btnNotes));
                    dialog.dismiss();
                })
                .show();
    }


    // Opens another dialog box to optionally add notes when "Bad" button is clicked
    public void callNotesDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Notes");
        builder.setMessage("Why does this person have bad character?");

        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(50, 0, 50, 0);

        final EditText inputNotes = new EditText(this);
        inputNotes.setLayoutParams(params);
        inputNotes.setInputType(InputType.TYPE_CLASS_TEXT);
        inputNotes.setFilters(new InputFilter[] { new InputFilter.LengthFilter(20) });
        inputNotes.setHint("Add notes...");

        layout.addView(inputNotes);
        builder.setView(layout);

        builder.setPositiveButton("Ok", (dialog, which) -> {
            m_text = inputNotes.getText().toString();
            writeNotes(view);
            dialog.dismiss();
        });
        builder.setNegativeButton("Skip", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    // Writes to Notes (Helper function)
    public void writeNotes(View buttonView) {
        View view = (View) buttonView.getParent();
        TextView notesButtonView = (TextView) view.findViewById(R.id.contactName);
        String currentContact = notesButtonView.getText().toString();
        currentIndex = contactNames.indexOf(currentContact);

        Item item = itemsList.get(currentIndex);
        item.setAlertDialogMessage(m_text);
        m_text = "";
    }

    // NOTE: Keep read and write operations separate for Notes button; right now, they are both handled in this method
    // Reads from Notes (Click listener)
    @Override
    public void onNotesBtnClick(View buttonView) {
        View view = (View) buttonView.getParent();
        TextView notesButtonView = (TextView) view.findViewById(R.id.contactName);
        String currentContact = notesButtonView.getText().toString();
        currentIndex = contactNames.indexOf(currentContact);
        Item item = itemsList.get(currentIndex);

        // Update item message if it changes values
        if (!Objects.equals(item.getAlertDialogMessage(), item.getPreviousMessage())) {
            item.setUpdated(true);
            adapter.notifyItemChanged(currentIndex, m_text);                                    // notify RecyclerView adapter of the change
            item.setPreviousMessage(item.getAlertDialogMessage());                              // set previous message to current value of the alert dialog message
        }

        new AlertDialog.Builder(buttonView.getContext())
                .setTitle("Notes")
                .setMessage(item.getAlertDialogMessage())
                .show();
    }


    public void callInfoDialog(String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info");

        // Create mapping from contact name to correct data for that contact
        int index = contactNames.indexOf(key);
        List<String> info = otherData.get(index);

        String infoName = "Name: " + key;
        String infoGender = "Gender: " + info.get(0);
        String infoOccupation = "Occupation: " + info.get(1);
        String infoMaritalStatus = "Marital Status: " + info.get(2);
        String infoWantToMeet = "Want to Meet (Y/N): " + info.get(3);

        builder.setMessage(infoName + "\n" + infoGender + "\n" + infoOccupation + "\n" + infoMaritalStatus + "\n" + infoWantToMeet);
        builder.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    // Reads from Info (Click listener)
    @Override
    public void onInfoBtnClick(View buttonView) {
        View view = (View) buttonView.getParent();
        TextView infoButtonView = (TextView) view.findViewById(R.id.contactName);
        String currentContact = infoButtonView.getText().toString();
        callInfoDialog(currentContact);
    }

}