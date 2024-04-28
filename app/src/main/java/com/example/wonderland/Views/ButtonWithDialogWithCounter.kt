import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.wonderland.ViewModels.AttractionTicketViewModel
import com.example.wonderland.ViewModels.EventTicketViewModel
import com.example.wonderland.ViewModels.UserViewModel
import com.example.wonderland.Views.NeedToLoginDialog

@Composable
fun ButtonWithDialogWithCounter(screenId: Int,
                                attractionOrEventId: Int,
                                userViewModel: UserViewModel,
                                attractionTicketViewModel: AttractionTicketViewModel,
                                eventTicketViewModel: EventTicketViewModel,
                                navigateToAuth: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var showDialogLogin = remember { mutableStateOf(false) }

    var counterValue by remember { mutableStateOf(1) }
    val contex = LocalContext.current
    userViewModel.fetchUser(PreferencesManager(contex).getData("token", ""))
    Button(
        onClick = {
            if(userViewModel.userState.value.id == -1) {
                showDialogLogin.value = true
            } else
                showDialog = true
        },
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF4C0099)
        )
    ) {
        Text(
            "Buy ticket",
            color = Color.White,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Column(
                modifier = Modifier
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Choose ticket amount:", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(
                        onClick = { if (counterValue > 1) counterValue-- },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        elevation = ButtonDefaults.elevation(0.dp)
                    ) {
                        Text("–", fontSize = 40.sp)
                    }
                    Text(
                        text = "$counterValue",
                        modifier = Modifier
                            .widthIn(min = 30.dp),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        fontSize = 40.sp
                    )
                    Button(
                        onClick = { counterValue++ },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        elevation = ButtonDefaults.elevation(0.dp)

                    ) {
                        Text("+", fontSize = 40.sp)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { showDialog = false },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        elevation = ButtonDefaults.elevation(0.dp)
                    ) {
                        Text("Cancel", color = Color(0xFF4C0099))
                    }
                    Button(
                        onClick = {
                            for(i in 0 until counterValue) {
                                if(screenId == 1) {
                                    attractionTicketViewModel.addTicket(
                                        PreferencesManager(contex).getData("token", ""),
                                        attractionOrEventId
                                    )
                                } else if(screenId == 2) {
                                    eventTicketViewModel.addTicket(
                                        PreferencesManager(contex).getData("token", ""),
                                        attractionOrEventId
                                    )
                                }
                            }
                            Toast.makeText(contex, "Tickets was bought successfully!", Toast.LENGTH_LONG).show()
                            showDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4C0099)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            "Buy",
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }

    NeedToLoginDialog(text = "You need to authorize first to buy tickets.", showDialog = showDialogLogin, navigateToAuth)

}

@Preview
@Composable
fun f() {
    ButtonWithDialogWithCounter(1,1, UserViewModel(), AttractionTicketViewModel(), EventTicketViewModel(), {})
}
