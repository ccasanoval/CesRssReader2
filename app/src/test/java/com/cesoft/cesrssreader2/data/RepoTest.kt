package com.cesoft.cesrssreader2.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cesoft.cesrssreader2.data.local.FeedDb
import com.cesoft.cesrssreader2.data.local.dao.FeedDao
import com.cesoft.cesrssreader2.data.remote.FeedService
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class RepoTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dao: FeedDao
    @Mock
    private lateinit var service: FeedService
    private lateinit var repository: Repo

    private val testMatchId = "576558"

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        val db = mock(FeedDb::class.java)
        `when`(db.feedDao).thenReturn(dao)
        `when`(db.runInTransaction(any())).thenCallRealMethod()

        //repository = Repo(db, dao, service, TestContextProvider())
    }
/*
    @Test
    fun loadMatchDetail() {
        val dbData = MutableLiveData<Match>()
        `when`(dao.getMatchDetail(testMatchId)).thenReturn(dbData)

        val match = TestUtil.createMatchDetailRes(testMatchId)
        val call = successScheduleCall(match)
        `when`(service.getMatchDetail(testMatchId)).thenReturn(call)

        val data = repository.getEventDetail(testMatchId)
        verify(dao).getMatchDetail(testMatchId)
        verifyNoMoreInteractions(service)

        val obs = mock<Observer<Resource<Match>>>()
        data.observeForever(obs)
        verifyNoMoreInteractions(service)
        verify(obs).onChanged(Resource.loading(null))

        val updateDbData = MutableLiveData<Match>()
        `when`(dao.getMatchDetail(testMatchId)).thenReturn(updateDbData)

        dbData.postValue(null)
        verify(service).getMatchDetail(testMatchId)
        verify(dao).saveMatches(match.events!!)

        updateDbData.postValue(match.events!![0])
        verify(obs).onChanged(Resource.success(match.events!![0]))
    }
*/
}