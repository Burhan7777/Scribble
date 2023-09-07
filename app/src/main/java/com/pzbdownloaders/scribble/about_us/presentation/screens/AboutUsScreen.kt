package com.pzbdownloaders.scribble.about_us.presentation.screens

import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pzbdownloaders.scribble.R
import com.pzbdownloaders.scribble.common.presentation.FontFamily
import com.pzbdownloaders.scribble.common.presentation.MainActivity

@Composable
fun AboutUsScreen(
    activity: MainActivity
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(painter = painterResource(id = R.drawable.ic_launcher), contentDescription = "logo")
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "v-0.0.9 ALPHA",
            fontSize = 20.sp,
            fontFamily = FontFamily.fontFamilyBold,
            color = MaterialTheme.colors.onPrimary
        )
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Connect with me",
                    fontSize = 20.sp,
                    fontFamily = FontFamily.fontFamilyRegular,
                    color = MaterialTheme.colors.onPrimary
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_instagram),
                    contentDescription = "instagram",
                    modifier = Modifier.clickable {
                        Intent(Intent.ACTION_VIEW).apply {
                            this.data = Uri.parse("https://www.instagram.com/peerburhan/")
                        }.also {
                            activity.startActivity(it)
                        }
                    }
                )
            }
        }
    }
}


