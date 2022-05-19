package com.stone.stoneviewskt.ui.titletoolbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.stone.stoneviewskt.R
import com.stone.stoneviewskt.common.inflate
import com.stone.stoneviewskt.databinding.LayoutTitleToolbarBinding
import org.jetbrains.anko.imageResource

/**
 * desc:    左边一个返回按钮，图标或文字；中间title文本；右边图标或文字，或一个，或几个按钮
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/4/25 14:23
 */
class TitleToolbar(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    LinearLayout(context, attrs, defStyleAttr) {
    private var mLeftText: String? = null
    private var mLeftVisibility: Boolean = false
    private var mLeftImageRes: Int = RES_NONE
    private var mLeftImageVisibility: Boolean = true
    private var mMiddleText: String? = null
    private var mRightText: String? = null
    private var mRightVisibility: Boolean = true
    private var mRightImageRes: Int = RES_NONE
    private var mRightImageVisibility: Boolean = true
    private var mRightViewRes: Int = RES_NONE

    val mTvLeft: TextView
    val mTvMiddle: TextView
    val mTvRight: TextView
    val mIvLeft: ImageView
    val mIvRight: ImageView

    private var mBind: LayoutTitleToolbarBinding

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    companion object {
        private const val RES_NONE = -1
    }

    init {
//        LayoutInflater.from(context).inflate(R.layout.layout_title_toolbar, this, true)
        mBind = inflate<LayoutTitleToolbarBinding>(this, true)

        if (attrs != null) {
            val ary = context.obtainStyledAttributes(attrs, R.styleable.TitleToolbar)
            mLeftText = ary.getString(R.styleable.TitleToolbar_leftText)
            mLeftVisibility = ary.getBoolean(R.styleable.TitleToolbar_leftVisibility, false)
            mLeftImageRes = ary.getResourceId(R.styleable.TitleToolbar_leftImage, RES_NONE)
            mLeftImageVisibility = ary.getBoolean(R.styleable.TitleToolbar_leftImageVisibility, true)
            mMiddleText = ary.getString(R.styleable.TitleToolbar_middleText)
            mRightText = ary.getString(R.styleable.TitleToolbar_rightText)
            mRightImageRes = ary.getResourceId(R.styleable.TitleToolbar_rightImage, RES_NONE)
            mRightVisibility = ary.getBoolean(R.styleable.TitleToolbar_rightVisibility, false)
            mRightImageVisibility = ary.getBoolean(R.styleable.TitleToolbar_rightImageVisibility, false)
            mRightViewRes = ary.getResourceId(R.styleable.TitleToolbar_rightView, RES_NONE)
            ary.recycle()
        }

        mTvLeft = mBind.layoutTitleToolbarLeftTv
        mIvLeft = mBind.layoutTitleToolbarLeftIv
        mTvMiddle = mBind.layoutTitleToolbarMiddleTv
        mTvRight = mBind.layoutTitleToolbarRightTv
        mIvRight = mBind.layoutTitleToolbarRightIv

        mLeftText?.let { mTvLeft.text = it }
        mTvLeft.visibility = if (mLeftVisibility) View.VISIBLE else View.GONE

        mIvLeft.visibility = if (mLeftImageVisibility) View.VISIBLE else View.GONE
        if (mLeftImageRes != RES_NONE) mIvLeft.imageResource = mLeftImageRes

        mMiddleText?.let { mTvMiddle.text = it }

        mRightText?.let { mTvRight.text = it }
        mTvRight.visibility = if (mRightVisibility) View.VISIBLE else View.GONE
        mIvRight.visibility = if (mRightImageVisibility) View.VISIBLE else View.GONE
        if (mRightImageRes != RES_NONE) mIvRight.imageResource = mRightImageRes
        if (mRightViewRes != RES_NONE) {
            mIvRight.visibility = View.GONE
            //todo 如加个  right layout， add view， （inflate-mRightViewRes)
        }
    }

}