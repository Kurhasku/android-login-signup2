package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf("login") }

    if (currentScreen == "login") {
        LoginScreen(onSignUpClick = { currentScreen = "signup" })
    } else {
        SignUpScreen()
    }
}

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun LoginScreen(onSignUpClick: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LoginContent(
        email = email,
        onEmailChange = { email = it },
        password = password,
        onPasswordChange = { password = it },
        onLoginClick = { Log.d("Login", "Login clicked") },
        onSignUpClick = onSignUpClick
    )
}

@Composable
fun LoginContent(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", fontSize = 28.sp)
        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            label = "Email",
            value = email,
            onValueChange = onEmailChange,
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            label = "Password",
            value = password,
            onValueChange = onPasswordChange,
            keyboardType = KeyboardType.Password,
            isPassword = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = onLoginClick) {
                Text("Login")
            }
            Spacer(modifier = Modifier.width(12.dp))
            Button(onClick = onSignUpClick) {
                Text("Sign Up")
            }
        }
    }
}

@Composable
fun SignUpScreen() {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var agreed by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }

    SignUpContent(
        firstName = firstName,
        onFirstNameChange = { firstName = it },
        lastName = lastName,
        onLastNameChange = { lastName = it },
        email = email,
        onEmailChange = { email = it },
        password = password,
        onPasswordChange = { password = it },
        confirmPassword = confirmPassword,
        onConfirmPasswordChange = { confirmPassword = it },
        agreed = agreed,
        onAgreedChange = { agreed = it },
        showPassword = showPassword,
        onShowPasswordChange = { showPassword = it },
        onCreateAccountClick = {
            Log.d("SignUp", "First name: $firstName, Last name: $lastName, Email: $email")
        }
    )
}

@Composable
fun SignUpContent(
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    agreed: Boolean,
    onAgreedChange: (Boolean) -> Unit,
    showPassword: Boolean,
    onShowPasswordChange: (Boolean) -> Unit,
    onCreateAccountClick: () -> Unit
) {
    val allFieldsFilled = firstName.isNotEmpty() &&
            lastName.isNotEmpty() &&
            email.isNotEmpty() &&
            password.isNotEmpty() &&
            confirmPassword.isNotEmpty()

    val passwordsMatch = password == confirmPassword

    val buttonEnabled = allFieldsFilled && passwordsMatch && agreed && password.length >= 6

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Create account", fontSize = 28.sp)
        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            label = "First name",
            value = firstName,
            onValueChange = onFirstNameChange
        )
        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            label = "Last name",
            value = lastName,
            onValueChange = onLastNameChange
        )
        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            label = "Email address",
            value = email,
            onValueChange = onEmailChange,
            keyboardType = KeyboardType.Email
        )
        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            label = "Password",
            value = password,
            onValueChange = onPasswordChange,
            keyboardType = KeyboardType.Password,
            isPassword = !showPassword
        )

        if (password.length < 6) {
            Text(text = "Password must be at least 6 characters", color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(8.dp))

        InputField(
            label = "Confirm password",
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            keyboardType = KeyboardType.Password,
            isPassword = !showPassword
        )

        if (confirmPassword.isNotEmpty() && !passwordsMatch) {
            Text(text = "Passwords do not match", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = showPassword, onCheckedChange = onShowPasswordChange)
            Text("Show password")
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = agreed, onCheckedChange = onAgreedChange)
            Text("I agree to the Terms of Use")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onCreateAccountClick,
            enabled = buttonEnabled,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Create account")
        }
    }
}