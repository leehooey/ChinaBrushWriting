package com.chinabrushwriting.lee.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chinabrushwriting.lee.domain.PersonInfo;
import com.chinabrushwriting.lee.utils.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 采用单一模式
 * 仅仅可以读数据库
 * Created by Lee on 2016/7/21.
 */
public class InfoDao {

    private static File file = new File(UIUtils.getContext().getFilesDir(), "info.db");
    private static InfoDao infoDao;
    private final SQLiteDatabase db;


    private InfoDao() {
        db = SQLiteDatabase.openDatabase(file.toString(), null, SQLiteDatabase.OPEN_READONLY);
    }

    public static InfoDao getInstance() {
        if (infoDao == null) {
            infoDao = new InfoDao();
        }
        return infoDao;
    }

    /**
     * 查询数据库中所有信息
     *
     * @return
     */
    public List<PersonInfo> query() {
        return query(null, -1);
    }

    /**
     * 便利数据库中所有符合values的数据
     *
     * @param values
     * @return
     */
    public List<PersonInfo> query(String values) {
        return query(values, -1);
    }

    /**
     * 遍历数据库中所有符合bool的值
     *
     * @param bool
     * @return
     */
    public List<PersonInfo> query(int bool) {
        return query(null, bool);
    }

    /**
     * 便利数据库搜索符合values和bool的值
     *
     * @param values
     * @param bool
     * @return
     */
    public List<PersonInfo> query(String values, int bool) {

        Cursor cursor = null;
        if (values == null && bool == -1) {
            cursor = db.query("data", new String[]{"name", "value", "bool"},
                    null, null, null, null, null);
        } else if (values != null && bool == -1) {
            cursor = db.query("data", new String[]{"name", "value", "bool"},
                    "value=?", new String[]{values}, null, null, null);
        } else if (values == null && bool != -1) {
            cursor = db.query("data", new String[]{"name", "value", "bool"},
                    "bool=?", new String[]{bool + ""}, null, null, null);
        } else if (values != null && bool != -1) {
            cursor = db.query("data", new String[]{"name", "value", "bool"},
                    "value=? and bool=?", new String[]{values, bool + ""}, null, null, null);
        }
        if (cursor == null) {
            return null;
        }
        List<PersonInfo> personInfos = new ArrayList<>();
        while (cursor.moveToNext()) {
            PersonInfo person = new PersonInfo();
            person.setName(cursor.getString(0));
            person.setValues(cursor.getString(1));
            person.setBool(cursor.getInt(2));
            personInfos.add(person);
        }
        cursor.close();
        // db.close();
        return personInfos;
    }
}
