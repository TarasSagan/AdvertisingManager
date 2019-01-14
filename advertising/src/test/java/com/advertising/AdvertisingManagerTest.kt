package com.advertising

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

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        advertisingManagerTest = AdvertisingManager.Builder
                .addSponsor("test1", sponsor)
                .build()
        advertisingManagerTest.init()
    }


    @Test
    fun `show full screen success`(){

        given(sponsor.isOfferwallSponsor).willReturn(true)
        given(sponsor.isOfferwallAvailable()).willReturn(true)

        advertisingManagerTest.showFullScreenOffer("test1")
                .subscribe()

        verify(sponsor).showOfferWall()
    }

    @Test
    fun `show full screen fail when isOfferwallSponsor=false`(){
        advertisingManagerTest.showFullScreenOffer("test1")
                .subscribe()
        given(sponsor.isOfferwallSponsor).willReturn(false)
        given(sponsor.isOfferwallAvailable()).willReturn(true)

        verify(sponsor, Mockito.never()).showOfferWall()
    }

    @Test
    fun `show full screen fail when isOfferAvailable=false`(){
        advertisingManagerTest.showFullScreenOffer("test1")
                .subscribe()
        given(sponsor.isOfferwallSponsor).willReturn(true)
        given(sponsor.isOfferwallAvailable()).willReturn(false)

        verify(sponsor, Mockito.never()).showOfferWall()
    }

}