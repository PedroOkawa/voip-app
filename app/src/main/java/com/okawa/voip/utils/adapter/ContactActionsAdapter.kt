package com.okawa.voip.utils.adapter

import android.database.Cursor
import android.databinding.ViewDataBinding
import com.okawa.voip.model.Contact

abstract class ContactActionsAdapter<T, K : ViewDataBinding> : CursorBindingAdapter<T, K>()  {

    companion object {
        private const val DEFAULT_ACTIONS_POSITION = -1

        const val ACTION_TYPE_CALL = 0x0000
        const val ACTION_TYPE_EDIT = 0x0001
    }

    private var listener: ((actionType: Int, T?) -> Unit)? = null

    private var showActionsPosition = DEFAULT_ACTIONS_POSITION

    override fun setCursor(cursor: Cursor?) {
        super.setCursor(cursor)
        showActionsPosition = DEFAULT_ACTIONS_POSITION
    }

    /**
     * Method called when Call action is called
     *
     * @param item clicked
     */
    fun onActionCallClicked(item: T?) {
        listener?.invoke(ACTION_TYPE_CALL, item)
    }

    /**
     * Method called when Edit action is called
     *
     * @param item clicked
     */
    fun onActionEditClicked(item: T?) {
        listener?.invoke(ACTION_TYPE_EDIT, item)
    }

    /**
     * Defines the touch listener
     *
     * @param listener that will handle touch events
     */
    fun defineTouchListener(listener: (actionType: Int, T?) -> Unit) {
        this.listener = listener
    }

    /**
     * Validates if shows hide or not
     *
     * @param position index of the item
     */
    protected fun hideActions(position: Int) = showActionsPosition != position

    /**
     * Defines the action position and undo the previous position
     *
     * @param position index of the item
     */
    protected fun defineActionsPosition(position: Int) {
        val previousPosition = showActionsPosition

        showActionsPosition = if(showActionsPosition == position) DEFAULT_ACTIONS_POSITION else position

        if(showActionsPosition != previousPosition) {
            notifyItemChanged(previousPosition)
        }
    }
}