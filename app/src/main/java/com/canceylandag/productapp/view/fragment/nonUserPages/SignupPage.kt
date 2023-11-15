package com.canceylandag.productapp.view.fragment.nonUserPages

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.canceylandag.productapp.R
import com.canceylandag.productapp.databinding.FragmentSignupPageBinding
import com.canceylandag.productapp.view.activity.UserActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

private lateinit var fragmentSignupBinding: FragmentSignupPageBinding

private lateinit var email:String
private lateinit var password:String

private lateinit var auth:FirebaseAuth

class SignupPage : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentSignupPageBinding.bind(view)
        fragmentSignupBinding =binding
        auth =Firebase.auth

        fragmentSignupBinding.goToLogin.setOnClickListener {

            val action=SignupPageDirections.actionSignupPageToLoginPage()
            Navigation.findNavController(it).navigate(action)

        }

        fragmentSignupBinding.filledButton.setOnClickListener {

            signUp()

        }

    }


    fun signUp(){

        email = fragmentSignupBinding.emailTextField.text.toString()
        password = fragmentSignupBinding.passwordTextField.text.toString()

        if (email.isEmpty() || password.isEmpty()){

            Toast.makeText(activity,"Email veya Şifre Eksik", Toast.LENGTH_LONG).show()

        }

        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            Toast.makeText(activity,"Kayıt Başarılı",Toast.LENGTH_LONG).show()
            gotoFeeds()

        }.addOnFailureListener {

            Toast.makeText(activity,it.localizedMessage,Toast.LENGTH_LONG).show()

        }



    }

    fun gotoFeeds(){
        val intent = Intent(activity, UserActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

}