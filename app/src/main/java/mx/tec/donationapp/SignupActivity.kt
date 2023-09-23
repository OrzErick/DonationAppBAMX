package mx.tec.donationapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("Usuarios")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        val nameEditText = findViewById<EditText>(R.id.NameeditText)
        val signUpRegisterButton = findViewById<Button>(R.id.RegistrarseButton)
        val backButton = findViewById<Button>(R.id.BackButton)
        val emailRegisterEditText = findViewById<EditText>(R.id.EmailRegistereditText)
        val passwordRegisterEditText = findViewById<EditText>(R.id.PasswordRegistereditText)
        val confirmPasswordEditText = findViewById<EditText>(R.id.ConfirmPasswordeditText)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        signUpRegisterButton.setOnClickListener {
            val email = emailRegisterEditText.text.toString()
            val password = passwordRegisterEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            val name = nameEditText.text.toString() // Obtén el nombre del EditText

            if (password == confirmPassword) {
                // Las contraseñas coinciden, puedes proceder con el registro
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Registro exitoso
                            Toast.makeText(this, "REGISTRO EXITOSO", Toast.LENGTH_SHORT).show()

                            // Guarda el nombre del usuario en Firestore
                            usersCollection.document(email)
                                .set(mapOf("nombre" to name)) // Crea un documento con el nombre del usuario
                                .addOnSuccessListener {
                                    // El nombre se guardó con éxito en Firestore
                                    Toast.makeText(this, "Nombre guardado en Firestore", Toast.LENGTH_SHORT).show()

                                    // Regresa a la MainActivity
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    // Error al guardar el nombre en Firestore
                                    Toast.makeText(this, "Error al guardar el nombre en Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            // Registro fallido
                            Toast.makeText(this, "REGISTRO FALLIDO: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                // Las contraseñas no coinciden, muestra un mensaje de error
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }
    }
}