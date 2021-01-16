package com.jkhome.simplememo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jkhome.simplememo.data.Diary

class DiaryAdapter (private var diaryList: MutableList<Diary>) : RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryAdapter.DiaryViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val shouldAttachToParentImmediately = false
        val view = inflater.inflate(R.layout.recycler_diary_item, parent, shouldAttachToParentImmediately)
        return DiaryViewHolder(view)
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
            TODO("Not yet implemented")
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