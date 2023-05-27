package com.example.footpro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import com.bumptech.glide.Glide
import okhttp3.Request

class Adapter(private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    // Lista de itens exibidos no adaptador
    private var itemList: MutableList<CustomObject> = mutableListOf()

    // Lista original de itens antes da filtragem
    val originalItemList: MutableList<CustomObject> = mutableListOf()

    // Método chamado quando o adaptador precisa criar um novo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflar o layout de item do adaptador
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    // Interface para lidar com o clique no item do adaptador
    interface ItemClickListener {
        fun onItemClick()
    }

    // Método chamado para vincular dados ao ViewHolder
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemList[position]
        // Definir o nome do item no TextView correspondente
        holder.textName.text = item.nome
        // Carregar a imagem do item no ImageButton usando a biblioteca Glide
        Glide.with(holder.itemView)
            .load(item.image)
            .into(holder.imageButton)
        // Configurar o clique no ImageButton para chamar o método onItemClick() do listener
        holder.imageButton.setOnClickListener {
            itemClickListener.onItemClick()
        }
    }

    // Retorna o número total de itens na lista
    override fun getItemCount(): Int {
        return itemList.size
    }

    // Filtra a lista de itens com base em um novo conjunto de itens filtrados
    fun filter(filteredList: List<CustomObject>) {
        itemList = filteredList.toMutableList()
        notifyDataSetChanged()
    }

    // ViewHolder que mantém as referências dos elementos da interface do usuário para cada item
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.textName)
        val imageButton : ImageButton = itemView.findViewById(R.id.imageButton)
    }

    // Recupera os dados da API e atualiza a lista de itens
    fun fetchData() {
        GlobalScope.launch(Dispatchers.Main) {
            val responseData = withContext(Dispatchers.IO) {
                executeNetworkRequest()
            }

            if (!responseData.isNullOrEmpty()) {
                val parsedData = CustomObject.parseJson(responseData)
                // Adiciona os itens recuperados à lista do adaptador
                itemList.addAll(parsedData)
                originalItemList.addAll(parsedData)
                // Notifica o adaptador que os dados foram alterados
                notifyDataSetChanged()
            }
        }
    }

    // Executa a requisição de rede para obter os dados da API
    private fun executeNetworkRequest(): String? {
        val client = OkHttpClient()
        val url = "https://api.api-futebol.com.br/v1/campeonatos"
        val apiKey = "live_5f183acd9078f3bfa0344dce89ee09"

        val request = Request.Builder()
            .header("Authorization", "Bearer $apiKey")
            .url(url)
            .build()

        val response = client.newCall(request).execute()
        return response.body?.string()
    }
}
