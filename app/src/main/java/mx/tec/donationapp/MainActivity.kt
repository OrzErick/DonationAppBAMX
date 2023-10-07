package mx.tec.donationapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()


        val emailEditText = findViewById<EditText>(R.id.EmailAdresseditText)
        val passwordEditText = findViewById<EditText>(R.id.PasswordeditText)
        val logInButton = findViewById<Button>(R.id.IniciarSesionbutton)
        val signUpButton = findViewById<Button>(R.id.Registrarsebutton)

        logInButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "LOGIN EXITOSO", Toast.LENGTH_SHORT).show()
                        // Llamar a menuActivity() después de iniciar sesión exitosamente
                        //menuActivity()
                    } else {
                        Toast.makeText(this, "LOGIN FALLIDO: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        signUpButton.setOnClickListener {
            // Llamar a la función RegistrarActivity al hacer clic en el botón de registro
            signUpActivity()
        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
        }
    }
    private fun signUpActivity() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
        finish()
    }
    //private fun menuActivity(){
        //val intent = Intent(this, MenuActivity::class.java)
        //startActivity(intent)
        //finish()
    //}

}