package com.algarrobo.pc2_2021_2_mg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val auth = FirebaseAuth.getInstance()

        val edtdni: EditText = findViewById(R.id.edtdni)
        val edtclave: EditText = findViewById(R.id.edtclave)
        val btningresar: Button = findViewById(R.id.btningresar)
        val btnregistro: Button = findViewById(R.id.btnregistro)
        // instancia de Base de Datos

        btningresar.setOnClickListener {

            var dni: String = edtdni.text.toString()
            var clave: String = edtclave.text.toString()

            auth.signInWithEmailAndPassword("$dni@gmail.com",clave)
                .addOnCompleteListener(this){task ->
                    if (task.isSuccessful){
                        Snackbar
                            .make(
                                findViewById(android.R.id.content)
                                ,"ACCESO PERMITIDO"
                                , Snackbar.LENGTH_LONG
                            ).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    }else{
                        Snackbar
                            .make(
                                findViewById(android.R.id.content)
                                ,"EL USUARIO Y/O CLAVE NO EXISTE EN EL SISTEMA"
                                , Snackbar.LENGTH_LONG
                            ).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                }
        }
        btnregistro.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java)) // redirección al fragment registro
        }
    }
}