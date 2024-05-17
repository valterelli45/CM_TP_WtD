package intro.android.cm_tp_wtd.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import intro.android.cm_tp_wtd.R
import intro.android.cm_tp_wtd.data.entities.User
import intro.android.cm_tp_wtd.data.vm.WtDViewModel

class AddFragment : Fragment() {
    private lateinit var mWtDViewModel: WtDViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        ViewModelProvider(this)[WtDViewModel::class.java].also { this.mWtDViewModel = it }

        val button = view.findViewById<Button>(R.id.save)
        button.setOnClickListener {
            addUser()
        }

        val backButton = view.findViewById<Button>(R.id.backToList)
        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }

        return view
    }

    private fun addUser() {
        val userText = view?.findViewById<EditText>(R.id.addUser)?.text.toString()

        if(userText.isEmpty()) {
            Toast.makeText(view?.context, "Não pode um user vazio!", Toast.LENGTH_LONG).show()
        }
        else {
            val user = User(0, userText)

            mWtDViewModel.addUser(user)

            Toast.makeText(requireContext(), "Gravado com sucesso!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
    }
}