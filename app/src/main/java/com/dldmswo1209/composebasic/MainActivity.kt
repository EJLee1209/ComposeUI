package com.dldmswo1209.composebasic


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dldmswo1209.composebasic.ui.theme.ComposeBasicTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBasicTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("ㅋㅋㅋㅋ")
                }
            }
        }
    }
}

// 뷰
@Composable
fun Greeting(name: String) {
    // Scaffold 를 지원해서 머티리얼 라이브러리를 제공(액션바, FAB...)
    Scaffold(topBar = {
        TopAppBar(title = {Text(text = "컴포즈 UI")},
            backgroundColor = Color(0xfff25287)) },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Text(text = "클릭")
            }
        }
    ) {
//        Text(text = "안녕하세요 $name!")
        MyComposableView()
    }
}

@Composable
fun MyRowItem(){
    Log.d("testt", "MyRowItem: ")
    // Row - 수평 구조
    Row(
        Modifier
            .padding(10.dp)
            .background(Color(0xffeaffd0))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically // gravity
    ) {
        Text(text = "안녕하세요?!",
            Modifier
                .padding(10.dp)
                .background(Color.Yellow))
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "안녕하세요?!")
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "안녕하세요?!")
    }
}

@Composable
fun MyComposableView(){
    Log.d("testt", "MyComposableView()")

    // Column - 수직 구조
    Column(
        Modifier
            .background(Color.Green)
            .verticalScroll(rememberScrollState()) // 스크롤 먹히게 해줌
    ) {
        for(i in 0..50){
            MyRowItem()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeBasicTheme {
        Greeting("이은재 입니다")
    }
}