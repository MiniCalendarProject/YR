package com.example.cookandroid.myapplication;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button delbutton, addbutton;
    EditText info;

    private int myear;
    private int mmonth;
    private int mday;
    private Button pickdate;
    RadioGroup type;
    RadioButton mtype1, mtype2, mtype3, mtype4, mtype5, mtype6;
    int a;
    static final int DATE_DIALOG_ID = 0;

    myDBHelper myHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("일정 추가");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        pickdate = (Button) findViewById(R.id.pickdate);
        delbutton = (Button) findViewById(R.id.delbutton);
        addbutton = (Button) findViewById(R.id.addbutton);
        info = (EditText) findViewById(R.id.info);
        type = (RadioGroup) findViewById(R.id.type);

        final Calendar c = Calendar.getInstance();

        myear = c.get(Calendar.YEAR);
        mmonth = c.get(Calendar.MONTH);
        mday = c.get(Calendar.DAY_OF_MONTH);
        pickdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        mtype1 = (RadioButton) findViewById(R.id.type1);
        mtype2 = (RadioButton) findViewById(R.id.type2);
        mtype3 = (RadioButton) findViewById(R.id.type3);
        mtype4 = (RadioButton) findViewById(R.id.type4);
        mtype5 = (RadioButton) findViewById(R.id.type5);
        mtype6 = (RadioButton) findViewById(R.id.type6);

        mtype1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mtype1.setChecked(true);
                mtype2.setChecked(false);
                mtype3.setChecked(false);
                mtype4.setChecked(false);
                mtype5.setChecked(false);
                mtype6.setChecked(false);
                a= R.id.type1;
            }
        });

        mtype2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mtype2.setChecked(true);
                mtype1.setChecked(false);
                mtype3.setChecked(false);
                mtype4.setChecked(false);
                mtype5.setChecked(false);
                mtype6.setChecked(false);
                a= R.id.type2;
            }
        });
        mtype3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mtype3.setChecked(true);
                mtype2.setChecked(false);
                mtype1.setChecked(false);
                mtype4.setChecked(false);
                mtype5.setChecked(false);
                mtype6.setChecked(false);
                a= R.id.type3;
            }
        });
        mtype4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mtype4.setChecked(true);
                mtype2.setChecked(false);
                mtype3.setChecked(false);
                mtype1.setChecked(false);
                mtype5.setChecked(false);
                mtype6.setChecked(false);
                a= R.id.type4;
            }
        });
        mtype5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mtype5.setChecked(true);
                mtype2.setChecked(false);
                mtype3.setChecked(false);
                mtype4.setChecked(false);
                mtype1.setChecked(false);
                mtype6.setChecked(false);
                a= R.id.type5;
            }
        });
        mtype6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mtype6.setChecked(true);
                mtype2.setChecked(false);
                mtype3.setChecked(false);
                mtype4.setChecked(false);
                mtype5.setChecked(false);
                mtype1.setChecked(false);
                a= R.id.type6;
            }
        });

        myHelper = new myDBHelper(this);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String x;
                switch (a){
                    case R.id.type1:
                        x="기념일";
                        break;
                    case R.id.type2:
                        x="약속";
                        break;
                    case R.id.type3:
                        x="학교";
                        break;
                    case R.id.type4:
                        x="회사";
                        break;
                    case R.id.type5:
                        x="여행";
                        break;
                    default:
                        x="기타";
                }
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("INSERT INTO ToDoList VALUES (null, '" + myear + "', '"+mmonth+"', '"+mday+"', '" + info.getText().toString() + "','" + x + "');");
                sqlDB.close();
                Toast.makeText(getApplicationContext(),"추가되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateDisplay() {
        pickdate.setText(new StringBuilder().append(myear).append("-").append(mmonth + 1).append("-").append(mday));
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker View, int year, int monthOfYear, int dayOfMonth) {
            myear = year;
            mmonth = monthOfYear;
            mday = dayOfMonth;

            updateDisplay();

        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, myear, mmonth, mday);
        }
        return null;
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "calenderDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE ToDoList (_id INTEGER PRIMARY KEY AUTOINCREMENT, year INTEGER, month INTEGER, day INTEGER, info TEXT, type TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS ToDoList");
            onCreate(db);
        }
    }

}
