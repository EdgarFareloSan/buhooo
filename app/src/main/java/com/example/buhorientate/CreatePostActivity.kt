package com.example.buhorientate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.core.motion.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePostActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        addbutton.setOnClickListener {
            val serviceName =Servicenameet.text.toString()
            var posts = Post(serviceName)
            db.collection("posts").add(posts)
            val intent = Intent(this, Homeactivity::class.java)
            startActivity(intent)
        }






    }
}