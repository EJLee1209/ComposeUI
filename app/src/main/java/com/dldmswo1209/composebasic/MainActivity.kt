package com.dldmswo1209.composebasic


import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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
                    NavigationGraph()
                }
            }
        }
    }
}

// 네비게이션 라우트 이넘 (값을 가지는 이넘)
enum class NavRoute(val routeName: String, val description: String, val btnColor: Color){
    MAIN("MAIN","메인 화면",Color(0xFF90CAF9)),
    LOGIN("LOGIN","로그인 화면",Color(0xFFBC90F9)),
    REGISTER("REGISTER","회원가입 화면",Color(0xFFD64A9E)),
    USER_PROFILE("USER_PROFILE","유저 프로필 화면",Color(0xFF90F99B)),
    SETTING("SETTING","설정 화면",Color(0xFFFDE076)),
}

// 네비게이션 라우트 액션
class RouteAction(navHostController: NavHostController){
    // 특정 라우트로 이동
    val navTo: (NavRoute) -> (Unit) = { route->
        navHostController.navigate(route.routeName)
    }
    // 뒤로가기 이동
    val goBack: () -> (Unit) = {
        navHostController.navigateUp() // 뒤로가기
    }
}

@Composable
fun NavigationGraph(startRoute: NavRoute = NavRoute.MAIN){ // starting : 라우트 시작 지점
    // 네비게이션 컨트롤러
    val navController = rememberNavController()

    // 네비게이션 라우트 액션
    val routeAction = remember(navController) { RouteAction(navController) }

    // NavHost 로 네비게이션 결정
    // 네비게이션 연결할 녀석들을 설정
    NavHost(navController, startRoute.routeName){
        // 라우트 이름 = 화면의 키
        composable(NavRoute.MAIN.routeName){
            // 화면 = 값
            MainScreen(routeAction = routeAction)
        }
        // 라우트 이름 = 화면의 키
        composable(NavRoute.LOGIN.routeName){
            // 화면 = 값
            LoginScreen(routeAction = routeAction)
        }
        // 라우트 이름 = 화면의 키
        composable(NavRoute.REGISTER.routeName){
            // 화면 = 값
            RegisterScreen(routeAction = routeAction)
        }
        // 라우트 이름 = 화면의 키
        composable(NavRoute.USER_PROFILE.routeName){
            // 화면 = 값
            UserProfileScreen(routeAction = routeAction)
        }
        // 라우트 이름 = 화면의 키
        composable(NavRoute.SETTING.routeName){
            // 화면 = 값
            SettingScreen(routeAction = routeAction)
        }
    }

}

// 메인 화면
@Composable
fun MainScreen(routeAction: RouteAction){
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.padding(16.dp)) {
            NavButton(route = NavRoute.LOGIN, routeAction = routeAction)
            NavButton(route = NavRoute.REGISTER, routeAction = routeAction)
            NavButton(route = NavRoute.USER_PROFILE, routeAction = routeAction)
            NavButton(route = NavRoute.SETTING, routeAction = routeAction)
        }
    }
}

// 로그인 화면
@Composable
fun LoginScreen(routeAction: RouteAction){
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.padding(8.dp), Alignment.Center) {
            Text(text = "로그인 화면",style = TextStyle(Color.Black, 22.sp, FontWeight.Medium))
            Button(
                onClick = routeAction.goBack,
                Modifier
                    .padding(16.dp)
                    .offset(y = 100.dp)
            ) {
                Text(text = "뒤로가기")
            }
        }
    }
}

// 회원가입 화면
@Composable
fun RegisterScreen(routeAction: RouteAction){
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.padding(8.dp), Alignment.Center) {
            Text(text = "회원가입 화면",style = TextStyle(Color.Black, 22.sp, FontWeight.Medium))
            Button(
                onClick = routeAction.goBack,
                Modifier
                    .padding(16.dp)
                    .offset(y = 100.dp)
            ) {
                Text(text = "뒤로가기")
            }
        }
    }
}

// 유저 프로필 화면
@Composable
fun UserProfileScreen(routeAction: RouteAction){
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.padding(8.dp), Alignment.Center) {
            Text(text = "유저 프로필 화면",style = TextStyle(Color.Black, 22.sp, FontWeight.Medium))
            Button(
                onClick = routeAction.goBack,
                Modifier
                    .padding(16.dp)
                    .offset(y = 100.dp)
            ) {
                Text(text = "뒤로가기")
            }
        }
    }
}

// 설정 화면
@Composable
fun SettingScreen(routeAction: RouteAction){
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.padding(8.dp), Alignment.Center) {
            Text(text = "설정 화면",style = TextStyle(Color.Black, 22.sp, FontWeight.Medium))
            Button(
                onClick = routeAction.goBack,
                Modifier
                    .padding(16.dp)
                    .offset(y = 100.dp)
            ) {
                Text(text = "뒤로가기")
            }
        }
    }
}

// 컬럼에 있는 네비게이션 버튼
@Composable
fun ColumnScope.NavButton(route: NavRoute, routeAction: RouteAction){
    Button(onClick = {
        routeAction.navTo(route)
    }, colors = ButtonDefaults.buttonColors(backgroundColor = route.btnColor),
    modifier = Modifier
        .weight(1f)
        .padding(8.dp)
        .fillMaxSize()) {
        Text(text = route.description,
        style = TextStyle(Color.White, 22.sp, FontWeight.Medium)
        )
    }
}

@Composable
fun UserListView(userVM: UserVM = viewModel()){
    // collectAsState() - flow 값 변화 감지 근데 state 이므로 값이 들어오면, 리컴포지션에 의해 뷰가 다시 그려짐
    val users by userVM.usersFlow.collectAsState()

    if(users.isEmpty()){
        Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    }else{
        LazyColumn(){
            items(users){
                UserView(data = it)
            }
        }
    }
}

@Composable
fun UserView(data: User){
    val typography = MaterialTheme.typography
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // imageUrl = data.avatar 하면 되는데, ktor 로 가져온 더미데이터의 이미지가 로드가 안되서 그냥 고양이사진 넣어놨음
            ProfileImage(imageUrl = "https://product.cdn.cevaws.com/var/storage/images/_aliases/reference/media/feliway-2017/images/kor-kr/1_gnetb-7sfmbx49emluey4a/6341829-1-kor-KR/1_gNETb-7SfMBX49EMLUeY4A.jpg")
            Column() {
                Text(text = data.name, style = typography.body1)
            }
        }
    }
}


@Composable
fun ProfileImage(imageUrl: String, modifier: Modifier = Modifier){
    // 이미지 비트맵
    val bitmap: MutableState<Bitmap?> = remember { mutableStateOf(null) }
    val imageModifier = modifier
        .size(50.dp, 50.dp)
        .clip(CircleShape)

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(imageUrl)
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                // 이미지를 비트맵으로 받아서 준비가 됐으면
                bitmap.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    // bitmap 이 있다면
    bitmap.value?.asImageBitmap()?.let {
        Image(
            bitmap = it,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = imageModifier
        )
    } ?: Image(
        painter = painterResource(id = R.drawable.ic_empty_user_image),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = imageModifier
    )
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
        NavigationGraph()
    }
}