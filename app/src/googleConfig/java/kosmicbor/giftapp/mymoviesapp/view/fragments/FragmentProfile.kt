package kosmicbor.giftapp.mymoviesapp.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.CircleCropTransformation
import kosmicbor.giftapp.mymoviesapp.R
import kosmicbor.giftapp.mymoviesapp.Router
import kosmicbor.giftapp.mymoviesapp.databinding.FragmentProfileBinding

class FragmentProfile : Fragment(R.layout.fragment_profile) {
    companion object {
        fun newInstance() = FragmentProfile()
        const val DEFAULT_USER_NAME = "BATMAN"
        const val IS_ADULT = "IS_ADULT"
    }

    private val binding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)
    private var isAdultOnly = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkAdultCondition()
        binding.apply {
            userImage.apply {
                scaleX = 2F
                scaleY = 2F
                load(R.drawable.avatar) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }

            }
            userName.text = DEFAULT_USER_NAME

            adultCheckbox.setOnCheckedChangeListener { _, isChecked ->

                if (isChecked) {
                    isAdultOnly = true
                    setAdultCondition(isAdultOnly)
                } else {
                    isAdultOnly = false
                    setAdultCondition(isAdultOnly)
                }
            }

            contactsBtn.setOnClickListener {
                Router.openFragmentContacts(requireActivity().supportFragmentManager)
            }

            mapBtn.setOnClickListener {

                Router.openMap(requireActivity().supportFragmentManager)
            }
        }
    }

    private fun setAdultCondition(isAdultOnly: Boolean) {
        activity?.let {
            it.getPreferences(Context.MODE_PRIVATE).edit().apply {
                putBoolean(IS_ADULT, isAdultOnly)
                apply()
            }
        }
    }

    private fun checkAdultCondition() {
        activity?.let {

            binding.adultCheckbox.isChecked =
                it.getPreferences(Context.MODE_PRIVATE).getBoolean(IS_ADULT, false)
        }
    }
}