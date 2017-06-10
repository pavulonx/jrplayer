package pl.rozen.swim.jrplayer.domain

import android.support.annotation.DrawableRes
import pl.rozen.swim.jrplayer.R

class Audio(val data: String = "",
            val title: String = "",
            val album: String = "",
            val artist: String = "",
            @DrawableRes val coverID: Int = UNDEFINED_AUDIO_IMAGE) {

    override fun toString(): String {
        return "Audio(title='$title', artist='$artist)"
    }

    companion object {
        @DrawableRes val UNDEFINED_AUDIO_IMAGE: Int = R.drawable.undefined_audio_image
    }
}
