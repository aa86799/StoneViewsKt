#include <jni.h>
#include <string>
#include <android/bitmap.h>
#include <android/log.h>

#define sloge(...) __android_log_print(ANDROID_LOG_ERROR, "stone.stone", __VA_ARGS__)

uint32_t find_closest_palette_color(int value) {
    if (value < 128) {
        return 0xFF000000;
    }
    return 0xFFFFFFFF;
}

/*
 * from: https://github.com/jeffreyliu8/Native-Floyd-Steinberg-Dithering
 * 静态二维数组，无法释放内存，会较快造成 栈溢出，至OOM；
 * 改成动态申请二维数组，使用完，释放内存。
 */
void floydSteinberg1(AndroidBitmapInfo *info, void *pixels) {
    uint32_t *pixelLine;
    void *p = pixels;
    int w = info->width;
    int h = info->height;
//    uint32_t ary[h][w];
    uint32_t **ary = new uint32_t *[h];
    for (int y = 0; y < h; ++y) {
        ary[y] = new uint32_t[w];
        pixelLine = (uint32_t *) p;
        for (int x = 0; x < w; ++x) {
            //extract the RGB values from the pixel
            ary[y][x] = 0xFF000000 | (pixelLine[x] & 0x00FFFFFF);
        }
        p = (char *) p + info->stride;
    }

    for (int y = 0; y < h; ++y) {
        pixelLine = (uint32_t *) pixels;
        for (int x = 0; x < w; ++x) {
            uint32_t oldPixel = ary[y][x];
//            uint32_t newPixel = find_closest_palette_color(oldPixel);
            uint32_t newPixel = (int) oldPixel >= 128 ? 0xFFFFFFFF : 0xFF000000;
            pixelLine[x] = newPixel;

            uint32_t err = oldPixel - newPixel;

            if (x + 1 < w)
                ary[y][x + 1] += (int) (err * (7. / 16));
            if (x > 0 && y + 1 < h)
                ary[y + 1][x - 1] += (int) (err * (3. / 16));
            if (y + 1 < h)
                ary[y + 1][x] += (int) (err * (5. / 16));
            if (x + 1 < w && y + 1 < h)
                ary[y + 1][x + 1] += (int) (err * (1. / 16));
        }
        pixels = (char *) pixels + info->stride;
    }

    for (int y = 0; y < h; ++y) {
        delete ary[y];
    }
    delete[] ary;
}

/*
 * 不使用数组，直接操作指针位移。
 */
void floydSteinberg2(AndroidBitmapInfo *info, void *pixels) {
    int w = info->width;
    int h = info->height;
    int mid = 128;
    uint32_t min = 0xFF000000;
    uint32_t max = 0xFFFFFFFF;
//    uint32_t minWhite = 0xFFFF0000;
    for (int y = 0; y < h; ++y) {
        for (int x = 0; x < w; ++x) {
            uint32_t pixel = *((uint32_t *) pixels + y * w + x);
//            if (pixel >= minWhite && pixel <= max) {//接近白色不处理
//                continue;
//            }
            uint32_t err = (int) pixel >= mid ? pixel - max : pixel - min;
            if (x + 1 < w) {
                *((uint32_t *) pixels + y * w + (x + 1)) += (int) (err * (7. / 16));
            }
            if (x >= 1 && y + 1 < h) {
                *((uint32_t *) pixels + (y + 1) * w + (x - 1)) += (int) (err * (3. / 16));
            }
            if (y + 1 < h) {
                *((uint32_t *) pixels + (y + 1) * w + x) += (int) (err * (5. / 16));
            }
            if (x + 1 < w && y + 1 < h) {
                *((uint32_t *) pixels + (y + 1) * w + (x + 1)) += (int) (err * (1. / 16));
            }
            pixel = (int) pixel >= mid ? max : min;
            *((uint32_t *) pixels + y * w + x) = pixel;
        }
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_stone_stoneviewskt_ui_imagefilter_filters_ImageFloydFilter_floydFilter(JNIEnv *env,
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

    floydSteinberg1(&bitmapInfo, pixels);
//    floydSteinberg2(&bitmapInfo, pixels);

    AndroidBitmap_unlockPixels(env, bitmap);
}
