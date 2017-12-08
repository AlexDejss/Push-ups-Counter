package dejss.pushupcounter

/**
 * Created by Dejss on 05.11.2017.
 */
class TrainPref(val date: String, val count: Int, val goal: Int){
    override fun toString(): String {
        return "$date $count $goal"
    }
}
