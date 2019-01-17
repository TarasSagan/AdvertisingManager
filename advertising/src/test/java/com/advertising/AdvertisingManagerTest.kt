package com.advertising

import com.advertising.exeptions.NoImplementedException
import com.advertising.exeptions.NoImplementedVideoException
import com.advertising.objects.FailSponsor
import com.advertising.objects.TrueSponsor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class AdvertisingManagerTest {

    @Mock
    lateinit var sponsor: IAdvertisingSponsor

    lateinit var advertisingManagerTest: AdvertisingManager

    lateinit var failSponsor: FailSponsor

    lateinit var trueSponsor: TrueSponsor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        failSponsor = FailSponsor()
        trueSponsor = TrueSponsor()

        advertisingManagerTest = AdvertisingManager.Builder
                .addSponsor("test1", sponsor)
                .addSponsor("trueSponsor", trueSponsor)
                .addSponsor("failSponsor", failSponsor)
                .build()
        advertisingManagerTest.init()
    }


    @Test
    //with Mockito
    fun `show full screen success`() {

        given(sponsor.isOfferwallSponsor).willReturn(true)
        given(sponsor.isOfferwallAvailable()).willReturn(true)

        advertisingManagerTest.showFullScreenOffer("test1")
                .test()

        verify(sponsor).showOfferWall()
    }

    @Test
    //with Mockito
    fun `show full screen fail when isOfferwallSponsor=false`() {
        advertisingManagerTest.showFullScreenOffer("test1")
                .test()
        given(sponsor.isOfferwallSponsor).willReturn(false)
        given(sponsor.isOfferwallAvailable()).willReturn(true)

        verify(sponsor, Mockito.never()).showOfferWall()
    }

    @Test
    //with Mockito
    fun `show full screen fail when isOfferAvailable=false`() {
        advertisingManagerTest.showFullScreenOffer("test1")
                .test()
        given(sponsor.isOfferwallSponsor).willReturn(true)
        given(sponsor.isOfferwallAvailable()).willReturn(false)

        verify(sponsor, Mockito.never()).showOfferWall()
    }

    @Test
    //without Mockito
    fun `showVideoPreRoll success`() {
        Assert.assertEquals(0, trueSponsor.videoShowCount)
        advertisingManagerTest.showVideoPreRoll("trueSponsor").test()
        Assert.assertEquals(1, trueSponsor.videoShowCount)
    }


    @Test
    //without Mockito
    fun `showVideoPreRoll fail`() {
        Assert.assertEquals(0, failSponsor.videoShowCount)
        advertisingManagerTest.showVideoPreRoll("failSponsor").test()
        Assert.assertNotEquals(1, failSponsor.videoShowCount) //assert thatmethod showVideoPreRoll not called
    }


    @Test
    //without Mockito
    fun `showInterstitial success`() {
        Assert.assertEquals(0, trueSponsor.interstitialShowCount)
        advertisingManagerTest.showInterstitial("trueSponsor").test()
        Assert.assertEquals(1, trueSponsor.interstitialShowCount)
    }

    @Test
    //without Mockito
    fun `showInterstitial fail`() {
        Assert.assertEquals(0, failSponsor.interstitialShowCount)
        advertisingManagerTest.showInterstitial("failSponsor").test()
        Assert.assertNotEquals(1, failSponsor.interstitialShowCount) //assert thatmethod showVideoPreRoll not called
    }

    @Test
    //with Mockito
    fun `isAvailableVideoPreRoll true`() {
        given(sponsor.isVideoSponsor).willReturn(true)

        val testObserver = advertisingManagerTest.isAvailableVideoPreRoll("test1").test()

        verify(sponsor).isVideoAvailable()!!
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        testObserver.isDisposed
    }

    @Test
    //with Mockito
    fun `isAvailableVideoPreRoll false`() {
        given(sponsor.isVideoSponsor).willReturn(false)

        val testObserver = advertisingManagerTest.isAvailableVideoPreRoll("test1").test()

        verify(sponsor, Mockito.never()).isVideoAvailable()!!
        testObserver.assertNoValues()
        testObserver.assertNotComplete()
        testObserver.assertError(NoImplementedVideoException::class.java)
        Assert.assertTrue(testObserver.errors().get(0).message.equals("test1 video sponsor no implemented"))

        testObserver.assertValueCount(0)
    }


    @Test
    //with Mockito
    fun `isAvailableInterstitial true`() {
        given(sponsor.isInterstitialSponsor).willReturn(true)

        val testObserver = advertisingManagerTest.isAvailableInterstitial("test1").test()

        verify(sponsor).isInterstitialSponsor
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        testObserver.isDisposed
    }

    @Test
    //with Mockito
    fun `isAvailableInterstitial false`() {
        given(sponsor.isInterstitialSponsor).willReturn(false)

        val testObserver = advertisingManagerTest.isAvailableInterstitial("test1").test()

        verify(sponsor, Mockito.never()).isVideoAvailable()
        testObserver.assertNoValues()
        testObserver.assertNotComplete()
        testObserver.assertError(NoImplementedException::class.java)
        Assert.assertTrue(testObserver.errors().get(0).message.equals("test1 full screen  sponsor no implemented"))

        testObserver.assertValueCount(0)
    }



    @Test
    //with Mockito
    fun `isAvailableFullScreenOffer true`() {
        given(sponsor.isOfferwallSponsor).willReturn(true)

        val testObserver = advertisingManagerTest.isAvailableFullScreenOffer("test1").test()

        verify(sponsor).isOfferwallAvailable()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        testObserver.isDisposed
    }

    @Test
    //with Mockito
    fun `isAvailableFullScreenOffer false`() {
        given(sponsor.isOfferwallSponsor).willReturn(false)

        val testObserver = advertisingManagerTest.isAvailableFullScreenOffer("test1").test()

        verify(sponsor, Mockito.never()).isOfferwallAvailable()
        testObserver.assertNoValues()
        testObserver.assertNotComplete()
        testObserver.assertError(NoImplementedException::class.java)
        Assert.assertTrue(testObserver.errors().get(0).message.equals("test1 full screen  sponsor no implemented"))

        testObserver.assertValueCount(0)
    }
}