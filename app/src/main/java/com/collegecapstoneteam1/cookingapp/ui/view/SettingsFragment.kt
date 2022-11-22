package com.collegecapstoneteam1.cookingapp.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.databinding.DataBindingUtil
import com.collegecapstoneteam1.cookingapp.R
import com.collegecapstoneteam1.cookingapp.databinding.FragmentSettingBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //로그인으로 이동
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)

        auth = FirebaseAuth.getInstance()
        binding.loginStatus.text = auth.currentUser?.email


        binding.signInSignUp.setOnClickListener {
           fireLogin()
        }
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onResume() {
        if (auth.currentUser?.email != null){
            binding.signInSignUp.text = "로그아웃"
        } else if (auth.currentUser?.email == null){
            binding.signInSignUp.text = "로그인 / 회원가입"
        }
        super.onResume()
    }

    fun fireLogin() {
        if (binding.signInSignUp.text == "로그인 / 회원가입"){
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)

        }else if (binding.signInSignUp.text == "로그아웃"){
            binding.signInSignUp.text = "로그인 / 회원가입"
            auth.signOut()
            binding.loginStatus.text = auth.currentUser?.email

        }
    }

}