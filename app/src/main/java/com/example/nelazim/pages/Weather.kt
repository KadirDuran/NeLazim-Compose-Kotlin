package com.example.nelazim.pages

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.nelazim.R
import com.example.nelazim.data.model.Result
import com.example.nelazim.data.model.getWeather


@Composable
fun WeatherPageStart()
{
    val context= LocalContext.current
    val user_data = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    var resultList by remember { mutableStateOf(listOf<Result>()) }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            if (user_data.getString("il", "") == "")
            {
                Toast.makeText(context,"Şehir ve ilçe bilgileriniz kaydetmeniz gerekli! Sizi yönlendiriyorum.",
                    Toast.LENGTH_LONG).show()
                //Yönlendirme olacak
            }
            else
            {
                LaunchedEffect(user_data) {
                    getWeather(context, user_data.getString("ilce", "").toString(), user_data.getString("il", "").toString()){
                            it->
                        resultList = if(it.isEmpty()){
                            arrayListOf()
                        }else
                        {
                            it
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(resultList) {
                        WeatherCard(weather = it)
                    }
                }

            }
        }
    }
}

@Composable
fun WeatherCard(weather: Result){
    val painter = rememberImagePainter(
        data = weather.icon,
        builder = {
            crossfade(true)
            placeholder(android.R.drawable.ic_menu_report_image) // Placeholder image while loading
        }
    )
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White))
    ElevatedCard(
        shape = RoundedCornerShape(80.dp, 25.dp, 80.dp, 25.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(horizontal = 15.dp)
            .wrapContentHeight(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(id = R.color.cardBgPurple)

        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "${weather.date} - ${weather.day}", color = colorResource(id = R.color.white))
            Image(
                painter = painter, contentDescription = null, modifier = Modifier
                    .width(128.dp)
                    .height(128.dp)
            )
            Text(
                text = "Sıcaklık : ${weather.degree}°  Nem : %${weather.humidity}",
                color = colorResource(id = R.color.white)
            )
            Text(
                text = "Gün içi En düşük sıcaklık ${weather.min}°",
                color = colorResource(id = R.color.white)
            )
            Text(
                text = "Gün içi En yüksek sıcaklık ${weather.max}°",
                color = colorResource(id = R.color.white)
            )
        }
    }

}
