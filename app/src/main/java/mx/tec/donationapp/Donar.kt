package mx.tec.donationapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class Donar : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_donar, container, false)

        val donar100 = view.findViewById<Button>(R.id.opcion1donativoButton)
        val donar200 = view.findViewById<Button>(R.id.opcion2donativoButton)
        val donar300 = view.findViewById<Button>(R.id.opcion3donativoButton)
        val donar400 = view.findViewById<Button>(R.id.opcion4donativoButton)
        val donar800 = view.findViewById<Button>(R.id.opcion5donativoButton)
        val donar900 = view.findViewById<Button>(R.id.opcion6donativoButton)


        donar100.setOnClickListener {
            abrirWebDonar("https://donate.stripe.com/test_fZe4iS2YU0zf2VWcMN")
        }

        donar200.setOnClickListener {
            abrirWebDonar("https://donate.stripe.com/test_fZe5mW6b61Dj0NO28a")
        }

        donar300.setOnClickListener {
            abrirWebDonar("https://donate.stripe.com/test_00gaHgdDyeq5cww7sv")
        }

        donar400.setOnClickListener {
            abrirWebDonar("https://donate.stripe.com/test_aEUaHg5723Lr8gg7sw")
        }

        donar800.setOnClickListener {
            abrirWebDonar("https://donate.stripe.com/test_3csdTs9ni5Tz7ccfZ3")
        }

        donar900.setOnClickListener {
            abrirWebDonar("https://donate.stripe.com/test_14k8z80QMdm18gg5kq")
        }

        return view
    }

    private fun abrirWebDonar(url: String) {
        val intent = Intent(activity, WebdonarActivity::class.java)
        intent.putExtra("URL", url)
        startActivity(intent)
    }
}