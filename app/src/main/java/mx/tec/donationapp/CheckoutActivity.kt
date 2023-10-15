package mx.tec.donationapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class Donar : Fragment() {
    private lateinit var totalDonarEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_donar, container, false)

        val opcion1Button = view.findViewById<Button>(R.id.opcion1donativoButton)
        val opcion2Button = view.findViewById<Button>(R.id.opcion2donativoButton)
        val opcion3Button = view.findViewById<Button>(R.id.opcion3donativoButton)
        val opcion4Button = view.findViewById<Button>(R.id.opcion4donativoButton)
        val opcion5Button = view.findViewById<Button>(R.id.opcion5donativoButton)
        val opcion6Button = view.findViewById<Button>(R.id.opcion6donativoButton)
        totalDonarEditText = view.findViewById<EditText>(R.id.TotalDonareditText)

        opcion1Button.setOnClickListener {
            val valor = 100
            actualizarTotalDonar(valor)
        }

        opcion2Button.setOnClickListener {
            val valor = 200
            actualizarTotalDonar(valor)
        }

        opcion3Button.setOnClickListener {
            val valor = 300
            actualizarTotalDonar(valor)
        }

        opcion4Button.setOnClickListener {
            val valor = 500
            actualizarTotalDonar(valor)
        }

        opcion5Button.setOnClickListener {
            val valor = 800
            actualizarTotalDonar(valor)
        }

        opcion6Button.setOnClickListener {
            val valor = 900
            actualizarTotalDonar(valor)
        }

        return view
    }

    private fun actualizarTotalDonar(valor: Int) {
        totalDonarEditText.setText(valor.toString())
    }
}