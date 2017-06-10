//package pl.rozen.swim.jrplayer.fragments
//
//import android.content.res.Configuration
//import android.os.Bundle
//import android.support.v4.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import pl.rozen.swim.jrplayer.R
//import pl.rozen.swim.jrplayer.activities.AudioDetailActivity
//import pl.rozen.swim.jrplayer.domain.Audio
//import kotlinx.android.synthetic.main.fragment_album_detail_second.*
//import org.jetbrains.anko.imageResource
//
//
///**
// * Created by rozen on 28.05.17.
// */
//class AlbumDetailSecondFragment : Fragment() {
//
//    private lateinit var audio: Audio
//
//    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater!!.inflate(R.layout.fragment_album_detail_second, container, false)
//    }
//
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        audio = (activity as AudioDetailActivity).audio
//        val albumImages = listOf(album_image_0, album_image_1, album_image_2, album_image_3, album_image_4, album_image_5)
//        val artistImages = listOf(artist_image_view_0, artist_image_view_1, artist_image_view_2, artist_image_view_3)
//        val artistLabels = listOf(artist_text_view_0, artist_text_view_1, artist_text_view_2, artist_text_view_3)
//
//
//        val displayMetrics = context.resources.displayMetrics
//        val dpHeight = displayMetrics.heightPixels / displayMetrics.density
//        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
//
//        for (albumImage in albumImages) {
//            val scale = (
//                    if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
//                        (dpWidth / gird_layout_images.columnCount)
//                    else (dpHeight / gird_layout_images.rowCount)
//                    ).toInt()
//            albumImage.layoutParams.height = scale
//            albumImage.layoutParams.width = scale
//        }
//
//        for ((i, albumImage) in albumImages.withIndex()) {
//            val photoId = if (i < audio.imagesIds.size) audio.imagesIds[i] else Audio.UNDEFINED_ALBUM_IMAGE
//            albumImage.imageResource = photoId
//        }
//
//        for ((i, artistImage) in artistImages.withIndex()) {
//            if (i < audio.artistsList.size) {
//                val photoId = audio.artistsList[i].photoId
//                artistImage.imageResource = photoId
//                artistLabels[i].text = audio.artistsList[i].name
//            } else {
//                artistImage.visibility = View.GONE
//                artistLabels[i].visibility = View.GONE
//            }
//        }
//
//    }
//}