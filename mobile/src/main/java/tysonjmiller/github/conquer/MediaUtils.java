package tysonjmiller.github.conquer;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Tyson Miller on 2/28/2015.
 */
public class MediaUtils {
    public final static String TAG = MediaUtils.class.getSimpleName();
    public final static int NOTIFICATION_ID = 12345;

    public static void showNotification(Service source, String songName, String artistName) {
        if (source == null) {
            return;
        }
        PendingIntent pi = PendingIntent.getActivity(source.getApplicationContext(), 0,
                new Intent(source.getApplicationContext(), source.getClass()),
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification();
        notification.tickerText = songName + " " + artistName;
        notification.icon = android.R.drawable.ic_media_play;
        notification.flags |= Notification.FLAG_ONGOING_EVENT;
        notification.setLatestEventInfo(source.getApplicationContext(), "MusicPlayerSample",
                "Playing: " + songName, pi);
        source.startForeground(NOTIFICATION_ID, notification);
    }

    public static ArrayList<Song> retrieveLocalMusic(Activity activity){
        ArrayList<Song> songs = new ArrayList();
        ContentResolver contentResolver = activity.getContentResolver();
        String[] projection = {
                MediaStore.Audio.Media._ID, // context id / uri id of the file
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA, // filepath of the audio file
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION
        };
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, projection, null, null, MediaStore.Audio.Media.TITLE);
        if (cursor == null) {
            // query failed, handle error.
            Log.e(TAG, "Query for media failed! Returning empty results");
            return songs;
        } else if (!cursor.moveToFirst()) {
            // no media on the device
            Log.e(TAG, "No media found on device! Returning empty results");
            return songs;
        } else {
            int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int dataColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int albumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            do {
                Song song = new Song();
                song.isLocal = true;
                song.localUri = cursor.getString(idColumn);
                song.artist = cursor.getString(artistColumn);
                song.title = cursor.getString(titleColumn);
                song.filePath = cursor.getString(dataColumn);
                song.album = cursor.getString(albumColumn);
                song.duration = cursor.getString(durationColumn);
                songs.add(song);
            } while (cursor.moveToNext());
            return songs;
        }
    }
}
