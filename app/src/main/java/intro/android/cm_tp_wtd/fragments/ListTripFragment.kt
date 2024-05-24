package intro.android.cm_tp_wtd.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import intro.android.cm_tp_wtd.R

class ListTripFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listtrip, container, false)

        val backIcon = view.findViewById<ImageView>(R.id.backIcon)
        backIcon.setOnClickListener{
            findNavController().navigate(R.id.action_listTripFragment_to_dashboardFragment)
        }

        return view
    }
}