package technogym.testmultithread

import android.content.Context
import com.serte.backgroundfetch.BaseWorkRunnable
import com.serte.backgroundfetch.WorkResult

/**
 * Created by sventurini on 21/01/2018.
 */
class WorkThread3 : BaseWorkRunnable<Int>() {

    override fun execute(context: Context?, result: WorkResult<Int>) {
        result.value = 3
        Thread.sleep(30)
    }
}