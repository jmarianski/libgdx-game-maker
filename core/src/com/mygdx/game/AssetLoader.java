package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Asset loader. Currently supports only
 */
public class AssetLoader {

    public Array<Texture> tex;
    public static AssetManager assets;

    public AssetLoader() {
        assets =  new AssetManager();
        tex = new Array<Texture>();
    }

    /**
     * Loads folder.
     * @param folder Path to this directory. It changes depending if you're working under
     *               PC or android
     * @param levels Checks how many levels it should go down. Negative value - goes until -1024
     */
    public void loadFolder(String folder, int levels) {
        // supports nesting
        FileHandle[] files = Gdx.files.internal(folder).list();
        for(FileHandle file: files) {
            System.out.println("loading "+file.name()+" ext: "+file.extension());
            if(file.extension().equals("png")) {
                System.out.println("loading texture");
                assets.load(file.path(), Texture.class);
            }
            if(file.isDirectory() && (levels!=0) && levels>-1024) {
                loadFolder(file.path(), --levels);
            }
        }
    }
    /**
     * Unloads folder
     * @param folder Path to this directory. It changes depending if you're working under
     *               PC or android
     * @param levels Checks how many levels it should go down. Negative value - goes until -1024
     */
    public void unloadFolder(String folder, int levels) {
        FileHandle[] files = Gdx.files.internal(folder).list();
        for(FileHandle file: files) {
            System.out.println("unloading "+file.name());
            if(file.extension().equals("png")) {
                tex.removeValue(assets.get(file.path(), Texture.class), false);
            }
            if(file.isDirectory() && levels!=0 && levels>-1024) {
                unloadFolder(file.path(), --levels);
            }
            if(!file.isDirectory())
                assets.unload(file.path());
        }
    }

    private <T> void waitingForLoad(final String filename, final Class<T> type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Random rand = new Random();
                try {
                    int timesleep = 0;
                    while (!assets.isLoaded(filename)) {
                        int sleep = 10 + rand.nextInt(100);
                        timesleep += sleep;
                        Thread.sleep(sleep);
                    }
                    if(assets.isLoaded(filename)) {
                        if (type == Texture.class) {
                            tex.add(assets.get(filename, Texture.class));
                        }
                    }
                } catch(InterruptedException e) {
                    // do nothing, what can we do?
                }

            }
        });
    }

    public boolean isLoaded(String fileName) {
        return assets.isLoaded(fileName);
    }

    public void finishLoading() {
        assets.finishLoading();
    }

    public void dispose() {
        tex.clear();
        assets.dispose();
    }



}
