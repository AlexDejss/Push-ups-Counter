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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        subtract_one.setOnClickListener({ changeProgress(-1) })
        subtract_ten.setOnClickListener({ changeProgress(-10) })
        add_one.setOnClickListener({ changeProgress(1) })
        add_ten.setOnClickListener({ changeProgress(10) })

        val day = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date())
        Log.v("Current day", day)
        /*

        val pushUps = ArrayList<PushUp>()

        pushUps.add(PushUp(13, "11.11.2012"))
        pushUps.add(PushUp(123, "12.11.2012"))
        pushUps.add(PushUp(122, "14.11.2012"))

        RecyclerView recyclerView = findViewById(R.id.push_up_content);

        PushUpAdapter pushUpAdapterJ = new PushUpAdapter(pushUps);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        recyclerView.setAdapter(pushUpAdapterJ);*/
    }

    private fun changeProgress(value: Int){
        push_count+=value
        if (push_count < 0){
            push_count = 0
        }
        push_up_goal.progress=push_count
        curr_pushes_count.text = push_count.toString()
    }
}