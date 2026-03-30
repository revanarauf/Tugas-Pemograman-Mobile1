package com.example.e_learning

import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.e_learning.databinding.ActivityRegisterBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize ViewBinding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()
        setupRealTimeValidation()
        setupListeners()
    }

    private fun setupSpinner() {
        // Custom data for Spinner
        val prodi = arrayOf("Informatika", "Sistem Informasi", "Teknik Komputer", "Data Science")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, prodi)
        binding.spinnerProdi.adapter = adapter
    }

    private fun setupRealTimeValidation() {
        // Email real-time validation
        binding.etEmail.addTextChangedListener { text ->
            val email = text.toString().trim()
            if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.tilEmail.error = "Format email tidak valid"
            } else {
                binding.tilEmail.error = null
            }
        }

        // Password real-time validation
        binding.etPassword.addTextChangedListener { text ->
            if (text.toString().length < 6) {
                binding.tilPassword.error = "Password minimal 6 karakter"
            } else {
                binding.tilPassword.error = null
            }
        }

        // Confirm Password real-time validation
        binding.etConfirmPassword.addTextChangedListener { text ->
            val pass = binding.etPassword.text.toString()
            if (text.toString() != pass) {
                binding.tilConfirmPassword.error = "Password tidak sama"
            } else {
                binding.tilConfirmPassword.error = null
            }
        }
    }

    private fun setupListeners() {
        // Submit button click
        binding.btnSubmit.setOnClickListener {
            if (validateAll()) {
                showConfirmationDialog()
            }
        }

        // Navigate back to Login
        binding.tvLogin.setOnClickListener {
            finish()
        }
    }

    private fun validateAll(): Boolean {
        var isValid = true

        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val pass = binding.etPassword.text.toString()
        val confirmPass = binding.etConfirmPassword.text.toString()

        // Name validation
        if (name.isEmpty()) {
            binding.tilName.error = "Nama wajib diisi"
            isValid = false
        } else {
            binding.tilName.error = null
        }

        // Email validation
        if (email.isEmpty()) {
            binding.tilEmail.error = "Email wajib diisi"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Format email tidak valid"
            isValid = false
        } else {
            binding.tilEmail.error = null
        }

        // Password validation
        if (pass.length < 6) {
            binding.tilPassword.error = "Password minimal 6 karakter"
            isValid = false
        } else {
            binding.tilPassword.error = null
        }

        // Confirm Password validation
        if (confirmPass != pass) {
            binding.tilConfirmPassword.error = "Password tidak sama"
            isValid = false
        } else {
            binding.tilConfirmPassword.error = null
        }

        // Gender validation
        if (binding.rgGender.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Pilih jenis kelamin", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        // Hobbies validation (Min 3)
        var count = 0
        if (binding.cbCoding.isChecked) count++
        if (binding.cbGaming.isChecked) count++
        if (binding.cbMusik.isChecked) count++
        if (binding.cbOlahraga.isChecked) count++
        if (binding.cbTraveling.isChecked) count++

        if (count < 3) {
            Toast.makeText(this, "Pilih minimal 3 hobi", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Konfirmasi")
            .setMessage("Apakah data sudah benar?")
            .setPositiveButton("Ya") { _, _ ->
                Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_LONG).show()
                finish()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
