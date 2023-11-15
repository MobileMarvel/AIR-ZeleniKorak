package hr.foi.air.zelenikorak

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class RegistrationActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var ime: EditText
    private lateinit var prezime: EditText
    private lateinit var email: EditText
    private lateinit var lozinka: EditText

    private fun Register() {
        val firstName = ime.text.toString().trim { it <= ' ' }
        val lastName = prezime.text.toString().trim { it <= ' ' }
        val userEmail = email.text.toString().trim { it <= ' ' }
        val userPassword = lozinka.text.toString().trim { it <= ' ' }

        if (firstName.isEmpty()) {
            ime.error = "Ime ne može biti prazno"
            return
        }
        if (lastName.isEmpty()) {
            prezime.error = "Prezime ne može biti prazno"
            return
        }
        if (userEmail.isEmpty()) {
            email.error = "Email ne može biti prazan"
            return
        }
        if (userPassword.isEmpty()) {
            lozinka.error = "Lozinka ne može biti prazna"
            return
        }

        mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = mAuth.currentUser
                db = FirebaseFirestore.getInstance()
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName("$firstName $lastName")
                    .build()

                user?.updateProfile(profileUpdates)?.addOnCompleteListener { profileTask ->
                    if (profileTask.isSuccessful) {
                        // User profile updated successfully
                        val userDocument = db.collection("users").document(user.uid)
                        val userData = hashMapOf(
                            "firstName" to firstName,
                            "lastName" to lastName
                        )
                        userDocument.set(userData).addOnCompleteListener { firestoreTask ->
                            if (firestoreTask.isSuccessful) {
                                // User data saved successfully
                            } else {
                                // Handle failure to save user data
                            }
                        }
                    }
                }
            } else {
                // If sign in fails, display a message to the user.
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        ime = findViewById(R.id.register_name)
        prezime = findViewById(R.id.register_lastName)
        email = findViewById(R.id.register_email)
        lozinka = findViewById(R.id.register_password)
        val registerButton: Button = findViewById(R.id.register_b)

        registerButton.setOnClickListener {
            Register()
        }
    }
}
