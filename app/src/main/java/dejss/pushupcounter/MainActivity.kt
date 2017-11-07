package dejss.pushupcounter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import dejss.pushupcounter.DataBase.GoalSetter
import dejss.pushupcounter.DataBase.PushOperations
import dejss.pushupcounter.PushUpsProgress.Details
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.line_statistics.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Dejss on 06.11.2017.
 */
class MainActivity : AppCompatActivity() {

    private var push_count = 0
    private var statPushGoal = 0
    private var statPushCount = 0

    private val operations = PushOperations(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GUIaction()
        restoreInfo()

    }

    private fun GUIaction(){
        SubtractOne.setOnClickListener{ changeProgress(-1) }
        SubtractTen.setOnClickListener{ changeProgress(-10) }
        AddOne.setOnClickListener{ changeProgress(1) }
        AddTen.setOnClickListener{ changeProgress(10) }

        PushUpsGoal.setOnClickListener{GoalSetter(PushUpsGoal.text.toString().toInt(),this).show()}

        ActionButton.text = getString(R.string.see_details)

        ActionButton.setOnClickListener {
            val fullPrgrss = FullPrgrssNum.text
            val fullPrgrssPerCent = FullPrgrssPerCent.text

            val detailsActivity = Intent(this, Details::class.java)

            detailsActivity.putExtra("FullProgress", fullPrgrss)
            detailsActivity.putExtra("FullProgressPerCent", fullPrgrssPerCent)

            startActivity(detailsActivity)
            overridePendingTransition(R.anim.slide_from_right, R.anim.hold)
        }
    }

    private fun restoreInfo(){
        val day = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date())
        val storage = getSharedPreferences("push_ups", Context.MODE_PRIVATE)

        var counter = storage.getInt("goal",-1)

        Log.v("MA_goal", counter.toString())
        //if first run
        if(counter == -1){
            counter = 100
            changeTodayGoal(counter)
            PushUpsGoal.text = counter.toString()
            save()
        }
        else {
            //check for null, if true - break
            val push_ups = operations.readDay(day)
            push_count = push_ups!!.count

            push_up_goal.progress = push_count

            PushUpsGoal.text = push_ups.goal.toString()
            PushUpsCurr.text = push_ups.count.toString()
        }
        loadStatistics()


    }

    private fun loadStatistics(){
        val list: ArrayList<PushUp> = operations.readListDay()
        for(i in 0 until list.size){
            statPushGoal += list[i].goal
            statPushCount += list[i].count
        }
        calcTheStatistic()
    }

    private fun calcTheStatistic(){
        var pre_cen = (statPushCount *100)/ statPushGoal
        FullPrgrssNum.text = "${statPushCount}/${statPushGoal}"
        FullPrgrssPerCent.text = "${pre_cen}%"
    }

    private fun changeProgress(value: Int){
        var ini = push_count
        push_count+=value
        if (push_count < 0){
            push_count = 0
        }
        statPushCount +=push_count-ini

        push_up_goal.progress=push_count
        PushUpsCurr.text = push_count.toString()
        save()
    }

    private fun save(){
        val day = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date())
        operations.saveData(day, push_count, PushUpsGoal.text.toString().toInt())
        calcTheStatistic()
    }

    fun changeTodayGoal(newGoal: Int){
        val storage = getSharedPreferences("push_ups", Context.MODE_PRIVATE)
        storage.edit().putInt("goal", newGoal).apply()

        statPushGoal+=(newGoal-PushUpsGoal.text.toString().toInt())

        PushUpsGoal.text = newGoal.toString()
        save()
    }



}

