package com.example.scheduler

import android.content.Intent
import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_exam.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    var answer: String? = null
    var answer2: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ExamDataDB = GlobalApplication.appDataBaseInstance.ExamDataInterface()

//        val ExamDataDB =
//            Room.databaseBuilder(this, AppDatabase::class.java, "db")
//                .addMigrations(AppDatabase.MIGRATION_1_2).addMigrations(AppDatabase.MIGRATION_2_3)
//                .allowMainThreadQueries() // <- 개발시에만 적용
//                .build()
//
        val assetManager: AssetManager = resources.assets
        val inputStream: InputStream = assetManager.open("ExamData.txt")
        ExamDataDB.deleteAll()
        inputStream.bufferedReader().readLines().forEach {
            var token = it.split("\t")
            answer = token[6].replace("[^0-9]".toRegex(), "")

            if (answer!!.toInt() > 10)
                answer2 = answer!!.toInt() % 10

            var input = ExamDataTable(
                token[0].substring(0, TextUtils.indexOf(token[0], ".")).toInt(),
                token[0].substring(TextUtils.indexOf(token[0], ".") + 1, token[0].length),
                token[1],
                token[2],
                token[3],
                token[4],
                answer!!.substring(0).toInt(),
                answer2
            )
            ExamDataDB.insert(input)
        }
        println("MAIN ACTIVITY) ANSWER1: $answer, ANSWER2: $answer2")

        btn_start.setOnClickListener{
            val intent: Intent = Intent(this, ActivityExam::class.java)
//            intent.putExtra("answer1", answer!!.substring(0).toInt())
//            intent.putExtra("answer2", answer2)
            startActivity(intent)
        }


    }
}