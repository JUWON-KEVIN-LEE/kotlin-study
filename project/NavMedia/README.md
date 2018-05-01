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
class MediaSourceFactory constructor(private val context: Context) {

    private val dataSourceFactory: DataSource.Factory
    private val trackSelector: DefaultTrackSelector

    init {
        dataSourceFactory = buildDataSourceFactory(true)

        val adaptiveTrackSelectionFactory = AdaptiveTrackSelection.Factory(BANDWIDTH_METER)
        trackSelector = DefaultTrackSelector(adaptiveTrackSelectionFactory)
    }

    fun getTrackSelector(): DefaultTrackSelector = trackSelector

    /**
     * Return DefaultDataSourceFactory
     */
    private fun buildDataSourceFactory(useBandwidthMeter: Boolean) : DataSource.Factory
            = buildDataSourceFactory(if(useBandwidthMeter) BANDWIDTH_METER else null)

    private fun buildDataSourceFactory(bandwidthMeter: DefaultBandwidthMeter?) : DataSource.Factory
        = DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, applicationName),
                bandwidthMeter as TransferListener<in DataSource>)
        /*
        = DefaultDataSourceFactory(
                context,
                listener,
                DefaultHttpDataSourceFactory(Util.getUserAgent(context, applicationName),
                						listener)
                )
        */

    /**
     * Return MediaSource using streaming or other media type (C.ContentType)
     */
    fun buildMediaSource(uri: Uri, @C.ContentType type: Int) : MediaSource {
        return when(type) {
            C.TYPE_SS -> SsMediaSource
                    .Factory(DefaultSsChunkSource.Factory(dataSourceFactory),
                            buildDataSourceFactory(false))
                    .createMediaSource(uri)
            C.TYPE_DASH -> DashMediaSource
                    .Factory(DefaultDashChunkSource.Factory(dataSourceFactory),
                            buildDataSourceFactory(false))
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

    companion object {
        private const val applicationName: String = "NavMedia"
        private val BANDWIDTH_METER = DefaultBandwidthMeter()
    }
}
```

<br>

### Fullscreen

```java
// scenario 0. 유저가 전체화면 버튼 클릭시
// playback control view
private synchronized void changeOrientation(@OrientationType int orientation) {
        Context context = getContext();

        if(!(context instanceof Activity)) return;

        if(onOrientationChangedListener == null) return;

        switch (orientation) {
            case LANDSCAPE:
                setPortrait(false);
            	/*
                public void setPortrait(boolean portrait) {
                    this.portrait = portrait;
                }
            	*/
                changeUI(false);
                /*
                private void changeUI(boolean portrait) {
                    if(portrait) {
                        enterFullScreenButton.setVisibility(View.VISIBLE);
                        exitFullScreenButton.setVisibility(View.GONE);
                        lockButton.setVisibility(View.GONE);
                        videoListUp.setVisibility(View.GONE);

                        portraitView.setVisibility(View.VISIBLE);
                        landscapeView.setVisibility(View.GONE);
                    } else {
                        enterFullScreenButton.setVisibility(View.GONE);
                        exitFullScreenButton.setVisibility(View.VISIBLE);
                        lockButton.setVisibility(View.VISIBLE);
                        videoListUp.setVisibility(View.VISIBLE);

                        portraitView.setVisibility(View.GONE);
                        landscapeView.setVisibility(View.VISIBLE);
                    }
                }
            	*/
                ((Activity)context)
            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case PORTRAIT:
                setPortrait(true);
                changeUI(true);
                ((Activity)context)
            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                break;
        }

        onOrientationChangedListener.onOrientaionChanged(orientation);
    }

// Scenario 1. Sensor 를 이용해서 Orientation 을 변경했을 시
```

<br>

### Init player

```kotlin
private fun initPlayer(uriString: String) {
    try
    {
        val uri = Uri.parse(uriString)
        val type = Util.inferContentType(uri)
        val mediaSource = mediaSourceFactory.buildMediaSource(uri, type)

        player = ExoPlayerFactory.newSimpleInstance(this, mediaSourceFactory.getTrackSelector())

        playerView.requestFocus()

        playerView.player = player
        player.playWhenReady = autoPlay
        player.prepare(mediaSource)

    } catch (e: Exception) {
        // TODO Error Handling
    }
}
```

```kotlin
override fun onStart() {
    super.onStart()
    if(Util.SDK_INT > 23) initPlayer(uriString)
}

override fun onResume() {
    super.onResume()
    if(Util.SDK_INT <= 23) initPlayer(uriString)
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

<br>

**BandwidthMeter( 대역폭 )** : Estimates bandwidth by listening to data transfers.

**AdaptiveTrackSelection** : A bandwidth based adaptive **TrackSelection**, whose selected track is updated to be the one of highest quality given the current network conditions and the state of the buffer.

<br>

**DataSourceFactory** : Produces **DataSource** instances that delegate to HttpDataSources

​                                        for non-file/asset/content URIs.

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

<br>

### Reference

[ExoPlayer for Application developers](https://pt.slideshare.net/HassanAbid1/exoplayer-for-application-developers/10)