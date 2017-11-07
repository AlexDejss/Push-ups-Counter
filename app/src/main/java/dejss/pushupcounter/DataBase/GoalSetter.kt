package dejss.pushupcounter.DataBase

import android.app.Dialog
import android.os.Bundle
import dejss.pushupcounter.MainActivity
import dejss.pushupcounter.R
import kotlinx.android.synthetic.main.dlg_select_goal.*

/**
 * Created by Dejss on 07.11.2017.
 */
class GoalSetter(private val goal: Int,private val activity: MainActivity) : Dialog(activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dlg_select_goal)
        GoalTimes.setText(goal.toString())
        SaveValue.setOnClickListener { saveValue() }
        CancelDlg.setOnClickListener { dismiss() }
    }

    private fun saveValue(){
        activity.changeTodayGoal(GoalTimes.text.toString().toInt())
        dismiss()
    }

}