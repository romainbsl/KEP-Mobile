package kep.mobile.android

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.CollapsingToolbarLayout
import kep.mobile.common.InjectorCommon
import kep.mobile.common.data.BASE_URL
import kep.mobile.common.data.URL_PROTOCOL
import kep.mobile.common.domain.model.Speaker
import kep.mobile.common.domain.model.Talk
import kep.mobile.common.presentation.TalkDetailPresenter
import kep.mobile.common.presentation.TalkDetailView
import kotlinx.android.synthetic.main.talk_detail.view.*

/**
 * A fragment representing a single Talk detail screen.
 * This fragment is either contained in a [TalkListActivity]
 * in two-pane mode (on tablets) or a [TalkDetailActivity]
 * on handsets.
 */
class TalkDetailFragment : Fragment(), TalkDetailView {

    private lateinit var rootView: View
    private lateinit var presenter: TalkDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = InjectorCommon.provideTalkDetailPresenter()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.talk_detail, container, false)

        presenter.attachView(this)

        arguments?.let {
            if (it.containsKey(ARG_TALK_ID)) {
                presenter.getCurrentTalk(it.getString(ARG_TALK_ID) as String)
            }
        }

        return rootView
    }

    override fun onDestroy() {
        super.onDestroy()
        println("DETTACH")
        presenter.detachView()
    }

    override fun onFailureGetTalkDetail(e: Exception) {
        println(e.message)
    }

    @SuppressLint("SetTextI18n")
    override fun onSuccessGetTalkDetail(talk: Talk) {
        talk.let {
            rootView.talk_title.text = it.title
            rootView.talk_detail.text = it.content
            rootView.talk_room.text = "${it.timeslot} - ${it.room}"

            if (it.speakers.isEmpty()) return@let

            val speakerPhoto: (String) -> String = { id: String -> "${URL_PROTOCOL.name}://$BASE_URL/speakers/$id.jpg" }

            rootView.img_1.showSpeakerImage(speakerPhoto(it.speakers.first().id))
            if (it.speakers.size == 2)
                rootView.img_2.showSpeakerImage(speakerPhoto(it.speakers.last().id))

            rootView.talk_speaker.text = it.speakers.joinToString(transform = Speaker::name)
        }
    }

    private fun ImageView.showSpeakerImage(imageUrl: String) {
        visibility = View.VISIBLE
        Glide.with(this@TalkDetailFragment)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(this)
    }

    companion object {
        /**
         * The fragment argument representing the talk ID that this fragment
         * represents.
         */
        const val ARG_TALK_ID = "id"
    }
}
