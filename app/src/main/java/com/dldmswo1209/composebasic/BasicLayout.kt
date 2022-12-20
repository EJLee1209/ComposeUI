package com.dldmswo1209.composebasic

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.dldmswo1209.composebasic.ui.theme.ComposeBasicTheme
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class RowActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBasicTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ButtonsContainer()
                }
            }
        }
    }
}

// Row - 아이템을 가로로 나열할 때
// arrangement : Row, Column 같은 요소들이 들어가는
// 컨테이너 성격의 컴포저블에서 요소들의 아이템을 정렬할 때 사용
// Arrangement.Start : 왼쪽으로
// Arrangement.End : 오른쪽으로
// Arrangement.SpaceAround : 빈 공간을 남겨두기
// Arrangement.Center : 요소들에 넣기
// Arrangement.SpaceBetween : 사이에 공간을 밀어넣기
// Arrangement.SpaceEvenly : 요소들 사이에 공간을 똑같이 하기

// alignment : LinerLayout 에서 gravity 와 같다
@Composable
fun Container(){
    Row(
        Modifier
            .background(Color.White)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        DummyBox()
        DummyBox()
        DummyBox()
    }
}

// Column - 아이템을 세로로 나열할 때
@Composable
fun VerticalContainer(){
    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        DummyBox()
        DummyBox()
        DummyBox()
    }
}

//onClick: () -> Unit,
//modifier: Modifier = Modifier,
//enabled: Boolean = true, 클릭 여부
//interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }, 사용자의 인터렉션 처리
//elevation: ButtonElevation? = ButtonDefaults.elevation(), 그림자
//shape: Shape = MaterialTheme.shapes.small, // 모양
//border: BorderStroke? = null, // 테두리
//colors: ButtonColors = ButtonDefaults.buttonColors(), // 버튼 색
//contentPadding: PaddingValues = ButtonDefaults.ContentPadding, // 내용물 밀어넣는 공간
//content: @Composable RowScope.() -> Unit

@Composable
fun ButtonsContainer(){
    val buttonBorderGradient = Brush.horizontalGradient(listOf(Color.Yellow, Color.Red))

    // remember 라는 녀석이 변경 되면, 리컴포지션에 의해 뷰가 다시 그려짐
    val interactionSourceForFirstBtn = remember { MutableInteractionSource() }
    val isPressedForFirstBtn by interactionSourceForFirstBtn.collectIsPressedAsState()

    val interactionSourceForSecondBtn = remember { MutableInteractionSource() }
    val isPressedForSecondBtn by interactionSourceForSecondBtn.collectIsPressedAsState()

    val pressStatusTitle = if (isPressedForFirstBtn) "버튼을 누르고 있다." else "버튼에서 손을 뗐다."
    val pressedBtnRadius = if(isPressedForSecondBtn) 0.dp else 20.dp

    val pressedBtnRadiusWithAnim : Dp by animateDpAsState(
        if(isPressedForSecondBtn) 0.dp else 20.dp
    )

    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Button(onClick = {
            Log.d("testt", "ButtonsContainer: 버튼 1 클릭")
        },
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp
        )) {
            Text(text = "버튼 1")
        }
        Button(
            onClick = {
                Log.d("testt", "ButtonsContainer: 버튼 2 클릭")
            },
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            enabled = true
        ) {
            Text(text = "버튼 2")
        }
        Button(
            onClick = {
                Log.d("testt", "ButtonsContainer: 버튼 3 클릭")
            },
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            enabled = true,
            shape = CircleShape
        ) {
            Text(text = "버튼 3")
        }

        Button(
            onClick = {
                Log.d("testt", "ButtonsContainer: 버튼 4 클릭")
            },
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            enabled = true,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(4.dp, Color.Yellow),
            contentPadding = PaddingValues(horizontal = 100.dp, vertical = 20.dp)
        ) {
            Text(text = "버튼 4")
        }

        Button(
            onClick = {
                Log.d("testt", "ButtonsContainer: 버튼 5 클릭")
            },
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            enabled = true,
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(4.dp, buttonBorderGradient),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue,
                disabledBackgroundColor = Color.LightGray
            ),
            interactionSource = interactionSourceForFirstBtn,

        ) {
            Text(text = "버튼 5", color = Color.White)
        }
        Text(text = pressStatusTitle)

        Button(
            onClick = {
                Log.d("testt", "ButtonsContainer: 버튼 6 클릭")
            },
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            enabled = true,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Blue,
                disabledBackgroundColor = Color.LightGray
            ),
            interactionSource = interactionSourceForSecondBtn,
            modifier = Modifier.drawColoredShadow(
                color = Color.Red,
                alpha = 0.5f,
                borderRadius = 10.dp,
                shadowRadius = pressedBtnRadiusWithAnim,
                offsetY = 0.dp,
                offsetX = 0.dp
            )

            ) {
            Text(text = "버튼 6", color = Color.White)
        }
    }
}


// Box - 뷰를 겹쳐서 그리고 싶을 때 사용
// propagateMinConstraints = true 로 하면
// 박스 안에 있는 제일 작은 크기의 뷰를 컨테이너 박스의 크기 만큼 컨스트레인트를 준다(부모에 크기를 맞춤)
@Composable
fun BoxContainer(){
    Box(
        Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
//        propagateMinConstraints = true
    )
    {
        DummyBox(Modifier.size(200.dp,200.dp))
        DummyBox(Modifier.size(150.dp,150.dp), color = Color.Yellow)
        DummyBox(color = Color.Blue)

    }
}

// BoxWithConstraints 는 Box 와 마찬가지로 뷰를 겹칠 수 있다.
// 일반 Box 와 차이점은 뷰의 최소 최대 가로, 최소 최대 세로 등을 알 수 있다.
// 크기에 따른 뷰 변경이 가능
// 만약 크기에 따라 다른 내용을 보여주고 싶거나 레이아웃이 변경될 때 용이함
@Composable
fun BoxWithConstraintsContainer(){
    BoxWithConstraints(
        Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    )
    {
        
        if(this.minHeight > 400.dp){
            DummyBox(Modifier.size(200.dp,200.dp), color = Color.Green)

        }else{
            DummyBox(Modifier.size(200.dp,200.dp), color = Color.Yellow)
        }
        Text(text = "minHeight : ${this.minHeight}")

//        Column() {
//            BoxWithConstraintsItem(modifier = Modifier.size(200.dp).background(Color.Yellow))
//            BoxWithConstraintsItem(modifier = Modifier.size(300.dp).background(Color.Green))
//        }

//        DummyBox(Modifier.size(200.dp,200.dp))
//        DummyBox(Modifier.size(150.dp,150.dp), color = Color.Yellow)
//        DummyBox(color = Color.Blue)
    }
}

@Composable
fun BoxWithConstraintsItem(modifier: Modifier = Modifier){
    BoxWithConstraints(
        modifier,
        contentAlignment = Alignment.Center,
    )
    {
        if(this.minWidth > 200.dp){
            Text(text = "이것은 큰 상자이다.")
        }else{
            Text(text = "이것은 작은 상자이다.")
        }


    }
}

//text: String,
//modifier: Modifier = Modifier,
//color: Color = Color.Unspecified,
//fontSize: TextUnit = TextUnit.Unspecified,
//fontStyle: FontStyle? = null,
//fontWeight: FontWeight? = null,
//fontFamily: FontFamily? = null,
//letterSpacing: TextUnit = TextUnit.Unspecified,
//textDecoration: TextDecoration? = null,
//textAlign: TextAlign? = null,
//lineHeight: TextUnit = TextUnit.Unspecified,
//overflow: TextOverflow = TextOverflow.Clip,
//softWrap: Boolean = true,
//maxLines: Int = Int.MAX_VALUE,
//onTextLayout: (TextLayoutResult) -> Unit = {},
//style: TextStyle = LocalTextStyle.current

@Composable
fun TextContainer(){
    val name = "이은재"

    var words = stringResource(id = R.string.dummy_short_text)
    var wordsArray = words.split(" ")
    val scrollState = rememberScrollState()


    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp),


    ) {
        Text(
            text = "Hello ${name}",
            Modifier
                .fillMaxWidth()
                .background(Color.Yellow),
            style = TextStyle(textAlign = TextAlign.Center)
        )
        Text(
            text = "Hello ${name}",
            Modifier.fillMaxWidth(),
            style = TextStyle(textAlign = TextAlign.Start),
        )
        Text(
            text = stringResource(id = R.string.dummy_short_text),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(textAlign = TextAlign.Justify),
            textDecoration = TextDecoration.combine(
                listOf(
                    TextDecoration.LineThrough,
                    TextDecoration.Underline
                )
            ),
            fontWeight = FontWeight.W200,
            fontFamily = FontFamily.Monospace,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )
        Text(
            text = stringResource(id = R.string.dummy_short_text),
            Modifier
                .fillMaxWidth()
                .background(Color.Yellow),
            style = TextStyle(textAlign = TextAlign.Start),
            fontFamily = FontFamily(Font(R.font.cafe24)),
            lineHeight = 40.sp
        )
        Text(text = buildAnnotatedString {
            append("안녕하세요?")
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
            ){
                append("개발자 인은재 입니다!")
            }
            withStyle(
              style = SpanStyle(color = Color.Red)
            ) {
                append("오늘도 꾸준히 공부합시다")
            }
        })
        Text(text = buildAnnotatedString {
            wordsArray.forEach {
                if(it.contains("목숨")){
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.ExtraBold
                        ),
                    ){
                        append("${it} ")
                    }
                }else{
                    append("${it} ")
                }
            }
        })

        ClickableText(text = AnnotatedString("클릭미!"), onClick = {
            Log.d("testt", "TextContainer: 클릭미가 클릭됐다!")
        })

        Text(
            text = stringResource(id = R.string.dummy_long_text),
            style = TextStyle(lineHeight = 20.sp)
        )
    }


}

@Composable
fun DummyBox(modifier: Modifier = Modifier, color: Color? = null){
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)

    // color 가 있으면 해당 값을 넣어주고, 없으면 랜덤값을 넣어주자
    val randomColor = color?.let {
        it
    } ?: Color(red, green, blue)


    Box(modifier = modifier
        .size(100.dp)
        .background(randomColor)
    ) {
    }
}

@Composable
fun ShapeContainer(){
    var polySides by remember { mutableStateOf(3) }

    Column(
        Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
//        DummyBox(modifier = Modifier.clip(RectangleShape))
        DummyBox(modifier = Modifier.clip(CircleShape))
        DummyBox(modifier = Modifier.clip(RoundedCornerShape(20.dp)))
        DummyBox(modifier = Modifier.clip(CutCornerShape(10.dp)))
        DummyBox(modifier = Modifier.clip(TriangleShape()))
        DummyBox(modifier = Modifier.clip(PolyShape(polySides,100f)))

        Text(text = "polySides : ${polySides}")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                polySides += 1
            }) {
                Text(text = "PolySides Up")
            }
            Button(onClick = {
                polySides = 3
            }) {
                Text(text = "PolySides = 3")
            }
        }
    }

}

// 커스텀 shape
class TriangleShape(): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path = path)
    }
}

class PolyShape(private val sides: Int, private val radius: Float) : Shape{
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = Path().apply {
            this.polygon(sides, radius, size.center)
        })
    }

}

fun Path.polygon(sides: Int, radius: Float, center: Offset){
    val angle = 2.0 * Math.PI / sides
    moveTo(
        x = center.x + (radius * cos(0.0)).toFloat(),
        y = center.y + (radius * sin(0.0)).toFloat()
    )
    for (i in 1 until sides){
        lineTo(
            x = center.x + (radius * cos(angle * i)).toFloat(),
            y = center.y + (radius * sin(angle * i)).toFloat()
        )
    }
    close()
}


@Preview(showBackground = true)
@Composable
fun MyPreView() {
    ComposeBasicTheme {
        ButtonsContainer()
    }
}