package com.example.footpro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footpro.databinding.ActivityHomeBinding

class Home : AppCompatActivity(), Adapter.ItemClickListener {

    private lateinit var textResult: TextView
    private lateinit var binding: ActivityHomeBinding
    private var adapter = Adapter(this)

    // Implementação do método onItemClick da interface Adapter.ItemClickListener
    override fun onItemClick() {
        // Criação de um Intent para iniciar a atividade CampeonatoBr
        val intent = Intent(this, CampeonatoBr::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflar o layout da atividade usando o ViewBinding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Inicializar o RecyclerView
        initRecyclerView()
        // Inicializar o SearchView
        initSearchView()
        // Obter os dados da API
        fetchData()
        // Obter a referência do TextView textResult
        textResult = binding.textResult
    }

    private fun initRecyclerView() {
        // Configurar o LayoutManager do RecyclerView como LinearLayoutManager
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        // Criar uma instância do adaptador
        adapter = Adapter(this)
        // Definir o adaptador no RecyclerView
        binding.recyclerView.adapter = adapter
    }

    private fun initSearchView() {
        // Configurar o listener para o SearchView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filter(it) }
                return true
            }
        })
    }

    private fun filter(query: String) {
        // Filtrar a lista de itens do adaptador com base no texto de pesquisa
        val filteredList = adapter.originalItemList.filter { item ->
            item.nome.contains(query, ignoreCase = true)
        }
        // Aplicar o filtro ao adaptador
        adapter.filter(filteredList)

        // Atualizar o TextView textResult com o resultado da pesquisa
        val resultText = if (filteredList.isEmpty()) {
            "Nenhum resultado encontrado"
        } else {
            "Resultados encontrados: ${filteredList.size}"
        }
        textResult.text = resultText
    }

    private fun fetchData() {
        // Obter os dados da API usando o método fetchData() do adaptador
        adapter.fetchData()
    }
}


