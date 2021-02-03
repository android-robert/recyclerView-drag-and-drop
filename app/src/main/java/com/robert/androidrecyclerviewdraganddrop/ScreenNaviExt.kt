package com.robert.androidrecyclerviewdraganddrop

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlin.reflect.KClass


enum class StyleAnimation {
    SLIDE_FROM_RIGHT, SLIDE_FROM_LEFT, SLIDE_FROM_BOTTOM, SLIDE_NONE
}

fun <T : Activity> KClass<T>.start(activity: Activity, finish: Boolean = false) {
    activity.startActivity(Intent(activity, this.java))
    if (finish) {
        activity.finish()
    }
}

fun <T : Activity> KClass<T>.startClearTop(activity: Activity) {
    val intent = Intent(activity, this.java)
    activity.startActivity(intent)
    activity.finishAffinity()

}

fun androidx.fragment.app.FragmentActivity.replace(fragment: androidx.fragment.app.Fragment, holder: Int = R.id.fragmentHolder, isAddToStack: Boolean = true, animation: StyleAnimation = StyleAnimation.SLIDE_FROM_RIGHT) {
    supportFragmentManager.beginTransaction().apply {
        replace(holder, fragment)
        if (isAddToStack) {
            val currentFragment = supportFragmentManager.findFragmentById(holder)
            val stackName = if (currentFragment == null)
                getFragmentName(fragment)
            else
                getFragmentName(currentFragment)

            addToBackStack(stackName)
        }
        setAnimation(this, animation)
        commit()
    }
}

fun androidx.fragment.app.FragmentActivity.backStack(name: String): Boolean {
    val entryCount = supportFragmentManager.backStackEntryCount
    if (entryCount > 0) {
        return supportFragmentManager.popBackStackImmediate(name, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    return false
}

fun getFragmentName(fragment: androidx.fragment.app.Fragment): String {
    return fragment::class.java.canonicalName ?: fragment::class.java.simpleName
}

fun androidx.fragment.app.FragmentActivity.add(fragment: androidx.fragment.app.Fragment, holder: Int = R.id.fragmentHolder, isAddToStack: Boolean = true, animation: StyleAnimation = StyleAnimation.SLIDE_FROM_RIGHT) {
    supportFragmentManager.beginTransaction().run {
        setAnimation(this, animation)
        add(holder, fragment)
        if (isAddToStack)
            addToBackStack(null)
        commit()
    }
}

fun androidx.fragment.app.Fragment.replace(fragment: androidx.fragment.app.Fragment, holder: Int = R.id.fragmentHolder, isAddToStack: Boolean = true, animation: StyleAnimation = StyleAnimation.SLIDE_FROM_RIGHT) {
    fragmentManager?.beginTransaction()?.run {
        setAnimation(this, animation)
        replace(holder, fragment)
        if (isAddToStack)
            addToBackStack(null)
        commit()
    }
}

fun androidx.fragment.app.Fragment.add(fragment: androidx.fragment.app.Fragment, holder: Int = R.id.fragmentHolder, isAddToStack: Boolean = true, animation: StyleAnimation = StyleAnimation.SLIDE_FROM_RIGHT) {
    fragmentManager?.beginTransaction()?.run {
        setAnimation(this, animation)
        add(holder, fragment)
        if (isAddToStack)
            addToBackStack(null)

        commit()
    }
}

fun androidx.fragment.app.Fragment.goBack(): Boolean {
    return if (activity != null) {
        activity?.onBackPressed()
        true
    } else false


}

fun showKeyboard(activity: Activity) {
    try {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(activity.currentFocus, InputMethodManager.SHOW_IMPLICIT)
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun hideKeyboard(activity: Activity): Boolean {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    try {
        val view = activity.currentFocus
        if (view != null)
            return imm.hideSoftInputFromWindow(view.windowToken, 0)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return false
}

fun hideKeyboard(mContext: Context, v: View) {
    val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    try {
        imm.hideSoftInputFromWindow(v.windowToken, 0)
        v.clearFocus()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Activity.openWebBrowser(url: String) {
    if (url.isBlank()) return
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = Uri.parse(url)
    startActivity(openURL)
}

fun androidx.fragment.app.Fragment.openWebBrowser(url: String) {
    if (url.isBlank()) return
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = Uri.parse(url)
    startActivity(openURL)
}

fun androidx.fragment.app.Fragment.openMap(lat: Double, lng: Double, label: String = "") {
    val gmmIntentUri = Uri.parse("geo:0,0?q=$lat,$lng($label)")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    startActivity(mapIntent)
}

fun androidx.fragment.app.FragmentActivity.popToRootFragment() {
    while (supportFragmentManager.backStackEntryCount > 1) {
        supportFragmentManager.popBackStackImmediate()
    }
}

fun androidx.fragment.app.Fragment.popToChildFragment(toStackCount: Int = 1) {
    while (childFragmentManager.backStackEntryCount > toStackCount) {
        childFragmentManager.popBackStackImmediate()
    }
}

fun androidx.fragment.app.Fragment.popRootFragment() {
    childFragmentManager.popBackStackImmediate(0, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
}

fun androidx.fragment.app.Fragment.popToRoot() {
    activity?.popToRootFragment()
}

private fun setAnimation(transaction: androidx.fragment.app.FragmentTransaction, styleAnimation: StyleAnimation) {
    when (styleAnimation) {
        StyleAnimation.SLIDE_FROM_RIGHT -> transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right)
        StyleAnimation.SLIDE_FROM_LEFT -> transaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.stack_pop, R.anim.stack_push, R.anim.slide_out_to_left)
        StyleAnimation.SLIDE_FROM_BOTTOM -> transaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.stack_push, R.anim.stack_pop, R.anim.slide_out_bottom)
        else -> {
        }
    }
}

interface HasHolderName {

    val childActivePageHolderName: Int

    val childActiveFragment: androidx.fragment.app.Fragment?
}

