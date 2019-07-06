package com.example.bookrating.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RatingBar
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import com.example.bookrating.R
import kotlinx.android.synthetic.main.rating_dialog.view.*

/**
 * This class represents custom dialog view which contains
 * rating bar, edit box and labels.
 */
class RatingDialogView(context: Context) : LinearLayout(context), RatingBar.OnRatingBarChangeListener {

    private var noteDescriptions: List<String>? = null

    init {
        setup(context)
    }

    /**
     * This method returns current rating.
     *
     * @return number of current selected stars
     */
    val rateNumber: Float
        get() = ratingBar.rating

    /**
     * This method sets maximum numbers of start which are visible.
     *
     * @param numberOfStars maximum number of stars
     */
    fun setNumberOfStars(numberOfStars: Int) {
        ratingBar.setNumStars(numberOfStars)
    }

    /**
     * This method sets color for note descriptions.
     */
    fun setNoteDescriptionTextColor(@ColorRes colorResId: Int) {
        noteDescriptionText.setTextColor(getColorFromRes(colorResId))
    }

    /**
     * This method sets note descriptions for each rating value.
     *
     * @param noteDescriptions list of note descriptions
     */
    fun setNoteDescriptions(noteDescriptions: List<String>) {
        val numberOfStars = noteDescriptions.size
        setNumberOfStars(numberOfStars)
        this.noteDescriptions = noteDescriptions
    }

    /**
     * This method sets default number of stars.
     *
     * @param defaultRating number of stars
     */
    fun setDefaultRating(defaultRating: Int) {
        ratingBar.setRating(defaultRating.toFloat())
    }

    /**
     * This method sets dialog's title.
     *
     * @param title dialog's title text
     */
    fun setTitleText(title: String) {
        titleText.text = title
        titleText.visibility = View.VISIBLE
    }

    /**
     * This method sets dialog's description text.
     *
     * @param content dialog's description text
     */
    fun setDescriptionText(content: String) {
        descriptionText.text = content
        descriptionText.visibility = View.VISIBLE
    }

    /**
     * This method sets color of dialog's title.
     *
     * @param color resource id of title label color
     */
    fun setTitleTextColor(@ColorRes color: Int) {
        titleText.setTextColor(getColorFromRes(color))
    }

    /**
     * This method sets color of dialog's description.
     *
     * @param color resource id of description label color
     */
    fun setDescriptionTextColor(@ColorRes color: Int) {
        descriptionText.setTextColor(getColorFromRes(color))
    }

    override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
        updateNoteDescriptionText((rating - 1).toInt())
    }

    private fun updateNoteDescriptionText(rating: Int) {
        if (noteDescriptions == null || (noteDescriptions?.isEmpty() ?: true)) {
            noteDescriptionText.visibility = View.GONE
            return
        }

        if (rating >= 0) {
            val text = noteDescriptions!![rating]
            noteDescriptionText.text = text
            noteDescriptionText.visibility = View.VISIBLE
        }
    }

    @SuppressLint("ResourceType")
    private fun setup(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.rating_dialog, this, true)
        ratingBar.setIsIndicator(false)
        ratingBar.setOnRatingBarChangeListener(this)

//        TextViewCompat.setTextAppearance(
//            titleText,
//            fetchAttributeValue(R.attr.appRatingDialogTitleStyle)
//        )
//        TextViewCompat.setTextAppearance(
//            descriptionText,
//            fetchAttributeValue(R.attr.appRatingDialogDescriptionStyle)
//        )
//        TextViewCompat.setTextAppearance(
//            noteDescriptionText,
//            fetchAttributeValue(R.attr.appRatingDialogNoteDescriptionStyle)
//        )
    }

    private val theme: Resources.Theme
        get() = context.theme

    private fun getColorFromRes(@ColorRes colorResId: Int): Int {
        return ResourcesCompat.getColor(context.resources, colorResId, theme)
    }

    private fun fetchAttributeValue(attr: Int): Int {
        val outValue = TypedValue()
        context.theme.resolveAttribute(attr, outValue, true)
        return outValue.data
    }
}