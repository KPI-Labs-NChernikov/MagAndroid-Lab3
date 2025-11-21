package dev.nikita_chernikov.lab3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.nikita_chernikov.lab3.databinding.ActivityClassmateCreateBinding

class ClassmateCreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClassmateCreateBinding
    private lateinit var sqliteManager: SQLiteManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityClassmateCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sqliteManager = SQLiteManager.getInstance(this)

        binding.btnCreate.setOnClickListener {
            var hasErrors = false
            val firstName = binding.inputFirstName.text.toString().trim()
            val lastName = binding.inputLastName.text.toString().trim()
            val patronymic = binding.inputPatronymic.text.toString().trim()

            if (firstName.isEmpty()) {
                binding.inputFirstName.error = getString(R.string.field_required)
                hasErrors = true
            }
            else
            {
                binding.inputFirstName.error = null
            }
            if (lastName.isEmpty()) {
                binding.inputLastName.error = getString(R.string.field_required)
                hasErrors = true
            }
            else
            {
                binding.inputLastName.error = null
            }

            if (hasErrors)
            {
                return@setOnClickListener
            }

            sqliteManager.addClassmate(Classmate(firstName = firstName, lastName = lastName, patronymic = patronymic))
            Toast.makeText(this, "Одногрупника було додано успішно.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
