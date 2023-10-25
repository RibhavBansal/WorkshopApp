package com.example.workshopapp.Database
//import android.content.ContentValues
//import android.content.Context
//import android.database.Cursor
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//import android.annotation.SuppressLint
//
//class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
//
//    companion object {
//
//        @Volatile
//        private var instance: DatabaseHelper? = null
//        fun getInstance(context: Context): DatabaseHelper {
//            return instance ?: synchronized(this) {
//                instance ?: DatabaseHelper(context).also { instance = it }
//            }
//        }
//
//        private const val DATABASE_VERSION = 1
//        private const val DATABASE_NAME = "WorkshopDatabase.db"
//
//        // Table name and columns for workshops
//        private const val TABLE_WORKSHOPS = "workshops"
//        private const val COLUMN_ID = "id"
//        private const val COLUMN_TITLE = "title"
//        private const val COLUMN_DESCRIPTION = "description"
//
//        // Table name and columns for users
//        private const val TABLE_USERS = "users"
//        private const val COLUMN_FULL_NAME = "fullName"
//        private const val COLUMN_EMAIL = "email"
//        private const val COLUMN_PASSWORD = "password"
//    }
//
//    override fun onCreate(db: SQLiteDatabase) {
//        val createTableWorkshopsQuery = "CREATE TABLE $TABLE_WORKSHOPS " +
//                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "$COLUMN_TITLE TEXT," +
//                "$COLUMN_DESCRIPTION TEXT);"
//        db.execSQL(createTableWorkshopsQuery)
//
//        // Insert some initial workshop entries for testing
////        insertInitialWorkshops(db)
//
//        val createTableUsersQuery = "CREATE TABLE $TABLE_USERS " +
//                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "$COLUMN_FULL_NAME TEXT," +
//                "$COLUMN_EMAIL TEXT," +
//                "$COLUMN_PASSWORD TEXT);"
//        db.execSQL(createTableUsersQuery)
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL("DROP TABLE IF EXISTS $TABLE_WORKSHOPS")
//        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
//        onCreate(db)
//    }
//
//    fun addWorkshop(workshop: Workshop) {
//        val values = ContentValues()
//        values.put(COLUMN_TITLE, workshop.title)
//        values.put(COLUMN_DESCRIPTION, workshop.description)
//
//        val db = this.writableDatabase
//        db.insert(TABLE_WORKSHOPS, null, values)
//        db.close()
//    }
//
//    fun getAllWorkshops(): ArrayList<Workshop> {
//        val workshops = ArrayList<Workshop>()
//        val query = "SELECT * FROM $TABLE_WORKSHOPS"
//        val db = this.readableDatabase
//        val cursor: Cursor? = db.rawQuery(query, null)
//        cursor?.use {
//            if (it.moveToFirst()) {
//                val idColumnIndex = it.getColumnIndex(COLUMN_ID)
//                val titleColumnIndex = it.getColumnIndex(COLUMN_TITLE)
//                val descriptionColumnIndex = it.getColumnIndex(COLUMN_DESCRIPTION)
//
//                do {
//                    val id = it.getInt(idColumnIndex)
//                    val title = it.getString(titleColumnIndex)
//                    val description = it.getString(descriptionColumnIndex)
//                    workshops.add(Workshop(id, title, description))
//                } while (it.moveToNext())
//            }
//        }
//        return workshops
//    }
//
//    fun insertInitialWorkshops() {
//        val db = this.writableDatabase
//        // Insert some initial workshop entries for testing
//        addWorkshop(Workshop(1, "Workshop 1", "Description for Workshop 1"))
//        addWorkshop(Workshop(2, "Workshop 2", "Description for Workshop 2"))
//        addWorkshop(Workshop(3, "Workshop 3", "Description for Workshop 3"))
//        db.close()
//    }
//
//    fun registerUser(user: User) {
//        val values = ContentValues()
//        values.put(COLUMN_FULL_NAME, user.fullName)
//        values.put(COLUMN_EMAIL, user.email)
//        values.put(COLUMN_PASSWORD, user.password)
//
//        val db = this.writableDatabase
//        db.insert(TABLE_USERS, null, values)
//        db.close()
//    }
//
//    // Check if the user has already applied for a workshop
//    fun hasUserApplied(userId: Int, workshopId: Int): Boolean {
//        val db = this.readableDatabase
//        val query = "SELECT * FROM $TABLE_WORKSHOPS " +
//                "WHERE $COLUMN_USER_ID = ? AND $COLUMN_WORKSHOP_ID = ?"
//        val cursor = db.rawQuery(query, arrayOf(userId.toString(), workshopId.toString))
//        cursor.use {
//            return it.count > 0
//        }
//    }
//
//    // Record a workshop application for a user
//    fun recordWorkshopApplication(userId: Int, workshopId: Int) {
//        val db = this.writableDatabase
//        val values = ContentValues()
//        values.put(COLUMN_USER_ID, userId)
//        values.put(COLUMN_WORKSHOP_ID, workshopId)
//        db.insert(TABLE_WORKSHOP_APPLICATIONS, null, values)
//    }
//
//
//    @SuppressLint("Range")
//    fun login(email: String, password: String): User? {
//        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
//        val db = this.readableDatabase
//        val cursor = db.rawQuery(query, arrayOf(email, password))
//        cursor.use {
//            if (it.moveToFirst()) {
//                val id = it.getInt(it.getColumnIndex(COLUMN_ID))
//                val fullName = it.getString(it.getColumnIndex(COLUMN_FULL_NAME))
//                val userEmail = it.getString(it.getColumnIndex(COLUMN_EMAIL))
//                val userPassword = it.getString(it.getColumnIndex(COLUMN_PASSWORD))
//                return User(id, fullName, userEmail, userPassword)
//            }
//        }
//        return null
//    }
//
//}

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        @Volatile
        private var instance: DatabaseHelper? = null
        fun getInstance(context: Context): DatabaseHelper {
            return instance ?: synchronized(this) {
                instance ?: DatabaseHelper(context).also { instance = it }
            }
        }

        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "WorkshopApp.db"

        // Table name and columns for workshops
        private const val TABLE_WORKSHOPS = "workshops"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESCRIPTION = "description"

        // Table name and columns for workshop applications
        private const val TABLE_WORKSHOP_APPLICATIONS = "workshop_applications"
        private const val COLUMN_USER_ID = "user_id"
        private const val COLUMN_WORKSHOP_ID = "workshop_id"

        // Define table and column names for users
        private const val TABLE_USERS = "users"
        private const val COLUMN_FULL_NAME = "full_name"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
    }

    fun getDatabase(): SQLiteDatabase {
        return this.writableDatabase
    }

    fun insertInitialWorkshops() {
        val db = this.writableDatabase
        // Insert some initial workshop entries for testing
        addWorkshop(Workshop(1, "Workshop 1", "Description for Workshop 1"),db)
        addWorkshop(Workshop(2, "Workshop 2", "Description for Workshop 2"),db)
        addWorkshop(Workshop(3, "Workshop 3", "Description for Workshop 3"),db)
        db.close()
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create the workshops table
        val createWorkshopsTableQuery = "CREATE TABLE $TABLE_WORKSHOPS " +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_TITLE TEXT," +
                "$COLUMN_DESCRIPTION TEXT);"
        db.execSQL(createWorkshopsTableQuery)

        // Create the workshop applications table
        val createApplicationsTableQuery = "CREATE TABLE $TABLE_WORKSHOP_APPLICATIONS " +
                "($COLUMN_USER_ID INTEGER," +
                "$COLUMN_WORKSHOP_ID INTEGER);"
        db.execSQL(createApplicationsTableQuery)

        // create the users table
        val createTableUsersQuery =  "CREATE TABLE $TABLE_USERS " +
                    "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_FULL_NAME TEXT, " +
                    "$COLUMN_EMAIL TEXT, " +
                    "$COLUMN_PASSWORD TEXT);"
        db.execSQL(createTableUsersQuery)
    }

    fun fetchAppliedWorkshopsFromDatabase(userId: Int, db: SQLiteDatabase): List<Workshop> {
        val appliedWorkshops = ArrayList<Workshop>()
        val query = "SELECT w.* FROM $TABLE_WORKSHOPS w " +
                "INNER JOIN $TABLE_WORKSHOP_APPLICATIONS wa ON w.$COLUMN_ID = wa.$COLUMN_WORKSHOP_ID " +
                "WHERE wa.$COLUMN_USER_ID = ?"

        val cursor: Cursor? = db.rawQuery(query, arrayOf(userId.toString()))

        cursor?.use {
            if (it.moveToFirst()) {
                val idColumnIndex = it.getColumnIndex(COLUMN_ID)
                val titleColumnIndex = it.getColumnIndex(COLUMN_TITLE)
                val descriptionColumnIndex = it.getColumnIndex(COLUMN_DESCRIPTION)

                do {
                    val id = it.getInt(idColumnIndex)
                    val title = it.getString(titleColumnIndex)
                    val description = it.getString(descriptionColumnIndex)
                    appliedWorkshops.add(Workshop(id, title, description))
                } while (it.moveToNext())
            }
        }

        return appliedWorkshops
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop existing tables and recreate them if necessary
        db.execSQL("DROP TABLE IF EXISTS $TABLE_WORKSHOPS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_WORKSHOP_APPLICATIONS")
        onCreate(db)
    }

    // Add a workshop to the database
    fun addWorkshop(workshop: Workshop, db: SQLiteDatabase) {
        val values = ContentValues()
        values.put(COLUMN_TITLE, workshop.title)
        values.put(COLUMN_DESCRIPTION, workshop.description)
        db.insert(TABLE_WORKSHOPS, null, values)
    }

    // Get a list of all workshops
    fun getAllWorkshops(db: SQLiteDatabase): List<Workshop> {
        val workshops = ArrayList<Workshop>()
        val query = "SELECT * FROM $TABLE_WORKSHOPS"
        val cursor: Cursor? = db.rawQuery(query, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val idColumnIndex = it.getColumnIndex(COLUMN_ID)
                val titleColumnIndex = it.getColumnIndex(COLUMN_TITLE)
                val descriptionColumnIndex = it.getColumnIndex(COLUMN_DESCRIPTION)

                do {
                    val id = it.getInt(idColumnIndex)
                    val title = it.getString(titleColumnIndex)
                    val description = it.getString(descriptionColumnIndex)
                    workshops.add(Workshop(id, title, description))
                } while (it.moveToNext())
            }
        }
        return workshops
    }

    // Check if the user has already applied for a workshop
    fun hasUserApplied(userId: Int, workshopId: Int, db: SQLiteDatabase): Boolean {
        val query = "SELECT * FROM $TABLE_WORKSHOP_APPLICATIONS " +
                "WHERE $COLUMN_USER_ID = ? AND $COLUMN_WORKSHOP_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString(), workshopId.toString()))
        cursor.use {
            return it.count > 0
        }
    }

    // Record a workshop application for a user
    fun recordWorkshopApplication(userId: Int, workshopId: Int, db: SQLiteDatabase) {
        val values = ContentValues()
        values.put(COLUMN_USER_ID, userId)
        values.put(COLUMN_WORKSHOP_ID, workshopId)
        db.insert(TABLE_WORKSHOP_APPLICATIONS, null, values)
    }

    fun registerUser(user: User) {
        val values = ContentValues()
        values.put(COLUMN_FULL_NAME, user.fullName)
        values.put(COLUMN_EMAIL, user.email)
        values.put(COLUMN_PASSWORD, user.password)
        val db = this.writableDatabase
        db.insert(TABLE_USERS, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun login(email: String, password: String): User? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        return cursor.use {
            if (it.moveToFirst()) {
                val id = it.getInt(it.getColumnIndex(COLUMN_ID))
                val fullName = it.getString(it.getColumnIndex(COLUMN_FULL_NAME))
                val userEmail = it.getString(it.getColumnIndex(COLUMN_EMAIL))
                val userPassword = it.getString(it.getColumnIndex(COLUMN_PASSWORD))
                User(id, fullName, userEmail, userPassword)
            } else {
                null // Return null if no user with the provided credentials is found
            }
        }
    }

}

