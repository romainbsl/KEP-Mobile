package kep.mobile.android

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kep.mobile.common.InjectorCommon
import kep.mobile.common.domain.model.Talk
import kep.mobile.common.domain.model.Type
import kep.mobile.common.presentation.TalkListPresenter
import kep.mobile.common.presentation.TalkListView
import kotlinx.android.synthetic.main.activity_talk_list.*
import kotlinx.android.synthetic.main.talk_list.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [TalkDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class TalkListActivity : AppCompatActivity(), TalkListView {

    private lateinit var presenter: TalkListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talk_list)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false);
        supportActionBar?.setIcon(resources.getDrawable(R.mipmap.ic_title))
        supportActionBar?.title = title;

        about.setOnClickListener { v ->
            v.context.startActivity(Intent(v.context, AboutActivity::class.java))
        }

        presenter = InjectorCommon.provideTalkListPresenter()

    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    class SimpleItemRecyclerViewAdapter(
            private val parentActivity: TalkListActivity,
            private val values: List<Talk>
    ) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.TalkViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val selectedTalk = v.tag as Talk
                val intent = Intent(v.context, TalkDetailActivity::class.java).apply {
                    putExtra(TalkDetailFragment.ARG_TALK_ID, selectedTalk.id)
                }
                v.context.startActivity(intent)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalkViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.talk_list_content, parent, false)
            return TalkViewHolder(view)
        }

        override fun onBindViewHolder(holder: TalkViewHolder, position: Int) {
            val item = values[position]

            with(holder) {
                println(item.type)
                setTitle(
                    when(item.type) {
                        Type.CODELAB -> "Codelab: ${item.title}"
                        Type.WORKSHOP -> "Workshop: ${item.title}"
                        else -> item.title
                    }
                )

                setStartsAt(item.timeslot)
                setDetails(item.room)

                isFirstInTimeGroup = position == 0 ||
                        (values[position - 1]).timeslot != item.timeslot
            }

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class TalkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val contentView = view.findViewById<TextView>(R.id.content)
            private val timeslotView = view.findViewById<TextView>(R.id.timeslot)
            private val roomView = view.findViewById<TextView>(R.id.room)
            private val talkLayoutView = view.findViewById<LinearLayout>(R.id.talk_layout)

            var isFirstInTimeGroup: Boolean = false
                set(value) {
                    field = value
                    timeslotView.visibility = if (value) View.VISIBLE else View.INVISIBLE
                }

            val titleOffset: Int
                get() = talkLayoutView.left

            fun setTitle(title: String) {
                contentView.text = title
            }

            fun setStartsAt(timeslot: String) {
                timeslotView.text = timeslot
            }

            fun setDetails(room: String) {
                roomView.text = room
            }
        }

    }

    override fun onSuccessGetTalkList(talkList: List<Talk>) {
        talk_list.adapter = SimpleItemRecyclerViewAdapter(this,
                talkList.sortedWith(compareBy(Talk::timeslot, Talk::title)))
    }

    override fun onFailureGetTalkList(e: Exception) {
        println("Error")
    }

    override fun checkInternetConnection(): Boolean {
        println("Check Internet Connection")
        return true
    }
}