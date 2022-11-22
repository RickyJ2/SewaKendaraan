package com.example.sewakendaraan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sewakendaraan.databinding.FragmentProfileBinding
import com.example.sewakendaraan.room.userRoom.User
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_profile.*
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = context as Home
        binding.editProfilePictureButton.setOnClickListener{
            replaceFragment(CameraFragment())
        }

        binding.inputLayoutUsername.editText?.setText(context.mUserViewModel.readLoginData?.value?.username)
        binding.inputLayoutEmail.editText?.setText(context.mUserViewModel.readLoginData?.value?.email)
        binding.inputLayoutHandphone.editText?.setText(context.mUserViewModel.readLoginData?.value?.handphone)
        binding.inputLayoutDateOfBirth.editText?.setText(context.mUserViewModel.readLoginData?.value?.dateOfBirth)

        binding.inputLayoutDateOfBirth.editText?.setOnFocusChangeListener{ _, hasFocus ->
            if(hasFocus){
                datePicker()
            }
        }
        binding.inputLayoutDateOfBirth.editText?.setOnClickListener{
            datePicker()
        }

        binding.updateProfileBtn.setOnClickListener{
            updateUser()
            replaceFragment(SettingFragment())
        }
    }
    private fun datePicker(){
        val context = context as Home
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select your birth of date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        inputLayoutDateOfBirth.setEndIconOnClickListener {
            datePicker.show(context.supportFragmentManager, datePicker.tag)
        }

        datePicker.addOnPositiveButtonClickListener {
            val myFormat = "MM/dd/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            inputLayoutDateOfBirth.editText?.setText(sdf.format(datePicker.selection))
        }
    }
    private fun updateUser(){
        val context = context as Home
        val username: String = binding.inputLayoutUsername.editText?.text.toString()
        val email: String = binding.inputLayoutEmail.editText?.text.toString()
        val handphone: String = binding.inputLayoutHandphone.editText?.text.toString()
        val dateOfBirth: String = binding.inputLayoutDateOfBirth.editText?.text.toString()

        val user = User(
            context.mUserViewModel.readLoginData.value!!.id,
            username,
            email,
            context.mUserViewModel.readLoginData?.value?.password.toString(),
            handphone,
            dateOfBirth
        )
        context.mUserViewModel.updateUser(user)
    }
    private fun  replaceFragment(fragment: Fragment){
        val context = context as Home
        val fragmentManager = context.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}