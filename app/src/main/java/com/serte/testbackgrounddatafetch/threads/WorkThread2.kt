package technogym.testmultithread

import android.content.Context
import com.serte.backgroundfetch.BaseWorkRunnable
import com.serte.backgroundfetch.Status
import com.serte.backgroundfetch.StatusType
import com.serte.backgroundfetch.WorkResult

/**
 * Created by sventurini on 21/01/2018.
 */
class WorkThread2 : BaseWorkRunnable<Int>() {

    override fun execute(context: Context?, result: WorkResult<Int>) {
        result.value = 2
        result.status.message = ""
        result.status.type = StatusType.NO_ERRORS

        Thread.sleep(4000)
    }
}