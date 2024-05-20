package intro.android.cm_tp_wtd.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import intro.android.cm_tp_wtd.MainActivity
import intro.android.cm_tp_wtd.R

class LoginFragment: Fragment() {

    private lateinit var auth: FirebaseAuth
    private val db by lazy { (activity as MainActivity).db }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        auth = FirebaseAuth.getInstance()

        val backButton = view.findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
        }

        val registerButton = view.findViewById<TextView>(R.id.createAccountTextButton)
        registerButton.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        val loginButton = view.findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val email = view.findViewById<EditText>(R.id.emailEditText).text.toString()
            val password = view.findViewById<EditText>(R.id.passwordEditText).text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Se não houver usuário atual, faça o login com o email e a senha
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            // Login bem-sucedido, atualize a interface do usuário com as informações do usuário conectado
                            Log.d(TAG, "signInWithEmail: success")
                            //adicionarDocumento() // Após o login, adicione o documento
                            //excluirDados()
                            val user = auth.currentUser
                            findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                        } else {
                            // Se falhar, exiba uma mensagem de erro
                            Toast.makeText(
                                requireContext(),
                                "Falha na autenticação.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }else{
                Toast.makeText(requireContext(), "Please enter email and password.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}