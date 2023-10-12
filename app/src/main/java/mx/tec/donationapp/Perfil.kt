package mx.tec.donationapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentSnapshot

class Perfil : Fragment() {
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("Usuarios")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        val logOutButton = view.findViewById<Button>(R.id.logoutButton)
        val usernameTextView = view.findViewById<TextView>(R.id.usernameTextView)
        val birthdayTextView = view.findViewById<TextView>(R.id.birthdayTextView)
        val emailTextView = view.findViewById<TextView>(R.id.emailTextView)

        logOutButton.setOnClickListener {
            auth.signOut()

            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        // Obtener el correo electrónico del usuario actual
        val userEmail = auth.currentUser?.email

        // Buscar el documento correspondiente en Firestore
        if (userEmail != null) {
            usersCollection.document(userEmail)
                .get()
                .addOnSuccessListener { documentSnapshot: DocumentSnapshot? ->
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        val userData = documentSnapshot.data
                        val username = userData?.get("nombre") as String
                        val birthday = userData["cumpleanos"] as String

                        // Actualizar los TextView con la información del usuario
                        usernameTextView.text = "Nombre: $username"
                        birthdayTextView.text = "Cumpleaños: $birthday"
                        emailTextView.text = "Correo Electrónico: $userEmail"
                    }
                }
        }

        return view
    }
}
