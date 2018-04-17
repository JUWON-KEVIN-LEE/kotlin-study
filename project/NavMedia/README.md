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
- ExoPlayer2
- ReactiveX Java2 + Android
- Dagger2
- Glide
- Retrofit2

<br>

### 2.1

ExoPlayer2 (Google Media Framework Library)

### init player

```kotlin
private fun initPlayer() {
    val bandwidthMeter = DefaultBandwidthMeter()
    val extractorsFactory = DefaultExtractorsFactory()
    val trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)

    val mediaSourceFactory = DefaultDataSourceFactory(this,
            Util.getUserAgent(this, "NavMedia"),
            bandwidthMeter as TransferListener<in DataSource>)

    val mediaSource = ExtractorMediaSource
            .Factory(mediaSourceFactory)
            .setExtractorsFactory(extractorsFactory)
            .createMediaSource(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))

    playerView.requestFocus()
    trackSelector = DefaultTrackSelector(trackSelectionFactory)

    player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

    playerView.player = player
    player.playWhenReady = autoPlay
    player.prepare(mediaSource)
}
```

### release player

```kotlin
private fun releasePlayer() {
    player.release()
    autoPlay = player.playWhenReady
}
```

### Util

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

### Classes

**BandwidthMeter** : Estimates bandwidth by listening to data transfers.

**ExtractorsFactory** : Provides an array of extractors for the following formats.

![formats](https://github.com/JUWON-KEVIN-LEE/kotlin-study/blob/master/project/NavMedia/images/formats.png)

**AdaptiveTrackSelection** : A bandwidth based adaptive **TrackSelection**, whose selected track is updated to be the one of highest quality given the current network conditions and the state of the buffer.

**DataSourceFactory** : Produces **DataSource** instances that delegate to HttpDataSources for non-file/asset/content URIs.

**ExtractorMediaSource** : Provides one period that loads data from a Uri and extracted using an **Extractor**.
