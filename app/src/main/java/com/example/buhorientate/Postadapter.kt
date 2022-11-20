package com.example.buhorientate
import android.app.Activity
import android.content.Intent
import android.service.autofill.Dataset
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_post.view.*

class PostAdapter(private val activity: Activity, private val dataset: List<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>(){
    class ViewHolder(val layout: View) : RecyclerView.ViewHolder(layout)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.card_post, parent, false)
        return ViewHolder(layout)
    }

    override fun getItemCount()= dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = dataset[position]

        holder.layout.NameBussines.text =post.ServiceName
        holder.layout.Especificaciones.text = post.post

        holder.layout.sharebtn.setOnClickListener {
            val sendIntent= Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.post)
                type ="text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            activity.startActivity(shareIntent)
        }
    }
}