package com.example.odyssey.presentation.ui.components.tripPlanning

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.odyssey.data.models.PlanningStep
import com.example.odyssey.data.models.StepType
import com.example.odyssey.domain.entities.UserTripInput
import com.example.odyssey.presentation.theme.AdventureOrange
import com.example.odyssey.presentation.ui.components.common.BudgetSliderComponent
import com.example.odyssey.presentation.ui.components.common.DatePickerComponent
import com.example.odyssey.presentation.ui.components.common.MultipleChoiceComponent
import com.example.odyssey.presentation.ui.components.common.NumberPickerComponent
import com.example.odyssey.presentation.ui.components.common.SingleChoiceComponent
import com.example.odyssey.presentation.ui.components.common.TextInputComponent

@Composable
fun PlanningStepContent(
    step: PlanningStep,
    stepNumber: Int,
    totalSteps: Int,
    onAnswerSubmitted: (String) -> Unit,
    userInput: UserTripInput,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "step_scale"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Question Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = step.question,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                when (step.type) {
                    StepType.TEXT_INPUT -> {
                        TextInputComponent(
                            placeholder = "Enter your destination...",
                            onValueChange = onAnswerSubmitted
                        )
                    }
                    StepType.SINGLE_CHOICE -> {
                        SingleChoiceComponent(
                            options = step.options ?: emptyList(),
                            onSelectionChange = onAnswerSubmitted
                        )
                    }
                    StepType.MULTIPLE_CHOICE -> {
                        MultipleChoiceComponent(
                            options = step.options ?: emptyList(),
                            onSelectionChange = onAnswerSubmitted
                        )
                    }
                    StepType.NUMBER_PICKER -> {
                        NumberPickerComponent(
                            initialValue = 1,
                            onValueChange = { onAnswerSubmitted(it.toString()) }
                        )
                    }
                    StepType.BUDGET_SLIDER -> {
                        BudgetSliderComponent(
                            onValueChange = { onAnswerSubmitted(it.toString()) }
                        )
                    }
                    StepType.DATE_PICKER -> {
                        DatePickerComponent(
                            onDateSelected = onAnswerSubmitted
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Motivational Message
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = AdventureOrange.copy(alpha = 0.1f)
            )
        ) {
            Text(
                text = getMotivationalMessage(stepNumber),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic
            )
        }
    }
}
