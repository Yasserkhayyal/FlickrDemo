package com.android.khayal.flickrdemo.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.android.khayal.flickrdemo.repository.DataRepositoryImp
import com.android.khayal.flickrdemo.ui.main.MainFragmentViewModel
import com.android.khayal.flickrdemo.vo.Resource
import com.android.khayal.flickrdemo.vo.SearchResponse
import com.android.khayal.flickrdemo.vo.Status
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import kotlin.reflect.jvm.isAccessible

class MainFragmentViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    lateinit var repo: DataRepositoryImp
    lateinit var viewModel: MainFragmentViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainFragmentViewModel(repo)
    }

    @Test
    fun testWhenSuccessfulResponseLiveDataVariablesHaveSuccessValues() {
        val expectedResult = MutableLiveData<Resource<Array<SearchResponse.Item>>>()
        expectedResult.value = Resource.success(arrayOf())
        `when`(repo.fetchData("", "")).thenReturn(expectedResult)
        viewModel.getSearchData("", "")
        assertEquals(expectedResult.value!!.status, Status.SUCCESS)
        assertArrayEquals(expectedResult.value!!.data, viewModel.feed.value)
        assertEquals(expectedResult.value!!.message, viewModel.error.value)
        assertEquals(false, viewModel.showLoading.value)
        verify(repo).removeObserver(viewModel)
    }

    @Test
    fun testWhenFailureResponseLiveDataVariablesHaveFailureValues() {
        val expectedResult = MutableLiveData<Resource<Array<SearchResponse.Item>>>()
        expectedResult.value = Resource.error("Throwable test msg", null)
        `when`(repo.fetchData("", "")).thenReturn(expectedResult)
        viewModel.getSearchData("", "")
        assertEquals(expectedResult.value!!.status, Status.ERROR)
        assertArrayEquals(expectedResult.value!!.data, viewModel.feed.value)
        assertEquals(expectedResult.value!!.message, viewModel.error.value)
        assertEquals(false, viewModel.showLoading.value)
        verify(repo).removeObserver(viewModel)
    }

    @Test
    fun testWhenLoadingResponseLiveDataVariablesHaveLoadingValues() {
        val expectedResult = MutableLiveData<Resource<Array<SearchResponse.Item>>>()
        expectedResult.value = Resource.loading(arrayOf())
        `when`(repo.fetchData("", "")).thenReturn(expectedResult)
        viewModel.getSearchData("", "")
        assertEquals(expectedResult.value!!.status, Status.LOADING)
        assertArrayEquals(expectedResult.value!!.data, viewModel.feed.value)
        assertEquals(expectedResult.value!!.message, viewModel.error.value)
        assertEquals(true, viewModel.showLoading.value)
    }

    @Test
    fun testRepoRemoveObserverIsInvokedUponOnClearedIsInvoked(){
        MainFragmentViewModel::class.members
            .single { it.name == "onCleared" }
            .apply { isAccessible = true }
            .call(MainFragmentViewModel(repo).apply {
                viewModel = this
            })

        verify(repo).removeObserver(viewModel)
    }

}