package sse.com.moneyalertsse.menu.splash;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.Locale;

import sse.com.moneyalertsse.Menu_principale;
import sse.com.moneyalertsse.R;
import sse.com.moneyalertsse.baseDeDonnee.DaoBase.ClasseDao.DepenseDao;
import sse.com.moneyalertsse.menu.parametre.HelpFragment;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView iconeSplash = (ImageView) findViewById(R.id.imageSplash);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        iconeSplash.startAnimation(animation);


        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        DepenseDao depenseDao = new DepenseDao(getBaseContext());

                        depenseDao.ouvrirBaseDonnee();

                        depenseDao.misAjourUtilite();

                        depenseDao.fermerBaseDeDonnee();

                    }
                });

                runOnUiThread(new Runnable()
                {
                    // gestion de la langue
                    @Override
                    public void run() {
                        gestionDeLaLangue(getBaseContext());
                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // gestion des notificatios

                        gestionDeNotification(getBaseContext());

                    }
                });

                Intent intent = new Intent(getApplicationContext(), Menu_principale.class);

                startActivity(intent);

                finish();

            }
        }, 6000);
    }

    /**
     * fonction permettant de gérer le changement de langue
     */

    public static void gestionDeLaLangue(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String langue = preferences.getString("langue", "fr");

        Locale locale = new Locale(langue);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.setLocale(locale);

        context.getResources().updateConfiguration(configuration, context.getApplicationContext().getResources().getDisplayMetrics());

    }

    /**
     * fonction permettant les notifications
     */

    public static void gestionDeNotification(Context context)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean notification = preferences.getBoolean("notification", false);

        if(notification)
        {
            // on definit l'heure et la minute de la notification doit être vu

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);

            // on crée l'alarme

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            // pour la notification

            int idNotification = 3; // l'identifiant de la notification

            // on crée la notification

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

            // les parametres de la notification

            Notification.Builder notif = new Notification.Builder(context.getApplicationContext());
            notif.setSmallIcon(R.drawable.icone);
            notif.setContentText(context.getResources().getString(R.string.infoNotif));
            notif.setWhen(System.currentTimeMillis());
            notif.setAutoCancel(true);


            Intent intent = new Intent(context.getApplicationContext(), HelpFragment.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            notif.setContentIntent(pendingIntent);



            if (notificationManager != null)
            {
                notificationManager.notify(idNotification, notif.build());
            }

            // definition de la periode à laquelle l'alarme doit se declencher ainsi que la notification

            if (alarmManager != null)
            {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HALF_DAY, pendingIntent);
            }


        }

    }
}
