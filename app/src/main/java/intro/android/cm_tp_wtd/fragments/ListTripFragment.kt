package intro.android.cm_tp_wtd.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import intro.android.cm_tp_wtd.R
import intro.android.cm_tp_wtd.adapters.ListViewAdapter
import intro.android.cm_tp_wtd.models.Trip

class ListTripFragment : Fragment() {

    private lateinit var tripAdapter: ListViewAdapter
    private val trips = mutableListOf<Trip>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listtrip, container, false)

        val backIcon = view.findViewById<ImageView>(R.id.backIcon)
        backIcon.setOnClickListener {
            findNavController().navigate(R.id.action_listTripFragment_to_dashboardFragment)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTrips)
        recyclerView.layoutManager = LinearLayoutManager(context)

        tripAdapter = ListViewAdapter(trips) { trip ->
            removeTrip(trip)
        }
        recyclerView.adapter = tripAdapter

        loadTrips()

        return view
    }

    private fun loadTrips() {
        // Add some example trips
        trips.add(Trip("drawable/background_image.png", "Trip to Paris", "01.06.2024", "4.5"))
        trips.add(Trip("drawable/background_image.png", "Trip to Rome", "15.04.2024", "4.8"))
        // Notify the adapter that the data has changed
        tripAdapter.notifyDataSetChanged()
    }

    private fun removeTrip(trip: Trip) {
        trips.remove(trip)
        tripAdapter.notifyDataSetChanged()
    }
}
