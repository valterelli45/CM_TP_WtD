package intro.android.cm_tp_wtd.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import intro.android.cm_tp_wtd.MainActivity
import intro.android.cm_tp_wtd.R
import java.util.UUID

class ProfileFragment: Fragment() {
    private lateinit var auth: FirebaseAuth
    private val db by lazy { (activity as MainActivity).db }
    private val PERMISSIONS_REQUEST_CODE = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        val profileIcon = view.findViewById<ImageView>(R.id.profileIcon)
        val profilePicture = view.findViewById<ImageView>(R.id.profilePicture)
        val editProfilePictureButton = view.findViewById<Button>(R.id.editProfilePictureButton)
        val nameEditText = view.findViewById<EditText>(R.id.nameEditText)
        val usernameEditText = view.findViewById<EditText>(R.id.usernameEditText)
        val emailText = view.findViewById<TextView>(R.id.emailText)
        val saveButton = view.findViewById<Button>(R.id.saveButton)
        val changePasswordButton = view.findViewById<Button>(R.id.changePasswordButton)

        // Carregar dados do usuário
        currentUser?.uid?.let { uid ->
            db.collection("users").document(uid).get().addOnSuccessListener { document ->
                if (document != null) {
                    nameEditText.setText(document.getString("name"))
                    usernameEditText.setText(document.getString("username"))
                    emailText.text = document.getString("email")

                    val profileImageUrl = document.getString("profileImageUrl")
                    if (!profileImageUrl.isNullOrEmpty()) {
                        Glide.with(this)
                            .load(profileImageUrl)
                            .into(profilePicture)
                        Glide.with(this)
                            .load(profileImageUrl)
                            .into(profileIcon)
                    }
                } else {
                    Toast.makeText(requireContext(), "No user data found", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to load user data", Toast.LENGTH_SHORT).show()
            }
        }

        // Salvar dados do usuário
        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val username = usernameEditText.text.toString()

            // Criar um mapa de atualizações do tipo certo
            val userUpdates = hashMapOf<String, Any>(
                "name" to name,
                "username" to username
            )

            currentUser?.uid?.let { uid ->
                db.collection("users").document(uid).update(userUpdates as Map<String, Any>).addOnSuccessListener {
                    Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Abrir dialogo de alteração de senha
        changePasswordButton.setOnClickListener {
            val dialog = PasswordFragment()
            dialog.show(childFragmentManager, "ChangePasswordDialogFragment")
        }

        // Tratar a seleção da imagem
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageUri: Uri? = data?.data

                // Fazer upload da imagem para o Firestore
                if (imageUri != null) {
                    uploadImageToFirestore(imageUri)
                    profilePicture.setImageURI(imageUri)
                }
            }
        }

        // Solicitar permissões e abrir galeria de imagens
        editProfilePictureButton.setOnClickListener {
            if (checkAndRequestPermissions()) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                resultLauncher.launch(intent)
            }
        }

        return view
    }

    private fun checkAndRequestPermissions(): Boolean {
        val permissions = arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES)
        val permissionsNeeded = permissions.filter {
            ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED
        }
        return if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(requireActivity(), permissionsNeeded.toTypedArray(), PERMISSIONS_REQUEST_CODE)
            false
        } else {
            true
        }
    }

    // Fazer upload da imagem para o Firestore
    private fun uploadImageToFirestore(imageUri: Uri) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("images/${auth.currentUser?.uid}/profileImage/${UUID.randomUUID()}")
        val uploadTask = imageRef.putFile(imageUri)
        val currentUser = auth.currentUser

        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Imagem carregada com sucesso
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                // Atualizar a URL da imagem no perfil do usuário
                currentUser?.uid?.let { uid ->
                    db.collection("users").document(uid).update("profileImageUrl", imageUrl)
                        .addOnSuccessListener {
                            // URL da imagem atualizada com sucesso no Firestore
                            Toast.makeText(requireContext(), "Profile picture updated successfully", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener { e ->
                            // Falha ao atualizar a URL da imagem
                            Toast.makeText(requireContext(), "Failed to update profile picture: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }.addOnFailureListener { exception ->
            // Falha no upload da imagem
            Toast.makeText(requireContext(), "Failed to upload image: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }
}