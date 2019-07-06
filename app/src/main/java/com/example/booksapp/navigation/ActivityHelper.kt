package com.example.booksapp.navigation

import android.content.Context
import android.content.Intent

/**
 * Create intent for activity by class name
 */
fun intentTo(context: Context, activityClassName: String): Intent {
    return Intent(context, Class.forName(activityClassName))
}

interface DynamicActivity {
    /**
     * The activity class name.
     */
    val phoneClassName: String

}

object Activities {

    const val RATING_PACKAGE = "com.example.bookrating"

    object BookRatingActivity : DynamicActivity {
        override val phoneClassName = "$RATING_PACKAGE.ui.BooksActivity"

        fun newIntent(context: Context): Intent {
            return intentTo(context, phoneClassName)
        }
    }
}