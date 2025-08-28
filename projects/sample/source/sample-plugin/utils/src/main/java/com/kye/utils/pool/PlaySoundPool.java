package com.kye.utils.pool;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import com.kye.utils.RxSchedulers;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Routing
 * Desc TODO
 * Source
 * Created by lt on 2019/6/24 18:05
 * Modify by lt on 2019/6/24 18:05
 * Version 1.0
 */
public class PlaySoundPool {
    private int streamVolume;
    private SoundPool soundPool = null;
    private ConcurrentHashMap<Integer, Integer> soundPoolMap;

    private PlaySoundPool(Context context) {

        try {
            // 初始化soundPool 对象,第一个参数是允许有多少个声音流同时播放,第2个参数是声音类型,第三个参数是声音的品质
//			soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 100);
//            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC, 100);
            SoundPool.Builder spb = new SoundPool.Builder();
            spb.setMaxStreams(7);  //但是不建议将这个值设置的较大，较大会占用比较大的内存空间的。
            AudioAttributes.Builder ab = new AudioAttributes.Builder();
            ab.setUsage(AudioAttributes.USAGE_MEDIA);
            spb.setAudioAttributes(ab.build());
            soundPool = spb.build();
            // 初始化HASH表
            soundPoolMap = new ConcurrentHashMap<Integer, Integer>();
            soundPoolMap.put(1, soundPool.load(context, com.kye.pda.biz.base.utils.R.raw.success2, 1));
            soundPoolMap.put(2, soundPool.load(context, com.kye.pda.biz.base.utils.R.raw.errorflag, 1));
            soundPoolMap.put(3, soundPool.load(context, com.kye.pda.biz.base.utils.R.raw.short_erro, 1));
            soundPoolMap.put(4, soundPool.load(context, com.kye.pda.biz.base.utils.R.raw.success3, 1));
            soundPoolMap.put(5, soundPool.load(context, com.kye.pda.biz.base.utils.R.raw.waybill_success, 1));
            soundPoolMap.put(6, soundPool.load(context, com.kye.pda.biz.base.utils.R.raw.quick_success, 1));
            soundPoolMap.put(7, soundPool.load(context, com.kye.pda.biz.base.utils.R.raw.waybill_fail, 1));
//            Thread.sleep(200);
            // 获得声音设备和设备音量
            AudioManager mgr = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            streamVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (Build.VERSION.SDK_INT >= 22) {
                streamVolume = 1;
            }
//			streamVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        } catch (Exception ex) {
            // Log.printLog("[PlaySoundPool]" + ex.getMessage());
        }
    }

    private static PlaySoundPool instance = null;

    /*
     * Play SoundPool实例
     */
    public static PlaySoundPool getInstance(Context application) {
        if (instance == null) {
            instance = new PlaySoundPool(application);
        }
        return instance;
    }

    /*
     * 播放扫描成功声音
     */
    public void playScanSuccessSound() {
        Integer soundId = soundPoolMap.get(1);
        play(soundId);
    }

    /*
     * 播放扫描成功声音
     */
    public void playScanSuccessSoundNew() {
        Integer soundId = soundPoolMap.get(4);
        play(soundId);
    }

    /*
     * 播放扫描失败声音
     */
    public void playScanErrorSound() {
        Integer soundId = soundPoolMap.get(2);
        play(soundId);
    }

    /*
     * 播放扫描失败声音
     */
    public void playShortErrorSound() {
        try {
            Integer soundId = soundPoolMap.get(3);
            play(soundId);
        } catch (Exception ex) {
            Log.i("initLetterBoardHelper", "播放音频失效:" + ex.getMessage());
        }

    }

    /**
     * 播放报单成功的声音
     */
    public void playWaybillSuccessSound() {
        Integer soundId = soundPoolMap.get(5);
        play(soundId);
    }

    /**
     * 播放成功的声音
     */
    public void playSuccessSound() {
        Integer soundId = soundPoolMap.get(6);
        play(soundId);
    }

    /**
     * 根据key播放对应的音频
     */
    public void playSound(int key) {
        Integer soundId = soundPoolMap.get(key);
        play(soundId);
    }

    private void play(Integer soundId) {
        if (soundId != null) {
            //播放放到子线程，解决线上ANR问题
            RxSchedulers.getExecutorService().execute(new Runnable() {
                @Override
                public void run() {
                    soundPool.play(soundId, streamVolume, streamVolume, 0, 0, 1f);
                }
            });
        }
    }

    public void release() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
