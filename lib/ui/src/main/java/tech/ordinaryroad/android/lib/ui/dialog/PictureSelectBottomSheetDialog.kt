package tech.ordinaryroad.android.lib.ui.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import tech.ordinaryroad.android.lib.ui.R


class PictureSelectBottomSheetDialog : BottomSheetDialog, View.OnClickListener {

    private var i: IOnClickListener

    constructor(i: IOnClickListener, context: Context) : super(context) {
        this.i = i
    }

    constructor(i: IOnClickListener, context: Context, theme: Int) : super(context, theme) {
        this.i = i
    }

    constructor(
        i: IOnClickListener, context: Context, cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener) {
        this.i = i
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.dialog_bottom_sheet_picture_select)
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        findViewById<TextView>(R.id.text_view_clear_user_head)?.setOnClickListener(this)
        findViewById<TextView>(R.id.text_view_select_picture)?.setOnClickListener(this)
        findViewById<TextView>(R.id.text_view_hide_bottom)?.setOnClickListener(this)
        val baseFrameLayout = findViewById<FrameLayout>(R.id.design_bottom_sheet)
        baseFrameLayout?.setBackgroundResource(android.R.color.transparent)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.text_view_clear_user_head -> {
                MaterialAlertDialog(context, titleRes = R.string.confirm_clear).show {
                    i.onClickClear()
                }
            }
            R.id.text_view_select_picture -> i.onClickSelect()
            else -> {
                //ignore
            }
        }
        dismiss()
    }

    interface IOnClickListener {
        fun onClickClear()
        fun onClickSelect()
    }

}