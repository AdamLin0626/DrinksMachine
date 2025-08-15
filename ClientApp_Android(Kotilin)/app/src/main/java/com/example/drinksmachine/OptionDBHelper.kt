package com.example.drinksmachine

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OptionDBHelper(context: Context) :
    SQLiteOpenHelper(context, "options.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE options (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "type TEXT NOT NULL)"
        )

        // 預設插入資料
        db.execSQL("INSERT INTO options (name, type) VALUES ('BlackTea', 'Drink')")
        db.execSQL("INSERT INTO options (name, type) VALUES ('GreenTea', 'Drink')")
        db.execSQL("INSERT INTO options (name, type) VALUES ('Milk', 'Drink')")
        db.execSQL("INSERT INTO options (name, type) VALUES ('Chacolate', 'Drink')")
        db.execSQL("INSERT INTO options (name, type) VALUES ('Coffee', 'Drink')")

        db.execSQL("INSERT INTO options (name, type) VALUES ('Pear', 'Topping')")
        db.execSQL("INSERT INTO options (name, type) VALUES ('Coconut', 'Topping')")
        db.execSQL("INSERT INTO options (name, type) VALUES ('GrassJelly', 'Topping')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS options")
        onCreate(db)
    }

    // 根據指定的類型（TypeA / TypeB）查詢對應的 label 並回傳清單
    // 根據指定的類型（Drink / Topping）查詢對應的選項
    fun getOptionsByType(type: String): List<String> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT name FROM options WHERE type = ?", arrayOf(type))
        val list = mutableListOf<String>()
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0))  // 取得 name 欄位值
        }
        cursor.close()
        return list
    }

}
