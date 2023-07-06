@file:OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)

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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

val optionsMap = mutableMapOf(
    "Miedź" to 0.0178,
    "Aluminium" to 0.0282
)

@Composable
fun Screen4(navController: NavHostController) {
    val bar = TopBarDetail()
    val keyboardController = LocalSoftwareKeyboardController.current

    val options = listOf("Miedź", "Aluminium")

    var material by remember { mutableStateOf("Miedź") }

    var expended by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0])}

    var lengthInput by remember { mutableStateOf("") }
    var sectionAreaInput by remember { mutableStateOf("") }

    val length = lengthInput.toDoubleOrNull() ?: 0.0
    val sectionArea = sectionAreaInput.toDoubleOrNull() ?: 0.0

    val resistanceValue = calculateWireResistance(length, sectionArea, material)

    Scaffold(
        topBar = {
            TopAppBarNavigation(
                title = "Rezystancja przewodów",
                onBackClick = { navController.navigate(bar.navigate) }
            )
        }
    ) {
            contentPadding ->
        LazyColumn(modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                Spacer(modifier = Modifier.padding(top=20.dp))
                    ExposedDropdownMenuBox( expanded = expended, onExpandedChange = {expended = !expended}, modifier = Modifier
                        .width(300.dp)
                        .height(75.dp)) {
                        TextField( value = selectedOptionText, onValueChange ={}, readOnly = true, label = { Text(
                            text = "Materiał", fontSize = 12.sp
                        )}, trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = expended)}, colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        ExposedDropdownMenu(expanded = expended, onDismissRequest = { expended = false},) {

                            options.forEach{selectedText->
                                DropdownMenuItem(onClick = {
                                    selectedOptionText = selectedText
                                    expended = false
                                    material = selectedText
                                }) {
                                    Text(text = selectedText)

                                }
                            }
                        }
                    }
                OutlinedTextField(
                    value = lengthInput,
                    onValueChange = { lengthInput = it },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next),
                    modifier = Modifier
                        .padding(20.dp)
                        .height(75.dp)
                        .width(300.dp),
                    textStyle = TextStyle(fontSize = 30.sp),
                    label = { Text(text = "Długość (m)", fontSize = 20.sp ) },
                    )
                OutlinedTextField(
                    value = sectionAreaInput,
                    onValueChange = { sectionAreaInput = it },
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
                    label = { Text(text = "Pole przekroju (mm^2)", fontSize = 20.sp ) },
                )

                Text(text = "Rezystancja przewodu: ", fontWeight = FontWeight.Bold, fontSize = 35.sp,
                    modifier = Modifier.padding(top = 20.dp, bottom = 5.dp))
                Text(text = resistanceValue + " Ω" , fontWeight = FontWeight.Bold, fontSize = 50.sp)

            }
        }
    }

}

fun calculateWireResistance(len: Double, sec: Double, material : String) : String{

    var value : Double

    if(sec == 0.0)
    {
        return 0.0.toString()
    }else {
        value = optionsMap.getValue(material) * (len/sec)
        value = kotlin.math.round(value * 10000) / 10000
    }


    return value.toString()
}