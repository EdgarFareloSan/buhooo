package com.example.buhorientate

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_authentification.*

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentification)



        setup()
        session()
    }
    private fun session(){
        val prefs:SharedPreferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email:String? = prefs.getString("email",null)
        val provider:String? = prefs.getString("provider",null)
        if (email !=null && provider !=null){
            authLayout.visibility =View.INVISIBLE
            showHome(email, ProviderType.valueOf(provider))
        }

    }

    private fun setup() {
        title = "Autenticación"

        signInButton.setOnClickListener{
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailEditText.text.toString()?:"",
                    passwordEditText.text.toString()?:"").addOnCompleteListener {
                        if (it.isSuccessful){
                            showHome(it.result?.user?.email ?:"" , ProviderType.BASIC)
                        }else{
                            showAlert("Error al iniciar sesion")
                        }
                    }
            }
        }

        SignUpbutton.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }

    }


    fun showAlert(errorType:String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(errorType)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    fun showHome(email:String,provider: ProviderType) {
        val homeIntent: Intent = Intent(this, Homeactivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider",provider.name)
        }
        startActivity(homeIntent)
    }




}