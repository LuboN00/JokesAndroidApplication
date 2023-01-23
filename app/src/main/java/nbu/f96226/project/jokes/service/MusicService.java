package nbu.f96226.project.jokes.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;

import nbu.f96226.project.jokes.R;


public class MusicService extends Service {
    private MediaPlayer player;
    CountDownTimer timer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MusicService that = this;

        timer = new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
                player = MediaPlayer.create(that, R.raw.laugh);
                player.setLooping(true);
                player.start();
            }
            public void onFinish() {
                stopSelf();
            }
        }.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();
        timer.cancel();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
