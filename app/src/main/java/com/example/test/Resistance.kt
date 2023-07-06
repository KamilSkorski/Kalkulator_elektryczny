@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.test


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController



@Composable
fun Screen1(navController: NavHostController) {
    val bar = TopBarDetail()
    val keyboardController = LocalSoftwareKeyboardController.current
    var c1Input by remember { mutableStateOf("") }
    var c2Input by remember { mutableStateOf("") }
    var c3Input by remember { mutableStateOf("") }

    val selectedType = remember { mutableStateOf("szeregowe") }

    val c1 = c1Input.toDoubleOrNull() ?: 0.0
    val c2 = c2Input.toDoubleOrNull() ?: 0.0
    val c3 = c3Input.toDoubleOrNull() ?: 0.0
    val calculatedValue = calculateCapacity(c1, c2, c3, selectedType.value)
    Scaffold(
        topBar = {
            TopAppBarNavigation(
                title = "Połączenie kondensatorów",
                onBackClick = { navController.navigate(bar.navigate) }
            )
        }
    ) {
            contentPadding ->
        LazyColumn(modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            item  {
                Text(text = "Wybierz rodzaj połączenia:", fontSize = 25.sp, modifier = Modifier.padding(top = 20.dp, bottom = 20.dp))
                Row{
                    RadioButton(selected = selectedType.value == "szeregowe" ,
                        onClick = { selectedType.value = "szeregowe" },
                        colors = RadioButtonDefaults.colors(Color.Blue))

                    Spacer(modifier = Modifier.size(1.dp))

                    Text(text = "szeregowe", fontSize = 25.sp, modifier = Modifier.padding(top = 4.dp))

                    Spacer(modifier = Modifier.size(16.dp))

                    RadioButton(selected = selectedType.value == "równoległe" ,
                    onClick = { selectedType.value = "równoległe" },
                    colors = RadioButtonDefaults.colors(Color.Blue))

                    Spacer(modifier = Modifier.size(1.dp))
                    Text(text = "równoległe", fontSize = 25.sp, modifier = Modifier.padding(top = 4.dp))

                    Spacer(modifier = Modifier.size(16.dp))
                }

                OutlinedTextField(
                    value = c1Input,
                    onValueChange = { c1Input = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next),
                    modifier = Modifier
                        .padding(20.dp)
                        .height(75.dp)
                        .width(300.dp),
                    textStyle = TextStyle(fontSize = 30.sp),
                    label = { Text(text = "C1 (μF)", fontSize = 20.sp ) },

                )
                OutlinedTextField(
                    value = c2Input,
                    onValueChange = { c2Input = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next),
                    modifier = Modifier
                        .padding(20.dp)
                        .height(75.dp)
                        .width(300.dp),
                    textStyle = TextStyle(fontSize = 30.sp),
                    label = { Text(text = "C2 (μF)", fontSize = 20.sp ) },

                    )
                OutlinedTextField(
                    value = c3Input,
                    onValueChange = { c3Input = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }
                    ),
                    modifier = Modifier
                        .padding(20.dp)
                        .height(75.dp)
                        .width(300.dp),
                    textStyle = TextStyle(fontSize = 30.sp),
                    label = { Text(text = "C3 (μF)", fontSize = 20.sp ) },

                    )
                /*
                Button( onClick = { /*TODO*/ }, modifier = Modifier
                    .width(200.dp)
                    .height(75.dp)) {
                    Text(text = "Oblicz", fontSize = 35.sp)
                }
                */

                Text(text = "Pojemność zastępcza: ", fontWeight = FontWeight.Bold, fontSize = 35.sp,
                    modifier = Modifier.padding(top = 20.dp, bottom = 5.dp))
                Text(text = calculatedValue + " μF" , fontWeight = FontWeight.Bold, fontSize = 50.sp)

            }

            }
        }
    }


fun calculateCapacity(c1: Double, c2: Double, c3: Double, type: String): String {

    var value = 0.0
    val items: ArrayList<Double> = ArrayList()
    items.add(c1)
    items.add(c2)
    items.add(c3)
    var amount = 0.0

    if (type == "równoległe") {
        value = c1 + c2 + c3
    } else {
        for (i in 0..2) {
            if (items[i] != 0.0)
                amount += 1 / items[i]

            if (amount == 0.0) {
                value = 0.0
            } else {
                value = 1 / amount
            }


        }
    }
        value = kotlin.math.round(value * 1000) / 1000

        return value.toString()
    }


