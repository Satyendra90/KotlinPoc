package com.kotlin.poc.ui.newsfeed

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.kotlin.poc.model.ApiDataWrapper
import com.kotlin.poc.model.NewsFeedResponse
import com.kotlin.poc.ui.utils.RxImmediateSchedulerRule
import com.kotlin.poc.webservice.NewsFeedApi
import io.reactivex.Observable
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class NewsFeedViewModelTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    // Test rule for RxJava to run synchronously in unit test
    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    private lateinit var newsFeedViewModel: NewsFeedViewModel

    @Mock
    private lateinit var newsFeedApiService: NewsFeedApi

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        newsFeedViewModel = NewsFeedViewModel(newsFeedApiService)
    }

    @Test
    fun `get news feed data with success response`() {

        //mock data
        val newsFeedResponse = NewsFeedResponse("About India", ArrayList())
        //mock api response
        Mockito.`when`(newsFeedApiService.getNewsFeed()).thenAnswer {
            return@thenAnswer Observable.just(newsFeedResponse)
        }

        val observer = mock(Observer::class.java) as Observer<ApiDataWrapper<NewsFeedResponse>>
        newsFeedViewModel.getNewsFeedListLiveData().observeForever(observer)
        newsFeedViewModel.loadNewsFeed()

        //verify
        assertNotNull(newsFeedViewModel.getNewsFeedListLiveData().value)
        assertEquals(newsFeedResponse.title, newsFeedViewModel.getNewsFeedListLiveData().value?.data?.title)
    }

    /*@Test
    fun `get news feed data with error response`() {

        //mock data
        val socketException = SocketException("No internet connection")
        //mock api response
        Mockito.`when`(newsFeedApiService.getNewsFeed()).thenAnswer {
            return@thenAnswer Observable.error<SocketException>(socketException)
        }

        val observer = mock(Observer::class.java) as Observer<ApiDataWrapper<NewsFeedResponse>>
        newsFeedViewModel.getNewsFeedListLiveData().observeForever(observer)
        newsFeedViewModel.loadNewsFeed()

        // Verify
        assertNotNull(newsFeedViewModel.getNewsFeedListLiveData().value)
        assertEquals(socketException.message, newsFeedViewModel.getNewsFeedListLiveData().value?.error?.msg)
    }*/

    @After
    fun tearDown() {

    }
}