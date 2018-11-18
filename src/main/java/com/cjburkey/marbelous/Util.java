package com.cjburkey.marbelous;

/**
 * Created by CJ Burkey on 2018/11/18
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class Util {
    
    public static byte min(byte a, byte b) {
        return (a > b) ? b : a;
    }
    
    public static short min(short a, short b) {
        return (a > b) ? b : a;
    }
    
    public static int min(int a, int b) {
        return (a > b) ? b : a;
    }
    
    public static float min(float a, float b) {
        return (a > b) ? b : a;
    }
    
    public static long min(long a, long b) {
        return (a > b) ? b : a;
    }
    
    public static double min(double a, double b) {
        return (a > b) ? b : a;
    }
    
    public static byte max(byte a, byte b) {
        return (a < b) ? b : a;
    }
    
    public static short max(short a, short b) {
        return (a < b) ? b : a;
    }
    
    public static int max(int a, int b) {
        return (a < b) ? b : a;
    }
    
    public static float max(float a, float b) {
        return (a < b) ? b : a;
    }
    
    public static long max(long a, long b) {
        return (a < b) ? b : a;
    }
    
    public static double max(double a, double b) {
        return (a < b) ? b : a;
    }
    
    public static byte clamp(byte val, byte min, byte max) {
        return min(max(val, min), max);
    }
    
    public static short clamp(short val, short min, short max) {
        return min(max(val, min), max);
    }
    
    public static int clamp(int val, int min, int max) {
        return min(max(val, min), max);
    }
    
    public static float clamp(float val, float min, float max) {
        return min(max(val, min), max);
    }
    
    public static float clamp01(float val) {
        return clamp(val, 0.0f, 1.0f);
    }
    
    public static float clamp11(float val) {
        return clamp(val, -1.0f, 1.0f);
    }
    
    public static long clamp(long val, long min, long max) {
        return min(max(val, min), max);
    }
    
    public static double clamp(double val, double min, double max) {
        return min(max(val, min), max);
    }
    
    public static double clamp01(double val) {
        return clamp(val, 0.0d, 1.0d);
    }
    
    public static double clamp11(double val) {
        return clamp(val, -1.0d, 1.0d);
    }
    
}
