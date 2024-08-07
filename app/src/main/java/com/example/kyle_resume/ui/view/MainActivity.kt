package com.example.kyle_resume.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kyle_resume.R
import com.example.kyle_resume.ui.theme.KyleresumeTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KyleresumeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val scrollState = rememberScrollState()
    val maxScrollValue = scrollState.maxValue

    val firstAlphaStart = 0f
    val firstAlphaEnd = 0.25f
    val secondAlphaStart = 0.52f
    val secondAlphaEnd = 0.5f
    val thirdAlphaStart = 0.5f
    val thirdAlphaEnd = 0.75f
    val fourthAlphaStart = 0.75f
    val fourthAlphaEnd = 1f

    val scrollPercentage = scrollState.value / maxScrollValue.toFloat()

    val firstAlpha by animateFloatAsState(
        targetValue = when {
            scrollPercentage in firstAlphaStart..firstAlphaEnd -> (scrollPercentage - firstAlphaStart) / (firstAlphaEnd - firstAlphaStart)
            scrollPercentage > firstAlphaEnd -> 1f
            else -> 0f
        }
    )
    val secondAlpha by animateFloatAsState(
        targetValue = when {
            scrollPercentage in secondAlphaStart..secondAlphaEnd -> (scrollPercentage - secondAlphaStart) / (secondAlphaEnd - secondAlphaStart)
            scrollPercentage > secondAlphaEnd -> 1f
            else -> 0f
        }
    )
    val thirdAlpha by animateFloatAsState(
        targetValue = when {
            scrollPercentage in thirdAlphaStart..thirdAlphaEnd -> (scrollPercentage - thirdAlphaStart) / (thirdAlphaEnd - thirdAlphaStart)
            scrollPercentage > thirdAlphaEnd -> 1f
            else -> 0f
        }
    )
    val fourthAlpha by animateFloatAsState(
        targetValue = when {
            scrollPercentage in fourthAlphaStart..fourthAlphaEnd -> (scrollPercentage - fourthAlphaStart) / (fourthAlphaEnd - fourthAlphaStart)
            scrollPercentage > fourthAlphaEnd -> 1f
            else -> 0f
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .background(Color.White)
        ) {
            Image(
                painter = painterResource(id = R.drawable.kyle),
                contentDescription = "me",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(firstAlpha)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(Color.Black)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "안녕하세요",
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(firstAlpha)
                    .padding(bottom = 8.dp),
                style = TextStyle(color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 65.sp)
            )
            Text(
                text = "무슨무슨 개발자",
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(secondAlpha)
                    .padding(bottom = 8.dp),
                style = TextStyle(color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 50.sp)
            )
            Text(
                text = "Kyle",
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(thirdAlpha)
                    .padding(bottom = 8.dp),
                style = TextStyle(color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 100.sp)
            )
            Text(
                text = "입니다",
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(fourthAlpha)
                    .padding(bottom = 8.dp),
                style = TextStyle(color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 50.sp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(Color.Black)
        ) {
            InfiniteLoopPager(modifier = Modifier.fillMaxSize())
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {

        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun InfiniteLoopPager(
    modifier: Modifier = Modifier,
    list: List<Painter> = listOf(
        painterResource(id = R.drawable.beach),
        painterResource(id = R.drawable.child),
        painterResource(id = R.drawable.fishing),
        painterResource(id = R.drawable.cake),
        painterResource(id = R.drawable.layup)
    )
) {
    val pageDelay = 1000L
    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = Unit) {
        var initialPage = Int.MAX_VALUE / 2

        while (initialPage % list.size != 0) {
            initialPage++
        }
        pagerState.scrollToPage(initialPage)
    }

    LaunchedEffect(key1 = pagerState.currentPage) {
        launch {
            while (true) {
                delay(pageDelay)
                withContext(NonCancellable) {
                    if (pagerState.currentPage + 1 in 0..Int.MAX_VALUE) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }
        }
    }

    Box(modifier = modifier) {
        HorizontalPager(
            count = Int.MAX_VALUE,
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
        ) { index ->
            list.getOrNull(index % (list.size))?.let { img ->
                ImageCard(painter = img, contentDescription = "hi")
            }
        }

        PagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 16.dp),
            count = list.size,
            dotSize = 6.dp,
            spacedBy = 4.dp,
            currentPage = pagerState.currentPage % list.size,
            selectedColor = Color.White,
            unSelectedColor = Color.LightGray
        )
    }
}

@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    count: Int,
    dotSize: Dp,
    spacedBy: Dp,
    currentPage: Int,
    selectedColor: Color,
    unSelectedColor: Color
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(spacedBy)) {
        (0 until count).forEach { index ->
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .background(
                        color = if (index == currentPage) {
                            selectedColor
                        } else {
                            unSelectedColor
                        },
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
fun ImageCard(
    painter: Painter,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxSize(),
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
        }
    }
}

