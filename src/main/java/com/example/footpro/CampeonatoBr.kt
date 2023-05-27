package com.example.footpro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footpro.databinding.ActivityCampeonatoBrBinding

class CampeonatoBr : AppCompatActivity() {

    private lateinit var binding: ActivityCampeonatoBrBinding
    private val adapter = AdapterBrasileirao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCampeonatoBrBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        fetchData()
    }

    private fun initRecyclerView() {
        // Configurar o layout manager para o RecyclerView
        binding.recyclerBR.layoutManager = LinearLayoutManager(this)
        // Definir que o tamanho do RecyclerView é fixo
        binding.recyclerBR.setHasFixedSize(true)
        // Configurar o adaptador para o RecyclerView
        binding.recyclerBR.adapter = adapter
    }

    private fun fetchData() {
        // Chamar o método fetchData() do adaptador para buscar os dados
        adapter.fetchData()
    }
}
