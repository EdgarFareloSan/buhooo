package com.example.buhorientate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_post.*
import java.util.*

class CreatePostActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        val auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        addbutton.setOnClickListener {
            val serviceName =Servicenameet.text.toString()
            val postText = postEditText.text.toString()
            val date = Date()
            val userName = auth.uid

            if(serviceName.isNotEmpty() && postText.isNotEmpty()){
                val posts = Post(postText, serviceName, date, userName)

                db.collection("posts").add(posts)
                val intent = Intent(this, Homeactivity::class.java)
                startActivity(intent)
            }else{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Error")
                builder.setMessage("Ingrese todos los datos")
                builder.setPositiveButton("Aceptar", null)
                val dialog: AlertDialog = builder.create()
                dialog.show()
            }


        }






    }
}