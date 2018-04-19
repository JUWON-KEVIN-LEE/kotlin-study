DI (의존성 주입, Dependency Injection)

### 0. 

Dependency Injection

**What is Dependency Injection ?**

Traditionally, we create the instance of dependent class object and do the required operations. 

When applying Dependency Injection, the objects are given their dependencies at creation time by some external entity.

...

dependencies are injected into objects.



It is built upon the concept of **Inversion of Control** 

**what is IoC?** 



**DI Modes**

common ways : 

- Constructor based injection

  ```java
  // Constructor
  Client(Service service) {
      // Save the reference to the passed-in service inside this client
      this.service = service;
  }
  ```

- Setter method

  ```java
  // Setter method
  public void setService(Service service) {
      this.service = service;
  }
  ```

- Interface

  ```java
  // Service setter interface.
  public interface ServiceSetter {
      public void setService(Service service);
  }

  // Client class
  public class Client implements ServiceSetter {
      // Internal reference to the service used by this client.
      private Service service;

      // Set the service that this client is to use.
      @Override
      public void setService(Service service) {
          this.service = service;
      }
  }
  ```

  ​

**Benefits of DI**

- Loose coupling
- Easily Testable code
- Code reusabliity

<br>

### 1.

**Jake Wharton**

**BEFORE**

```kotlin
/**
 * Tweeter > TweeterApi > OkHttpClient
 */
class TweeterApi {
    private lateinit var client: OkHttpClient

    constructor(client : OkHttpClient) {
        /*
        BEFORE
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

class Timeline {
  	private val cache = mutableListOf<Tweet>()
  	private lateinit var api: TweeterApi
  	
  	private lateinit var user: String
  
    constructor(user: String, api: TweeterApi) {
      this.user = user
      this.api = api
    }
  
  	fun get(): mutableListOf<Tweet> {...}
  	fun load(amout: Int) {...}
}
```

```kotlin
val client = OkHttpClient()
val api = TweeterApi(client)
val user = "Jake Wharton"

val tweeter = Tweeter(user, api)
tweeter.tweet("Tweet!")

val timeline = Timeline(user, api)
timeline.load(20)
for...
```



**AFTER**

```kotlin
@Module
class TweeterModule(val user: String) {
    @Provides @Singleton
    fun provideTweeter(api: TweeterApi): Tweeter {
        return Tweeter(user, api)
    }

  	@Provides @Singleton
  	fun provideTweeterApi(okHttpClient: OkHttpClient): TweeterApi {
    	return TweeterApi(okHttpClient)
  	}
  
    @Provides @Singleton
  	fun provideOkHttpClient(): OkHttpClient {
      	return OkhttpClient()
  	}
  
    fun provideTimeline(api: TweeterApi): Timeline {
		return Timeline(user, api)
    }
}
```

![module_0](https://github.com/JUWON-KEVIN-LEE/kotlin-study/blob/master/di/images/module_0.png)

![module_1](https://github.com/JUWON-KEVIN-LEE/kotlin-study/blob/master/di/images/module_1.png)

<br>

### 2.

<br>



#### 출처

[Fundamentals of Dependency Injection and popular libraries in Android](https://android.jlelse.eu/fundamentals-of-dependency-injection-and-popular-libraries-in-android-c17cf48b5253)