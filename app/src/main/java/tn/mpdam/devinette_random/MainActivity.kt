package tn.mpdam.devinette_random

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tn.mpdam.devinette_random.ui.theme.Devinette_RandomTheme
import android.util.Log
import androidx.compose.runtime.*


class MainActivity : ComponentActivity() {
    private var score by mutableStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Devinette_RandomTheme {
                Surface(color = Color.LightGray) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                                .background(MaterialTheme.colors.primary),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Drag & Drop RandomNumbers",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        DragDropList(
                            items = ReorderItem,
                            onMove = { fromIndex, toIndex ->
                                ReorderItem.move(fromIndex, toIndex)

                            }
                        )

                        // Add a spacer to push the button to the bottom
                        Spacer(modifier = Modifier.weight(4f))

                    }




                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button (
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                            onClick = { checkAscendingOrder()
                                        updateScore()
                                      },
                        ){
                            Text(text="Click to Check" , color = Color.White)
                        }

                        // Display the score
                        Text(text = "Score :$score", fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(16.dp))


                    }


                }
            }
        }
    }


    // Generate a list of 5 items with random numbers
    val ReorderItem = (1..5).map { "${generateRandomNumber()}" }.toMutableStateList()

    private fun generateRandomNumber(): Int {
        return (1..100).random() // Adjust the range as needed
    }

    private fun checkAscendingOrder() {
        val isAscending = ReorderItem == ReorderItem.sorted()
        val isAscendingIndividual = ReorderItem.zip(ReorderItem.sorted()).all { it.first == it.second }

        Log.d("Debug", "ReorderItem: $ReorderItem, Sorted: ${ReorderItem.sorted()}, isAscendingIndividual: $isAscendingIndividual")

        if (!isAscendingIndividual) {
            showToast("Arrangement is wrong!")
        } else {
            showToast("Great Job, it's Correct you have won 5 points!")
        }

    }
    private fun updateScore() {
        val isAscendingIndividual = ReorderItem.zip(ReorderItem.sorted()).all { it.first == it.second }
        if (isAscendingIndividual) {
            score += 5
        } else {
            score += 0
        }
    }



    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
