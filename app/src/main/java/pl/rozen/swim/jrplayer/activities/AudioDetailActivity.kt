package pl.rozen.swim.jrplayer.activities

import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.SeekBar
import com.google.gson.Gson
import pl.rozen.swim.jrplayer.R
import pl.rozen.swim.jrplayer.domain.Audio
import kotlinx.android.synthetic.main.activity_audio_detail.*
import org.jetbrains.anko.imageBitmap
import org.jetbrains.anko.imageResource


class AudioDetailActivity : AppCompatActivity() {

    lateinit var audio: Audio
    lateinit var mp: MediaPlayer

    companion object {
        val ALBUM_GSON = "AudioDetailActivity:albumGson"
        val BUTTON_SEEK_MILLISECONDS = 5_000
    }

    private val handler: Handler = Handler()

    private val viewUpdater: Runnable = Runnable {
        val duration = mp.duration
        val currentDuration = mp.currentPosition
        val sb = audio_seek_bar
        sb.progress = currentDuration * sb.max / duration
        audio_current_time_tv.text = formatMillisToSec(currentDuration)
        Log.d("THREAD", duration.toString())
        performViewUpdates()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_detail)

        val albumGsonString = intent.getStringExtra(ALBUM_GSON)
        audio = Gson().fromJson<Audio>(albumGsonString, Audio::class.java)

//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        supportActionBar?.title = audio.title

        mp = MediaPlayer()
        mp.reset()
        mp.setDataSource(audio.data)
        mp.prepare()

        audio_artist_tv.text = audio.artist
        audio_album_tv.text = audio.album
        audio_title_tv.text = audio.title


        audio_total_time_tv.text = formatMillisToSec(mp.duration)
        audio_current_time_tv.text = formatMillisToSec(mp.currentPosition)

        audio_seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                handler.removeCallbacks(viewUpdater)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                handler.removeCallbacks(viewUpdater)
                if (seekBar != null) {
                    seekAudio(seekBar.progress * mp.duration / seekBar.max)
                }
                performViewUpdates()
            }
        })

        audio_detail_play_pause_ib.setOnClickListener { playPauseMusic() }

        audio_detail_forward_ib.setOnClickListener { seekAudioFromCurrentPosition(BUTTON_SEEK_MILLISECONDS) }
        audio_detail_backward_ib.setOnClickListener { seekAudioFromCurrentPosition(-BUTTON_SEEK_MILLISECONDS) }

        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(audio.data)
        val embeddedPicture: ByteArray? = mediaMetadataRetriever.embeddedPicture
        if (embeddedPicture != null) {
            val bitmap = BitmapFactory.decodeByteArray(embeddedPicture, 0, embeddedPicture.size)
            audio_album_art_iv.imageBitmap = bitmap
        } else
            audio_album_art_iv.imageResource = Audio.UNDEFINED_AUDIO_IMAGE
    }

    fun playPauseMusic() {
        if (mp.isPlaying) {
            mp.pause()
            audio_detail_play_pause_ib.imageResource = android.R.drawable.ic_media_play
        } else {
            mp.start()
            audio_detail_play_pause_ib.imageResource = android.R.drawable.ic_media_pause
            performViewUpdates()
        }
    }

    fun performViewUpdates() {
        handler.postDelayed(viewUpdater, 100)
    }

    fun seekAudioFromCurrentPosition(milliseconds: Int) {

        if (0 > mp.currentPosition + milliseconds) {
            mp.seekTo(0)
        } else if (mp.currentPosition + milliseconds > mp.duration) {
            mp.pause()
            mp.seekTo(mp.duration)
        } else {

            mp.seekTo(mp.currentPosition + milliseconds)

        }
        Log.d("CURR_POS", mp.currentPosition.toString())
    }

    fun seekAudio(milliseconds: Int) {
        if (mp.duration > milliseconds && milliseconds > 0)
            mp.seekTo(milliseconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        mp.release()
        handler.removeCallbacks(viewUpdater)
    }

    fun formatMillisToSec(milliseconds: Int): String {

        var seconds = milliseconds / 1000
        var minutes = seconds / 60
        var hours = minutes / 60
        val days = hours / 24
        seconds -= minutes * 60
        minutes -= hours * 60
        hours -= days * 24

        if (hours == 0)
            return "$minutes:%02d".format(seconds)
        else
            return "$hours:$minutes:%02d".format(seconds)
    }
}
