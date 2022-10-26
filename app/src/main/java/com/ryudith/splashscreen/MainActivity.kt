package com.ryudith.splashscreen

import android.graphics.pdf.PdfDocument.Page
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ryudith.splashscreen.ui.theme.SplashScreenTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreenTheme {
                // A surface container using the 'background' color from the theme
                val navHostControllerState = rememberNavController()
                PageNavigation(navHostControllerState)
            }
        }
    }
}

@Composable
fun PageNavigation (navHostController: NavHostController)
{
    NavHost(navController = navHostController, startDestination = "splashScreen")
    {
        composable("splashScreen")
        {
            SplashScreenPage(navHostController)
        }

        composable("homePage")
        {
            HomePage()
        }
    }
}

@Composable
fun SplashScreenPage (navHostController: NavHostController)
{
    val startSplash = remember {
        mutableStateOf(true)
    }
    val offsetY = animateDpAsState(targetValue = if (startSplash.value) 0.dp else -8.dp, animationSpec = tween(1000))
    val alphaVal = animateFloatAsState(targetValue = if (startSplash.value) 0F else 1F, animationSpec = tween(1000))

    Column(modifier = Modifier
        .fillMaxSize()
        .background(colorResource(id = R.color.purple_500))
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.ic_splash_screen),
                contentDescription = "Splash Icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.Center)
                    .absoluteOffset(y = offsetY.value)
            )

            if (!startSplash.value)
            {
                Text(
                    text = "Welcome...",
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .alpha(alphaVal.value)
                )
            }
        }
    }

    LaunchedEffect(key1 = Unit)
    {
        delay(2000L)
        startSplash.value = false

        delay(2000L)
        navHostController.navigate("homePage")
    }
}

@Composable
fun HomePage() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Text("Android")
    }
}