package dev.nikita_chernikov.lab3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.getStringOrNull
import java.util.Date
import androidx.core.database.sqlite.transaction

class SQLiteManager( context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object
    {
        private const val DATABASE_NAME: String = "ClassmatesDB"
        private const val DATABASE_VERSION: Int = 2

        private const val TABLE_NAME: String = "Classmates"

        private const val ID_FIELD: String = "Id"
        private const val FIRST_NAME_FIELD: String = "FirstName"
        private const val LAST_NAME_FIELD: String = "LastName"
        private const val PATRONYMIC_FIELD: String = "PATRONYMIC"
        private const val CREATED_AT_FIELD: String = "CreatedAt"

        @Volatile
        private var INSTANCE: SQLiteManager? = null

        fun getInstance(context: Context): SQLiteManager {
            return INSTANCE ?: synchronized(this) {
                val instance = SQLiteManager(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL ( """CREATE TABLE $TABLE_NAME (
            $ID_FIELD INTEGER PRIMARY KEY AUTOINCREMENT,
            $FIRST_NAME_FIELD TEXT NOT NULL,
            $LAST_NAME_FIELD TEXT NOT NULL,
            $PATRONYMIC_FIELD TEXT,
            $CREATED_AT_FIELD INTEGER NOT NULL)""".trimMargin())
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun seed() {
        val classmates = arrayOf(
            Classmate(firstName ="Максим", lastName = "Стельмашенко", patronymic = "Максимович"),
            Classmate(firstName = "Ігор", lastName = "Філоненко", patronymic = "Русланович"),
            Classmate(firstName = "Олександр", lastName = "Черненко", patronymic = "Євгенович"),
            Classmate(firstName = "Нікіта", lastName = "Черніков", patronymic = "Миколайович"),
            Classmate(firstName = "Володимир", lastName = "Галятін", patronymic = "Сергійович"))

        val db = writableDatabase
        db.transaction {
            try {
                db.execSQL("DELETE FROM $TABLE_NAME")

                for (classmate in classmates) {
                    val values = ContentValues().apply {
                        put(FIRST_NAME_FIELD, classmate.firstName)
                        put(LAST_NAME_FIELD, classmate.lastName)
                        put(PATRONYMIC_FIELD, classmate.patronymic)
                        put(CREATED_AT_FIELD, classmate.createdAt.time)
                    }

                    insert(TABLE_NAME, null, values)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addClassmate(classmate: Classmate) {
        val values = ContentValues().apply {
            put(FIRST_NAME_FIELD, classmate.firstName)
            put(LAST_NAME_FIELD, classmate.lastName)
            put(PATRONYMIC_FIELD, classmate.patronymic)
            put(CREATED_AT_FIELD, classmate.createdAt.time)
        }

        val db = writableDatabase
        db.insert(TABLE_NAME, null, values)
    }

    fun getLastClassmate() : Classmate?
    {
        val db = this.readableDatabase

        var classmate: Classmate? = null

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME ORDER BY $ID_FIELD DESC LIMIT 1",
            null
        )

        cursor.use {
            if (it.moveToFirst()) {
                classmate = Classmate(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getStringOrNull(3),
                    Date(cursor.getLong(4))
                )
            }
        }

        return classmate
    }

    fun updateClassmate(classmate: Classmate)
    {
        val values = ContentValues().apply {
            put(FIRST_NAME_FIELD, classmate.firstName)
            put(LAST_NAME_FIELD, classmate.lastName)
            put(PATRONYMIC_FIELD, classmate.patronymic)
        }

        val db = writableDatabase
        db.update(
            TABLE_NAME,
            values,
            "$ID_FIELD = ?",
            arrayOf(classmate.id.toString())
        )
    }

    fun getClassmates() : ArrayList<Classmate> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        val classmates = ArrayList<Classmate>()
        cursor.use {
            while (cursor.moveToNext())
            {
                val classmate = Classmate(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getStringOrNull(3),
                    Date(cursor.getLong(4))
                )
                classmates.add(classmate)
            }
        }

        return classmates
    }
}
