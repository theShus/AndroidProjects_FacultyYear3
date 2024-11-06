package com.example.projekat_septembar.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projekat_septembar.data.models.SellerDetails
import com.example.projekat_septembar.databinding.ActivityContactSellerBinding
import com.squareup.picasso.Picasso

class ContactSellerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactSellerBinding
    private lateinit var sellerDetails: SellerDetails




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactSellerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sellerDetails = intent.getParcelableExtra("seller")!!

        println(sellerDetails.first_name + " aaa " + sellerDetails.email)
        initView()
        initListeners()
    }


    private fun initView() {
        binding.sellerNameTv.text = sellerDetails.first_name
        binding.sellerLastnameTv.text = sellerDetails.last_name
        binding.sellerPhoneTv.text = sellerDetails.phone
        binding.sellerEmailTv.text = sellerDetails.email
        Picasso.get().load(sellerDetails.avatar).into(binding.sellerImage)
    }

    private fun initListeners() {
        binding.button.setOnClickListener{
            val userName: String = binding.userFirstNameEt.text.toString()
            val userLastname: String = binding.UserLastnameEt.text.toString()
            val usePhone: Int = binding.userPhoneEt.text.toString().toInt()

            if (userName != "" && userLastname != "" && usePhone > 0){

                val returnIntent = Intent()
                returnIntent.putExtra("name",userName)
                returnIntent.putExtra("lastname",userLastname)
                returnIntent.putExtra("phone", usePhone)

                this.setResult(RESULT_OK, returnIntent)
                this.finish()
            }
            else Toast.makeText(this, "Please fill in all fields properly ", Toast.LENGTH_SHORT).show()
        }
    }


}