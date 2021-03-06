package org.wysaid.nativePort;

import java.nio.ShortBuffer;

/**
 * Created by wangyang on 15/7/29.
 */

//A wrapper for native class FrameRecorder
public class CGEFrameRecorder extends CGEFrameRenderer {

    static {
        NativeLibraryLoader.load();
    }

    public CGEFrameRecorder() {
        super(0); //防止多次初始化
        mNativeAddress = nativeCreateRecorder();
    }

    /////////////////视频录制相关////////////////////

    public boolean startRecording(int fps, String filename) {
        if(mNativeAddress != 0)
            return nativeStartRecording(mNativeAddress, fps, filename);
        return false;
    }

    public boolean isRecordingStarted() {
        if(mNativeAddress != 0)
            return nativeIsRecordingStarted(mNativeAddress);
        return false;
    }

    public boolean endRecording(boolean shouldSave) {
        if(mNativeAddress != 0)
            return nativeEndRecording(mNativeAddress, shouldSave);
        return false;
    }

    public void pauseRecording() {
        if(mNativeAddress != 0)
            nativePauseRecording(mNativeAddress);
    }

    //Not completed by now
//    public boolean isRecordingPaused() {
//        if(mNativeAddress != 0)
//            return nativeIsRecordingPaused(mNativeAddress);
//        return false;
//    }
//
//    public boolean resumeRecording() {
//        if(mNativeAddress != 0)
//            return nativeResumeRecording(mNativeAddress);
//        return false;
//    }

    public double getTimestamp() {
        if(mNativeAddress != 0)
            return nativeGetTimestamp(mNativeAddress);
        return 0.0;
    }

    public double getVideoStreamtime() {
        if(mNativeAddress != 0)
            return nativeGetVideoStreamtime(mNativeAddress);
        return 0.0;
    }

    public double getAudioStreamtime() {
        if(mNativeAddress != 0)
            return nativeGetAudioStreamtime(mNativeAddress);
        return 0.0;
    }

    public void setTempDir(String dir) {
        if(mNativeAddress != 0)
            nativeSetTempDir(mNativeAddress, dir);
    }

    //需要置于GPU绘制线程
    public void recordImageFrame() {
        if(mNativeAddress != 0)
            nativeRecordImageFrame(mNativeAddress);
    }

    //需要自行loop
    public void recordAudioFrame(ShortBuffer audioBuffer, int bufferLen) {
        if(mNativeAddress != 0)
            nativeRecordAudioFrame(mNativeAddress, audioBuffer, bufferLen);
    }

    /////////////////      private         ///////////////////////

    private native long nativeCreateRecorder();

    /////////////////视频录制相关////////////////////
    private native boolean nativeStartRecording(long holder, int fps, String filename);
    private native boolean nativeIsRecordingStarted(long holder);
    private native boolean nativeEndRecording(long holder, boolean shouldSave);
    private native void nativePauseRecording(long holder);
//    private native boolean nativeIsRecordingPaused(long holder);
//    private native boolean nativeResumeRecording(long holder);
    private native double nativeGetTimestamp(long holder);

    private native double nativeGetVideoStreamtime(long holder);
    private native double nativeGetAudioStreamtime(long holder);

    private native void nativeSetTempDir(long holder, String dir);

    private native void nativeRecordImageFrame(long holder);
    private native void nativeRecordAudioFrame(long holder, ShortBuffer audioBuffer, int bufferLen);

}
