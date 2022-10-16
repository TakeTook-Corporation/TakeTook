package com.example.pin_module

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.core.widgets.temp.MainActivityExtension
import com.example.pin_module.databinding.FragmentPinBinding

/*
 * @author y.gladkikh
 */
class PinFragment : Fragment() {

    private var _binding: FragmentPinBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PinEntryFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentPinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.codeLiveData.observe(viewLifecycleOwner, ::handleCode)

        with(binding) {
            pinEntry.showKeyboard() //TODO never hide
            pinEntry.doOnTextChanged { text, _, _, _ ->
                viewModel.codeLiveData.value = text.toString()
            }
        }
    }

    private fun handleCode(code: String) {
        val goToNextPage = viewModel.compareCodes(code)
        if (goToNextPage) {
            (activity as MainActivityExtension).mainActivityAction(this@PinFragment)
        }
    }

    private fun View.showKeyboard() {
        this.requestFocus()
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "PIN_FRAGMENT_TAG"
    }
}
