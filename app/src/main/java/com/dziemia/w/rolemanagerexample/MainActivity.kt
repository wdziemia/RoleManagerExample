package com.dziemia.w.rolemanagerexample

import android.app.role.RoleManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var rootView: View
    lateinit var roleManager: RoleManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roleManager = getSystemService(RoleManager::class.java)
        rootView = main_root

        main_button.setOnClickListener {
            if (isRoleHeld()){
                Toast.makeText(this, "Role currently held!", Toast.LENGTH_SHORT).show()
            } else {
                val roleRequestIntent = roleManager.createRequestRoleIntent(RoleManager.ROLE_MUSIC)
                startActivityForResult(roleRequestIntent, ROLE_REQUEST_CODE)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        updateBackground()
    }

    private fun updateBackground() {
        rootView.background = if (isRoleHeld()) {
            ContextCompat.getDrawable(this, android.R.color.holo_green_dark)
        } else {
            ContextCompat.getDrawable(this, android.R.color.holo_red_dark)
        }
    }

    private fun isRoleHeld(): Boolean {
        return roleManager.isRoleAvailable(RoleManager.ROLE_MUSIC) &&
                roleManager.isRoleHeld(RoleManager.ROLE_MUSIC)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ROLE_REQUEST_CODE) {
            updateBackground()
        }
    }

    companion object {
        const val ROLE_REQUEST_CODE = 10000
    }

}
