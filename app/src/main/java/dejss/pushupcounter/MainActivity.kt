package dejss.pushupcounter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dejss.pushupcounter.DataBase.GoalSetter
import dejss.pushupcounter.DataBase.TrainDBOperations
import dejss.pushupcounter.PushUpsProgress.Details
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.line_statistics.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Dejss on 06.11.2017.
 */
class MainActivity : AppCompatActivity() {

    private val waitTimeToSave = 5000L
    private var trainProgress = 0
    private var timeTillSave = 0L
    private var changeThread = Thread{ timerBeforeSave()}

    private val operations = TrainDBOperations(this)

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GUIaction()
        receiveInfo()
    }

    @Override
    override fun onStop() {
        super.onStop()
        if(changeThread.isAlive){
            timeTillSave = waitTimeToSave
        }
    }

    fun changeGoal(newGoal: Int){
        val storage = getSharedPreferences(Train.TRAIN, Context.MODE_PRIVATE)
        storage.edit().putInt(Train.GOAL, newGoal).apply()

        TrainProgressBarView.max = newGoal
        TrainGoalView.text = newGoal.toString()

        saveToBase()
    }

    private fun GUIaction(){
        SubtractOne.setOnClickListener{ changeProgress(-1) }
        SubtractTen.setOnClickListener{ changeProgress(-10) }
        AddOne.setOnClickListener{ changeProgress(1) }
        AddTen.setOnClickListener{ changeProgress(10) }

        TrainGoalView.setOnClickListener{GoalSetter(TrainGoalView.text.toString().toInt(),this).show()}

        ActionButton.text = getString(R.string.details)

        ActionButton.setOnClickListener {
            val detailsActivity = Intent(this, Details::class.java)
            detailsActivity.putExtra(Train.COUNT, TrainProgressBarView.progress)
            detailsActivity.putExtra(Train.GOAL, TrainProgressBarView.max)
            startActivity(detailsActivity)
            overridePendingTransition(R.anim.slide_from_right, R.anim.hold)
        }
    }

    //===========================================
    //      Restore info from base and SP
    //===========================================

    private fun receiveInfo(){
        var savedGoal = getSharedPreferences(Train.TRAIN, Context.MODE_PRIVATE).getInt(Train.GOAL,-1)

        if(savedGoal == -1) savedGoal = 200

        if(intent.extras != null){

            var loadedGoal = intent.extras.getInt(Train.GOAL)
            if(loadedGoal != 0)
                savedGoal = loadedGoal
            trainProgress = intent.extras.getInt(Train.COUNT)
        }

        TrainProgressView.text = trainProgress.toString()
        TrainGoalView.text = savedGoal.toString()
        TrainProgressBarView.max = savedGoal
        TrainProgressBarView.progress = trainProgress

    }

    //=========================================
    //      Change the push ups progress
    //=========================================

    private fun changeProgress(value: Int){
        trainProgress += value

        TrainProgressBarView.progress = trainProgress
        TrainProgressView.text = trainProgress.toString()

        startTimeToSave()
    }

    private fun saveToBase(){
        val currentDay = SimpleDateFormat(Train.DATE_PATTERN, Locale.US).format(Date())
        operations.saveDay(currentDay, trainProgress, TrainGoalView.text.toString().toInt())
    }

    //===============================
    //      Thread work show
    //===============================

    private fun startTimeToSave() {
        timeTillSave = 0
        if (!changeThread.isAlive) {
            changeThread.start()
        }
    }

    private fun timerBeforeSave(){
        val step = 100L
        while(timeTillSave < waitTimeToSave){
            Thread.sleep(100)
            timeTillSave+=step
        }
        runOnUiThread {
            saveToBase()
        }
    }

}

