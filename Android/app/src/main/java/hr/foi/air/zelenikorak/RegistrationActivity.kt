package hr.foi.air.zelenikorak

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var ime: EditText
    private lateinit var prezime: EditText
    private lateinit var email: EditText
    private lateinit var lozinka: EditText

    private fun Register() {
        val ime = ime.text.toString().trim { it <= ' ' }
        val prezime = prezime.text.toString().trim { it <= ' ' }
        val email = email.text.toString().trim { it <= ' ' }
        val lozinka = lozinka.text.toString().trim { it <= ' ' }
        if (ime.isEmpty()) {
            ime.error = "Ime ne može biti prazno"
        }
        if (prezime.isEmpty()) {
            prezime.setError("Ime ne može biti prazno")
        }
        if (email.isEmpty()) {
            email.setError("Ime ne može biti prazno")
        }
        if (lozinka.isEmpty()) {
            lozinka.setError("Ime ne može biti prazno")
        }

        else{
            mAuth.createUserWithEmailAndPassword(ime, prezime, email, lozinka).addOnCompleteListener(new )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        mAuth = FirebaseAuth.getInstance()
        ime = findViewById(R.id.register_name)
        prezime = findViewById(R.id.register_lastName)
        email = findViewById(R.id.register_email)
        lozinka = findViewById(R.id.register_password)
        val register_b: Button = findViewById(R.id.register_b)

        register_b.setOnClickListener {
            Register()
        }
        /*textLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class))
            }
        })*/
    }
}