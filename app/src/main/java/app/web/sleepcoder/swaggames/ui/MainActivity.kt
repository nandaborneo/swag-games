package app.web.sleepcoder.swaggames.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import app.web.sleepcoder.swaggames.R
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

        viewBinding.bottomNavView.apply {
            itemIconTintList = null
            val navHost = supportFragmentManager.findFragmentById(viewBinding.fragmentContainerView.id) as NavHostFragment
            navHost.navController.addOnDestinationChangedListener{
                _, destination, _ ->
                visibility = when("${destination.id}"){
                    "${R.id.homeFragment}" -> View.VISIBLE
                    "2113994752" -> View.VISIBLE
                    "${R.id.profileFragment}" -> View.VISIBLE
                    else -> View.GONE
                }
            }
            setupWithNavController(
                navHost.navController
            )

        }
    }
}