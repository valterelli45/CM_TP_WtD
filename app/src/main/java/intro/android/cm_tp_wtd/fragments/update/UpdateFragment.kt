package intro.android.cm_tp_wtd.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import intro.android.cm_tp_wtd.R
import intro.android.cm_tp_wtd.data.entities.User
import intro.android.cm_tp_wtd.data.vm.WtDViewModel

class UpdateFragment : Fragment() {
    private  val args by navArgs<UpdateFragmentArgs>()
    private lateinit var mWtDViewModel: WtDViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mWtDViewModel = ViewModelProvider(this)[WtDViewModel::class.java]

        view.findViewById<TextView>(R.id.updateUser).text = args.currentUser.name

        val updateButton = view.findViewById<Button>(R.id.update)
        updateButton.setOnClickListener {
            updateUser()
        }

        val deleteButton = view.findViewById<Button>(R.id.delete)
        deleteButton.setOnClickListener {
            deleteUser()
        }

        val backButton = view.findViewById<Button>(R.id.backToListFromUpdate)
        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }

        return  view
    }

    private  fun updateUser(){
        val userText = view?.findViewById<EditText>(R.id.updateUser)?.text.toString()

        if(userText.isEmpty()) {
            makeText(context , "Não pode um user vazio!", Toast.LENGTH_LONG).show()
        }
        else {
            val user = User(args.currentUser.id, userText)

            mWtDViewModel.updateUser(user)

            makeText(requireContext(), "User atualizado com sucesso!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Sim") { _, _ ->
            mWtDViewModel.deleteUser(args.currentUser)
            makeText(
                requireContext(),
                "User apagado com sucesso!",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("Não") { _, _ -> }
        builder.setTitle("Apagar")
        builder.setMessage("Tem a certeza que pretende apagar o user?")
        builder.create().show()
    }
}