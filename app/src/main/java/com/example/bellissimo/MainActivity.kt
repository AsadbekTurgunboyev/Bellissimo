package com.example.bellissimo

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.allenliu.badgeview.BadgeFactory
import com.allenliu.badgeview.BadgeView
import com.example.bellissimo.adapters.BottomAdapter
import com.example.bellissimo.adapters.ProductsAdapter
import com.example.bellissimo.data.PreferenceManager
import com.example.bellissimo.databinding.ActivityMainBinding
import com.example.bellissimo.`interface`.BottomInterface
import com.example.bellissimo.models.BottomItemModel
import com.example.bellissimo.models.ProductItemModel
import com.example.bellissimo.models.ProductSize
import com.example.bellissimo.utils.LanguageUtils
import com.google.firebase.database.*
import java.util.*


class MainActivity : AppCompatActivity(),BottomInterface {

    val list = arrayListOf<BottomItemModel>()
    lateinit var preferenceManager :PreferenceManager

    lateinit var databaseReference: DatabaseReference
    val save_order = arrayListOf<ProductItemModel>()

    val valueEventListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
           save_order.clear()
            for (ds in  snapshot.children){
                val model = ds.getValue(ProductItemModel::class.java)
                model?.let { save_order.add(it) }
            }

            BadgeFactory.create(this@MainActivity)
                .setTextColor(Color.WHITE)
                .setWidthAndHeight(20, 20)
                .setBadgeBackground(Color.BLUE)
                .setTextSize(10)
                .setBadgeGravity(Gravity.END or Gravity.TOP)
                .setBadgeCount(save_order.size)
                .setShape(BadgeView.SHAPE_CIRCLE)
                .setSpace(5, 5)
                .bind(viewBinding.buttonShop)

        }

        override fun onCancelled(error: DatabaseError) {

        }
    }
    val productsList = arrayListOf<ProductItemModel>()
    lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        preferenceManager = PreferenceManager(this)
        preferenceManager.getLanguage()?.let { setLocale(this, it) }
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)



        viewBinding.openMenu.setOnClickListener {
            viewBinding.layout1.open()
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("order")

        databaseReference.addValueEventListener(valueEventListener)

        settList()


        viewBinding.recyclerView.adapter = BottomAdapter(list,this)
        val layoutManager = LinearLayoutManager(this)
        viewBinding.productRecyclerview.layoutManager = layoutManager
        viewBinding.btnRu.setTextColor(ContextCompat.getColorStateList(this, R.color.text_color))
        viewBinding.btnRu.setBackgroundResource(R.drawable.background_shape_selector)

        if (preferenceManager.getLanguage() == "uz"){

            viewBinding.btnUzb.isSelected = true
        }else{
            viewBinding.btnRu.isSelected = true

        }
        viewBinding.btnUzb.setTextColor(ContextCompat.getColorStateList(this, R.color.text_color))
        viewBinding.btnUzb.setBackgroundResource(R.drawable.background_shape_selector)

        viewBinding.btnUzb.setOnClickListener {
            setLanguage("uz")
            it.isSelected = !it.isSelected
            viewBinding.btnRu.isSelected = false
        }
        viewBinding.btnRu.setOnClickListener {
            setLanguage("ru")

            it.isSelected = !it.isSelected
            viewBinding.btnUzb.isSelected = false
        }



// Replace this with the number entered by the user
        val positionToScrollTo = 5

        viewBinding.productRecyclerview.adapter = ProductsAdapter(productsList)


    }

    private fun setLanguage(s: String) {
        LanguageUtils.setLocale(this,s,preferenceManager)


    }
    fun setLocale(activity: Activity, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(activity.resources.configuration)
        config.setLocale(locale)

        activity.baseContext.resources.updateConfiguration(
            config,
            activity.baseContext.resources.displayMetrics
        )
    }

    private fun settList() {
        list.add(BottomItemModel(getString(R.string.pitsa), R.drawable.pizza))
        list.add(BottomItemModel(getString(R.string.gazaklar), R.drawable.french_fries))
        list.add(BottomItemModel(getString(R.string.ichimliklar), R.drawable.cocktail))
        list.add(BottomItemModel(getString(R.string.salatlar), R.drawable.salad))
        list.add(BottomItemModel(getString(R.string.desert), R.drawable.sweets))
        list.add(BottomItemModel(getString(R.string.desert), R.drawable.sweets))
        list.add(BottomItemModel(getString(R.string.desert), R.drawable.sweets))


        productsList.add(
            ProductItemModel(
                "To'vuqli donar",
                R.drawable.pitsa1,
                60000,
                "Pitsa",
                "Yumshoq xamir, tovuq donar go'shti, ayzerbek karami, pishloq piyoz pomidor",
                ProductSize(true, 60000, "Kichik"),
                ProductSize(true, 72000, "O'rtacha"),
                ProductSize(true, 85000, "Katta")
            )
        )
        productsList.add(
            ProductItemModel(
                "Pishloqli pitsa",
                R.drawable.pitsa2,
                39000,
                "Pitsa",
                "Haqiqiy motsarella firmenniy va alfreddo sousi bilan uyg'unlikda",
                ProductSize(true, 39000, "Kichik"),
                ProductSize(true, 65000, "O'rtacha"),
                ProductSize(true, 95000, "Katta")
            )
        )
        productsList.add(
            ProductItemModel(
                "Kartoshka fri",
                R.drawable.fri1,
                16000,
                "Gazaklar",
                "Pechdan yangi uzilgan, qarsildoq kartoshka fri",
                ProductSize(true, 16000, "Kichik"),
                ProductSize(false, 0, "O'rtacha"),
                ProductSize(false, 0, "Katta")
            )
        )

        productsList.add(
            ProductItemModel(
                "Po-Derevenski kartoshkasi",
                R.drawable.fri2,
                16000,
                "Gazaklar",
                "Pechdan yangi uzilgan, qarsildoq kartoshka fri",
                ProductSize(true, 16000, "Kichik"),
                ProductSize(false, 0, "O'rtacha"),
                ProductSize(false, 0, "Katta")
            )
        )

        productsList.add(
            ProductItemModel(
                "Coca-Cola", R.drawable.cola, 8000, "Ichimliklar", "",
                ProductSize(true, 8000, "Kichik"),
                ProductSize(true, 11000, "O'rtacha"),
                ProductSize(true, 18000, "Katta")
            )
        )
        productsList.add(
            ProductItemModel(
                "Fanta", R.drawable.fanta, 8000, "Ichimliklar", "",
                ProductSize(true, 8000, "Kichik"),
                ProductSize(true, 11000, "O'rtacha"),
                ProductSize(true, 18000, "Katta")
            )
        )
    }

    override fun clickBottom(categories: String) {
        val index = productsList.indexOfFirst { it.categoryName == categories }
        if (index>= 0){

            viewBinding.productRecyclerview.smoothScrollToPosition(index)
        }
    }

}