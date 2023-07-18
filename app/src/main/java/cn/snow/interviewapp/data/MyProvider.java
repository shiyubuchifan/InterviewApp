package cn.snow.interviewapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyProvider extends ContentProvider {
    private Context mContext;
    MyDBHelper mDbHelper = null;
    SQLiteDatabase db = null;

    // 设置ContentProvider的唯一标识
    public static final String AUTOHORITY = "cn.snow.interviewapp.provider.test";
    public static final int User_Code = 1;
    public static final int Age_Code = 2;
    // UriMatcher类使用:在ContentProvider 中注册URI
    private static final UriMatcher mMatcher;

    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //初始化
        mMatcher.addURI(AUTOHORITY, "user", User_Code);//user对应的是finch.db数据库的user表
        mMatcher.addURI(AUTOHORITY, "age", Age_Code);//job对应的是finch.db数据库的job表
        // 若URI资源路径 = content://cn.snow.interviewapp.provider.test/user ，则返回注册码User_Code
        // 若URI资源路径 = content://cn.snow.interviewapp.provider.test/age ，则返回注册码Age_Code
    }

    /**
     * 初始化ContentProvider
     */
    @Override
    public boolean onCreate() {
        mContext = getContext();
        // 在ContentProvider创建时对数据库进行初始化
        // 运行在主线程，故不能做耗时操作,此处仅作展示
        mDbHelper = new MyDBHelper(getContext());
        db = mDbHelper.getWritableDatabase();
        // 初始化两个表的数据(先清空两个表,再各加入一个记录)
        db.execSQL("delete from user");
        db.execSQL("insert into user values(1,'Snow');");
        db.execSQL("insert into user values(2,'Rain');");
        db.execSQL("delete from age");
        db.execSQL("insert into age values(1,'18');");
        db.execSQL("insert into age values(2,'17');");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
        // 该方法在最下面
        String table = getTableName(uri);
//        // 通过ContentUris类从URL中获取ID
//        long personid = ContentUris.parseId(uri);
//        System.out.println(personid);
        // 查询数据
        return db.query(table, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    /**
     * 添加数据
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
        // 该方法在最下面
        String table = getTableName(uri);

        //nullColumnHack：当values参数为空或者里面没有内容的时候，我们insert是会失败的（底层数据库不允许插入一个空行），
        // 为了防止这种情况，我们要在这里指定一个列名，到时候如果发现将要插入的行为空行时，就会将你指定的这个列名的值设为null，然后再向数据库中插入。
        String columnName = getExistColumnName(uri);
        long id = db.insert(table, columnName, values);
        return id != -1 ? ContentUris.withAppendedId(uri, id) : null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    /**
     * 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
     */
    private String getTableName(Uri uri) {
        String tableName = null;
        switch (mMatcher.match(uri)) {
            case User_Code:
                tableName = MyDBHelper.USER_TABLE_NAME;
                break;
            case Age_Code:
                tableName = MyDBHelper.AGE_TABLE_NAME;
                break;
        }
        return tableName;
    }

    private String getExistColumnName(Uri uri) {
        String columnName = null;
        switch (mMatcher.match(uri)) {
            case User_Code:
                columnName = "_id";
                break;
            case Age_Code:
                columnName = "_id";
                break;
        }
        return columnName;
    }
}