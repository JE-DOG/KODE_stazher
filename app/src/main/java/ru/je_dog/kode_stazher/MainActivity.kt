package ru.je_dog.kode_stazher

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.dsl.module
import ru.je_dog.core.data.common.util.NetworkConnectionMonitorImpl
import ru.je_dog.core.data.common.util.NetworkConnectionMonitory
import ru.je_dog.feature.user_profile.navigation.userProfile
import ru.je_dog.feature.users.di.searchUsersModule
import ru.je_dog.feature.users.navigation.searchUsers
import ru.je_dog.feature.users.navigation.searchUsersRoute
import ru.je_dog.kode_stazher.di.appModule
import ru.je_dog.kode_stazher.ui.theme.KODE_stazherTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            KODE_stazherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    KoinApplication(moduleList = {

                        listOf(
                            appModule,
                            searchUsersModule,
                            module {
                                single<Context> { App.INSTANCE }
                                single<NavController> { navController }
                            }
                        )

                    }) {

                        NavHost(
                            navController = navController,
                            startDestination = searchUsersRoute
                        ){

                            searchUsers()
                            userProfile()
 
                        }
                    }

                }
            }
        }
    }

}