package com.blink.blinkshopping.ui.login

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.AdapterView.*
import android.widget.TextView.OnEditorActionListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.blink.blinkshopping.R
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.customViews.CustomTextView
import com.blink.blinkshopping.databinding.FragmentLoginBinding
import com.blink.blinkshopping.models.BrowseSavingModel
import com.blink.blinkshopping.models.Countries
import com.blink.blinkshopping.otptextview.OTPListener
import com.blink.blinkshopping.util.PasswordStrength
import com.blink.blinkshopping.util.SharedPrefForHistory
import com.blink.blinkshopping.util.SharedStorage
import com.blink.blinkshopping.util.Utils.isValidEmail
import com.blink.blinkshopping.utils.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.startup.infobase.utils.Globals.ConvertingCountryList
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.ForgetLt
import kotlinx.android.synthetic.main.fragment_register.ResetLt
import kotlinx.android.synthetic.main.fragment_register.UserregisterSucusseLt
import kotlinx.android.synthetic.main.fragment_register.callHelpLineAtForget
import kotlinx.android.synthetic.main.fragment_register.callHelpLineAtVerify
import kotlinx.android.synthetic.main.fragment_register.chatHelpLineAtForget
import kotlinx.android.synthetic.main.fragment_register.clickVerifyEmailOtp
import kotlinx.android.synthetic.main.fragment_register.countrySpinner
import kotlinx.android.synthetic.main.fragment_register.discardResetPwd
import kotlinx.android.synthetic.main.fragment_register.editEmail
import kotlinx.android.synthetic.main.fragment_register.editEmail1
import kotlinx.android.synthetic.main.fragment_register.editEmailAtForget
import kotlinx.android.synthetic.main.fragment_register.editFName
import kotlinx.android.synthetic.main.fragment_register.editLName
import kotlinx.android.synthetic.main.fragment_register.editMobile
import kotlinx.android.synthetic.main.fragment_register.editNewCPass
import kotlinx.android.synthetic.main.fragment_register.editNewPass
import kotlinx.android.synthetic.main.fragment_register.editPass
import kotlinx.android.synthetic.main.fragment_register.editPassword
import kotlinx.android.synthetic.main.fragment_register.editPhone
import kotlinx.android.synthetic.main.fragment_register.editRegiEmail
import kotlinx.android.synthetic.main.fragment_register.errorForPwd
import kotlinx.android.synthetic.main.fragment_register.errorTerms
import kotlinx.android.synthetic.main.fragment_register.eye_img
import kotlinx.android.synthetic.main.fragment_register.eye_img_new_cpass
import kotlinx.android.synthetic.main.fragment_register.eye_img_new_pass
import kotlinx.android.synthetic.main.fragment_register.eye_img_pass
import kotlinx.android.synthetic.main.fragment_register.forgetTxt
import kotlinx.android.synthetic.main.fragment_register.getUpdate
import kotlinx.android.synthetic.main.fragment_register.headerLt
import kotlinx.android.synthetic.main.fragment_register.loadingProgress
import kotlinx.android.synthetic.main.fragment_register.loginRL
import kotlinx.android.synthetic.main.fragment_register.loginTxt
import kotlinx.android.synthetic.main.fragment_register.ltPwdStregth
import kotlinx.android.synthetic.main.fragment_register.mobileLt
import kotlinx.android.synthetic.main.fragment_register.password_strength
import kotlinx.android.synthetic.main.fragment_register.progressBar
import kotlinx.android.synthetic.main.fragment_register.regVerifySubmitOtp
import kotlinx.android.synthetic.main.fragment_register.regiserSuccessBtn
import kotlinx.android.synthetic.main.fragment_register.registerClose
import kotlinx.android.synthetic.main.fragment_register.registerRL
import kotlinx.android.synthetic.main.fragment_register.registerVerifyRL
import kotlinx.android.synthetic.main.fragment_register.resendOtp
import kotlinx.android.synthetic.main.fragment_register.resetSubmitBtn
import kotlinx.android.synthetic.main.fragment_register.reset_otp_view
import kotlinx.android.synthetic.main.fragment_register.signinBtn
import kotlinx.android.synthetic.main.fragment_register.submitResetOtpTxt
import kotlinx.android.synthetic.main.fragment_register.termsCondition
import kotlinx.android.synthetic.main.fragment_register.tv_timer
import kotlinx.android.synthetic.main.fragment_register.txtRegister
import kotlinx.android.synthetic.main.fragment_register.txtRegisterClick
import kotlinx.android.synthetic.main.fragment_register.txtSignin
import kotlinx.android.synthetic.main.fragment_register.verify_email_otp_view
import kotlinx.android.synthetic.main.fragment_register.verify_mobile_otp_view
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class LoginFragment : Fragment(), HasSupportFragmentInjector {

    fun LoginFragment() {
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override fun supportFragmentInjector() = dispatchingAndroidInjector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var loginViewModel: LoginViewModel
    lateinit var binding: FragmentLoginBinding

    private var datePickerDialog: DatePickerDialog? = null
    private var calendar: Calendar? = null

    //  var baseArrayList: MutableList<BaseConfurableArrayList> = mutableListOf()

    private val countrySpinnerList = ArrayList<String>()
    var selectedCountry = ""
    var timeStamp =
        SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().time)

    var selectedDOB = timeStamp
    var countriesList: MutableList<Countries> = mutableListOf()
    var genderSelected: Int = 1
    var getUpdates: Boolean = false
    var getTermsCondtions: Boolean = false
    var fromWhichPage = ""

    var sharedStorage: SharedStorage? = null
    var sharedStorageHistory: SharedPrefForHistory? = null

    var root: View? = null
    var countDownTimer: CountDownTimer? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login
            , container, false)
        root = binding.getRoot()

        //activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
       // getDialog()!!.getWindow()
           // .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        sharedStorage = SharedStorage.getInstance(this@LoginFragment.context!!)
        sharedStorageHistory = SharedPrefForHistory.getInstance(this@LoginFragment.context!!)

        return binding.root
    }

    companion object {
        private const val From = "from"
        private const val Layout = "layout"
        private const val Category = "category"
        fun newInstance(from: String, layout: String, category: String): LoginFragment? {
            val fragment: LoginFragment = LoginFragment()
            val args = Bundle()
            args.putString(From, from)
            args.putString(Layout, layout)
            args.putString(Category, category)
            fragment.setArguments(args)
            return fragment
        }
    }


    //override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    private fun setMargins(
        view: View,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val p: ViewGroup.MarginLayoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
            val scale: Float = activity!!.getResources().getDisplayMetrics().density
            // convert the DP into pixel
            val l = (left * scale + 0.5f).toInt()
            val r = (right * scale + 0.5f).toInt()
            val t = (top * scale + 0.5f).toInt()
            val b = (bottom * scale + 0.5f).toInt()
            p.setMargins(l, t, r, b)
            view.requestLayout()
        }
    }
/*
    override fun onCreateDialog(savedInstanceState: Bundle?): BottomSheetDialog {
        val dialog = BottomSheetDialog(requireContext(), theme)

        dialog.setOnShowListener {
            dialog.behavior.setBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        dialog.dismiss()
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (!slideOffset.isNaN()) dialog.window?.setDimAmount(0.5f - ((slideOffset * -1) / 2))
                }
            })

//            val bottomSheet =
//                dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
//            BottomSheetBehavior.from<FrameLayout?>(bottomSheet!!).state =  BottomSheetBehavior.STATE_EXPANDED
//            BottomSheetBehavior.from<FrameLayout?>(bottomSheet!!).skipCollapsed = true
//            BottomSheetBehavior.from<FrameLayout?>(bottomSheet).isHideable = true
//            dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        }

        return dialog
    }
*/

    /*override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED
        }
        return bottomSheetDialog
    }
*/
    override fun onStart() {
        super.onStart()
        /*dialog?.also {
            val bottomSheet = dialog?.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
            val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
            val layout =
                root?.findViewById(R.id.bottom_sheet) as CardView //rootLayout is root of your fragment layout.
            layout.viewTreeObserver?.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    try {
                        layout.viewTreeObserver.removeGlobalOnLayoutListener(this)
                        behavior.peekHeight = layout.height
                        view?.requestLayout()
                    } catch (e: Exception) {
                    }
                }
            })
        }*/
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
        loginViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner

        // (view?.parent as View).setBackgroundColor(getResources().getColor(R.color.extrashadow))  //TODO

        val resources = resources

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            assert(view != null)
            val parent = view?.parent as View
            val layoutParams = parent.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.setMargins(
                0,
                0,
                0,
                0
            )
            parent.layoutParams = layoutParams
        }


        // setMargins(binding.bottomSheet,0,50,0,0)

        //binding.data = layoutLiveData
        calendar = Calendar.getInstance()

        txtSignUpR.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.registerFragment);
        }

        txtRegister.setOnClickListener {
            txtSignin.setTextColor(activity!!.resources.getColor(R.color.view_color))
            txtRegister.setTextColor(activity!!.resources.getColor(R.color.price_blue_color))
            loginRL.visibility = View.GONE
            registerRL.visibility = View.VISIBLE
            getCountryCall()
        }

        getCountryCall()

        registerClose.setOnClickListener {
            if (fromWhichPage.equals("frmRegister")) {
                emptyVeriyEmailMobile()
            } else {
                mListener!!.onItemClick("closeSheet")
            }
        }

        //for Login ui
        //editEmail.addTextChangedListener(MyTextWatcher(editEmail, binding))
        //editPassword.addTextChangedListener(MyTextWatcher(editPassword, binding))

        editEmail.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            val isSoftImeEvent = (event == null
                    && (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEND))
            if (isSoftImeEvent) {
                val email: String = binding.editEmail.getText().toString().trim()
                if (email.isEmpty()) {
                    binding.editEmail.background =
                        view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                    binding.errorEmail.setText(resources.getString(R.string.is_required))
                    binding.errorEmail.visibility = View.VISIBLE
                } else if (!isValidEmail(email)) {
                    binding.editEmail.background =
                        view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                    binding.errorEmail.setText(resources.getString(R.string.error_email))
                    binding.errorEmail.visibility = View.VISIBLE
                } else {
                    binding.editPassword.requestFocus()
                    binding.editEmail.background =
                        view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                    binding.errorEmail.visibility = View.GONE
                }
                return@OnEditorActionListener true
            }
            false
        })


        editEmailAtForget.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            val isSoftImeEvent = (event == null
                    && (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEND))
            if (isSoftImeEvent) {
                val email: String = binding.editEmailAtForget.getText().toString().trim()
                if (email.isEmpty()) {
                    binding.editEmailAtForget.background =
                        view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                    binding.errorForgetEmail.setText(resources.getString(R.string.is_required))
                    binding.errorForgetEmail.visibility = View.VISIBLE
                } else if (!isValidEmail(email)) {
                    binding.editEmailAtForget.background =
                        view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                    binding.errorForgetEmail.setText(resources.getString(R.string.error_email))
                    binding.errorForgetEmail.visibility = View.VISIBLE
                } else {
                    binding.editEmailAtForget.background =
                        view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                    binding.errorForgetEmail.visibility = View.GONE
                }
                return@OnEditorActionListener true
            }
            false
        })




        editPassword.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            val isSoftImeEvent = (event == null
                    && (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEND))
            if (isSoftImeEvent) {
                val pwd: String = binding.editPassword.getText().toString().trim()
                if (TextUtils.isEmpty(pwd)) {
                    binding.ltpwd.background =
                        view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                    //binding.editLName.requestFocus()
                    binding.errorPwd.visibility = View.VISIBLE
                } else {
                    binding.ltpwd.background = view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                    binding.errorPwd.visibility = View.GONE
                    loginValidatioons(binding)
                }
                return@OnEditorActionListener true
            }
            false
        })



        eye_img.setOnClickListener {
            togglePassVisability(activity!!, editPassword, eye_img)
        }

        loginTxt.setSingleClickListener {
            loginValidatioons(binding)
        }
        resendOtp.setOnClickListener {
            clickVerifyEmailOtp.performClick()
        }
        forgetTxt.setSingleClickListener {
            binding.editEmail.background = view!!.resources.getDrawable(R.drawable.box_bg_1dp)
            binding.errorEmail.visibility = View.GONE
            ForgetLt.visibility = View.VISIBLE
            loginRL.visibility = View.GONE
            headerLt.visibility = View.GONE

            editEmailAtForget.text = editEmail.getText()
            /*val email: String = binding.editEmail.getText().toString().trim()
            if (email.isEmpty()) {
                binding.editEmail.background =
                        view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                binding.editEmail.requestFocus()
                binding.errorEmail.setText(resources.getString(R.string.is_required))
                binding.errorEmail.visibility = View.VISIBLE
            } else if (!isValidEmail(email)) {
                binding.editEmail.background =
                    view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                binding.editEmail.requestFocus()
                binding.errorEmail.setText(resources.getString(R.string.error_email))
                binding.errorEmail.visibility = View.VISIBLE
            }else{
                binding.editEmail.background = view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                binding.errorEmail.visibility = View.GONE
                ForgetLt.visibility = View.VISIBLE
                loginRL.visibility = View.GONE
                headerLt.visibility = View.GONE

                editEmailAtForget.text = editEmail.getText()
            }*/
        }

        clickVerifyEmailOtp.setOnClickListener {
//            val email: String = editEmailAtForget.getText().toString().trim()
//            if (email.isEmpty() || !isValidEmail(email)) {
//                binding.editEmailAtForget.background =
//                    view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
//                binding.editEmailAtForget.requestFocus()
//                binding.errorForgetEmail.visibility = View.VISIBLE
//            } else {
//                binding.editEmailAtForget.background =
//                    view!!.resources.getDrawable(R.drawable.box_bg_1dp)
//                binding.errorForgetEmail.visibility = View.GONE
//                sendOtp(
//                    binding.editEmailAtForget.text.toString().trim(),
//                    "",
//                    "login", "frmForget"
//                )
//            }

            val email: String = binding.editEmailAtForget.getText().toString().trim()
            if (email.isEmpty()) {
                binding.editEmailAtForget.background =
                    view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                binding.errorForgetEmail.setText(resources.getString(R.string.is_required))
                binding.errorForgetEmail.visibility = View.VISIBLE
            } else if (!isValidEmail(email)) {
                binding.editEmailAtForget.background =
                    view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                binding.errorForgetEmail.setText(resources.getString(R.string.error_email))
                binding.errorForgetEmail.visibility = View.VISIBLE
            } else {
                binding.editEmailAtForget.background =
                    view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                binding.errorForgetEmail.visibility = View.GONE

                sendOtp(
                    binding.editEmailAtForget.text.toString().trim(),
                    "",
                    "login", "frmForget"
                )
            }

        }


        resendOtp.setOnClickListener {
            val email: String = editEmailAtForget.getText().toString().trim()
            if (email.isEmpty() || !isValidEmail(email)) {
                binding.editEmailAtForget.background =
                    view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                binding.editEmailAtForget.requestFocus()
                binding.errorForgetEmail.visibility = View.VISIBLE
            } else {
                binding.editEmailAtForget.background =
                    view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                binding.errorForgetEmail.visibility = View.GONE

                sendOtp(
                    binding.editEmailAtForget.text.toString().trim(),
                    "",
                    "login", "frmForget"
                )
            }
        }


        //for Register Ui
        //txtDob.setText(selectedDOB)
        countrySpinner.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    selectedCountry = countriesList.get(position - 1).mobile_code
                } else {
                    selectedCountry = ""
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })
        /*lt_dob.setOnClickListener(View.OnClickListener {

            datePickerDialog = DatePickerDialog(
                activity,
                OnDateSetListener { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                    calendar!!.set(Calendar.YEAR, year)
                    calendar!!.set(Calendar.MONTH, month)
                    calendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    txtDob.setText(updateDate("dd-MM-yyyy"))
                    selectedDOB = updateDate("dd-MM-yyyy")!!.toString()
                },
                calendar!!.get(Calendar.YEAR),
                calendar!!.get(Calendar.MONTH),
                calendar!!.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog!!.getDatePicker().setMaxDate(System.currentTimeMillis())
            datePickerDialog!!.show()

        })*/
       /* radio_gender.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.maleRadio -> genderSelected = 1
                R.id.femaleRadio -> genderSelected = 2
            }
        })*/
        getUpdate?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                getUpdates = true
            else
                getUpdates = false
        }
        termsCondition?.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                getTermsCondtions = true
                errorTerms.visibility = View.GONE
            } else {
                getTermsCondtions = false
                errorTerms.visibility = View.VISIBLE
            }
        }

//        editEmail1.addTextChangedListener(MyTextWatcher(editEmail1, binding))
//        editFName.addTextChangedListener(MyTextWatcher(editFName, binding))
//        editLName.addTextChangedListener(MyTextWatcher(editLName, binding))

        //  editPass.addTextChangedListener(MyTextWatcher(editPass, binding))
       // editCPass.addTextChangedListener(MyTextWatcher(editCPass, binding))


        editEmail1.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            val isSoftImeEvent = (event == null
                    && (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEND))
            if (isSoftImeEvent) {
                val email: String = binding.editEmail1.getText().toString().trim()
                if (email.isEmpty()) {
                    binding.editEmail1.background =
                        view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                    binding.errorRegEmail.setText(resources.getString(R.string.is_required))
                    binding.errorRegEmail.visibility = View.VISIBLE
                } else if (!isValidEmail(email)) {
                    binding.editEmail1.background =
                        view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                    binding.errorRegEmail.setText(resources.getString(R.string.error_email))
                    binding.errorRegEmail.visibility = View.VISIBLE
                } else {
                    binding.editFName.requestFocus()
                    binding.editEmail1.background =
                        view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                    binding.errorRegEmail.visibility = View.GONE
                }
                return@OnEditorActionListener true
            }
            false
        })

        editFName.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            val isSoftImeEvent = (event == null
                    && (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEND))
            if (isSoftImeEvent) {
                val names: String = binding.editFName.getText().toString().trim()
                if (TextUtils.isEmpty(names)/* || names.length < 5*/) {
                    binding.editFName.background =
                        view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                    //binding.editFName.requestFocus()
                    binding.errorFirstName.visibility = View.VISIBLE
                } else {
                    binding.editLName.requestFocus()
                    binding.editFName.background =
                        view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                    binding.errorFirstName.visibility = View.GONE
                }
                return@OnEditorActionListener true
            }
            false
        })

        editLName.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            val isSoftImeEvent = (event == null
                    && (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEND))
            if (isSoftImeEvent) {
                val names: String = binding.editLName.getText().toString().trim()
                if (TextUtils.isEmpty(names)/* || names.length < 5*/) {
                    binding.editLName.background =
                        view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                    //binding.editLName.requestFocus()
                    binding.errorLastName.visibility = View.VISIBLE
                } else {
                    binding.editPass.requestFocus()
                    binding.editLName.background =
                        view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                    binding.errorLastName.visibility = View.GONE
                }
                return@OnEditorActionListener true
            }
            false
        })

        editPass.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            val isSoftImeEvent = (event == null
                    && (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEND))
            if (isSoftImeEvent) {
                val pwd: String = binding.editPass.getText().toString().trim()
                if (TextUtils.isEmpty(pwd)) {
                    binding.ltpwdReg.background =
                        view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                    //binding.editLName.requestFocus()
                    binding.errorRegPwd.visibility = View.VISIBLE
                } else {

                    if (pwd.length < 8) {
                        binding.ltpwdReg.background =
                            view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                        binding.editPass.requestFocus()
                        binding.errorRegPwd.visibility = View.VISIBLE
                        binding.errorRegPwd.text = "Must contain at least 8 character(s)."
                    } else {
                        if (pwd.isEmpty() || !isValidPassword(pwd)) {
                            binding.ltpwdReg.background =
                                view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                            binding.editPass.requestFocus()
                            binding.errorRegPwd.visibility = View.VISIBLE
                            binding.errorRegPwd.text =
                                "A password must contain at least 3 of the following: lowercase, uppercase, digits, special characters."
                        } else {
                            //binding.editCPass.requestFocus()
                            binding.ltpwdReg.background =
                                view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                            binding.errorRegPwd.visibility = View.GONE
                        }
                    }

//                    binding.editPass.requestFocus()
//                    binding.ltpwdReg.background =
//                        view!!.resources.getDrawable(R.drawable.box_bg_1dp)
//                    binding.errorRegPwd.visibility = View.GONE
                }
                return@OnEditorActionListener true
            }
            false
        })

       /* editCPass.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            val isSoftImeEvent = (event == null
                    && (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEND))
            if (isSoftImeEvent) {
                val cpwd: String = binding.editCPass.getText().toString().trim()
                if (TextUtils.isEmpty(cpwd)) {
                    binding.ltcpwdReg.background =
                        view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                    //binding.editLName.requestFocus()
                    binding.errorRegCPwd.visibility = View.VISIBLE
                } else {

                    if (cpwd.length < 8) {
                        binding.ltcpwdReg.background =
                            view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                        binding.editCPass.requestFocus()
                        binding.errorRegCPwd.visibility = View.VISIBLE
                        binding.errorRegCPwd.text = "Must contain at least 8 character(s)."
                    } else {
                        if (cpwd.isEmpty() || !isValidPassword(cpwd)) {
                            binding.ltcpwdReg.background =
                                view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                            binding.editCPass.requestFocus()
                            binding.errorRegCPwd.visibility = View.VISIBLE
                            binding.errorRegCPwd.text =
                                "A password must contain at least 3 of the following: lowercase, uppercase, digits, special characters."
                        } else {
                            binding.ltcpwdReg.background =
                                view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                            binding.errorRegCPwd.visibility = View.GONE
                            validateMatchPassword(
                                binding.editCPass,
                                binding.editPass,
                                binding.ltcpwdReg,
                                binding.errorRegCPwd
                            )
                        }
                    }
//                    binding.editCPass.requestFocus()
//                    binding.ltcpwdReg.background =
//                        view!!.resources.getDrawable(R.drawable.box_bg_1dp)
//                    binding.errorRegCPwd.visibility = View.GONE
                }
                return@OnEditorActionListener true
            }
            false
        })*/

        editPhone.addTextChangedListener(MyTextWatcher(editPhone, binding))

        eye_img_pass.setOnClickListener {
            togglePassVisability(activity!!, editPass, eye_img_pass)
        }
        /*eye_img_cpass.setOnClickListener {
            togglePassVisability(activity!!, editCPass, eye_img_cpass)
        }*/

        //editEmailAtForget.addTextChangedListener(MyTextWatcher(editEmailAtForget, binding))

        signinBtn.setSingleClickListener {
            registerValidatioons(binding)
        }

        regVerifySubmitOtp.setOnClickListener {
            verifyRegisterOtpValidation()
        }

        regiserSuccessBtn.setOnClickListener {
            loginRL.visibility = View.VISIBLE
            headerLt.visibility = View.VISIBLE
            UserregisterSucusseLt.visibility = View.GONE
        }

        submitResetOtpTxt.setOnClickListener {
            val email: String = binding.editEmailAtForget.getText().toString().trim()

            if (email.isEmpty()) {
                binding.editEmailAtForget.background =
                    view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                binding.errorForgetEmail.setText(resources.getString(R.string.is_required))
                binding.errorForgetEmail.visibility = View.VISIBLE
            } else if (!isValidEmail(email)) {
                binding.editEmailAtForget.background =
                    view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                binding.errorForgetEmail.setText(resources.getString(R.string.error_email))
                binding.errorForgetEmail.visibility = View.VISIBLE
            } else {
                binding.editEmailAtForget.background =
                    view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                binding.errorForgetEmail.visibility = View.GONE

                resetverifyOtpValidation()
            }

        }


        verify_email_otp_view?.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                toast(activity!!, "emailOtp OTP is $otp")
                emailOtp = otp
                verifyRegisterOtpValidation()
            }
        }
        verify_mobile_otp_view?.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                toast(activity!!, "mobileOtp OTP is $otp")
                mobileOtp = otp
            }
        }

        reset_otp_view?.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                //toast(activity!!, "resetOtp OTP is $otp")
                resetOtp = otp
                resetverifyOtpValidation()
            }
        }

        txtRegisterClick.setOnClickListener {
            txtSignin.setTextColor(activity!!.resources.getColor(R.color.view_color))
            txtRegister.setTextColor(activity!!.resources.getColor(R.color.price_blue_color))
            loginRL.visibility = View.GONE
            registerRL.visibility = View.VISIBLE
            ForgetLt.visibility = View.GONE
            headerLt.visibility = View.VISIBLE
        }

//        editNewPass.addTextChangedListener(MyTextWatcher(editNewPass, binding))
//        editNewCPass.addTextChangedListener(MyTextWatcher(editNewCPass, binding))

        editNewPass.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            val isSoftImeEvent = (event == null
                    && (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEND))
            if (isSoftImeEvent) {
                val pwd: String = binding.editNewPass.getText().toString().trim()
                if (TextUtils.isEmpty(pwd)) {
                    binding.ltNewPwd.background =
                        view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                    //binding.editLName.requestFocus()
                    binding.errorNewPwd.visibility = View.VISIBLE
                } else {
//                    binding.editNewPass.requestFocus()
//                    binding.ltNewPwd.background =
//                            view!!.resources.getDrawable(R.drawable.box_bg_1dp)
//                    binding.errorNewPwd.visibility = View.GONE

                    if (pwd.length < 8) {
                        binding.ltNewPwd.background =
                            view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                        binding.editNewPass.requestFocus()
                        binding.errorNewPwd.visibility = View.VISIBLE
                        binding.errorNewPwd.text = "Must contain at least 8 character(s)."
                    } else {
                        if (pwd.isEmpty() || !isValidPassword(pwd)) {
                            binding.ltNewPwd.background =
                                view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                            binding.editNewPass.requestFocus()
                            binding.errorNewPwd.visibility = View.VISIBLE
                            binding.errorNewPwd.text =
                                "A password must contain at least 3 of the following: lowercase, uppercase, digits, special characters."
                        } else {
                            binding.editNewCPass.requestFocus()
                            binding.ltNewPwd.background =
                                view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                            binding.errorNewPwd.visibility = View.GONE
                        }
                    }

                }
                return@OnEditorActionListener true
            }
            false
        })

        editNewCPass.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            val isSoftImeEvent =
                (event == null && (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEND))
            if (isSoftImeEvent) {
                val cpwd: String = binding.editNewCPass.getText().toString().trim()
                val pwd: String = binding.editNewPass.getText().toString().trim()
                if (TextUtils.isEmpty(cpwd)) {
                    binding.ltNewCPwd.background =
                        view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                    //binding.editLName.requestFocus()
                    binding.errorNewCPwd.visibility = View.VISIBLE
                } else {
//                    binding.editNewCPass.requestFocus()
//                    binding.ltNewCPwd.background = view!!.resources.getDrawable(R.drawable.box_bg_1dp)
//                    binding.errorNewCPwd.visibility = View.GONE
                    if (cpwd.length < 8) {
                        binding.ltNewCPwd.background =
                            view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                        binding.editNewCPass.requestFocus()
                        binding.errorNewCPwd.visibility = View.VISIBLE
                        binding.errorNewCPwd.text = "Must contain at least 8 character(s)."
                    } else {
                        if (cpwd.isEmpty() || !isValidPassword(cpwd)) {
                            binding.ltNewCPwd.background =
                                view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                            binding.editNewCPass.requestFocus()
                            binding.errorNewCPwd.visibility = View.VISIBLE
                            binding.errorNewCPwd.text =
                                "A password must contain at least 3 of the following: lowercase, uppercase, digits, special characters."
                        } else {

                            if (!cpwd.equals(pwd)) {
                                binding.ltNewCPwd.background =
                                    view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                                binding.editNewCPass.requestFocus()
                                binding.errorNewCPwd.visibility = View.VISIBLE
                                binding.errorNewCPwd.text = "Passwords must match."
                            } else {
                                binding.ltNewCPwd.background =
                                    view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                                binding.errorNewCPwd.visibility = View.GONE

                            }
                        }
                    }


                }
                return@OnEditorActionListener true
            }
            false
        })

        eye_img_new_pass.setOnClickListener {
            togglePassVisability(activity!!, editNewPass, eye_img_new_pass)
        }
        eye_img_new_cpass.setOnClickListener {
            togglePassVisability(activity!!, editNewCPass, eye_img_new_cpass)
        }

        resetSubmitBtn.setOnClickListener {  //TODO 2
            val newPass: String = binding.editNewPass.getText().toString().trim()
            val newCPass: String = binding.editNewCPass.getText().toString().trim()
            if (newPass.isEmpty() && newCPass.isEmpty()) {
                binding.errorNewPwd.visibility = View.VISIBLE
                binding.errorNewCPwd.visibility = View.VISIBLE
            } else {

                if (newPass.length < 8) {
                    binding.editNewPass.requestFocus()
                    binding.errorNewPwd.visibility = View.VISIBLE
                    binding.errorNewPwd.text = "Must contain at least 8 character(s)."
                } else {
                    if (!isValidPassword(newPass)) {
                        binding.editNewPass.requestFocus()
                        binding.errorNewPwd.text =
                            "A password must contain at least 3 of the following: lowercase, uppercase, digits, special characters."
                        binding.errorNewPwd.visibility = View.VISIBLE
                    } else {
                        binding.errorNewPwd.visibility = View.GONE
                        //

                        if (newCPass.length < 8) {
                            binding.editNewCPass.requestFocus()
                            binding.errorNewCPwd.visibility = View.VISIBLE
                            binding.errorNewCPwd.text = "Must contain at least 8 character(s)."
                        } else {
                            if (!isValidPassword(newCPass)) {
                                binding.editNewCPass.requestFocus()
                                binding.errorNewCPwd.text =
                                    "A password must contain at least 3 of the following: lowercase, uppercase, digits, special characters."
                                binding.errorNewCPwd.visibility = View.VISIBLE
                            } else {

                                if (!newCPass.equals(newPass)) {
                                    binding.ltNewCPwd.background =
                                        view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                                    binding.editNewCPass.requestFocus()
                                    binding.errorNewCPwd.visibility = View.VISIBLE
                                    binding.errorNewCPwd.text = "Passwords must match."
                                } else {
                                    binding.ltNewCPwd.background =
                                        view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                                    binding.errorNewCPwd.visibility = View.GONE

                                    resetNewPasswordApiCall(
                                        editEmailAtForget.text.toString(),
                                        editNewPass.text.toString()
                                    )

                                }


                            }
                        }


                    }
                }


            }

        }

        callHelpLineAtVerify.setOnClickListener {
//            val phone_intent = Intent(Intent.ACTION_CALL)
//            phone_intent.setData(Uri.parse("tel:" + callHelpLineAtVerify.text))
//            startActivity(phone_intent)
            toast(activity!!, "Calling HelpLine")
        }
        callHelpLineAtForget.setOnClickListener {
            toast(activity!!, "Calling HelpLine")
        }
        chatHelpLineAtForget.setOnClickListener {
            toast(activity!!, "Chat with HelpLine")
        }
        chatHelpLineAtForget.setOnClickListener {
            toast(activity!!, "Chat with HelpLine")
        }
        discardResetPwd.setOnClickListener {
            //toast(activity!!, "Chat with HelpLine")
            ResetLt.visibility = View.GONE
            loginRL.visibility = View.VISIBLE
            headerLt.visibility = View.VISIBLE
        }


        editPass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s != null && !s.isEmpty()) {
                    updatePasswordStrengthView(s.toString())
                    password_strength.visibility = View.VISIBLE
                    ltPwdStregth.visibility = View.VISIBLE
                } else {
                    password_strength.visibility = View.GONE
                    ltPwdStregth.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable) {
                validatePassword(
                    binding.editPass,
                    binding.ltpwdReg,
                    binding.errorRegPwd
                )
            }

            fun validatePassword(
                editText: EditText,
                ltpwd: LinearLayout,
                errorPwd: CustomTextView
            ): Boolean {
                val pwd: String = editText.getText().toString().trim()

                if (pwd.length < 8) {
                    ltpwd.background = view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                    editPass.requestFocus()
                    errorPwd.visibility = View.VISIBLE
                    errorPwd.text = "Must contain at least 8 character(s)."
                } else {
                    if (pwd.isEmpty() || !isValidPassword(pwd)) {
                        ltpwd.background = view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                        editPass.requestFocus()
                        errorPwd.visibility = View.VISIBLE
                        errorPwd.text =
                            "A password must contain at least 3 of the following: lowercase, uppercase, digits, special characters."
                        return false
                    } else {
                        ltpwd.background = view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                        errorPwd.visibility = View.GONE
                    }
                }

                return true
            }
        })
    }


    private fun updateDate(format: String): String? {
        val sdf =
            SimpleDateFormat(format, Locale("en", "UK"))
        return sdf.format(calendar!!.time)
    }

    fun emptyVeriyEmailMobile() {
        registerRL.visibility = View.VISIBLE
        headerLt.visibility = View.VISIBLE
        registerVerifyRL.visibility = View.GONE
        txtSignin.setTextColor(activity!!.resources.getColor(R.color.price_blue_color))
        txtRegister.setTextColor(activity!!.resources.getColor(R.color.view_color))

        editMobile.setText("")
        editRegiEmail.setText("")
        verify_mobile_otp_view.setOTP("")
//        mob_otp1.setText("")
    }

    fun getCountryCall() {
        loginViewModel.getCountryDetail()
        val countryLiveData = loginViewModel.countryLiveData
        countryLiveData.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {

                countriesList = ConvertingCountryList(it).data.countrycode
                countrySpinnerList.add("Select")

                for (i in countriesList.indices) {
                    countrySpinnerList.add(countriesList.get(i).mobile_code)
                }
                val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    activity,
                    android.R.layout.simple_spinner_item,
                    countrySpinnerList
                )

                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                countrySpinner.setAdapter(dataAdapter)

                //countrySpinner.setSelection(119)

            }
        })
    }


    private var mListener: ItemClickListener? = null

    interface ItemClickListener {
        fun onItemClick(item: String?)
        fun onLoggedIn(item: Boolean?)
    }

    override fun onAttach(activity: Context) {
        super.onAttach(activity)
        mListener = if (activity is ItemClickListener) {
            activity
        } else {
            throw RuntimeException(
                context.toString() + " must implement ItemClickListener"
            )
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    private class MyTextWatcher(private val view: View, private val binding: FragmentLoginBinding) :
        TextWatcher {
        override fun beforeTextChanged(
            charSequence: CharSequence?,
            i: Int,
            i1: Int,
            i2: Int
        ) {
        }

        override fun onTextChanged(
            charSequence: CharSequence?,
            i: Int,
            i1: Int,
            i2: Int
        ) {
            //   updatePasswordStrengthView(charSequence.toString())
        }

        override fun afterTextChanged(editable: Editable?) {
            when (view.id) {
                //     R.id.input_name -> validateName()
                R.id.editEmail -> validateEmail(binding.editEmail, binding.errorEmail)
                R.id.editPassword -> validatePassword(
                    binding.editPassword,
                    binding.ltpwd,
                    binding.errorPwd
                )

                R.id.editEmail1 -> validateEmail(binding.editEmail1, binding.errorRegEmail)
                R.id.editEmailAtForget -> validateEmail(
                    binding.editEmailAtForget,
                    binding.errorForgetEmail
                )

                R.id.editPass -> validatePassword(
                    binding.editPass,
                    binding.ltpwdReg,
                    binding.errorRegPwd
                )
                /*R.id.editCPass -> validateMatchongPassword(
                    binding.editCPass,
                    binding.editPass,
                    binding.ltcpwdReg,
                    binding.errorRegCPwd
                )*/

                R.id.editNewPass -> validatePassword(
                    binding.editNewPass,
                    binding.ltNewPwd,
                    binding.errorNewPwd
                )

                R.id.editNewCPass -> validateMatchongPassword(
                    binding.editNewCPass,
                    binding.editNewPass,
                    binding.ltNewCPwd,
                    binding.errorNewCPwd
                )


                R.id.editFName -> validateNames(binding.editFName, binding.errorFirstName)
                R.id.editLName -> validateNames(binding.editLName, binding.errorLastName)
                //R.id.editPhone -> validatePhones(binding.editPhone, binding.errorPhone)

            }
        }


        fun validateEmail(editText: EditText, errorText: CustomTextView): Boolean {
            val email: String = editText.getText().toString().trim()
            if (email.isEmpty()) {
                editText.background =
                    view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                editText.requestFocus()
                errorText.setText(view!!.resources.getString(R.string.is_required))
                errorText.visibility = View.VISIBLE
                return false
            } else if (!isValidEmail(email)) {
                editText.background =
                    view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                editText.requestFocus()
                errorText.setText(view!!.resources.getString(R.string.error_email))
                errorText.visibility = View.VISIBLE
                return false
            } else {
                editText.background = view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                errorText.visibility = View.GONE
            }
            return true
        }

        fun validatePassword(
            editText: EditText,
            ltpwd: LinearLayout,
            errorPwd: CustomTextView
        ): Boolean {
            val pwd: String = editText.getText().toString().trim()

            if (pwd.length < 8) {
                ltpwd.background = view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                editText.requestFocus()
                errorPwd.visibility = View.VISIBLE
                errorPwd.text = "Must contain at least 8 character(s)."
            } else {
                if (pwd.isEmpty() || !isValidPassword(pwd)) {
                    ltpwd.background = view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                    editText.requestFocus()
                    errorPwd.visibility = View.VISIBLE
                    errorPwd.text =
                        "A password must contain at least 3 of the following: lowercase, uppercase, digits, special characters."
                    return false
                } else {
                    ltpwd.background = view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                    errorPwd.visibility = View.GONE
                }
            }

            return true
        }

        fun validateMatchongPassword(
            editCText: EditText,
            editText: EditText,
            ltpwd: LinearLayout,
            errorPwd: CustomTextView
        ): Boolean {
            val pwd: String = editCText.getText().toString().trim()
            if (pwd.length < 8) {
                ltpwd.background = view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                editCText.requestFocus()
                errorPwd.visibility = View.VISIBLE
                errorPwd.text = "Must contain at least 8 character(s)."
            } else {
                if (pwd.isEmpty() || !isValidPassword(pwd)) {
                    ltpwd.background = view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                    editCText.requestFocus()
                    errorPwd.visibility = View.VISIBLE
                    errorPwd.text =
                        "A password must contain at least 3 of the following: lowercase, uppercase, digits, special characters."
                    return false
                } else {
                    ltpwd.background = view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                    errorPwd.visibility = View.GONE
                    validateMatchPassword(editCText, editText, ltpwd, errorPwd)
                }
            }
            return true
        }

        fun validateMatchPassword(
            editCText: EditText,
            editText: EditText,
            ltpwd: LinearLayout,
            errorPwd: CustomTextView
        ): Boolean {
            val cpwd: String = editCText.getText().toString().trim()
            val pwd: String = editText.getText().toString().trim()
            if (!cpwd.equals(pwd)) {
                ltpwd.background = view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                editCText.requestFocus()
                errorPwd.visibility = View.VISIBLE
                errorPwd.text = "Passwords must match."
                return false
            } else {
                ltpwd.background = view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                errorPwd.visibility = View.GONE
            }
            return true
        }


        fun validateNames(editText: EditText, errorText: CustomTextView): Boolean {
            val names: String = editText.getText().toString().trim()
            if (TextUtils.isEmpty(names) || names.length < 5) {
                editText.background = view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                editText.requestFocus()
                errorText.visibility = View.VISIBLE
                return false
            } else {
                editText.background = view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                errorText.visibility = View.GONE
            }
            return true
        }

        /*fun validatePhones(editText: EditText, errorText: CustomTextView): Boolean {
            val phone: String = editText.getText().toString().trim()
            if (!isValidMobile(phone)) {
                editText.background = view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                editText.requestFocus()
                errorText.visibility = View.VISIBLE
                return false
            } else {
                editText.background = view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                errorText.visibility = View.GONE
            }
            return true
        }
*/

    }

    fun updatePasswordStrengthView(password: String) {
        // if (TextView.VISIBLE != binding.passwordStrength.visibility) return

        if (password.isEmpty()) {
            binding.passwordStrength.text = ""
            binding.progressBar.progress = 0
            return
        }
        val str: PasswordStrength = PasswordStrength.calculateStrength(password)
        binding.passwordStrength.setText(str.getText(activity))
        binding.passwordStrength.setTextColor(str.getColor())
        binding.progressBar.progressDrawable.setColorFilter(str.getColor(), PorterDuff.Mode.SRC_IN)
        if (str.getText(activity!!).equals("too short")) {
            progressBar.progress = 25
        } else if (str.getText(activity!!).equals("Password Strength is Weak")) {
            progressBar.progress = 50
        } else if (str.getText(activity!!).equals("Password Strength is Medium")) {
            progressBar.progress = 75
        } else {
            progressBar.progress = 100
        }
    }

    fun loginValidatioons(binding: FragmentLoginBinding) {  //TODO  1
        val email: String = binding.editEmail.getText().toString().trim()
        val pwd: String = binding.editPassword.getText().toString().trim()

        if (email.isEmpty() && pwd.isEmpty()) {
            binding.errorEmail.setText(resources.getString(R.string.isRequire))
            binding.errorEmail.visibility = View.VISIBLE
            binding.errorPwd.visibility = View.VISIBLE

        } else {
            if (email.isEmpty()) {
                binding.editEmail.background =
                    view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                binding.editEmail.requestFocus()
                binding.errorEmail.setText(resources.getString(R.string.error_email))
                binding.errorEmail.visibility = View.VISIBLE
            } else if (!isValidEmail(email)) {
                binding.editEmail.background =
                    view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                binding.errorEmail.setText(resources.getString(R.string.error_email))
                binding.errorEmail.visibility = View.VISIBLE
            } else {
                if (pwd.isEmpty()) {
                    binding.editPassword.requestFocus()
                    binding.errorPwd.visibility = View.VISIBLE
                } else {
                    if (pwd.length < 8) {
//                    binding.ltpwd.background =
//                        view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                        binding.editPassword.requestFocus()
                        binding.errorPwd.visibility = View.VISIBLE
                        binding.errorPwd.text = "Must contain at least 8 character(s)."
                    } else {
                        if (!isValidPassword(pwd)) {
                            binding.ltpwd.background =
                                view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                            binding.editPassword.requestFocus()
                            binding.errorPwd.text =
                                "A password must contain at least 3 of the following: lowercase, uppercase, digits, special characters."
                            binding.errorPwd.visibility = View.VISIBLE
                        } else {
                            binding.ltpwd.background =
                                view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                            binding.errorPwd.visibility = View.GONE
                            //  toast(activity!!, "cliked to Login page ")
                            LoginUserApiCall(
                                editEmail.text.toString(),
                                editPassword.text.toString(), ""
                            )
                        }
                    }
                }

                binding.editEmail.background = view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                binding.errorEmail.visibility = View.GONE
            }
        }

    }

    fun registerValidatioons(binding: FragmentLoginBinding) {
        val email: String = binding.editEmail1.getText().toString().trim()
        val phone: String = binding.editPhone.getText().toString().trim()

        if (email.isEmpty() || !isValidEmail(email)) {
            binding.editEmail1.background =
                view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
            binding.editEmail1.requestFocus()
            binding.errorRegEmail.visibility = View.VISIBLE
        } else {

            binding.editEmail1.background =
                view!!.resources.getDrawable(R.drawable.box_bg_1dp)
            binding.errorRegEmail.visibility = View.GONE

            val names: String = binding.editFName.getText().toString().trim()
            if (TextUtils.isEmpty(names)/* || names.length < 5*/) {
                binding.editFName.background =
                    view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                binding.editFName.requestFocus()
                binding.errorFirstName.visibility = View.VISIBLE
            } else {

                val lnames: String = binding.editLName.getText().toString().trim()
                if (TextUtils.isEmpty(lnames) /*|| lnames.length < 5*/) {
                    binding.editLName.background =
                        view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                    binding.editLName.requestFocus()
                    binding.errorLastName.visibility = View.VISIBLE
                } else {

                    val pwd: String = binding.editPass.getText().toString().trim()
                    if (pwd.isEmpty() || !isValidPassword(pwd)) {
                        binding.ltpwdReg.background =
                            view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                        binding.editPass.requestFocus()
                        binding.errorRegPwd.visibility = View.VISIBLE
                        binding.errorRegPwd.text = "Is required."

                    } else {
                       // val cpwd: String = binding.editCPass.getText().toString().trim()
                       /* if (cpwd.isEmpty() || !isValidPassword(cpwd)) {
                            binding.ltcpwdReg.background =
                                view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
                            binding.editCPass.requestFocus()
                            binding.errorRegCPwd.visibility = View.VISIBLE
                            binding.errorRegCPwd.text = "Is required."
                        } else {*/

                            //val cpwd: String = binding.editCPass.getText().toString().trim()
                            val pwd: String = binding.editPass.getText().toString().trim()

                                //if (selectedCountry != null && selectedCountry.isNotEmpty()) {  //Kuwait

                                    if (selectedDOB != null && selectedDOB.isNotEmpty()) {

                                        if (getTermsCondtions) {
                                            sendOtp(
                                                binding.editEmail1.text.toString().trim(),
                                                binding.editPhone.text.toString().trim(),
                                                "register", "frmRegister"
                                            )
                                            errorTerms.visibility = View.GONE
                                        } else {
                                            errorTerms.visibility = View.VISIBLE
                                        }
                                    } else {
                                        toast(activity!!, "select DOB")
                                    }


                                /*binding.ltcpwdReg.background =
                                    view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                                binding.errorRegCPwd.visibility = View.GONE*/


                        binding.ltpwdReg.background =
                            view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                        binding.errorRegPwd.visibility = View.GONE
                    }

                    binding.editLName.background =
                        view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                    binding.errorLastName.visibility = View.GONE
                }

                binding.editFName.background =
                    view!!.resources.getDrawable(R.drawable.box_bg_1dp)
                binding.errorFirstName.visibility = View.GONE
            }

        }
    }

    fun validateMatchPassword(
        editCText: EditText,
        editText: EditText,
        ltpwd: LinearLayout,
        errorCPwd: CustomTextView
    ): Boolean {
        val cpwd: String = editCText.getText().toString().trim()
        val pwd: String = editText.getText().toString().trim()
        if (!cpwd.equals(pwd)) {
            ltpwd.background = view!!.resources.getDrawable(R.drawable.error_box_bg_1dp)
            editCText.requestFocus()
            errorCPwd.visibility = View.VISIBLE
            errorCPwd.text = "Passwords must match."

            return false
        } else {
            binding.editPhone.requestFocus()
            ltpwd.background = view!!.resources.getDrawable(R.drawable.box_bg_1dp)
            errorCPwd.visibility = View.GONE
        }
        return true
    }


    fun sendOtp(email: String, mobilenumber: String, type: String, from: String) {
        loadingProgress.visibility = View.VISIBLE
        loginViewModel.getsendOtpDetail(email, "", type)
        val sendOtpLiveData = loginViewModel.sendOtpLiveData
        sendOtpLiveData.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {
                loadingProgress.visibility = View.GONE
                if (it.data!!.sendOtp()!!.status().equals("true")) {

                    clickVerifyEmailOtp.visibility = View.GONE

                    if (type.equals("register")) {
                        fromRegistersendOtpSuccess(email, mobilenumber, type, from)
                        toast(activity!!, it.data!!.sendOtp()!!.message()!!)
                    } else if (type.equals("login")) {
                        binding.ltResetEmailOtp.visibility = View.VISIBLE
                        binding.resetOtpView.setOTP("")
                        binding.forgetVerifyOtp.visibility = View.VISIBLE
                        resendOtp.visibility = View.GONE
                        tv_timer.visibility = View.VISIBLE
                        countDownTimer = object : CountDownTimer(30000, 1000) {
                            override fun onFinish() {
                                resendOtp.visibility = View.VISIBLE
                                tv_timer.visibility = View.GONE
                            }

                            override fun onTick(p0: Long) {
                                resendOtp.visibility = View.GONE
                                tv_timer.setText("00 : " + (p0 / 1000).toString())
                            }
                        }.start()
                        toast(activity!!, it.data!!.sendOtp()!!.message()!!)
                    }
                } else {
                    binding.ltResetEmailOtp.visibility = View.GONE
                    binding.forgetVerifyOtp.visibility = View.GONE
                    alertDialog("Error!", it.data!!.sendOtp()!!.message()!!, activity!!)
                }

            } else if (it is Resource.Failure) {
                loadingProgress.visibility = View.GONE
            }
        })
    }

    fun fromRegistersendOtpSuccess(
        email: String,
        mobilenumber: String,
        type: String,
        from: String
    ) {
        fromWhichPage = from
        registerVerifyRL.visibility = View.VISIBLE
        registerRL.visibility = View.GONE
        headerLt.visibility = View.GONE

        editRegiEmail.text = editEmail1.text
        if (mobilenumber != null && !mobilenumber.isEmpty()) {
            setFocuasables(true, editMobile)
            mobileLt.visibility = View.VISIBLE

            verify_mobile_otp_view.setEnabled(true)
            verify_mobile_otp_view.setClickable(true)

            editMobile.text = editPhone.text
            binding.mobileRightImg.setImageDrawable(
                view!!.resources.getDrawable(
                    R.drawable.ic_blue_right_mark
                )
            )
            binding.mobileOtpCircle.setImageDrawable(
                view!!.resources.getDrawable(
                    R.drawable.ic_enabled_green
                )
            )
        } else {
            setFocuasables(false, editMobile)
            mobileLt.visibility = View.GONE
            verify_mobile_otp_view.setEnabled(false)
            verify_mobile_otp_view.setClickable(false)


            binding.mobileRightImg.setImageDrawable(
                view!!.resources.getDrawable(
                    R.drawable.ic_grey_right_mark
                )
            )
            binding.mobileOtpCircle.setImageDrawable(
                view!!.resources.getDrawable(
                    R.drawable.ic_disbled_gray
                )
            )
        }
    }

    var emailOtp: String = ""
    var mobileOtp: String = ""
    var resetOtp: String = ""

    fun verifyRegisterOtpValidation() {

        if (editMobile.text != null && !editMobile.text.isEmpty()) {

            if (mobileOtp != null && !mobileOtp.isEmpty() && mobileOtp.length == 6
                && emailOtp != null && !emailOtp.isEmpty() && emailOtp.length == 6
            ) {
                verifyOtpApiCall(editRegiEmail.text.toString(), mobileOtp, emailOtp)
            } else {
                toast(activity!!, "enter proper otp that sent to email and mobile")
            }

        } else {
            if (emailOtp != null && !emailOtp.isEmpty() && emailOtp.length == 6) {
                verifyOtpApiCall(editRegiEmail.text.toString(), "", emailOtp)
            } else {
                toast(activity!!, "enter proper otp from email")
            }
        }

    }

    fun verifyOtpApiCall(email: String, mobileOtp: String, emailOtp: String) {
        loadingProgress.visibility = View.VISIBLE

        loginViewModel.getVeirfyOtpDetail(email, mobileOtp, emailOtp)
        val verifyOtpLiveData = loginViewModel.verifyOtpLiveData
        verifyOtpLiveData.observe(viewLifecycleOwner, Observer { it ->
            loadingProgress.visibility = View.GONE
            if (it is Resource.Success) {


                toast(activity!!, it.data!!.verifyOtp()!!.message()!!)
                if (it.data!!.verifyOtp()!!.status().equals("true")) {
                    RegisterUserApiCall(
                        editFName.text.toString(),
                        Integer.parseInt(selectedCountry),
                        selectedDOB,
                        editMobile.text.toString(),
                        editLName.text.toString(),
                        editRegiEmail.text.toString(),
                        editPass.text.toString(),
                        genderSelected,
                        getUpdates
                    )
                }
            } else if (it is Resource.Failure) {
                loadingProgress.visibility = View.GONE
            }
        })
    }

    fun RegisterUserApiCall(
        name: String,
        country: Int,
        dob: String,
        mobile_number: String,
        lname: String,
        email: String,
        password: String,
        gender: Int,
        isSubscribe: Boolean
    ) {
        loadingProgress.visibility = View.VISIBLE

        loginViewModel.getRegisterUserDetail(
            name,
            country,
            dob,
            mobile_number,
            lname,
            email,
            password,
            gender,
            isSubscribe
        )
        val registerUserLiveData = loginViewModel.registerUserLiveData
        registerUserLiveData.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {
                loadingProgress.visibility = View.GONE

                toast(activity!!, it.data!!.createCustomer()!!.customer()!!.email().toString())
                //UserregisterSucusseLt.visibility = View.VISIBLE
                LoginUserApiCall(
                    editEmail1.text.toString(),
                    editPass.text.toString(),
                    "register"
                )

            } else if (it is Resource.Failure) {
                loadingProgress.visibility = View.GONE
            }
        })
    }

    fun LoginUserApiCall(email: String, password: String, from: String) {
        loadingProgress.visibility = View.VISIBLE
        loginViewModel.getLoginTokenDetail(email, password)
        val LoginForTokenLiveData = loginViewModel.LoginForTokenLiveData
        LoginForTokenLiveData.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {
                loadingProgress.visibility = View.GONE

                if (it.data!!.generateCustomerToken() != null) {
                     toast(activity!!, "Logged in Successfully")
                    mListener!!.onItemClick("closeSheet")

                    //plainAlertDialog("Logged in Successfully", activity!!)

                    loginRL.visibility = View.GONE
                    errorForPwd.visibility = View.GONE
                    mListener!!.onLoggedIn(true)

                    sharedStorage!!.setUserEmail1(email)
                    sharedStorage!!.setUserPassword(password)
                    sharedStorage!!.setUserLogin(true)
                    sharedStorage!!.setUserToken(it.data!!.generateCustomerToken()!!.token())

                  //  saveHistoryToApi()

                    Navigation.findNavController(binding.root).navigate(R.id.homeFragment);

                } else {
                    //toast(activity!!, "Invalid Email/Password")
                    alertDialog("Error!", activity!!.getString(R.string.wrong_cred), activity!!)
                }

            } else if (it is Resource.Failure) {
                errorForPwd.visibility = View.VISIBLE
                loadingProgress.visibility = View.GONE
            }
        })
    }

    var mHistoryList: ArrayList<BrowseSavingModel>? = null

    fun saveHistoryToApi() {
        if (sharedStorage!!.isLogin) {

            val gson = Gson()
            val json: String = sharedStorageHistory!!.getProductClickId()!!

            if (json != null && !json.isEmpty()) {

                val type: Type = object : TypeToken<ArrayList<BrowseSavingModel?>?>() {}.getType()
                mHistoryList = gson.fromJson(json, type)

                var entityFrmLocalHistory: ArrayList<Int> = arrayListOf()
                for (i in mHistoryList!!.indices) {
                    entityFrmLocalHistory.add(Integer.parseInt(mHistoryList!!.get(i).entity_id))
                }

                //TODO issue here at api call
                loginViewModel.addBrosingHistory(entityFrmLocalHistory)
                val browsingHistoryAddLiveData = loginViewModel.browsingHistoryAddLiveData
                browsingHistoryAddLiveData.observe(
                    viewLifecycleOwner,
                    Observer { itBh ->
                        if (itBh is Resource.Success) {
                            if (itBh.data!!.browsingHistoryMutation() != null) {

                                sharedStorageHistory!!.deleteUserData()

                                val json: String = sharedStorageHistory!!.getProductClickId()!!

                            }
                        }
                    })
            }
        }

    }

    fun resetverifyOtpValidation() {

        if (resetOtp != null && !resetOtp.isEmpty() && resetOtp.length == 6) {
            resetVerifyOtpApiCall(editEmailAtForget.text.toString(), resetOtp)
        } else {
            toast(activity!!, "Enter OTP from Registered  Email")
        }

    }

    fun resetVerifyOtpApiCall(email: String, emailOtp: String) {
        loadingProgress.visibility = View.VISIBLE
        loginViewModel.getResetVerifyEmailOtpDetail(email, emailOtp)
        val resetEmailOtpLiveDatay = loginViewModel.resetEmailOtpLiveDatay
        resetEmailOtpLiveDatay.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {
                loadingProgress.visibility = View.GONE
                toast(activity!!, it.data!!.verifyEmailOtp()!!.token()!!)
                if (it.data!!.verifyEmailOtp()!!.status().equals("true")) {
                    ResetLt.visibility = View.VISIBLE
                    headerLt.visibility = View.GONE
                    ForgetLt.visibility = View.GONE
                    binding.ltResetEmailOtp.visibility = View.GONE
                    binding.forgetVerifyOtp.visibility = View.GONE
                }
                if (it.data!!.verifyEmailOtp()!!.token()!!.equals("Invalid OTP")) {
                    resendOtp.visibility = View.VISIBLE
                } else {
                    resendOtp.visibility = View.GONE
                }
            } else if (it is Resource.Failure) {
                loadingProgress.visibility = View.GONE
            }
        })
    }


    fun resetNewPasswordApiCall(email: String, NewPassword: String) {
        loadingProgress.visibility = View.VISIBLE
        loginViewModel.getNewPasswordApiCall(email, NewPassword)
        val newPasswordLiveDatay = loginViewModel.newPasswordLiveDatay
        newPasswordLiveDatay.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {
                loadingProgress.visibility = View.GONE
                if (it.data!!.mobileresetpassword()!!.status().equals("true")) {
                    ResetLt.visibility = View.GONE
                    headerLt.visibility = View.VISIBLE
                    loginRL.visibility = View.VISIBLE
                    toast(activity!!, it.data!!.mobileresetpassword()!!.message()!!)
                }
            } else if (it is Resource.Failure) {
                loadingProgress.visibility = View.GONE
            }
        })
    }


}


//TPODO

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val bottomSheetDialog =
//            super.onCreateDialog(savedInstanceState) as BottomSheetDialog
//        bottomSheetDialog.setOnShowListener { dia ->
//            val dialog = dia as BottomSheetDialog
//            val bottomSheet =
//                dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
////            dialog.window.setFlags(
////                WindowManager.LayoutParams.FLAG_FULLSCREEN,
////                WindowManager.LayoutParams.MATCH_PARENT
////            )
//            BottomSheetBehavior.from<FrameLayout?>(bottomSheet!!).state =
//                BottomSheetBehavior.STATE_EXPANDED
//            BottomSheetBehavior.from<FrameLayout?>(bottomSheet).skipCollapsed = true
//            BottomSheetBehavior.from<FrameLayout?>(bottomSheet).isHideable = true
//            dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
//        }
//        return bottomSheetDialog
//    }





