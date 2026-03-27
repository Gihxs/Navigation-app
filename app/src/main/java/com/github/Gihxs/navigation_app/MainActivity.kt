package com.github.Gihxs.navigation_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.Gihxs.navigation_app.screens.LoginScreen
import com.github.Gihxs.navigation_app.screens.MenuScreen
import com.github.Gihxs.navigation_app.screens.PedidosScreen
import com.github.Gihxs.navigation_app.screens.PerfilScreen
import com.github.Gihxs.navigation_app.ui.theme.NavigationappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationappTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()


                    NavHost(
                        navController = navController,
                        startDestination = "login",
                    ) {
                        composable(route = "login") {
                            LoginScreen(modifier = Modifier.padding(innerPadding), navController)
                        }
                        composable(route = "menu") {
                            MenuScreen(modifier = Modifier.padding(innerPadding), navController)
                        }
                        // define rota com parâmetro opcional "cliente", usando valor padrão caso não seja enviado
                        composable(
                            route = "pedidos?cliente={cliente}",
                            arguments = listOf(navArgument("cliente") {
                                defaultValue = "Cliente Genérico"
                            })
                        ) {
                            PedidosScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController,
                                it.arguments?.getString("cliente")
                            )
                        }

                        // define rota com parâmetro obrigatório "nome"
                        composable(
                            route = "perfil/{nome}/{idade}",
                            arguments = listOf(
                                navArgument("nome") { type = NavType.StringType },
                                navArgument("idade") {
                                    type = NavType.IntType
                                } // define "idade" como parâmetro inteiro
                            )
                        ) {

                            // recupera os parâmetros enviados na navegação
                            val nome: String? =
                                it.arguments?.getString("nome", "Usuário Genérico")
                            val idade: Int? =
                                it.arguments?.getInt("Idade", 0)

                            PerfilScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController,
                                nome!!,
                                idade!! // garante que a idade não seja nula
                            )
                        }
                    }
                }
            }
        }
    }
}
