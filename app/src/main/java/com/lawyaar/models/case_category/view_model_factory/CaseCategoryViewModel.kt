package com.lawyaar.models.case_category.view_model_factory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.models.case_category.CaseCategory
import com.lawyaar.models.category.CategoryModel
import com.lawyaar.retrofit.MainRepostry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CaseCategoryViewModel constructor(private val repostry: MainRepostry) :
    ViewModel() {


    init {

        viewModelScope.launch(Dispatchers.IO) {
            repostry.getCaseCategory()
        }

    }

    val category: LiveData<CaseCategory>
        get() = repostry.caseCategory

}