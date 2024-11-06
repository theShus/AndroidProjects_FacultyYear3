package com.example.rmaproject2.presentation.view.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rmaproject2.R
import com.example.rmaproject2.presentation.view.ui.theme.Purple500
import com.example.rmaproject2.presentation.view.ui.theme.RMAProject2Theme


class LoginActivity : ComponentActivity() {
    companion object {
        const val USERNAME = "USERNAME"
        const val PASSWORD = "PASSWORD"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RMAProject2Theme {
                navigatePage()
            }
        }
    }

    override fun finish() {
        super.finish()
    }
}

@Composable
fun navigatePage() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login_page",
        builder = {
            composable("login_page", content = { LoginPage(navController = navController) })
        }
    )

}


@Composable
fun LoginPage(navController: NavController) {



    val context = LocalContext.current
    val activity = (LocalContext.current as? Activity)

    val scaffoldState = rememberScaffoldState()
    val usernameVal = remember { mutableStateOf("") }
    val passwordVal = remember { mutableStateOf("") }

    val sharedPreferences: SharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)

    val passwordVisiblity = remember { mutableStateOf(false) }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White),
                contentAlignment = Alignment.TopCenter
            ) {

            }

            Spacer(modifier = Modifier.padding(100.dp))

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                scaffoldState = scaffoldState
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(25.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "RAF Student Helper",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.padding(20.dp))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = usernameVal.value,
                            onValueChange = { usernameVal.value = it },
                            label = { Text(text = "Username") },
                            placeholder = { Text(text = "Username") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )

                        OutlinedTextField(
                            value = passwordVal.value,
                            onValueChange = { passwordVal.value = it },
                            trailingIcon = {
                                IconButton(onClick = {
                                    passwordVisiblity.value = !passwordVisiblity.value
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.password_eye),
                                        contentDescription = "password",
                                        tint = if (passwordVisiblity.value) Purple500 else Color.Gray
                                    )
                                }
                            },
                            label = { Text(text = "Password") },
                            placeholder = { Text(text = "Password") },
                            singleLine = true,
                            visualTransformation = if (passwordVisiblity.value)
                                VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )

                        Spacer(modifier = Modifier.padding(10.dp))


                        Button(
                            onClick = {
                                if (usernameVal.value.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Please enter username!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else if (passwordVal.value.isEmpty()) {
                                    Toast.makeText(
                                        context,
                                        "Please enter the password",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    sharedPreferences//stavimo koji je user trenutno ulogovan
                                        .edit()
                                        .putString(LoginActivity.USERNAME, usernameVal.toString())
                                        .putString(LoginActivity.PASSWORD, passwordVal.toString())
                                        .apply();
                                    Toast.makeText(context,"Logged Successfully!",Toast.LENGTH_SHORT).show()


                                    context.startActivity(Intent(context, AppActivity::class.java))
                                    activity?.finish()

                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(50.dp)
                        ) {
                            Text(text = "Sign In", fontSize = 20.sp)
                        }

                        Spacer(modifier = Modifier.padding(20.dp))

                        Text(
                            text = "Create an Account?",
                            modifier = Modifier
                                .clickable {
                                    navController.navigate("register_page")
                                }
                        )

                        Spacer(modifier = Modifier.padding(20.dp))
                    }
                }
            }
        }
    }
}
