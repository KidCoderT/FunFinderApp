package com.udacity.funfinder.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.udacity.funfinder.R
import com.udacity.funfinder.databinding.ActivityAuthenticationBinding
import com.udacity.funfinder.myapp.MainActivity

class AuthenticationActivity : AppCompatActivity() {
    // registers a callback for the FirebaseUI Activity result contract:
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }


    companion object {
        const val TAG = "AuthenticationActivity"
    }

    private lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_authentication)

        // Implement the create account and sign in using FirebaseUI, use sign in using email and sign in using Google
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        // Create intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setLogo(R.drawable.auth_bg)
            .setTheme(R.style.firebase_ui_auth_style)
            .build()

        // Start the signup intent when clicked login button
        binding.loginButton.setOnClickListener {
            signInLauncher.launch(signInIntent)
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            Log.i(TAG, "Error")
        }
    }
}