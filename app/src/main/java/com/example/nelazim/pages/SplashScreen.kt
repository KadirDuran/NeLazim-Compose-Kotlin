package com.example.nelazim.pages

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nelazim.R
import com.example.nelazim.data.model.Service_Info

@Composable
fun StartSplashScreen()
{
    val se = ArrayList<Service_Info>()
    se.add(Service_Info(1,"Hava Durumu", R.drawable.weather))
    se.add(Service_Info(2,"Nöbetçi Eczaneler", R.drawable.pharmacist))
    se.add(Service_Info(3,"Namaz Vakitleri", R.drawable.mosque))
    se.add(Service_Info(4,"Döviz Fiyatları", R.drawable.currency))
    ServiceCatalogCreate(serviceInfo = se)
}

@Composable
fun ServiceCatalogCreate(serviceInfo: ArrayList<Service_Info>) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 50.dp)
    ) {
        LazyColumn( modifier = Modifier.fillMaxWidth()) {
            items(serviceInfo){
                CardCreate(serviceInfo = it, context = context)
            }
        }



    }
}

@Composable
fun CardCreate(serviceInfo: Service_Info, context: Context)
{
    ElevatedCard(shape = RoundedCornerShape(50.dp, 25.dp, 50.dp, 25.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(200.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(id = R.color.cardBgPurple)
        ),
        onClick = {
            Toast.makeText(context, "Redirect : " + serviceInfo.id, Toast.LENGTH_LONG).show()
        }
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text( text = serviceInfo.title, modifier = Modifier.padding(0.dp,10.dp))

            Image(
                painter = painterResource(id = serviceInfo.imgId),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

        }
    }

}