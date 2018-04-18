# GitRepos
### 0.
Github Repository Project

<br>

### 1.

Key features : 

- Kotlin
- MVVM, Repository Pattern
- AAC(Android Architecture Components)
  - Room
  - LiveData
  - ViewModel
  - PagingLibrary (not used)
- Dagger2
- Retrofit2, Moshi

<br>

### 2.1

#### Room

**0. model**

```kotlin
/**
 * @NonNull !! / @Nullable ? cause error
 * Moshi : @Json(name = "field name in JSON")
 */
@Entity(tableName = "repo")
data class Repo(@Json(name = "id") @PrimaryKey val id: String,
                @Json(name = "name") val name: String,
                @Json(name = "language") val language: String?,
                @Json(name = "description") val description: String?,
                @Json(name = "forks_count") val forks_count: Int,
                @Json(name = "watchers_count") val watchers_count: Int,
                @Json(name = "open_issues_count") val open_issues_count: Int,
                @Json(name = "owner") @Embedded val owner: Owner,
                @Json(name = "pushed_at") var pushed_at: String?,
                @Json(name = "created_at") val created_at: String,
                @Json(name = "updated_at") val updated_at: String?,
                @Json(name = "last_inserted") var last_inserted: Long) {

    data class Owner(@Json(name = "login") val login: String,
                     @Json(name = "avatar_url") val avatar_url: String)

}
```



**1. dao**

```kotlin
/**
 * Advantages
 * 0.
 * Using Dao classes will allow you to abstract the database communication in
 * a more logical layer which will be much easier to mock in tests (compared to running direct
 * sql queries).
 *
 * 1.
 * Room also verifies all of your queries in {@link Dao} classes while the application is being
 * compiled so that if there is a problem in one of the queries, you will be notified instantly.
 */
@Dao
interface RepoDao {
    @Query("SELECT * FROM repo WHERE upper(login) = upper(:name)")
    fun selectByName(name: String): LiveData<List<Repo>>

  	/**
     * OnConflictStrategy.REPLACE : replace the old data and continue the transaction.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repos: List<Repo>)
}

// abstract class
@Dao
abstract class RepoDao {
  	@Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(repos: List<Repo>)
  
  	@Query("SELECT * FROM repo WHERE upper(login) = upper(:name)")
    abstract fun selectByName(name: String) : LiveData<List<Repo>>;
}
```



**2. database**

```kotlin
/** 
 * should be an abstract class and extend RoomDatabase
 */
@Database(entities = [(Repo::class)], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun repoDao(): RepoDao
}
```



**3. di**

```kotlin
/**
 * Allows Room to destructively recreate database tables if {@link Migration}s that would
 * migrate old database schemas to the latest schema version are not found.
 * 
 * Note that this will delete all of the data in the database tables managed by Room.
 */
@Singleton @Provides
fun provideDB(app: Application): AppDatabase = Room
        .databaseBuilder(app, AppDatabase::class.java, "github.db")
        .fallbackToDestructiveMigration()
        .build()
```

