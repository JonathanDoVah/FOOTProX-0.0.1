package com.example.footpro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Oculta a barra de ação (action bar)
        supportActionBar?.hide()

        // Cria um Handler para adicionar um atraso antes de executar uma ação
        Handler(Looper.getMainLooper()).postDelayed({
            // Cria um Intent para iniciar a atividade Home
            val intent = Intent(this@MainActivity, Home::class.java)
            startActivity(intent)
        }, 3000)  // Aguarda 3 segundos (3000 milissegundos) antes de iniciar a atividade Home
    }
}
