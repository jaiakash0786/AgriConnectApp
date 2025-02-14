package com.example.agriconnectapp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Login Title
        Text(
            text = "Login",
            fontSize = 32.sp,
            color = Color(0xFF4CAF50), // Green color
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Email Input Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password Input Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Error Message
        errorMessage?.let {
            Text(text = it, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Login Button
        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    errorMessage = "Please enter email and password"
                    return@Button
                }
                isLoading = true
                errorMessage = null
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        isLoading = false
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                            navController.navigate("main") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            errorMessage = task.exception?.localizedMessage ?: "Login Failed"
                            Log.e("Login", "Error: ${task.exception?.message}")
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotEmpty() && password.isNotEmpty()
        ) {
            Text("Login", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Sign Up Navigation
        TextButton(
            onClick = { navController.navigate("signup") },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Don't have an account? Sign Up", color = Color(0xFF4CAF50)) // Green color
        }

        // Loading Indicator
        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}
