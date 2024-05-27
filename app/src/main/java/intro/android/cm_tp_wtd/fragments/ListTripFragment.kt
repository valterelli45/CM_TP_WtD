package intro.android.cm_tp_wtd.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import intro.android.cm_tp_wtd.MainActivity
import intro.android.cm_tp_wtd.R
import intro.android.cm_tp_wtd.adapters.ListViewAdapter
import intro.android.cm_tp_wtd.models.Location
import intro.android.cm_tp_wtd.models.Trip

class ListTripFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private val db by lazy { (activity as MainActivity).db }
    private lateinit var tripAdapter: ListViewAdapter
    private val tripsList = mutableListOf<Trip>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listtrip, container, false)
        auth = FirebaseAuth.getInstance()

        tripAdapter = ListViewAdapter(tripsList, { trip ->
            tripsList.remove(trip)
            tripAdapter.notifyDataSetChanged()
        }, childFragmentManager)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewTrips)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = tripAdapter

        val backIcon = view.findViewById<ImageView>(R.id.backIcon)
        backIcon.setOnClickListener {
            findNavController().navigate(R.id.action_listTripFragment_to_dashboardFragment)
        }

        loadTrips()

        return view
    }

    private fun loadTrips() {
        val uid = auth.currentUser?.uid
        uid?.let {
            db.collection("trips").document(it).collection("trips").get()
                .addOnSuccessListener { result ->
                    tripsList.clear()
                    for (document in result) {
                        val tripName = document.getString("name") ?: ""
                        val tripDate = document.getString("date") ?: ""
                        val tripRating = document.getDouble("rating")?.toFloat() ?: 0f
                        val tripId = document.id

                        // Buscar a primeira imagem da primeira localização
                        val locations = document.get("locations") as? List<Map<String, Any>>
                        val imageUrl = if (locations != null && locations.isNotEmpty()) {
                            val firstLocation = Location.fromMap(locations[0])
                            firstLocation.imageUrl
                        } else {
                            "" // URL de imagem padrão ou vazia
                        }

                        val trip = Trip(tripId, imageUrl, tripName, tripDate, tripRating)
                        tripsList.add(trip)
                    }
                    tripAdapter.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting trip documents", exception)
                    Toast.makeText(requireContext(), "Erro ao carregar as viagens.", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
