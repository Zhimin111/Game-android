package fr.epita.android.games

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val data: List<ToDoObject>
        var usedList = ArrayList<Int>()
        val callback: Callback<List<ToDoObject>> = object : Callback<List<ToDoObject>> {
            override fun onFailure(call: Call<List<ToDoObject>>, t: Throwable) {
                Log.d("xxx", "sdsds")
            }

            override fun onResponse(
                call: Call<List<ToDoObject>>,
                response: Response<List<ToDoObject>>
            ) {
                if (response.code() == 200) {
                    val res = response.body()
                    if (res != null) {
//                        Toast.makeText(
//                            this@MainActivity,
//                            "Got" + res.size,
//                            Toast.LENGTH_SHORT
//                        ).show()
                        var randomId: Int = Random.nextInt(res!!.size)
                        val ids =
                            arrayOf(R.id.imageView1, R.id.imageView2, R.id.imageView3, R.id.imageView4)
                        for (id in ids) {
                            val imageView = findViewById<ImageView>(id)

                            while (usedList.contains(res[randomId].id)) {
                                randomId = Random.nextInt(res.size - 1)
                            }
                            usedList.add(res[randomId].id)

                            Glide
                                .with(this@MainActivity)
                                .load(res[randomId].picture)
                                .into(imageView)


                        }
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

        service.Gamelist().enqueue(callback)

        imageView1.setOnClickListener {
            val explicitIntent: Intent = Intent(this@MainActivity, SecondActivity::class.java)
            explicitIntent.putExtra("game_id", usedList[0].toString())
            startActivity(explicitIntent)
        }
        imageView2.setOnClickListener {
            val explicitIntent: Intent = Intent(this@MainActivity, SecondActivity::class.java)
            explicitIntent.putExtra("game_id", usedList[1].toString())
            startActivity(explicitIntent)
        }
        imageView3.setOnClickListener {
            val explicitIntent: Intent = Intent(this@MainActivity, SecondActivity::class.java)
            explicitIntent.putExtra("game_id", usedList[2].toString())
            startActivity(explicitIntent)
        }
        imageView4.setOnClickListener {
            val explicitIntent: Intent = Intent(this@MainActivity, SecondActivity::class.java)
            explicitIntent.putExtra("game_id", usedList[3].toString())
            startActivity(explicitIntent)
        }


    }
}