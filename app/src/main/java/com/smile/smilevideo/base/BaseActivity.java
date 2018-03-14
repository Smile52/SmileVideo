package com.smile.smilevideo.base;

import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.smile.smilevideo.R;

import java.util.ArrayList;
import java.util.List;

/**封装baseActivity
 * Created by yaojiulong on 2016/12/20.
 */

public abstract class BaseActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    protected Context mContext;
    private static final int REQUEST_CODE_PERMISSION = 2020; //权限请求码
    private boolean isNeedCheckPermission = true; //判断是否需要检测，防止无限弹框申请权限
    private Toolbar mToolbar;
    private ProgressDialog mDialog;
    private ImageView mRightmenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //默认不弹出软键盘，在父类处理
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initView();
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViewId();
        initData();
        setListener();
    }

    protected void initData() {

    }
    protected void findViewId(){

    }

    protected void setListener(){

    }

    protected abstract void initView();

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedCheckPermission){
            checkAllPermission();
        }
    }

    /**
     * 检查全部的权限，无权限则申请相关权限
     */
    protected void checkAllPermission(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            List<String> needRequestPermissionList = getDeniedPermissions(getNeedPermissions());
            if (null != needRequestPermissionList && needRequestPermissionList.size() > 0) {
                ActivityCompat.requestPermissions(this, needRequestPermissionList.toArray(
                        new String[needRequestPermissionList.size()]), REQUEST_CODE_PERMISSION);
            }
        }
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     */
    private List<String> getDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) !=
                    PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                needRequestPermissonList.add(permission);
            }
        }
        return needRequestPermissonList;
    }


    /**
     *添加toolbar
     */
    public void addToolbar(){
        mToolbar = findView(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRightmenu= (ImageView) mToolbar.findViewById(R.id.toolbar_right_menu);
        mRightmenu.setVisibility(View.GONE);
    }

    public ImageView getToolbarmenu(){
        mRightmenu.setVisibility(View.VISIBLE);
        return mRightmenu;
    }

    /**
     * 设置toolbar标题
     * @param title
     */
    public void setToolbarTitle(String title){
        if (mToolbar!=null){
            mToolbar.setTitle(title);
        }
    }



    /**
     * 弹出Toast
     *
     * @param text
     */
    public void showToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 解决绑定view时类型转换
     * @param id
     * @param <E>
     * @return
     */
    @SuppressWarnings("unchecked")
    public final <E extends View> E findView(int id){
        try {
            return (E) findViewById(id);
        }catch (ClassCastException e){
            throw  e;
        }
    }

    /**
     * 授权之后回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_CODE_PERMISSION){
            if (!verifyPermissions(grantResults)) {
                permissionGrantedFail();
                showTipsDialog();
                isNeedCheckPermission = false;
            } else permissionGrantedSuccess();
        }
    }

    /**
     * 检测所有的权限是否都已授权
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示对话框
     */
    protected void showTipsDialog() {
        new AlertDialog.Builder(this).setTitle("提示信息").setMessage("当前应用缺少" + getDialogTipsPart()
                + "权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                }).show();
    }

    /**
     * 启动当前应用设置页面
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }


    /**
     * 获取需要进行检测的权限数组
     */
    protected abstract String[] getNeedPermissions();

    /**
     * 获取弹框提示部分内容
     *
     * @return
     */
    protected String getDialogTipsPart() {
        return "必要";
    }

    /**
     * 权限授权成功
     */
    protected abstract void permissionGrantedSuccess();

    /**
     * 权限授权失败
     */
    protected abstract void permissionGrantedFail();


    public void showProgressDialog(){
        mDialog= ProgressDialog.show(this,"提示","请稍等...",false);
    }

    public void dimssDialog(){
        if (mDialog!=null)
            mDialog.dismiss();
    }

  /*  *//**
     * 显示Snackbar
     * @param view 父布局
     * @param msg 内容
     *//*
    public void showSnackView(View view,String msg){
        Snackbar snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        ColoredSnackbar.alert(snackbar).show();
    }

    public View getView(){
        return getWindow().getDecorView();
    }

    *//**
     *
     * @param currentContext 当前context
     * @param cls 目标class
     * @param view 从哪个布局开始铺满
     *//*
    protected void startActivityWithFull(final Context currentContext, final Class<?> cls, final View view){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CircularAnim.fullActivity((Activity)currentContext,view)
                        .go(new CircularAnim.OnAnimationEndListener() {
                            @Override
                            public void onAnimationEnd() {
                                startActivity(new Intent(currentContext, cls));
                                finish();
                            }
                        });
            }
        },1000);
    }
*/
    /**
     * 实现点击空白处，软键盘消失事件
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏软件盘
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void initSwipeRefreshLayout(SwipeRefreshLayout layout){
        layout.setColorSchemeResources(R.color.refresh_color_1
                , R.color.refresh_color_2, R.color.refresh_color_3, R.color.refresh_color_4);
    }
}
