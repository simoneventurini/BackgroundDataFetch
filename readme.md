# BackgroundDataFetch
Simple kotlin module for Android to fetch data on your Activity or Fragment

## Getting started

### Dependency

```
maven {
   url "https://jitpack.io"
}

dependencies {
    compile 'com.github.simoneventurini:BackgroundDataFetch:1.0.0'
}
```

### Usage
Define your async operation extending the BaseWorkRunnable Class and defining the result you want.
```
class FetchUserData: BaseWorkRunnable<String>() {

    override fun execute(context: Context?, result: WorkResult<String>) {
        result.value = "hello"
    }
}
```
Execute the operation inside an Activity or a Fragment
```
class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "YOUR_TAG"
    }

    private lateinit var helper: WorkHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        helper = WorkHelper(this@MainActivity)
        helper.setNThreads(2) //optional, default value = 4
        helper.execute(FetchUserData(), TAG, Callback<String> {
                    result -> Log.d(TAG, "TAG= " + result.tag + " Result=" + result.value)
                })
    }

    override fun onResume() {
        super.onResume()
        helper.onResume()
    }

    override fun onPause() {
        super.onPause()
        helper.onPause()
    }
}
```