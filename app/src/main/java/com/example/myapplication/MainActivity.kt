package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val clearButton by lazy {findViewById<Button>(R.id.btn_clear)}
    private val addButton by lazy {findViewById<Button>(R.id.btn_add)}
    private val runButton by lazy {findViewById<Button>(R.id.btn_run)}
    private val numpick by lazy {findViewById<NumberPicker>(R.id.np_num)}

    private val numTextViewList : List<TextView> by lazy{
        listOf<TextView> (
            findViewById(R.id.tv_num1),
            findViewById(R.id.tv_num1),
            findViewById(R.id.tv_num1),
            findViewById(R.id.tv_num1),
            findViewById(R.id.tv_num1),
            findViewById(R.id.tv_num1)
        )
    }

    private var didRun = false
    private val picknumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numpick.minValue = 1
        numpick.maxValue = 45

        initAddButton()
        initRunButton()
        initClearButton()
    }
        private fun initAddButton(){
            addButton.setOnClickListener() {
                when{
                    didRun -> showToast("초기화 후에 시도해주세요")
                    picknumberSet.size >= 5 -> showToast("숫자는 최대 5개까지 선택할 수 있습니다.")
                    picknumberSet.contains(numpick.value) -> showToast("이미 선택된 숫자 입니다.")
                    else -> {
                        val textView = numTextViewList[picknumberSet.size]
                        textView.isVisible = true
                        textView.text = numpick.value.toString()

                        setNumBack(numpick.value, textView)
                        picknumberSet.add(numpick.value)

                    }
                }
            }
        }

    private fun initClearButton() {
        clearButton.setOnClickListener {
        numTextViewList.forEach{it.isVisible = false}
            didRun = false
            numpick.value = 1
      }
    }


    private fun initRunButton(){
        runButton.setOnClickListener{
            val list = getRandom()

            didRun = true
            list.forEachIndexed {index, number ->
                val textView = numTextViewList[index]
                textView.text = number.toString()
                textView.isVisible = true
                setNumBack(number, textView)
            }
        }
    }

    private fun getRandom(): List<Int> {
        val numbers = (1..45).filter { it !in picknumberSet }
        return (picknumberSet + numbers.shuffled().take(6-picknumberSet.size)).sorted()
    }

    private fun setNumBack(number:Int, textView: TextView){
        val background = when(number) {
            in 1..10 -> R.drawable.circle_yellow
            in 11..20 -> R.drawable.circle_blru
            in 21..30 -> R.drawable.circle_red
            in 31..40 -> R.drawable.circle_grey
            else -> R.drawable.circle_green
        }
            textView.background = ContextCompat.getDrawable(this,background)

    }



        private fun showToast(message: String) {
            Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
        }


}