package adapter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.uas_10120068_alberiansyah.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseNotificationHandle extends FirebaseMessagingService {

    public FirebaseNotificationHandle(Context applicationContext) {
    }

    public void onMessageReceived(Context context, @NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            // Tampilkan notifikasi di bilah atas layar
            showNotification(context, title, body);
        }
    }

    public void showNotification(Context context, String title, String body) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "channel_id";
        String channelName = "Channel Name";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, channelId)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setSmallIcon(R.drawable.red);

        Notification notification = builder.build();
        notificationManager.notify(0, notification);
    }
}

