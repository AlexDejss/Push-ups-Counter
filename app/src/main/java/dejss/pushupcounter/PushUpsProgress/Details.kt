package dejss.pushupcounter.PushUpsProgress

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import dejss.pushupcounter.DataBase.TrainDBOperations
import dejss.pushupcounter.MainActivity
import dejss.pushupcounter.R
import dejss.pushupcounter.Train
import dejss.pushupcounter.TrainPref
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.line_statistics.*
import java.text.SimpleDateFormat
import java.util.*

class Details : AppCompatActivity() {

    private var loadList = Thread{ loadProgressList()}
    private var trainGoal = 0
    private var trainProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        loadIntent()
        GUIload()
        loadList.start()
    }

    private fun loadIntent(){
        if(intent.extras != null){
            var loadedGoal = intent.extras.getInt(Train.GOAL)
            if(loadedGoal != 0)
                trainGoal = loadedGoal
            trainProgress = intent.extras.getInt(Train.COUNT)
        }
    }

    private fun GUIload(){
        TopPanelInfoView.text = "Today: $trainProgress/$trainGoal ${(100*trainProgress)/trainGoal}%"

        ActionButton.text = getString(R.string.return_to_main)
        ActionButton.setOnClickListener {
            val returnToMain = Intent(this, MainActivity::class.java)
            returnToMain.putExtra(Train.COUNT, trainProgress)
            returnToMain.putExtra(Train.GOAL, trainGoal)
            startActivity(returnToMain)

            overridePendingTransition(R.anim.hold_appear, R.anim.slide_in_right)
        }

        AllDaysProgressView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun loadProgressList(){
        var wholeProgress = TrainDBOperations(this).readListDay()
        var filteredDays = loadTillThisDay(wholeProgress)
        runOnUiThread {
            AllDaysProgressView.adapter = PushUpAdapter(filteredDays)
            WaitUtilLoadView.visibility = View.INVISIBLE
        }
    }

    private fun loadTillThisDay(wholeProgress: ArrayList<TrainPref>): ArrayList<TrainPref>{
        val df = SimpleDateFormat(Train.DATE_PATTERN)
        val currentDay = SimpleDateFormat(Train.DATE_PATTERN, Locale.US).format(Date())
        var filteredDays = ArrayList<TrainPref>(wholeProgress.size)

        wholeProgress.filterTo(filteredDays) { df.parse(it.date) <= df.parse(currentDay) }

        return filteredDays
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ActionButton.callOnClick()

    }
}
