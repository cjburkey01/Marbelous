package com.cjburkey.marbelous.graphics;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.nio.FloatBuffer;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * Created by CJ Burkey on 2018/11/18
 */
@SuppressWarnings("unused")
public class ShaderProgram {
    
    private boolean linked;
    private int program;
    private final Object2IntOpenHashMap<ShaderType> shaders = new Object2IntOpenHashMap<>();
    private final Object2IntOpenHashMap<String> uniforms = new Object2IntOpenHashMap<>();
    
    public ShaderProgram() {
        program = glCreateProgram();
        if (program <= 0) {
            throw new RuntimeException("Failed to create shader program");
        }
    }
    
    public void addShader(ShaderType shaderType, String source) {
        if (linked) {
            throw new RuntimeException("Program is already linked");
        }
        if (shaders.containsKey(shaderType)) {
            throw new RuntimeException("Shader program already has shader of type: " + shaderType);
        }
        int shader = glCreateShader(shaderType.type);
        if (shader <= 0) {
            throw new RuntimeException("Failed to create shader of type: " + shaderType);
        }
        glShaderSource(shader, source);
        glCompileShader(shader);
        String info = glGetShaderInfoLog(shader).trim();
        if (!info.isEmpty()) {
            throw new RuntimeException("Failed to load shader source of type: " + shaderType + ": " + info);
        }
        shaders.put(shaderType, shader);
    }
    
    public void finish() {
        if (linked) {
            throw new RuntimeException("Program is already linked");
        }
        for (int shader : shaders.values()) {
            glAttachShader(program, shader);
        }
        glLinkProgram(program);
        String info = glGetProgramInfoLog(program).trim();
        if (!info.isEmpty()) {
            throw new RuntimeException("Failed to link shader program: " + info);
        }
        for (int shader : shaders.values()) {
            glDetachShader(program, shader);
            glDeleteShader(shader);
        }
        shaders.clear();
        linked = true;
    }
    
    public void bind() {
        if (!linked) {
            throw new RuntimeException("Program is not yet linked");
        }
        glUseProgram(program);
    }
    
    public void cleanup() {
        glDeleteProgram(program);
    }
    
    public void setUniform(String name, float value) {
        int at = uniformLoc(name);
        if (at >= 0) {
            glUniform1f(at, value);
        }
    }
    
    public void setUniform(String name, Vector2f value) {
        int at = uniformLoc(name);
        if (at >= 0) {
            glUniform2f(at, value.x, value.y);
        }
    }
    
    public void setUniform(String name, Vector3f value) {
        int at = uniformLoc(name);
        if (at >= 0) {
            glUniform3f(at, value.x, value.y, value.z);
        }
    }
    
    public void setUniform(String name, Vector4f value) {
        int at = uniformLoc(name);
        if (at >= 0) {
            glUniform4f(at, value.x, value.y, value.z, value.w);
        }
    }
    
    public void setUniform(String name, Matrix3f value) {
        int at = uniformLoc(name);
        if (at >= 0) {
            FloatBuffer buffer = memAllocFloat(9);
            value.get(buffer);
            buffer.flip();
            glUniformMatrix3fv(at, false, buffer);
            memFree(buffer);
        }
    }
    
    public void setUniform(String name, Matrix4f value) {
        int at = uniformLoc(name);
        if (at >= 0) {
            FloatBuffer buffer = memAllocFloat(16);
            value.get(buffer);
            buffer.flip();
            glUniformMatrix4fv(at, false, buffer);
            memFree(buffer);
        }
    }
    
    private int uniformLoc(String name) {
        if (!linked) {
            throw new RuntimeException("The shader has not been linked");
        }
        if (uniforms.containsKey(name)) {
            return uniforms.getInt(name);
        }
        int at = glGetUniformLocation(program, name);
        uniforms.put(name, at);
        return at;
    }
    
    public enum ShaderType {
        VERTEX(GL_VERTEX_SHADER),
        FRAGMENT(GL_FRAGMENT_SHADER),
        GEOMETRY(GL_GEOMETRY_SHADER);
        
        private final int type;
        
        ShaderType(int type) {
            this.type = type;
        }
    }
    
}
