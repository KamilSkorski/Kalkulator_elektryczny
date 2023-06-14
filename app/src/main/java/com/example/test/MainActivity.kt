package com.example.test
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test.ui.theme.TestTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyApp()
                }
            }
        }
    }
}



@SuppressLint("SuspiciousIndentation")
@Composable
fun MyApp() {
 val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("screen1") { Screen1(navController) }
        composable("screen2") { Screen2(navController) }
        composable("screen3") { Screen3(navController) }
        composable("screen4") { Screen4(navController) }
    }
}
@Composable
fun HomeScreen(navController: NavHostController) {

    val button = HomeButton()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in 0 until 4) {
            item {
                Button(
                    onClick = { navController.navigate("screen${i+1}") },
                    modifier = Modifier
                        .padding(button.padding)
                        .size(button.with, button.height)
                ) {
                    Text(
                        text = button.buttonTexts[i],
                        fontSize = button.fontSize,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }}



//@Preview(showBackground = true)



    /*
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        //verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       item{ Button(
            onClick = {  },
            modifier = Modifier
                .padding(16.dp)
                .size(width = 400.dp, height = 100.dp)
        ) {
            Text(text = "Połączenie kondensatorów",
                    fontSize = 35.sp,
                    textAlign = TextAlign.Center)
        }
        Button(
            onClick = {  },
            modifier = Modifier
                .padding(16.dp)
                .size(width = 400.dp, height = 100.dp)
        ) {
            Text(text = "Połączenie rezystorów",
                fontSize = 35.sp,
                textAlign = TextAlign.Center)
        }
        Button(
            onClick = {  },
            modifier = Modifier
                .padding(16.dp)
                .size(width = 400.dp, height = 100.dp)
        ) {
            Text(text = "Prąd/Napięcie/Moc",
                fontSize = 35.sp,
                textAlign = TextAlign.Center)
        }
        Button(
            onClick = {  },
            modifier = Modifier
                .padding(16.dp)
                .size(width = 400.dp, height = 100.dp)
        ) {
            Text(text = "Rezystancja przewodu",
                fontSize = 35.sp,
                textAlign = TextAlign.Center)
        }
        Button(
            onClick = {  },
            modifier = Modifier
                .padding(16.dp)
                .size(width = 400.dp, height = 100.dp)
        ) {
            Text(text = "Obwody RLC",
            fontSize = 35.sp)
        }
    }
}
*/

@Composable
fun DefaultPreview() {
    MyApp()
}

