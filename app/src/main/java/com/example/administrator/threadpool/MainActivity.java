package com.example.administrator.threadpool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.threadpoollibrary.RunnableWithPriority;
import com.example.threadpoollibrary.ThreadPoolUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 1; i <= 10; i++) {
            ThreadPoolUtil.executeTask(new RunnableWithPriority(i) {
                @Override
                public void run() {
                    System.out.println(this.getPriority() + " started");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {
                    }
                    System.out.println(this.getPriority() + " finished");
                }
            });
        }

    }
}
