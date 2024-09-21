package com.example.agetominutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)

        btnDatePicker.setOnClickListener{
            clickDatePicker()
        }

    }

    private fun clickDatePicker(){

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener{
                _, selectedYear, selectedMonth, selectedDayOfMonth ->

            Toast.makeText(this,
                "Selected year is $selectedYear, month is ${selectedMonth+1} " +
                        "& day is $selectedDayOfMonth" , Toast.LENGTH_LONG).show()

            // Used to select the date and store the date in selectedDate and display it in the app
            val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
            tvSelectedDate?.text = selectedDate

            val sdf= SimpleDateFormat( "dd/mm/yyyy", Locale.ENGLISH)
            val theDate = sdf.parse(selectedDate)
            theDate?.let {
                // we / time by 1000 to convert it to sec, then div by 60 to make it in min which = 60000
                val selectedDateInMinutes = theDate.time / 60000

                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                currentDate?.let {
                    val currentDateInMinutes = currentDate.time / 60000

                    val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                    tvAgeInMinutes?.text = differenceInMinutes.toString()
                }

            }

        },
            year,
            month,
            day)

        dpd.datePicker.maxDate = System.currentTimeMillis() - 84600000 // total millisecond in a day
        dpd.show()
    }


}