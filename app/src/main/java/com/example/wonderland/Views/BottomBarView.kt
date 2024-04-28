package com.example.wonderland.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.wonderland.R
import com.example.wonderland.Screen


data class BottomItem(
    val label:String,
    val iconId: Int,
    val route: String
)

@Composable
fun BottomBarView(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val availableRoutes = listOf(
        Screen.MainScreen.route,
        Screen.RestaurantScreen.route,
        Screen.AccountScreen.route,
        Screen.AuthorizationScreen.route
    );
    val itemList: List<BottomItem> = listOf(
        BottomItem(
            "Main",
            R.drawable.baseline_home_24,
            Screen.MainScreen.route
        ),
        BottomItem(
            "Food",
            R.drawable.fork_and_knife,
            Screen.RestaurantScreen.route
        ),
        BottomItem(
            "Account",
            R.drawable.baseline_person_24,
            Screen.AccountScreen.route
        )
    )

    if (availableRoutes.contains(currentRoute)){
        BottomNavigation(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
            backgroundColor = Color.White,
            elevation = 20.dp
        )
        {
            itemList.forEach {
                    item ->
                NavBottomItem(navController, item)
            }

        }
    }

}

@Composable
fun NavBottomItem(
    navController: NavController,
    item: BottomItem
){
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route;

    var color = Color.Black
    if (currentRoute == item.route){
        color = Color(0xFF4C0099)
    }

    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(horizontal = 26.dp, vertical = 8.dp)
        .background(Color.White)
        .clickable { navController.navigate(item.route) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painter = painterResource(id = item.iconId), contentDescription = null,
            modifier = Modifier.size(30.dp),
            tint = color)
        Text(text = item.label, fontSize = 14.sp, color = color)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewBottomBarView() {
//     BottomBarView(navController = rememberNavController())
//}
