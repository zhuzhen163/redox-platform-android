package view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.redoxyt.app.common.R;

import java.util.List;

/**
 * description:弹出窗自定义
 * author: GJX
 * date:2017/2/16:18:15
 */
public class CommonCustomDialog extends Dialog {


    public CommonCustomDialog(Context context) {
        super(context);
    }

    public CommonCustomDialog(Context context, int theme) {
        super(context, theme);

    }


    public static class Builder {
        /**
         * description:0原始，1输入框，
         * class:
         * author: GJX
         * date:
         */
        public int mType;
        int mViewSpacingLeft;
        int mViewSpacingRight;
        int mViewSpacingTop;
        int mViewSpacingBottom;
        private Context context;
        private String mTitle;
        private View nBView;
        private int mStyle;
        private List mList;
        private String mMessage;
        private String mMessageTwo;
        private String mMessageThree;
        private String mMessageFour;
        private String mTitleMessage;
        private String mTitleMessageTwo;
        private String mTitleMessageThree;
        private String mTitleMessageFour;
        private String mNewPassWord;
        private String mOldPassWord;
        private int mProgress;
        private String mReNewPassWord;
        private String mEditText;
        private String mPositiveButtonText;
        private String mNeutralButtonText;
        private String mNegativeButtonText;
        private View mContentView;
        private boolean mCancelable;
        private boolean mOutCancelable;
        private Adapter mAdapter;
        private OnClickListener mPositiveButtonClickListener;
        private OnClickListener mNegativeButtonClickListener;
        private OnClickListener mNeutralButtonClickListener;
        private int mViewLayoutResId;
        private boolean mViewSpacingSpecified;
        private View mView;
        private boolean mPassWord;
        private boolean isShowing;
        private boolean isSetBackGround;
        private int mHeight = 0;
        private int mWeight = 0;
        EditText et;//

        public Builder(Context context, int Type) {
            this.context = context;
            this.mType = Type;
        }

        public Builder isSetBackGround(boolean SetBackGround) {
            this.isSetBackGround = SetBackGround;
            return this;
        }

        public Builder setHeight(int height) {
            this.mHeight = height;
            return this;
        }

        public Builder setWeight(int weight) {
            this.mWeight = weight;
            return this;
        }

        public boolean getDialogIsShowing() {

            return isShowing;
        }

        public Builder setMessage(String message) {
            this.mMessage = message;
            return this;
        }

        public View getGridView() {
            return nBView;
        }

        public Builder setGridView(View view) {
            this.nBView = view;
            return this;
        }

        public Builder setList(List list) {
            this.mList = list;
            return this;
        }

        public Builder setAdapter(Adapter adapter) {
            this.mAdapter = adapter;
            return this;
        }

        public Builder setMessageTwo(String messagetwo) {
            this.mMessageTwo = messagetwo;
            return this;
        }

        public Builder setMessageThree(String messagethree) {
            this.mMessageThree = messagethree;
            return this;
        }

        public Builder setMessageFour(String messagefour) {
            this.mMessageFour = messagefour;
            return this;
        }

        public Builder setTitleMessage(String message) {
            this.mTitleMessage = message;
            return this;
        }

        public Builder setTitleMessageTwo(String messagetwo) {
            this.mTitleMessageTwo = messagetwo;
            return this;
        }

        public Builder setTitleMessageThree(String messagethree) {
            this.mTitleMessageThree = messagethree;
            return this;
        }

        public Builder setTitleMessageFour(String messagefour) {
            this.mTitleMessageFour = messagefour;
            return this;
        }

        /**
         * 写入message
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.mMessage = (String) context.getText(message);
            return this;
        }

        /**
         * 写入EditText
         *
         * @param edittext
         * @return
         */
        public Builder setEditText(int edittext) {
            this.mEditText = (String) context.getText(edittext);
            return this;
        }

        /**
         * 获取EditText值
         *
         * @param edittext
         * @return
         */
        public String getEditText(String edittext) {
            this.mEditText = edittext;
            return edittext;
        }

        /**
         * 修改输入框样式
         */
        public void setEditTextType() {
            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        }

        /**
         * 获取message
         *
         * @param
         * @return
         */
        public String getEditText() {

            return mEditText;
        }

        /**
         * 写入EditText
         *
         * @param edittext
         * @return
         */
        public Builder setEditText(String edittext) {
            this.mEditText = edittext;
            return this;
        }

        /**
         * 获取EditText值
         *
         * @param newpassword
         * @return
         */
        public String getNewPassWord(String newpassword) {
            this.mNewPassWord = newpassword;
            return newpassword;
        }

        /**
         * 获取message
         *
         * @param
         * @return
         */
        public String getNewPassWord() {

            return mNewPassWord;
        }

        /**
         * 获取EditText值
         *
         * @param oldpassword
         * @return
         */
        public String getOldPassWord(String oldpassword) {
            this.mOldPassWord = oldpassword;
            return oldpassword;
        }

        /**
         * 获取message
         *
         * @param
         * @return
         */
        public String getOldPassWord() {
            return mOldPassWord;
        }

        /**
         * 获取EditText值
         *
         * @param renewpassword
         * @return
         */
        public String getReNewPassWord(String renewpassword) {
            this.mReNewPassWord = renewpassword;
            return renewpassword;
        }

        /**
         * 获取message
         *
         * @param
         * @return
         */
        public String getReNewPassWord() {
            return mReNewPassWord;
        }

        /**
         * 写title
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.mTitle = (String) context.getText(title);
            return this;
        }

        public Builder setProgress(int progress) {
            this.mProgress = progress;
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param style
         * @return
         */

        public Builder setStyle(int style) {
            this.mStyle = style;
            return this;
        }

        /**
         * description:
         * class:
         * author: GJX
         * date:
         */
        public Builder setContentView(View v) {
            this.mContentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.mPositiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.mPositiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.mPositiveButtonText = positiveButtonText;
            this.mPositiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.mNegativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.mNegativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.mNegativeButtonText = negativeButtonText;
            this.mNegativeButtonClickListener = listener;
            return this;
        }

        public Builder setNeutralButton(int neutralButtonText,
                                        OnClickListener listener) {
            this.mNeutralButtonText = (String) context
                    .getText(neutralButtonText);
            this.mNeutralButtonClickListener = listener;
            return this;
        }

        public Builder setNeutralButton(String neutralButtonText,
                                        OnClickListener listener) {
            this.mNeutralButtonText = neutralButtonText;
            this.mNeutralButtonClickListener = listener;
            return this;
        }

        /**
         * description:false时dialog弹出后会点击屏幕或物理返回键，dialog不消失
         * class:
         * author: GJX
         * date:
         */
        public Builder setCancelable(boolean cancelable) {
            this.mCancelable = cancelable;
            return this;
        }

        public Builder setPassWord(boolean passWord) {
            this.mPassWord = passWord;
            return this;
        }

        /**
         * description:
         * class:
         * author: GJX
         * date:
         */
        public Builder setCanceledOnTouchOutside(boolean outcancelable) {
            this.mOutCancelable = outcancelable;
            return this;
        }

        /**
         * Set a custom view resource to be the contents of the Dialog. The
         * resource will be inflated, adding all top-level views to the screen.
         *
         * @param layoutResId Resource ID to be inflated.
         * @return This Builder object to allow for chaining of calls to set
         * methods
         */
        public Builder setView(int layoutResId) {
            this.mView = null;
            this.mViewLayoutResId = layoutResId;
            this.mViewSpacingSpecified = false;
            return this;
        }

        /**
         * description:可以自定义设置一个dialog
         * class:
         * author: GJX
         * date:
         */
        public Builder setView(View view) {
            this.mView = view;
            this.mViewLayoutResId = 0;
            this.mViewSpacingSpecified = false;
            return this;
        }

        /**
         * Set a custom view to be the contents of the Dialog, specifying the
         * spacing to appear around that view. If the supplied view is an
         * instance of a {@link ListView} the light background will be used.
         *
         * @param view              The view to use as the contents of the Dialog.
         * @param viewSpacingLeft   Spacing between the left edge of the view and
         *                          the dialog frame
         * @param viewSpacingTop    Spacing between the top edge of the view and
         *                          the dialog frame
         * @param viewSpacingRight  Spacing between the right edge of the view
         *                          and the dialog frame
         * @param viewSpacingBottom Spacing between the bottom edge of the view
         *                          and the dialog frame
         * @return This Builder object to allow for chaining of calls to set
         * methods
         * s设置前后左右间距
         * <p>
         * This is currently hidden because it seems like people should just
         * be able to put padding around the view.
         * @hide
         */
        public Builder setView(View view, int viewSpacingLeft, int viewSpacingTop,
                               int viewSpacingRight, int viewSpacingBottom) {
            this.mView = view;
            this.mViewLayoutResId = 0;
            this.mViewSpacingSpecified = true;
            this.mViewSpacingLeft = viewSpacingLeft;
            this.mViewSpacingTop = viewSpacingTop;
            this.mViewSpacingRight = viewSpacingRight;
            this.mViewSpacingBottom = viewSpacingBottom;
            return this;
        }


        public CommonCustomDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
//            LayoutInflater inflater = LayoutInflater.from(context);
            // instantiate the dialog with the custom Theme
            //dialog样式设置
            CommonCustomDialog dialog = new CommonCustomDialog(context, R.style.Dialog);
            switch (mType) {
                case 0:
                    initMessage(dialog, inflater);
                    break;
                case 1:
                    initEditText(dialog, inflater);
                    break;
                case 2:
                    initInputEdit(dialog, inflater);
                    break;
                case 3:
                    initMessage2(dialog, inflater);
                    break;
                case 4:
                    initMessage3(dialog, inflater);
                    break;
                case 5:
                    initView(dialog, inflater);
                    break;
                case 6:
                    initMapDialog(dialog, inflater);
                    break;
                case 8:
                    initSuccess(dialog, inflater);
                    break;
                case 9:
                    initDefeat(dialog, inflater);
                    break;
            }


            return dialog;
        }


        private void initDefeat(final CommonCustomDialog dialog, LayoutInflater inflater) {
            //界面设置
            View layout = inflater.inflate(R.layout.messagedialog_simple_style_defeat, null);

            dialog.setCancelable(mCancelable);
            //点击外边
            if (mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }

            //添加一个额外的内容视图到屏幕上。
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // 设置title
            if (mTitle != null) {
                ((TextView) layout.findViewById(R.id.title)).setText(mTitle);
                layout.findViewById(R.id.view_title).setVisibility(View.VISIBLE);
                ((TextView) layout.findViewById(R.id.message)).setTextSize(14);
                layout.findViewById(R.id.title_layout).setVisibility(
                        View.VISIBLE);
            } else {
                // 没有隐藏
                layout.findViewById(R.id.title).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.title_layout).setVisibility(
                        View.GONE);
                ((TextView) layout.findViewById(R.id.message)).setTextSize(17);
                layout.findViewById(R.id.view_title).setVisibility(View.GONE);
            }
            // 设置内容
            if (mMessage != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(mMessage);
            } else if (mContentView != null) {
                //如果没有消息集
                //添加contentView身体的对话框
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(mContentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            }


            // 设置确定按钮
            if (mPositiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(mPositiveButtonText);
                if (mPositiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    mPositiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // 没有则隐藏
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.v_btn).setVisibility(View.GONE);
            }
            // 设置取消按钮
            if (mNegativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(mNegativeButtonText);
                if (mNegativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    mNegativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // 没有隐藏
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.v_btn).setVisibility(View.GONE);
            }
            dialog.setContentView(layout);
        }

        private void initSuccess(final CommonCustomDialog dialog, LayoutInflater inflater) {

            //界面设置
            View layout = inflater.inflate(R.layout.messagedialog_simple_style_success, null);

            dialog.setCancelable(mCancelable);
            //点击外边
            if (mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }

            //添加一个额外的内容视图到屏幕上。
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // 设置title
            if (mTitle != null) {
                ((TextView) layout.findViewById(R.id.title)).setText(mTitle);
                layout.findViewById(R.id.view_title).setVisibility(View.VISIBLE);
                ((TextView) layout.findViewById(R.id.message)).setTextSize(14);
                layout.findViewById(R.id.title_layout).setVisibility(
                        View.VISIBLE);
            } else {
                // 没有隐藏
                layout.findViewById(R.id.title).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.title_layout).setVisibility(
                        View.GONE);
                ((TextView) layout.findViewById(R.id.message)).setTextSize(17);
                layout.findViewById(R.id.view_title).setVisibility(View.GONE);
            }
            // 设置内容
            if (mMessage != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(mMessage);
            } else if (mContentView != null) {
                //如果没有消息集
                //添加contentView身体的对话框
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(mContentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            }


            // 设置确定按钮
            if (mPositiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(mPositiveButtonText);
                if (mPositiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    mPositiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // 没有则隐藏
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.v_btn).setVisibility(View.GONE);
            }
            // 设置取消按钮
            if (mNegativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(mNegativeButtonText);
                if (mNegativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    mNegativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // 没有隐藏
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.v_btn).setVisibility(View.GONE);
            }
            dialog.setContentView(layout);
        }

        private void initInputEdit(final CommonCustomDialog dialog, LayoutInflater inflater) {
            View layout = inflater.inflate(R.layout.edittextdialog_input_style, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dialog.setCancelable(mCancelable);
            dialog.setCanceledOnTouchOutside(mOutCancelable);

//            LinearLayout ll = (LinearLayout) layout.findViewById(R.id.ll_content);
//            ll.removeView(mView);
//            ll.addView(mView);
            // 设置title
            if (mTitle != null) {
                TextView tvTitle = (TextView) layout.findViewById(R.id.title);
                tvTitle.setText(mTitle);
            }
            final EditText oldPwd = (EditText) layout.findViewById(R.id.et_old_pwd);
            final EditText newPwd1 = (EditText) layout.findViewById(R.id.et_new_pwd1);
            final EditText newPwd2 = (EditText) layout.findViewById(R.id.et_new_pwd2);

            isShowing = dialog.isShowing();

            // 设置确定按钮
            if (mPositiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(mPositiveButtonText);
                if (mPositiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    getOldPassWord(oldPwd.getText().toString().trim());
                                    getNewPassWord(newPwd1.getText().toString().trim());
                                    getReNewPassWord(newPwd2.getText().toString().trim());
                                    mPositiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            }
            // 设置取消按钮
            if (mNegativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(mNegativeButtonText);
                if (mNegativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    mNegativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            }
            dialog.setContentView(layout);
        }

        private void initView(final CommonCustomDialog dialog, LayoutInflater inflater) {
            //界面设置
//            View layout = inflater.inflate(R.layout.viewdialog_simple_style, null);
//
//            //添加一个额外的内容视图到屏幕上。
//            /*if (isSetBackGround == true) {
//                if (mHeight == 0) {
//					if (mWeight == 0) {
//						dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//					} else {
//						dialog.addContentView(layout, new ViewGroup.LayoutParams(mWeight, ViewGroup.LayoutParams.WRAP_CONTENT));
//					}
//				} else {
//					if (mWeight == 0) {
//						dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight));
//					} else {
//						dialog.addContentView(layout, new ViewGroup.LayoutParams(mWeight, mHeight));
//					}
//				}
//			} else {
//
//			}*/
//            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            dialog.setCancelable(mCancelable);
//            dialog.setCanceledOnTouchOutside(mOutCancelable);
//
//            LinearLayout ll = (LinearLayout) layout.findViewById(R.id.ll_content);
//            ll.removeView(mView);
//            ll.addView(mView);
//            // 设置title
//            if (mTitle != null) {
//                TextView tvTitle = (TextView) layout.findViewById(R.id.title);
//                tvTitle.setText(mTitle);
//            } else {
//                // 没有隐藏
//                layout.findViewById(R.id.title).setVisibility(
//                        View.GONE);
//                layout.findViewById(R.id.v_title2).setVisibility(View.GONE);
//                layout.findViewById(R.id.v_title1).setVisibility(View.GONE);
//            }
//            isShowing = dialog.isShowing();
//
//// 设置确定按钮
//            if (mPositiveButtonText != null) {
//                ((Button) layout.findViewById(R.id.positiveButton))
//                        .setText(mPositiveButtonText);
//                if (mPositiveButtonClickListener != null) {
//                    ((Button) layout.findViewById(R.id.positiveButton))
//                            .setOnClickListener(new View.OnClickListener() {
//                                public void onClick(View v) {
//                                    mPositiveButtonClickListener.onClick(dialog,
//                                            DialogInterface.BUTTON_POSITIVE);
//                                }
//                            });
//                }
//            } else {
//                // 没有则隐藏
//                layout.findViewById(R.id.positiveButton).setVisibility(
//                        View.GONE);
//                layout.findViewById(R.id.v_btn).setVisibility(View.GONE);
//            }
//
//            // 设置中心按钮
//            if (mNeutralButtonText != null) {
//                ((Button) layout.findViewById(R.id.neutralButton))
//                        .setText(mNeutralButtonText);
//                if (mNegativeButtonClickListener != null) {
//                    ((Button) layout.findViewById(R.id.neutralButton))
//                            .setOnClickListener(new View.OnClickListener() {
//                                public void onClick(View v) {
//                                    mNeutralButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEUTRAL);
//                                }
//                            });
//                }
//            } else {
//                // 没有隐藏
//                layout.findViewById(R.id.neutralButton).setVisibility(
//                        View.GONE);
//                layout.findViewById(R.id.v_btn2).setVisibility(View.GONE);
//            }
//            // 设置取消按钮
//            if (mNegativeButtonText != null) {
//                ((Button) layout.findViewById(R.id.negativeButton))
//                        .setText(mNegativeButtonText);
//                if (mNegativeButtonClickListener != null) {
//                    ((Button) layout.findViewById(R.id.negativeButton))
//                            .setOnClickListener(new View.OnClickListener() {
//                                public void onClick(View v) {
//                                    mNegativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
//                                }
//                            });
//                }
//            } else {
//                // 没有隐藏
//                layout.findViewById(R.id.negativeButton).setVisibility(
//                        View.GONE);
//                layout.findViewById(R.id.v_btn).setVisibility(View.GONE);
//            }
//            if (mNegativeButtonText == null && mPositiveButtonText == null && mNeutralButtonText == null) {
//                layout.findViewById(R.id.v).setVisibility(View.GONE);
//                layout.findViewById(R.id.layout_btn).setVisibility(View.GONE);
//            }
//
//            dialog.setContentView(layout);
        }


        private void initMessage3(final CommonCustomDialog dialog, LayoutInflater inflater) {
            //界面设置
            View layout = inflater.inflate(R.layout.messagedialog_simple_style3, null);

            dialog.setCancelable(mCancelable);
            //点击外边
            if (mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }

            //添加一个额外的内容视图到屏幕上。
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            // 设置内容
            if (mTitleMessage != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(mTitleMessage);
            } else if (mContentView != null) {
                //如果没有消息集
                //添加contentView身体的对话框
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(mContentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            }
            if (mTitleMessageTwo != null) {
                ((TextView) layout.findViewById(R.id.message2)).setText(mTitleMessageTwo);
            } else if (mContentView != null) {
                //如果没有消息集
                //添加contentView身体的对话框
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(mContentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            }
            if (mTitleMessageThree != null) {
                ((TextView) layout.findViewById(R.id.message3)).setText(mTitleMessageThree);
            } else if (mContentView != null) {
                //如果没有消息集
                //添加contentView身体的对话框
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(mContentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            }
            if (mTitleMessageFour != null) {
                ((TextView) layout.findViewById(R.id.message4)).setText(mTitleMessageFour);

            }
            // 设置内容
            if (mMessage != null) {
                ((TextView) layout.findViewById(R.id.tv_message)).setText(mMessage);
            } else if (mContentView != null) {
                //如果没有消息集
                //添加contentView身体的对话框
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(mContentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            }
            if (mMessageTwo != null) {
                ((TextView) layout.findViewById(R.id.tv_message2)).setText(mMessageTwo);
            } else if (mContentView != null) {
                //如果没有消息集
                //添加contentView身体的对话框
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(mContentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            }
            if (mMessageThree != null) {
                ((TextView) layout.findViewById(R.id.tv_message3)).setText(mMessageThree);
            } else if (mContentView != null) {
                //如果没有消息集
                //添加contentView身体的对话框
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(mContentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            }
            if (mMessageFour != null) {
                ((TextView) layout.findViewById(R.id.tv_message4)).setText(mMessageFour);


            }
            // 设置title
            if (mTitle != null) {
                ((TextView) layout.findViewById(R.id.title)).setText(mTitle);
                layout.findViewById(R.id.view_title).setVisibility(View.VISIBLE);
                ((TextView) layout.findViewById(R.id.message)).setTextSize(14);
                layout.findViewById(R.id.title_layout).setVisibility(
                        View.VISIBLE);
            } else {
                // 没有隐藏
                layout.findViewById(R.id.title).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.title_layout).setVisibility(
                        View.GONE);
                ((TextView) layout.findViewById(R.id.message)).setTextSize(17);
                layout.findViewById(R.id.view_title).setVisibility(View.GONE);
            }


            // 设置确定按钮
            if (mPositiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(mPositiveButtonText);
                if (mPositiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    mPositiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // 没有则隐藏
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.v_btn).setVisibility(View.GONE);
            }
            // 设置取消按钮
            if (mNegativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(mNegativeButtonText);
                if (mNegativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    mNegativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // 没有隐藏
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.v_btn).setVisibility(View.GONE);
            }
            dialog.setContentView(layout);
        }


        private void initMessage2(final CommonCustomDialog dialog, LayoutInflater inflater) {

            //界面设置
            View layout = inflater.inflate(R.layout.messagedialog_simple_style2, null);

            dialog.setCancelable(mCancelable);
            //点击外边
            if (mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }

            //添加一个额外的内容视图到屏幕上。
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            // 设置内容
            if (mMessage != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(mMessage);
            } else if (mContentView != null) {
                //如果没有消息集
                //添加contentView身体的对话框
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(mContentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            }
            if (mMessageTwo != null) {
                ((TextView) layout.findViewById(R.id.message2)).setText(mMessageTwo);
            } else if (mContentView != null) {
                //如果没有消息集
                //添加contentView身体的对话框
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(mContentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            }
            if (mMessageThree != null) {
                ((TextView) layout.findViewById(R.id.message3)).setText(mMessageThree);
            } else if (mContentView != null) {
                //如果没有消息集
                //添加contentView身体的对话框
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(mContentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            }
            // 设置title
            if (mTitle != null) {
                ((TextView) layout.findViewById(R.id.title)).setText(mTitle);
                layout.findViewById(R.id.view_title).setVisibility(View.VISIBLE);
                ((TextView) layout.findViewById(R.id.message)).setTextSize(14);
                layout.findViewById(R.id.title_layout).setVisibility(
                        View.VISIBLE);
            } else {
                // 没有隐藏
                layout.findViewById(R.id.title).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.title_layout).setVisibility(
                        View.GONE);
                ((TextView) layout.findViewById(R.id.message)).setTextSize(17);
                layout.findViewById(R.id.view_title).setVisibility(View.GONE);
            }


            // 设置确定按钮
            if (mPositiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(mPositiveButtonText);
                if (mPositiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    mPositiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // 没有则隐藏
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.v_btn).setVisibility(View.GONE);
            }
            // 设置取消按钮
            if (mNegativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(mNegativeButtonText);
                if (mNegativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    mNegativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // 没有隐藏
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.v_btn).setVisibility(View.GONE);
            }
            dialog.setContentView(layout);
        }


        /**
         * description:单输入框
         * class:
         * author: GJX
         * date:
         */
        private void initEditText(final CommonCustomDialog dialog, LayoutInflater inflater) {

//            //界面设置
//            View layout = inflater.inflate(R.layout.edittextdialog_simple_style, null);
//            final EditText etNum = (EditText) layout.findViewById(R.id.edittext);
//            et = etNum;
//            dialog.setCancelable(mCancelable);
//            //点击外边
//            if (mCancelable) {
//                dialog.setCanceledOnTouchOutside(true);
//            }
//
//            //添加一个额外的内容视图到屏幕上。
//            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//            // 设置内容
//            switch (mStyle) {
//                case 0://电话号
//                    etNum.setInputType(BIND_IMPORTANT);
//                    etNum.setMaxEms(11);
//                    etNum.setKeyListener(new
//                            DigitsKeyListener(false, true));
//                    break;
//                case 1:
//                    etNum.setInputType(BIND_IMPORTANT);
//                    break;
//                case 2:
//                    etNum.setInputType(BIND_IMPORTANT);
//                    etNum.setKeyListener(new DigitsKeyListener(false, true));
//                    break;
//                case 3:
//                    etNum.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    break;
//                default:
//                    break;
//            }
//
//
//            // 设置title
//            if (mTitle != null) {
//                ((TextView) layout.findViewById(R.id.title)).setText(mTitle);
//                layout.findViewById(R.id.view_title).setVisibility(View.VISIBLE);
//            } else {
//                // 没有隐藏
//                layout.findViewById(R.id.title).setVisibility(
//                        View.GONE);
//                layout.findViewById(R.id.view_title).setVisibility(View.GONE);
//            }
//
//
//            // 设置确定按钮
//            if (mPositiveButtonText != null) {
//                ((Button) layout.findViewById(R.id.positiveButton))
//                        .setText(mPositiveButtonText);
//                if (mPositiveButtonClickListener != null) {
//                    ((Button) layout.findViewById(R.id.positiveButton))
//                            .setOnClickListener(new View.OnClickListener() {
//                                public void onClick(View v) {
//                                    getEditText(etNum.getText().toString().trim());
//                                    mPositiveButtonClickListener.onClick(dialog,
//                                            DialogInterface.BUTTON_POSITIVE);
//
//
//                                }
//                            });
//                }
//            } else {
//                // 没有则隐藏
//                layout.findViewById(R.id.positiveButton).setVisibility(
//                        View.GONE);
//                layout.findViewById(R.id.v_btn).setVisibility(View.GONE);
//            }
//            // 设置取消按钮
//            if (mNegativeButtonText != null) {
//                ((Button) layout.findViewById(R.id.negativeButton))
//                        .setText(mNegativeButtonText);
//                if (mNegativeButtonClickListener != null) {
//                    ((Button) layout.findViewById(R.id.negativeButton))
//                            .setOnClickListener(new View.OnClickListener() {
//                                public void onClick(View v) {
//                                    mNegativeButtonClickListener.onClick(dialog,
//                                            DialogInterface.BUTTON_NEGATIVE);
//                                }
//                            });
//                }
//            } else {
//                // 没有隐藏
//                layout.findViewById(R.id.negativeButton).setVisibility(
//                        View.GONE);
//                layout.findViewById(R.id.v_btn).setVisibility(View.GONE);
//            }
//            dialog.setContentView(layout);

        }


        /**
         * description:信息视窗
         * class:
         * author: GJX
         * date:
         */
        private void initMessage(final CommonCustomDialog dialog, LayoutInflater inflater) {

            //界面设置
            View layout = inflater.inflate(R.layout.messagedialog_simple_style, null);

            dialog.setCancelable(mCancelable);
            //点击外边
            if (mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }

            //添加一个额外的内容视图到屏幕上。
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            // 设置内容
            if (mMessage != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(mMessage);
            } else if (mContentView != null) {
                //如果没有消息集
                //添加contentView身体的对话框
                ((LinearLayout) layout.findViewById(R.id.content))
                        .removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content))
                        .addView(mContentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            }
            // 设置title
            if (mTitle != null) {
                ((TextView) layout.findViewById(R.id.title)).setText(mTitle);
                layout.findViewById(R.id.view_title).setVisibility(View.VISIBLE);
                ((TextView) layout.findViewById(R.id.message)).setTextSize(14);
                layout.findViewById(R.id.title_layout).setVisibility(
                        View.VISIBLE);
            } else {
                // 没有隐藏
                layout.findViewById(R.id.title).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.title_layout).setVisibility(
                        View.GONE);
                ((TextView) layout.findViewById(R.id.message)).setTextSize(17);
                layout.findViewById(R.id.view_title).setVisibility(View.GONE);
            }


            // 设置确定按钮
            if (mPositiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton))
                        .setText(mPositiveButtonText);
                if (mPositiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.positiveButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    mPositiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // 没有则隐藏
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.v_btn).setVisibility(View.GONE);
            }
            // 设置取消按钮
            if (mNegativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton))
                        .setText(mNegativeButtonText);
                if (mNegativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.negativeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    mNegativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // 没有隐藏
                layout.findViewById(R.id.negativeButton).setVisibility(
                        View.GONE);
                layout.findViewById(R.id.v_btn).setVisibility(View.GONE);
            }
            dialog.setContentView(layout);

        }

        /**
         * description:
         * class:
         * author: s
         * date:
         */
        private void initMapDialog(final CommonCustomDialog dialog, LayoutInflater inflater) {

            //界面设置
            View layout = inflater.inflate(R.layout.common_map_dialog_layout, null);

            dialog.setCancelable(mCancelable);
            //点击外边
            if (mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            //添加一个额外的内容视图到屏幕上。
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            RelativeLayout rlayout = (RelativeLayout) layout.findViewById(R.id.common_dialog_layoutview);
            rlayout.removeAllViews();
            rlayout.addView(nBView);
            dialog.setContentView(layout);

        }
    }
}
