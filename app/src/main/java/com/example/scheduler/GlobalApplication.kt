package com.example.scheduler

import android.app.Application
import androidx.room.Room

class GlobalApplication : Application() {
    companion object {
        lateinit var appInstance: GlobalApplication
            private set
        lateinit var appDataBaseInstance: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this

        appDataBaseInstance =
            Room.databaseBuilder(appInstance.applicationContext, AppDatabase::class.java, "db")
                .addMigrations(AppDatabase.MIGRATION_1_2).addMigrations(AppDatabase.MIGRATION_2_3)
                .allowMainThreadQueries() // <- 개발시에만 적용
                .build()
    }
}