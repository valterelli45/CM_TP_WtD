package intro.android.cm_tp_wtd.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import intro.android.cm_tp_wtd.MainActivity
import intro.android.cm_tp_wtd.R

class PasswordFragment : DialogFragment() {

    private lateinit var auth: FirebaseAuth
    private val db by lazy { (activity as MainActivity).db }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_password, container, false)

        auth = FirebaseAuth.getInstance()

        val currentPasswordEditText = view.findViewById<EditText>(R.id.currentPasswordEditText)
        val newPasswordEditText = view.findViewById<EditText>(R.id.newPasswordEditText)
        val changePasswordButton = view.findViewById<Button>(R.id.changePasswordButton)

        changePasswordButton.setOnClickListener {
            val currentPassword = currentPasswordEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()

            if (currentPassword.isNotEmpty() && newPassword.isNotEmpty()) {
                val user = auth.currentUser
                if (user != null) {
                    val credential = EmailAuthProvider.getCredential(user.email!!, currentPassword)
                    user.reauthenticate(credential).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            user.updatePassword(newPassword).addOnCompleteListener { task2 ->
                                if (task2.isSuccessful) {
                                    user.uid.let { uid ->
                                        db.collection("users").document(uid).update("password",newPassword)
                                    }
                                    Toast.makeText(requireContext(), "Password updated successfully", Toast.LENGTH_SHORT).show()
                                    dismiss()
                                } else {
                                    Toast.makeText(requireContext(), "Error updating password", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(requireContext(), "Current password is incorrect", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
