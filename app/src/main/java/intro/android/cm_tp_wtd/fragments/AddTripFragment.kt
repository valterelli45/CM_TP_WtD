package intro.android.cm_tp_wtd.fragments

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import intro.android.cm_tp_wtd.MainActivity
import intro.android.cm_tp_wtd.R
import intro.android.cm_tp_wtd.adapters.LocationAdapter
import intro.android.cm_tp_wtd.models.Location
import java.util.UUID

class AddTripFragment: DialogFragment() {
    private lateinit var auth: FirebaseAuth
    private val db by lazy { (activity as MainActivity).db }
    private lateinit var locationAdapter: LocationAdapter
    private val locationList = mutableListOf<Location>()
    private val PERMISSIONS_REQUEST_CODE = 101

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_addtrip, container, false)
        auth = FirebaseAuth.getInstance()

        locationAdapter = LocationAdapter(locationList)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewLocations)
        val addLocationButton = view.findViewById<Button>(R.id.addLocationButton)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = locationAdapter

        val saveTripButton = view.findViewById<Button>(R.id.saveTripButton)
        saveTripButton.setOnClickListener {
            val tripName = view.findViewById<EditText>(R.id.tripNameEditText).text.toString()
            val tripDescription = view.findViewById<EditText>(R.id.tripDescriptionEditText).text.toString()
            val tripDate = view.findViewById<EditText>(R.id.tripDateEditText).text.toString()
            val tripRatingBar = view.findViewById<RatingBar>(R.id.tripRatingBar)
            val tripRating = tripRatingBar.rating

            if (tripName.isNotEmpty() && tripDescription.isNotEmpty() && tripDate.isNotEmpty() && tripRating != 0f) {
                val user = auth.currentUser
                val uid = user?.uid
                val tripDoc = hashMapOf(
                    "name" to tripName,
                    "description" to tripDescription,
                    "date" to tripDate,
                    "rating" to tripRating,
                    "locations" to locationList.map { it.toMap() }
                )
                uid?.let {
                    db.collection("trips").document(it)
                        .set(tripDoc)
                        .addOnSuccessListener {
                            Log.d(ContentValues.TAG, "Trip document successfully written!")
                            dismiss()
                        }.addOnFailureListener { e ->
                            Log.w(ContentValues.TAG, "Error writing trip document", e)
                            Toast.makeText(requireContext(), "Erro ao salvar os dados da viagem.", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                Toast.makeText(requireContext(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageUri: Uri? = data?.data

                if (imageUri != null) {
                    uploadImageToFirestore(imageUri)
                }
            }
        }

        // Solicitar permissões e abrir galeria de imagens
        addLocationButton.setOnClickListener {
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

    private fun uploadImageToFirestore(imageUri: Uri) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("images/${auth.currentUser?.uid}/tripsImages/${UUID.randomUUID()}")
        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                val locationName = view?.findViewById<EditText>(R.id.locationEditText)?.text.toString()
                if (locationName.isNotEmpty()) {
                    val location = Location(locationName, imageUrl)
                    locationList.add(location)
                    locationAdapter.notifyDataSetChanged()
                    view?.findViewById<EditText>(R.id.locationEditText)?.text?.clear()
                } else {
                    Toast.makeText(requireContext(), "Por favor, insira o nome do local.", Toast.LENGTH_SHORT).show()
                }
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(requireContext(), "Falha ao carregar imagem: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
