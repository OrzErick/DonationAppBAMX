package mx.tec.donationapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class Scores : Fragment() {
    private lateinit var auth: FirebaseAuth
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scores, container, false)

        val totalDonadoTextView = view.findViewById<TextView>(R.id.TotaldonadoTextView)
        val canastasBasicasTextView = view.findViewById<TextView>(R.id.CanastasbasicasTextView)

        // Obtén el correo electrónico del usuario actual
        val userEmail = auth.currentUser?.email

        if (userEmail != null) {
            // Accede al documento en la colección "Donativos" con el nombre del correo del usuario
            val donativosRef = firestore.collection("Donativos").document(userEmail)

            donativosRef.get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Recupera la cantidad donada del documento
                        val cantidadDonada = document.getLong("Cantidad")

                        if (cantidadDonada != null) {
                            // Actualiza el TextView con la cantidad donada
                            totalDonadoTextView.text = "$$cantidadDonada.00"

                            // Realiza la división y muestra el resultado en el TextView
                            val canastasBasicas = cantidadDonada / 400
                            canastasBasicasTextView.text = "$canastasBasicas Canastas Básicas"
                        }
                    }
                }
        }

        return view
    }
}
