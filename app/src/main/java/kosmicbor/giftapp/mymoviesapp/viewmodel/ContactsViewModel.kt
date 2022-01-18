package kosmicbor.giftapp.mymoviesapp.viewmodel

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kosmicbor.giftapp.mymoviesapp.domain.contacts_data.Contact
import kosmicbor.giftapp.mymoviesapp.view.AppState
import kosmicbor.giftapp.mymoviesapp.view.Error
import kosmicbor.giftapp.mymoviesapp.view.Success
import kosmicbor.giftapp.mymoviesapp.view.LoadingState

class ContactsViewModel : ViewModel() {

    private val contactsLiveData = MutableLiveData<AppState<*>>()
    private val contactsList = mutableListOf<Contact>()

    fun getContactsLiveData(): LiveData<AppState<*>> = contactsLiveData

    fun getContactsList(contentResolver: ContentResolver) {

        contactsLiveData.value = LoadingState
        try {
            val cursor: Cursor? = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"

            )

            cursor?.let {
                val columnIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)
                val contactName =
                    it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val contactPhoneNumber =
                    it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

                if (columnIndex >= 0) {
                    for (i in 0..it.count) {
                        if (it.moveToPosition(i)) {
                            val contact = Contact(
                                it.getInt(columnIndex),
                                it.getString(contactName),
                                it.getString(contactPhoneNumber)
                            )
                            contactsList.add(contact)
                            Log.d("Contacts", contactsList.toString())
                            contactsLiveData.postValue(Success(contactsList))
                        }
                    }

                }
                cursor.close()
            }
        } catch (e: Exception) {
            contactsLiveData.postValue(Error<Throwable>(e))
        }
    }
}