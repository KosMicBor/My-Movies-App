package kosmicbor.giftapp.mymoviesapp.view.fragments

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import kosmicbor.giftapp.mymoviesapp.R
import kosmicbor.giftapp.mymoviesapp.databinding.FragmentContactsBinding
import kosmicbor.giftapp.mymoviesapp.domain.contacts_data.Contact
import kosmicbor.giftapp.mymoviesapp.domain.showSnackBar
import kosmicbor.giftapp.mymoviesapp.domain.viewHide
import kosmicbor.giftapp.mymoviesapp.domain.viewShow
import kosmicbor.giftapp.mymoviesapp.view.Success
import kosmicbor.giftapp.mymoviesapp.view.Error
import kosmicbor.giftapp.mymoviesapp.view.LoadingState
import kosmicbor.giftapp.mymoviesapp.view.adapters.ContactsRVAdapter
import kosmicbor.giftapp.mymoviesapp.viewmodels.ContactsViewModel

class FragmentContacts : Fragment(R.layout.fragment_contacts) {

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentContacts()
    }

    private val binding: FragmentContactsBinding by viewBinding(FragmentContactsBinding::bind)
    private val viewModel: ContactsViewModel by viewModels()
    private val contactsAdapter: ContactsRVAdapter = ContactsRVAdapter(listOf())
    private val permissionResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            when {
                result -> {
                    viewModel.getContactsList(requireActivity().contentResolver)
                }
                !shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    AlertDialog.Builder(context)
                        .setTitle(getString(R.string.permission_dialog_title))
                        .setMessage(getString(R.string.contacts_permossion_dialog_message))
                        .setPositiveButton(getString(R.string.permission_pos_btn_text)) { _, _ ->
                            checkPermission()
                        }
                        .setNegativeButton(getString(R.string.permission_dialog_neg_btn_text)) { dialog, _ ->
                            dialog.dismiss()
                        }
                }
                else -> Toast.makeText(context, "Contacts now unavailable!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private fun checkPermission() {
        permissionResult.launch(Manifest.permission.READ_CONTACTS)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            checkPermission()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.contactsRecyclerview.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = contactsAdapter
        }

        viewModel.getContactsLiveData().observe(viewLifecycleOwner) {

            when (it) {
                is Success -> {
                    binding.apply {
                        contactsProgressBar.viewHide()
                        contactsRecyclerview.viewShow()
                    }
                    contactsAdapter.updateContactsList(it.value as MutableList<Contact>)

                }

                is Error -> {
                    binding.apply {
                        contactsProgressBar.viewShow()
                        contactsRecyclerview.viewHide()
                        binding.root.showSnackBar(
                            binding.root,
                            R.string.error_load_cotnacts_message,
                            Snackbar.LENGTH_INDEFINITE,
                            R.string.try_again_btn,
                        ) {
                            viewModel.getContactsList(requireActivity().contentResolver)
                        }
                    }
                }

                is LoadingState -> {
                    binding.apply {
                        contactsProgressBar.viewShow()
                        contactsRecyclerview.viewHide()
                    }
                }
            }

        }
    }
}