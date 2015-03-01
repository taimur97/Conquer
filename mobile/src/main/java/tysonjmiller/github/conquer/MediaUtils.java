package tysonjmiller.github.conquer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;

/**
 * Created by Tyson Miller on 2/28/2015.
 */
public class MediaUtils {
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
}
