package app.web.sleepcoder.core.ui

interface OnItemClicked<T> {
    fun itemClick(position: T)
}