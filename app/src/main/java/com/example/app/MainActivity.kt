package com.example.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.app.databinding.ActivityMainBinding
import com.example.app.entity.User
import com.example.app.widget.CodeView
import com.example.core.utils.CacheUtils
import com.example.core.utils.Utils
import com.example.lesson.LessonActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val usernameKey = "username"
    private val passwordKey = "password"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        binding.run {
            etUsername.setText(CacheUtils[usernameKey])
            etPassword.setText(CacheUtils[passwordKey])
            btnLogin.setOnClickListener(this@MainActivity)
            codeView.setOnClickListener(this@MainActivity)
        }

    }

    override fun onClick(v: View) {
        when (v) {
            is CodeView -> {
                v.updateCode()
            }
            is Button -> {
                login()
            }
        }
    }

    private fun login() {
        val username = binding.etUsername.text?.toString() ?:""
        val password = binding.etPassword.text?.toString() ?:""
        val code = binding.etCode.text?.toString() ?:""
        val user = User(username, password, code)
        if (verify(user)) {
            CacheUtils.save(usernameKey, username)
            CacheUtils.save(passwordKey, password)
            startActivity(Intent(this, LessonActivity::class.java))
        }
    }

    private fun verify(user: User): Boolean {
        if (user.username != null && user.username!!.length < 4) {
            Utils.toast("用户名不合法")
            return false
        }
        if (user.password != null && user.password!!.length < 4) {
            Utils.toast("密码不合法")
            return false
        }
        return true
    }
}