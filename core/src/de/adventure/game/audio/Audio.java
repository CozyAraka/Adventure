package de.adventure.game.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import de.adventure.game.Main;

public class Audio {
    private Sound sound;
    private Music music;

    private float volume;
    private long soundID;
    private boolean isMusic;

    public Main main;

    public Audio(String path, float volume, boolean isMusic, Main main) {
        this.isMusic = isMusic;
        this.main = main;
        this.volume = volume;

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

    public void setAsLoop() {
        if(isMusic) {
            music.setLooping(true);
        }
    }

    public void setVolume(float volume) {
        if(isMusic) {
            music.setVolume(volume);
        }else {
            sound.setVolume(soundID, volume);
        }
    }

    private float volume1 = 0.1F;
    public void playOnCords(float x, float y) {
        float val1 = (float) -Math.sqrt((double) (x - main.getPlayer().getXCord()) * (x - main.getPlayer().getXCord()) + (y - main.getPlayer().getYCord()) * (y - main.getPlayer().getYCord())) / 300;
        volume1 = 0.1F + val1;
        if(volume1 < 0) {
            volume1 = 0;
        }
        //System.out.println(val1);
        //System.out.println(volume1);
        music.setVolume(volume1);
        //System.out.println(val1);
        System.out.println(main.getPlayer().getXCord() + " XCord : YCord " + main.getPlayer().getYCord());
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
