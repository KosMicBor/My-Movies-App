package kosmicbor.giftapp.mymoviesapp.view.adapters

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kosmicbor.giftapp.mymoviesapp.databinding.ContactsRecyclerviewItemBinding
import kosmicbor.giftapp.mymoviesapp.domain.contacts_data.Contact
import kosmicbor.giftapp.mymoviesapp.utils.ContactDiffUtil

class ContactsRVAdapter(list: List<Contact>) : RecyclerView.Adapter<ContactsRVAdapter.ContactsViewHolder>() {

    private lateinit var diffUtil: ContactDiffUtil
    private val contactsList = mutableListOf<Contact>()

    init {
        contactsList.addAll(list)
    }

    inner class ContactsViewHolder(val binding: ContactsRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val binding = ContactsRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContactsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.binding.apply {
            contactName.text = contactsList[position].name
            contactPhoneNumber.text = contactsList[position].phone
            contactsContainer.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:${contactsList[position].phone}")
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    fun updateContactsList(newContactsList: List<Contact>) {
        diffUtil = ContactDiffUtil(contactsList, newContactsList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        contactsList.clear()
        contactsList.addAll(newContactsList)
        Log.d("ContactsListAdapter", contactsList.toString())
        diffResult.dispatchUpdatesTo(this)
    }
}