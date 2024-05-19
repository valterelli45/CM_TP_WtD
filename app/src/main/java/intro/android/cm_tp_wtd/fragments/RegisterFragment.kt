package intro.android.cm_tp_wtd.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import intro.android.cm_tp_wtd.R

class RegisterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val backButton = view.findViewById<ImageView>(R.id.backButton2)
        backButton.setOnClickListener{
            //findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }

        val loginButton = view.findViewById<TextView>(R.id.loginTextButton)
        loginButton.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return view
    }
}