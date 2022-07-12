package com.example.scheduler

import android.app.Activity
import android.content.Context
import android.content.res.AssetManager
import android.os.Bundle
import android.text.TextUtils.indexOf
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.room.Room
import androidx.room.util.TableInfo
import com.example.scheduler.AppDatabase.Companion.MIGRATION_2_3
import kotlinx.android.synthetic.main.fragment_exam.*
import java.io.InputStream
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class FragmentExam : Fragment() {
    var checked: Array<Boolean> = arrayOf(false, false, false, false)
    var answer1: Int? = null
    var answer2: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("TAG", "onAttach")
    }

    override fun onStart() {
        super.onStart()
        Log.e("TAG", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("TAG", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("TAG", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("TAG", "onStop")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("TAG", "onCreateView")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exam, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAG", "onViewCreated")
        val context: Context = view.context

        initData()
    }

    fun initData() {
        Log.e("TAG", "setData")

        val ExamDataDB = GlobalApplication.appDataBaseInstance.ExamDataInterface()

//        val assetManager: AssetManager = resources.assets
//        val inputStream: InputStream = assetManager.open("ExamData.txt")
//        ExamDataDB.deleteAll()
//        inputStream.bufferedReader().readLines().forEach {
//            var token = it.split("\t")
//            val answer: String = token[6].replace("[^0-9]".toRegex(), "")
//            println("asdfasdfsafadfasdfsaddfasfdsafsafdsafsadf" + answer)
//
//            var input = ExamDataTable(
//                token[0].substring(0, indexOf(token[0], ".")).toInt(),
//                token[0].substring(indexOf(token[0], ".") + 1, token[0].length),
//                token[1],
//                token[2],
//                token[3],
//                token[4],
//                answer.substring(0).toInt(),
//                answer2
//            )
//            ExamDataDB.insert(input)
//        }

        val num = arguments?.get("num") as Int
        val examData: List<ExamDataTable> = ExamDataDB.getRandom(num)
        val data: ExamDataTable = examData.get(0)

        Log.e("TAG", "examData.size = " + examData.size)

        println("FRAGMENT EXAM) NUM: $num")

        tv_question.setText(data.question)
        check_1.setText(data.ask1)
        check_2.setText(data.ask2)
        check_3.setText(data.ask3)
        check_4.setText(data.ask4)

        btn_check.setOnClickListener {
            val answers: Array<Boolean> = arrayOf(false, false, false, false)
            var p1: Int = data.answer1 - 1
            var p2: Int? = null
            if (answer2 != null)
                p2 = answer2!! - 1
            else
                p2 = p1

            println("P1: ${p1.toString()}, P2: ${p2.toString()}")

            if (p1 > 10) {
                p2 = p1 % 10
                p1 = p1 / 10 -1
            }

            answers[p1] = true
            answers[p2] = true

            checked[0] = check_1.isChecked
            checked[1] = check_2.isChecked
            checked[2] = check_3.isChecked
            checked[3] = check_4.isChecked

//            if (checked)
//                Toast.makeText(context, "정답을 하나이상 체크해주세요", Toast.LENGTH_LONG).show()
//            else
            if (answers.contentEquals(checked))
                right()
            else{
                wrong()
                println("P1: ${p1.toString()}, P2: ${p2.toString()}")
                println("ANSWERS: ${answers[0].toString()}, ${answers[1].toString()}, ${answers[2].toString()}, ${answers[3].toString()}")
                println("CHECKED: ${checked[0].toString()}, ${checked[1].toString()}, ${checked[2].toString()}, ${checked[3].toString()}")
            }



        }
    }

    fun right() {
        println("정답")
        img_result.setImageResource(R.mipmap.img_right)
        Toast.makeText(context, " 정답입니다 !! ", Toast.LENGTH_LONG).show()
    }

    fun wrong() {
        println("오답")
        img_result.setImageResource(R.mipmap.img_wrong)
        Toast.makeText(context, " 오답입니다 !! ", Toast.LENGTH_LONG).show()
    }

}