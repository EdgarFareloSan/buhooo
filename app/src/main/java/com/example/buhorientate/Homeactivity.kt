package com.example.buhorientate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*

enum class ProviderType{
    BASIC,
    GOOGLE
}


class Homeactivity : AppCompatActivity() {
    private val db= FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        db.collection("posts").addSnapshotListener { value, _ ->
            val posts = value!!.toObjects(Post::class.java)

            posts.forEachIndexed { index, post ->
                post.uid = value.documents[index].id
            }
            rv.apply{
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@Homeactivity)
                adapter= PostAdapter(this@Homeactivity, posts)
            }
        }
        fab.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }
        val bundle:Bundle? =intent.extras
        val email:String? =bundle?.getString("email")
        val provider:String? =bundle?.getString("provider")
        setUp()
        val prefs = getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()


    }
    private fun setUp() {
        title = "Home"


    }
}