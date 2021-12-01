package com.blink.blinkshopping.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.blink.blinkshopping.*
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.repository.*
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val countryRepository: CountryRepository,
    private val sendOtpRepository: SendOtpRepository,
    private val verifyOtpRepository: VeriftOtpRepository,
    private val registerUserRepository: RegisterUserRepository,
    private val loginRepository: LoginRepository,
    private val VerifyEmailOtpRepository: VerifyEmailOtpRepository,
    private val resetPwdRepository:ResetPwdRepository,
    private val browsinghistoryaddrepository: BrowsingHistoryAddRepository

) : ViewModel() {

    lateinit var browsingHistoryAddLiveData: LiveData<Resource<BrowsingHistoryAddMutation.Data>>
    fun addBrosingHistory(id: List<Int>?) {
        browsingHistoryAddLiveData = browsinghistoryaddrepository.getBrowsingHistoryAdd(id)
    }

    lateinit var countryLiveData: LiveData<Resource<GetCountriesListQuery.Data>>
    fun getCountryDetail() {
        countryLiveData = countryRepository.getCountryDetail()
    }

    lateinit var sendOtpLiveData: LiveData<Resource<SendOtpMutation.Data>>
    fun getsendOtpDetail(email: String, mobilenumber: String, type: String) {
        sendOtpLiveData = sendOtpRepository.getSendOtpDetail(email, mobilenumber, type)
    }

    lateinit var verifyOtpLiveData: LiveData<Resource<VerifyOtpMutation.Data>>
    fun getVeirfyOtpDetail(email: String, mobileOtp: String, emailOtp: String) {
        verifyOtpLiveData = verifyOtpRepository.getVeirfyOtpDetail(email, mobileOtp, emailOtp)
    }

    lateinit var registerUserLiveData: LiveData<Resource<RegisterUserMutation.Data>>
    fun getRegisterUserDetail(name: String, country: Int, dob: String, mobile_number: String, lname: String, email: String, password: String, gender: Int, isSubscribe: Boolean) {
        registerUserLiveData = registerUserRepository.getRegisterUserDetail(name, country, dob, mobile_number, lname, email, password, gender, isSubscribe)
    }

    lateinit var LoginForTokenLiveData: LiveData<Resource<LoginMutation.Data>>
    fun getLoginTokenDetail(email: String, password: String) {
        LoginForTokenLiveData = loginRepository.getLoginTokenDetail(email, password)
    }

    lateinit var forgeverifyOtpLiveData: LiveData<Resource<VerifyOtpMutation.Data>>
    fun getForgetVerifyOtpDetail(email: String, mobileOtp: String, emailOtp: String) {
        forgeverifyOtpLiveData = verifyOtpRepository.getVeirfyOtpDetail(email, mobileOtp, emailOtp)
    }

    lateinit var resetEmailOtpLiveDatay: LiveData<Resource<ResetVerifyEmailOtpMutation.Data>>
    fun getResetVerifyEmailOtpDetail(email: String,otp: String) {
        resetEmailOtpLiveDatay = VerifyEmailOtpRepository.getResetVerifyEmailOtpDetail(email, otp)
    }


    lateinit var newPasswordLiveDatay: LiveData<Resource<ResetpasswordMutation.Data>>
    fun getNewPasswordApiCall(email: String,newPassword: String) {
        newPasswordLiveDatay = resetPwdRepository.getResetPwdDetails(email, newPassword)
    }


    override fun onCleared() {
        countryRepository.clear()
        sendOtpRepository.clear()
        verifyOtpRepository.clear()
        registerUserRepository.clear()
        loginRepository.clear()
        VerifyEmailOtpRepository.clear()

        browsinghistoryaddrepository.clear()

        super.onCleared()
    }

}