package com.example.buhorientate

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_authentification.*
import kotlin.reflect.KClass

@Suppress("DEPRECATION")
class AuthActivity : AppCompatActivity() {
    private val GOOGLE_SIGN_IN = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentification)
        setup()
        session()
    }
    // Guardado de datos
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
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString()
                ).addOnCompleteListener {
                        if (it.isSuccessful){
                            showHome(it.result?.user?.email ?:"" , ProviderType.BASIC)
                        }else{
                            showAlert("Error al iniciar sesion")
                        }
                    }
            }
        }
        googleButton.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }

        SignUpbutton.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }

    }


    private fun showAlert(errorType: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(errorType)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showHome(email:String,provider: ProviderType) {
        val homeIntent: Intent = Intent(this, Homeactivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider",provider.name)
        }
        startActivity(homeIntent)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if(account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(account.email ?: "", ProviderType.GOOGLE)
                        }else {
                            showAlert("Error al iniciar sesion")
                        }
                    }
                }
            } catch(e:ApiException) {
                showAlert("Error al iniciar sesion")
            }
        }
    }
}

