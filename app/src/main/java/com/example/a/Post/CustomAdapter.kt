package com.example.a.Post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a.R
import org.w3c.dom.Text

class CustomAdapter(private val postlist: ArrayList<Post>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val icon: ImageView
        val title: TextView
        val post: TextView

        init {
            icon = view.findViewById(R.id.icon)
            title = view.findViewById(R.id.tvtitle)
            post = view.findViewById(R.id.tvpost)
        }
    }

    //レイアウトの設定
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_post, viewGroup, false)
        return ViewHolder(view)
    }

    //Viewの設定
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val post = postlist[position]

        viewHolder.icon.setImageResource(post.icon)
        viewHolder.title.text = post.title
        viewHolder.post.text = post.detail
    }

    //表示数を返す
    override fun getItemCount(): Int = postlist.size
}