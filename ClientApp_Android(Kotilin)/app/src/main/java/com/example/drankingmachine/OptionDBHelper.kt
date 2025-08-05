package com.example.drankingmachine

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
        db.execSQL("INSERT INTO options (name, type) VALUES ('紅茶', 'Drink')")
        db.execSQL("INSERT INTO options (name, type) VALUES ('綠茶', 'Drink')")
        db.execSQL("INSERT INTO options (name, type) VALUES ('奶茶', 'Drink')")

        db.execSQL("INSERT INTO options (name, type) VALUES ('珍珠', 'Topping')")
        db.execSQL("INSERT INTO options (name, type) VALUES ('椰果', 'Topping')")
        db.execSQL("INSERT INTO options (name, type) VALUES ('仙草', 'Topping')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS options")
        onCreate(db)
    }

    // 根據指定的類型（TypeA / TypeB）查詢對應的 label 並回傳清單
    fun getOptionsByType(type: String): List<String> {
        val db = readableDatabase

        // 執行 SQL 查詢語句，取得指定類型的選項
        val cursor = db.rawQuery("SELECT label FROM options WHERE type = ?", arrayOf(type))

        val list = mutableListOf<String>()
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0))  // 取得第一欄（label）加入清單
        }
        cursor.close()

        return list
    }
}
