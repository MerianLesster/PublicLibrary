package com.example.android.publiclibrary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class BookBorrowedInfomation extends AppCompatActivity {
    private EditText BookID,BookName,BorrowerID,ShelfNo,Date;

    private android.widget.Spinner Category;

    //private Button Date;
    //int year_x, month_x, day_x;
    //static final int DIALOG_ID =0;

    private Button ButtonAdd;
    private Button ButtonView;
    private Button ButtonDelete;
    private Button ButtonUpdate;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_borrowed_infomation);
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

        createDatabase();
        BookID=(EditText) findViewById(R.id.txt_BookID);
        BookName=(EditText) findViewById(R.id.txt_BookName);
        ShelfNo=(EditText) findViewById(R.id.txt_ShelfNo);
        BorrowerID=(EditText) findViewById(R.id.txt_BorrowerID);
        Category=(Spinner) findViewById(R.id.spinner);
        Date=(EditText) findViewById(R.id.txt_Date);

        ButtonAdd=(Button) findViewById(R.id.btn__Add);
        ButtonView=(Button) findViewById(R.id.btn__search) ;
        ButtonDelete=(Button) findViewById(R.id.btn__delete);
        ButtonUpdate=(Button) findViewById(R.id.btn__update);

        ButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertIntoDb();
            }
        });

        ButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewData();
            }
        });

        ButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteData();
            }
        });

        ButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateData();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Category_array, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        Category.setAdapter(adapter);

        /*final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);

        showdialog();*/
    }

    protected void createDatabase()
    {
        db=openOrCreateDatabase("Library__System", Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS BorrowBook_Details(BookID VARCHAR PRIMARY KEY NOT NULL,BookName VARCHAR,Category VARCHAR, BorrowerID VARCHAR, ShelfNo VARCHAR, Date_Of_Borrowed VARCHAR );");
    }

    protected void insertIntoDb()
    {
        String ID=BookID.getText().toString().trim();
        String NAME=BookName.getText().toString().trim();
        String CATEGORY=Category.getSelectedItem().toString().trim();
        String BorrowID=BorrowerID.getText().toString().trim();
        String SHELF_NO =ShelfNo.getText().toString().trim();
        String BorrowedDate=Date.getText().toString().trim();

        if (ID.isEmpty() || NAME.isEmpty() || CATEGORY.isEmpty() || BorrowID.isEmpty() || SHELF_NO.isEmpty() || BorrowedDate.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please fill all the fields above!! ",Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            String query = "Insert into BorrowBook_Details( BookID,BookName,Category,BorrowerID,ShelfNo,Date_Of_Borrowed) values('" + ID + "','" + NAME +"','" + Category.getSelectedItem().toString() +"','" + BorrowID +"','" + SHELF_NO +"','"+ BorrowedDate+"');";
            db.execSQL(query);
            Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();
            ClearFiled();
        }
    }
    protected void ViewData()
    {
        boolean Notvalid = false;
        String ID = BookID.getText().toString().trim();
        String CATEGORY=Category.getSelectedItem().toString().trim();
        if(ID.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please enter the BookID to Search",Toast.LENGTH_LONG).show();
            ClearFiled();
        }

        else if (!Notvalid)
        {
            String query = "Select * from BorrowBook_Details where BookID ='" + ID + "';";
            Cursor cursorResult = db.rawQuery(query, new String[]{});
            if(cursorResult.moveToFirst())
            {
                do{
                    String add1 = cursorResult.getString(1);
                    BookName.setText(add1);
                    String add2 = cursorResult.getString(2);
                    Category.setSelection(((ArrayAdapter<String>)Category.getAdapter()).getPosition(add2));
                    String add3 = cursorResult.getString(3);
                    BorrowerID.setText(add3);
                    String add4 = cursorResult.getString(4);
                    ShelfNo.setText(add4);
                    String add5 = cursorResult.getString(5);
                    Date.setText(add5);

                    Toast.makeText(getApplicationContext(), "Book Information Found", Toast.LENGTH_SHORT).show();
                }while (cursorResult.moveToNext());
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Please enter the the correct BookID to Search",Toast.LENGTH_LONG).show();
                ClearFiled();
            }
        }
    }

    private void ClearFiled()
    {
        BookID.setText(null);
        BookName.setText(null);
        BorrowerID.setText(null);
        ShelfNo.setText(null);
        Date.setText(null);
    }

    protected void DeleteData()
    {
        boolean Not_valid = false;
        String ID=BookID.getText().toString().trim();
        //if(ID.isEmpty())
        // {
        //    Toast.makeText(getApplicationContext(), "Please enter the BookID to Delete the data", Toast.LENGTH_LONG).show();
        // }
        if(!Not_valid)
        {
            String query = "delete from BorrowBook_Details where BookID ='" + ID + "';";
            db.execSQL(query);
            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_LONG).show();
            ClearFiled();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please enter the the correct BookID to Delete", Toast.LENGTH_LONG).show();
            ClearFiled();
        }

    }

    protected void UpdateData()
    {
        String ID=BookID.getText().toString().trim();
        String NAME=BookName.getText().toString().trim();
        String CATEGORY=Category.getSelectedItem().toString().trim();
        String BorrowID=BorrowerID.getText().toString().trim();
        String SHELF_NO =ShelfNo.getText().toString().trim();
        String BorrowedDate =Date.getText().toString().trim();

        if (ID.isEmpty() || NAME.isEmpty() || CATEGORY.isEmpty() || BorrowID.isEmpty() || SHELF_NO.isEmpty() || BorrowedDate.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please fill all the fields with the correct ID to UPDATE ",Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            String query = "Update BorrowBook_Details set BookName = '" + NAME + "', Category = '" + Category.getSelectedItem().toString() + "', BorrowerID = '" + BorrowID + "', ShelfNo = '" + SHELF_NO + "', Date_Of_Borrowed = '" + BorrowedDate + "' where BookID = '" + ID + "';";
            db.execSQL(query);

            Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sSelected=parent.getItemAtPosition(position).toString();
        Toast.makeText(this,sSelected, Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

}

