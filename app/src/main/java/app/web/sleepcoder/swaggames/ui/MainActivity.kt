package app.web.sleepcoder.swaggames.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import app.web.sleepcoder.swaggames.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        viewBinding.lifecycleOwner = this
        setContentView(viewBinding.root)

        viewBinding.bottomNavView.itemIconTintList = null
        var navHost = supportFragmentManager.findFragmentById(viewBinding.fragmentContainerView.id) as NavHostFragment
        viewBinding.bottomNavView.setupWithNavController(
            navHost.navController
        )
    }
}