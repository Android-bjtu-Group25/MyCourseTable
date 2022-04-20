package com.example.mycoursetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import java.io.FileOutputStream;

import table.Weektime;

public class deletecourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletecourse);

        final String[] wd = new String[1];
        final String[] dt = new String[1];
        wd[0] = "1";
        dt[0] = "1";
        RadioGroup weekday = findViewById(R.id.weekday);
        RadioGroup daytime = findViewById(R.id.daytime);
        Button Return=findViewById(R.id.return_to_main_delete);
        Button delete=findViewById(R.id.delete_course);

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
                startActivity(new Intent(deletecourse.this, MainActivity.class));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteClass(Integer.valueOf(wd[0]),Integer.valueOf(dt[0]));
                startActivity(new Intent(deletecourse.this, MainActivity.class));
            }
        });

    }
    void deleteClass(int weekday, int daytime) {
        //设置存储文件,写入空文件，表示删除
        writefile(weekday + "-" + daytime + "",null);
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