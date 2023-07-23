package com.lawyaar.models.booked_session.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawyaar.core.constants.CANCELLED
import com.lawyaar.core.constants.COMPLETED
import com.lawyaar.core.constants.OPEN
import com.lawyaar.core.constants.RESCHEDULED
import com.lawyaar.models.booked_session.AppointmentBookedModel
import com.lawyaar.models.booked_session.AppointmentBookedRequest
import com.lawyaar.models.booked_session.BookedSessionModel
import com.lawyaar.retrofit.MainRepostry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookedSessionViewModel constructor(private val repostry: MainRepostry) :
    ViewModel() {


    fun getBookedSession(token: String, children: String, userID: String, sessionStatusList: List<String?>?) {
        //"clientId" : "d6943a55-4259-44bb-b954-ca3fa93f4bdb"
        // "advocateId": "7ccec5d8-c3d2-4f22-b41e-368684645b29"

        val request = AppointmentBookedRequest(
            advocateId = "",
            clientId = "",
            sessionStatusList = sessionStatusList
        )
        viewModelScope.launch(Dispatchers.IO) {
            repostry.getAppointmentBooked(token, children, userID, request)
        }
    }

    val bookedSessionLD: LiveData<AppointmentBookedModel>
        get() = repostry.getAppointmentBooked
}