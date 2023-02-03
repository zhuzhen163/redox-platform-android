package view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by 高大爽 on 2018/6/13.
 */

public class CustomEditText extends android.support.v7.widget.AppCompatEditText {
    private Context context;

    public CustomEditText(Context context) {
        super(context);
        this.context = context;
        //init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //init();
    }

    private void init() {
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (!s.toString().equals(stringFilter(s.toString()))) {
                        Toast.makeText(context, "不能输入特殊符号", Toast.LENGTH_SHORT).show();
                        int pos = s.length() - 1;
                        s.delete(pos, pos + 1);
                    }
                }

            }
        });

    }

    private String stringFilter(String str) throws PatternSyntaxException {
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }
}
