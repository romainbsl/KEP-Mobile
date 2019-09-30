package kep.mobile

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jetbrains.handson.mpp.mobile.R
import kep.mobile.common.KotlinEverywhereParisApi
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val api = KotlinEverywhereParisApi()

        uiScope.launch {

            val speakers = async(Dispatchers.IO) {
                api.getSpeakers().map { it.name }.joinToString("\n")
            }
            println(speakers.await())
//            val talks = api.getTalks().map { it.title }.joinToString("\n")
//            println(talks)

            findViewById<TextView>(R.id.main_text).text = speakers.await()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
