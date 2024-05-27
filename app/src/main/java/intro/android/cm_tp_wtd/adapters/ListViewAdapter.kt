package intro.android.cm_tp_wtd.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import intro.android.cm_tp_wtd.R
import intro.android.cm_tp_wtd.models.Trip

class ListViewAdapter(
    private val trips: MutableList<Trip>,
    private val onRemove: (Trip) -> Unit
) : RecyclerView.Adapter<ListViewAdapter.TripViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trip, parent, false)
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = trips[position]
        holder.bind(trip)
    }

    override fun getItemCount(): Int {
        return trips.size
    }

    inner class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tripImageView: ImageView = itemView.findViewById(R.id.tripImage)
        private val titleTextView: TextView = itemView.findViewById(R.id.tvTitle)
        private val dateTextView: TextView = itemView.findViewById(R.id.tvDate)
        private val timeFrameTextView: TextView = itemView.findViewById(R.id.tvRating)
        private val removeTripButton: ImageView = itemView.findViewById(R.id.removeLocationButton)  // Assuming you add a remove button in item_trip.xml

        fun bind(trip: Trip) {

            Glide.with(itemView.context).load(trip.imageUrl).into(tripImageView)
            titleTextView.text = trip.title
            dateTextView.text = trip.date
            timeFrameTextView.text = trip.rating

            removeTripButton.setOnClickListener {
                onRemove(trip)
            }
        }
    }
}
