package kep.mobile

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jetbrains.handson.mpp.mobile.R
import kep.mobile.common.InjectorCommon
import kep.mobile.common.TalkPresenter
import kep.mobile.common.TalkView
import kep.mobile.common.model.data.Talk

class MainActivity : AppCompatActivity(), TalkView {

    private lateinit var presenter: TalkPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = InjectorCommon.provideTalkPresenter()
        presenter.attachView(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.getTalkList()
    }

    override fun onSuccessGetTalkList(talkList: List<Talk>) {
        findViewById<TextView>(R.id.main_text).text = talkList.map { it.title }.joinToString("\n - ")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
