package com.example.footpro

import org.json.JSONArray
import org.json.JSONException

data class CustomObject2(
    val nomePosition: String,  // Posição do time no campeonato
    val teamName: String,  // Nome do time
    val points: String,  // Pontos do time no campeonato
    val games: String,  // Quantidade de jogos disputados pelo time
    val wins: String,  // Quantidade de vitórias do time
    val draws: String,  // Quantidade de empates do time
    val losses: String,  // Quantidade de derrotas do time
    val goalsAgainst: String,  // Gols sofridos pelo time
    val goalDifference: String,  // Saldo de gols do time
    val image: String  // URL do escudo do time
) {
    companion object {
        // Método para analisar uma string JSON e retornar uma lista de objetos CustomObject2
        fun parseJson(jsonData: String?): List<CustomObject2> {
            val customObjectList = mutableListOf<CustomObject2>()

            try {
                val jsonArray = JSONArray(jsonData)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)

                    // Extrair os valores dos campos do objeto JSON
                    val nomePosition = jsonObject.getString("posicao")
                    val teamName = jsonObject.getString("nome_popular")
                    val points = jsonObject.getString("pontos")
                    val games = jsonObject.getString("jogos")
                    val wins = jsonObject.getString("vitorias")
                    val draws = jsonObject.getString("empates")
                    val losses = jsonObject.getString("derrotas")
                    val goalsAgainst = jsonObject.getString("gols_contra")
                    val goalDifference = jsonObject.getString("saldo_gols")
                    val image = jsonObject.getString("escudo")

                    // Criar um objeto CustomObject2 com os valores extraídos e adicioná-lo à lista
                    val customObject = CustomObject2(
                        nomePosition, teamName, points, games, wins, draws, losses, goalsAgainst, goalDifference, image
                    )
                    customObjectList.add(customObject)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            // Retornar a lista de objetos CustomObject2
            return customObjectList
        }
    }
}
