package com.example.odyssey.presentation.ui.components.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.odyssey.presentation.theme.TravelBlue
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerComponent(
    onDateSelected: (String) -> Unit
) {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    Button(
        onClick = {
            // TODO: Replace with an actual date picker dialog
            val date = LocalDate.now().plusDays(7) // Default example
            selectedDate = date
            onDateSelected(date.format(DateTimeFormatter.ISO_LOCAL_DATE))
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = TravelBlue.copy(alpha = 0.1f),
            contentColor = TravelBlue
        )
    ) {
        Icon(
            imageVector = Icons.Default.CalendarMonth,
            contentDescription = "Select Date",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = selectedDate?.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
                ?: "Select Date",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
