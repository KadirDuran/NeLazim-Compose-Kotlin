package com.example.nelazim.pages

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nelazim.R
import com.example.nelazim.data.model.Currency
import com.example.nelazim.data.repository.getCurrencys

@Composable
fun CurrencyStart(){
    val context= LocalContext.current
    val user_data = context.getSharedPreferences("UserData", Context.MODE_PRIVATE).getString("il","")
    var currencyList by remember { mutableStateOf(listOf<Currency>()) }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (user_data == "")
            {
                Toast.makeText(context,"Şehir ve ilçe bilgileriniz kaydetmeniz gerekli! Sizi yönlendiriyorum.",
                    Toast.LENGTH_LONG).show()
                //Yönlendirme olacak
            }
            else
            {
                LaunchedEffect(user_data) {
                    getCurrencys(context) {
                            currency ->
                        currencyList = if (currency.isEmpty()) {
                            emptyList()
                        } else {
                            currency
                        }
                    }
                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(currencyList){
                        CurrencyCardView(it)
                    }
                }

            }
        }
    }
}

@Composable
fun CurrencyCardView(currency: Currency) {
    ElevatedCard(
        shape = RoundedCornerShape(50.dp, 25.dp, 50.dp, 25.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(180.dp)
            .padding(top = 50.dp)
            .wrapContentHeight(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(id = R.color.white)

        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.cardBgPurple))
        ) {
            Text(
                text =currency.name,
                color = colorResource(id = R.color.white),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 10.dp)
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text(
                    text = "Alış Fiyatı : ${currency.buying} TL",
                    color = colorResource(id = R.color.cardBgPurple),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 10.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text(
                    text = "Satış Fiyatı : ${currency.selling} TL",
                    color = colorResource(id = R.color.cardBgPurple),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(vertical = 10.dp)
                )
            }
        }
    }
}
