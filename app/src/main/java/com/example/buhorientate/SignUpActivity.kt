package com.example.buhorientate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setup()
    }

    private fun setup() {
        title = "Registro"

        signUpButtonB.setOnClickListener {
            if (editTextPasswordB.text.toString() == editTextConfirmPasswordB.text.toString()
            ) {
                if (editTextEmailAddressB.text.isNotEmpty() && editTextPersonName.text.isNotEmpty() &&
                    editTextPasswordB.text.isNotEmpty() && editTextConfirmPasswordB.text.isNotEmpty()) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        editTextEmailAddressB.text.toString(),
                        editTextPasswordB.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {

                            showHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        } else {
                            showAlert("Error al registrarse")
                        }
                    }
                } else {
                    showAlert("Por favor ingrese todos los datos")
                }
            } else {
                showAlert("Las contraseñas no coinciden")
            }
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

    private fun showHome(email: String, provider: ProviderType) {
        val homeIntent: Intent = Intent(this, Homeactivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }
}