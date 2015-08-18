package com.lhh.test.gkbh.getkeyboardheight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class MainActivity extends ActionBarActivity {

    View view = null;
    View et = null;
    Button btn_hide = null;
    int defaultSize = 0;
    int keyboardHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.rlRoot);
        et = findViewById(R.id.et);
        btn_hide = (Button)findViewById(R.id.btn_hide);
        btn_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int[] location = new int[2];
                et.getLocationOnScreen(location);
                int y = location[1];
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)et.getLayoutParams();
                params.setMargins(params.leftMargin, y, params.rightMargin, keyboardHeight);
                et.setLayoutParams(params);
                hideSoftKeyborad(MainActivity.this);
            }
        });
        defaultSize = getDefaultSize();
        getScreenSize();
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                int heightDifference = r.bottom;
                if(defaultSize - heightDifference <= 100){
                    //没有软键盘
                }else {
                    //变化了，有软键盘
                    keyboardHeight = defaultSize - heightDifference;
                    Log.i("Keyboard Size", "Size: " + heightDifference);
                }

            }
        });
    }

    public static void hideSoftKeyborad(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    int mScreenHeight = 0;
    int statusBarHeight = 0;

    private void getScreenSize(){
        WindowManager windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        mScreenHeight = windowManager.getDefaultDisplay().getHeight() - statusBarHeight;
    }

    public int getDefaultSize(){
        Rect r = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        statusBarHeight = r.top;
        int heightDifference = r.bottom - r.top;
        return heightDifference;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
