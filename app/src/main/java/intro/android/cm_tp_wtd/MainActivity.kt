package intro.android.cm_tp_wtd

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialize o Firebase Auth
        auth = FirebaseAuth.getInstance()
        val email = "valter07082001@gmail.com"
        val password = "1epca92a"

        val currentUser = auth.currentUser
        if (currentUser == null) {
            // Se não houver usuário atual, faça o login com o email e a senha
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login bem-sucedido, atualize a interface do usuário com as informações do usuário conectado
                        Log.d(TAG, "signInWithEmail: success")
                        //adicionarDocumento() // Após o login, adicione o documento
                        //excluirDados()
                    } else {
                        // Se falhar, exiba uma mensagem de erro
                        Toast.makeText(
                            baseContext,
                            "Falha na autenticação.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }

    private fun adicionarDocumento() {
        val data = hashMapOf(
            "nome" to "João",
            "idade" to 30
        )

        db.collection("usuarios")
            .add(data)
            .addOnSuccessListener {
                Toast.makeText(this,"Adicionado com sucesso", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this,"Falha ao adicionar", Toast.LENGTH_SHORT).show()
            }
    }

    private fun excluirDados() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            db.collection("usuarios")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        document.reference.delete()
                            .addOnSuccessListener {
                                Log.d(TAG, "Documento excluído com sucesso: ${document.id}")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Falha ao excluir documento: ${document.id}", e)
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Falha ao obter documentos", exception)
                }
        } else {
            Toast.makeText(this, "Nenhum usuário conectado", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        private const val TAG = "MainActivity"
    }
}