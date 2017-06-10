package pl.rozen.swim.jrplayer.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import pl.rozen.swim.jrplayer.R
import pl.rozen.swim.jrplayer.domain.Audio
import org.jetbrains.anko.imageResource
import kotlinx.android.synthetic.main.album_list_row.view.*


class AudioAdapter(private val albumsList: MutableList<Audio>,
                   private val itemClick: (Audio) -> Unit) :
        RecyclerView.Adapter<AudioAdapter.AlbumViewHolder>() {

    override fun getItemCount() = albumsList.size


    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) = holder.bindAlbum(albumsList[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.album_list_row, parent, false)
        return AlbumViewHolder(itemView, itemClick)
    }

    class AlbumViewHolder(itemView: View, val itemClick: (Audio) -> Unit) : RecyclerView.ViewHolder(itemView) {

        fun bindAlbum(audio: Audio) = with(itemView) {
            itemView.setOnClickListener { itemClick(audio) }
            bindViews(audio, poster, title, artist, album)
        }

        fun bindViews(audio: Audio, poster: ImageView, title: TextView, artist: TextView, album: TextView) = with(audio) {
            poster.imageResource = coverID
            // TODO("Add proper images")

            artist.text = this.artist
            title.text = this.title
            album.text = this.album
        }

    }
}