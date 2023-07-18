package cn.snow.interviewapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    //数据库版本号
 public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
 }
    //数据库版本号
 private static final int DATABASE_VERSION = 1;
 // 数据库名
 private static final String DATABASE_NAME = "test.db";
 // 表名
 public static final String USER_TABLE_NAME = "user";
 public static final String AGE_TABLE_NAME = "age";
 @Override
 public void onCreate(SQLiteDatabase db) {
        // 创建两个表格:用户表 和职业表
 db.execSQL("CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT)");
 db.execSQL("CREATE TABLE IF NOT EXISTS " + AGE_TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + " age TEXT)");
 }
    @Override
 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}