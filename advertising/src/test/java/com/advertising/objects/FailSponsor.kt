package com.advertising.objects

import android.view.ViewGroup
import com.advertising.IAdvertisingSponsor

class FailSponsor : IAdvertisingSponsor {

    var videoShowCount = 0
    var bannerShowCount = 0
    var offerwallShowCount = 0
    var interstitialShowCount = 0

    override fun init() {}

    override val isVideoSponsor: Boolean
        get() = false

    override val isOfferwallSponsor: Boolean
        get() = false

    override val isInterstitialSponsor: Boolean
        get() = false

    override val isBannerSponsor: Boolean
        get() = false

    override fun isOfferwallAvailable(): Boolean {
        return false
    }

    override fun isVideoAvailable(): Boolean {
        return false
    }

    override fun isInterstitialAvailable(): Boolean {
        return false
    }

    override fun setBannerView(banner: ViewGroup) {
        bannerShowCount++
    }

    override fun showVideoPreRoll() {
        videoShowCount++
    }

    override fun showOfferWall() {
        offerwallShowCount++
    }

    override fun showInterstitial() {
        interstitialShowCount++
    }
}