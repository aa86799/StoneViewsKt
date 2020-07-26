#include <jni.h>
#include <string>
#include <malloc.h>
#include <android/bitmap.h>
#include <jpeglib.h>
#include "turbojpeg.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_stone_myapplicationasdf_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

void write_JPEG_file(uint8_t *data, int w, int h, jint q, const char *path) {
//    3.1、创建jpeg压缩对象
    jpeg_compress_struct jcs;
    //错误回调
    jpeg_error_mgr error;
    jcs.err = jpeg_std_error(&error);
    //创建压缩对象
    jpeg_create_compress(&jcs);
//    3.2、指定存储文件  write binary
    FILE *f = fopen(path, "wb");
    jpeg_stdio_dest(&jcs, f);
//    3.3、设置压缩参数
    jcs.image_width = w;
    jcs.image_height = h;
    //bgr
    jcs.input_components = 3;
    jcs.in_color_space = JCS_RGB;
    jpeg_set_defaults(&jcs);
    //开启哈夫曼功能
    jcs.optimize_coding = true;
    jpeg_set_quality(&jcs, q, 1);
//    3.4、开始压缩
    jpeg_start_compress(&jcs, 1);
//    3.5、循环写入每一行数据
    int row_stride = w * 3;//一行的字节数
    JSAMPROW row[1];
    while (jcs.next_scanline < jcs.image_height) {
        //取一行数据
        uint8_t *pixels = data + jcs.next_scanline * row_stride;
        row[0]=pixels;
        jpeg_write_scanlines(&jcs,row,1);
    }
//    3.6、压缩完成
    jpeg_finish_compress(&jcs);
//    3.7、释放jpeg对象
    fclose(f);
    jpeg_destroy_compress(&jcs);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_stone_stoneviewskt_ui_libjpeg_LibJpegFragment_compressJpeg(JNIEnv *env, jobject thiz,
                                                                    jobject bitmap, jint quality,
                                                                    jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);

    AndroidBitmapInfo info; //初始化 info
    AndroidBitmap_getInfo(env, bitmap, &info); //获取信息到  info
    uint8_t *pixels; //无符号字符型
    AndroidBitmap_lockPixels(env, bitmap, (void **)&pixels);//锁定位图像素
    auto w = info.width;
    auto h = info.height;
    uint8_t *data = static_cast<uint8_t *>(malloc(w * h * 3)); //动态开空间
    uint8_t *temp = data;
    uint8_t r, g, b;
    int color;
    for (int i = 0; i < w; ++i) {
        for (int j = 0; j < h; ++j) {
            color = *(int *) pixels; //从pixels 读取
            r = (color >> 16) & 0xFF;
            g = (color >> 8) & 0xFF;
            b = color & 0xFF;
            *data = b;
            *(1 + data) = g;
            *(2 + data) = r;
            data += 3; //data跳过3字节

            pixels += 4; //pixels跳过四字节;  原bitmap 每像素是是4字节： argb
        }
    }

    //把得到的新的图片的信息存入一个新文件 中
    write_JPEG_file(temp, w, h, quality, path);

    //释放内存
    free(temp);
    AndroidBitmap_unlockPixels(env, bitmap);
    env->ReleaseStringUTFChars(path_, path);
}