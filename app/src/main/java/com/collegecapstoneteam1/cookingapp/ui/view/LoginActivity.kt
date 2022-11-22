package com.collegecapstoneteam1.cookingapp.ui.view

import android.app.Activity
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.collegecapstoneteam1.cookingapp.R
import com.collegecapstoneteam1.cookingapp.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private val auth by lazy{
        FirebaseAuth.getInstance()
    }

    private val googleSignInOptions: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
    private val googleSignClient by lazy {
        GoogleSignIn.getClient(this@LoginActivity, googleSignInOptions)
    }
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =
            DataBindingUtil.setContentView(this@LoginActivity, R.layout.activity_login)
        binding.lifecycleOwner = this@LoginActivity

        val email = binding.etEmail.text
        val pw = binding.etPassword.text

        setResultSignUp()

        with(binding){
            btnLogin.setOnClickListener {
                loginEmail(email.toString(), pw.toString())
            }
            tvSignup.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            tvSignup.setOnClickListener {
                regEmail(email.toString(), pw.toString())
            }
            signinGoogle.setOnClickListener {
                loginWithGoogle()
            }

            tvBack.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            tvBack.setOnClickListener {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }




        setContentView(binding.root)
    }

    fun loginEmail(email: String, pw: String){
        Log.d("@@@@@@@@@@@@", "$email $pw")
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pw)){
            Toast.makeText(this@LoginActivity, "아이디 및 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else{
            auth?.signInWithEmailAndPassword(email, pw)?.addOnCompleteListener{
                    task ->
                if(task.isSuccessful){
                    moveMainPage(task.result?.user)
                } else{
                    //로그인 실패
                    if (pw.length < 6) {
                        Toast.makeText(this@LoginActivity, "비밀번호는 6자리 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@LoginActivity, "아이디 및 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    fun regEmail(email: String, pw: String){
        Log.d("@@@@@@@@@@@@", "$email $pw")
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pw)){
            Toast.makeText(this@LoginActivity, "아이디 및 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            auth?.createUserWithEmailAndPassword(email, pw)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // 회원가입 완료 -> 바로 로그인
                        moveMainPage(task.result?.user)
                    } else if (task.exception?.message.isNullOrEmpty()) {
                        // 회원가입 실패
                        //Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                        Toast.makeText(this@LoginActivity, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        // 같은 아이디가 있을 시 로그인 시도
                        loginEmail(email, pw)
                    }
                }
        }
    }
    private fun setResultSignUp() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                // 정상적으로 결과가 받아와진다면 조건문 실행
                if (result.resultCode == Activity.RESULT_OK) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    handleSignInResult(task)
                }
            }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email.toString()
            val familyName = account?.familyName.toString()
            val givenName = account?.givenName.toString()
            val displayName = account?.displayName.toString()
            val photoUrl = account?.photoUrl.toString()

            Log.d("로그인한 유저의 이메일", email)
            Log.d("로그인한 유저의 성", familyName)
            Log.d("로그인한 유저의 이름", givenName)
            Log.d("로그인한 유저의 전체이름", displayName)
            Log.d("로그인한 유저의 프로필 사진의 주소", photoUrl)

            val nextIntent = Intent(this@LoginActivity, MainActivity::class.java)
            nextIntent.putExtra("user", account)
            startActivity(nextIntent)
            finish()

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(this@LoginActivity, "구글 로그인 실패", Toast.LENGTH_SHORT).show()
            Log.w("failed", "signInResult:failed code=" + e.statusCode)
        }
    }

    // ------------------------ 구글 관련 start ------------------------------
    private fun loginWithGoogle() {
        val signInIntent: Intent = googleSignClient.getSignInIntent()
        resultLauncher.launch(signInIntent)
    }

    fun moveMainPage(user: FirebaseUser?){
        // 파이어베이스 유저 상태가 있을 경우에만 넘어감
        if(user != null){
            val nextIntent = Intent(this@LoginActivity, MainActivity::class.java)
            nextIntent.putExtra("user", user)
            startActivity(nextIntent)
            finish()
        }
    }
}