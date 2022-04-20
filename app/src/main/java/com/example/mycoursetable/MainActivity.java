package com.example.mycoursetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.*;
import android.widget.RelativeLayout;
import android.widget.TextView;

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


import table.*;
import table.Class;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果是第一次使用软件，则创建没有的文件
        createfiles();//第一次启动创建相应的文件
        setContentView(R.layout.activity_main);
        Button bn = findViewById(R.id.my_btn);//d

        Button setbase = findViewById(R.id.setbase);

        ClassTable classTable = new ClassTable(this);//已经读取了课程信息，显示对应课程

        TextView t = findViewById(R.id.weekNumber);

        if(classTable.weekNum<=16&&classTable.weekNum>=0)
        {
            t.setText("第" + classTable.weekNum + "周");
            showClasses(classTable);
        }
        else
            t.setText("请设置正确的起始日期");
        //长按事件，添加课程
        bn.setOnLongClickListener(new View.OnLongClickListener() {

            @Override//长按跳转到添加界面
            public boolean onLongClick(View view) {
                //bn.setBackgroundColor(Color.parseColor("#D54FB8"));
                startActivity(new Intent(MainActivity.this, setcourse.class));
                return false;
            }
        });
        //点击事件，删除课程
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, deletecourse.class));
            }
        });

        setbase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, setbase.class));
            }
        });

    }


    public void showClasses(ClassTable classTable) {
        int i, j;
        View v = findViewById(R.id.day1);

        TextView t = null;
        Class c;
        for (i = 0; i < 7; i++) {
            //选择天
            switch (i) {
                case 0:
                    v = findViewById(R.id.day1);
                    break;
                case 1:
                    v = findViewById(R.id.day1);
                    break;
                case 2:
                    v = findViewById(R.id.day1);
                    break;
                case 3:
                    v = findViewById(R.id.day1);
                    break;
                case 4:
                    v = findViewById(R.id.day1);
                    break;
                case 5:
                    v = findViewById(R.id.day1);
                    break;
                case 6:
                    v = findViewById(R.id.day1);
                    break;
            }
            for (j = 0; j < 5; j++) {
                switch (j) {
                    case 0:
                        t = v.findViewById(R.id.c1);
                        break;
                    case 1:
                        t = v.findViewById(R.id.c2);
                        break;
                    case 2:
                        t = v.findViewById(R.id.c3);
                        break;
                    case 3:
                        t = v.findViewById(R.id.c4);
                        break;
                    case 4:
                        t = v.findViewById(R.id.c5);
                        break;
                }
                c = classTable.list.get(i * 5 + j);
                if (c == null) continue;
                else//有课程
                {
                    String s = c.name + "\n@" + c.position;
                    if (c.weektime == Weektime.normal)//全有课，直接显示
                    {
                        t.setText(s);
                    } else if (c.weektime == Weektime.d) {//单周
                        if (classTable.weekNum / 2 == 1)
                            t.setText(s);
                    } else if (c.weektime == Weektime.s)//双周
                    {
                        if (classTable.weekNum / 2 == 0)
                            t.setText(s);
                    } else if (c.weektime == Weektime.q)//前半学期
                    {
                        if (classTable.weekNum <= 8)
                            t.setText(s);
                    } else if (c.weektime == Weektime.h)//后半学期
                    {
                        if (classTable.weekNum > 8)
                            t.setText(s);
                    }
                }
            }
        }
    }
    //创建基础存储程序
    public void createfiles() {
        /*
        如果base，1-1....等文件不存在，则创建文件
         */
        try {
            FileInputStream in = openFileInput("1-1");
            //如果没有生成过文件，会报错
        } catch (Exception e) {
            createfile("base");
            for (int i = 1; i <= 7; i++)
                for (int j = 1; j <= 5; j++)
                    createfile(i + "-" + j);
        }
    }

    public void createfile(String filename) {
        String string = "";
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void teststream() {

        //测试输入流
        /*
        String filename;
        filename = "1-1";
        String string = "Hello world\nqqqq";
        FileOutputStream outputStream;
        try{
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

         */

        //测试输出流
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("1-1");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String[] readFile(String filename) {
        String arr[] = {};
        if (filename.equals("base")) {
            arr = new String[2];
        } else
            arr = new String[6];

        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            int i = 0, j = 0;
            while ((line = reader.readLine()) != null) {
                arr[i++] = line;
            }
            if (i == 0)
                arr = null;//文件为空
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return arr;
    }

    public void writefile(String filename, String[] arrs) {
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            String string;
            for (String arr : arrs) {
                if (arr == null)
                    arr = "";
                string = arr + "\n";
                outputStream.write(string.getBytes());
            }

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





