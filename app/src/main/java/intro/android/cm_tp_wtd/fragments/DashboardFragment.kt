package intro.android.cm_tp_wtd.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import intro.android.cm_tp_wtd.MainActivity
import intro.android.cm_tp_wtd.R

class DashboardFragment: Fragment() {
    private lateinit var auth: FirebaseAuth
    private val db by lazy { (activity as MainActivity).db }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        val profileButton = view.findViewById<ImageView>(R.id.profileIcon)
        val addtripButton = view.findViewById<Button>(R.id.addTripButton)
        addtripButton.setOnClickListener{
            val dialog = AddTripFragment()
            dialog.show(childFragmentManager, "AddTripDialogFragment")
        }

        // Carregar dados do usuário
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
            }.addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to load user data", Toast.LENGTH_SHORT).show()
            }
        }

        profileButton.setOnClickListener{
            findNavController().navigate(R.id.action_dashboardFragment_to_profileFragment)
        }

        return view
    }
}