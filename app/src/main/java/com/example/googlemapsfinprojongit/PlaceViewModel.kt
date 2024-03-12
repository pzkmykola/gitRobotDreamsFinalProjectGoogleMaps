package com.example.googlemapsfinprojongit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.InvalidationTracker
import com.example.googlemapsfinprojongit.dbmap.PlaceFB

class PlaceViewModel : ViewModel() {
    private val repo = MyApplication.getApp().repo
//    private val _listState = MutableLiveData<ListState>(ListState.EmptyList)
//    val listState: LiveData<ListState> = _listState
//    private val observer = InvalidationTracker.Observer<List<PlaceFB>> {
//        _listState.postValue(ListState.UpdatedList(list = it))
//    }

//    init {
//        this.getAll().observeForever(observer)
//    }
    fun add(title: String, location: String, urlImage:String):Boolean{
        return repo.add(title = title , location = location, urlImage = urlImage)
    }

    fun remove(place: PlaceFB){
        repo.remove(place)
    }


    sealed class ListState {
        object EmptyList:ListState()
        class UpdatedList(val list:List<PlaceFB>):ListState()
    }
}