package dam.pmdm.spyrothedragon

import android.media.SoundPool
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import dam.pmdm.spyrothedragon.databinding.ActivityMainBinding
import dam.pmdm.spyrothedragon.databinding.GuideBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var guideBinding: GuideBinding
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bindig del include
        guideBinding = GuideBinding.bind(binding.includeLayout.root)

        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.navHostFragment)

        navHostFragment?.let {
            navController = NavHostFragment.findNavController(it)
            NavigationUI.setupWithNavController(binding.navView, navController!!)
            NavigationUI.setupActionBarWithNavController(this, navController!!)
        }

        binding.navView.setOnItemSelectedListener { menuItem ->
            selectedBottomMenu(menuItem)
        }

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_characters,
                R.id.navigation_worlds,
                R.id.navigation_collectibles -> {
                    // En las pantallas de los tabs no mostramos la flecha atrás
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }

                else -> {
                    // En el resto de pantallas sí
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }

        // Prefs -> Mostrar guía si es la primera vez
        val prefs = getSharedPreferences("SpyroPrefs", MODE_PRIVATE)
        if (!prefs.getBoolean("guia_completada", false)) {
            mostrarGuia()
        } else {
            guideBinding.guideLayout.visibility = View.GONE
        }
    }

    private fun selectedBottomMenu(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_characters ->
                navController?.navigate(R.id.navigation_characters)

            R.id.nav_worlds ->
                navController?.navigate(R.id.navigation_worlds)

            else ->
                navController?.navigate(R.id.navigation_collectibles)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.about_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_info) {
            showInfoDialog()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun showInfoDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.title_about)
            .setMessage(R.string.text_about)
            .setPositiveButton(R.string.accept, null)
            .show()
    }

    private fun mostrarGuia() {
        guideBinding.guideLayout.visibility = View.VISIBLE
        mostrarPantalla(1)

        // Pantalla 1 -> 2
        guideBinding.buttonComenzar.setOnClickListener { animarTransicion { mostrarPantalla(2) } }
        // Pantalla 2 -> 3
        guideBinding.btnSiguienteP2.setOnClickListener { animarTransicion { mostrarPantalla(3) } }
        guideBinding.btnOmitirP2.setOnClickListener { cerrarGuia() }
        // Pantalla 3 -> 4
        guideBinding.btnSiguienteP3.setOnClickListener { animarTransicion { mostrarPantalla(4) } }
        guideBinding.btnOmitirP3.setOnClickListener { cerrarGuia() }
        // Pantalla 4 -> 5
        guideBinding.btnSiguienteP4.setOnClickListener { animarTransicion { mostrarPantalla(5) } }
        guideBinding.btnOmitirP4.setOnClickListener { cerrarGuia() }
        // Pantalla 5 -> 6
        guideBinding.btnSiguienteP5.setOnClickListener { animarTransicion { mostrarPantalla(6) } }
        guideBinding.btnAdelante.setOnClickListener { cerrarGuia() }
    }

    private fun mostrarPantalla(numero: Int) {
        guideBinding.pantalla1.visibility = View.GONE
        guideBinding.pantalla2.visibility = View.GONE
        guideBinding.pantalla3.visibility = View.GONE
        guideBinding.pantalla4.visibility = View.GONE
        guideBinding.pantalla5.visibility = View.GONE
        guideBinding.pantalla6.visibility = View.GONE

        when (numero) {
            1 -> guideBinding.pantalla1.visibility = View.VISIBLE
            2 -> {
                val soundPool = SoundPool.Builder().setMaxStreams(1).build()
                val soundId = soundPool.load(this, R.raw.fool1, 1)
                soundPool.setOnLoadCompleteListener { _, _, _ ->
                    soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                }
                guideBinding.pantalla2.visibility = View.VISIBLE
                animarBocadillo(guideBinding.bocadilloP2)

            }

            3 -> {
                val soundPool = SoundPool.Builder().setMaxStreams(1).build()
                val soundId = soundPool.load(this, R.raw.fool1, 1)
                soundPool.setOnLoadCompleteListener { _, _, _ ->
                    soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                }
                guideBinding.pantalla3.visibility = View.VISIBLE
                animarBocadillo(guideBinding.bocadilloP3)
            }

            4 -> {
                val soundPool = SoundPool.Builder().setMaxStreams(1).build()
                val soundId = soundPool.load(this, R.raw.fool1, 1)
                soundPool.setOnLoadCompleteListener { _, _, _ ->
                    soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                }
                guideBinding.pantalla4.visibility = View.VISIBLE
                animarBocadillo(guideBinding.bocadilloP4)
            }

            5 -> {
                val soundPool = SoundPool.Builder().setMaxStreams(1).build()
                val soundId = soundPool.load(this, R.raw.fool1, 1)
                soundPool.setOnLoadCompleteListener { _, _, _ ->
                    soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                }
                guideBinding.pantalla5.visibility = View.VISIBLE
                animarBocadillo(guideBinding.bocadilloP5)
            }

            6 -> {
                guideBinding.pantalla6.visibility = View.VISIBLE

                val soundPool = SoundPool.Builder().setMaxStreams(1).build()
                val soundId = soundPool.load(this, R.raw.up, 1)
                soundPool.setOnLoadCompleteListener { _, _, _ ->
                    soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                }
            }
        }
    }

    // Animación simple de fade in
    private fun animarTransicion(accion: () -> Unit) {
        val layout = guideBinding.guideLayout
        layout.animate()
            .alpha(0f)
            .setDuration(300)
            .withEndAction {
                accion()
                layout.animate()
                    .alpha(1f)
                    .setDuration(300)
                    .start()
            }.start()
    }

    // Animación simple de fade in
    private fun animarBocadillo(bocadillo: View) {
        bocadillo.animate()
            .scaleX(1.1f)
            .scaleY(1.1f)
            .setDuration(300)
            .withEndAction {
                // Al terminar, volvemos a la escala original (1.0)
                bocadillo.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(300)
                    .start()
            }
            .start()
    }

    // Para cuando se de al botón de omitir o adelante en la última pantalla
    // Hace un fadeout y guarda el estado de la guía el en prefs
    private fun cerrarGuia() {
        guideBinding.guideLayout.animate()
            .alpha(0f)
            .setDuration(400)
            .withEndAction {
                guideBinding.guideLayout.visibility = View.GONE
                getSharedPreferences("SpyroPrefs", MODE_PRIVATE)
                    .edit {
                        putBoolean("guia_completada", true)
                    }
            }.start()
    }
}
