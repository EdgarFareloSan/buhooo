package com.example.buhorientate

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*

enum class ProviderType{
    BASIC
}

class Homeactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bundle:Bundle? =intent.extras
        val email:String? =bundle?.getString("email")
        val provider:String? =bundle?.getString("provider")
        setUp(email ?:"", provider ?:"")
        val prefs = getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()
        val post=Post("Hola mundo", "Empanadas vida")
        val posts = listOf(post)
        rv.apply{
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@Homeactivity)
            adapter= PostAdapter(this@Homeactivity, posts)
        }
    }
    private fun setUp(email: String, provider: String){
        title = "Home"


    }
}