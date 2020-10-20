package com.kalashnyk.denys.defaultproject.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.kalashnyk.denys.defaultproject.presentation.base.ItemsLoadListener
import com.kalashnyk.denys.moduleproject.data_source.data.BaseCardModel
import com.kalashnyk.denys.moduleproject.data_source.data.ItemClickListener
import com.kalashnyk.denys.moduleproject.data_source.database.converters.ConverterFactory
import com.kalashnyk.denys.moduleproject.data_source.database.entity.MessagesEntity
import com.kalashnyk.denys.moduleproject.usecases.MessagesUseCases
import java.util.NoSuchElementException

/**
 *
 */
class MessagesViewModel(private val messagesUseCases: MessagesUseCases) : BasePagingViewModel() {

    init {
        initPagedConfig()
    }

    /**
     *
     */
    fun initLiveData(type: String, listener: ItemsLoadListener<PagedList<BaseCardModel>>, clickListener: ItemClickListener<MessagesEntity>) {
        itemLoadedListener = listener
        initPagedListLiveData(messagesUseCases.getCardsFactory(type, ConverterFactory(clickListener)))
    }


    /**
     *
     */
    fun getPagedList(): LiveData<PagedList<BaseCardModel>> = listCards

    override fun fetchData(screenType : String) {
        compositeDisposable.add(messagesUseCases.fetchData(screenType)
            .subscribe({ setRefreshing(false) }, { throwable ->
                if (throwable is NoSuchElementException) {
                    itemLoadedListener.onItemsLoaded(null)
                } else {
                    throwable.message?.let { itemLoadedListener.onLoadError(it) }
                }
                setRefreshing(false)
            })
        )
    }

    override fun rangeData(screenType: String, itemId: String) {
        setLoading(true)
        compositeDisposable.add(messagesUseCases.fetchNext(screenType, itemId)
            .subscribe({ setLoading(false) },
                { setLoading(false) }
            )
        )
    }

    override fun clearCachedItems(screenType : String) {
        messagesUseCases.deleteCachedItems(screenType)
    }
}