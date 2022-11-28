package com.example.buhorientate
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.card_post.view.*
import java.text.SimpleDateFormat

class PostAdapter(private val activity: Activity, private val dataset: List<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>(){
    class ViewHolder(val layout: View) : RecyclerView.ViewHolder(layout)
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.card_post, parent, false)
        return ViewHolder(layout)
    }

    override fun getItemCount()= dataset.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = dataset[position]
        val likes= post.likes!!.toMutableList()
        var liked= likes.contains(auth.uid)
        holder.layout.likesCount.text = "${likes.size} Me gusta"
        holder.layout.NameBussines.text = post.serviceName
        holder.layout.Especificaciones.text = post.post
        setColor(liked, holder.layout.likebtn)

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm a")

        holder.layout.fechaTextView.text = sdf.format(post.date)

        holder.layout.likebtn.setOnClickListener {
            liked = !liked
            setColor(liked, holder.layout.likebtn)

            if(liked) likes.add(auth.uid!!)
            else likes.remove(auth.uid)

            val doc = db.collection("posts").document(post.uid!!)

            db.runTransaction {
                it.update(doc,  "likes", likes)
                null
            }
        }

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
    private fun setColor(liked: Boolean, likeButton: Button){
        if (liked) likeButton.setTextColor(ContextCompat.getColor(activity,R.color.purple_700))

    }
}