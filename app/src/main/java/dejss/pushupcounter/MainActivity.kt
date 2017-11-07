package dejss.pushupcounter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dejss.pushupcounter.DataBase.PushOperations
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Dejss on 06.11.2017.
 */
class MainActivity : AppCompatActivity() {

    var push_count = 0
    private val operations = PushOperations(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GUIaction()
        restoreInfo()

    }

    private fun GUIaction(){
        subtract_one.setOnClickListener({ changeProgress(-1) })
        subtract_ten.setOnClickListener({ changeProgress(-10) })
        add_one.setOnClickListener({ changeProgress(1) })
        add_ten.setOnClickListener({ changeProgress(10) })
    }

    private fun restoreInfo(){
        val day = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date())

        //check for null, if true - break
        val push_ups = operations.readDay(day) ?: return

        push_count = push_ups.count

        push_up_goal.progress=push_count

        PushUpsCurr.text = push_ups.count.toString()
        PushUpsGoal.text = push_ups.goal.toString()

        calcProgress()

    }

    private fun calcProgress(){
        val list: ArrayList<PushUp> = operations.readListDay()

        var goal = 0
        var count = 0

        for(i in 0 until list.size){
            goal += list[i].goal
            count += list[i].count
        }

        var pre_cen = (count*100)/goal

        FullPrgrssNum.text = "${count}/${goal}"
        FullPrgrssPerCent.text = "${pre_cen}%"
    }



    private fun changeProgress(value: Int){
        push_count+=value

        if (push_count < 0){
            push_count = 0
        }

        push_up_goal.progress=push_count
        PushUpsCurr.text = push_count.toString()

        save(push_count)
    }

    private fun save(progress: Int){
        val day = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date())
        operations.saveData(day, progress,100)
    }

}

