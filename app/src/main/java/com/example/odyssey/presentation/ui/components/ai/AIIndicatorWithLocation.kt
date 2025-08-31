package com.example.odyssey.presentation.ui.components.ai

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOff
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.odyssey.presentation.theme.AdventureOrange
import com.example.odyssey.presentation.theme.Gray500
import com.example.odyssey.presentation.theme.NatureGreen
import com.example.odyssey.presentation.theme.TravelBlue

@Composable
fun AIIndicatorWithLocation(
    isActive: Boolean,
    hasLocationData: Boolean,
    onLocationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Pulsing animation for active AI indicator
    val infiniteTransition = rememberInfiniteTransition(label = "ai_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    // Scaling animation for location status changes
    val scaleTransition = updateTransition(
        targetState = hasLocationData,
        label = "location_scale"
    )
    val scale by scaleTransition.animateFloat(
        transitionSpec = { spring(dampingRatio = Spring.DampingRatioMediumBouncy) },
        label = "scale_animation"
    ) { hasLocation ->
        if (hasLocation) 1f else 0.9f
    }

    // Color animation for different states
    val colorTransition = updateTransition(
        targetState = Triple(isActive, hasLocationData, hasLocationData),
        label = "color_transition"
    )
    val backgroundColor by colorTransition.animateColor(
        transitionSpec = { tween(300) },
        label = "background_color"
    ) { (active, hasLocation, _) ->
        when {
            !hasLocation -> Gray500.copy(alpha = 0.7f)
            active -> NatureGreen.copy(alpha = if (isActive) pulseAlpha else 1f)
            else -> AdventureOrange.copy(alpha = 0.8f)
        }
    }

    // Icon selection based on state
    val icon: ImageVector = when {
        !hasLocationData -> Icons.Default.LocationOff
        isActive -> Icons.Default.Psychology // AI brain when active
        else -> Icons.Default.LocationOn
    }

    Box(
        modifier = modifier
            .size(56.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onLocationClick() },
        contentAlignment = Alignment.Center
    ) {
        // Outer ring for enhanced visual effect
        if (isActive && hasLocationData) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
            )
        }

        // Main icon
        Icon(
            imageVector = icon,
            contentDescription = when {
                !hasLocationData -> "Location disabled"
                isActive -> "AI active with location"
                else -> "Location available"
            },
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )

        // Status dot indicator
        if (hasLocationData) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(if (isActive) Color.White else TravelBlue)
                    .align(Alignment.TopEnd)
                    .offset(x = (-4).dp, y = 4.dp)
            )
        }
    }
}