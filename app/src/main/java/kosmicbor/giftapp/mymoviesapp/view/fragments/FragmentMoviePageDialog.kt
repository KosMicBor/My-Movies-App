package kosmicbor.giftapp.mymoviesapp.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kosmicbor.giftapp.mymoviesapp.R

class FragmentMoviePageDialog : BottomSheetDialogFragment() {

    private var note: String? = ""
    var okListener: OkClickListener? = null

    companion object {
        fun newInstance() = FragmentMoviePageDialog()
        private const val NOTE_DIALOG = "NOTE_DIALOG"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.insert_note_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputText: TextInputEditText = view.findViewById(R.id.note_input_edit_text)
        val buttonOK: MaterialButton = view.findViewById(R.id.btnOk)
        val buttonCancel: MaterialButton = view.findViewById(R.id.btnCancel)

        isCancelable = false;
        buttonOK.setOnClickListener {
            note = inputText.text.toString().trim()
            okListener?.onClick(note)
            dismiss()
        }

        buttonCancel.setOnClickListener {

        }
    }

    fun interface OkClickListener {
        fun onClick(noteText: String?)
    }
}