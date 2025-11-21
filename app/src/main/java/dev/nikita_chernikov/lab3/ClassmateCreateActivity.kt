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

        binding.btnCreate.setOnClickListener {
            val name = binding.inputFullName.text.toString().trim()

            if (name.isEmpty()) {
                binding.inputFullName.error = "Це поле обов'язкове."
            } else {
                binding.inputFullName.error = null
                Toast.makeText(this, "Одногрупника було додано успішно.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
