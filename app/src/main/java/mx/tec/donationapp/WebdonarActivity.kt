package mx.tec.donationapp

import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference

class WebdonarActivity : AppCompatActivity() {

    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webdonar)

        val webView = findViewById<WebView>(R.id.URLwebView)
        webView.settings.javaScriptEnabled = true

        val url = intent.getStringExtra("URL")
        val cantidadDonada = url?.let { obtenerCantidadDonada(it) } ?: 0

        if (url != null) {
            webView.loadUrl(url)
        }

        val confirmarDonativoButton = findViewById<Button>(R.id.ConfirmarDonativobutton)
        confirmarDonativoButton.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            val user = auth.currentUser

            if (user != null) {
                val correoUsuario = user.email

                if (correoUsuario != null) {
                    val donativosRef = firestore.collection("Donativos").document(correoUsuario)
                    donativosRef.get()
                        .addOnSuccessListener { document ->
                            val cantidadActual = document.getLong("Cantidad") ?: 0
                            val nuevaCantidad = cantidadActual + cantidadDonada
                            registrarCantidadDonada(donativosRef, nuevaCantidad)
                        }
                }
            }
        }

        val regresarDonarButton = findViewById<Button>(R.id.CancelarDonativobutton)
        regresarDonarButton.setOnClickListener {
            finish()
        }
    }

    private fun obtenerCantidadDonada(url: String): Int {
        return when (url) {
            "https://donate.stripe.com/test_fZe4iS2YU0zf2VWcMN" -> 100
            "https://donate.stripe.com/test_fZe5mW6b61Dj0NO28a" -> 200
            "https://donate.stripe.com/test_00gaHgdDyeq5cww7sv" -> 300
            "https://donate.stripe.com/test_aEUaHg5723Lr8gg7sw" -> 400
            "https://donate.stripe.com/test_3csdTs9ni5Tz7ccfZ3" -> 800
            "https://donate.stripe.com/test_14k8z80QMdm18gg5kq" -> 900
            else -> 0
        }
    }

    private fun registrarCantidadDonada(donativosRef: DocumentReference, nuevaCantidad: Long) {
        val donativoData = hashMapOf("Cantidad" to nuevaCantidad)

        donativosRef.set(donativoData)
            .addOnSuccessListener {
                finish()
            }
            .addOnFailureListener {
                // Manejar errores si es necesario
            }
    }
}
