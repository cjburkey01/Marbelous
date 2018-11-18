package com.cjburkey.marbelous;

import java.util.Objects;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Created by CJ Burkey on 2018/11/18
 */
public class Marbelous implements Runnable, Stoppable {
    
    private long window;
    private boolean running = false;
    
    public void run() {
        GLFWErrorCallback.createPrint(System.err).set();
        
        if (!glfwInit()) {
            throw new RuntimeException("Failed to initialize GLFW");
        }
        
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        
        window = glfwCreateWindow(300, 300, "Marbelous 0.0.1", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create GLFW window");
        }
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
        
        GL.createCapabilities();
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        
        running = true;
        while (running) {
            glClear(GL_COLOR_BUFFER_BIT);
            
            // TODO: UPDATE
            // TODO: RENDER
            
            glfwSwapBuffers(window);
            glfwPollEvents();
            if (glfwWindowShouldClose(window)) {
                stop();
            }
        }
        
        glfwDestroyWindow(window);
        glfwFreeCallbacks(window);
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }
    
    public void stop() {
        running = false;
    }
    
    // Start Marbelous
    public static void main(String[] args) {
        new Marbelous().run();
    }
    
}
