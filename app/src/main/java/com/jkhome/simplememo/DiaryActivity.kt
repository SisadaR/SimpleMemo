package com.jkhome.simplememo

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.jkhome.simplememo.data.DatabaseManager.DiaryEntry.COLUMN_DATE
import com.jkhome.simplememo.data.DatabaseManager.DiaryEntry.COLUMN_DIARY
import com.jkhome.simplememo.data.DatabaseManager.DiaryEntry.COLUMN_TITLE
import com.jkhome.simplememo.data.DatabaseManager.DiaryEntry.TABLE_NAME
import com.jkhome.simplememo.data.DatabaseManager.DiaryEntry._ID
import com.jkhome.simplememo.data.Diary
import com.jkhome.simplememo.data.DiaryDBHelper
import com.jkhome.simplememo.databinding.ActivityDiaryBinding

class DiaryActivity : AppCompatActivity() {

    private val binding: ActivityDiaryBinding by lazy { ActivityDiaryBinding.inflate(layoutInflater) }
    private  lateinit var linearLayoutManager : LinearLayoutManager
    private var diaryList : ArrayList<Diary> = ArrayList()
    private  lateinit var adapter: DiaryAdapter
    private  lateinit var mDBHelper: DiaryDBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        mDBHelper = DiaryDBHelper(this)

    }

    override fun onStart() {
        super.onStart()
        displayDataInfo()
    }

    private fun displayDataInfo() {

        diaryList.clear()

        val db = mDBHelper.readableDatabase
        val projection = arrayOf(_ID, COLUMN_DATE, COLUMN_TITLE, COLUMN_DIARY)
        val cursor: Cursor = db.query(TABLE_NAME,projection,null,null,null,null,null)
        val idColumnIndex = cursor.getColumnIndexOrThrow(_ID)
        val dataColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_DATE)
        val titleColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_TITLE)
        val diaryColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_DIARY)

        while (cursor.moveToNext()){
            val currentId= cursor.getInt(idColumnIndex)
            val currentDate= cursor.getString(dataColumnIndex)
            val currentTitle= cursor.getString(titleColumnIndex)
            val currentDiary= cursor.getString(diaryColumnIndex)

            diaryList.add(Diary(currentId,currentDate,currentTitle,currentDiary))
        }
        cursor.close()

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