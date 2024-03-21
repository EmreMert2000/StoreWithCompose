package com.example.storewithcompose.Screens


import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.storewithcompose.R
import com.google.firebase.auth.FirebaseAuth



@Composable
fun LoginScreen(

    onLoginClick:() ->Unit,
    onCustomerClick: () -> Unit
)
{
    var email by remember {
        mutableStateOf("")
    }
    var password by remember{
        mutableStateOf("")


    }
    val context= LocalContext.current


    Column (
        modifier= Modifier
            .fillMaxSize()
            .padding(16.dp)
        )
    {
        //Logo Design
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Resminiz için kaynak tanımını burada değiştirin
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },

            textStyle = TextStyle(
                color = Color.Blue,
                fontSize = 18.sp
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
           Spacer(modifier=Modifier.height(16.dp))


        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            textStyle = TextStyle(
                color = Color.Blue,
                fontSize = 18.sp
            ),
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(modifier = Modifier.height((16.dp)))

        //Login Button
        Button(onClick ={ signInWithEmailAndPassword(email,password,context,onLoginClick) }
            ,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)


        ) {
            Text(text = "Giriş Yap.")
        }

           Spacer(modifier = Modifier.height(16.dp))

       Button(onClick = {onCustomerClick()},modifier = Modifier
           .fillMaxWidth()
           .height(56.dp)) {
              Text(text = "Müşteriyim.")

       }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /*TODO*/ },modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)){
            Text(text = "Hakkımızda")
        }






    }



}

//I will do it later ...
fun signInWithEmailAndPassword(
    email: String,
    password: String,
    context: Context,
    onLoginClick: () -> Unit
) {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {

                onLoginClick()

                Toast.makeText(context, "Ürünlerinizi görebilirsiniz.", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(
                    context,
                    "Giriş yapılamadı. Lütfen e-posta ve şifrenizi kontrol edin.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}
