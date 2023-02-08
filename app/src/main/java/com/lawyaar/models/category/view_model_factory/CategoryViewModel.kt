package com.lawyaar.models.category.view_model_factory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.models.category.CategoryModel
import com.lawyaar.models.language.LanguageModel
import com.lawyaar.retrofit.MainRepostry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel constructor(private val repostry: MainRepostry) :
    ViewModel() {


    init {

        viewModelScope.launch(Dispatchers.IO) {
            repostry.getLaweyCategory()
        }

    }

    val category: LiveData<CategoryModel>
        get() = repostry.laweyCategory

}