package com.example.scheduler

import android.content.Context
import android.os.FileObserver.DELETE
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.security.AccessController.getContext

@Entity
data class ExamDataTable(
    @PrimaryKey val num: Int,
    val question: String,
    val ask1: String,
    val ask2: String,
    val ask3: String,
    val ask4: String,
    val answer1: Int,
    val answer2: Int?
)

@Dao
interface ExamDataInterface {

    @Query("SELECT * FROM ExamDataTable")
    fun getAll(): List<ExamDataTable>

    @Insert
    fun insert(ExamDataTable: ExamDataTable)

    @Query("DELETE FROM ExamDataTable")
    fun deleteAll()

    @Query("SELECT * FROM ExamDataTable WHERE num = :n")
    fun getRandom(n: Int): List<ExamDataTable>
}

@Database(entities = [ExamDataTable::class], exportSchema = false, version = 3)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var instance: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ExamDataTable ADD answer2 INTEGER")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE ExamDataTable RENAME COLUMN answer TO answer1")
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "db")
                    .fallbackToDestructiveMigration()
//                    addCallback(object : Callback() {
//                        override fun onCreate(db: SupportSQLiteDatabase) {
//                            super.onCreate(db)
//                        }
//                    })
                    .addMigrations(MIGRATION_1_2)
                    .addMigrations(MIGRATION_2_3)
                    .build()
            }.also {
                instance = it
            }

        }


    }

    abstract fun ExamDataInterface(): ExamDataInterface
}

class ExamData {

}