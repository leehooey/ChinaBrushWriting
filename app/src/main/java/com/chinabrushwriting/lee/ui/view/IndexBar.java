package com.chinabrushwriting.lee.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.chinabrushwriting.lee.R;
import com.chinabrushwriting.lee.utils.AnimaUtils;
import com.chinabrushwriting.lee.utils.UIUtils;

/**
 * Created by Lee on 2016/7/22.
 */
public  class IndexBar extends View {
    private static final String TAG = "IndexBar";
    private String[] letter = new String[]{
            "A", "B", "C", "D", "E", "F", "J", "H", "I", "G",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };
    private Paint paint;
    private float width;
    private float cellHeight;
    private int textHeight;
    private TextView tvShowView;

    public IndexBar(Context context) {
        super(context);
        initView();
    }

    public IndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public IndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int viewHeight = getMeasuredHeight();
        cellHeight = viewHeight * 1f / letter.length;//每一个字母占的高度
    }

    private void initView() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //索引栏宽度
        width = UIUtils.getDimens(R.dimen.index_bar_width);
        //字母大小
        paint.setTextSize(UIUtils.getDimens(R.dimen.text_size));
        //字母以底边中心为坐标原点
        paint.setTextAlign(Paint.Align.CENTER);
        //获取文字高度大小
        textHeight = getTextHeight(letter[0]);
    }

    //记录当前手指滑动位置的字母
    private int lastIndex = -1;

    //将文字绘制到view
    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < letter.length; i++) {
            //当前字母Y轴大小
            float height = i * cellHeight + cellHeight / 2 + textHeight / 2;
            if (i == lastIndex) {
                paint.setColor(Color.GRAY);
            } else {
                paint.setColor(Color.WHITE);
            }
            canvas.drawText(letter[i], width / 2, height, paint);
        }

    }

    /**
     * 手指滑动索引栏所触发的事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float position = event.getY();
                //根据手指移动的位置判断属于什么索引
                int index = (int) (position / cellHeight);
                //在同一个索引移动打印一次
                if (index != lastIndex) {
                    op.onPosition(letter[index]);
                    lastIndex = index;
                    //系统重新绘制界面
                    invalidate();
                }
                tvShowView.setVisibility(View.VISIBLE);
                break;
            case MotionEvent.ACTION_UP:
                tvShowView.startAnimation(AnimaUtils.alpha(1, 0, 1000));
                tvShowView.setVisibility(GONE);

                lastIndex = -1;
                break;
        }
        return true;
    }

    //获取字体的高度
    private int getTextHeight(String text) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    private OnPositionChanged op;

    //设置监听器
    public void setPositionChangedListener(OnPositionChanged op) {
        this.op = op;
    }

    //listView调用该方法重新绘制view
    public void refreshView(char c) {
        // lastIndex=position;
        String str = c + "";
        for (int i = 0; i < letter.length; i++) {
            if (str.equals(letter[i])) {
                lastIndex = i;
                break;
            }
        }
        invalidate();
    }

    public interface OnPositionChanged {
        void onPosition(String letter);
    }

    public  void  setTextValueListener(OnTextValueListener listener) {
        this.tvShowView = listener.get();
    }
    public interface OnTextValueListener {
        TextView get();
    }

}
