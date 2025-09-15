package com.example.drinksmachine

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.room.Database
import java.io.File
import java.io.FileOutputStream


class OptionDBHelper(private val context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "drinksOption.db"
        private const val DB_VERSION = 1
    }

    private val dbPath: String = context.getDatabasePath(DB_NAME).path

    // 核心修正：在建構子中執行一次性檢查和複製
    // 這是確保資料庫在任何操作前已準備好的最佳時機。
    init {
        checkAndCopyDatabase()
    }

    // onCreate 只在資料庫檔案首次被建立時被呼叫
    override fun onCreate(db: SQLiteDatabase?) {
        copyDatabase()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // TODO: 處理資料庫升級
    }

    /**
     * 檢查資料庫檔案是否存在，如果不存在則從 assets 資料夾複製。
     * 這個方法只在應用程式第一次啟動時執行一次。
     */
    private fun checkAndCopyDatabase() {
        val dbFile = File(dbPath)
        // 只有當資料庫檔案不存在時才進行複製
//        if (!dbFile.exists()) {
            copyDatabase()
//        }
    }

    private fun copyDatabase() {
        try {
            context.assets.open(DB_NAME).use { input ->
                File(dbPath).apply { parentFile?.mkdirs() }
                FileOutputStream(dbPath).use { output ->
                    val buffer = ByteArray(1024)
                    var length: Int
                    while (input.read(buffer).also { length = it } > 0) {
                        output.write(buffer, 0, length)
                    }
                    output.flush()
                }
            }
            Log.i("DB_Function", "資料庫複製完成: $dbPath")
        } catch (e: Exception) {
            Log.e("DB_Function", "資料庫複製失敗", e)
        }
    }

    fun getOptionsBySubType(subType: String): List<String> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT tw FROM OPTIONS WHERE subType = ?", arrayOf(subType))
        val list = mutableListOf<String>()
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0))
        }
        cursor.close()
        return list
    }

    fun getOptionsByMainType(mainType: String): List<String> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT tw FROM OPTIONS WHERE mainType = ?", arrayOf(mainType))
        val list = mutableListOf<String>()
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0))
        }
        cursor.close()
        return list
    }

    /**新增此函式，用於獲取所有點擊次數大於零的選項*/
    fun getSelectedOptionsFromDB(): List<Pair<String, Int>> {
        val db = this.readableDatabase
        val list = mutableListOf<Pair<String, Int>>()
        // 查詢 tw 和 click_count 欄位，條件是 click_count 大於 0
        val cursor = db.rawQuery("SELECT tw, countTemp FROM OPTIONS WHERE countTemp > 0", null)
        while (cursor.moveToNext()) {
            val tw = cursor.getString(0)
            val count = cursor.getInt(1)
            list.add(Pair(tw, count))
        }
        cursor.close()
        return list
    }

    fun getOptionsByType(type: String): List<String> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT tw FROM OPTIONS WHERE type = ?", arrayOf(type))
        val list = mutableListOf<String>()
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0))
        }
        cursor.close()
        return list
    }

    /**抓columnName ="XXX"的不重複值*/
    fun getDistinctOptionsByColumn(columnName: String): List<String> {
        val db = this.readableDatabase
        val list = mutableListOf<String>()

        // DISTINCT 讓結果不重複
        val cursor = db.rawQuery("SELECT DISTINCT $columnName FROM OPTIONS", null)
        while (cursor.moveToNext()) {
            val value = cursor.getString(0)
            if (!value.isNullOrEmpty()) list.add(value)
        }
        cursor.close()
        return list
    }
}


//class OptionDBHelper(context: Context) :
//    SQLiteOpenHelper(context, "options.db", null, 1) {
//
//    override fun onCreate(db: SQLiteDatabase) {
//        db.execSQL(
//            "CREATE TABLE options (" +
//                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                    "name TEXT NOT NULL," +
//                    "type TEXT NOT NULL)"
//        )
//
//        // 預設插入資料
//        db.execSQL("INSERT INTO options (name, type) VALUES ('BlackTea', 'Drink')")
//        db.execSQL("INSERT INTO options (name, type) VALUES ('GreenTea', 'Drink')")
//        db.execSQL("INSERT INTO options (name, type) VALUES ('Milk', 'Drink')")
//        db.execSQL("INSERT INTO options (name, type) VALUES ('Chacolate', 'Drink')")
//        db.execSQL("INSERT INTO options (name, type) VALUES ('Coffee', 'Drink')")
//
//        db.execSQL("INSERT INTO options (name, type) VALUES ('Pear', 'Topping')")
//        db.execSQL("INSERT INTO options (name, type) VALUES ('Coconut', 'Topping')")
//        db.execSQL("INSERT INTO options (name, type) VALUES ('GrassJelly', 'Topping')")
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL("DROP TABLE IF EXISTS options")
//        onCreate(db)
//    }
//
//    // 根據指定的類型（TypeA / TypeB）查詢對應的 label 並回傳清單
//    // 根據指定的類型（Drink / Topping）查詢對應的選項
//    fun getOptionsByType(type: String): List<String> {
//        val db = readableDatabase
//        val cursor = db.rawQuery("SELECT name FROM options WHERE type = ?", arrayOf(type))
//        val list = mutableListOf<String>()
//        while (cursor.moveToNext()) {
//            list.add(cursor.getString(0))  // 取得 name 欄位值
//        }
//        cursor.close()
//        return list
//    }
//}