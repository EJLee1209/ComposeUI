package com.dldmswo1209.composebasic

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.dldmswo1209.composebasic.ui.theme.ComposeBasicTheme
import kotlinx.coroutines.launch
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
                    TextFieldTest()
                }
            }
        }
    }
}

//value: TextFieldValue,
//onValueChange: (TextFieldValue) -> Unit,
//modifier: Modifier = Modifier,
//enabled: Boolean = true,
//readOnly: Boolean = false,
//textStyle: TextStyle = LocalTextStyle.current,
//label: @Composable (() -> Unit)? = null,
//placeholder: @Composable (() -> Unit)? = null,
//leadingIcon: @Composable (() -> Unit)? = null,
//trailingIcon: @Composable (() -> Unit)? = null,
//isError: Boolean = false,
//visualTransformation: VisualTransformation = VisualTransformation.None,
//keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
//keyboardActions: KeyboardActions = KeyboardActions(),
//singleLine: Boolean = false,
//maxLines: Int = Int.MAX_VALUE,
//interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
//shape: Shape =
//MaterialTheme.shapes.small.copy(bottomEnd = ZeroCornerSize, bottomStart = ZeroCornerSize),
//colors: TextFieldColors = TextFieldDefaults.textFieldColors()

@Composable
fun TextFieldTest(){
    var userInput by remember { mutableStateOf(TextFieldValue()) }
    var phoneNumberInput by remember { mutableStateOf(TextFieldValue()) }
    var emailInput by remember { mutableStateOf(TextFieldValue()) }
    var passwordInput by remember { mutableStateOf(TextFieldValue()) }
    val shouldShowPassword = remember { mutableStateOf(false) }

    val passwordRes: (Boolean) -> (Int) = {
        if(it){
            R.drawable.ic_baseline_visibility_24
        }else{
            R.drawable.ic_baseline_visibility_off_24
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text(text = "사용자 입력") },
            placeholder = { Text(text = "작성해 주세요") },
            singleLine = false,
            maxLines = 2
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = phoneNumberInput,
            onValueChange = { phoneNumberInput = it },
            label = { Text(text = "전화번호") },
            placeholder = { Text(text = "010-1234-1234") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = emailInput,
            onValueChange = { emailInput = it },
            label = { Text(text = "이메일 주소") },
            placeholder = { Text(text = "이메일 주소를 입력해주세요") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            trailingIcon = { IconButton(onClick = { Log.d("testt", "TextFieldTest: 체크버튼 클릭")}) {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
            } }
//            trailingIcon = { Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null) }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = passwordInput,
            onValueChange = { passwordInput = it },
            label = { Text(text = "패스워드") },
            placeholder = { Text(text = "패스워드를 입력해주세요") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            trailingIcon = { IconButton(onClick = {
                Log.d("testt", "TextFieldTest: 비밀번호 visible 버튼 클릭")
                shouldShowPassword.value = !shouldShowPassword.value
            }) {
                Icon(painter = painterResource(id = passwordRes(shouldShowPassword.value)), contentDescription = null)
            } },
            visualTransformation = if(shouldShowPassword.value) VisualTransformation.None else PasswordVisualTransformation()
//            trailingIcon = { Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null) }
        )

    }
}

@Composable
fun MySnackBar(){
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val buttonTitle : (SnackbarData?) -> String = { snackbarData ->
        if(snackbarData != null) "스낵바 숨기기" else "스낵바 보여주기"
    }
    val buttonColor : (SnackbarData?) -> Color = { snackbarData ->
        if(snackbarData != null) {
            Color.Black
        } else {
            Color.Blue
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonColor(snackBarHostState.currentSnackbarData),
                contentColor = Color.White
            ),
            onClick = {
                Log.d("testt", "MySnackBar: 스낵바 버튼 클릭")
                if(snackBarHostState.currentSnackbarData != null){
                    Log.d("testt", "MySnackBar: 이미 스낵바가 있다.")
                    snackBarHostState.currentSnackbarData?.dismiss()
                    return@Button
                }

                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        "오늘도 빡코딩",
                        "확인",
                        SnackbarDuration.Short
                    ).let {
                        when(it){
                            SnackbarResult.Dismissed ->  Log.d("testt", "MySnackBar: 스낵바 닫아짐")
                            SnackbarResult.ActionPerformed -> Log.d("testt", "MySnackBar: 스낵바 확인 버튼 클릭")
                        }
                    }
            }
        }) {
            Text(buttonTitle(snackBarHostState.currentSnackbarData))
        }

        // 스낵바 보여지는 부분
        SnackbarHost(hostState = snackBarHostState, modifier = Modifier.align(Alignment.BottomCenter))
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

//checked: Boolean, 체크 상태
//onCheckedChange: ((Boolean) -> Unit)?, 체크 상태 변경 콜백
//modifier: Modifier = Modifier,
//enabled: Boolean = true,
//interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
//colors: CheckboxColors = CheckboxDefaults.colors() 체크 박스 색

@Composable
fun CheckBoxContainer(){
    // MutableState 객체를 선언하는 세가지 방법
    val checkedStatusForFirst = remember { mutableStateOf(false) }
//    var checkedStatusForSecond by remember { mutableStateOf(false) }
//    val (checkedStatusForThird, setCheckedStatusForThird) = remember { mutableStateOf(false) }

    val checkedStatusForSecond = remember { mutableStateOf(false) }
    val checkedStatusForThird = remember { mutableStateOf(false) }

    val checkedStatesArray = listOf(
        checkedStatusForFirst,
        checkedStatusForSecond,
        checkedStatusForThird
    )

    val allBoxChecked: (Boolean) -> Unit = { isAllBoxChecked->
        Log.d("testt", "isAllBoxChecked: ${isAllBoxChecked}")
        checkedStatesArray.forEach { it.value = isAllBoxChecked }
    }

    val checkedStatusForFourth : Boolean = checkedStatesArray.all { it.value }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CheckboxWithTitle("1번 확인 사항", checkedStatusForFirst)
        CheckboxWithTitle("2번 확인 사항", checkedStatusForSecond)
        CheckboxWithTitle("3번 확인 사항", checkedStatusForThird)

//        Checkbox(
//            checked = checkedStatusForSecond,
//            onCheckedChange = { isChecked->
//                Log.d("testt", "CheckBoxContainer: isChecked : ${isChecked}")
//                checkedStatusForSecond = isChecked
//            }
//        )
        Spacer(modifier = Modifier.height(10.dp))
        AllAgreeCheckbox(title = "모두 동의", shouldChecked = checkedStatusForFourth, allBoxChecked)
        Spacer(modifier = Modifier.height(10.dp))
        MyCustomCheckBox(title = "커스텀 체크박스", withRipple = true) // 리플 있음
        MyCustomCheckBox(title = "커스텀 체크박스", withRipple = false) // 리플 없음
//        Checkbox(
//            checked = checkedStatusForThird,
//            onCheckedChange = { isChecked->
//                Log.d("testt", "CheckBoxContainer: isChecked : ${isChecked}")
//                setCheckedStatusForThird.invoke(isChecked)
//            },
//            colors = CheckboxDefaults.colors(
//                checkedColor = Color.Red,
//                uncheckedColor = Color(0xFFEF9A9A),
//                checkmarkColor = Color.Yellow
//            )
//        )
    }
}

@Composable
fun CheckboxWithTitle(title: String, isCheckedState : MutableState<Boolean>){
    Row(
        modifier = Modifier
//            .background(Color.Yellow)
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isCheckedState.value,
            onCheckedChange = { isChecked->
                Log.d("testt", "CheckBoxContainer: isChecked : ${isChecked}")
                isCheckedState.value = isChecked
            }
        )
        Text(text = title)
    }
}

@Composable
fun AllAgreeCheckbox(
    title: String,
    shouldChecked : Boolean,
    allBoxChecked: (Boolean)->(Unit)
){
    Row(
        modifier = Modifier
//            .background(Color.Yellow)
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = shouldChecked,
            onCheckedChange = { isChecked->
                Log.d("testt", "CheckBoxContainer: isChecked : ${isChecked}")
                allBoxChecked(isChecked)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Red,
                uncheckedColor = Color(0xFFEF9A9A),
                checkmarkColor = Color.Yellow
            )
        )
        Text(text = title)
    }
}
@Composable
fun MyCustomCheckBox(title: String, withRipple: Boolean = false){
    var isCheckedState by remember { mutableStateOf(false) }

    var rippleEffect = if(withRipple) rememberRipple(
        radius = 20.dp,
        color = Color.Blue
    )  else null

    Row(
        modifier = Modifier
//            .background(Color.Yellow)
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(60.dp)
                .padding(10.dp)
                .clickable(
                    indication = rippleEffect,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    Log.d("testt", "MyCustomCheckBox: 클릭됐다")
                    isCheckedState = !isCheckedState
                }
        ){
            Image(
                painter = if(isCheckedState) painterResource(id = R.drawable.ic_checked) else painterResource(id = R.drawable.ic_unchecked),
                contentDescription = null,
            )
        }


        Text(text = title)
    }
}


@Preview(showBackground = true)
@Composable
fun MyPreView() {
    ComposeBasicTheme {
        TextFieldTest()
    }
}