package pl.rozen.swim.jrplayer.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_audio_detail.*

import pl.rozen.swim.jrplayer.R
import pl.rozen.swim.jrplayer.domain.Audio
//import pl.rozen.swim.jrplayer.fragments.AlbumDetailSecondFragment
//import pl.rozen.swim.jrplayer.fragments.AlbumDetailMainFragment
//import pl.rozen.swim.jrplayer.models.DatabaseHelper

class AudioDetailActivity : AppCompatActivity() {

//    lateinit var audio: Audio

    companion object {
        val NUM_PAGES = 2
        val ALBUM_INDEX = "AudioDetailActivity:albumIndex"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_detail)

        val albumIndex = intent.getIntExtra(ALBUM_INDEX, -1)
//        audio = DatabaseHelper.getAlbumAt(albumIndex)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
//        supportActionBar?.title = audio.title

    }


}
