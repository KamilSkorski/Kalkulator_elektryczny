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
fun Screen2(navController: NavHostController) {

    val keyboardController = LocalSoftwareKeyboardController.current
    var r1Input by remember { mutableStateOf("") }
    var r2Input by remember { mutableStateOf("") }
    var r3Input by remember { mutableStateOf("") }

    val selectedType = remember { mutableStateOf("szeregowe") }

    val r1 = r1Input.toDoubleOrNull() ?: 0.0
    val r2 = r2Input.toDoubleOrNull() ?: 0.0
    val r3 = r3Input.toDoubleOrNull() ?: 0.0
    val calculatedResistance = calculateResistance(r1, r2, r3, selectedType.value)

    Scaffold(
        topBar = {
            TopAppBarNavigation(
                title = "Połączenie rezystorów",
                onBackClick = { navController.navigate("home") }
            )
        }
    ){
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
                    value = r1Input,
                    onValueChange = { r1Input = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next),
                    modifier = Modifier
                        .padding(20.dp)
                        .height(75.dp)
                        .width(300.dp),
                    textStyle = TextStyle(fontSize = 30.sp),
                    label = { Text(text = "R1 (Ω)", fontSize = 20.sp ) },

                    )
                OutlinedTextField(
                    value = r2Input,
                    onValueChange = { r2Input = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next),
                    modifier = Modifier
                        .padding(20.dp)
                        .height(75.dp)
                        .width(300.dp),
                    textStyle = TextStyle(fontSize = 30.sp),
                    label = { Text(text = "R2 (Ω)", fontSize = 20.sp ) },

                    )
                OutlinedTextField(
                    value = r3Input,
                    onValueChange = { r3Input = it },
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
                    label = { Text(text = "R3 (Ω)", fontSize = 20.sp ) },

                    )

                Text(text = "Opór zastępczy: ", fontWeight = FontWeight.Bold, fontSize = 35.sp,
                    modifier = Modifier.padding(top = 20.dp, bottom = 5.dp))
                Text(text = calculatedResistance + " Ω" , fontWeight = FontWeight.Bold, fontSize = 50.sp)
            }

        }
    }
}


fun calculateResistance(r1: Double, r2: Double, r3: Double, type: String): String {

    var value = 0.0
    val items: ArrayList<Double> = ArrayList()
    items.add(r1)
    items.add(r2)
    items.add(r3)
    var amount = 0.0

    if (type == "szeregowe") {
        value = r1 + r2 + r3
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


