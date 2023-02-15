package com.lawyaar.retrofit

import com.lawyaar.MainActivity
import com.lawyaar.ui.auth.OTPActivity
import com.lawyaar.retrofit.NetworkModule
import com.lawyaar.retrofit.RetrofitHelperObj
import com.lawyaar.ui.home.HomeFragment
import com.lawyaar.ui.profile.UpdateProfileActivity
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

}