#include <jni.h>
#include <string>
#include <android/bitmap.h>
#include <android/log.h>

#define sloge(...) __android_log_print(ANDROID_LOG_ERROR, "stone.stone", __VA_ARGS__)

uint32_t find_closest_palette_color2(int value) {
    if (value < 128) {
        return 0xFF000000;
    }
    return 0xFFFFFFFF;
}

void stucki(AndroidBitmapInfo *info, void *pixels) {
    int w = info->width;
    int h = info->height;
    int mid = 128;
    uint32_t max = 0xFF000000;
    uint32_t min = 0xFFFFFFFF;
    for (int y = 0; y < h; ++y) {
        for (int x = 0; x < w; ++x) {
            uint32_t pixel = *((uint32_t *) pixels + y * w + x);
            uint32_t err = (int) pixel >= mid ? pixel - min : pixel - max;
            if (x + 1 < w)
                *((uint32_t *) pixels + y * w + (x + 1)) += (int) (err * (8. / 42));
            if (x + 2 < w)
                *((uint32_t *) pixels + y * w + (x + 2)) += (int) (err * (4. / 42));

            if (x >= 2 && y + 1 < h)
                *((uint32_t *) pixels + (y + 1) * w + (x - 2)) += (int) (err * (2. / 42));
            if (x >= 1 && y + 1 < h)
                *((uint32_t *) pixels + (y + 1) * w + (x - 1)) += (int) (err * (4. / 42));
            if (y + 1 < h)
                *((uint32_t *) pixels + (y + 1) * w + (x)) += (int) (err * (8. / 42));
            if (x + 1 < w && y + 1 < h)
                *((uint32_t *) pixels + (y + 1) * w + (x + 1)) += (int) (err * (4. / 42));
            if (x + 2 < w && y + 1 < h)
                *((uint32_t *) pixels + (y + 1) * w + (x + 2)) += (int) (err * (2. / 42));

            if (x >= 2 && y + 2 < h)
                *((uint32_t *) pixels + (y + 2) * w + (x - 2)) += (int) (err * (1. / 42));
            if (x >= 1 && y + 2 < h)
                *((uint32_t *) pixels + (y + 2) * w + (x - 1)) += (int) (err * (2. / 42));
            if (y + 2 < h)
                *((uint32_t *) pixels + (y + 2) * w + (x)) += (int) (err * (4. / 42));
            if (x + 1 < w && y + 2 < h)
                *((uint32_t *) pixels + (y + 2) * w + (x + 1)) += (int) (err * (2. / 42));
            if (x + 2 < w && y + 2 < h)
                *((uint32_t *) pixels + (y + 2) * w + (x + 2)) += (int) (err * (1. / 42));

            pixel = (int) pixel >= mid ? min : max;
            *((uint32_t *) pixels + y * w + x) = pixel;
        }
    }
}


extern "C"
JNIEXPORT void JNICALL
Java_com_stone_stoneviewskt_ui_imagefilter_filters_ImageStuckiFilter_stuckiFilter(JNIEnv *env,
                                                                                  jobject thiz,
                                                                                  jobject bitmap) {
    if (bitmap == NULL) {
        sloge("bitmap is null\n");
        return;
    }

    AndroidBitmapInfo bitmapInfo;
    memset(&bitmapInfo, 0, sizeof(bitmapInfo));
    // Need add "jnigraphics" into target_link_libraries in CMakeLists.txt
    AndroidBitmap_getInfo(env, bitmap, &bitmapInfo); //获取位图信息

    // Lock the bitmap to get the buffer
    void *pixels = nullptr;
    int res = AndroidBitmap_lockPixels(env, bitmap, &pixels); //锁定位图像素缓冲区
    if (JNI_OK == res) {
//        sloge("AndroidBitmap_lockPixels success\n");
    }

    stucki(&bitmapInfo, pixels);

    AndroidBitmap_unlockPixels(env, bitmap);
}
