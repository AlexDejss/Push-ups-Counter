package dejss.pushupcounter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Dejss on 06.11.2017.
 */
class MainActivity : AppCompatActivity() {

    var push_count: Int = 0
    private val operations = PushOperator(this)

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
        curr_pushes_count.text = push_count.toString()
    }

    private fun changeProgress(value: Int){
        push_count+=value
        if (push_count < 0){
            push_count = 0
        }
        push_up_goal.progress=push_count
        curr_pushes_count.text = push_count.toString()

        save(push_count)
    }

    private fun save(progress: Int){
        val day = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date())
        operations.saveData(day, progress)
    }
}





/*

val pushUps = ArrayList<PushUp>()

pushUps.add(PushUp(13, "11.11.2012"))
pushUps.add(PushUp(123, "12.11.2012"))
pushUps.add(PushUp(122, "14.11.2012"))

RecyclerView recyclerView = findViewById(R.id.push_up_content);

PushUpAdapter pushUpAdapterJ = new PushUpAdapter(pushUps);

recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

recyclerView.setAdapter(pushUpAdapterJ);*/