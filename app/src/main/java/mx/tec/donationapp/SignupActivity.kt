package mx.tec.donationapp

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
        val birthdayEditText = findViewById<EditText>(R.id.BirthdayeditText) // Agregado para obtener la fecha de cumpleaños

        backButton.setOnClickListener {
            navigateToMainActivity()
        }

        signUpRegisterButton.setOnClickListener {
            val email = emailRegisterEditText.text.toString()
            val password = passwordRegisterEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            val name = nameEditText.text.toString()
            val birthday = birthdayEditText.text.toString() // Obtén la fecha de cumpleaños

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || birthday.isEmpty()) {
                showToast("Por favor, completa todos los campos")
            } else {
                if (password == confirmPassword) {
                    handleSignUp(email, password, confirmPassword, name, birthday)
                } else {
                    showToast("Las contraseñas no coinciden")
                }
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleSignUp(email: String, password: String, confirmPassword: String, name: String, birthday: String) {
        // Lógica de manejo del registro
        if (password == confirmPassword) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registro exitoso
                        showToast("REGISTRO EXITOSO")

                        // Guarda el nombre y la fecha de cumpleaños del usuario en Firestore
                        saveUserDataToFirestore(email, name, birthday)

                        // Además, guarda el nombre y el correo en la colección "ID"
                        saveNameAndEmailToIDCollection(email, name)
                    } else {
                        // Registro fallido
                        showToast("REGISTRO FALLIDO: ${task.exception?.message}")
                    }
                }
        } else {
            // Las contraseñas no coinciden
            showToast("Las contraseñas no coinciden")
        }
    }

    private fun saveUserDataToFirestore(email: String, name: String, birthday: String) {
        usersCollection.document(email)
            .set(mapOf("nombre" to name, "cumpleanos" to birthday))
            .addOnSuccessListener {
                showToast("Nombre y fecha de cumpleaños guardados en Firestore")
                navigateToMainActivity()
            }
            .addOnFailureListener { e ->
                showToast("Error al guardar el nombre y la fecha de cumpleaños en Firestore: ${e.message}")
            }
    }

    private fun saveNameAndEmailToIDCollection(email: String, name: String) {
        val user = auth.currentUser
        if (user != null) {
            val userID = user.uid
            db.collection("ID").document(userID)
                .set(mapOf("nombre" to name, "email" to email))
                .addOnSuccessListener {
                    showToast("Nombre y email guardados en la colección 'ID' con el documento de usuario ID")
                    navigateToMainActivity()
                }
                .addOnFailureListener { e ->
                    showToast("Error al guardar el nombre y email en la colección 'ID': ${e.message}")
                }
        } else {
            showToast("No se pudo obtener el ID del usuario actual")
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
