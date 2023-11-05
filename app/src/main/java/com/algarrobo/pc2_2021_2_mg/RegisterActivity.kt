package com.algarrobo.pc2_2021_2_mg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.algarrobo.pc2_2021_2_mg.models.UsuariosModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val edtregdni: EditText = findViewById(R.id.edtregdni)
        val edtregnombre: EditText = findViewById(R.id.edtregnombre)
        val edtregpassword: EditText = findViewById(R.id.edtregpassword)
        val edtregpassword2: EditText = findViewById(R.id.edtregpassword2)
        val btnregcuent: Button = findViewById(R.id.btnregcuent)

        val db = FirebaseFirestore.getInstance() // instancia de la BD
        val collectionRef = db.collection("Usuarios") // tabla o colección
        val auth = FirebaseAuth.getInstance() // creacion de usuario

        btnregcuent.setOnClickListener {
            var DNI = edtregdni.text.toString()
            var Clave = edtregpassword.text.toString()
            var NombreCompleto = edtregnombre.text.toString()
            var claveverif = edtregpassword2.text.toString()

            auth.createUserWithEmailAndPassword("$DNI@gmail.com",Clave)
                .addOnCompleteListener(this){task->

                    if(Clave == claveverif){
                    if (task.isSuccessful){
                        //Se registró en Firebase Auth y deberá registrarse en Firestore
                        val user: FirebaseUser? = auth.currentUser

                        val usermodel = UsuariosModel(DNI,Clave,NombreCompleto)
                        collectionRef.add(usermodel)
                            .addOnCompleteListener{

                            }.addOnFailureListener{error->
                                Snackbar
                                    .make(
                                        findViewById(android.R.id.content)
                                        ,"Ocurrió un error al registrar el modelo"
                                        , Snackbar.LENGTH_LONG
                                    ).show()
                                startActivity(Intent(this, RegisterActivity::class.java))
                            }
                        Snackbar
                            .make(
                                findViewById(android.R.id.content)
                                ,"Registro exitoso del usuario"
                                , Snackbar.LENGTH_LONG
                            ).show()
                        startActivity(Intent(this, LoginActivity::class.java))

                        }
                    }else{
                        Snackbar
                            .make(
                                findViewById(android.R.id.content)
                                ,"Ocurrió un error al registrarse"
                                , Snackbar.LENGTH_LONG
                            ).show()
                        startActivity(Intent(this, RegisterActivity::class.java))

                    }

                    }

                }

        }


    }