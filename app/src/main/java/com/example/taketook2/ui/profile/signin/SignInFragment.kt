package com.example.taketook2.ui.profile.signin

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.StateSet.TAG
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.TAG
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.taketook2.R
import com.example.taketook2.databinding.FragmentSignInBinding
import com.example.taketook2.ui.profile.pin.PinActivity
import com.example.taketook2.ui.profile.pin.PinFragment
import com.example.taketook2.ui.profile.registration.RegistrationActivity
import com.example.taketook2.ui.profile.registration.RegistrationFragment

/*
 * @author y.gladkikh
 */
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            setupPhoneTextView()
            signInButton.setOnClickListener {
                startActivity(PinActivity.createIntent(requireActivity()))
            }
            createAccount.setOnClickListener {
                startActivity(RegistrationActivity.createIntent(requireActivity()))
            }
        }
    }

    private fun FragmentSignInBinding.setupPhoneTextView(): TextWatcher {
        textPhone.setText("+7")
        return textPhone.doOnTextChanged { text, _, _, dir ->
            onPhoneTextChanged(text, dir)
        }
    }

    private fun FragmentSignInBinding.onPhoneTextChanged(text: CharSequence?, dir: Int) {
        val size = text?.length
        val last = size?.minus(1)
        val t = textPhone.text

        if (size != null && last != null && t != null) {
            if (dir == 0 && size <= 2) {
                setTextAndSelection("+7", 2)
            }

            if (size == 3 && dir == 1) {
                setTextAndSelection("${t.substring(0, last)} (${text[last]}", 5)
            }

            if (size == 7 && dir == 1) {
                setTextAndSelection("$t) ", 9)
            }

            if (size == 8 && dir == 1 && t[last - 1] != '-') {
                setTextAndSelection("${t.substring(0, 7)}) ${t[last]}", 10)
            }

            if (size == 9 && dir == 1 && t[last - 1] != ' ') {
                setTextAndSelection("${t.substring(0, 8)} ${t[last]}", 10)
            }

            checkZeroOneDirectional(
                size = 12,
                symbol = '-',
                lastIndex = last,
                direction = dir,
                text = t
            )

            checkZeroOneDirectional(
                size = 15,
                symbol = '-',
                lastIndex = last,
                direction = dir,
                text = t,
            )

            if (size > 18 && dir == 1) {
                setTextAndSelection(t.substring(0, 18), 18)
            }
        }
    }

    private fun FragmentSignInBinding.checkZeroOneDirectional(size: Int, symbol: Char, lastIndex: Int, direction: Int, text: CharSequence) {
        if (text.length == size && direction == 1) {
            setTextAndSelection("$text-", size + 1)
        }

        if (text.length == size + 1 && direction == 1 && text[lastIndex - 1] != symbol) {
            setTextAndSelection("${text.substring(0, size)}-${text[lastIndex]}", size + 2)
        }
    }

    private fun FragmentSignInBinding.setTextAndSelection(text: String, selection: Int) {
        textPhone.setText(text)
        textPhone.setSelection(selection)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
