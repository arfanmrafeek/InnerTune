package com.zionhuang.music.ui.listeners

import androidx.fragment.app.Fragment
import com.zionhuang.music.R
import com.zionhuang.music.db.entities.DownloadedPlaylist
import com.zionhuang.music.db.entities.LikedPlaylist
import com.zionhuang.music.extensions.exceptionHandler
import com.zionhuang.music.models.MediaMetadata
import com.zionhuang.music.models.sortInfo.SongSortInfoPreference
import com.zionhuang.music.models.toMediaMetadata
import com.zionhuang.music.repos.SongRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LikedPlaylistMenuListener(override val fragment: Fragment) : BaseMenuListener<LikedPlaylist>(fragment) {
    override suspend fun getMediaMetadata(items: List<LikedPlaylist>): List<MediaMetadata> =
        SongRepository.getLikedSongs(SongSortInfoPreference).getList().map { it.toMediaMetadata() }

    fun play() {
        playAll(context.getString(R.string.liked_songs), emptyList())
    }

    fun playNext() {
        playNext(emptyList(), context.resources.getQuantityString(R.plurals.snackbar_playlist_play_next, 1, 1))
    }

    fun addToQueue() {
        addToQueue(emptyList(), context.resources.getQuantityString(R.plurals.snackbar_playlist_added_to_queue, 1, 1))
    }

    fun addToPlaylist() {
        addToPlaylist { playlist ->
            SongRepository.addToPlaylist(playlist, SongRepository.getLikedSongs(SongSortInfoPreference).getList())
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun download() {
        GlobalScope.launch(context.exceptionHandler) {
            SongRepository.downloadSongs(SongRepository.getLikedSongs(SongSortInfoPreference).getList().map { it.song })
        }
    }
}

class DownloadedPlaylistMenuListener(override val fragment: Fragment) : BaseMenuListener<DownloadedPlaylist>(fragment) {
    override suspend fun getMediaMetadata(items: List<DownloadedPlaylist>): List<MediaMetadata> =
        SongRepository.getDownloadedSongs(SongSortInfoPreference).getList().map { it.toMediaMetadata() }

    fun play() {
        playAll(context.getString(R.string.downloaded_songs), emptyList())
    }

    fun playNext() {
        playNext(emptyList(), context.resources.getQuantityString(R.plurals.snackbar_playlist_play_next, 1, 1))
    }

    fun addToQueue() {
        addToQueue(emptyList(), context.resources.getQuantityString(R.plurals.snackbar_playlist_added_to_queue, 1, 1))
    }

    fun addToPlaylist() {
        addToPlaylist { playlist ->
            SongRepository.addToPlaylist(playlist, SongRepository.getLikedSongs(SongSortInfoPreference).getList())
        }
    }
}