package com.example.buhorientate

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
    private fun setUp(email: String, provider: String){
        title = "Home"


    }
}