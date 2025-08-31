package com.example.odyssey.presentation.ui.components.tripPlanning

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.odyssey.domain.entities.TripPlan
import com.example.odyssey.presentation.theme.TravelBlue

@Composable
fun TripOverviewCard(tripPlan: TripPlan) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = tripPlan.itinerary.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TravelBlue
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = tripPlan.itinerary.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TripStatCard(
                    icon = Icons.Default.CalendarMonth,
                    label = "Duration",
                    value = "${tripPlan.itinerary.days.size} Days"
                )
                TripStatCard(
                    icon = Icons.Default.AttachMoney,
                    label = "Budget",
                    value = "$${tripPlan.itinerary.totalEstimatedCost.toInt()}"
                )
                TripStatCard(
                    icon = Icons.Default.LocationOn,
                    label = "Destination",
                    value = tripPlan.destination
                )
            }
        }
    }
}