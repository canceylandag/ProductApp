package com.canceylandag.productapp.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.canceylandag.productapp.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class UserActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        auth=Firebase.auth
        setSupportActionBar(findViewById(R.id.toolbar))

       /* val navController=supportFragmentManager.findFragmentById(R.id.nav_products) as NavHostFragment
        val appBarConfiguration= AppBarConfiguration(navController.navController.graph)
        findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar).setupWithNavController(navController.navController,appBarConfiguration)*/
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.user_activity_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.menu_logout->{signout(); return true}

        else-> super.onOptionsItemSelected(item)
        }

    }

    private fun signout(){
        auth.signOut()
        val intent= Intent(this@UserActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}