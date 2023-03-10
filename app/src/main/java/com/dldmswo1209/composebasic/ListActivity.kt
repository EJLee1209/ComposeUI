package com.dldmswo1209.composebasic

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.dldmswo1209.composebasic.ui.theme.ComposeBasicTheme
import com.dldmswo1209.composebasic.ui.theme.myPink
import com.dldmswo1209.composebasic.utils.DummyDataProvider
import com.dldmswo1209.composebasic.utils.RandomUser

class ListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBasicTheme {
                ContentView()
            }
        }
    }
}

@Composable
fun ContentView(){
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(backgroundColor = Color.White,
        topBar = {MyAppBar()}) {
            RandomUserListView(randomUsers = DummyDataProvider.userList)
        }
        
    }
}

@Composable
fun MyAppBar(){
    TopAppBar(
        elevation = 10.dp,
        backgroundColor = myPink,
        modifier = Modifier.height(58.dp)
    ) {
        Text(
            text = "????????? ?????????!",
            Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            fontSize = 18.sp,
            fontWeight = FontWeight.Black
        )

    }
}

@Composable
fun RandomUserListView(randomUsers: List<RandomUser>){
    // ????????? ????????? ????????? LazyColumn = RecyclerView ??? ????????? ???????????? ???
    LazyColumn{
        items(randomUsers){ // randomUsers ??? forEach ????????? ?????? ?????? ??????
            RandomUserView(it)
        }
    }
}

@Composable
fun RandomUserView(randomUser: RandomUser){
    val typography = MaterialTheme.typography
    Card(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 10.dp
    ) {
        Row(
            Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically, // ????????? ??????
            horizontalArrangement = Arrangement.spacedBy(10.dp) // ?????? ????????? ???????????? ????????????, 10dp ?????? ?????????
        ){

            ProfileImg(imageUrl = randomUser.profileImage)

            Column() {
                Text(
                    text = randomUser.name,
                    style = typography.subtitle1
                )
                Text(
                    text = randomUser.description,
                    style = typography.body1
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ProfileImg(imageUrl: String, modifier: Modifier = Modifier){
    // ????????? ?????????
    val bitmap: MutableState<Bitmap?> = mutableStateOf(null)
    val imageModifier = modifier
        .size(50.dp, 50.dp)
        .clip(CircleShape)
    
    Glide.with(LocalContext.current)
        .asBitmap()
        .load(imageUrl)
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                // ???????????? ??????????????? ????????? ????????? ?????????
                bitmap.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    // bitmap ??? ?????????
    bitmap.value?.asImageBitmap()?.let { 
        Image(
            bitmap = it,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = imageModifier
        )
    } ?: Image(
        painter = painterResource(id = R.drawable.ic_empty_user_image),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = imageModifier
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    ComposeBasicTheme {
        ContentView()
    }
}