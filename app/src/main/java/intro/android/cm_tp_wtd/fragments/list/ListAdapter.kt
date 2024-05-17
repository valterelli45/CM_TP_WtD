package intro.android.cm_tp_wtd.fragments.list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import intro.android.cm_tp_wtd.R
import intro.android.cm_tp_wtd.data.entities.User

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var usersList = emptyList<User>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = usersList[position]
        holder.itemView.findViewById<TextView>(R.id.user_txt).text = currentItem.name

        if(position%2 == 0)
            holder.itemView.findViewById<ConstraintLayout>(R.id.rowLayout).setBackgroundColor(Color.parseColor("#d6d4e0"))
        else
            holder.itemView.findViewById<ConstraintLayout>(R.id.rowLayout).setBackgroundColor(Color.parseColor("#b8a9c9"))

        holder.itemView.findViewById<ConstraintLayout>(R.id.rowLayout).setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(user: List<User>){
        this.usersList = user
        notifyDataSetChanged()
    }
}