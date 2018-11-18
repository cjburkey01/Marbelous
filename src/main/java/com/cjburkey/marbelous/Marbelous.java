package com.cjburkey.marbelous;

import com.cjburkey.marbelous.graphics.Window;

/**
 * Created by CJ Burkey on 2018/11/18
 */
@SuppressWarnings("unused")
public final class Marbelous implements Runnable, Stoppable {
    
    public static final Marbelous instance = new Marbelous();
    
    private boolean running = false;
    
    private long lastUpdate = System.nanoTime();
    private float deltaUpdate;
    private long lastRender = System.nanoTime();
    private float deltaRender;
    
    private Marbelous() {
    }
    
    public void run() {
        Window window = new Window(300, 300, "Marbelous 0.0.1", true);
        window.setClearColor(0.85f, 0.85f, 0.85f);
        window.halfSize();
        window.center();
        window.show();
        
        running = true;
        while (running) {
            window.begin();
            if (window.shouldClose()) {
                stop();
            }
            update();
            render();
            window.finish();
        }
        
        window.cleanup();
    }
    
    public void stop() {
        running = false;
    }
    
    private void update() {
        long now = System.nanoTime();
        deltaUpdate = (float) ((now - lastUpdate) / 1000000000.0d);
        lastUpdate = now;
    }
    
    private void render() {
        long now = System.nanoTime();
        deltaRender = (float) ((now - lastRender) / 1000000000.0d);
        lastRender = now;
    }
    
    public float getDeltaUpdate() {
        return deltaUpdate;
    }
    
    public float getDeltaRender() {
        return deltaRender;
    }
    
    // Start Marbelous
    public static void main(String[] args) {
        instance.run();
    }
    
}
