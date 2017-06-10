//package pl.rozen.swim.jrplayer.fragments
//
//import android.os.Bundle
//import android.support.v4.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import kotlinx.android.synthetic.main.fragment_album_detail_main.*
//import org.jetbrains.anko.imageResource
//
//
//import pl.rozen.swim.jrplayer.R
//import pl.rozen.swim.jrplayer.activities.AudioDetailActivity
//import pl.rozen.swim.jrplayer.domain.Audio
//
//
//class AlbumDetailMainFragment : Fragment() {
//
//    private lateinit var audio: Audio
//
//    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        // Inflate the layout for this fragment
//        return inflater!!.inflate(R.layout.fragment_album_detail_main, container, false)
//    }
//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        this.audio = (activity as AudioDetailActivity).audio
//        description_text_view.text = this.audio.description
//        album_author_text_view.text = this.audio.artist
//        album_title_text_view.text = this.audio.title
//        album_detail_rating_bar.rating = this.audio.rating
//        poster_album_detail_image_view.imageResource = this.audio.coverID
//        album_detail_rating_bar.setOnRatingBarChangeListener {
//            _, rating, fromUser ->
//            if (fromUser) audio.rating = rating
//
//        }
//    }
//}
