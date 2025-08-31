package com.example.odyssey.presentation.ui.components.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.odyssey.presentation.theme.AdventureOrange
import com.example.odyssey.presentation.theme.NatureGreen

@Composable
fun BudgetSliderComponent(
    onValueChange: (Double) -> Unit
) {
    var budget by remember { mutableStateOf(1000f) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "$${budget.toInt()}",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = NatureGreen
        )

        Spacer(modifier = Modifier.height(16.dp))

        Slider(
            value = budget,
            onValueChange = {
                budget = it
                onValueChange(it.toDouble())
            },
            valueRange = 100f..5000f,
            modifier = Modifier.fillMaxWidth(),
            colors = SliderDefaults.colors(
                thumbColor = AdventureOrange,
                activeTrackColor = AdventureOrange,
                inactiveTrackColor = Color.Gray.copy(alpha = 0.3f)
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("$100", style = MaterialTheme.typography.bodySmall)
            Text("$5000", style = MaterialTheme.typography.bodySmall)
        }
    }
}