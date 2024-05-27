package intro.android.cm_tp_wtd.adapters

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import intro.android.cm_tp_wtd.R
import intro.android.cm_tp_wtd.fragments.EditTripFragment
import intro.android.cm_tp_wtd.models.Trip

class ListViewAdapter(
    private val trips: MutableList<Trip>,
    private val onRemove: (Trip) -> Unit,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<ListViewAdapter.TripViewHolder>() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

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
        private val titleTextView: TextView = itemView.findViewById(R.id.tripTitle)
        private val dateTextView: TextView = itemView.findViewById(R.id.tripDate)
        private val ratingTripView: TextView = itemView.findViewById(R.id.tripRating)
        private val removeTripButton: ImageView = itemView.findViewById(R.id.removeTripButton)
        private val editTripButton: ImageView = itemView.findViewById(R.id.editTripButton)

        fun bind(trip: Trip) {
            Glide.with(itemView.context).load(trip.imageUrl).into(tripImageView)
            titleTextView.text = trip.title
            dateTextView.text = trip.date
            ratingTripView.text = trip.rating.toString()

            removeTripButton.setOnClickListener {
                onRemove(trip)
                removeTrip(trip)
            }

            editTripButton.setOnClickListener {
                val dialog = EditTripFragment.newInstance(trip.id)
                dialog.show(fragmentManager, "EditTripFragment")
            }
        }

        private fun removeTrip(trip: Trip) {
            auth = FirebaseAuth.getInstance()
            val user = auth.currentUser
            val uid = user?.uid
            uid?.let {
                db.collection("trips").document(it).collection("trips").document(trip.id)
                    .delete()
                    .addOnSuccessListener {
                        Log.d(TAG, "Trip document successfully deleted!")
                        trips.remove(trip)
                        notifyDataSetChanged()
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error deleting trip document", e)
                    }
            }
        }
    }
}
