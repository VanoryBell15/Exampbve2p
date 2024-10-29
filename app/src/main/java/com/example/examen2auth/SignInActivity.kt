package com.example.examen2auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.examen2auth.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth


class SignInActivity : AppCompatActivity() {


    // Variables para el enlace de vista (View Binding) y la autenticación de Firebase
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // layout usando View Binding
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Configurar listener para el TextView que lleva a la pantalla de registro
        binding.textView.setOnClickListener {
            // Redirige a SignUpActivity
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Configurar listener para el botón de inicio de sesión
        binding.button.setOnClickListener {
            // Obtener el email y la contraseña de los campos de texto
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            // Verificar que los campos de email y contraseña no estén vacíos
            if (email.isNotEmpty() && pass.isNotEmpty()) {

                //  iniciar sesión con Firebase usando email y contraseña
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Si el inicio de sesión es exitoso, redirige a MainActivity
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        // Muestra un mensaje de error si el inicio de sesión falla
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                // Muestra un mensaje si algún campo está vacío
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        // Verifica si el usuario ya está autenticado al iniciar la actividad
        if (firebaseAuth.currentUser != null) {
            // Si el usuario ya ha iniciado sesión, redirige a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
