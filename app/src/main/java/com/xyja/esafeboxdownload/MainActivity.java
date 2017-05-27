package com.xyja.esafeboxdownload;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.xyja.downloadlibrary.RxDownload;

import java.io.File;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

    private static String DOWNLOAD_PATH = ROOT_PATH + File.separator
            + "xyja" + File.separator + "esafebox" + File.separator + "download";

    private String url = "http://www.jsxyja.com:8081/_files/app-release.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        File file = new File(DOWNLOAD_PATH);
        if ((null != file) && (!file.exists())) {
            file.mkdirs();
        }

        RxDownload.getInstance(this)
                .maxDownloadNumber(2)
                .maxRetryCount(3)
                .defaultSavePath(DOWNLOAD_PATH)
                .maxThread(3);

        download();
    }

    private void download() {

        RxDownload.getInstance(this)
                .serviceDownload(url)   //只需传url即可，添加一个下载任务
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(MainActivity.this, "开始下载", Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.w("", throwable);
                        Toast.makeText(MainActivity.this, "添加任务失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}