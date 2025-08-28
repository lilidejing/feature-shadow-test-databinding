package com.kye.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Map;


/**
 * @author BradySun
 * @description:音频播放工具类
 * @date : 2023/4/13 11:07
 */
public class SoundPoolUtil {
    private  static SoundPool sSoundPool;
    private  static Map<String,Sound> sMap = new HashMap<>();



    /**
     * 播放声音
     * @param resId 资源
     */
    public static void playSound(Context context, int resId) {
        if (sSoundPool == null) {
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setMaxStreams(3);
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            builder.setAudioAttributes(attrBuilder.build());
            sSoundPool = builder.build();
            sSoundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
                play(sampleId);
            });
        }
        String id = String.valueOf(resId);
        if (!sMap.containsKey(id)) {
            Sound sound = new Sound();
            sound.soundId = sSoundPool.load(context, resId, 1);
            sMap.put(id, sound);
        }else{
            Sound sound = sMap.get(id);
            if (sound != null) {
                play(sound.soundId);
            }
        }
    }

    /**
     *      设置音量
     * @param resId     R.raw.  资源
     * @param volume    音量 0-1
     */
    public static void setVolume(int resId, float volume) {
        String id = String.valueOf(resId);
        Sound sound = sMap.get(id);
        if (sound == null || sound.streamId == -1) {
            return;
        }
        sSoundPool.setVolume(sound.streamId, volume, volume);
        sound.volume = volume;
    }


    /**
     *      停止
     * @param resId
     */
    public static void setStop(int resId) {
        String id = String.valueOf(resId);
        Sound sound = sMap.get(id);
        if (sound == null || sound.streamId == -1) {
            return;
        }
        sSoundPool.stop(sound.streamId);
    }

    /**
     *      设置循环次数
     * @param resId     R.raw.  资源
     * @param loop      音量 0-1
     */
    public static void setLoop(int resId, int loop) {
        String id = String.valueOf(resId);
        Sound sound = sMap.get(id);
        if (sound == null || sound.streamId == -1) {
            return;
        }
        sSoundPool.pause(sound.streamId);
        sSoundPool.setLoop(sound.streamId, loop);
        sSoundPool.resume(sound.streamId);
    }


    /**
     *      释放资源
     */
    public static void releaseAll() {
        sMap.clear();
        if (sSoundPool == null) {
            return;
        }
        sSoundPool.release();
    }

    private static void play(int soundId){
        sSoundPool.play(soundId, 1f, 1f, 0, 0, 1f);
    }

    static class Sound{
        public int soundId = -1;
        public int streamId = -1;
        public int loop = 0;
        public float volume = 1f;
    }
}
