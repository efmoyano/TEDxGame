/*
 * Copyright (C) 2011,2012,2013 Samuel Audet
 *
 * This file is part of JavaCV.
 *
 * JavaCV is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version (subject to the "Classpath" exception
 * as provided in the LICENSE.txt file that accompanied this code).
 *
 * JavaCV is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JavaCV.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * This file was derived from videoInput.h of videoInput 0.1995,
 * which is covered by the following copyright notice:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * //////////////////////////////////////////////////////////
 * //Written by Theodore Watson - theo.watson@gmail.com    //
 * //Do whatever you want with this code but if you find   //
 * //a bug or make an improvement I would love to know!    //
 * //                                                      //
 * //Warning This code is experimental                     //
 * //use at your own risk :)                               //
 * //////////////////////////////////////////////////////////
 * /////////////////////////////////////////////////////////
 *                        Shoutouts
 *
 * Thanks to:
 *
 *            Dillip Kumar Kara for crossbar code.
 *            Zachary Lieberman for getting me into this stuff
 *            and for being so generous with time and code.
 *            The guys at Potion Design for helping me with VC++
 *            Josh Fisher for being a serious C++ nerd :)
 *            Golan Levin for helping me debug the strangest
 *            and slowest bug in the world!
 *
 *            And all the people using this library who send in
 *            bugs, suggestions and improvements who keep me working on
 *            the next version - yeah thanks a lot ;)
 *
 */

package com.googlecode.javacv.cpp;

import com.googlecode.javacpp.BytePointer;
import com.googlecode.javacpp.CharPointer;
import com.googlecode.javacpp.Pointer;
import com.googlecode.javacpp.annotation.ByRef;
import com.googlecode.javacpp.annotation.Cast;
import com.googlecode.javacpp.annotation.MemberGetter;
import com.googlecode.javacpp.annotation.Opaque;
import com.googlecode.javacpp.annotation.Platform;

import static com.googlecode.javacpp.Loader.*;

/**
 *
 * @author Samuel Audet
 */
@Platform(value="windows", include="<videoInput.cpp>",
          includepath={"../videoInput-update2013/videoInputSrcAndDemos/libs/videoInput/",
                       "../videoInput-update2013/videoInputSrcAndDemos/libs/DShow/Include/"},
          link={"ole32", "oleaut32", "amstrmid", "strmiids", "uuid"})
public class videoInputLib {
    static { load(); videoInput.setComMultiThreaded(true); }

    /**
     *
     * @return
     */
    @Cast("bool")
    public static native boolean verbose();

    /**
     *
     * @param verbose
     */
    public static native void verbose(boolean verbose);

    /**
     *
     */
    public static final double
            VI_VERSION = 0.1995;
    
    /**
     *
     */
    public static final int
            VI_MAX_CAMERAS = 20,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_NUM_TYPES   = 18,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_NUM_FORMATS = 18,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_COMPOSITE = 0,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_S_VIDEO   = 1,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_TUNER     = 2,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_USB       = 3,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_1394      = 4,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_NTSC_M   = 0,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_PAL_B    = 1,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_PAL_D    = 2,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_PAL_G    = 3,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_PAL_H    = 4,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_PAL_I    = 5,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_PAL_M    = 6,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_PAL_N    = 7,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_PAL_NC   = 8,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_SECAM_B  = 9,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_SECAM_D  = 10,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_SECAM_G  = 11,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_SECAM_H  = 12,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_SECAM_K  = 13,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_SECAM_K1 = 14,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_SECAM_L  = 15,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_NTSC_M_J = 16,

    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    VI_NTSC_433 = 17;

    /**
     *
     */
    @Opaque public static class GUID                  extends Pointer { }

    /**
     *
     */
    @Opaque public static class ICaptureGraphBuilder2 extends Pointer { }

    /**
     *
     */
    @Opaque public static class IGraphBuilder         extends Pointer { }

    /**
     *
     */
    @Opaque public static class IBaseFilter           extends Pointer { }

    /**
     *
     */
    @Opaque public static class IAMCrossbar           extends Pointer { }

    /**
     *
     */
    @Opaque public static class IMediaControl         extends Pointer { }

    /**
     *
     */
    @Opaque public static class ISampleGrabber        extends Pointer { }

    /**
     *
     */
    @Opaque public static class IMediaEventEx         extends Pointer { }

    /**
     *
     */
    @Opaque public static class IAMStreamConfig       extends Pointer { }

    /**
     *
     */
    @Opaque public static class _AMMediaType          extends Pointer { }

    /**
     *
     */
    @Opaque public static class SampleGrabberCallback extends Pointer { }

    /**
     *
     * @return
     */
    public static native int comInitCount();

    /**
     *
     * @param comInitCount
     */
    public static native void comInitCount(int comInitCount);

    /**
     *
     */
    public static class videoDevice extends Pointer {
        static { load(); }

        /**
         *
         */
        public videoDevice() { allocate(); }

        /**
         *
         * @param p
         */
        public videoDevice(Pointer p) { super(p); }
        private native void allocate();

        /**
         *
         * @param w
         * @param h
         */
        public native void setSize(int w, int h);

        /**
         *
         * @param pBF
         */
        public native void NukeDownstream(IBaseFilter pBF);

        /**
         *
         */
        public native void destroyGraph();

        /**
         *
         * @return
         */
        public native int videoSize();

        /**
         *
         * @param videoSize
         * @return
         */
        public native videoDevice videoSize(int videoSize);

        /**
         *
         * @return
         */
        public native int width();

        /**
         *
         * @param width
         * @return
         */
        public native videoDevice width(int width);

        /**
         *
         * @return
         */
        public native int height();

        /**
         *
         * @param height
         * @return
         */
        public native videoDevice height(int height);

        /**
         *
         * @return
         */
        public native int tryWidth();

        /**
         *
         * @param tryWidth
         * @return
         */
        public native videoDevice tryWidth(int tryWidth);

        /**
         *
         * @return
         */
        public native int tryHeight();

        /**
         *
         * @param tryHeight
         * @return
         */
        public native videoDevice tryHeight(int tryHeight);

        /**
         *
         * @return
         */
        public native ICaptureGraphBuilder2 pCaptureGraph();

        /**
         *
         * @param pCaptureGraph
         * @return
         */
        public native videoDevice pCaptureGraph(ICaptureGraphBuilder2 pCaptureGraph);

        /**
         *
         * @return
         */
        public native IGraphBuilder pGraph();

        /**
         *
         * @param pGraph
         * @return
         */
        public native videoDevice pGraph(IGraphBuilder pGraph);

        /**
         *
         * @return
         */
        public native IMediaControl pControl();

        /**
         *
         * @param pControl
         * @return
         */
        public native videoDevice pControl(IMediaControl pControl);

        /**
         *
         * @return
         */
        public native IBaseFilter pVideoInputFilter();

        /**
         *
         * @param pVideoInputFilter
         * @return
         */
        public native videoDevice pVideoInputFilter(IBaseFilter pVideoInputFilter);

        /**
         *
         * @return
         */
        public native IBaseFilter pGrabberF();

        /**
         *
         * @param pGrabberF
         * @return
         */
        public native videoDevice pGrabberF(IBaseFilter pGrabberF);

        /**
         *
         * @return
         */
        public native IBaseFilter pDestFilter();

        /**
         *
         * @param pDestFilter
         * @return
         */
        public native videoDevice pDestFilter(IBaseFilter pDestFilter);

        /**
         *
         * @return
         */
        public native IAMStreamConfig streamConf();

        /**
         *
         * @param streamConf
         * @return
         */
        public native videoDevice streamConf(IAMStreamConfig streamConf);

        /**
         *
         * @return
         */
        public native ISampleGrabber  pGrabber();

        /**
         *
         * @param pGrabber
         * @return
         */
        public native videoDevice pGrabber(ISampleGrabber pGrabber);

        /**
         *
         * @return
         */
        public native _AMMediaType pAmMediaType();

        /**
         *
         * @param pAmMediaType
         * @return
         */
        public native videoDevice pAmMediaType(_AMMediaType pAmMediaType);

        /**
         *
         * @return
         */
        public native IMediaEventEx pMediaEvent();

        /**
         *
         * @param pMediaEvent
         * @return
         */
        public native videoDevice pMediaEvent(IMediaEventEx pMediaEvent);

        /**
         *
         * @return
         */
        public native @ByRef GUID videoType();

        /**
         *
         * @param videoType
         * @return
         */
        public native videoDevice videoType(GUID videoType);

        /**
         *
         * @return
         */
        public native @Cast("long") int formatType();

        /**
         *
         * @param formatType
         * @return
         */
        public native videoDevice formatType(int formatType);

        /**
         *
         * @return
         */
        public native SampleGrabberCallback sgCallback();

        /**
         *
         * @param sgCallback
         * @return
         */
        public native videoDevice sgCallback(SampleGrabberCallback sgCallback);

        /**
         *
         * @return
         */
        public native @Cast("bool") boolean tryDiffSize();

        /**
         *
         * @param tryDiffSize
         * @return
         */
        public native videoDevice tryDiffSize(boolean tryDiffSize);

        /**
         *
         * @return
         */
        public native @Cast("bool") boolean useCrossbar();

        /**
         *
         * @param useCrossbar
         * @return
         */
        public native videoDevice useCrossbar(boolean useCrossbar);

        /**
         *
         * @return
         */
        public native @Cast("bool") boolean readyToCapture();

        /**
         *
         * @param readyToCapture
         * @return
         */
        public native videoDevice readyToCapture(boolean readyToCapture);

        /**
         *
         * @return
         */
        public native @Cast("bool") boolean sizeSet();

        /**
         *
         * @param sizeSet
         * @return
         */
        public native videoDevice sizeSet(boolean sizeSet);

        /**
         *
         * @return
         */
        public native @Cast("bool") boolean setupStarted();

        /**
         *
         * @param setupStarted
         * @return
         */
        public native videoDevice setupStarted(boolean setupStarted);

        /**
         *
         * @return
         */
        public native @Cast("bool") boolean specificFormat();

        /**
         *
         * @param specificFormat
         * @return
         */
        public native videoDevice specificFormat(boolean specificFormat);

        /**
         *
         * @return
         */
        public native @Cast("bool") boolean autoReconnect();

        /**
         *
         * @param autoReconnect
         * @return
         */
        public native videoDevice autoReconnect(boolean autoReconnect);

        /**
         *
         * @return
         */
        public native int nFramesForReconnect();

        /**
         *
         * @param nFramesForReconnect
         * @return
         */
        public native videoDevice nFramesForReconnect(int nFramesForReconnect);

        /**
         *
         * @return
         */
        @Cast("unsigned long")
        public native int nFramesRunning();

        /**
         *
         * @param nFramesRunning
         * @return
         */
        public native videoDevice nFramesRunning(int nFramesRunning);

        /**
         *
         * @return
         */
        public native int connection();

        /**
         *
         * @param connection
         * @return
         */
        public native videoDevice connection(int connection);

        /**
         *
         * @return
         */
        public native int storeConn();

        /**
         *
         * @param storeConn
         * @return
         */
        public native videoDevice storeConn(int storeConn);

        /**
         *
         * @return
         */
        public native int myID();

        /**
         *
         * @param myID
         * @return
         */
        public native videoDevice myID(int myID);

        /**
         *
         * @return
         */
        @Cast("long")
        public native int requestedFrameTime();

        /**
         *
         * @param requestedFrameTime
         * @return
         */
        public native videoDevice requestedFrameTime(int requestedFrameTime);

        /**
         *
         * @return
         */
        @MemberGetter @Cast("char*")  public native BytePointer nDeviceName();

        /**
         *
         * @return
         */
        @MemberGetter @Cast("WCHAR*") public native CharPointer wDeviceName();

        /**
         *
         * @return
         */
        @Cast("unsigned char*") public native BytePointer pixels();

        /**
         *
         * @param pixels
         * @return
         */
        public native videoDevice pixels(BytePointer pixels);

        /**
         *
         * @return
         */
        @Cast("char*")          public native BytePointer pBuffer();

        /**
         *
         * @param pBuffer
         * @return
         */
        public native videoDevice pBuffer(BytePointer pBuffer);
    }

    /**
     *
     */
    public static class videoInput extends Pointer {
        static { load(); }

        /**
         *
         */
        public videoInput() { allocate(); }

        /**
         *
         * @param p
         */
        public videoInput(Pointer p) { super(p); }
        private native void allocate();

        /**
         *
         * @param _verbose
         */
        public static native void setVerbose(@Cast("bool") boolean _verbose);

        /**
         *
         * @param bMulti
         */
        public static native void setComMultiThreaded(@Cast("bool") boolean bMulti);

        /**
         *
         * @return
         */
        public static int listDevices() { return listDevices(false); }

        /**
         *
         * @param silent
         * @return
         */
        public static native int listDevices(@Cast("bool") boolean silent);

        /**
         *
         * @param deviceID
         * @return
         */
        public static native String getDeviceName(int deviceID);

        /**
         *
         * @param useCallback
         */
        public native void setUseCallback(@Cast("bool") boolean useCallback);

        /**
         *
         * @param deviceID
         * @param idealFramerate
         */
        public native void setIdealFramerate(int deviceID, int idealFramerate);

        /**
         *
         * @param deviceNumber
         * @param doReconnect
         * @param numMissedFramesBeforeReconnect
         */
        public native void setAutoReconnectOnFreeze(int deviceNumber, @Cast("bool") boolean doReconnect, int numMissedFramesBeforeReconnect);

        /**
         *
         * @param deviceID
         * @return
         */
        public native boolean setupDevice(int deviceID);

        /**
         *
         * @param deviceID
         * @param w
         * @param h
         * @return
         */
        public native boolean setupDevice(int deviceID, int w, int h);

        /**
         *
         * @param deviceID
         * @param connection
         * @return
         */
        public native boolean setupDevice(int deviceID, int connection);

        /**
         *
         * @param deviceID
         * @param w
         * @param h
         * @param connection
         * @return
         */
        public native boolean setupDevice(int deviceID, int w, int h, int connection);

        /**
         *
         * @param deviceNumber
         * @param format
         * @return
         */
        public native boolean setFormat(int deviceNumber, int format);

        /**
         *
         * @param deviceID
         * @return
         */
        public native boolean isFrameNew(int deviceID);

        /**
         *
         * @param deviceID
         * @return
         */
        public native boolean isDeviceSetup(int deviceID);

        /**
         *
         * @param deviceID
         * @return
         */
        public BytePointer getPixels(int deviceID) { return getPixels(deviceID, true, false); }

        /**
         *
         * @param deviceID
         * @param flipRedAndBlue
         * @param flipImage
         * @return
         */
        public native @Cast("unsigned char*") BytePointer getPixels(int deviceID,
                @Cast("bool") boolean flipRedAndBlue, @Cast("bool") boolean flipImage);

        /**
         *
         * @param id
         * @param pixels
         * @return
         */
        public boolean getPixels(int id, BytePointer pixels) { return getPixels(id, pixels, true, false); }

        /**
         *
         * @param id
         * @param pixels
         * @param flipRedAndBlue
         * @param flipImage
         * @return
         */
        public native boolean getPixels(int id, @Cast("unsigned char*") BytePointer pixels,
                @Cast("bool") boolean flipRedAndBlue, @Cast("bool") boolean flipImage);

        /**
         *
         * @param deviceID
         */
        public native void showSettingsWindow(int deviceID);

        /**
         *
         * @param deviceID
         * @param Property
         * @param lValue
         * @param Flags
         * @param useDefaultValue
         * @return
         */
        public native boolean setVideoSettingFilter(int deviceID, @Cast("long") int Property,
                @Cast("long") int lValue, @Cast("long") int Flags/*=0*/, @Cast("bool") boolean useDefaultValue/*=false*/);

        /**
         *
         * @param deviceID
         * @param Property
         * @param pctValue
         * @param Flags
         * @return
         */
        public native boolean setVideoSettingFilterPct(int deviceID, @Cast("long") int Property,
                float pctValue, @Cast("long") int Flags/*=0*/);

        /**
         *
         * @param deviceID
         * @param Property
         * @param min
         * @param max
         * @param SteppingDelta
         * @param currentValue
         * @param flags
         * @param defaultValue
         * @return
         */
        public native boolean getVideoSettingFilter(int deviceID, @Cast("long") int Property,
                @ByRef @Cast("long*") int[] min, @ByRef @Cast("long*") int[] max,
                @ByRef @Cast("long*") int[] SteppingDelta, @ByRef @Cast("long*") int[] currentValue,
                @ByRef @Cast("long*") int[] flags, @ByRef @Cast("long*") int[] defaultValue);

        /**
         *
         * @param deviceID
         * @param Property
         * @param lValue
         * @param Flags
         * @param useDefaultValue
         * @return
         */
        public native boolean setVideoSettingCamera(int deviceID, @Cast("long") int Property,
                @Cast("long") int lValue, @Cast("long") int Flags/*=0*/, @Cast("bool") boolean useDefaultValue/*=false*/);

        /**
         *
         * @param deviceID
         * @param Property
         * @param pctValue
         * @param Flags
         * @return
         */
        public native boolean setVideoSettingCameraPct(int deviceID, @Cast("long") int Property,
                float pctValue, @Cast("long") int Flags/*=0*/);

        /**
         *
         * @param deviceID
         * @param Property
         * @param min
         * @param max
         * @param SteppingDelta
         * @param currentValue
         * @param flags
         * @param defaultValue
         * @return
         */
        public native boolean getVideoSettingCamera(int deviceID, @Cast("long") int Property,
                @ByRef @Cast("long*") int[] min, @ByRef @Cast("long*") int[] max,
                @ByRef @Cast("long*") int[] SteppingDelta, @ByRef @Cast("long*") int[] currentValue,
                @ByRef @Cast("long*") int[] flags, @ByRef @Cast("long*") int[] defaultValue);

//        public native boolean setVideoSettingCam(int deviceID, @Cast("long") int Property,
//                @Cast("long") int lValue, @Cast("long") int Flags/*=0*/, @Cast("bool") boolean useDefaultValue/*=false*/);

        /**
         *
         * @param deviceID
         * @return
         */
        
        public native int  getWidth(int deviceID);

        /**
         *
         * @param deviceID
         * @return
         */
        public native int  getHeight(int deviceID);

        /**
         *
         * @param deviceID
         * @return
         */
        public native int  getSize(int deviceID);

        /**
         *
         * @param deviceID
         */
        public native void stopDevice(int deviceID);

        /**
         *
         * @param deviceID
         * @return
         */
        public native boolean restartDevice(int deviceID);

        /**
         *
         * @return
         */
        public native int  devicesFound();

        /**
         *
         * @param devicesFound
         * @return
         */
        public native videoInput devicesFound(int devicesFound);

        /**
         *
         * @return
         */
        public native @Cast("long") int propBrightness();

        /**
         *
         * @param propBrightness
         * @return
         */
        public native videoInput propBrightness(int propBrightness);

        /**
         *
         * @return
         */
        public native @Cast("long") int propContrast();

        /**
         *
         * @param propContrast
         * @return
         */
        public native videoInput propContrast(int propContrast);

        /**
         *
         * @return
         */
        public native @Cast("long") int propHue();

        /**
         *
         * @param propHue
         * @return
         */
        public native videoInput propHue(int propHue);

        /**
         *
         * @return
         */
        public native @Cast("long") int propSaturation();

        /**
         *
         * @param propSaturation
         * @return
         */
        public native videoInput propSaturation(int propSaturation);

        /**
         *
         * @return
         */
        public native @Cast("long") int propSharpness();

        /**
         *
         * @param propSharpness
         * @return
         */
        public native videoInput propSharpness(int propSharpness);

        /**
         *
         * @return
         */
        public native @Cast("long") int propGamma();

        /**
         *
         * @param propGamma
         * @return
         */
        public native videoInput propGamma(int propGamma);

        /**
         *
         * @return
         */
        public native @Cast("long") int propColorEnable();

        /**
         *
         * @param propColorEnable
         * @return
         */
        public native videoInput propColorEnable(int propColorEnable);

        /**
         *
         * @return
         */
        public native @Cast("long") int propWhiteBalance();

        /**
         *
         * @param propWhiteBalance
         * @return
         */
        public native videoInput propWhiteBalance(int propWhiteBalance);

        /**
         *
         * @return
         */
        public native @Cast("long") int propBacklightCompensation();

        /**
         *
         * @param propBacklightCompensation
         * @return
         */
        public native videoInput propBacklightCompensation(int propBacklightCompensation);

        /**
         *
         * @return
         */
        public native @Cast("long") int propGain();

        /**
         *
         * @param propGain
         * @return
         */
        public native videoInput propGain(int propGain);

        /**
         *
         * @return
         */
        public native @Cast("long") int propPan();

        /**
         *
         * @param propPan
         * @return
         */
        public native videoInput propPan(int propPan);

        /**
         *
         * @return
         */
        public native @Cast("long") int propTilt();

        /**
         *
         * @param propTilt
         * @return
         */
        public native videoInput propTilt(int propTilt);

        /**
         *
         * @return
         */
        public native @Cast("long") int propRoll();

        /**
         *
         * @param propRoll
         * @return
         */
        public native videoInput propRoll(int propRoll);

        /**
         *
         * @return
         */
        public native @Cast("long") int propZoom();

        /**
         *
         * @param propZoom
         * @return
         */
        public native videoInput propZoom(int propZoom);

        /**
         *
         * @return
         */
        public native @Cast("long") int propExposure();

        /**
         *
         * @param propExposure
         * @return
         */
        public native videoInput propExposure(int propExposure);

        /**
         *
         * @return
         */
        public native @Cast("long") int propIris();

        /**
         *
         * @param propIris
         * @return
         */
        public native videoInput propIris(int propIris);

        /**
         *
         * @return
         */
        public native @Cast("long") int propFocus();

        /**
         *
         * @param propFocus
         * @return
         */
        public native videoInput propFocus(int propFocus);
    }
}
