package com.example.sumativa

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Definición de la base de datos
class UserDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_PHONE TEXT,
                $COLUMN_USERNAME TEXT UNIQUE,
                $COLUMN_PASSWORD TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // Agregar usuario
    fun addUser(name: String, phone: String, username: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_PHONE, phone)
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
        }
        return db.insert(TABLE_USERS, null, values)
    }

    // Verificar usuario en el login
    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun getAllUsers(): List<HomeActivity.Persona> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_USERS", null)
        val users = mutableListOf<HomeActivity.Persona>()
        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val avatar = R.drawable.avatar2 // Aquí puedes agregar lógica para diferentes avatares si lo deseas
                users.add(HomeActivity.Persona(nombre, avatar))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return users
    }


    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Users.db"
        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_PASSWORD = "password"
    }
}
