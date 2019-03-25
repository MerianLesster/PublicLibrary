package com.example.android.publiclibrary;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FindBookInformation extends AppCompatActivity {

    private Button Search;
    private EditText BookID, BookName, Author, Price, ShelfNo;
    private Spinner Category;
    private SQLiteDatabase DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_book_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Search = (Button) findViewById(R.id.btn_Search_Book);

        BookID = (EditText) findViewById(R.id.txt___BookID);
        BookName = (EditText) findViewById(R.id.txt___BookName);
        Author = (EditText) findViewById(R.id.txt___Author);
        Price = (EditText) findViewById(R.id.txt___Price);
        ShelfNo = (EditText) findViewById(R.id.txt___ShelfNo);
        Category = (Spinner) findViewById(R.id.spinner3);

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search_Info();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Category_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        Category.setAdapter(adapter);

    }

    private void ClearFiled() {
        BookID.setText(null);
        BookName.setText(null);
        Author.setText(null);
        Price.setText(null);
        ShelfNo.setText(null);
    }

    private void Search_Info() {
        boolean Notvalid = false;
        String ID = BookID.getText().toString().trim();
        String CATEGORY = Category.getSelectedItem().toString().trim();
        if (ID.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter the BookID to Search", Toast.LENGTH_LONG).show();
            ClearFiled();
        } else if (!Notvalid) {
            String query = "Select * from LibraryBook_Details where BookID ='" + ID + "';";
            Cursor cursorResult = DB.rawQuery(query, new String[]{});
            if (cursorResult.moveToFirst()) {
                do {
                    String add1 = cursorResult.getString(1);
                    BookName.setText(add1);
                    String add2 = cursorResult.getString(2);
                    Category.setSelection(((ArrayAdapter<String>) Category.getAdapter()).getPosition(add2));
                    String add3 = cursorResult.getString(3);
                    Author.setText(add3);
                    String add4 = cursorResult.getString(4);
                    Price.setText(add4);
                    String add5 = cursorResult.getString(5);
                    ShelfNo.setText(add5);

                    Toast.makeText(getApplicationContext(), "Book Information Found", Toast.LENGTH_SHORT).show();
                } while (cursorResult.moveToNext());
            } else {
                Toast.makeText(getApplicationContext(), "Please enter the the correct BookID to Search", Toast.LENGTH_LONG).show();
                ClearFiled();
            }
        }
    }
}