package fr.epita.android.games

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WSinterface {

    @GET("game/list")
    fun Gamelist(): Call<List<ToDoObject>>

    @GET("game/details")
    fun getPicById(@Query("game_id") game_id : Int): Call<Informations>

}
