package com.robin.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.*
import android.view.View
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener


class MainActivity : AppCompatActivity() {
    var currentImageUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme(){
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = VISIBLE
        // Instantiate the RequestQueue.
        currentImageUrl = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, currentImageUrl, null,
            { response ->
                val url = response.getString("url")
                val memeImage = findViewById<ImageView>(R.id.memeImage)
                Glide.with(this).load(url).listener(object : RequestListener<Drawable> {

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }


                }).into(memeImage)
            },
            { error ->
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
            })

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(JsonObjectRequest)
    }
    fun shareMeme(view: View) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        var ImageUrl = "https://i.redd.it/otzn82nirum91.jpg"
        var chooser = Intent.createChooser(intent,"Share this meme using...")
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, checkout this cool meme  $ImageUrl")
        startActivity(chooser)


    }
    fun nextMeme(view: View) {
        loadMeme()
    }
}