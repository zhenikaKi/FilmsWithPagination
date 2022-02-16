package ru.kirea.filmswithpagination

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.getKoin
import ru.kirea.filmswithpagination.databinding.MainActivityBinding
import ru.kirea.filmswithpagination.di.Scopes
import ru.kirea.filmswithpagination.navigation.FilmListScreen

class MainActivity : AppCompatActivity() {

    private val binding: MainActivityBinding by lazy { MainActivityBinding.inflate(layoutInflater) }
    private val scope = getKoin().createScope<MainActivity>()

    private var navigatorHolder: NavigatorHolder = scope.get(qualifier = named(Scopes.NAVIGATOR))
    private val router: Router = scope.get(qualifier = named(Scopes.ROUTER))
    private val navigator = AppNavigator(this, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (savedInstanceState == null)
        {
            router.newRootScreen(FilmListScreen())
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}