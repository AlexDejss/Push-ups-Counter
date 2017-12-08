package dejss.pushupcounter.PushUpsProgress

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import dejss.pushupcounter.TrainPref
import dejss.pushupcounter.R

/**
 * Created by Dejss on 05.11.2017.
 */
class PushUpAdapter(var pushups: ArrayList<TrainPref>) : RecyclerView.Adapter<PushUpAdapter.PushUpHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PushUpHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.line_push_up, parent, false)
        return PushUpHolder(view)
    }

    override fun onBindViewHolder(holder: PushUpHolder, position: Int) {
        holder.date.text = pushups[position].date
        holder.count.text = pushups[position].count.toString()
    }

    override fun getItemCount(): Int {
        return pushups.size
    }

    class PushUpHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView = view.findViewById(R.id.line_date)
        var count: TextView = view.findViewById(R.id.line_count)

    }
}