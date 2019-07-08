package com.advertising.objects

import android.view.ViewGroup
import com.advertising.IAdvertisingSponsor

public class TrueSponsor : IAdvertisingSponsor {

    var videoShowCount = 0
    var bannerShowCount = 0
    var offerwallShowCount = 0
    var interstitialShowCount = 0

    override fun init() {}

    override val isVideoSponsor: Boolean
        get() = true

    override val isOfferwallSponsor: Boolean
        get() = true

    override val isInterstitialSponsor: Boolean
        get() = true

    override val isBannerSponsor: Boolean
        get() = true

    override fun isOfferwallAvailable(): Boolean {
        return true
    }

    override fun isVideoAvailable(): Boolean {
        return true
    }

    override fun isInterstitialAvailable(): Boolean {
        return true
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