package com.example.astrobrowser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class History : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history)
        val receivedIntent = intent
        val cardNum = receivedIntent.getStringExtra("cardNum")
        val filesList = populateRecyclerView(cardNum.toString())
        val rv: RecyclerView = findViewById(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(false)
        rv.adapter = Adapter(filesList)
    }
    // This adds file information passed from the intent to cards so that
    // it can be displayed in a recycler view.
    private fun populateRecyclerView(cardNum: String): ArrayList<Data> {
        val list = ArrayList<Data>()
        var tempDescription: String = ""

        for(i in 0 until cardNum.length){
            if (cardNum.get(i) == '?') {
                list += Data(tempDescription)
                tempDescription = ""
            }
            if (cardNum.get(i) != '?') {
                tempDescription += cardNum.get(i)
            }
        }

        repeat(20) {
            list += Data("Dummy data to demonstrate\n a recycler view")
        }

        return list
    }
}