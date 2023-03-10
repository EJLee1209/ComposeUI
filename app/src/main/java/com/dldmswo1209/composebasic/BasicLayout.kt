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
            label = { Text(text = "????????? ??????") },
            placeholder = { Text(text = "????????? ?????????") },
            singleLine = false,
            maxLines = 2
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = phoneNumberInput,
            onValueChange = { phoneNumberInput = it },
            label = { Text(text = "????????????") },
            placeholder = { Text(text = "010-1234-1234") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = emailInput,
            onValueChange = { emailInput = it },
            label = { Text(text = "????????? ??????") },
            placeholder = { Text(text = "????????? ????????? ??????????????????") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            trailingIcon = { IconButton(onClick = { Log.d("testt", "TextFieldTest: ???????????? ??????")}) {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
            } }
//            trailingIcon = { Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null) }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = passwordInput,
            onValueChange = { passwordInput = it },
            label = { Text(text = "????????????") },
            placeholder = { Text(text = "??????????????? ??????????????????") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            trailingIcon = { IconButton(onClick = {
                Log.d("testt", "TextFieldTest: ???????????? visible ?????? ??????")
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
        if(snackbarData != null) "????????? ?????????" else "????????? ????????????"
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
                Log.d("testt", "MySnackBar: ????????? ?????? ??????")
                if(snackBarHostState.currentSnackbarData != null){
                    Log.d("testt", "MySnackBar: ?????? ???????????? ??????.")
                    snackBarHostState.currentSnackbarData?.dismiss()
                    return@Button
                }

                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        "????????? ?????????",
                        "??????",
                        SnackbarDuration.Short
                    ).let {
                        when(it){
                            SnackbarResult.Dismissed ->  Log.d("testt", "MySnackBar: ????????? ?????????")
                            SnackbarResult.ActionPerformed -> Log.d("testt", "MySnackBar: ????????? ?????? ?????? ??????")
                        }
                    }
            }
        }) {
            Text(buttonTitle(snackBarHostState.currentSnackbarData))
        }

        // ????????? ???????????? ??????
        SnackbarHost(hostState = snackBarHostState, modifier = Modifier.align(Alignment.BottomCenter))
    }
}


// Row - ???????????? ????????? ????????? ???
// arrangement : Row, Column ?????? ???????????? ????????????
// ???????????? ????????? ?????????????????? ???????????? ???????????? ????????? ??? ??????
// Arrangement.Start : ????????????
// Arrangement.End : ???????????????
// Arrangement.SpaceAround : ??? ????????? ????????????
// Arrangement.Center : ???????????? ??????
// Arrangement.SpaceBetween : ????????? ????????? ????????????
// Arrangement.SpaceEvenly : ????????? ????????? ????????? ????????? ??????

// alignment : LinerLayout ?????? gravity ??? ??????
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

// Column - ???????????? ????????? ????????? ???
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
//enabled: Boolean = true, ?????? ??????
//interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }, ???????????? ???????????? ??????
//elevation: ButtonElevation? = ButtonDefaults.elevation(), ?????????
//shape: Shape = MaterialTheme.shapes.small, // ??????
//border: BorderStroke? = null, // ?????????
//colors: ButtonColors = ButtonDefaults.buttonColors(), // ?????? ???
//contentPadding: PaddingValues = ButtonDefaults.ContentPadding, // ????????? ???????????? ??????
//content: @Composable RowScope.() -> Unit

@Composable
fun ButtonsContainer(){
    val buttonBorderGradient = Brush.horizontalGradient(listOf(Color.Yellow, Color.Red))

    // remember ?????? ????????? ?????? ??????, ?????????????????? ?????? ?????? ?????? ?????????
    val interactionSourceForFirstBtn = remember { MutableInteractionSource() }
    val isPressedForFirstBtn by interactionSourceForFirstBtn.collectIsPressedAsState()

    val interactionSourceForSecondBtn = remember { MutableInteractionSource() }
    val isPressedForSecondBtn by interactionSourceForSecondBtn.collectIsPressedAsState()

    val pressStatusTitle = if (isPressedForFirstBtn) "????????? ????????? ??????." else "???????????? ?????? ??????."
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
            Log.d("testt", "ButtonsContainer: ?????? 1 ??????")
        },
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp
        )) {
            Text(text = "?????? 1")
        }
        Button(
            onClick = {
                Log.d("testt", "ButtonsContainer: ?????? 2 ??????")
            },
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            enabled = true
        ) {
            Text(text = "?????? 2")
        }
        Button(
            onClick = {
                Log.d("testt", "ButtonsContainer: ?????? 3 ??????")
            },
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            enabled = true,
            shape = CircleShape
        ) {
            Text(text = "?????? 3")
        }

        Button(
            onClick = {
                Log.d("testt", "ButtonsContainer: ?????? 4 ??????")
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
            Text(text = "?????? 4")
        }

        Button(
            onClick = {
                Log.d("testt", "ButtonsContainer: ?????? 5 ??????")
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
            Text(text = "?????? 5", color = Color.White)
        }
        Text(text = pressStatusTitle)

        Button(
            onClick = {
                Log.d("testt", "ButtonsContainer: ?????? 6 ??????")
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
            Text(text = "?????? 6", color = Color.White)
        }
    }
}


// Box - ?????? ????????? ????????? ?????? ??? ??????
// propagateMinConstraints = true ??? ??????
// ?????? ?????? ?????? ?????? ?????? ????????? ?????? ???????????? ????????? ?????? ?????? ????????????????????? ??????(????????? ????????? ??????)
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

// BoxWithConstraints ??? Box ??? ??????????????? ?????? ?????? ??? ??????.
// ?????? Box ??? ???????????? ?????? ?????? ?????? ??????, ?????? ?????? ?????? ?????? ??? ??? ??????.
// ????????? ?????? ??? ????????? ??????
// ?????? ????????? ?????? ?????? ????????? ???????????? ????????? ??????????????? ????????? ??? ?????????
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
            Text(text = "????????? ??? ????????????.")
        }else{
            Text(text = "????????? ?????? ????????????.")
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
    val name = "?????????"

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
            append("????????????????")
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold
                ),
            ){
                append("????????? ????????? ?????????!")
            }
            withStyle(
              style = SpanStyle(color = Color.Red)
            ) {
                append("????????? ????????? ???????????????")
            }
        })
        Text(text = buildAnnotatedString {
            wordsArray.forEach {
                if(it.contains("??????")){
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

        ClickableText(text = AnnotatedString("?????????!"), onClick = {
            Log.d("testt", "TextContainer: ???????????? ????????????!")
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

    // color ??? ????????? ?????? ?????? ????????????, ????????? ???????????? ????????????
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

// ????????? shape
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

//checked: Boolean, ?????? ??????
//onCheckedChange: ((Boolean) -> Unit)?, ?????? ?????? ?????? ??????
//modifier: Modifier = Modifier,
//enabled: Boolean = true,
//interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
//colors: CheckboxColors = CheckboxDefaults.colors() ?????? ?????? ???

@Composable
fun CheckBoxContainer(){
    // MutableState ????????? ???????????? ????????? ??????
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

        CheckboxWithTitle("1??? ?????? ??????", checkedStatusForFirst)
        CheckboxWithTitle("2??? ?????? ??????", checkedStatusForSecond)
        CheckboxWithTitle("3??? ?????? ??????", checkedStatusForThird)

//        Checkbox(
//            checked = checkedStatusForSecond,
//            onCheckedChange = { isChecked->
//                Log.d("testt", "CheckBoxContainer: isChecked : ${isChecked}")
//                checkedStatusForSecond = isChecked
//            }
//        )
        Spacer(modifier = Modifier.height(10.dp))
        AllAgreeCheckbox(title = "?????? ??????", shouldChecked = checkedStatusForFourth, allBoxChecked)
        Spacer(modifier = Modifier.height(10.dp))
        MyCustomCheckBox(title = "????????? ????????????", withRipple = true) // ?????? ??????
        MyCustomCheckBox(title = "????????? ????????????", withRipple = false) // ?????? ??????
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
                    Log.d("testt", "MyCustomCheckBox: ????????????")
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