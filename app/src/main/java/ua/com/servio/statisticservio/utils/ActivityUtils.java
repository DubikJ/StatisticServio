package ua.com.servio.statisticservio.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ua.com.servio.statisticservio.R;

public class ActivityUtils {

    public ActivityUtils() {
    }

    public int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int)(dp * (displayMetrics.densityDpi / 160f));
    }

    public void showMessage(String textMessage, Context mContext) {
        if (textMessage == null || textMessage.isEmpty()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getString(R.string.questions_title_info));
        builder.setMessage(textMessage);

        builder.setNeutralButton(mContext.getString(R.string.questions_answer_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

        // TODO (start stub): to set size text in AlertDialog
        TextView textView = (TextView) alert.findViewById(android.R.id.message);
        textView.setTextSize(mContext.getResources().getDimension(R.dimen.text_size_standart));
        Button button1 = (Button) alert.findViewById(android.R.id.button1);
        button1.setTextSize(mContext.getResources().getDimension(R.dimen.text_size_standart));
        Button button2 = (Button) alert.findViewById(android.R.id.button2);
        button2.setTextSize(mContext.getResources().getDimension(R.dimen.text_size_standart));
        Button button3 = (Button) alert.findViewById(android.R.id.button3);
        button3.setTextSize(mContext.getResources().getDimension(R.dimen.text_size_standart));
        // TODO: (end stub) ------------------
    }

    public void showShortToast(Context mContext, String message){
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(Context mContext, String message){
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    public void showQuestion(Activity mActivity, String textTitle, String textMessage, final QuestionAnswer questionAnswer) {
        if (textMessage == null || textMessage.isEmpty()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.DialogTheme);
        builder.setTitle(textTitle != null && !textTitle.isEmpty() ? textTitle : mActivity.getString(R.string.questions_title_info));
        builder.setMessage(textMessage);

        builder.setPositiveButton(mActivity.getString(R.string.questions_answer_yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                questionAnswer.onPositiveAnsver();
            }
        });

        builder.setNegativeButton(mActivity.getString(R.string.questions_answer_no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                questionAnswer.onNegativeAnsver();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                questionAnswer.onNegativeAnsver();
            }
        });
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
//        alert.setCancelable(false);
        alert.show();

        // TODO (start stub): to set size text in AlertDialog
        TextView textView = (TextView) alert.findViewById(android.R.id.message);
        textView.setTextSize(mActivity.getResources().getDimension(R.dimen.text_size_standart));
        Button button1 = (Button) alert.findViewById(android.R.id.button1);
        button1.setTextSize(mActivity.getResources().getDimension(R.dimen.text_size_standart));
        Button button2 = (Button) alert.findViewById(android.R.id.button2);
        button2.setTextSize(mActivity.getResources().getDimension(R.dimen.text_size_standart));
        Button button3 = (Button) alert.findViewById(android.R.id.button3);
        button3.setTextSize(mActivity.getResources().getDimension(R.dimen.text_size_standart));
        // TODO: (end stub) ------------------
    }

    public interface QuestionAnswer {

        void onPositiveAnsver();

        void onNegativeAnsver();

        void onNeutralAnsver();

    }

    public void hideKeyboard(Context context){
        ((InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    public void showSelectionList(Activity mActivity, String textTitle, final List<String> listString, final ListItemClick listItemClick) {
        if (listString == null) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.DialogTheme);
        builder.setTitle(textTitle != null && !textTitle.isEmpty() ? textTitle : mActivity.getString(R.string.questions_title_info));
        builder.setAdapter(new ArrayAdapter<String>(mActivity,
                        R.layout.row_sevice_item, R.id.textItem, listString),
                                               new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog, int which) {
                                                       listItemClick.onItemClik(which, listString.get(which));
                                                   }
                                               });

        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    public interface ListItemClick {

        void onItemClik(int item, String text);
    }

    public void showDatePicket(Context context, int year, int monthOfYear, int dayOfMonth,
                               final DatePicketSet datePicketSet){

        DatePickerDialog.OnDateSetListener dateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                datePicketSet.onDateSet(year, monthOfYear, dayOfMonth);
            }
        };

        new DatePickerDialog(context, dateDialog, year, monthOfYear, dayOfMonth).show();


    }

    public interface DatePicketSet {

        void onDateSet(int year, int monthOfYear, int dayOfMonth);
    }


}
