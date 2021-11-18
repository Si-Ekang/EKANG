package com.siekang.core.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TableLayout
import android.widget.TableRow
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.siekang.R
import timber.log.Timber


class ToggleButtonGroupTableLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TableLayout(context, attrs), View.OnClickListener {

    private val TAG = "ToggleButtonGroupTableLayout"

    private var tbGroup: ToggleButtonGroupTableLayout? = null
    private var activeRadioButton: RadioButton? = null

    val radioButtonLiveData: MutableLiveData<Int> = MutableLiveData()
    fun getRadioButtonLiveData(): LiveData<Int> {
        return radioButtonLiveData
    }

    /* Remaining constructors here */

    init {
        Timber.d("Kotlin init block called.")
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        tbGroup = findViewById(R.id.tb_group)
    }

    /* (non-Javadoc)
     * @see android.widget.TableLayout#addView(android.view.View, int, android.view.ViewGroup.LayoutParams)
     */
    override fun addView(
        child: View, index: Int,
        params: ViewGroup.LayoutParams?
    ) {
        super.addView(child, index, params)
        setChildrenOnClickListener(child as TableRow)
    }


    /* (non-Javadoc)
     * @see android.widget.TableLayout#addView(android.view.View, android.view.ViewGroup.LayoutParams)
     */
    override fun addView(child: View, params: ViewGroup.LayoutParams?) {
        super.addView(child, params)
        setChildrenOnClickListener(child as TableRow)
    }


    private fun setChildrenOnClickListener(tr: TableRow) {
        val c: Int = tr.childCount
        for (i in 0 until c) {
            val v: View = tr.getChildAt(i)
            (v as? RadioButton)?.setOnClickListener(this)
        }
    }

    fun getCheckedRadioButtonId(): Int {
        return if (activeRadioButton != null) {
            activeRadioButton!!.id
        } else -1
    }


    override fun onClick(view: View) {
        val rb = view as RadioButton
        if (activeRadioButton != null) {
            activeRadioButton!!.isChecked = false
        }
        rb.isChecked = true
        activeRadioButton = rb

        radioButtonLiveData.value = activeRadioButton?.id
    }
}