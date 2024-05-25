package intro.android.cm_tp_wtd.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import intro.android.cm_tp_wtd.R
import intro.android.cm_tp_wtd.models.Location

class LocationAdapter(
    private val locations: MutableList<Location>,
    private val onRemove: (Location) -> Unit
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = locations[position]
        holder.bind(location)
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val locationNameTextView: TextView = itemView.findViewById(R.id.locationNameTextView)
        private val locationImageView: ImageView = itemView.findViewById(R.id.locationImageView)
        private val removeLocationButton: ImageView = itemView.findViewById(R.id.removeLocationButton)

        fun bind(location: Location) {
            locationNameTextView.text = location.name
            Glide.with(itemView.context).load(location.imageUrl).into(locationImageView)

            removeLocationButton.setOnClickListener {
                onRemove(location)
            }
        }
    }
}
