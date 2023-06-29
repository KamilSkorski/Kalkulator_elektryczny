package com.example.test

import android.content.ClipData
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun Screen5(navController: NavHostController) {
    val bar = TopBarDetail()
    Scaffold(
        topBar = {
            Row (modifier = Modifier
                .fillMaxWidth()
                .background(bar.color)
                .height(bar.rowHeight)) {

                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(bar.iconPadding)
                        .clickable {
                            navController.navigate(bar.navigate)
                        }
                        .size(bar.iconSize)
                )
                Row(modifier = Modifier
                    .height(bar.rowHeight)
                    .padding(0.dp,18.dp,0.dp,0.dp),
                ){
                    Text(
                        text = "Obwody RLC",
                        textAlign = bar.textAlign,
                        fontSize = bar.fontSize
                    )
                }
            }
        }
    ) {
            contentPadding ->
        LazyColumn(modifier = Modifier.padding(contentPadding)) {
            item {

            }
        }
    }

}