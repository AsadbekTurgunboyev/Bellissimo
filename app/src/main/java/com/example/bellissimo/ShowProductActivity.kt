package com.example.bellissimo

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.bellissimo.databinding.ActivityShowProductBinding
import com.example.bellissimo.models.ProductItemModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ShowProductActivity : AppCompatActivity() {


    lateinit var firebaseDatabase: DatabaseReference
    lateinit var viewBinding: ActivityShowProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityShowProductBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("order")


        viewBinding.backButton.setOnClickListener {
            onBackPressed()
        }



        val product: ProductItemModel? = intent.getParcelableExtra("products")
        if (product != null) {
            // Access properties of 'product'
            val productName = product.productName
            viewBinding.titleProduct.text = productName
            viewBinding.productPrice.text = product.smallSize?.price?.let {
                formatNumbersWithSpaces(
                    it
                )
            }
            val des = product.productDescription
            viewBinding.descProduct.text = des
            product.productImage?.let { viewBinding.imageProduct.setImageResource(it) }
            val smallSizePrice = product.smallSize?.price
            // etc.
        }

        viewBinding.buttonAdd.setOnClickListener {

            firebaseDatabase.push().setValue(product).addOnCompleteListener {
                if (it.isSuccessful){
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)

                }
            }
        }
        viewBinding.btnSmall.setTextColor(ContextCompat.getColorStateList(this, R.color.text_color))
        viewBinding.btnSmall.setBackgroundResource(R.drawable.background_shape_selector)

        viewBinding.btnMedium.setTextColor(ContextCompat.getColorStateList(this, R.color.text_color))
        viewBinding.btnMedium.setBackgroundResource(R.drawable.background_shape_selector)

        viewBinding.btnLarge.setTextColor(ContextCompat.getColorStateList(this, R.color.text_color))
        viewBinding.btnLarge.setBackgroundResource(R.drawable.background_shape_selector)

        viewBinding.btnSmall.isSelected = true
        viewBinding.btnSmall.setOnClickListener {
            it.isSelected = !it.isSelected
            viewBinding.btnMedium.isSelected = false
            product?.smallSize?.price?.let { it1 -> formatNumbersWithSpaces(it1) }
            viewBinding.btnLarge.isSelected = false
        }
        viewBinding.btnMedium.setOnClickListener {
            it.isSelected = !it.isSelected
            viewBinding.btnSmall.isSelected = false
            product?.mediumSize?.price?.let { it1 -> formatNumbersWithSpaces(it1) }
            viewBinding.btnLarge.isSelected = false
        }
        viewBinding.btnLarge.setOnClickListener {
            it.isSelected = !it.isSelected
            viewBinding.btnMedium.isSelected = false
            product?.largeSize?.price?.let { it1 -> formatNumbersWithSpaces(it1) }
            viewBinding.btnSmall.isSelected = false
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun formatNumbersWithSpaces(numbers: Int): String {
        val numberFormat = "%,d"


        val formattedNumber = String.format(numberFormat, numbers)
        formattedNumber.replace(",", " ")


        return "$formattedNumber so'm"
    }
}