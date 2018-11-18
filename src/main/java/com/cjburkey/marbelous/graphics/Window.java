package com.cjburkey.marbelous.graphics;

import com.cjburkey.marbelous.Util;
import java.util.Objects;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Created by CJ Burkey on 2018/11/18
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class Window {
    
    private static Window win;
    
    private long window;
    private int width;
    private int height;
    private String title;
    private final Vector3f clearColor = new Vector3f();
    private boolean shouldClose;
    private boolean vsync;
    
    public Window(int width, int height, String title, boolean vsync) {
        if (win != null) {
            throw new RuntimeException("GLFW window already created");
        }
        
        this.width = width;
        this.height = height;
        this.title = title;
        this.vsync = vsync;
        
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
        
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create GLFW window");
        }
        
        glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            this.width = w;
            this.height = h;
            glViewport(0, 0, w, h);
        });
        
        glfwMakeContextCurrent(window);
        setVsync(vsync);
        GL.createCapabilities();
    }
    
    public void show() {
        glfwShowWindow(window);
    }
    
    public void hide() {
        glfwHideWindow(window);
    }
    
    public void begin() {
        glfwPollEvents();
        shouldClose = glfwWindowShouldClose(window);
        glClear(GL_COLOR_BUFFER_BIT);
    }
    
    public void finish() {
        glfwSwapBuffers(window);
    }
    
    public void setClearColor(float red, float green, float blue) {
        clearColor.set(Util.clamp01(red), Util.clamp01(green), Util.clamp01(blue));
        glClearColor(clearColor.x, clearColor.y, clearColor.z, 1.0f);
    }
    
    public void cleanup() {
        hide();
        glfwDestroyWindow(window);
        glfwFreeCallbacks(window);
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }
    
    public boolean shouldClose() {
        return shouldClose;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        glfwSetWindowSize(window, width, height);
    }
    
    public Vector2i getMonitorSize(long monitor) {
        GLFWVidMode mon = glfwGetVideoMode(monitor);
        if (mon == null) {
            return new Vector2i();
        }
        return new Vector2i(mon.width(), mon.height());
    }
    
    public Vector2i getMonitorSize() {
        return getMonitorSize(glfwGetPrimaryMonitor());
    }
    
    public void center() {
        Vector2i monitor = getMonitorSize();
        glfwSetWindowPos(window, (monitor.x - width) / 2, (monitor.y - height) / 2);
    }
    
    public void halfSize() {
        Vector2i monitor = getMonitorSize();
        setSize(monitor.x / 2, monitor.y / 2);
    }
    
    public void setTitle(String title) {
        this.title = title;
        glfwSetWindowTitle(window, title);
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setVsync(boolean vsync) {
        glfwSwapInterval(vsync ? 1 : 0);
    }
    
    public boolean getVsync() {
        return vsync;
    }
    
    public static Window getInstance() {
        return win;
    }
    
}
