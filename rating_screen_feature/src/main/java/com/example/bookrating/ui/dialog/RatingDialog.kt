package com.example.bookrating.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.bookrating.R
import com.example.bookrating.ui.listener.RatingDialogListener
import com.example.bookrating.ui.view.RatingDialogView

const val CURRENT_RATE_NUMBER = "currentRateNumber"
const val BOOK_ID = "bookId"
const val NUMBER_OF_STARS = 5

class RatingDialog : DialogFragment() {

    private var listener: RatingDialogListener? = null

    private lateinit var data: String
    private lateinit var dialogView: RatingDialogView
    private lateinit var alertDialog: AlertDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return setupAlertDialog(activity!!)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putFloat(CURRENT_RATE_NUMBER, dialogView.rateNumber)
        super.onSaveInstanceState(outState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val rateNumber: Float? = savedInstanceState?.getFloat(CURRENT_RATE_NUMBER)
        if (rateNumber != null) {
            dialogView.setDefaultRating(rateNumber.toInt())
        }
    }

    private fun setupAlertDialog(context: Context): AlertDialog {
        dialogView = RatingDialogView(context)
        val dialogBuilder = AlertDialog.Builder(context)

        data = arguments?.getSerializable(BOOK_ID) as String

        dialogBuilder.setView(dialogView)

        setupDescription()
        setupRatingBar()
        setupNegativeButton(dialogBuilder)
        setupPositiveButton(dialogView, dialogBuilder)

        alertDialog = dialogBuilder.create()

        return alertDialog
    }

    private fun setupDescription() {
        dialogView.setTitleText(getString(R.string.rating_dialog))
        dialogView.setDescriptionText(getString(R.string.rating_dialog_description))
    }

    private fun setupRatingBar() {
        dialogView.setNumberOfStars(NUMBER_OF_STARS)
        dialogView.setNoteDescriptions(listOf(getString(R.string.do_not_open), getString(R.string.bad), getString(R.string.normal), getString(R.string.good), getString(R.string.excelent)))
    }

    private fun setupNegativeButton(builder: AlertDialog.Builder) {
        builder.setNegativeButton(getText(R.string.cancel)) { _, _ ->
            listener?.onNegativeButtonClicked()
        }
    }

    private fun setupPositiveButton(dialogView: RatingDialogView, builder: AlertDialog.Builder) {
        builder.setPositiveButton(getText(R.string.rate)) { _, _ ->
            val rateNumber = dialogView.rateNumber.toInt()
            listener?.onPositiveButtonClicked(rateNumber, data)
        }
    }

    fun setListener(ratingListener: RatingDialogListener) {
        listener = ratingListener
    }

    companion object {

        fun newInstance(bookId: String): RatingDialog {
            val fragment = RatingDialog()
            val bundle = Bundle()
            bundle.putSerializable(BOOK_ID, bookId)
            fragment.arguments = bundle
            return fragment
        }
    }

}