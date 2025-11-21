package dev.nikita_chernikov.lab3

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.nikita_chernikov.lab3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sqliteManager: SQLiteManager

    companion object {
        private const val EXAMPLE_LAST_NAME : String = "Петренко"
        private const val EXAMPLE_FIRST_NAME : String = "Петро"
        private const val EXAMPLE_PATRONYMIC : String = "Петрович"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sqliteManager = SQLiteManager.getInstance(this)
        sqliteManager.seed()

        binding.btnShowAll.setOnClickListener {
            val startListActivityIntent = Intent(this, ClassmateListActivity::class.java)
            startActivity(startListActivityIntent)
        }
        binding.btnCreate.setOnClickListener {
            val createClassmateActivityIntent = Intent(this, ClassmateCreateActivity::class.java)
            startActivity(createClassmateActivityIntent)
        }
        binding.btnChangeLastFullNameToPetrenkopp.setOnClickListener {
            val lastClassmate = sqliteManager.getLastClassmate()
            if (lastClassmate != null)
            {
                val previousFullName = lastClassmate.fullName
                lastClassmate.firstName = EXAMPLE_FIRST_NAME
                lastClassmate.lastName = EXAMPLE_LAST_NAME
                lastClassmate.patronymic = EXAMPLE_PATRONYMIC
                sqliteManager.updateClassmate(lastClassmate)
                Toast.makeText(this, "Ім'я одногрупника $previousFullName (id: ${lastClassmate.id}) було замінено успішно.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
