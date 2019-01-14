package com.advertising

import android.view.ViewGroup



interface IAdvertisingSponsor {

    fun init()

    val isVideoSponsor: Boolean
    val isOfferwallSponsor: Boolean
    val isInterstitialSponsor: Boolean
    val isBannerSponsor: Boolean

    fun isOfferwallAvailable(): Boolean
    fun isVideoAvailable(): Boolean
    fun isInterstitialAvailable(): Boolean

    fun setBannerView(banner: ViewGroup)

    fun showVideoPreRoll()
    fun showOfferWall()
    fun showInterstitial()
}