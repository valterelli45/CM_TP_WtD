package intro.android.cm_tp_wtd.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import intro.android.cm_tp_wtd.R

class DashboardFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val profileButton = view.findViewById<ImageView>(R.id.profileIcon)
        profileButton.setOnClickListener{
            findNavController().navigate(R.id.action_dashboardFragment_to_profileFragment)
        }

        return view
    }
}