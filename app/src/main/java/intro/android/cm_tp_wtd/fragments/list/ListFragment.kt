package intro.android.cm_tp_wtd.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import intro.android.cm_tp_wtd.R
import intro.android.cm_tp_wtd.data.vm.WtDViewModel

class ListFragment : Fragment() {
    private  lateinit var mWtDViewModel: WtDViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)

        // Recyclerview
        val adapter = ListAdapter()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // UserViewModel
        mWtDViewModel = ViewModelProvider(this).get(WtDViewModel::class.java)
        mWtDViewModel.readAllUsers.observe(viewLifecycleOwner, Observer { note ->
            adapter.setData(note)
        })

        val button = view.findViewById<FloatingActionButton>(R.id.btn_add_new_user_from_list)
        button.setOnClickListener(){
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        return view
    }
}