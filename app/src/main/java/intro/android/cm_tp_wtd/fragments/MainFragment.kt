package intro.android.cm_tp_wtd.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import intro.android.cm_tp_wtd.R

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val loginButton = view.findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        }

        val createAccountButton = view.findViewById<TextView>(R.id.createAccountButton)
        createAccountButton.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_registerFragment)
        }

        return view
    }
}