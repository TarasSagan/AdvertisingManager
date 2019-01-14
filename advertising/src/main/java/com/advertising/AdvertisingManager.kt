package com.advertising

import com.advertising.exeptions.NoImplementedException
import com.advertising.exeptions.NoImplementedVideoException
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.RuntimeException

open class AdvertisingManager(private val sponsors: HashMap<String, IAdvertisingSponsor>)  {

    private constructor(builder: Builder) : this( builder.sponsors)

    fun init() = Completable.fromAction {
        for ((key, value) in sponsors) value.init()
    }

    fun isAvailableVideoPreRoll(sponsor: String): Single<Boolean> {
        return Single.create { emitter ->
            if (!sponsors.containsKey(sponsor)) {
                emitter.onError(NoImplementedVideoException("$sponsor video sponsor was not found "))
                return@create
            }

            if (!sponsors[sponsor]?.isVideoSponsor!!) {
                emitter.onError(NoImplementedVideoException("$sponsor video sponsor no implemented"))
                return@create
            }
            emitter.onSuccess(sponsors[sponsor]?.isVideoAvailable()!!)
        }
    }

    fun showVideoPreRoll(sponsor: String) = Completable.fromAction {
        if (sponsors[sponsor]?.isVideoSponsor!! and sponsors[sponsor]?.isVideoAvailable()!!) {
            sponsors[sponsor]?.showVideoPreRoll()
        }
    }

    fun showFullScreenOffer(sponsor: String) = Completable.fromAction {
        if (sponsors[sponsor]?.isOfferwallSponsor!! and sponsors[sponsor]?.isOfferwallAvailable()!!) {
            sponsors[sponsor]?.showOfferWall()
        }
    }

    fun showInterstitial(sponsor: String) = Completable.fromAction {
        if (sponsors[sponsor]?.isInterstitialSponsor!! and sponsors[sponsor]?.isInterstitialAvailable()!!) {
            sponsors[sponsor]?.showInterstitial()
        }
    }
    
    fun isAvailableInterstitial(sponsor: String): Single<Boolean> {
        return Single.create { emitter ->
            if (!sponsors.containsKey(sponsor) ) {
                emitter.onError(NoImplementedException("$sponsor full screen sponsor was not found"))
                return@create
            }
            if (!sponsors[sponsor]?.isInterstitialSponsor!!) {
                emitter.onError(NoImplementedException("$sponsor full screen  sponsor no implemented"))
                return@create
            }
            emitter.onSuccess(sponsors[sponsor]?.isInterstitialAvailable()!!)
        }
    }

    fun isAvailableFullScreenOffer(sponsor: String): Single<Boolean> {
        return Single.create { emitter ->
            if (!sponsors.containsKey(sponsor) ) {
                emitter.onError(NoImplementedException("$sponsor full screen sponsor was not found "))
                return@create
            }
            if (!sponsors[sponsor]?.isOfferwallSponsor!!) {
                emitter.onError(NoImplementedException("$sponsor full screen  sponsor no implemented"))
                return@create
            }
            emitter.onSuccess(sponsors[sponsor]?.isOfferwallAvailable()!!)
        }
    }

    companion object Builder {
        private val sponsors: HashMap<String, IAdvertisingSponsor> = HashMap()

        fun addSponsor(name: String, sponsor: IAdvertisingSponsor): Builder {
            sponsors[name] = sponsor
            return this
        }

        fun build(): AdvertisingManager {
            if (sponsors.isEmpty()) throw RuntimeException("No Advertising sponsors")
            return AdvertisingManager(this)
        }
    }
}
