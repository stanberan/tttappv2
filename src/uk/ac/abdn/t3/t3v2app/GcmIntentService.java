package uk.ac.abdn.t3.t3v2app;

import java.util.Date;

import uk.ac.abdn.t3.t3v2app.R.drawable;

import com.google.android.gms.gcm.GoogleCloudMessaging;






import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
	private static final String TAG = null;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification(extras);
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification(extras);
            // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
               
              
                sendNotification(extras);
                Log.e("NOTIFICATION", "Received: " + extras.toString());
              //  Toast.makeText(AppController.getInstance().context, extras.toString(), Toast.LENGTH_LONG).show();
                
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
    private Bitmap getBitmap(int id) {
        Drawable myIcon = getResources().getDrawable(id );//hope so id == R.drawable.youricon
        Bitmap bitmap = ((BitmapDrawable)myIcon ).getBitmap();
        return bitmap;
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(Bundle b) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
Intent i=new Intent(this,OverviewActivity.class);
//add extras from t3 server
i.putExtras(b);
i.putExtra("caller", "notification");
//get times and display in not.
long ms=Long.parseLong(b.getString("time"));
String pattern="MM/dd/yyyy HH:mm:ss";
String sent=DateFormat.format(pattern, new Date(ms)).toString();
String received=DateFormat.format(pattern, new Date()).toString();


        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                i, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_stat_t3_nfc_blue_transparent_notification)
        .setLargeIcon(getBitmap(R.drawable.ic_launcher))
        .setContentTitle("New Capabilities Detected!")
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText("Notification sent:"+sent+"\nNotification received:"+received))
        .setContentText(b.getString("message"));

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}