package intro.android.cm_tp_wtd.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import intro.android.cm_tp_wtd.R
import intro.android.cm_tp_wtd.models.Dashboard

class DashboardAdapter(
    private val dashboard: MutableList<Dashboard>,
    private val onRemove: (Dashboard) -> Unit
) : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dashboard, parent, false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val dashboard = dashboard[position]
        holder.bind(dashboard)
    }

    override fun getItemCount(): Int {
        return dashboard.size
    }

    inner class DashboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tripImageView: ImageView = itemView.findViewById(R.id.tripImage)
        private val tripTitleTextView: TextView = itemView.findViewById(R.id.tvTitle)
        private val tripDateTextView: TextView = itemView.findViewById(R.id.tvDate)
        private val tripRatingTextView: TextView = itemView.findViewById(R.id.tvRating)
        private val createdByIcon: ImageView = itemView.findViewById(R.id.createdByIcon)
        private val createdByTextView: TextView = itemView.findViewById(R.id.tvCreatedBy)

        fun bind(dashboard: Dashboard) {
            tripTitleTextView.text = dashboard.name ?: ""
            tripDateTextView.text = dashboard.date ?: ""
            tripRatingTextView.text = dashboard.classification?.toString() ?: ""
            createdByTextView.text = dashboard.username ?: ""

            Glide.with(itemView.context).load(dashboard.imageUrl).into(tripImageView)

            // Add remove functionality if needed
        }
    }
}
