package dejss.pushupcounter.PushUpsProgress

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import dejss.pushupcounter.DataBase.PushOperations
import dejss.pushupcounter.MainActivity
import dejss.pushupcounter.R
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.line_statistics.*

class Details : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        GUIload()
    }

    private fun GUIload(){
        val bundle = intent.extras

        if(bundle!=null) {
            FullPrgrssNum.text = "${bundle.get("FullProgress")}"
            FullPrgrssPerCent.text = "${bundle.get("FullProgressPerCent")}"
        }

        ActionButton.text = getString(R.string.return_to_main)
        ActionButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        TrainList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        TrainList.adapter = PushUpAdapter(PushOperations(this).readListDay())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ActionButton.callOnClick()
    }
}
