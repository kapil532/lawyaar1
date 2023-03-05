package com.lawyaar.retrofit

import com.lawyaar.MainActivity
import com.lawyaar.ui.auth.OTPActivity
import com.lawyaar.retrofit.NetworkModule
import com.lawyaar.retrofit.RetrofitHelperObj
import com.lawyaar.ui.book_slots.BookingSlotActivity
import com.lawyaar.ui.home.HomeFragment
import com.lawyaar.ui.lawyaardetails.LawyaarDetailsActivity
import com.lawyaar.ui.payment_screen.PaymentActivity
import com.lawyaar.ui.profile.UpdateProfileActivity
import com.lawyaar.ui.slideshow.BookedAppointmentFragment
import com.lawyaar.ui.wallet.WalletActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent
{

  fun inject(mainActivity: MainActivity)
  fun inject(homeFragment: HomeFragment)
  fun inject(otpActivity : OTPActivity)

  fun inject(updateProfileActivity: UpdateProfileActivity)

  fun inject(walletActivity: WalletActivity)

  fun inject(lawyerDetailsActivity: LawyaarDetailsActivity)

  fun inject(bookingSlotActivity: BookingSlotActivity)


  fun inject(bookedAppointmentFragment: BookedAppointmentFragment)
  fun inject(paymentActivity: PaymentActivity)

}