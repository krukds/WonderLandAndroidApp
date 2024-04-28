package com.example.wonderland.Views

import PreferencesManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wonderland.ViewModels.UserViewModel

@Composable
fun SettingsView(userViewModel: UserViewModel,
                 onBackNavCLicked: () -> Unit,
                 navigateToAuth: () -> Unit) {
    val focusManager = LocalFocusManager.current
    val user = userViewModel.userState.value
    val (email, setEmail) = remember { mutableStateOf(user.email) }
    val (firstname, setFirstname) = remember { mutableStateOf(user.first_name) }
    val (lastname, setLastname) = remember { mutableStateOf(user.last_name) }
    val (phone, setPhone) = remember { mutableStateOf(user.phone) }
    val (password, setPassword) = remember { mutableStateOf(user.password) }
    var passwordVisible by remember { mutableStateOf(false) }

    var showDialog = remember { mutableStateOf(false) }

    var showDialogError = remember { mutableStateOf(false) }

    if(userViewModel.errorState.value != "") {
        showDialogError.value = true
    }

    val context = LocalContext.current
    val onDeleteClicked = {
        userViewModel.deleteUser(PreferencesManager(context).getData("token", ""))
        userViewModel.updateUserId(-1)
        userViewModel.clearToken()
        PreferencesManager(context).saveData("token", "")
        navigateToAuth()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFECECEC))
            .verticalScroll(rememberScrollState())
    ) {
        TopBarView("My account settings", onBackNavCLicked)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "Accout information",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

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
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { setPassword(it) },
                label = { Text("Password") },
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
            Spacer(modifier = Modifier.height(26.dp))
            Text(
                "Personal information",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
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
            Spacer(modifier = Modifier.height(26.dp))

            Button(
                onClick = {
                    userViewModel.updateUser(
                        PreferencesManager(context).getData("token", ""),
                        email,
                        password,
                        firstname,
                        lastname,
                        phone
                    )
                    Toast.makeText(context, "Personal information was updated successfully!", Toast.LENGTH_LONG).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF4C0099)
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Done", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(26.dp))
            Text(
                "Delete account",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "By deleting your account, you may be unable to access certain services.",
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Delete Account",
                color = Color(0xFF4C0099),
                modifier = Modifier.clickable {
                    showDialog.value = true
                }
            )
            DeleteAccountDialog(showDialog = showDialog, onDeleteClicked)
            ErrorDialog(
                userViewModel,
                text = userViewModel.errorState.value,
                showDialog = showDialogError
            )
        }
    }

}

@Preview
@Composable
fun PreviewSettingsView() {
    SettingsView(UserViewModel(), {}, {})
}