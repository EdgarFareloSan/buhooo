package com.example.buhorientate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePostActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        addbutton.setOnClickListener {
            val serviceName =Servicenameet.text.toString()
            val posts = Post(serviceName)
            db.collection("posts").add(posts)
            val intent = Intent(this, Homeactivity::class.java)
            startActivity(intent)
        }






    }
}