package com.jkhome.simplememo

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.jkhome.simplememo.data.DatabaseManager.DiaryEntry.TABLE_NAME
import com.jkhome.simplememo.data.DatabaseManager.DiaryEntry._ID
import com.jkhome.simplememo.data.Diary
import com.jkhome.simplememo.data.DiaryDBHelper

class DiaryAdapter (private var diaryList: MutableList<Diary>) : RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryAdapter.DiaryViewHolder {


        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val shouldAttachToParentImmediately = false
        val view = inflater.inflate(R.layout.recycler_diary_item, parent, shouldAttachToParentImmediately)
        val holder = DiaryViewHolder(view)

        view.findViewById<ImageButton>(R.id.delete_button).setOnClickListener {

            val position =  holder.adapterPosition
            Toast.makeText(context,"click on id $position",Toast.LENGTH_LONG).show()
            val mDBHelper = DiaryDBHelper(view.context)
            val db = mDBHelper.writableDatabase
            val selection = "$_ID = ?"
            val selectionArgs = arrayOf("${(diaryList[position].id)}")
            db.delete(TABLE_NAME,selection,selectionArgs)
            diaryList.removeAt(position)
            notifyDataSetChanged()
        }

        return holder
    }

    override fun onBindViewHolder(holder: DiaryAdapter.DiaryViewHolder, position: Int) {
        val item = diaryList[position]
        holder.bindDiary(item)

    }

    override fun getItemCount(): Int {
        return diaryList.size
    }

    class DiaryViewHolder (v:View) : RecyclerView.ViewHolder(v), View.OnClickListener
    {
        private var view:View = v
        private lateinit var  diary: Diary
        private var date: TextView
        private var title:TextView

        override fun onClick(v: View?) {
            val context = itemView.context
            val intent = Intent(context, NewDiary::class.java)
            intent.putExtra("rowId", diary.id)
            context.startActivity(intent)

        }

        init {
            date = view.findViewById(R.id.date_recycler_item)
            title = view.findViewById(R.id.title_recycler_item)
            view.setOnClickListener(this)
        }

        fun bindDiary(diary: Diary)
        {
            this.diary = diary
            date.text = diary.date
            title.text = diary.title
        }
    }
}