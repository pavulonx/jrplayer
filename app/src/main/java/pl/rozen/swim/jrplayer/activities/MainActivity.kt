package pl.rozen.swim.jrplayer.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity
import pl.rozen.swim.jrplayer.R
import pl.rozen.swim.jrplayer.adapters.AudioAdapter
import pl.rozen.swim.jrplayer.domain.Audio
import java.util.ArrayList


class MainActivity : AppCompatActivity() {

    lateinit var adapter: AudioAdapter
    lateinit private var audioList: MutableList<Audio>
    val REQUEST_ID_MULTIPLE_PERMISSIONS = 1

    fun loadAudioList(): MutableList<Audio> {
        val audioList = ArrayList<Audio>()

        if (checkAndRequestPermissions()) {
            val contentResolver = contentResolver

            val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
            val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
            val cursor = contentResolver.query(uri, null, selection, null, sortOrder)

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    audioList.add(Audio(data, title, album, artist))
                }
            }
            cursor?.close()
        }
        return audioList
    }

    private fun checkAndRequestPermissions(): Boolean {
        if (SDK_INT >= Build.VERSION_CODES.M) {
            val permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            val listPermissionsNeeded = ArrayList<String>()

            if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
                return false
            } else {
                return true
            }
        }
        return false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        audioList = loadAudioList()

        adapter = AudioAdapter(audioList, { it: Audio ->
            startActivity<AudioDetailActivity>(AudioDetailActivity.ALBUM_GSON.to(Gson().toJson(it)))
        })
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()

        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, mLayoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        recyclerView.adapter = adapter

        adapter.notifyDataSetChanged()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_about -> {
                startActivity<AboutActivity>()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
