package com.example.footpro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class AdapterBrasileirao : RecyclerView.Adapter<AdapterBrasileirao.MyViewHolder>() {

    // Lista de objetos personalizados (dados do campeonato)
    private val PositionList: MutableList<CustomObject2> = mutableListOf()

    // Método chamado quando o ViewHolder é criado
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflar o layout do item da lista
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_adapter_brasileirao, parent, false)
        return MyViewHolder(itemView)
    }

    // Método chamado para vincular os dados aos elementos de visualização no ViewHolder
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Obter o objeto personalizado (dados do campeonato) para a posição atual
        val item = PositionList[position]

        // Definir os dados nos elementos de visualização adequados
        holder.textPosition.text = item.nomePosition
        holder.textTeamName.text = item.teamName
        holder.textPoints.text = item.points
        holder.textGames.text = item.games
        holder.textWins.text = item.wins
        holder.textDraws.text = item.draws
        holder.textLosses.text = item.losses
        holder.textGoalsAgainst.text = item.goalsAgainst
        holder.textGoalDifference.text = item.goalDifference

        // Carregar o logotipo da equipe usando a biblioteca Glide
        Glide.with(holder.itemView)
            .load(item.image)
            .into(holder.imageLogo)
    }

    // Método para obter o número de itens na lista
    override fun getItemCount(): Int = PositionList.size

    // ViewHolder que representa cada item da lista
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Elementos de visualização no item da lista
        val textPosition: TextView = itemView.findViewById(R.id.textPosition)
        val imageLogo : ImageButton = itemView.findViewById(R.id.imageLogo)
        val textTeamName : TextView = itemView.findViewById(R.id.textTeamName)
        val textPoints : TextView = itemView.findViewById(R.id.textPoints)
        val textGames : TextView = itemView.findViewById(R.id.textGames)
        val textWins : TextView = itemView.findViewById(R.id.textWins)
        val textDraws : TextView = itemView.findViewById(R.id.textDraws)
        val textLosses : TextView = itemView.findViewById(R.id.textLosses)
        val textGoalsAgainst : TextView = itemView.findViewById(R.id.textGoalsAgainst)
        val textGoalDifference : TextView = itemView.findViewById(R.id.textGoalDifference)
    }

    // Método para buscar os dados do campeonato a partir de uma API
    fun fetchData() {
        GlobalScope.launch(Dispatchers.Main) {
            // Executar a solicitação de rede em uma coroutine de IO
            val responseData = withContext(Dispatchers.IO) {
                executeNetworkRequest()
            }

            // Verificar se a resposta não está vazia
            if (!responseData.isNullOrEmpty()) {
                // Analisar os dados da resposta em objetos personalizados (dados do campeonato)
                val parsedData = CustomObject2.parseJson(responseData)

                // Adicionar os objetos personalizados à lista
                PositionList.addAll(parsedData)

                // Notificar o adapter sobre a mudança nos dados
                notifyDataSetChanged()
            }
        }
    }

    // Método para executar uma solicitação de rede para obter os dados do campeonato
    private fun executeNetworkRequest(): String? {
        val client = OkHttpClient()
        val url = "https://api.api-futebol.com.br/v1/campeonatos/10/tabela"
        val apiKey = "live_5f183acd9078f3bfa0344dce89ee09"

        // Construir a solicitação GET com o cabeçalho de autorização
        val request = Request.Builder()
            .header("Authorization", "Bearer $apiKey")
            .url(url)
            .build()

        // Executar a solicitação usando OkHttp e obter a resposta como uma string
        val response = client.newCall(request).execute()
        return response.body?.string()
    }
}
