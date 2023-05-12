package com.machado.learncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.machado.learncompose.ui.theme.LearnComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearnComposeTheme {
                CalculatorUI()
            }
        }
    }
}


@Composable
fun CalculatorUI() {
    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    var operation by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(24.dp)
        ) {
            Text(text = number1, fontSize = 48.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = operation, fontSize = 48.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = number2, fontSize = 48.sp)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.5f),
            verticalArrangement = Arrangement.Bottom
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(2f)
                        .height(70.dp),
                    onClick = {
                        number1 = ""
                        number2 = ""
                        operation = ""
                    }) {
                    Text(text = "AC", modifier = Modifier.padding(8.dp))
                }
                Button(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                        .height(70.dp),
                    onClick = {
                        if (number2 != "") {
                            number2 = number2.substring(0..number2.length - 2)
                        } else if (number1 != "") {
                            number1 = number1.substring(0..number1.length - 2)
                        }
                    }) {
                    Text(text = "<-", modifier = Modifier.padding(8.dp))
                }
                OperationButton(operation = "/", modifier = Modifier.weight(1f)) {
                    operation = it
                    if (number1.length > 6) number1 = number1.substring(0..6)
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                for (i in 7..9) {
                    NumberButton(number = i, modifier = Modifier.weight(1f), onPress = { num ->
                        if (operation == "") {
                            number1 += num.toString()
                        } else {
                            number2 += num.toString()
                        }
                    })
                }
                OperationButton(operation = "x") {
                    operation = it
                    if (number1.length > 6) number1 = number1.substring(0..6)
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                for (i in 4..6) {
                    NumberButton(number = i, modifier = Modifier.weight(1f), onPress = { num ->
                        if (operation == "") {
                            number1 += num.toString()
                        } else {
                            number2 += num.toString()
                        }
                    })
                }
                OperationButton(operation = "+", modifier = Modifier.weight(1f)) {
                    operation = it
                    if (number1.length > 6) number1 = number1.substring(0..6)
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                for (i in 1..3) {
                    NumberButton(number = i, modifier = Modifier.weight(1f), onPress = { num ->
                        if (operation == "") {
                            number1 += num.toString()
                        } else {
                            number2 += num.toString()
                        }
                    })
                }
                OperationButton(operation = "-") {
                    operation = it
                    if (number1.length > 6) number1 = number1.substring(0..6)
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                NumberButton(number = 0, modifier = Modifier.weight(1f)) { num ->
                    if (operation == "") {
                        number1 += num.toString()
                    } else {
                        number2 += num.toString()
                    }
                }
                OperationButton(operation = "=", modifier = Modifier.weight(1f)) {
                    if (number1.length > 6) number1 = number1.substring(0..6)
                    if (number2.length > 6) number2 = number2.substring(0..6)

                    if (number1 != "" && number2 != "") {
                        val answer: Long = when (operation) {
                            "/" -> {
                                if (number2.toLong() == 0L) {
                                    0
                                } else {
                                    number1.toLong() / number2.toLong()
                                }
                            }

                            "x" -> {
                                number1.toLong() * number2.toLong()
                            }

                            "+" -> {
                                number1.toLong() + number2.toLong()
                            }

                            else -> {
                                number1.toLong() - number2.toLong()
                            }
                        }
                        number1 = "$answer"
                        number2 = ""
                        operation = ""
                    }

                }
            }

        }
    }
}

@Composable
fun OperationButton(
    operation: String,
    modifier: Modifier = Modifier,
    onPress: (operation: String) -> Unit
) {
    Button(
        modifier = modifier
            .padding(8.dp)
            .height(70.dp),
        onClick = {
            onPress(operation)
        }) {
        Text(
            text = operation,
            modifier = Modifier.padding(8.dp),
        )
    }
}

@Composable
fun NumberButton(number: Int, modifier: Modifier = Modifier, onPress: (number: Int) -> Unit) {
    Button(
        modifier = modifier
            .padding(8.dp)
            .height(70.dp),
        onClick = {
            onPress(number)
        },
    ) {
        Text(
            text = number.toString(),
            modifier = Modifier.padding(8.dp),
        )
    }
}