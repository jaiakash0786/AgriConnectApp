package com.example.agriconnectapp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SignupScreen(navController: NavController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("Farmer") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Signup",
            fontSize = 32.sp,
            color = Color(0xFF4CAF50), // Green color
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Name Input
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Email Input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Confirm Password Input
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Role Selection
        Row {
            RadioButton(
                selected = role == "Farmer",
                onClick = { role = "Farmer" }
            )
            Text("Farmer", modifier = Modifier.padding(start = 4.dp))

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = role == "Exporter",
                onClick = { role = "Exporter" }
            )
            Text("Exporter", modifier = Modifier.padding(start = 4.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        errorMessage?.let {
            Text(text = it, color = Color.Red, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Signup Button
        Button(
            onClick = {
                if (password != confirmPassword) {
                    errorMessage = "Passwords do not match"
                    return@Button
                }
                isLoading = true
                errorMessage = null
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid ?: return@addOnCompleteListener
                            val user = hashMapOf(
                                "name" to name,
                                "email" to email,
                                "role" to role
                            )
                            db.collection("users").document(userId).set(user)
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Signup Successful", Toast.LENGTH_SHORT).show()
                                    navController.navigate("dashboard")
                                }
                                .addOnFailureListener { e ->
                                    errorMessage = "Firestore Error: ${e.message}"
                                    Log.e("Signup", "Firestore Error: ${e.message}")
                                }
                        } else {
                            errorMessage = task.exception?.message ?: "Signup Failed"
                            Log.e("Signup", "Error: ${task.exception?.message}")
                        }
                        isLoading = false
                    }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()
        ) {
            Text("Signup", fontSize = 18.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Navigate to Login
        TextButton(
            onClick = { navController.navigate("login") },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Already have an account? Login", color = Color(0xFF4CAF50))
        }

        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    SignupScreen(navController = NavController(LocalContext.current))
}
