package intro.android.cm_tp_wtd.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import intro.android.cm_tp_wtd.MainActivity
import intro.android.cm_tp_wtd.R
import intro.android.cm_tp_wtd.adapters.DashboardAdapter
import intro.android.cm_tp_wtd.models.Dashboard
import intro.android.cm_tp_wtd.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private val db by lazy { (activity as MainActivity).db }
    private lateinit var recyclerView: RecyclerView
    private lateinit var dashboardAdapter: DashboardAdapter
    private val dashboardList: MutableList<Dashboard> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        val profileButton = view.findViewById<ImageView>(R.id.profileIcon)
        val addTripButton = view.findViewById<Button>(R.id.addTripButton)
        val listTripButton = view.findViewById<ImageView>(R.id.listTripsIcon)

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewTrips)
        recyclerView.layoutManager = LinearLayoutManager(context)
        dashboardAdapter = DashboardAdapter(dashboardList) { dashboard ->
            // Handle remove action if needed
        }
        recyclerView.adapter = dashboardAdapter

        // Load trips data from API
        loadTrips()

        listTripButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_listTripFragment)
        }

        addTripButton.setOnClickListener {
            val dialog = AddTripFragment()
            dialog.show(childFragmentManager, "AddTripDialogFragment")
        }

        // Load user data
        currentUser?.uid?.let { uid ->
            db.collection("users").document(uid).get().addOnSuccessListener { document ->
                if (document != null) {
                    val profileImageUrl = document.getString("profileImageUrl")
                    if (!profileImageUrl.isNullOrEmpty()) {
                        Glide.with(this)
                            .load(profileImageUrl)
                            .into(profileButton)
                    }
                } else {
                    Toast.makeText(requireContext(), "No user data found", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load user data", Toast.LENGTH_SHORT).show()
            }
        }

        profileButton.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_profileFragment)
        }

        return view
    }

    private fun loadTrips() {
        val call = ApiClient.apiService.getTrips()
        call.enqueue(object : Callback<List<Dashboard>> {
            override fun onResponse(call: Call<List<Dashboard>>, response: Response<List<Dashboard>>) {
                if (response.isSuccessful) {
                    val trips = response.body()
                    if (trips != null) {
                        // Log the content of the list
                        Log.d("DashboardList", "List of Dashboards: $trips")

                        dashboardList.clear()
                        dashboardList.addAll(trips)
                        dashboardAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(requireContext(), "No trips found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to retrieve trips", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Dashboard>>, t: Throwable) {
                Log.e("APIError", "Failed to load trips: ${t.message}", t)
                Toast.makeText(requireContext(), "Failed to load trips", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
