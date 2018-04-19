# DI (의존성 주입, Dependency Injection)
### 0. 

Dependency Injection

<br>

### 1.

**Jake Wharton**

```kotlin
/**
 * Tweeter > TweeterApi > OkHttpClient
 */
class TweeterApi {
    private lateinit var client: OkHttpClient

    constructor TweeterApi(client : OkHttpClient) {
        /*
        before
        this.client = OkHttpClient()
        */
        this.client = client
    }

    fun postTweet(String user, String tweet) {
        Request request = // TODO build POST request...
        client.newCall(request).execute()
    }
}

class Tweeter {
    private lateinit var user: String
    private lateinit var api: TweeterApi

    constructor(user: String, api: TweeterApi) {
      	/*
        before
        this.user = "JakeWharton"
        this.api = TweeterApi()
        */
        this.user = user
        this.api = api
    }

    fun tweet(tweet: String) {
        api.postTweet(user, tweet)
    }
}
```

```kotlin
@Module
class TweeterModule(val user: String) {
    @Provides @Singleton
    fun provideTweeter(api: TweeterApi): Tweeter {
        return Twitter(api, user)
    }

    @Provides @Singleton
    ...
}
```



<br>

### 2.

<br>

### 3.

<br>

### 4.