package com.yusys.mpos.base.widget.keyboard;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yusys.mpos.R;
import com.yusys.mpos.base.YXApplication;

import java.util.List;

/**
 * 软键盘管理器,管理自定义软键盘
 * 需要在根布局中引入KeyboardView
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-04-05
 */
public class KeyboardManager {

    // 编码,在配置文件dimens中编辑,需要动态的初始化
    private static int CODE_GOLEFT;// 左移
    private static int CODE_GORIGHT;// 右移

    private static KeyboardManager instance;
    private KeyboardView keyboardView;
    private Keyboard keyboardWords;// 字母键盘
    private Keyboard keyboardNumbers;// 数字键盘

    private EditText target;// 软键盘的文字输出到哪里
    private Activity rootActivity;
    private ViewGroup rootView;// 根控件
    private int inputType;
    public boolean isNum;// 是否是数字键盘
    public boolean isUpper;// 是否大写

    /**
     * 单例,同一时间只能存在一个软键盘
     *
     * @return 软键盘管理器
     */
    public static KeyboardManager getInstance() {
        if (instance == null) {
            instance = new KeyboardManager();
            instance.init();
        }
        return instance;
    }

    private KeyboardManager() {
    }

    private void init() {
        CODE_GOLEFT = YXApplication.getInstance().getResources().getInteger(R.integer.code_goleft);
        CODE_GORIGHT = YXApplication.getInstance().getResources().getInteger(R.integer.code_goright);
        // 实例化软键盘
        LayoutInflater inflater = (LayoutInflater) YXApplication.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        keyboardView = (KeyboardView) inflater.inflate(R.layout.item_keyboard, null);
        keyboardWords = new Keyboard(YXApplication.getInstance(), R.xml.xml_keyboard_words);
        keyboardNumbers = new Keyboard(YXApplication.getInstance(), R.xml.xml_keyboard_numbers);
        keyboardView.setKeyboard(keyboardWords);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(true);
        keyboardView.setOnKeyboardActionListener(new OnKeyboardActionListener() {
            @Override
            public void swipeUp() {
            }

            @Override
            public void swipeRight() {
            }

            @Override
            public void swipeLeft() {
            }

            @Override
            public void swipeDown() {
            }

            @Override
            public void onText(CharSequence text) {
            }

            @Override
            public void onRelease(int primaryCode) {
            }

            @Override
            public void onPress(int primaryCode) {
            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                if (target == null) {
                    return;
                }
                Editable editable = target.getText();
                int start = target.getSelectionStart();
                switch (primaryCode) {
                    case Keyboard.KEYCODE_CANCEL:// 完成
                        hideKeyboard();
                        break;
                    case Keyboard.KEYCODE_DELETE:// 回退
                        if (editable != null && editable.length() > 0) {
                            if (start > 0) {
                                editable.delete(start - 1, start);
                            }
                        }
                        break;
                    case Keyboard.KEYCODE_SHIFT:// 大小写切换
                        changeKey();
                        keyboardView.setKeyboard(keyboardWords);
                        break;
                    case Keyboard.KEYCODE_MODE_CHANGE:// 键盘类型切换
                        if (isNum) {
                            isNum = false;
                            keyboardView.setKeyboard(keyboardWords);
                        } else {
                            isNum = true;
                            keyboardView.setKeyboard(keyboardNumbers);
                        }
                        break;
                    default:
                        if (primaryCode == CODE_GOLEFT) { // go left
                            if (start > 0) {
                                target.setSelection(start - 1);
                            }
                        } else if (primaryCode == CODE_GORIGHT) { // go right
                            if (start < target.length()) {
                                target.setSelection(start + 1);
                            }
                        } else {
                            editable.insert(start, Character.toString((char) primaryCode));
                        }
                        break;
                }
            }
        });

    }

    /**
     * 得到软键盘的输出目标
     *
     * @return 目标
     */
    public EditText getTarget() {
        return target;
    }

    /**
     * 将软键盘的字符显示到目标上,设置的同时可能会刷新根视图
     *
     * @param target 目标
     */
    public void setTarget(EditText target) {
        if (target == null) {
            return;
        }
        this.target = target;
        inputType = target.getInputType();
        target.setInputType(InputType.TYPE_NULL);// 强制隐藏系统默认软键盘
        if (rootActivity != target.getContext()) {// 得到新的根视图
            rootActivity = (Activity) target.getContext();
            ViewGroup contentView = (ViewGroup) rootActivity.findViewById(android.R.id.content);
            rootView = (ViewGroup) contentView.getChildAt(0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showKeyboard() {
        ViewGroup parent = (ViewGroup) keyboardView.getParent();
        if (parent != null) {// 有父控件说明软键盘正在显示
            return;
        }
        if (rootView != null) {
            keyboardView.setKeyboard(keyboardWords);
            target.setInputType(inputType);
            rootView.addView(keyboardView);
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard() {
        ViewGroup parent = (ViewGroup) keyboardView.getParent();
        if (parent != null) {
            parent.removeView(keyboardView);
            keyboardView.setKeyboard(keyboardWords);
        }
    }

    /**
     * 大小写切换
     */
    private void changeKey() {
        List<Key> keylist = keyboardWords.getKeys();
        if (isUpper) {// 大写切换小写
            isUpper = false;
            for (Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        } else {// 小写切换大写
            isUpper = true;
            for (Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
            }
        }
    }


    private boolean isword(String str) {
        String wordstr = "abcdefghijklmnopqrstuvwxyz";
        if (wordstr.indexOf(str.toLowerCase()) > -1) {
            return true;
        }
        return false;
    }

}