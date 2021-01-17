package com.jkhome.simplememo

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.jkhome.simplememo.data.DatabaseManager.DiaryEntry.COLUMN_DATE
import com.jkhome.simplememo.data.DatabaseManager.DiaryEntry.COLUMN_DIARY
import com.jkhome.simplememo.data.DatabaseManager.DiaryEntry.COLUMN_TITLE
import com.jkhome.simplememo.data.DatabaseManager.DiaryEntry.TABLE_NAME
import com.jkhome.simplememo.data.DatabaseManager.DiaryEntry._ID
import com.jkhome.simplememo.data.DiaryDBHelper
import java.text.SimpleDateFormat
import java.util.*

class NewDiary : AppCompatActivity() {
    
    private var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_diary)



        id = intent.getIntExtra("rowId", 0)
        if (id != 0)
        {
            readDiary(id)
        }
        else
        {
            val currentDate = SimpleDateFormat("EEE, d MMM yyyy")
            findViewById<TextView>(R.id.current_date_diary).text = currentDate.format(Date())
        }

    }

    private fun readDiary(id: Int) {
        val mDBHelper = DiaryDBHelper(this)
        val db = mDBHelper.readableDatabase
        val projection = arrayOf(COLUMN_DATE, COLUMN_TITLE, COLUMN_DIARY)
        val selection = "$_ID = ?"
        val selectionArgs = arrayOf("$id")
        val cursor = db.query(
            TABLE_NAME,
            projection,
            selection,
            selectionArgs,null,null,null
        )
        val dateColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_DATE)
        val titleColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_TITLE)
        val diaryColumnIndex = cursor.getColumnIndexOrThrow(COLUMN_DIARY)
        while (cursor.moveToNext()){
            val currentDate = cursor.getString(dateColumnIndex)
            val currentTitle = cursor.getString(titleColumnIndex)
            val currentDiary = cursor.getString(diaryColumnIndex)

            findViewById<TextView>(R.id.current_date_diary).text = currentDate
            findViewById<EditText>(R.id.title_diary).setText(currentTitle)
            findViewById<EditText>(R.id.diary_text).setText(currentDiary)
        }

        cursor.close()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.action_bar_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId){
            R.id.save_diary ->{

                if(id == 0)
                    insertDiary()
                else
                    updateDiary()

                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateDiary() {
        val mDBHelper = DiaryDBHelper(this)
        val db = mDBHelper.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,findViewById<EditText>(R.id.title_diary).text.toString())
            put(COLUMN_DIARY, findViewById<EditText>(R.id.diary_text).text.toString())
        }
        db.update(TABLE_NAME,values,"$_ID = $id")
    }

    private fun insertDiary() {
        val dateString = findViewById<TextView>(R.id.current_date_diary).text.toString()
        val titleString = findViewById<EditText>(R.id.title_diary).text.toString()
        val diary = findViewById<EditText>(R.id.diary_text).text.toString()
        val mDBHelper = DiaryDBHelper(this)
        val db = mDBHelper.writableDatabase
        val value = ContentValues().apply {
            put(COLUMN_DATE,dateString)
            put(COLUMN_TITLE,titleString)
            put(COLUMN_DIARY,diary)
        }
        val rowId = db.insert(TABLE_NAME,null,value)
        if(rowId.equals(-1)){
            Toast.makeText(this,"Problem inserting new entry",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
        }

    }
}