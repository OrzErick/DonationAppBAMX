package mx.tec.donationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import mx.tec.donationapp.databinding.ActivityBottonNavegationBinding

class ButtonNavigationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBottonNavegationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottonNavegationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Perfil())

        binding.bottonNavigationView.setOnItemSelectedListener{

            when(it.itemId){
                R.id.perfil -> replaceFragment(Perfil())
                R.id.donar -> replaceFragment(Donar())
                R.id.score -> replaceFragment(Scores())

                else ->{

                }
            }
            true
        }

    }



    private fun replaceFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}