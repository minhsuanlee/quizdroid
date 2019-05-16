package edu.washington.minhsuan.quizdroid

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.lang.Exception

class UrlService : IntentService("UrlService") {

    private val TAG = "UrlService"

    private val mHandler = Handler()
    private var nagging = true
    private val FILENAME = "questions.json"

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Service created!")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.v(TAG, "Service being created")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        var minute: Int
        var url: String
        intent?.extras.apply {
            url = this!!.getString("Url")
            minute = this!!.getInt("Time")
        }

        while (nagging) {
            mHandler.post {
                Toast.makeText(this@UrlService, "Will be downloading from $url every $minute minute(s)",
                    Toast.LENGTH_SHORT).show()
                downloadJson(url, minute)
            }
            try {
                Thread.sleep((minute * 60  * 1000).toLong())
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }

    private fun downloadJson(url: String, min: Int) {
        val queue = Volley.newRequestQueue(this)

        val jsonObjectRequest = StringRequest (
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Toast.makeText(this,"Downloading from $url", Toast.LENGTH_LONG).show()
                Log.v(TAG, response)

                try {
                    val fos = this.openFileOutput(FILENAME, Context.MODE_PRIVATE)
                    if (response != null) {
                        fos.write(response.toByteArray())
                    }
                    fos.flush()
                    fos.close()
                    Toast.makeText(this,"Successfully saved to ${this.filesDir}/$FILENAME",
                        Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.stackTrace
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "ERROR: %s".format(error.toString()), Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(jsonObjectRequest)
    }

    override fun onDestroy() {
        Log.v(TAG, "Service being destoryed")
        Toast.makeText(this@UrlService, "Stop downloading...", Toast.LENGTH_LONG).show()
        nagging = false
        super.onDestroy()
    }
}
