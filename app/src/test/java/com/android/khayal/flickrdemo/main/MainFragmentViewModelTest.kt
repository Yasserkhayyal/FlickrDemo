package com.android.khayal.flickrdemo.main

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.khayal.flickrdemo.data.DataRepository
import com.android.khayal.flickrdemo.models.SearchResponse
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.lang.Thread.sleep

class MainFragmentViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val repo: DataRepository = mock<DataRepository>(DataRepository::class.java)
    private val disposables: CompositeDisposable = mock(CompositeDisposable::class.java)
    private val viewModel: MainFragmentViewModel = MainFragmentViewModel(repository = repo,disposables = disposables)

    @Before
    fun setUp() {
        Mockito.clearInvocations(repo)
    }

    @Test
    fun `with api success should emmit value`() {
        val content = SearchResponse.Content(arrayOf())

        val observable = Observable.just(content)
        `when`(repo.fetchData("", "")).thenReturn(observable)

        viewModel.getSearchData("", "")

        sleep(100)

        assertArrayEquals(content.items, viewModel.feed.value)
    }

    @Test
    fun `with api failure should emit error`() {
        val observable = Observable.error<SearchResponse.Content>(Throwable())
        `when`(repo.fetchData("", "")).thenReturn(observable)

        assertArrayEquals(null, viewModel.feed.value)
    }


    @Test
    fun `disposables cleared on MainFragment destroyed`() {
        viewModel.onCleared()
        verify(disposables).dispose()
        assertEquals(0, disposables.size())
    }

}