package com.grizzlyfungames.ecommersappcompose

import android.app.Application
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.crossfade
import dagger.hilt.android.HiltAndroidApp
import okio.Path.Companion.toPath

@HiltAndroidApp
class ShopApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val myImageLoader = ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(this, 0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache").path.toPath())
                    .maxSizeBytes(512L * 1024 * 1024)
                    .build()
            }
            .components {
                add(OkHttpNetworkFetcherFactory())
            }
            .crossfade(true)
            .build()

        SingletonImageLoader.setSafe { myImageLoader }
    }
}