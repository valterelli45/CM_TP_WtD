package intro.android.cm_tp_wtd.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import intro.android.cm_tp_wtd.MainActivity
import intro.android.cm_tp_wtd.R

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private val db by lazy { (activity as MainActivity).db }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        auth = FirebaseAuth.getInstance()

        val backButton: ImageView = view.findViewById(R.id.backButton2)
        val loginButton: TextView = view.findViewById(R.id.loginTextButton)
        val signUpButton: Button = view.findViewById(R.id.signupButton)
        val emailEditText: EditText = view.findViewById(R.id.emailEditText)
        val passwordEditText: EditText = view.findViewById(R.id.passwordEditText)
        val confirmPasswordEditText: EditText = view.findViewById(R.id.confirmPasswordEditText)

        backButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
        }

        loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val passwordConfirm = confirmPasswordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && passwordConfirm.isNotEmpty()) {
                if (password == passwordConfirm) {
                    if (password.length >= 6) {
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(requireActivity()) { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "createUserWithEmail: success")
                                    // Clear text fields
                                    emailEditText.text.clear()
                                    passwordEditText.text.clear()
                                    confirmPasswordEditText.text.clear()
                                    val user = auth.currentUser
                                    findNavController().navigate(R.id.action_registerFragment_to_dashboardFragment)
                                } else {
                                    val exceptionMessage = task.exception?.message
                                    Toast.makeText(
                                        requireContext(),
                                        "Authentication failed: $exceptionMessage",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Password should be at least 6 characters.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Passwords do not match.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please fill all fields.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}