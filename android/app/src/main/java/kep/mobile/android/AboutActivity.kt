package kep.mobile.android

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kep.mobile.common.InjectorCommon
import kep.mobile.common.domain.model.About
import kep.mobile.common.presentation.AboutInfoPresenter
import kep.mobile.common.presentation.AboutInfoView
import kotlinx.android.synthetic.main.about.*
import kotlinx.android.synthetic.main.activity_talk_detail.*


class AboutActivity: AppCompatActivity(), AboutInfoView {

    private lateinit var presenter: AboutInfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(toolbar)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter = InjectorCommon.provideAboutPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onSuccessGetAboutInfo(about: About) {
        about_info.text = about.info

        website_button.setOnClickListener {
            startActivity(
                    Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(about.website)
                    }
            )
        }
        twitter_button.setOnClickListener {
            startActivity(
                    Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(about.twitter)
                    }
            )
        }
        location_button.setOnClickListener {
            val place = about.location
            // Create a Uri from an intent string. Use the result to create an Intent.
            val gmmIntentUri = Uri.parse("google.streetview:cbll=${place.latitude},${place.longitude}?q=${place.name}")

            // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            // Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps")

// Attempt to start an activity that can handle the Intent
            startActivity(mapIntent)
        }
    }

    override fun onFailureGetAboutInfo(e: Exception) {
        println(e.localizedMessage)
    }
}