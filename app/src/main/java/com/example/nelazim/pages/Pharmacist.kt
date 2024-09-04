package com.example.nelazim.pages

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nelazim.R
import com.example.nelazim.model.Pharmacy
import com.example.nelazim.model.getPharmacys
import java.util.ArrayList

@Composable
fun PharmacyStart() {
    val context= LocalContext.current
    val user_data = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    var pharmacyList by remember { mutableStateOf(listOf<Pharmacy>()) }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (user_data.getString("il", "") == "")
            {
               Toast.makeText(context,"Şehir ve ilçe bilgileriniz kaydetmeniz gerekli! Sizi yönlendiriyorum.",Toast.LENGTH_LONG).show()
                //Yönlendirme olacak
            }
            else
            {
                LaunchedEffect(user_data) {
                    getPharmacys(context, user_data.getString("ilce", "").toString(), user_data.getString("il", "").toString()) {
                        pharmacy ->
                        pharmacyList = if (pharmacy.isEmpty()) {
                            emptyList()
                        } else {
                            pharmacy
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(pharmacyList) {
                        PharmacyCardView(pharmacy = it)
                    }
                }

            }
        }
    }
}

@Composable
fun PharmacyCardView(pharmacy: Pharmacy,) {
    val context = LocalContext.current
    ElevatedCard(
        shape = RoundedCornerShape(50.dp, 25.dp, 50.dp, 25.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(250.dp)
            .padding(top = 50.dp).wrapContentHeight(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(id = R.color.cardBgPurple)

        )
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colorResource(id = R.color.cardBgPurple),
                    shape = RoundedCornerShape(50.dp, 25.dp, 50.dp, 25.dp)
                ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text(
                    text = pharmacy.name,
                    color = colorResource(id = R.color.cardBgPurple),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 10.dp)
                )
            }
            Text(
                text = "Adres Bilgisi :\n${pharmacy.address}",
                modifier = Modifier.padding(20.dp,10.dp,20.dp,0.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Call,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Text(text = pharmacy.phone)
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "null",
                            tint = Color.White
                        )
                        Text(text = "Haritada Göster")
                    }

                }
            }


        }
    }
}


