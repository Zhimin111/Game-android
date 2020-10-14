package fr.epita.android.games

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_second.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var id = intent.getStringExtra("game_id")

        val callback: Callback<Informations> = object : Callback<Informations> {
            override fun onFailure(call: Call<Informations>, t: Throwable) {
                Log.d("xxx", "sdsds")
            }

            override fun onResponse(
                call: Call<Informations>,
                response: Response<Informations>
            ) {
                if (response.code() == 200) {
                    val res = response.body()
                    if (res != null) {
                        Toast.makeText(
                            this@SecondActivity,
                            "Got" + res.name,
                            Toast.LENGTH_SHORT
                        ).show()

                        val imageView = findViewById<ImageView>(R.id.imageInfo)
                        Glide
                            .with(this@SecondActivity)
                            .load(res.picture)
                            .into(imageView)

                        Name.text = "Name: " + res.name
                        Type.text ="Type:"+ res.type
                        Nb.text = "Nb players: " + res.players
                        Year.text = "Year: " + res.year
                        description.text = "Description: " + res.description_en
//

                    }
                }
            }
        }

        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        val service: WSinterface = retrofit.create(WSinterface::class.java)
        service.getPicById(id.toInt()).enqueue(callback)
    }
}