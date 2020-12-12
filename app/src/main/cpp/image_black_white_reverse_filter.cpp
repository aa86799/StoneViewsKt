//
// Created by austin stone on 2020/12/4.
//

#include <jni.h>
#include <string>
#include <android/bitmap.h>
#include <android/log.h>

#define slog __android_log_print
#define sloge(...) __android_log_print(ANDROID_LOG_ERROR, "stone.stone", __VA_ARGS__)

#define MAKE_RGB565(r, g, b) ((((r) >> 3) << 11) | (((g) >> 2) << 5) | ((b) >> 3))
#define MAKE_ARGB(a, r, g, b) ((a&0xff)<<24) | ((r&0xff)<<16) | ((g&0xff)<<8) | (b&0xff)

#define RGB565_R(p) ((((p) & 0xF800) >> 11) << 3)
#define RGB565_G(p) ((((p) & 0x7E0 ) >> 5) << 2)
#define RGB565_B(p) ( ((p) & 0x1F )    << 3)

#define RGB8888_A(p) (p & (0xff<<24) >> 24 )
#define RGB8888_R(p) (p & (0xff<<16) >> 16 )
#define RGB8888_G(p) (p & (0xff<<8) >> 8 )
#define RGB8888_B(p) (p & (0xff) )

extern "C"
JNIEXPORT void JNICALL
Java_com_stone_stoneviewskt_ui_imagefilter_ImageBlackWhiteReverseFilter_blackWhiteReverseFilter(JNIEnv *env,
                                                                              jobject thiz,
                                                                              jobject bitmap) {
    if (bitmap == NULL) {
        sloge("bitmap is null\n");
        return;
    }

    AndroidBitmapInfo bitmapInfo;
    memset(&bitmapInfo, 0, sizeof(bitmapInfo));
    // Need add "jnigraphics" into target_link_libraries in CMakeLists.txt
    AndroidBitmap_getInfo(env, bitmap, &bitmapInfo);

    // Lock the bitmap to get the buffer
    void *pixels = nullptr;
    int res = AndroidBitmap_lockPixels(env, bitmap, &pixels);
    if (JNI_OK == res) {
//        sloge("AndroidBitmap_lockPixels success\n");
    }

    // From top to bottom
    for (int y = 0; y < bitmapInfo.height; ++y) {
        // From left to right
        for (int x = 0; x < bitmapInfo.width; ++x) {
            int a, r, g, b;
            void *pixel = nullptr;
            // Get each pixel by format

            int ratio = 128;
            if (bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
                pixel = ((uint32_t *) pixels) + y * bitmapInfo.width + x;
                uint32_t v = *((uint32_t *) pixel);
                r = RGB8888_R(v);
                g = RGB8888_G(v);
                b = RGB8888_B(v);
                a = RGB8888_A(v); //add
                int sum = r + g + b;
//                *((uint32_t *) pixel) = MAKE_ARGB(0x1f, sum / ratio, sum / ratio, sum / ratio);
                *((uint32_t *) pixel) = MAKE_ARGB(a, sum / ratio, sum / ratio, sum / ratio);
            } else if (bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGB_565) {
                pixel = ((uint16_t *) pixels) + y * bitmapInfo.width + x;
                uint16_t v = *((uint16_t *) pixel);
                r = RGB565_R(v);
                g = RGB565_G(v);
                b = RGB565_B(v);
                int sum = r + g + b;
                *((uint16_t *) pixel) = MAKE_RGB565(sum / ratio, sum / ratio, sum / ratio);
            }
        }
    }

    AndroidBitmap_unlockPixels(env, bitmap);

}
