package kep.mobile

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jetbrains.handson.mpp.mobile.R
import kep.mobile.common.InjectorCommon
import kep.mobile.common.domain.model.Talk
import kep.mobile.common.presentation.TalkListPresenter
import kep.mobile.common.presentation.TalkView

class MainActivity : AppCompatActivity(), TalkView {

    private lateinit var presenter: TalkListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = InjectorCommon.provideTalkPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onSuccessGetTalkList(talkList: List<Talk>) {
        findViewById<TextView>(R.id.main_text).text = talkList.map { it.title }.joinToString("\n - ")
    }
    override fun onFailureGetTalkList(e: Exception) {
        Toast.makeText(applicationContext, e.localizedMessage, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
