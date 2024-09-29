package com.example.tiketkereta

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    // Declare UI elements
    lateinit var edtName: EditText
    lateinit var btnTime: Button
    lateinit var btnDate: Button
    lateinit var spinnerDestination: Spinner
    lateinit var btnOrder: Button
    var selectedTime: String = ""
    var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        edtName = findViewById(R.id.edt_name)
        btnTime = findViewById(R.id.btn_time)
        btnDate = findViewById(R.id.btn_date)
        spinnerDestination = findViewById(R.id.spinner_destination)
        btnOrder = findViewById(R.id.btn_order)

        // Array of destinations
        val destinations = arrayOf("Tujuan", "Surabaya", "Jakarta", "Bandung", "Yogyakarta", "Semarang")

        // Create custom ArrayAdapter for the Spinner
        val adapter = object : ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, destinations
        ) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val inflater: LayoutInflater = layoutInflater
                val view: View = inflater.inflate(R.layout.custom_spinner_item, parent, false)

                val textView: TextView = view.findViewById(R.id.text)
                val iconView: ImageView = view.findViewById(R.id.icon)
                textView.text = destinations[position]

                // Set icon for spinner items based on the position
                if (position == 0) {
                    iconView.visibility = View.GONE
                } else {
                    iconView.visibility = View.VISIBLE
                }
                return view
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                textView.text = destinations[position]
                return view
            }
        }

        // Set the custom ArrayAdapter to the spinner
        spinnerDestination.adapter = adapter

        // TimePicker for departure time (Digital style)
        btnTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePicker = TimePickerDialog(this, R.style.CustomTimePickerDialog, { _, selectedHour, selectedMinute ->
                selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                btnTime.text = selectedTime
            }, hour, minute, true)
            timePicker.show()
        }

        // DatePicker for departure date
        btnDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, R.style.CustomDatePickerDialog, { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = String.format("%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear)
                btnDate.text = selectedDate
            }, year, month, day)
            datePicker.show()
        }

        // Set the button click listener
        btnOrder.setOnClickListener {
            validateInputs() // Validate inputs before showing confirmation dialog
        }
    }

    // Function to validate inputs
    private fun validateInputs() {
        val name = edtName.text.toString()
        val destination = spinnerDestination.selectedItem.toString()

        // Check if all fields are filled
        if (name.isEmpty()) {
            Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
        } else if (destination == "Tujuan") {
            Toast.makeText(this, "Silakan pilih tujuan", Toast.LENGTH_SHORT).show()
        } else if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Tanggal tidak boleh kosong", Toast.LENGTH_SHORT).show()
        } else if (selectedTime.isEmpty()) {
            Toast.makeText(this, "Waktu tidak boleh kosong", Toast.LENGTH_SHORT).show()
        } else {
            // If all fields are valid, show the confirmation dialog
            showConfirmationDialog()
        }
    }

    private fun showConfirmationDialog() {
        // Inflate the custom layout
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirmation, null)

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        // Find views in the custom layout
        val btnBeli: Button = dialogView.findViewById(R.id.btnBeli)
        val btnBatal: Button = dialogView.findViewById(R.id.btnBatal)

        // Set up the button listeners
        btnBeli.setOnClickListener {
            val name = edtName.text.toString()
            val destination = spinnerDestination.selectedItem.toString()

            // Start SuccessActivity with user input data
            val intent = Intent(this@MainActivity, SuccessActivity::class.java)
            intent.putExtra("NAME", name)
            intent.putExtra("TIME", selectedTime)
            intent.putExtra("DATE", selectedDate)
            intent.putExtra("DESTINATION", destination)
            startActivity(intent)
            alertDialog.dismiss()
        }

        btnBatal.setOnClickListener {
            alertDialog.dismiss()
        }

        // Show the AlertDialog with rounded corners
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.show()
    }
}
