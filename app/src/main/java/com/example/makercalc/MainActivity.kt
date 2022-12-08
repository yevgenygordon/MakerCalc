package com.example.makercalc

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.makercalc.databinding.ActivityMainBinding

/**
 * Main Activity, dient als Einstiegspunkt für die App
 */
class MainActivity : AppCompatActivity() {

    /* -------------------- Klassen Variablen -------------------- */

    /** Bindet das XML-View mit der Klasse um auf die Elemente zugreifen zu können */
    private lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()

    /* -------------------- Lifecycle -------------------- */

    /**
     * Lifecycle Methode, wird aufgerufen wenn Activity erstellt wird
     *
     * @param savedInstanceState      Save state vom view
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        // Das Binding zur XML-Datei
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navHost = supportFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        val navController = navHost.navController
        navController.addOnDestinationChangedListener{
            _,destination,_->
            if (destination.id != R.id.topicsFragment && destination.id != R.id.constructionkitFragment) {
            viewModel.unsetTemp()
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }



        binding.btnMenu.setOnClickListener {
            binding.mainFragment.findNavController().navigate(R.id.menuFragment)
        }


        binding.btnSuche.setOnClickListener {  }

        binding.btnSortby.setOnClickListener {
            binding.mainFragment.findNavController().navigate(R.id.sortbyFragment)
        }
    }
}
