package kep.mobile.android

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kep.mobile.common.InjectorCommon
import kep.mobile.common.domain.model.Talk
import kep.mobile.common.presentation.TalkListPresenter
import kep.mobile.common.presentation.TalkListView
import kotlinx.android.synthetic.main.activity_talk_list.*
import kotlinx.android.synthetic.main.talk_list.*
import kotlinx.android.synthetic.main.talk_list_content.view.*

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
//        toolbar.title = title

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(false);
        getSupportActionBar()?.setIcon(getResources().getDrawable(R.mipmap.ic_title))
        getSupportActionBar()?.setTitle(title);

        about.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
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
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.talk_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
//            holder.idView.text = item.id
            holder.contentView.text = item.title

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.id_text
            val contentView: TextView = view.content
        }
    }

    override fun onSuccessGetTalkList(talkList: List<Talk>) {
        talk_list.adapter = SimpleItemRecyclerViewAdapter(this, talkList)
    }

    override fun onFailureGetTalkList(e: Exception) {
        println("Error")
    }

    override fun checkInternetConnection(): Boolean {
        println("Check Internet Connection")
        return true
    }

}
