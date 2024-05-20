package intro.android.cm_tp_wtd

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /*private fun adicionarDocumento() {
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
    }*/
}