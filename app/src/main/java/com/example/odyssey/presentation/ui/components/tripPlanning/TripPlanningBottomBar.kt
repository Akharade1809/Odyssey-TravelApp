package com.example.odyssey.presentation.ui.components.tripPlanning

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.odyssey.presentation.theme.*

// 2. Trip Planning Bottom Bar
@Composable
fun TripPlanningBottomBar(
    canGoBack: Boolean,
    canProceed: Boolean,
    isLastStep: Boolean,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back Button
            OutlinedButton(
                onClick = onBackClick,
                enabled = canGoBack,
                modifier = Modifier
                    .height(50.dp)
                    .width(120.dp),
                shape = RoundedCornerShape(25.dp),
                border = BorderStroke(2.dp, if (canGoBack) TravelBlue else Color.Gray)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Back", fontWeight = FontWeight.Medium)
            }

            // Next/Generate Button
            Button(
                onClick = onNextClick,
                enabled = canProceed,
                modifier = Modifier
                    .height(50.dp)
                    .defaultMinSize(minWidth = 150.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isLastStep) NatureGreen else TravelBlue,
                    disabledContainerColor = Color.Gray.copy(alpha = 0.3f)
                )
            ) {
                if (isLastStep) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Generate",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Generate Plan", fontWeight = FontWeight.Bold)
                } else {
                    Text("Continue", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Next",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

// 4. Itinerary Generated Success


// 5. Trip Overview Card


// 6. Trip Stat Card


// 7. Day Plan Card


// 8. Activity Item Card


// 9. Planning Step Content (for the main steps)

// Helper function for motivational messages
fun getMotivationalMessage(stepNumber: Int): String {
    val messages = listOf(
        "🌍 Every great journey begins with a single step!",
        "📅 The best time to travel is now!",
        "👥 Great adventures are better when shared!",
        "💰 Smart planning leads to amazing experiences!",
        "🎯 Your interests shape your perfect adventure!",
        "✈️ Your travel style makes each trip unique!",
        "🏠 The right place to stay enhances every moment!",
        "✨ You're just moments away from your dream itinerary!"
    )
    return messages.getOrElse(stepNumber - 1) { messages.last() }
}
