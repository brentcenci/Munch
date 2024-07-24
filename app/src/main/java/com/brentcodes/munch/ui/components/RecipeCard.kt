package com.brentcodes.munch.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.brentcodes.munch.R
import com.brentcodes.munch.model.data.Result

@Composable
fun RecipeCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.clip(RoundedCornerShape(10.dp)).border(1.dp, Color.Green)
    ) {
        AsyncImage(
            model = "https://www.nab.com.au/about-us/careers/early-careers/technology-talent-program/_jcr_content/root/banner/image.coreimg.90.1920.jpeg/1658129696908/nab-grads-banner-lucy-2500x900.jpeg",
            contentDescription = "Recipe Image",
            modifier = Modifier.fillMaxWidth().height(160.dp),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxWidth().height(80.dp)
        ) {
            Text(
                "Recipe Name",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
                )
            Text("Brief summary of the recipe here, talking about flavours and other components that may educate a user on what to expect",
                maxLines = 1,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray
            )
            Text(
                "Health rating? Calories? Time it takes?",
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun RecipeCardTest(modifier: Modifier = Modifier, result: Result, onClick: (Result) -> Unit, isSaved: Boolean, onSave: (String) -> Unit, onUnsave: (String) -> Unit ) {
    val time = remember { result.readyInMinutes ?: 0 }
    val timeDisplay = if (time > 59) "${time/60} hrs ${time%60} mins" else "$time mins"
    val timeHours = remember { if (time > 59) "${time/60}h" else "" }
    val timeMins = remember { if (time%60 != 0) " ${time%60}m" else "" }
    Column(
        modifier = modifier.shadow(8.dp, RoundedCornerShape(10.dp)).clickable { onClick(result) }
    ) {
        Box(modifier = Modifier.fillMaxWidth().height(160.dp).background(Color.White)) {
            AsyncImage(
                model = result.image,
                contentDescription = "Recipe Image",
                modifier = Modifier.fillMaxWidth().height(160.dp),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth().height(70.dp).background(Color.White).padding(5.dp)
        ) {
            Row(modifier = Modifier.height(30.dp)) {
                Text(
                    result.title?: "Title",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = {
                    val id = result.id.toString()
                    if (!isSaved) onSave(id) else onUnsave(id)
                }) {
                    val painter = if (isSaved) painterResource(R.drawable.saved) else painterResource(R.drawable.save)
                    Icon(painter, "", modifier = Modifier.size(24.dp))
                }
            }

            Text(
                "$timeHours$timeMins , ${result.pricePerServing}",
                fontSize = 12.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
}