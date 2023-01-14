package de.adventure.game.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Audio {
    private Sound sound;
    private Music music;

    private float volume;
    private long soundID;
    private boolean isMusic;

    public Audio(String path, float volume, boolean isMusic) {
        this.isMusic = isMusic;

        if(isMusic) {
            music = Gdx.audio.newMusic(Gdx.files.internal(path));
            music.setVolume(volume);
        }else {
            sound = Gdx.audio.newSound(Gdx.files.internal(path));
        }
    }

    public void play() {
        if(isMusic) {
            music.play();
        }else {
            soundID = sound.play();
            sound.setVolume(soundID, volume);
        }
    }

    public void pause() {
        if(isMusic) {
            music.pause();
        }else {
            sound.pause();
        }
    }

    public void resume() {
        if(isMusic) {
            music.play();
        }else {
            sound.resume();
        }
    }

    public void stop() {
        if(isMusic) {
            music.stop();
        }else {
            sound.stop();
        }
    }

    public long getSoundID() {
        return soundID;
    }
}
