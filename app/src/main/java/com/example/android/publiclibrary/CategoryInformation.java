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

public class CategoryInformation extends AppCompatActivity {

    private EditText NoOfBooks;
    private Spinner Category;
    private Button Analyse;
    private SQLiteDatabase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_information);
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

        NoOfBooks = (EditText) findViewById(R.id.txt_NO_of_BOOKs);
        Category = (Spinner) findViewById(R.id.spinner4);
        Analyse = (Button) findViewById(R.id.btn_analyse);

        Analyse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumberOfBooks();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Category_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        Category.setAdapter(adapter);

    }

    private void NumberOfBooks()
        {
            String query = " Select COUNT(*) from LibraryBook_Details where Category ='" + Category.getSelectedItem().toString() + "';";
            Cursor cursorResult = DB.rawQuery(query, new String[]{});
            if(cursorResult.moveToFirst())
            {
                do{
                    String add1 = cursorResult.getString(1);
                    NoOfBooks.setText(add1);

                    Toast.makeText(getApplicationContext(), "Number of Books Found", Toast.LENGTH_SHORT).show();
                }while (cursorResult.moveToNext());
            }
        }

}
