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
import com.canceylandag.productapp.databinding.FragmentLoginPageBinding
import com.canceylandag.productapp.view.activity.UserActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

private lateinit var fragmentLoginPageBinding: FragmentLoginPageBinding
private lateinit var email:String
private lateinit var password:String
private lateinit var auth:FirebaseAuth

class LoginPage : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoginPageBinding.bind(view)

        fragmentLoginPageBinding =binding;

        auth =Firebase.auth

        val curentUser= auth.currentUser

        if (curentUser!=null){
            gotoFeeds()

        }

        fragmentLoginPageBinding.goToSignup.setOnClickListener {

            val action=LoginPageDirections.actionLoginPageToSignupPage()
            Navigation.findNavController(it).navigate(action)

        }

        fragmentLoginPageBinding.filledButton.setOnClickListener {

            signIn()

        }


    }

    fun signIn(){

        email = fragmentLoginPageBinding.emailTextField.text.toString()
        password = fragmentLoginPageBinding.passwordTextField.text.toString()

        if (email.isEmpty() || password.isEmpty()){

            Toast.makeText(activity,"Email veya Şifre Eksik",Toast.LENGTH_LONG).show()

        }

        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            Toast.makeText(activity,"Giriş Başarılı",Toast.LENGTH_LONG).show()
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