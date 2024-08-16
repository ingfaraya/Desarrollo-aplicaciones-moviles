package com.example.semana1

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PrincipalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.principal)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recetas = arrayOf(
            arrayOf("Ensalada Cesar", "Una deliciosa ensalada con aderezo César, crutones y queso parmesano."),
            arrayOf("Sopa de Tomate", "Sopa cremosa de tomate con un toque de albahaca fresca y crema."),
            arrayOf("Pollo al Curry", "Pollo tierno cocinado en una salsa de curry rica y aromática."),
            arrayOf("Tacos de Pescado", "Tacos de pescado ligeros con pescado blanco, repollo y una salsa picante."),
            arrayOf("Pasta Carbonara", "Pasta cremosa con panceta, queso pecorino y pimienta negra."),
            arrayOf("Brownies de Chocolate", "Brownies ricos y chocolatosos con nueces crujientes.")
        )

        val textView: TextView = findViewById(R.id.text_view_list)

        val concatenatedRecetas = recetas.joinToString(separator = "\n\n") { receta ->
            "${receta[0]}\n${receta[1]}"
        }

        textView.text = concatenatedRecetas
    }
}