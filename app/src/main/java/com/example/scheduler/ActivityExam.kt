package com.example.scheduler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_exam.*
import kotlin.random.Random

class ActivityExam : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)
        val intent: Intent = getIntent()
        var questions = IntArray(10, {0})
        for (i in 0..9){
            val r: Int = Random.nextInt(722) + 1
            questions[i] = r
        }
        val fragmentExam = FragmentExam()


        val bundle = Bundle()
        var num = 0
        bundle.putInt("num", questions[num])
        fragmentExam.arguments = bundle

        supportFragmentManager.beginTransaction().add(R.id.frame_question, fragmentExam).commit()

        btn_next.setOnClickListener{
            num++
            bundle.clear()
            bundle.putInt("num", questions[num])
            val nextFragmentExam = FragmentExam()
            nextFragmentExam.arguments = bundle
            val fm = supportFragmentManager.beginTransaction()
            fm.replace(R.id.frame_question, nextFragmentExam).commit()
            println("ACTIVITY EXAM) QUESTIONS : ${questions[num]}")
            println("ACTIVITY EXAM) NUM: $num")
        }
    }
}