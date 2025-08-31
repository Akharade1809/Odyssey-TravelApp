package com.example.odyssey.presentation.ui.components.tripPlanning

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.odyssey.presentation.theme.AdventureOrange
import com.example.odyssey.presentation.theme.NatureGreen
import com.example.odyssey.presentation.theme.TravelBlue

@Composable
fun TripPlanningTopBar(
    currentStep: Int,
    totalSteps: Int,
    onBackClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(TravelBlue, AdventureOrange, NatureGreen)
                    )
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .clickable { onBackClick() }
                    .size(28.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Plan Your Trip",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
                Text(
                    text = "Step $currentStep of $totalSteps",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 16.sp
                )
            }

            // AI Badge
            Box(
                modifier = Modifier
                    .background(
                        Color.White.copy(alpha = 0.2f),
                        CircleShape
                    )
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "AI Powered",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Progress Bar
        val progress = currentStep.toFloat() / totalSteps.toFloat()
        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = tween(500, easing = EaseOutCubic),
            label = "progress_animation"
        )

        LinearProgressIndicator(
            progress = animatedProgress,
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = AdventureOrange,
            trackColor = Color.White.copy(alpha = 0.3f)
        )
    }
}