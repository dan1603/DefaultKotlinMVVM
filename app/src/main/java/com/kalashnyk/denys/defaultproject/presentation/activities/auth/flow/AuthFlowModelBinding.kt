package com.kalashnyk.denys.defaultproject.presentation.activities.auth.flow

import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.kalashnyk.denys.defaultproject.BR
import com.kalashnyk.denys.defaultproject.R
import com.kalashnyk.denys.defaultproject.utils.extention.addErrorForCheckBox
import com.kalashnyk.denys.defaultproject.utils.extention.addErrorForTextInputLayout
import com.kalashnyk.denys.defaultproject.utils.extention.clearErrorForCheckBox
import com.kalashnyk.denys.defaultproject.utils.extention.clearErrorForTextInputLayout
import java.util.*

/**
 *
 */
class AuthFlowModelBinding(
    private var authChild: AuthFlowModel,
    private var listener: IAuthFlow.IAuthListener?,
    private var callback: IAuthFlow.IAuthCallback
) : Observer, BaseObservable() {

    init {
        authChild.addObserver(this)
    }

    /**
     * @field agreeTerms
     */
    var agreeTerms: Boolean=false
        set(value) {
            field=value
            authChild.agreeTerms=field
            notifyPropertyChanged(BR.agreeTerms)
        }
        @Bindable get() {
            return authChild.agreeTerms
        }

    /**
     * @field authFlowError
     */
    var authFlowError: AuthFlowErrorModel=AuthFlowErrorModel()
        set(value) {
            field=value
            notifyPropertyChanged(BR.authFlowError)
        }
        @Bindable get() {
            return authChild.error
        }

    /**
     * @param o
     * @param arg
     */
    override fun update(o: Observable?, arg: Any?) {
        if (o is AuthFlowModel?) {
            if (arg == errorField) authFlowError=authChild.error
        }
    }

    fun onOpenScreen(typeNavigate: IAuthFlow.NavigationType)=listener?.openScreen(typeNavigate)

    fun onSocialAuth(type: IAuthFlow.SocialAuthType)=listener?.socialAuth(type, callback)

    fun onAuthRequest() {
        listener?.authRequest(authChild, callback)
    }

    fun onRoutBack(){
        listener?.routBack()
    }
    companion object {

        @JvmStatic
        @BindingAdapter(
            "bind:authError"
        )
        fun bindError(
            viewGroup: LinearLayout,
            authError: AuthFlowErrorModel
        ) {
            if (authError.type != AuthFlowErrorModel.AuthFlowErrorType.DEFAULT_ERROR) {
                authError.let {
                    handelAuthFlowError(
                        it,
                        viewGroup.findViewById(R.id.tilAuthEmail),
                        viewGroup.findViewById(R.id.tilAuthPassword),
                        viewGroup.findViewById(R.id.tilAuthConfirmPassword),
                        viewGroup.findViewById(R.id.checkBoxSignUpAgree)
                    )
                }
            } else {
                clearAuthFlowError(
                    viewGroup.findViewById(R.id.tilAuthEmail),
                    viewGroup.findViewById(R.id.tilAuthPassword),
                    viewGroup.findViewById(R.id.tilAuthConfirmPassword),
                    viewGroup.findViewById(R.id.checkBoxSignUpAgree)
                )
            }
        }

        private fun handelAuthFlowError(
            errorModel: AuthFlowErrorModel,
            tilEmail: TextInputLayout?,
            tilPassword: TextInputLayout?,
            tilConfirmPassword: TextInputLayout?,
            checkBoxTermsConditions: CheckBox?
        ) {
            when (errorModel.type) {
                AuthFlowErrorModel.AuthFlowErrorType.EMAIL_ERROR -> {
                    showAuthFlowError(errorModel, tilEmail)
                }
                AuthFlowErrorModel.AuthFlowErrorType.PASSWORD_ERROR -> {
                    showAuthFlowError(errorModel, tilPassword)
                }
                AuthFlowErrorModel.AuthFlowErrorType.PASSWORD_CONFIRM_ERROR -> {
                    showAuthFlowError(errorModel, tilConfirmPassword)
                }
                AuthFlowErrorModel.AuthFlowErrorType.TERMS_CONDITION_ERROR -> {
                    showAuthFlowError(errorModel, checkBoxTermsConditions)
                }
                AuthFlowErrorModel.AuthFlowErrorType.SIGN_UP_ERRORS -> {
                    showAuthFlowError(errorModel, tilConfirmPassword)
                    showAuthFlowError(errorModel, checkBoxTermsConditions)
                }
                AuthFlowErrorModel.AuthFlowErrorType.SIGN_IN_ERRORS -> {
                    showAuthFlowError(errorModel, tilPassword)
                }
            }
        }

        private fun <V : View> showAuthFlowError(
            errorModel: AuthFlowErrorModel,
            view: V?
        ) {
            if (view is TextInputLayout) {
                view.addErrorForTextInputLayout(errorModel.message?.toString(view.context))
            } else if (view is CheckBox) {
                view.addErrorForCheckBox()
            }
        }

        private fun clearAuthFlowError(
            tilEmail: TextInputLayout?,
            tilPassword: TextInputLayout?,
            tilConfirmPassword: TextInputLayout?,
            checkBoxTermsConditions: CheckBox?
        ) {
            tilEmail?.clearErrorForTextInputLayout()
            tilPassword?.clearErrorForTextInputLayout()
            tilConfirmPassword?.clearErrorForTextInputLayout()
            checkBoxTermsConditions?.clearErrorForCheckBox()
        }
    }
}