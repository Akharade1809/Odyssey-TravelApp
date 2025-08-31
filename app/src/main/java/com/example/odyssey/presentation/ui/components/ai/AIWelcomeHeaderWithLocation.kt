package com.example.odyssey.presentation.ui.components.ai

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.LocationOff
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.odyssey.presentation.theme.AdventureOrange
import com.example.odyssey.presentation.theme.NatureGreen
import com.example.odyssey.presentation.theme.TravelBlue


@Composable
fun AIWelcomeHeaderWithLocation(
    userName: String,
    isLoading: Boolean,
    locationStatus: String,
    hasLocationData: Boolean,
    onLocationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            TravelBlue.copy(alpha = 0.9f),
                            AdventureOrange.copy(alpha = 0.8f),
                            NatureGreen.copy(alpha = 0.7f)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Hello, $userName! ✨",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "AI-powered travel companion at your service",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }

                    // AI Indicator with Location Status
                    AIIndicatorWithLocation(
                        isActive = !isLoading,
                        hasLocationData = hasLocationData,
                        onLocationClick = onLocationClick
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Location Status Bar
                LocationStatusBar(
                    status = locationStatus,
                    isLoading = isLoading,
                    hasLocationData = hasLocationData,
                    onLocationClick = onLocationClick
                )
            }
        }
    }
}

@Composable
fun LocationStatusBar(
    status: String,
    isLoading: Boolean,
    hasLocationData: Boolean,
    onLocationClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLocationClick() }
    ) {
        Icon(
            imageVector = when {
                isLoading -> Icons.Default.AutoAwesome
                hasLocationData -> Icons.Default.LocationOn
                else -> Icons.Default.LocationOff
            },
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = status,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.9f),
            modifier = Modifier.weight(1f)
        )

        if (!hasLocationData && !isLoading) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Enable location",
                tint = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
