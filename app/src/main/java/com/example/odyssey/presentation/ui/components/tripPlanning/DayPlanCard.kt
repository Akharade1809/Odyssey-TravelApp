package com.example.odyssey.presentation.ui.components.tripPlanning

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.odyssey.domain.entities.DayPlan
import com.example.odyssey.presentation.theme.AdventureOrange
import com.example.odyssey.presentation.theme.NatureGreen
import com.example.odyssey.presentation.theme.TravelBlue

@Composable
fun DayPlanCard(day: DayPlan) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = TravelBlue.copy(alpha = 0.05f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(TravelBlue, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day.dayNumber.toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Day ${day.dayNumber}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = day.theme,
                        style = MaterialTheme.typography.bodyMedium,
                        color = AdventureOrange,
                        fontWeight = FontWeight.Medium
                    )
                }

                Text(
                    text = "$${day.estimateCost.toInt()}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = NatureGreen
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            day.activities.take(3).forEach { activity ->
                ActivityItemCard(activity = activity)
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (day.activities.size > 3) {
                Text(
                    text = "+ ${day.activities.size - 3} more activities",
                    style = MaterialTheme.typography.bodySmall,
                    color = TravelBlue,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    }
}