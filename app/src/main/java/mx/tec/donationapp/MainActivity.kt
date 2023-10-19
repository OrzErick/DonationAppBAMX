package mx.tec.donationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var lastToastMessage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.EmailAdresseditText)
        val passwordEditText = findViewById<EditText>(R.id.PasswordeditText)
        val logInButton = findViewById<Button>(R.id.IniciarSesionbutton)
        val signUpButton = findViewById<Button>(R.id.Registrarsebutton)
        val verPasswordCheckbox = findViewById<CheckBox>(R.id.verPasswordCheckbox)

        // Establece el Listener para CheckBox
        verPasswordCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // Mostrar la contraseña
                passwordEditText.transformationMethod = null
            } else {
                // Ocultar la contraseña
                passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        logInButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            signInWithEmailAndPassword(email, password) // Llama a la función para iniciar sesión
        }

        signUpButton.setOnClickListener {
            // Llamar a la función signUpActivity al hacer clic en el botón de registro
            signUpActivity()
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    lastToastMessage = "LOGIN EXITOSO"
                    Toast.makeText(this, lastToastMessage, Toast.LENGTH_SHORT).show()

                    // Inicio de sesión exitoso, inicia ButtonNavigationActivity aquí
                    val intent = Intent(this, ButtonNavigationActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    lastToastMessage = "LOGIN FALLIDO: ${task.exception?.message}"
                    Toast.makeText(this, lastToastMessage, Toast.LENGTH_SHORT).show()
                    findViewById<ImageView>(R.id.ErrorIcon2).visibility = View.VISIBLE
                    findViewById<ImageView>(R.id.ErrorIcon).visibility = View.VISIBLE
                }
            }
        } catch (e: Exception) {
            lastToastMessage = "LOGIN FALLIDO: $e"
            Toast.makeText(this, lastToastMessage, Toast.LENGTH_SHORT).show()
            findViewById<ImageView>(R.id.ErrorIcon2).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.ErrorIcon).visibility = View.VISIBLE
        }
    }

    fun signUpActivity() {
        val intent = Intent(this, SignupActivity::class.java)
        startActivity(intent)
        finish()
    }
}
