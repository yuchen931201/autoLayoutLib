package com.tz.autolayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.tz.autolayoutlib.AutoScreen;
import com.tz.autolayoutlib.AutoScreenJ;

/**
 * @ComputerCode: YD-YF-2015083113-1
 * @Author: TianZhen
 * @QQ: 959699751
 * @CreateTime: Created on 2019/2/19 16:05
 * @Package: com.tz.autolayout
 * @Description:
 **/
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AutoScreen.Companion.auto(this);
//        AutoScreenJ.auto(this);
    }
}
