package com.siekang.core.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TableLayout
import android.widget.TableRow
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.siekang.R
import timber.log.Timber


@SuppressLint("NewApi")
class ToggleButtonGroupTableLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TableLayout(context, attrs), View.OnClickListener {

    private val TAG = "ToggleButtonGroupTableLayout"
    private var mContext: Context? = null

    private var tbGroup: ToggleButtonGroupTableLayout? = null
    private var activeRadioButton: RadioButton? = null

    private var activeCardView: MaterialCardView? = null

    private val radioButtonLiveData: MutableLiveData<Int> = MutableLiveData()
    fun getRadioButtonLiveData(): LiveData<Int> {
        return radioButtonLiveData
    }

    /* Remaining constructors here */

    init {
        Timber.d("Kotlin init block called.")
        this.mContext = context
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
            // (v as? RadioButton)?.setOnClickListener(this)
            (v as? MaterialCardView)?.setOnClickListener(this)
        }
    }

    fun getCheckedRadioButtonId(): Int {
        /*return if (activeRadioButton != null) {
            activeRadioButton!!.id
        } else -1*/

        return if (activeCardView != null) {
            activeCardView!!.id
        } else -1
    }


    override fun onClick(view: View) {
        /*val rb = view as RadioButton
        if (activeRadioButton != null) {
            activeRadioButton!!.isChecked = false
        }
        rb.isChecked = true
        activeRadioButton = rb*/

        val cardView = view as MaterialCardView
        if (activeCardView != null) {
            activeCardView!!.isSelected = false
            uncheckCardView()
        }

        cardView.isSelected = true
        checkCardView(cardView)
        activeCardView = cardView

        radioButtonLiveData.value = activeCardView?.id
    }

    private fun uncheckCardView() {
        activeCardView?.strokeWidth = 0
        activeCardView?.setStrokeColor(ColorStateList.valueOf(mContext?.getColor(R.color.transparent)!!))
        // (activeCardView?.getChildAt(0) as MaterialTextView).setTextColor(mContext?.getColor(R.color.white)!!)

        val textView = (activeCardView?.getChildAt(0) as MaterialTextView)
        // textView.setTextColor(mContext?.getColor(R.color.quiz_item_selected_text_color)!!)

        textView.setTextColor(ContextCompat.getColor(mContext!!, R.color.white))
        textView.setShadowLayer(
            0.0f,
            0f,
            0f,
            ContextCompat.getColor(mContext!!, R.color.transparent)
        )
    }

    private fun checkCardView(cardView: MaterialCardView) {
        cardView.strokeWidth = 8
        cardView.setStrokeColor(ColorStateList.valueOf(mContext?.getColor(R.color.quiz_item_selected_stroke)!!))

        val textView = (cardView.getChildAt(0) as MaterialTextView)
        // textView.setTextColor(mContext?.getColor(R.color.quiz_item_selected_text_color)!!)

        textView.setTextColor(ContextCompat.getColor(mContext!!, R.color.white))
        textView.setShadowLayer(
            0.05f,
            -5f,
            5f,
            ContextCompat.getColor(mContext!!, R.color.quiz_item_selected_text_color)
        )
    }
}