package com.example.nelazim.pages

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.nelazim.Greeting
import com.example.nelazim.R
import com.example.nelazim.model.DataStrict
import com.example.nelazim.model.getCityDistrict
import com.example.nelazim.ui.theme.NeLazimTheme
import kotlinx.coroutines.runBlocking

@Composable
fun UserInfoPage(modifier: Modifier) {
    val data: List<DataStrict>
    runBlocking {
        data = getCityDistrict()
    }
    Column(
        modifier
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, end = 25.dp)

        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = null,
                tint = colorResource(id = R.color.cardBgPurple),
                modifier = Modifier
                    .width(35.dp)
                    .height(35.dp)

            )
        }
        val valueList: List<String> = data.map { it.il }
        var districtList by remember { mutableStateOf<List<String>>(listOf()) }
        var expanded by remember { mutableStateOf(false) }
        var selectedText by remember { mutableStateOf("") }
        var textfieldSize by remember { mutableStateOf(Size.Zero) }
        var expanded2 by remember { mutableStateOf(false) }
        var selectedText2 by remember { mutableStateOf("") }
        val contex = LocalContext.current
        val user_data = contex.getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = user_data.edit()
        val icon = if (expanded) Icons.Filled.KeyboardArrowUp
        else Icons.Filled.KeyboardArrowDown

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = colorResource(id = R.color.cardBgPurple),
                    disabledTextColor = colorResource(
                        id = R.color.cardBgPurple
                    )
                ),
                shape = RoundedCornerShape(50.dp),
                enabled = false,
                value = selectedText,
                onValueChange = { selectedText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded = !expanded
                    }
                    .onGloballyPositioned { coordinates ->
                        textfieldSize = coordinates.size.toSize()
                    },
                label = { Text("İl Seçiniz", color = colorResource(id = R.color.cardBgPurple)) },
                trailingIcon = {
                    Icon(
                        icon,
                        "contentDescription",
                        Modifier.clickable { expanded = !expanded },
                        tint = colorResource(id = R.color.cardBgPurple),
                    )
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.White)
                    .width(with(LocalDensity.current)
                    { textfieldSize.width.toDp() })
            ) {
                valueList.forEach { model ->
                    DropdownMenuItem(
                        modifier = Modifier.background(Color.White),
                        text = {
                            Text(text = model, color = colorResource(id = R.color.cardBgPurple))
                        },
                        onClick = {
                            selectedText = model
                            selectedText2 = ""
                            districtList = data.find { it.il == model }?.ilceleri ?: listOf()
                            expanded = false
                        }
                    )
                    HorizontalDivider(
                        color = colorResource(id = R.color.cardBgPurple)
                    )
                }
            }


            var textfieldSize2 by remember { mutableStateOf(Size.Zero) }
            val icon2 = if (expanded2) Icons.Filled.KeyboardArrowUp
            else Icons.Filled.KeyboardArrowDown

            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    disabledBorderColor = colorResource(id = R.color.cardBgPurple),
                    disabledTextColor = colorResource(
                        id = R.color.cardBgPurple
                    )
                ),
                enabled = false,
                shape = RoundedCornerShape(50.dp),
                value = selectedText2,
                onValueChange = { selectedText2 = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded2 = !expanded2
                    }
                    .onGloballyPositioned { coordinates ->
                        textfieldSize2 = coordinates.size.toSize()
                    },
                label = { Text("İlçe Seçiniz", color = colorResource(id = R.color.cardBgPurple)) },
                trailingIcon = {
                    Icon(
                        icon2,
                        "contentDescription",
                        Modifier.clickable { expanded2 = !expanded2 },
                        tint = colorResource(id = R.color.cardBgPurple)
                    )
                }
            )
            DropdownMenu(
                expanded = expanded2,
                onDismissRequest = { expanded2 = false },
                modifier = Modifier
                    .background(Color.White)
                    .width(with(LocalDensity.current) { textfieldSize2.width.toDp() })
            ) {
                districtList.forEach { model ->
                    DropdownMenuItem(
                        modifier = Modifier.background(Color.White),
                        text = {
                            Text(
                                text = model,
                                color = colorResource(id = R.color.cardBgPurple)
                            )
                        },
                        onClick = {
                            selectedText2 = model
                            expanded2 = false
                        }
                    )
                    HorizontalDivider(
                        color = colorResource(id = R.color.cardBgPurple)
                    )
                }
            }
        }

        fun SaveInfo() {
            if (selectedText != "" && selectedText2 != "") {
                editor.putString("il", selectedText)
                editor.putString("ilce", selectedText2)
                editor.apply()
            } else {
                Toast.makeText(contex, "İl ve ilçe seçmelisiniz!", Toast.LENGTH_LONG).show()
            }
        }


        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    SaveInfo()
                },
                shape = RoundedCornerShape(25.dp, 5.dp, 25.dp, 5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.cardBgPurple)
                )
            ) {
                Text(
                    text = "Bilgileri Kaydet",
                    color = colorResource(id = R.color.white),
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NeLazimTheme {
        UserInfoPage(modifier = Modifier.background(Color.White))
    }
}
