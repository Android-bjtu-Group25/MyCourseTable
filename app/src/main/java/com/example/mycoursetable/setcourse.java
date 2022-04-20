package com.example.mycoursetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import table.Class;
import table.Weektime;

public class setcourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        System.out.println("test");


        setContentView(R.layout.setcourse);
        //保存
        Button save = findViewById(R.id.save_course);

        //返回
        Button Return = findViewById(R.id.return_to_main);


        EditText courseName = findViewById(R.id.courseName);
        EditText teacherName = findViewById(R.id.teacherName);
        EditText locationName = findViewById(R.id.locationName);
        RadioGroup weektime = findViewById(R.id.weektime);
        RadioGroup weekday = findViewById(R.id.weekday);
        RadioGroup daytime = findViewById(R.id.daytime);
        final String[] wt = new String[1];
        final String[] wd = new String[1];
        final String[] dt = new String[1];
        wt[0] = "normal";
        wd[0] = "1";
        dt[0] = "1";

        weektime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.normal:
                        wt[0] = "normal";
                        break;
                    case R.id.q:
                        wt[0] = "q";
                        break;
                    case R.id.h:
                        wt[0] = "h";
                        break;
                    case R.id.d:
                        wt[0] = "d";
                        break;
                    case R.id.s:
                        wt[0] = "s";
                        break;
                    default:
                        break;
                }
            }
        });

        weekday.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.z1:
                        wd[0] = "1";
                        break;
                    case R.id.z2:
                        wd[0] = "2";
                        break;
                    case R.id.z3:
                        wd[0] = "3";
                        break;
                    case R.id.z4:
                        wd[0] = "4";
                        break;
                    case R.id.z5:
                        wd[0] = "5";
                        break;
                    case R.id.z6:
                        wd[0] = "6";
                        break;
                    case R.id.z7:
                        wd[0] = "7";
                        break;
                    default:
                        break;
                }
            }
        });

        daytime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.d1:
                        dt[0] = "1";
                        break;
                    case R.id.d2:
                        dt[0] = "2";
                        break;
                    case R.id.d3:
                        dt[0] = "3";
                        break;
                    case R.id.d4:
                        dt[0] = "4";
                        break;
                    case R.id.d5:
                        dt[0] = "5";
                        break;
                    default:
                        break;
                }


            }
        });


        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(setcourse.this, MainActivity.class));

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cN = courseName.getText().toString();
                String tN = teacherName.getText().toString();
                String lN = locationName.getText().toString();
                addClass(cN, Integer.valueOf(wd[0]), Integer.valueOf(dt[0]), Weektime.valueOf(wt[0]), tN, lN);
                startActivity(new Intent(setcourse.this, MainActivity.class));
            }
        });


    }

    void addClass(String name, int weekday, int daytime, Weektime weektime, String teacher, String position) {
        table.Class c = new table.Class(name, weekday, daytime, weektime, teacher, position);

        //设置存储文件
        String[] arrs = new String[6];
        arrs[0] = name;
        arrs[1] = weekday + "";
        arrs[2] = daytime + "";
        arrs[3] = weektime + "";
        arrs[4] = teacher + "";
        arrs[5] = position + "";

        writefile(weekday + "-" + daytime + "", arrs);
    }

    public void writefile(String filename, String[] arrs) {
        FileOutputStream outputStream;
        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            String string;
            for(String arr:arrs)
            {
                if(arr==null)
                    arr="";
                string=arr+"\n";
                outputStream.write(string.getBytes());
            }

            outputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}