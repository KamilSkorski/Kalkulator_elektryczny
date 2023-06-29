@file:OptIn(ExperimentalComposeUiApi::class)

package com.example.test

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun Screen3(navController: NavHostController) {
    val bar = TopBarDetail()
    val selectedType = remember { mutableStateOf("Moc") }

    val keyboardController = LocalSoftwareKeyboardController.current
    var currentInput by remember { mutableStateOf("") }
    var voltageInput by remember { mutableStateOf("") }
    var powerInput by remember { mutableStateOf("") }

    val current = currentInput.toDoubleOrNull() ?: 0.0
    val voltage = voltageInput.toDoubleOrNull() ?: 0.0
    val power = powerInput.toDoubleOrNull() ?: 0.0

    val calculatedValue = calculateValue(current, voltage, power, selectedType.value)
    Scaffold(
        topBar = {
            TopAppBarNavigation(
                title = "Moc/Napięcie/Natężenie",
                onBackClick = { navController.navigate(bar.navigate) }
            )
        }
    ) {
            contentPadding ->
        LazyColumn(modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            item {

                    Row(){RadioButton(selected = selectedType.value == "Moc" ,
                        onClick = { selectedType.value = "Moc" },
                        colors = RadioButtonDefaults.colors(Color.Blue))


                        Text(text = "Moc", fontSize = 28.sp, modifier = Modifier.padding(top = 4.dp))

                        RadioButton(selected = selectedType.value == "Napięcie" ,
                        onClick = { selectedType.value = "Napięcie" },
                        colors = RadioButtonDefaults.colors(Color.Blue))

                        Text(text = "Napięcie", fontSize = 28.sp, modifier = Modifier.padding(top = 4.dp))}


                Row(){RadioButton(selected = selectedType.value == "Natężenie" ,
                    onClick = { selectedType.value = "Natężenie" },
                    colors = RadioButtonDefaults.colors(Color.Blue))

                    Text(text = "Natężenie", fontSize = 28.sp, modifier = Modifier.padding(top = 4.dp))}

                OutlinedTextField(
                    value = if(selectedType.value!="Natężenie") currentInput else "",
                    onValueChange = { currentInput = it },
                    enabled =   if(selectedType.value=="Natężenie") false else true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next),
                    modifier = Modifier
                        .padding(20.dp)
                        .height(75.dp)
                        .width(300.dp),
                    textStyle = TextStyle(fontSize = 30.sp),
                    label = { Text(text = "Natężenie (A)", fontSize = 20.sp ) },
                    )

                OutlinedTextField(
                    value = if(selectedType.value!="Napięcie") voltageInput else "",
                    onValueChange = { voltageInput = it },
                    enabled =   if(selectedType.value=="Napięcie") false else true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal, imeAction = if(selectedType.value== "Moc") ImeAction.Done else ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }),
                    modifier = Modifier
                        .padding(20.dp)
                        .height(75.dp)
                        .width(300.dp),
                    textStyle = TextStyle(fontSize = 30.sp),
                    label = { Text(text = "Napięcie (V)", fontSize = 20.sp ) },

                    )

                OutlinedTextField(
                    value = if(selectedType.value!="Moc") powerInput else "",
                    onValueChange = { powerInput = it },
                    enabled =   if(selectedType.value=="Moc") false else true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal, imeAction = if(selectedType.value!= "Moc") ImeAction.Done else ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }),
                    modifier = Modifier
                        .padding(20.dp)
                        .height(75.dp)
                        .width(300.dp),
                    textStyle = TextStyle(fontSize = 30.sp),
                    label = { Text(text = "Moc (W)", fontSize = 20.sp ) },
                )

                Text(text = selectedType.value, fontWeight = FontWeight.Bold, fontSize = 35.sp,
                    modifier = Modifier.padding(top = 20.dp, bottom = 5.dp))
                Text(text = calculatedValue, fontWeight = FontWeight.Bold, fontSize = 50.sp)

            }
        }
    }

}

fun calculateValue(c: Double, v: Double, p: Double, type: String ): String
{
    var value = 0.0
    var stringValue :String

    if(type == "Moc")
    {
        value = v*c
        return roundValue(value).toString() + " W"
    }
    if(type == "Napięcie")
    {
        if(c!=0.0)
        {
            value = p/c
            return roundValue(value).toString() + " V"
        }
        else return 0.0.toString() + " V"
    }
    if(type == "Natężenie")
    {
        if(v!=0.0)
        {
            value = p/v
            return roundValue(value).toString() + " A"
        }
        else return 0.0.toString() + " A"
    }

    return 0.0.toString()
}

fun roundValue(value : Double) : Double{

    var roundedValue = (kotlin.math.round(value * 1000) / 1000)

    return roundedValue
}