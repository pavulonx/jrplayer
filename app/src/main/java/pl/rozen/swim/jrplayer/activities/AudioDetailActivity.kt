package pl.rozen.swim.jrplayer.activities

import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import pl.rozen.swim.jrplayer.R
import pl.rozen.swim.jrplayer.domain.Audio
import kotlinx.android.synthetic.main.activity_audio_detail.*
import org.jetbrains.anko.imageBitmap
import org.jetbrains.anko.imageResource


class AudioDetailActivity : AppCompatActivity() {

    lateinit var audio: Audio

    companion object {
        val ALBUM_GSON = "AudioDetailActivity:albumGson"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_detail)

        val albumGsonString = intent.getStringExtra(ALBUM_GSON)
        audio = Gson().fromJson<Audio>(albumGsonString, Audio::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = audio.title

        audio_artist_tv.text = audio.artist
        audio_album_tv.text = audio.album
        audio_title_tv.text = audio.title
        audio_total_time_tv.text = "3:30"
        audio_current_time_tv.text = "1:30"


//        try {
//            val mediaMetadataRetriever = MediaMetadataRetriever()
//            mediaMetadataRetriever.setDataSource(audio.data)
//            val embeddedPicture: ByteArray = mediaMetadataRetriever.embeddedPicture
//            val bitmap = BitmapFactory.decodeByteArray(embeddedPicture, 0, embeddedPicture.size)
//            audio_album_art.imageBitmap = bitmap
//        } catch (e: Exception){
//            audio_album_art.imageResource = Audio.UNDEFINED_AUDIO_IMAGE
//        }
//

        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(audio.data)
        val embeddedPicture: ByteArray? = mediaMetadataRetriever.embeddedPicture
        if (embeddedPicture != null) {
            val bitmap = BitmapFactory.decodeByteArray(embeddedPicture, 0, embeddedPicture.size)
            audio_album_art_iv.imageBitmap = bitmap
        } else
            audio_album_art_iv.imageResource = Audio.UNDEFINED_AUDIO_IMAGE

//        audio_album_art.layoutParams = ViewGroup.LayoutParams(500, 500)
    }


}
