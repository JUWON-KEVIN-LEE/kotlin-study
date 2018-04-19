# NavMedia
### 0.
Naver TV App 카피 프로젝트

<br>

### 1.

Key features : 

- Kotlin
- MVVM, Repository Pattern
- AAC(Android Architecture Components)

  - Room	


  - LiveData
  - ViewModel
  - PagingLibrary
- **ExoPlayer2**
- ReactiveX Java2 + Android
- Dagger2
- Glide
- Retrofit2

<br>

### 2.1 ExoPlayer2

ExoPlayer2 (Google Media Framework Library)

### Interfaces

- TrackRenderer
  - MediaCodecVideoTrackRenderer
  - MediaCodecAudioTrackRenderer
  - TextTrackRenderer
- SampleSource
  - ExtractorSampleSource( mp4 )
  - ChunkSampleSource( Dash, Smooth Streaming )
  - HlsSampleSource( HLS )
- DataSource
  - DefaultUriDataSource
  - AssetDataSource

...

### MediaSource Factory

```kotlin
class MediaSourceFactory {
    companion object {
        /**
         * Return DefaultDataSourceFactory
         */
        private fun buildDataSourceFactory(context: Context,
                                           useBandwidthMeter: Boolean) : DataSource.Factory {
            return buildDataSourceFactory(context,
                    if(useBandwidthMeter) DefaultBandwidthMeter() else null)
        }

        private fun buildDataSourceFactory(context: Context,
                                bandwidthMeter: DefaultBandwidthMeter?) : DataSource.Factory {
            return DefaultDataSourceFactory(
                    context,
                    Util.getUserAgent(context, "NavMedia"),
                    bandwidthMeter as TransferListener<in DataSource>)
        }

        /**
         * Return MediaSource using streaming or other media type (C.ContentType)
         * In ExoPlayer every piece of media is represented by MediaSource
         */
        fun buildMediaSource(context: Context, uri: Uri) : MediaSource {
            @C.ContentType val type = Util.inferContentType(uri)
            val dataSourceFactory : DataSource.Factory = buildDataSourceFactory(context, false)

            return when(type) {
                C.TYPE_SS -> SsMediaSource
                        .Factory(DefaultSsChunkSource.Factory(dataSourceFactory),
                                 dataSourceFactory)
                        .createMediaSource(uri)
                C.TYPE_DASH -> DashMediaSource
                        .Factory(DefaultDashChunkSource.Factory(dataSourceFactory),
                                 dataSourceFactory)
                        .createMediaSource(uri)
                C.TYPE_HLS -> HlsMediaSource
                        .Factory(dataSourceFactory)
                        .createMediaSource(uri)
                C.TYPE_OTHER -> ExtractorMediaSource
                        .Factory(dataSourceFactory)
                        .createMediaSource(uri)
                else -> throw IllegalStateException("Unsupprted Type : " + type)
            }
        }
    }
}
```

<br>

### PlayerUtil

```kotlin
class PlayerUtil {
    /**
     * Make full size of screen
     */
    fun fullScreen(playerView: PlayerView, fullScreen: Dialog): Boolean {
        (playerView.parent as ViewGroup).removeView(playerView)
        fullScreen.addContentView(playerView,
                            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                   ViewGroup.LayoutParams.MATCH_PARENT))
        fullScreen.show()

        return true // Is this view full size of screen ?
    }

    /**
    * Make original size of screen
    */
    fun closeFullScreen(playerView: PlayerView, fullScreen: Dialog,
                        parent: ViewGroup): Boolean {
        (playerView.parent as ViewGroup).removeView(playerView)
        parent.addView(playerView)
        fullScreen.dismiss()

        return false
    }
}
```

<br>

### Init player

```kotlin
private fun initPlayer(uriString: String) {
    try {
        val bandwidthMeter = DefaultBandwidthMeter()
        val mediaSource = PlayerUtil.buildMediaSource(this,
                    Uri.parse(uriString))

        val trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        trackSelector = DefaultTrackSelector(trackSelectionFactory)
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

        playerView.requestFocus()
        playerView.player = player
        player.playWhenReady = autoPlay
        player.prepare(mediaSource)
    } catch (e: Exception) {
        // TODO Error Handling
        Log.d(TAG, e.toString())
    }
}
```

### Release player

```kotlin
private fun releasePlayer() {
    player.release()
    autoPlay = player.playWhenReady
}
```

```kotlin
override fun onStart() {
    super.onStart()
    if(Util.SDK_INT > 23) initPlayer()
}

override fun onResume() {
    super.onResume()
    if(Util.SDK_INT <= 23) initPlayer()
}

override fun onPause() {
    super.onPause()
    if(Util.SDK_INT <= 23) releasePlayer()
}

override fun onStop() {
    super.onStop()
    if(Util.SDK_INT > 23) releasePlayer()
}
```

<br>

### Others

**Infer Content Type**

```java
// in exoplayer2
@C.ContentType
public static int inferContentType(Uri uri) {
    String path = uri.getPath();
    return path == null ? C.TYPE_OTHER : inferContentType(path);
}

@C.ContentType
public static int inferContentType(String fileName) {
    fileName = Util.toLowerInvariant(fileName);
    if (fileName.endsWith(".mpd")) {
      return C.TYPE_DASH;
    } else if (fileName.endsWith(".m3u8")) {
      return C.TYPE_HLS;
    } else if (fileName.matches(".*\\.ism(l)?(/manifest(\\(.+\\))?)?")) {
      return C.TYPE_SS;
    } else {
      return C.TYPE_OTHER;
    }
}
```

**BandwidthMeter** : Estimates bandwidth by listening to data transfers.

**DataSourceFactory** : Produces **DataSource** instances that delegate to HttpDataSources

​                                            for non-file/asset/content URIs.

**MediaSource** 

```java
// Factory Pattern
public final class SsMediaSource implements MediaSource, ... {
    public static final class Factory implements AdsMediaSource.MediaSourceFactory {
        ...
    }
}

public final class DashMediaSource implements MediaSource {
    public static final class Factory implements AdsMediaSource.MediaSourceFactory {
        ...
    }
}
```

**AdaptiveTrackSelection** : A bandwidth based adaptive **TrackSelection**, whose selected track is updated to be the one of highest quality given the current network conditions and the state of the buffer.



**Reference ** [ExoPlayer for Application developers](https://pt.slideshare.net/HassanAbid1/exoplayer-for-application-developers/10)