package com.example.myapplication.dhbatchu.ui


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.ProductType
import com.android.billingclient.api.BillingFlowParams.ProductDetailsParams
import com.android.billingclient.api.QueryProductDetailsParams.Product
import com.core.BaseFragment
import com.example.myapplication.Constants
import com.example.myapplication.Preference
import com.example.myapplication.databinding.FragmentStoreBinding
import com.example.myapplication.dhbatchu.utils.Utils
import com.google.common.collect.ImmutableList

class StoreFragment: BaseFragment<FragmentStoreBinding>() {

    private val adapter by lazy { PurchaseInAppAdapter() }
    private var billingClient: BillingClient? = null
//    private var productDetailsList = mutableListOf<ProductDetails>()
    private lateinit var  prefsHelper: Preference

    override fun getLayoutBinding(): FragmentStoreBinding {
        return FragmentStoreBinding.inflate(layoutInflater)
    }

    override fun updateUI(savedInstanceState: Bundle?) {
        initViews()
        binding.imvBack.setOnClickListener { onBackPressed() }
    }

    private fun initViews() {
        binding.listData.layoutManager = LinearLayoutManager(context)
        binding.listData.adapter = adapter
        adapter.setOnItemClickListener { item, pos ->
            launchPurchaseFlow(item)
        }
        billingClient = context?.let {
            BillingClient.newBuilder(it)
                .enablePendingPurchases()
                .setListener { billingResult: BillingResult, list: List<Purchase?>? ->
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && list != null) {
                        for (purchase in list) {
                            handlePurchase(purchase)
                        }
                    }
                }.build()
        }
        establishConnection()
    }

    private fun launchPurchaseFlow(productDetails: ProductDetails) {
        val productDetailsParamsList = ImmutableList.of(
            ProductDetailsParams.newBuilder()
                .setProductDetails(productDetails)
                .build()
        )
        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()
        activity?.let { billingClient?.launchBillingFlow(it, billingFlowParams) }
    }

    private fun handlePurchase(purchase: Purchase?) {
        val consumeParams = purchase?.purchaseToken?.let {
            ConsumeParams.newBuilder()
                .setPurchaseToken(it)
                .build()
        }
        val listener =
            ConsumeResponseListener { billingResult, purchaseToken ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // Handle the success of the consume operation.
                    //                    verifyInAppPurchase(purchase);
                }
            }
        if (consumeParams != null) {
            billingClient?.consumeAsync(consumeParams, listener)
        }
        verifyInAppPurchase(purchase)
    }

    private fun establishConnection() {
        billingClient?.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    showProducts()
                }
            }
            override fun onBillingServiceDisconnected() {
                establishConnection()
            }
        })
    }

    private fun verifyInAppPurchase(purchases: Purchase?) {
        val acknowledgePurchaseParams = purchases?.purchaseToken?.let {
            AcknowledgePurchaseParams
                .newBuilder()
                .setPurchaseToken(it)
                .build()
        }
        if (acknowledgePurchaseParams != null) {
            billingClient?.acknowledgePurchase(acknowledgePurchaseParams) { billingResult: BillingResult ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    allowMultiplePurchases(purchases)
                    setupResult(purchases.products[0], purchases.quantity)
                }
            }
        }
    }

    private fun allowMultiplePurchases(purchase: Purchase) {
        // gọi thằng này để mua nhiều lần
        val consumeParams = ConsumeParams
            .newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()
        billingClient!!.consumeAsync(consumeParams) { billingResult, s ->
            Toast.makeText(
                context,
                " Resume item ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupResult(proId: String, quantity: Int) {
        val newGold = prefsHelper.getCoin() + Utils.getCoinFromKey(proId) * quantity
        prefsHelper.setCoin( newGold)
    }

    private fun showProducts() {
        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(getInAppProductList())
            .build()
        billingClient?.queryProductDetailsAsync(params) { billingResult: BillingResult?, prodDetailsList: List<ProductDetails> ->
            Handler(Looper.getMainLooper()).postDelayed({
                if (prodDetailsList.isEmpty()) binding.txtNoResult.visibility = View.VISIBLE
                adapter.items = prodDetailsList as MutableList<ProductDetails>
            },1000)
        }
    }

    private fun getInAppProductList(): ImmutableList<Product> {
        return ImmutableList.of<Product>(
            Product.newBuilder()
                .setProductId(Constants.KEY_COIN)
                .setProductType(ProductType.INAPP)
                .build(),//Product 1
            Product.newBuilder()
                .setProductId(Constants.KEY_10_COIN)
                .setProductType(ProductType.INAPP)
                .build(),  //Product 2
            Product.newBuilder()
                .setProductId(Constants.KEY_20_COIN)
                .setProductType(ProductType.INAPP)
                .build(),  //Product 3
            Product.newBuilder()
                .setProductId(Constants.KEY_50_COIN)
                .setProductType(ProductType.INAPP)
                .build(),  //Product 4
            Product.newBuilder()
                .setProductId(Constants.KEY_100_COIN)
                .setProductType(ProductType.INAPP)
                .build(),  //Product 5
            Product.newBuilder()
                .setProductId(Constants.KEY_150_COIN)
                .setProductType(ProductType.INAPP)
                .build(),  //Product 6
            Product.newBuilder()
                .setProductId(Constants.KEY_200_COIN)
                .setProductType(ProductType.INAPP)
                .build()
        )
    }
}