package com.example.wonderland.Views

import PreferencesManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wonderland.ViewModels.UserViewModel

@Composable
fun SignupView(userViewModel: UserViewModel,
               onCloseCLicked: () -> Unit,
               navigateToAccount: () -> Unit,
               preferenceManager: PreferencesManager) {
    val focusManager = LocalFocusManager.current

    var showDialog = remember { mutableStateOf(false) }
    if(userViewModel.errorState.value != "") {
        showDialog.value = true
    } else if (userViewModel.tokenState.value != ""){
        preferenceManager.saveData("token", "Bearer " + userViewModel.tokenState.value)
        userViewModel.updateUserId(0)
        navigateToAccount()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = { focusManager.clearFocus() })
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFF4C0099), Color.Magenta),
                        center = Offset(0f, 0.5f), // Центр градієнту відносно розміру контейнера
                        radius = 1500f // Радіус градієнту
                    )
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "WonderLand",
                color = Color.White,
                fontSize = 50.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold
            )
        }
        IconButton(
            onClick = onCloseCLicked,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .background(Color.White)
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 180.dp)
                .background(
                    Color.White,
                    shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                )
                .padding(vertical = 32.dp, horizontal = 22.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("Create your account", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "You'll be able to log into services and experiences using the same email and password.",
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            SignUpForm(focusManager, userViewModel)
            ErrorDialog(
                userViewModel,
                text = userViewModel.errorState.value,
                showDialog = showDialog
            )
        }
    }
}

@Composable
fun SignUpForm(focusManager: FocusManager, userViewModel: UserViewModel) {
    val (email, setEmail) = remember { mutableStateOf("") }
    val (firstname, setFirstname) = remember { mutableStateOf("") }
    val (lastname, setLastname) = remember { mutableStateOf("") }
    val (phone, setPhone) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    TextField(
        value = email,
        onValueChange = setEmail,
        label = { Text("Email") },
        modifier = Modifier
            .fillMaxWidth(),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        )
    )
    Spacer(modifier = Modifier.height(16.dp))
    TextField(
        value = firstname,
        onValueChange = setFirstname,
        label = { Text("First Name") },
        modifier = Modifier
            .fillMaxWidth(),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        )
    )
    Spacer(modifier = Modifier.height(16.dp))
    TextField(
        value = lastname,
        onValueChange = setLastname,
        label = { Text("Last Name") },
        modifier = Modifier
            .fillMaxWidth(),
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        )
    )
    Spacer(modifier = Modifier.height(16.dp))
    TextField(
        value = phone,
        onValueChange = {
            setPhone(it.filter { it.isDigit() }.take(10))  // Залишаємо тільки цифри і обмежуємо до 10 цифр
        },
        label = { Text("Phone Number") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Gray
        ),
        shape = RoundedCornerShape(8.dp),

        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        )
    )
    Spacer(modifier = Modifier.height(16.dp))
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = { setPassword(it) },
        label = { Text("Create password") },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        placeholder = { Text("Password") },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        trailingIcon = {
            val image = if (passwordVisible)
                Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            // Toggle button to hide or display password
            IconButton(onClick = {passwordVisible = !passwordVisible}){
                Icon(imageVector  = image, description)
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Gray
        ),

        )
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = {
            userViewModel.signup(email, password, firstname, lastname, phone)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF4C0099)
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text("Sign up", fontSize = 18.sp, color = Color.White)
    }
}


//@Preview
//@Composable
//fun PreviewSignup() {
//    SignupView(AuthViewModel(),{})
//}