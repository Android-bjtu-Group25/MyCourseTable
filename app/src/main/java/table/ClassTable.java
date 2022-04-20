package table;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mycoursetable.MainActivity;

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

public class ClassTable extends AppCompatActivity {
    public Calendar startTime;
    public int weekNum;//第几周,第一周为1,
    public ArrayList<Class> list = new ArrayList<Class>();
    MainActivity mainActivity;
    public ClassTable(MainActivity m) {
        mainActivity=m;
        startTime = Calendar.getInstance();
        for (int i = 0; i < 35; i++) {
            list.add(null);//预制添加35门课
        }
        //从已有的文件读取添加过的课程
        String arr[] = {};
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                arr = mainActivity.readFile((i + 1) + "-" + (j + 1));
                if (arr == null)//没有课
                {
                    continue;
                } else {
                    Class c = new Class(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]),
                            Weektime.valueOf(arr[3]), arr[4], arr[5]);
                    list.set(i * 7 + j, c);
                }
            }
        }
        //从已有文件读本学期开始时间
        arr = mainActivity.readFile("base");

        if (arr == null) {
            startTime = startTime.getInstance();//默认当前时间是开始时间
        } else
            setDate(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]));

        calcWeekOffset();

    }
    public static String getDate() {
        Calendar cal = Calendar.getInstance();
        System.out.println("年:" + cal.get(Calendar.YEAR));
        System.out.println("月:" + (cal.get(Calendar.MONTH) + 1));
        System.out.println("日:" + cal.get(Calendar.DAY_OF_MONTH));
        System.out.println("时:" + cal.get(Calendar.HOUR_OF_DAY));
        System.out.println("分:" + cal.get(Calendar.MINUTE));
        System.out.println("秒:" + cal.get(Calendar.SECOND));

        //return getDate(cal);
        return "1";
    }
    public void setStartDate(int month, int day) {
        String[] arrs = new String[2];
        arrs[0] = month + "";
        arrs[1] = day + "";
        setDate(month, day);
        mainActivity.writefile("base", arrs);
    }
    //输入年月编辑起始日期
    public void setDate(int month, int day) {
        Calendar cal = this.startTime;
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Calendar calnow = Calendar.getInstance();
        //System.out.println(calcWeekOffset(cal.getTime(),calnow.getTime()));
        System.out.println(cal.getTime());
        calcWeekOffset();
    }
    //计算当前是第几天
    int calcDayOffset(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {  //同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {  //闰年
                    timeDistance += 366;
                } else {  //不是闰年

                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else { //不同年
            return day2 - day1;
        }
    }
    //计算当前是第几周
    public void calcWeekOffset() {
        Date startTime = this.startTime.getTime();
        Date endTime = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = dayOfWeek - 1;
        if (dayOfWeek == 0) dayOfWeek = 7;

        int dayOffset = calcDayOffset(startTime, endTime);

        int weekOffset = dayOffset / 7;
        int a;
        if (dayOffset > 0) {
            a = (dayOffset % 7 + dayOfWeek > 7) ? 1 : 0;
        } else {
            a = (dayOfWeek + dayOffset % 7 < 1) ? -1 : 0;
        }
        weekOffset = weekOffset + a;
        weekNum = weekOffset + 1;

    }
    //添加课程
    public void addClass(String name, int weekday, int daytime, Weektime weektime, String teacher, String position) {
        Class c = new Class(name, weekday, daytime, weektime, teacher, position);
        //设置内存对应类
        list.set((weekday - 1) * 7 + daytime, c);
        //设置存储文件
        String[] arrs = new String[6];
        arrs[0] = name;
        arrs[1] = weekday + "";
        arrs[2] = daytime + "";
        arrs[3] = weektime + "";
        arrs[4] = teacher + "";
        arrs[5] = position + "";

        mainActivity.writefile(weekday + "-" + daytime + "", arrs);
    }
    //删除课程
    public void deleteClass(int w, int d) {
        /*
        直接写入空即为删除
        同时把相应位置null
         */
        String[] arrs = {};
        mainActivity.writefile(w + "-" + d + "", arrs);
        list.set((w - 1) * 7 + d - 1, null);
    }
    //写文件
}






