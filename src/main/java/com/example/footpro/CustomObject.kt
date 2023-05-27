package com.example.footpro

import org.json.JSONArray
import org.json.JSONException

data class CustomObject(
    val nome: String,
    val image : String
) {
    companion object {
        // Método estático para analisar o JSON e retornar uma lista de objetos CustomObject
        fun parseJson(jsonData: String?): List<CustomObject> {
            val customObjectList = mutableListOf<CustomObject>()

            try {
                // Criação de um JSONArray a partir da string de dados JSON
                val jsonArray = JSONArray(jsonData)
                // Iterar pelos elementos do JSONArray
                for (i in 0 until jsonArray.length()) {
                    // Obter o objeto JSON na posição atual
                    val jsonObject = jsonArray.getJSONObject(i)
                    // Extrair os valores dos campos "nome" e "logo" do objeto JSON
                    val nome = jsonObject.getString("nome")
                    val image = jsonObject.getString("logo")
                    // Criar uma instância de CustomObject com os valores extraídos
                    val customObject = CustomObject(nome, image)
                    // Adicionar o objeto customObject à lista customObjectList
                    customObjectList.add(customObject)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            // Retornar a lista de objetos CustomObject
            return customObjectList
        }
    }
}


