package com.jkhome.simplememo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jkhome.simplememo.databinding.ActivityDiaryBinding

class DiaryActivity : AppCompatActivity() {

    private val binding: ActivityDiaryBinding by lazy { ActivityDiaryBinding.inflate(layoutInflater) }
    private  lateinit var linearLayoutManager : LinearLayoutManager
    private var diaryList : ArrayList<Diary> = ArrayList()
    private  lateinit var adapter: DiaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        diaryList.add(Diary("2020","firstDate","Hello"))
        diaryList.add(Diary("2020","secondDate","Hello"))
        diaryList.add(Diary("2020","thirdDate","Hello"))

        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        adapter = DiaryAdapter(diaryList)
        binding.recyclerView.adapter = adapter


    }

    fun createNewDiary(view: View){
        val intent = Intent(this, NewDiary::class.java)
        startActivity(intent)

    }
}